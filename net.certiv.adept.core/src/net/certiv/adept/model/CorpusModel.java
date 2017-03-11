package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.parser.Collector;
import net.certiv.adept.topo.Accumulator;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CorpusModel extends CorpusBase {

	private static final long GAP = 500000;

	@Expose private String corpusDirname;
	@Expose private long lastModified;

	// list of document names that represented in the corpus
	// key=docId; value=document pathname
	@Expose private Map<Integer, String> pathnames;
	// key=label; value=boost
	@Expose private Map<Factor, Double> labelBoosts;

	// corpus path (interface, so cannot be serialized)
	private Path corpusDir;
	// list of features that represent the corpus as a whole
	private List<Feature> features;
	// key = docId; value = contained features
	private Map<Integer, List<Feature>> docFeatures;
	// key = feature type; value = corresponding features
	private ArrayListMultimap<Long, Feature> index;

	private boolean consistent; // current state of model

	public CorpusModel() {
		super();
		pathnames = new LinkedHashMap<>();
		labelBoosts = new HashMap<>();
		features = new ArrayList<>();
		docFeatures = new LinkedHashMap<>();
		index = ArrayListMultimap.create();
	}

	public CorpusModel(Path corpusDir) {
		this();
		setCorpusDir(corpusDir);
	}

	/**
	 * Determines whether the corpus model, typically as freshly loaded from storage, is consistent
	 * with the actual document set contained within the corpus.
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
		this.corpusDir = corpusDir;
		this.corpusDirname = corpusDir.toString();
	}

	/** Add new document to the corpus model */
	public void add(Document doc) {
		write(corpusDir, doc);
	}

	/** Merge features collected during an ab initio build into the corpus model. */
	public void merge(Collector collector) {
		Document doc = collector.getDocument();
		pathnames.put(doc.getDocId(), doc.getPathname());
		docFeatures.put(doc.getDocId(), collector.getFeatures());
		List<Feature> weighted = weighFeatures(collector.getNonRuleFeatures());
		features.addAll(weighted);
		Log.debug(this, String.format("Processed %s (%s)", doc.getPathname(), weighted.size()));
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(Features loader) {
		docFeatures.put(loader.getDocId(), loader.getFeatures());
		features.addAll(loader.getReducedFeatures());
	}

	/** Perform operations to finalize the rebuilt corpus. */
	public void finalizeBuild() {
		getFeatureIndex();
		for (Long type : index.keySet()) {
			List<Feature> tfs = index.get(type);
			Accumulator accum = new Accumulator();
			for (Feature tf : tfs) {
				accum.gather(tf);
			}
			for (Feature tf : tfs) {
				accum.apply(tf);
			}
		}
		consistent = true;
	}

	/** Returns all features in the Corpus model */
	public List<Feature> getFeatures() {
		return features;
	}

	/** Returns a map, keyed by feature type, of all features in the Corpus model */
	public ArrayListMultimap<Long, Feature> getFeatureIndex() {
		if (index.isEmpty()) buildIndex();
		return index;
	}

	@Override
	public Map<Integer, List<Feature>> getDocFeatures() {
		return docFeatures;
	}

	@Override
	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	public Map<Integer, String> getPathnames() {
		return pathnames;
	}

	public Map<Factor, Double> getLabelBoosts() {
		if (labelBoosts.isEmpty()) {
			Factor.loadDefaults(labelBoosts);
		}
		return labelBoosts;
	}

	public void setLabelBoosts(Map<Factor, Double> labelBoosts) {
		this.labelBoosts = labelBoosts;
	}

	public TreeMultimap<Double, Feature> match(Feature target) {
		if (index.isEmpty()) buildIndex();

		TreeMultimap<Double, Feature> matches = TreeMultimap.create();
		List<Feature> typedList = index.get(target.getType());
		if (typedList != null) {
			for (Feature feature : typedList) {
				if (feature.getKind() == Kind.RULE) {
					Log.error(this, "Found rule in match set");
					continue;
				}

				int tTypes = target.getEdgeSet().getTypeCount();
				int fTypes = feature.getEdgeSet().getTypeCount();

				if (fTypes >= tTypes - 1 && fTypes <= tTypes + 1) {
					// compute and save relative distance between features
					double dist = target.distance(feature);
					matches.put(dist, feature);
				}
			}
		}
		return matches;
	}

	// index the corpus features by feature type
	private void buildIndex() {
		for (Feature f : features) {
			index.put(f.getType(), f);
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

	@Override
	public void save(Path corpusDir) throws Exception {
		lastModified = Time.now();
		super.save(corpusDir);
		lastModified = Time.getLastModified(corpusDir);
		consistent = true;
	}

	@Override
	public void write(Path corpusDir, Document doc) {
		super.write(corpusDir, doc);
		consistent = true;
	}

	/*
	 * Reduces the feature set initially constructed from a corpus document by collapsing equivalent
	 * root features to single instances with correspondingly increased weight. Equivalency is
	 * defined by identity of root feature type, equality of edge sets, including leaf node text,
	 * and identity of format.
	 */
	private List<Feature> weighFeatures(List<Feature> roots) {
		ArrayListMultimap<Long, Feature> index = ArrayListMultimap.create();
		for (Feature f : roots) {
			index.put(f.getType(), f);
		}
		List<Feature> weighted = new ArrayList<>();

		int tot = 0;
		int unq = 0;
		for (Long type : index.keySet()) {
			List<Feature> features = index.get(type);
			tot += features.size();
			List<Feature> uniques = new ArrayList<>();
			for (Feature feature : features) {
				Feature unique = getEquivalent(uniques, feature);
				if (unique == null) {
					uniques.add(feature);
				} else {
					unique.mergeEquivalent(feature);
				}
			}
			weighted.addAll(uniques);
			unq += uniques.size();

			if (uniques.size() < features.size()) {
				String aspect = features.get(0).getAspect();
				Log.debug(this, String.format("Feature reduction for %12s (%5d) %5d -> %d", aspect, type,
						features.size(), uniques.size()));
			}
		}
		int reduction = 100 - (unq * 100 / tot);
		Log.debug(this, String.format("Feature reduction (%2d%%) %6d -> %d", reduction, tot, unq));

		return weighted;
	}

	private Feature getEquivalent(List<Feature> uniques, Feature feature) {
		for (Feature unique : uniques) {
			if (unique.equivalentTo(feature)) return unique;
		}
		return null;
	}
}
