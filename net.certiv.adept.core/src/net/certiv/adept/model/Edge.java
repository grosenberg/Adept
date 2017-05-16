package net.certiv.adept.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Norm;

public class Edge implements Comparable<Edge> {

	private static final Table<Long, Long, Edge> cache = HashBasedTable.create();

	@Expose public long leafId;
	@Expose public long rootId;
	@Expose public EKind kind;

	@Expose public int metric;
	@Expose public int ortho;

	public Feature root; // edge root
	public Feature leaf; // connected leaf

	private EqEdge equiv;

	public static Edge create(Feature root, Feature leaf, EKind kind) {
		Edge edge = cache.get(leaf.getId(), root.getId());
		if (edge == null) {
			edge = new Edge(root, leaf, kind);
			cache.put(leaf.getId(), root.getId(), edge);
		}
		return edge;
	}

	private Edge(Feature root, Feature leaf, EKind kind) {
		this.root = root;
		this.leaf = leaf;
		this.kind = kind;

		rootId = root.getId();
		leafId = leaf.getId();

		// stream offset distance
		metric = root.offsetDistance(leaf);
		// vertical offset distance
		ortho = root.getLine() - leaf.getLine();
	}

	/** Returns a value representing the similarity of two edges of the same type. */
	public double similarity(Edge o) {
		double[] vals = new double[3];
		CoreMgr mgr = root.getMgr();
		vals[0] = mgr.getBoost(Factor.METRIC) * Norm.invDelta(metric, o.metric);
		vals[1] = mgr.getBoost(Factor.ORTHO) * Norm.invDelta(ortho, o.ortho);
		vals[2] = mgr.getBoost(Factor.TEXT) * (leaf.getText().equals(o.leaf.getText()) ? 1 : 0);
		return Norm.sum(vals) / vals.length;
	}

	public EqEdge equiv() {
		if (equiv == null) {
			equiv = new EqEdge(this);
		}
		return equiv;
	}

	@Override
	public int compareTo(Edge o) {
		if (rootId != o.rootId) Log.error(this, "Wrong orientation for " + toString());
		if (leaf.getType() < o.leaf.getType()) return -1;
		if (leaf.getType() > o.leaf.getType()) return 1;
		if (leaf.isVar()) return 0;
		return leaf.getText().compareTo(o.leaf.getText());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Edge o = (Edge) obj;
		if (leafId != o.leafId) return false;
		if (rootId != o.rootId) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) leafId;
		result = prime * result + (int) (leafId >>> 32);
		result = prime * result + (int) rootId;
		result = prime * result + (int) (rootId >>> 32);
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s {%s -> %s}", metric, root.getAspect(), leaf.getAspect());
	}
}
