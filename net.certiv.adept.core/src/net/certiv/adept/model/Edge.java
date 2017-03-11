package net.certiv.adept.model;

import java.util.Map;

import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Factor;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Norm;

public class Edge implements Comparable<Edge> {

	private static final Table<Long, Long, Edge> cache = HashBasedTable.create();

	@Expose public long leafId;
	@Expose public long rootId;

	@Expose public double metric;

	public Feature root; // edge root
	public Feature leaf; // connected leaf

	private EEdge equiv;

	public static Edge create(Feature root, Feature leaf) {
		Edge edge = cache.get(leaf.getId(), root.getId());
		if (edge == null) {
			edge = new Edge(root, leaf);
			cache.put(leaf.getId(), root.getId(), edge);
		}
		return edge;
	}

	private Edge(Feature root, Feature leaf) {
		this.root = root;
		this.leaf = leaf;

		rootId = root.getId();
		leafId = leaf.getId();

		// euclidean distance
		double[] p1 = new double[] { leaf.getCol(), leaf.getLine() };
		double[] p2 = new double[] { root.getCol(), root.getLine() };
		metric = FastMath.abs(MathArrays.distance(p1, p2));
	}

	public double similarity(Edge o) {
		double[] vals = new double[2];
		Map<Factor, Double> boosts = Tool.mgr.getFactors();
		vals[0] = boosts.get(Factor.METRIC) * Norm.delta(metric, o.metric);
		vals[1] = boosts.get(Factor.TEXT) * (leaf.getText().equals(o.leaf.getText()) ? 1 : 0);
		return Norm.sum(vals);
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
