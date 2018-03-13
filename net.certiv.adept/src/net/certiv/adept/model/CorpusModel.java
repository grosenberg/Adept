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
import net.certiv.adept.util.HashMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;
import net.certiv.adept.util.TreeMultimap;

public class CorpusModel {

	private static final String MSG = "%3s/%-4s (%2d%%) unique features: %s";
	private static final long TIME_OUT = 500000;

	@Expose private String corpusDirname;
	@Expose private long lastModified;

	// list of document names that represent the corpus
	// key=docId; value=document pathname
	@Expose private Map<Integer, String> pathnames;

	// -----------------------------------------------------------------

	private CoreMgr mgr;
	private boolean consistent; // current state of model

	// the corpus feature set
	// key=unique hash key; value=feature
	private HashMap<Integer, Feature> corpus;

	// the corpus feature set
	// key=unique feature id; value=feature
	private HashMap<Integer, Feature> idFeature;

	// the document feature sets
	// key = docId; value = feature list
	private HashMultilist<Integer, Feature> sources;

	// key = compare hash key; value = subset of corpus features
	private TreeMultimap<Integer, Feature> keyFeature;

	public CorpusModel() {
		pathnames = new LinkedHashMap<>();
		corpus = new HashMap<>();
		idFeature = new HashMap<>();
		sources = new HashMultilist<>();
		keyFeature = new TreeMultimap<>();
	}

	/** Constructor for creating a scratch corpus model. */
	public CorpusModel(Path corpusDir) {
		this();
		setCorpusDir(corpusDir);
	}

	public void dispose() {
		pathnames.clear();
		corpus.clear();
		idFeature.clear();
		sources.clear();
		keyFeature.clear();
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

		// collect the document features
		List<Feature> features = builder.getFeatures();
		sources.put(doc.getDocId(), features);

		// update the corpus feature set
		Map<Integer, Feature> uniques = reduce(features);
		corpus.putAll(uniques);

		// update the corpus comparision keyFeature
		for (Feature feature : uniques.values()) {
			idFeature.put(feature.getId(), feature);
			keyFeature.put(feature.getKey(), feature);
		}

		int docsize = features.size();					// unique features in document
		int incsize = uniques.size();					// that are unique to corpus
		int raise = incsize * 100 / docsize;
		Log.debug(this, String.format(MSG, incsize, docsize, raise, doc.getFilename()));
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(FeatureSet loader) {

		// collect the document features
		List<Feature> features = loader.getFeatures();
		sources.put(loader.getDocId(), features);

		// update the corpus feature set
		Map<Integer, Feature> uniques = reduce(features);
		corpus.putAll(uniques);

		// update the corpus comparision keyFeature
		for (Feature feature : uniques.values()) {
			keyFeature.put(feature.getKey(), feature);
		}
	}

	/*
	 * Reduces a document feature set to those unique relative to the current corpus feature set. For
	 * non-unique document features, the equivalency weight of the corresponding corpus feature is
	 * increased by the document feature token refs.
	 */
	private Map<Integer, Feature> reduce(List<Feature> features) {
		// key=typeKey; value=feature
		Map<Integer, Feature> uniques = new HashMap<>();

		for (Feature feature : features) {
			Feature existing = corpus.get(feature.getKey());
			if (existing == null) {
				uniques.put(feature.getKey(), feature);
			} else {
				existing.addWeight(feature.getWeight());
			}
		}
		return uniques;
	}

	/** Perform operations to finalize the (re)built corpus. */
	public void finalizeBuild(ParseRecord data) {
		Feature.clearPool();
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
		return new ArrayList<>(corpus.values());
	}

	/** Returns a map, keyed by feature type, of all features in the CorpusDocs model */
	public TreeMultimap<Integer, Feature> getFeatureIndex() {
		return keyFeature;
	}

	public HashMultilist<Integer, Feature> getDocFeatures() {
		return sources;
	}

	public Feature getFeature(int id) {
		return idFeature.get(id);
	}

	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	public Map<Integer, String> getPathnames() {
		return pathnames;
	}

	/**
	 * For the given document feature, returns the sets of best matching features from the corpus,
	 * ordered by similarity.
	 */
	public TreeMultimap<Double, Feature> matches(Feature feature) {
		ArraySet<Feature> comparables = keyFeature.get(feature.getKey());
		return Matcher.score(comparables, feature);
	}

	/**
	 * For the given document feature, returns the 'best' matching feature from the corpus.
	 */
	public Feature match(Feature feature) {
		// key=similarity, value=features
		TreeMultimap<Double, Feature> scored = matches(feature);
		ArraySet<Feature> possibles = scored.get(scored.lastKey());
		Feature best = Matcher.bestMatch(getCorpusFeatures(), possibles);
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
		keyFeature.clear();
	}

	private void clearFeatures() {
		corpus.clear();
	}
}
