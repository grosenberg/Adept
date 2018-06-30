/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
import java.util.Map.Entry;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Settings;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.Analyzer;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.load.CorpusData;
import net.certiv.adept.model.load.FeatureSet;
import net.certiv.adept.tool.ErrorDesc;
import net.certiv.adept.unit.HashMultilist;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Maths;
import net.certiv.adept.util.Utils;

public class CorpusModel {

	private static final String DocMsg = "Merging  %3d %5d ------------------- %s";
	private static final String UnqMsg = "  Unique %3d %5d %7.2f%% %9.4f%%";
	private static final String CorMsg = "  Corpus %3d %5d %7.2f%% %9.4f%%";
	private static final String StatMSG = "%s: %5.2f%% of %s hits (%s documents)";

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

	// key=docId; value=ref count;
	private Map<Integer, Integer> freq = new HashMap<>();

	public CorpusModel() {
		pathnames = new LinkedHashMap<>();
		corpus = new HashMap<>();
		sources = new HashMultilist<>();
	}

	/** Constructor for creating a scratch corpus model. */
	public CorpusModel(CoreMgr mgr, Path corpusDir) {
		this();
		this.mgr = mgr;
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
			mgr.getTool().toolInfo(this, "Model invalid: different number of documents");
			return false;
		}

		List<String> docnames = new ArrayList<>();
		for (Document doc : corpusDocs) {
			String docname = doc.getPathname();
			if (Utils.getLastModified(Paths.get(docname)) > lastModified) {
				mgr.getTool().toolInfo(this, "Model invalid: later modified document " + docname);
				return false;
			}
			docnames.add(docname);
		}

