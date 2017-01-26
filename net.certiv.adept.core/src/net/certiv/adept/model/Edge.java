package net.certiv.adept.model;

import com.google.gson.annotations.Expose;

public class Edge {

	public Feature root; // root of this edge
	public Feature leaf; // leaf connected by this edge

	@Expose public int rootId;
	@Expose public int leafId;

	@Expose public String leafAspect;

	@Expose public double metric;
	@Expose public double weight;

	public Edge(Feature root, Feature leaf) {
		this.root = root;
		this.leaf = leaf;

		rootId = root.getId();
		leafId = leaf.getId();
		leafAspect = leaf.getAspect();

		// euclidean distance
		metric = Math.sqrt(Math.pow(root.getX() - leaf.getX(), 2) + Math.pow(root.getY() - leaf.getY(), 2));
		weight = 1;
	}

	public boolean equivalentTo(Edge other) {
		if (leaf.getType() != other.leaf.getType()) return false;
		if (!leaf.getText().equals(other.leaf.getText())) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("{%s -> %s}", root.getAspect(), leafAspect);
	}
}
