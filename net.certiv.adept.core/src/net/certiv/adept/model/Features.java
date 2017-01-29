package net.certiv.adept.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.util.Log;

public class Features {

	@Expose private int docId;
	@Expose private String pathname;
	@Expose private List<Feature> features;

	public Features(int docId, String pathname, List<Feature> features) {
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

	/** Restore feature objects to edges using the persisted indicies */
	public void fixEdgeRefs() {
		Map<Integer, Feature> cache = new HashMap<>();
		for (Feature feature : features) {
			cache.put(feature.getId(), feature);
		}
		for (Feature feature : features) {
			for (List<Edge> edges : feature.getEdgesMap().values()) {
				for (Edge edge : edges) {
					edge.root = cache.get(edge.rootId);
					edge.leaf = cache.get(edge.leafId);
					if (edge.root == null || edge.leaf == null) {
						Log.error(this, "EdgeRef fixup failed.");
					}
				}
			}
		}
	}
}