		ArrayList<String> pn = new ArrayList<>(pathnames.values());
		pn.removeAll(docnames);
		if (!pn.isEmpty()) {
			mgr.getTool().toolInfo(this, "Model invalid: difference in sets of documents " + pn);
			return false;
		}
		return true;
	}

	/**
	 * Determines whether the corpus model document set, as stored, has been modified relative to the
	 * processed corpus.
	 */
	public boolean modified() {
		for (String pathname : pathnames.values()) {
			Path path = Paths.get(pathname);
			if (!path.toFile().isFile()) return true;
			if (Utils.getLastModified(path) > lastModified) return true;
		}
		return false;
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
		int[] docCnt = reportDoc(featureSetSize(features), name);

		// identify the unique document features & merge non-unique
		Map<Integer, Feature> uniques = reduce(features);
		reportUnq(docCnt, featureSetSize(uniques.values()));

		// merge unique features, implicitly including token refs
		int[] before = featureSetSize(corpus.values());
		corpus.putAll(uniques);
		reportCor(before, featureSetSize(corpus.values()));
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

	// reduce equivalent duplicates, merging to self
	private Feature reduceRefs(Feature feature) {
		List<RefToken> results = new ArrayList<>();

		for (RefToken ref : feature.getRefs()) {
			RefToken equiv = findEqiv(results, ref);
			if (equiv == null) {
				results.add(ref);
			} else {
				equiv.merge(ref);
				equiv.addRank(ref.getRank());
			}
		}

		feature.setRefs(results);
		return feature;
	}

	// reduce equivalent duplicates, merging to dest
	private Feature reduceRefs(Feature dest, Feature feature) {
		List<RefToken> existing = new ArrayList<>(dest.getRefs());

		for (RefToken ref : feature.getRefs()) {
			RefToken equiv = findEqiv(existing, ref);
			if (equiv == null) {
				existing.add(ref);
			} else {
				equiv.merge(ref);
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
	public void postBuild(ParseRecord data, Settings settings) {
		Feature.clearPool();
		Analyzer.evaluate(mgr.getTool(), data, getCorpusFeatures());
		consistent = true;
		save(settings.corpusDir);
	}

	private void save(final Path corpusDir) {
		lastModified = Utils.now();
		consistent = true;
		Thread t = new Thread(mgr.getThreadGroup(), new Runnable() {

			@Override
			public void run() {
				try {
					CorpusData.save(corpusDir, CorpusModel.this, true);
					lastModified = Utils.getLastModified(corpusDir);
				} catch (Exception e) {
					consistent = false;
					mgr.getTool().toolError(this, ErrorDesc.MODEL_SAVE_FAILURE, e, e.getMessage());
				}
			};
		});
		t.start();
	}

	public HashMultilist<Integer, Feature> getDocFeatures() {
		return sources;
	}

	/** Returns all features in the CorpusDocs model */
	public List<Feature> getCorpusFeatures() {
		return new ArrayList<>(corpus.values());
	}

	public int getCorpusFeaturesCount() {
		return corpus.size();
	}

	public int getCorpusRefTokensCount() {
		int cnt = 0;
		for (Feature feature : corpus.values()) {
			cnt += feature.getRefs().size();
		}
		return cnt;
	}

	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	public Map<Integer, String> getPathnames() {
		return pathnames;
	}

	/**
	 * For the given document feature, match each contained ref token to a corresponding corpus 'best'
	 * matching feature/ref token. The document ref tokens are updated with the match found or
	 * {@code null} if no suitable match is found in the corpus.
	 */
	public void match(Feature feature) {
		if (!feature.isMatched()) {
			// corpus features that might contain a valid match
			Feature matched = corpus.get(feature.getKey());
			if (matched != null) { // TODO: no feature match, so find nearest
				for (RefToken ref : feature.getRefs()) {
					// key=similarity, value=match ref tokens; descending order
					TreeMultiset<Double, RefToken> scored = matches(ref, matched);
					ref.chooseBest(scored);
					recordMatch(ref.matched);
				}
			}
			feature.setMatched(true);
		}
	}

	private void recordMatch(RefToken matched) {
		if (matched != null) {
			for (Integer docId : matched.docIds) {
				if (freq.containsKey(docId)) {
					freq.put(docId, freq.get(docId) + 1);
				} else {
					freq.put(docId, 1);
				}
			}
		}
	}

	public String getMatchStat() {
		int docId = getPrimaryMatchDocId();
		String docName = getPathname(docId);
		docName = Paths.get(docName).getFileName().toString();

		double total = Maths.sum(freq.values());
		int cnt = freq.get(docId);
		double pct = cnt * 100 / total;
		int docCnt = freq.size();
		return String.format(StatMSG, docName, pct, total, docCnt);
	}

	private int getPrimaryMatchDocId() {
		int docId = -1;
		int primary = -1;
		for (Entry<Integer, Integer> entry : freq.entrySet()) {
			if (entry.getValue() > primary) {
				docId = entry.getKey();
				primary = entry.getValue();
			}
		}
		return docId;
	}

	/** For visualization: find the corpus feature matching the given document feature. */
	public Feature getMatchingFeature(Feature srcFeature) {
		return corpus.get(srcFeature.getKey());
	}

	/** For visualization: find the refs that might be a match; results in descending order. */
	public TreeMultiset<Double, RefToken> getScoredMatches(Feature feature, RefToken ref) {
		Feature matched = corpus.get(feature.getKey());
		return matches(ref, matched);
	}

	// scores the given token ref against each of the token refs of the given feature
	// results are provided in a descending score keyed multiset
	private TreeMultiset<Double, RefToken> matches(RefToken ref, Feature feature) {
		TreeMultiset<Double, RefToken> scored = new TreeMultiset<>(Collections.reverseOrder());
		double[] maxRank = null;
		maxRank = feature.maxRank();
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

	/** Prepare the corpus for a new round of matching. */
	public void reset() {
		for (Feature feature : corpus.values()) {
			feature.setMatched(false);
		}
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

	private int[] reportDoc(int[] docCnt, String name) {
		mgr.getTool().toolInfo(this, "=============================================================================");
		mgr.getTool().toolInfo(this, String.format(DocMsg, docCnt[0], docCnt[1], name));
		return docCnt;
	}

	private void reportUnq(int[] docCnt, int[] unqCnt) {
		double upercent = unqCnt[0] * 100.0 / docCnt[0];
		double rpercent = unqCnt[1] * 100.0 / docCnt[1];

		mgr.getTool().toolInfo(this, String.format(UnqMsg, unqCnt[0], unqCnt[1], upercent, rpercent));
	}

	private void reportCor(int[] before, int[] after) {
		double chgFeatures = (after[0] - before[0]) * 100.0 / after[0];
		double chgRefs = (after[1] - before[1]) * 100.0 / after[1];
		mgr.getTool().toolInfo(this, String.format(CorMsg, after[0], after[1], chgFeatures, chgRefs));
	}

	private int[] featureSetSize(Collection<Feature> features) {
		int refs = 0;
		for (Feature feature : features) {
			refs += feature.getRefs().size();
		}
		return new int[] { features.size(), refs };
	}
}
