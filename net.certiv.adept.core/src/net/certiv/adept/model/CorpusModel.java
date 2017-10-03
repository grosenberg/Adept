package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.load.CorpusData;
import net.certiv.adept.model.load.FeatureSet;
import net.certiv.adept.model.parser.Builder;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.tune.Boosts;
import net.certiv.adept.model.util.Analyzer;
import net.certiv.adept.model.util.Chunk;
import net.certiv.adept.model.util.DamerauAlignment;
import net.certiv.adept.model.util.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.util.TreeMultimap;

public class CorpusModel {

	private static final String MSG = "Features %5s/%-5s (reduced %2d%%) %s";
	private static final long GAP = 500000;

	@Expose private String corpusDirname;
	@Expose private long lastModified;

	// list of document names that represented in the corpus
	// key=docId; value=document pathname
	@Expose private Map<Integer, String> pathnames;
	// boost values set
	@Expose private Boosts boosts;

	// corpus manager
	private ProcessMgr mgr;

	// list of features that represent the corpus as a whole
	private List<Feature> features;
	// key = docId; value = contained features
	private Map<Integer, List<Feature>> docFeatures;
	// key = feature type; value = corresponding features
	private ArrayListMultimap<Integer, Feature> index;

	private boolean consistent; // current state of model

	public CorpusModel() {
		super();
		pathnames = new LinkedHashMap<>();
		boosts = new Boosts();
		features = new ArrayList<>();
		docFeatures = new LinkedHashMap<>();
		index = ArrayListMultimap.create();
	}

	/** Constructor for creating a scratch corpus model. */
	public CorpusModel(Path corpusDir) {
		this();
		setCorpusDir(corpusDir);
		initBoosts();
	}

	public void dispose() {
		pathnames.clear();
		features.clear();
		docFeatures.clear();
		index.clear();
	}

	/**
	 * Determines whether the corpus model, typically as freshly loaded from storage, is consistent with
	 * the actual document set contained within the corpus.
	 */
	public boolean isValid(List<Document> corpusDocs) {
		if (pathnames.size() != corpusDocs.size()) {
			Log.info(this, "Model invalid: different number of documents");
			return false;
		}

		List<String> docnames = new ArrayList<>();
		for (Document doc : corpusDocs) {
			String docname = doc.getPathname();
			if (Time.getLastModified(Paths.get(docname)) > lastModified + GAP) {
				Log.info(this, "Model invalid: later modified document " + docname);
				return false;
			}
			docnames.add(docname);
		}

		ArrayList<String> pn = new ArrayList<>(pathnames.values());
		pn.removeAll(docnames);
		if (!pn.isEmpty()) {
			Log.info(this, "Model invalid: difference in sets of documents " + pn);
			return false;
		}
		return true;
	}

	public void setCorpusDir(Path corpusDir) {
		//		this.corpusDir = corpusDir;
		this.corpusDirname = corpusDir.toString();
	}

	public ProcessMgr getMgr() {
		return mgr;
	}

	public void setMgr(ProcessMgr mgr) {
		this.mgr = mgr;
	}

	/** Merge features collected during a scratch build into the corpus model. */
	public void merge(Builder builder) {
		Document doc = builder.getDocument();
		pathnames.put(doc.getDocId(), doc.getPathname());
		docFeatures.put(doc.getDocId(), builder.getAllFeatures());
		List<Feature> reduced = reduceFeatures(builder);
		features.addAll(reduced);

		int totalSize = builder.tokenTypeFeatureIndex.valueSize();
		int reducedSize = reduced.size();
		int reduction = 100 - (reducedSize * 100 / totalSize);
		Log.debug(this, String.format(MSG, reducedSize, totalSize, reduction, doc.getFilename()));
	}

	/* Reduces the feature set initially constructed from a corpus document by collapsing equivalent
	 * root features to single instances with correspondingly increased weight. Equivalency is defined
	 * by identity of root feature type, equality of edge sets, including leaf node text, and identity
	 * of format. */
	private List<Feature> reduceFeatures(ParseData data) {
		List<Feature> reduced = new ArrayList<>();
		for (Integer type : data.tokenTypeFeatureIndex.keySet()) {
			Set<Feature> typeFeatures = data.tokenTypeFeatureIndex.get(type);
			List<Feature> typeReduced = new ArrayList<>();
			for (Feature typeFeature : typeFeatures) {
				if (isUnique(typeReduced, typeFeature)) {
					typeReduced.add(typeFeature);
				}
			}
			reduced.addAll(typeReduced);
		}
		return reduced;
	}

