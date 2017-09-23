package net.certiv.adept.model.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.util.Log;

/**
 * Functions only as the persistance root for the storage of a feature set. Not used as part of the
 * operational model.
 */
public class FeatureSet {

	@Expose private int docId;
	@Expose private String pathname;
	@Expose private List<Feature> features;

	/**
	 * Collection of features.
	 * 
	 * @param docId the id of the document that originated these features
	 * @param pathname the pathname of the document that originated these features
	 * @param features the list of features identified in the document
	 */
	public FeatureSet(int docId, String pathname, List<Feature> features) {
		this.docId = docId;
		this.pathname = pathname;
		this.features = features;
	}

	public int getDocId() {
		return docId;
	}

	public String getPathname() {
		return pathname;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public List<Feature> getReducedFeatures() {
		List<Feature> result = new ArrayList<>();
		for (Feature feature : features) {
			if (feature.getKind() == Kind.RULE) continue;
			if (feature.isEquivalent()) continue;
			result.add(feature);
		}
		return result;
	}

	/** Restore feature objects to edgeSet using the persisted indicies */
	public void fixEdgeRefs() {
		Map<Long, Feature> cache = new HashMap<>();
		for (Feature feature : features) {
			cache.put(feature.getId(), feature);
		}
		for (Feature feature : features) {
			for (Edge edge : feature.getEdgeSet().getEdges()) {
				edge.root = cache.get(edge.rootId);
				edge.leaf = cache.get(edge.leafId);
				if (edge.root == null || edge.leaf == null) {
					Log.error(this, "EdgeRef fixup failed.");
				}
			}
		}
	}
}
