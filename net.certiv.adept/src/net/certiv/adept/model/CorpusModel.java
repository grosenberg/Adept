package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
import net.certiv.adept.unit.HashMultilist;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CorpusModel {

	private static final String MSG = "%3s/%-3s (%3d%%) unique features: %s";
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

	// the document feature sets
	// key = docId; value = feature list
	private HashMultilist<Integer, Feature> sources;

	public CorpusModel() {
		pathnames = new LinkedHashMap<>();
		corpus = new HashMap<>();
		sources = new HashMultilist<>();
	}

	/** Constructor for creating a scratch corpus model. */
	public CorpusModel(Path corpusDir) {
		this();
		setCorpusDir(corpusDir);
	}

	public void dispose() {
		pathnames.clear();
		corpus.clear();
		sources.clear();
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

		int docsize = features.size();					// unique features in document
		int corsize = uniques.size();					// unique to corpus
		int percent = corsize * 100 / docsize;
		Log.debug(this, String.format(MSG, corsize, docsize, percent, doc.getFilename()));
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(FeatureSet loader) {

		// collect the document features
		List<Feature> features = loader.getFeatures();
		sources.put(loader.getDocId(), features);

		// update the corpus feature set
		Map<Integer, Feature> uniques = reduce(features);
		corpus.putAll(uniques);
	}

	/**
	 * Reduces the given feature set to those unique relative to the current corpus feature set.
	 * <p>
	 * For each unique feature, the set of feature token refs reduced in the feature.
	 * <p>
	 * For each non-unique feature, the set of feature token refs are reduced into the corresponding
	 * corpus feature.
	 */
	private Map<Integer, Feature> reduce(List<Feature> features) {
		Map<Integer, Feature> uniques = new HashMap<>();
		for (Feature feature : features) {
			Feature existing = corpus.get(feature.getKey());
			if (existing == null) {
				uniques.put(feature.getKey(), feature.copy());

				List<RefToken> refs = reduceRefs(new ArrayList<>(), feature.getRefs());
				feature.setRefs(refs);

			} else {
				existing.addWeight(feature.getWeight());

				List<RefToken> refs = reduceRefs(existing.getRefs(), feature.getRefs());
				existing.setRefs(refs);
			}
		}
		return uniques;
	}

	private List<RefToken> reduceRefs(List<RefToken> existing, List<RefToken> refs) {
		List<RefToken> uniques = new ArrayList<>(refs);
		for (RefToken ref : refs) {
			for (RefToken exist : existing) {
				if (exist.equivalentTo(ref)) {
					exist.addRank(ref.getRank());
					uniques.remove(ref);
					break;
				}
			}
		}

		for (RefToken unique : uniques) {
			existing.add(unique.clone());
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

	public HashMultilist<Integer, Feature> getDocFeatures() {
		return sources;
	}

	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	public Map<Integer, String> getPathnames() {
		return pathnames;
	}

	/**
	 * For the given document feature, match each contained ref token to a corresponding corpus 'best'
	 * matching feature/ref token. The document ref tokens are update with the match found or
	 * {@code null} if no suitable match is found in the corpus.
	 */
	public void match(Feature feature) {
		if (!feature.isMatchDone()) {
			// corpus features that might contain a valid match
			Feature matched = corpus.get(feature.getKey());
			for (RefToken ref : feature.getRefs()) {

				// key=similarity, value=match ref tokens; descending order
				TreeMultiset<Double, RefToken> scored = matches(ref, matched);
				ref.chooseBest(scored);
			}
			feature.setMatchDone(true);
		}
	}

	/** For visualization: find the refs that might be a match; results in descending order. */
	public TreeMultiset<Double, RefToken> getScoredMatches(Feature feature, RefToken ref) {
		Feature featMatch = corpus.get(feature.getKey());
		return matches(ref, featMatch);
	}

	// scores the given token ref against each of the token refs of the given feature
	// results are provided in a descending score keyed multiset
	private TreeMultiset<Double, RefToken> matches(RefToken ref, Feature feature) {
		TreeMultiset<Double, RefToken> scored = new TreeMultiset<>(Collections.reverseOrder());

		int maxRank = feature.maxRank();
		for (RefToken match : feature.getRefs()) {
			scored.put(ref.score(match, maxRank), match);
		}
		return scored;
	}

	public boolean isConsistent() {
		return consistent;
	}

	public void setConsistent(boolean consistent) {
		this.consistent = consistent;
	}

	public void clear() {
		clearFeatures();
		pathnames.clear();
		lastModified = 0;
		consistent = false;
	}

	private void clearFeatures() {
		corpus.clear();
	}
}
