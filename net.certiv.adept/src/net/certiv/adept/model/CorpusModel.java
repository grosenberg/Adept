package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
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
import net.certiv.adept.unit.Pair;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CorpusModel {

	private static final long TIME_OUT = 500000;

	private static final String DocMsg = "Merging  %3d/%5d --------- %s";
	private static final String UnqMsg = "  Unique %3d/%5d %3d%%/%3d%%";
	private static final String CorMsg = "  Corpus %3d/%5d %3d%%/%3d%%";

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
		merge(doc.getDocId(), doc.getFilename(), builder.getFeatures());
	}

	/** Merge features as retrieved from persistant store into the corpus model. */
	public void merge(FeatureSet loader) {
		merge(loader.getDocId(), loader.getFilename(), loader.getFeatures());
	}

	private void merge(int docId, String name, List<Feature> features) {
		// retain the document features
		sources.put(docId, features);
		Pair<Integer, Integer> dfr = reportDoc(features, name);

		// identify the unique document features & merge non-unique
		Map<Integer, Feature> uniques = reduce(features);
		reportUnq(dfr, uniques.values());

		// merge unique features, implicitly including token refs
		Pair<Integer, Integer> cfr = featureSize(corpus.values());
		corpus.putAll(uniques);
		reportCor(cfr, corpus.values());
	}

	/*
	 * Reduces the given internally unique feature set to those unique relative to the corpus. For each
	 * unique, the token refs are reduced in the feature. For each non-unique, the feature token refs
	 * are reduced into the corpus feature.
	 */
	private Map<Integer, Feature> reduce(List<Feature> features) {
		Map<Integer, Feature> uniques = new HashMap<>();

		for (Feature feature : features) {
			feature = reduceRefs(feature.copy());

			Feature existing = corpus.get(feature.getKey());
			if (existing == null) {
				uniques.put(feature.getKey(), feature);

			} else {
				existing.addWeight(feature.getWeight());
				reduceRefs(existing, feature);
			}
		}
		return uniques;
	}

	// internally reduce equivalent duplicates, merging to self
	private Feature reduceRefs(Feature feature) {
		List<RefToken> results = new ArrayList<>();

		for (RefToken ref : feature.getRefs()) {
			RefToken equiv = findEqiv(results, ref);
			if (equiv == null) {
				results.add(ref);
			} else {
				equiv.addRank(ref.getRank());
			}
		}

		feature.setRefs(results);
		return feature;
	}

	// mutually reduce equivalent duplicates, merging to dest
	private Feature reduceRefs(Feature dest, Feature feature) {
		List<RefToken> existing = new ArrayList<>(dest.getRefs());

		for (RefToken ref : feature.getRefs()) {
			RefToken equiv = findEqiv(existing, ref);
			if (equiv == null) {
				existing.add(ref);
			} else {
				equiv.addRank(ref.getRank());
			}
		}

		dest.setRefs(existing);
		return dest;
	}

	private RefToken findEqiv(List<RefToken> targets, RefToken ref) {
		for (RefToken target : targets) {
			if (ref.equivalentTo(target)) return target;
		}
		return null;
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

	private Pair<Integer, Integer> reportDoc(List<Feature> features, String name) {
		Pair<Integer, Integer> sizes = featureSize(features);
		Log.debug(this, String.format(DocMsg, sizes.a, sizes.b, name));
		return sizes;
	}

	private Pair<Integer, Integer> reportUnq(Pair<Integer, Integer> fr, Collection<Feature> features) {
		Pair<Integer, Integer> sizes = featureSize(features);
		int upercent = sizes.a * 100 / fr.a;
		int rpercent = sizes.b * 100 / fr.b;

		Log.debug(this, String.format(UnqMsg, sizes.a, sizes.b, upercent, rpercent));
		return sizes;
	}

	private void reportCor(Pair<Integer, Integer> fr, Collection<Feature> features) {
		Pair<Integer, Integer> sizes = featureSize(features);
		int cpercent = (sizes.a - fr.a) * 100 / sizes.a;
		int rpercent = (sizes.b - fr.b) * 100 / sizes.b;

		Log.debug(this, String.format(CorMsg, sizes.a, sizes.b, cpercent, rpercent));
	}

	private Pair<Integer, Integer> featureSize(Collection<Feature> features) {
		int refs = 0;
		for (Feature feature : features) {
			refs += feature.getRefs().size();
		}
		return new Pair<>(features.size(), refs);
	}
}
