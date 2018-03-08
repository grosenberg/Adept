package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.load.CorpusData;
import net.certiv.adept.model.load.FeatureSet;
import net.certiv.adept.model.util.CorpusAnalyzer;
import net.certiv.adept.model.util.Matcher;
import net.certiv.adept.util.ArraySet;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.util.TreeMultimap;

public class CorpusModel {

	private static final String MSG = "Features %3s/%-4s (%2d%% unique) %s";
	private static final long TIME_OUT = 500000;

	@Expose private String corpusDirname;
	@Expose private long lastModified;

	// list of document names that represented in the corpus
	// key=docId; value=document pathname
	@Expose private Map<Integer, String> pathnames;

	// -----------------------------------------------------------------

	private CoreMgr mgr;
	private boolean consistent; // current state of model

	// list of features that consitute the corpus
	private List<Feature> corpus;

	// key = docId; value = document features
	private Map<Integer, List<Feature>> docFeatures;

	// key = compare key; value = subset of corpus features
	private TreeMultimap<Integer, Feature> index;

	public CorpusModel() {
		pathnames = new LinkedHashMap<>();
		corpus = new ArrayList<>();
		docFeatures = new LinkedHashMap<>();
		index = new TreeMultimap<>();
	}

	/** Constructor for creating a scratch corpus model. */
	public CorpusModel(Path corpusDir) {
		this();
		setCorpusDir(corpusDir);
	}

	public void dispose() {
		pathnames.clear();
		corpus.clear();
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
			if (Time.getLastModified(Paths.get(docname)) > lastModified + TIME_OUT) {
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
		this.corpusDirname = corpusDir.toString();
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	/** Merge features collected during a scratch build into the corpus model. */
	public void merge(Builder builder) {
		Document doc = builder.getDocument();
		pathnames.put(doc.getDocId(), doc.getPathname());

		List<Feature> features = builder.getFeatures();
		docFeatures.put(doc.getDocId(), features);
		List<Feature> reduced = reduce(features);
		corpus.addAll(reduced);
		for (Feature feature : reduced) {
			index.put(feature.getCompareKey(), feature);
		}

		int reducedSize = reduced.size();
		int totalSize = builder.getFeatures().size();
		int reduction = reducedSize * 100 / totalSize;
		Log.debug(this, String.format(MSG, reducedSize, totalSize, reduction, doc.getFilename()));
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(FeatureSet loader) {
		List<Feature> features = loader.getFeatures();
		docFeatures.put(loader.getDocId(), features);
		List<Feature> reduced = reduce(features);
		corpus.addAll(reduced);
		for (Feature feature : reduced) {
			index.put(feature.getCompareKey(), feature);
		}
	}

	/*
	 * Reduces the feature set initially constructed from a corpus document by collapsing comparable
	 * features to single instances with correspondingly increased equivalency weight.
	 */
	private List<Feature> reduce(List<Feature> features) {
		// key=typeKey; value=feature
		Map<Integer, Feature> reduced = new HashMap<>();

		for (Feature feature : features) {
			Feature existing = reduced.get(feature.getTypeKey());
			if (existing == null) {
				reduced.put(feature.getTypeKey(), feature);
			} else {
				existing.addEquivalent(feature.getDocId(), feature.getId());
				existing.addEquivalents(feature.getEquivalents());
			}
		}
		return (List<Feature>) reduced.values();
	}

	/** Perform operations to finalize the rebuilt corpus. */
	public void finalizeBuild(ParseRecord data) {
		CorpusAnalyzer analyzer = new CorpusAnalyzer(data);
		analyzer.execute();
		consistent = true;
	}

	public void save(Path corpusDir) throws Exception {
		lastModified = Time.now();
		CorpusData.save(corpusDir, this);
		lastModified = Time.getLastModified(corpusDir);
		consistent = true;
	}

	/** Returns all features in the CorpusDocs model */
	public List<Feature> getCorpusFeatures() {
		return corpus;
	}

	/** Returns a map, keyed by feature type, of all features in the CorpusDocs model */
	public TreeMultimap<Integer, Feature> getFeatureIndex() {
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

	/**
	 * For the given document feature, returns the 'best' matching feature from the corpus.
	 * <p>
	 * Public for visualization use.
	 */
	public Feature match(Feature feature) {
		ArraySet<Feature> comparables = index.get(feature.getCompareKey());

		// key=similarity, value=features
		TreeMultimap<Double, Feature> scored = Matcher.score(comparables, feature);
		ArraySet<Feature> possibles = scored.get(scored.lastKey());
		Feature best = Matcher.bestMatch(corpus, possibles);

		return best;
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
		corpus.clear();
	}
}
