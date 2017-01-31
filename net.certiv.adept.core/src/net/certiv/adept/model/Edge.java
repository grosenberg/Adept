package net.certiv.adept.model;

import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Label;
import net.certiv.adept.util.Log;

public class Edge implements Comparable<Edge> {

	public Feature root; // root of this edge
	public Feature leaf; // leaf connected by this edge

	@Expose public EdgeKey edgeKey;

	@Expose public int rootId;
	@Expose public int leafId;

	@Expose public double metric;
	@Expose public double rarity;

	public Edge(Feature root, Feature leaf) {
		this.root = root;
		this.leaf = leaf;

		rootId = root.getId();
		leafId = leaf.getId();
		edgeKey = EdgeKey.create(leaf.getId(), leaf.getText());

		// euclidean distance
		metric = Math.sqrt(Math.pow(root.getX() - leaf.getX(), 2) + Math.pow(root.getY() - leaf.getY(), 2));
		rarity = 1;
	}

	public double similarity(Edge o) {
		Map<String, Double> boosts = Tool.mgr.getLabelBoosts();
		double sim = 0;
		sim += boosts.get(Label.RARITY) * Feature.norm(rarity, o.rarity);
		sim += boosts.get(Label.METRIC) * Feature.norm(metric, o.metric);
		return sim;
	}

	public boolean equivalentTo(Edge other) {
		if (root == null || leaf == null) return false;
		if (root.getType() != other.root.getType()) return false;
		if (leaf.getType() != other.leaf.getType()) return false;
		if (!root.getText().equals(other.root.getText())) return false;
		if (!leaf.getText().equals(other.leaf.getText())) return false;
		return true;
	}

	@Override
	public int compareTo(Edge o) {
		if (rootId != o.rootId) Log.error(this, "Wrong orientation for " + toString());
		if (leaf.getType() < o.leaf.getType()) return -1;
		if (leaf.getType() > o.leaf.getType()) return 1;
		if (root.getStart() < o.root.getStart()) return -1;
		if (root.getStart() > o.root.getStart()) return 1;
		return leaf.getText().compareTo(o.leaf.getText());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edgeKey == null) ? 0 : edgeKey.hashCode());
		result = prime * result + leafId;
		result = prime * result + rootId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Edge other = (Edge) obj;
		if (edgeKey == null) {
			if (other.edgeKey != null) return false;
		} else if (!edgeKey.equals(other.edgeKey)) return false;
		if (leafId != other.leafId) return false;
		if (rootId != other.rootId) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("{%s -> %s}", root.getAspect(), leaf.getAspect());
	}
}
