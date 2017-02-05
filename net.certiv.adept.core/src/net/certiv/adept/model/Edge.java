package net.certiv.adept.model;

import java.util.Map;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.model.equiv.EEdge;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.util.Log;

public class Edge implements Comparable<Edge> {

	public Feature root; // root of this edge
	public Feature leaf; // leaf connected by this edge

	@Expose public EdgeKey edgeKey;

	@Expose public int rootId;
	@Expose public int leafId;

	@Expose public double metric;
	@Expose public double rarity;

	private EEdge equiv;

	public Edge(Feature root, Feature leaf) {
		this.root = root;
		this.leaf = leaf;

		rootId = root.getId();
		leafId = leaf.getId();
		edgeKey = EdgeKey.create(root, leaf);

		// euclidean distance
		metric = Math.sqrt(Math.pow(root.getX() - leaf.getX(), 2) + Math.pow(root.getY() - leaf.getY(), 2));
		rarity = 1;
	}

	public EdgeKey getEdgeKey() {
		return edgeKey;
	}

	public double similarity(Edge o) {
		double sim = 0;
		Map<Factor, Double> boosts = Tool.mgr.getFactors();
		sim += boosts.get(Factor.RARITY) * Feature.norm(rarity, o.rarity);
		sim += boosts.get(Factor.METRIC) * Feature.norm(metric, o.metric);
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

	public EEdge equiv() {
		if (equiv == null) {
			equiv = new EEdge(this);
		}
		return equiv;
	}

	@Override
	public int compareTo(Edge o) {
		if (rootId != o.rootId) Log.error(this, "Wrong orientation for " + toString());
		if (leaf.getType() < o.leaf.getType()) return -1;
		if (leaf.getType() > o.leaf.getType()) return 1;
		return leaf.getText().compareTo(o.leaf.getText());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Edge o = (Edge) obj;
		if (edgeKey == null) return false;
		if (!edgeKey.equals(o.edgeKey)) return false;
		if (rootId != o.rootId) return false;
		return true;
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
	public String toString() {
		return String.format("{%s -> %s}", root.getAspect(), leaf.getAspect());
	}
}
