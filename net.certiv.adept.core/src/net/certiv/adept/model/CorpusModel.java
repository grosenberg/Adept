package net.certiv.adept.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;

import net.certiv.adept.parser.Collector;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CorpusModel extends CorpusStore {

	private static final long GAP = 500000;

	// list of document names that represented in the corpus
	// key=doc id; value=document pathname
	@Expose Map<Integer, String> pathnames;
	@Expose private String corpusDirname;
	@Expose private long lastModified;

	// corpus path (interface, so cannot be serialized)
	private Path corpusDir;
	// list of features that represent the corpus as a whole
	private List<Feature> features;
	// same as sublists keyed by docId
	private Map<Integer, List<Feature>> docFeatures;
	// list of features keyed by feature type
	private Map<Integer, List<Feature>> index;

	private boolean consistent; // current state of model

	public CorpusModel() {
		super();
		pathnames = new LinkedHashMap<>();
		features = new ArrayList<>();
		docFeatures = new LinkedHashMap<>();
		index = new LinkedHashMap<>();
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

	/** Incorporate the collected features into the corpus model. */
	public void include(Collector collector) {
		Document doc = collector.getDocument();
		pathnames.put(doc.getDocId(), doc.getPathname());

		List<Feature> featureList = collector.getFeatures();

		docFeatures.put(doc.getDocId(), featureList);
		features.addAll(featureList);
		Log.debug(this, String.format("Processed %s [features=%s]", doc.getPathname(), featureList.size()));
	}

	/**
	 * Reduce the feature set of the initially constructed corpus model (containing all features
	 * from all documents). Equivalent features are collapsed to a single instance with
	 * correspondingly increased weight. Equivalency is defined by identity of feature type,
	 * equality of edge sets, identity of edge leaf node text, and identity of format.
	 */
	public void reduceConstraints() {
		clearIndex();
		getFeatureIndex();
		clearFeatures();
		addUniqueFeatures();
		clearIndex();
	}

	private void addUniqueFeatures() {
		int tot = 0;
		int unq = 0;
		for (Integer type : index.keySet()) {
			List<Feature> set = index.get(type);
			tot += set.size();
			String aspect = set.get(0).getAspect();
			List<Feature> sub = new ArrayList<>();
			for (Feature feature : set) {
				Feature equiv = getEquivalent(sub, feature);
				if (equiv == null) {
					sub.add(feature);
				} else {
					equiv.mergeEquivalent(feature);
				}
			}
			features.addAll(sub);
			unq += sub.size();

			if (sub.size() < set.size()) {
				Log.debug(this, String.format("Feature reduction for %12s (%5d) %5d -> %d", aspect, type, set.size(),
						sub.size()));
			}
		}
		int reduction = 100 - (unq * 100 / tot);
		Log.debug(this, String.format("Feature reduction (%2d%%) %6d -> %d", reduction, tot, unq));
	}

	private Feature getEquivalent(List<Feature> sub, Feature feature) {
		for (Feature f : sub) {
			if (f.equivalentTo(feature)) {
				return f;
			}
		}
		return null;
	}

	/** Add new document to the corpus model */
	public void add(Document doc) {
		write(corpusDir, doc);
	}

	/** Add collected features to the corpus model */
	public void add(Feature feature) {
		features.add(feature);
		consistent = true;
	}

	/** Add loaded feature sets to the corpus model */
	public void addAll(Features features) {
		if (this.features == null) {
			this.features = new ArrayList<>();
			this.docFeatures = new LinkedHashMap<>();
		}
		this.features.addAll(features.getFeatures());
		this.docFeatures.put(features.getDocId(), features.getFeatures());
	}

	@Override
	public String getPathname(int docId) {
		return pathnames.get(docId);
	}

	@Override
	public Map<Integer, List<Feature>> getDocFeatures() {
		return docFeatures;
	}

	/** Returns a map, keyed by feature type, of all features in the Corpus model */
	public Map<Integer, List<Feature>> getFeatureIndex() {
		if (index.isEmpty()) buildIndex();
		return index;
	}

	private void buildIndex() {
		for (Feature f : features) {
			List<Feature> fSet = index.get(f.getType());
			if (fSet == null) {
				fSet = new ArrayList<>();
				index.put(f.getType(), fSet);
			}
			fSet.add(f);
		}
	}

	/** Returns all features in the Corpus model */
	public List<Feature> getFeatures() {
		return features;
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
		// if (index != null)
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
		consistent = false;
	}

	@Override
	public void write(Path corpusDir, Document doc) {
		super.write(corpusDir, doc);
		consistent = true;
	}

	public TreeMap<Double, Feature> match(Feature feature) {
		if (index.isEmpty()) buildIndex();

		TreeMap<Double, Feature> om = new TreeMap<>();
		List<Feature> subList = index.get(feature.getType());
		for (Feature sub : subList) {
			om.put(feature.distance(sub), sub);
		}

		return om;
	}
}
