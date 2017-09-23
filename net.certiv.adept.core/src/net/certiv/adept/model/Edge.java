package net.certiv.adept.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.annotations.Expose;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.model.topo.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Maths;

public class Edge implements Comparable<Edge> {

	private static final Table<Long, Long, Edge> cache = HashBasedTable.create();

	@Expose public long leafId;
	@Expose public long rootId;
	@Expose public EdgeType kind;

	@Expose public int metric;
	@Expose public int ortho;

	public Feature root; // edge root
	public Feature leaf; // connected leaf

	private EqEdge equiv;

	public static Edge create(Feature root, Feature leaf, EdgeType kind) {
		Edge edge = cache.get(leaf.getId(), root.getId());
		if (edge == null) {
			edge = new Edge(root, leaf, kind);
			cache.put(leaf.getId(), root.getId(), edge);
		}
		return edge;
	}

	private Edge(Feature root, Feature leaf, EdgeType kind) {
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
		vals[0] = mgr.getBoost(Factor.METRIC) * Maths.invDelta(metric, o.metric);
		vals[1] = mgr.getBoost(Factor.ORTHO) * Maths.invDelta(ortho, o.ortho);
		vals[2] = mgr.getBoost(Factor.TEXT) * (leaf.getText().equals(o.leaf.getText()) ? 1 : 0);
		return Maths.sum(vals) / vals.length;
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

	/** Wrapper class to provide an 'equivalent' equals function */
	public static class EqEdge implements Comparable<EqEdge> {

		private Edge edge;

		private long leafType;
		private long rootType;
		private String leafText;
		private String rootText;

		public EqEdge(Edge edge) {
			this.edge = edge;

			leafType = edge.leaf.getType();
			rootType = edge.root.getType();
			leafText = getText(edge.leaf);
			rootText = getText(edge.root);
		}

		private String getText(Feature feature) {
			return feature.isVar() || feature.isRule() ? "" : feature.getText();
		}

		@Override
		public int compareTo(EqEdge o) {
			if (leafType < o.leafType) return -1;
			if (leafType > o.leafType) return 1;
			return leafText.compareTo(o.leafText);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			EqEdge o = (EqEdge) obj;
			if (edge == null || o.edge == null) return false;
			if (leafType != o.leafType) return false;
			if (rootType != o.rootType) return false;
			if (!leafText.equals(o.leafText)) return false;
			if (!rootText.equals(o.rootText)) return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + leafText.hashCode();
			result = prime * result + (int) (leafType >>> 32);
			result = prime * result + (int) (leafType);
			result = prime * result + rootText.hashCode();
			result = prime * result + (int) (rootType >>> 32);
			result = prime * result + (int) (rootType);
			return result;
		}
	}

}