	private boolean isUnique(List<Feature> uniques, Feature next) {
		for (Feature unique : uniques) {
			if (unique.equivalentTo(next)) {
				unique.mergeEquivalent(next);
				next.setEquivalent(true);
				return false;
			}
		}
		return true;
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(FeatureSet loader) {
		docFeatures.put(loader.getDocId(), loader.getFeatures());
		features.addAll(loader.getReducedFeatures());
	}

	/** Perform operations to finalize the rebuilt corpus. */
	public void finalizeBuild() {
		getFeatureIndex();
		for (int type : index.keySet()) {
			List<Feature> tfs = index.get(type);
			Analyzer ana = new Analyzer();
			for (Feature tf : tfs) {
				ana.accum(tf);
			}
			for (Feature tf : tfs) {
				ana.apply(tf);
			}
		}
		consistent = true;
	}

	public void save(Path corpusDir) throws Exception {
		lastModified = Time.now();
		CorpusData.save(corpusDir, this);
		lastModified = Time.getLastModified(corpusDir);
		consistent = true;
	}

	/** Returns all features in the CorpusDocs model */
	public List<Feature> getFeatures() {
		return features;
	}

	/** Returns a map, keyed by feature type, of all features in the CorpusDocs model */
	public ArrayListMultimap<Integer, Feature> getFeatureIndex() {
		if (index.isEmpty()) buildIndex();
		return index;
	}

	public Map<Integer, List<Feature>> getDocFeatures() {
		return docFeatures;
	}

	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	public Map<Integer, String> getPathnames() {
		return pathnames;
	}

	public void initBoosts() {
		for (Factor factor : Factor.values()) {
			boosts.put(factor, factor.getWeight());
		}
	}

	public double getBoost(Factor factor) {
		return boosts.get(factor);
	}

	public Boosts getBoosts() {
		return boosts;
	}

	public void setBoosts(Boosts boosts) {
		this.boosts = boosts;
	}

	/**
	 * Returns a multimap, ordered by {@code key=similarity value}, of the corpus features 'matching'
	 * the given feature. The last key corresponds to the features with the highest similarity.
	 * 
	 * The feature matching function first subselects for corpus features based on similarity of
	 * ancestor path, subject to an identity requirement for the initial terminal node. The resulting
	 * selection of corpus features is then ranked on a combination of ancestor path similarity and
	 * mutual feature similarity for each corpus feature.
	 * 
	 * While it should be unlikely, multiple features could have the same similarity value.
	 * 
	 * Public for visualization use.
	 */
	public TreeMultimap<Double, Feature> match(Feature docFeature) {
		List<Integer> ancestors = docFeature.getAncestorPath();
		// key=ancestors similarity, value=features
		TreeMultimap<Double, Feature> typeMatches = match(ancestors);

		// cluster and restrict number of matches then consdered
		Chunk.eval(typeMatches);
		typeMatches = Chunk.bestMatches();

		// key=total similarity, value=feature
		TreeMultimap<Double, Feature> results = new TreeMultimap<>();
		for (Double ancestorSim : typeMatches.keySet()) {
			Set<Feature> corFeatures = typeMatches.get(ancestorSim);
			for (Feature corFeature : corFeatures) {
				if (corFeature.getKind() == Kind.RULE) {
					Log.error(this, "Found rule in feature match set");
					continue;
				}

				double sim = docFeature.similarity(corFeature);
				results.put(sim, corFeature);
			}
		}
		return results;
	}

	// constrains to features of the same terminal 'type' 
	// then computes similarity of the parent ancestors 
	private TreeMultimap<Double, Feature> match(List<Integer> ancestors) {
		List<Feature> matching = index.get(ancestors.get(0));
		TreeMultimap<Double, Feature> results = new TreeMultimap<>();
		for (Feature match : matching) {
			List<Integer> apm = match.getAncestorPath();
			double dist = DamerauAlignment.distance(ancestors.subList(1, ancestors.size()), apm.subList(1, apm.size()));
			double sim = DamerauAlignment.simularity(dist, ancestors.size(), apm.size());
			results.put(sim, match);
		}
		return results;
	}

	// index the corpus features by feature type
	// also used to find node features that match the sequence anchor of the ancestor path  
	private void buildIndex() {
		for (Feature feature : features) {
			index.put(feature.getType(), feature);
		}
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public void clear() {
		clearIndex();
		clearFeatures();
		pathnames.clear();
		lastModified = 0;
		consistent = false;
	}

	private void clearIndex() {
		index.clear();
	}

	private void clearFeatures() {
		features.clear();
	}
}
