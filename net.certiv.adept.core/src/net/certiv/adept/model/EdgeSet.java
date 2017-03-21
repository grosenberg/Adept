package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Align;
import net.certiv.adept.topo.Factor;

/**
 * The collection of edges, typed by leaf, that defines the connections between a root feature and
 * its leaf features in the local group. The EdgeSet can contain multiple edges to same leaf type
 * features.
 */
public class EdgeSet {

	// key=leaf type; value=equivalent edges
	@Expose private ArrayListMultimap<Long, Edge> edgeSet;
	// edges, specified by leaf feature type, ordered by leaf feature line, col, type
	@Expose private List<Long> edgeOrder;

	private boolean mark;

	// cache for calculated values between this and other edge sets
	private final ArrayListMultimap<EdgeSet, Double> cache = ArrayListMultimap.create();

	private static final Comparator<Edge> MetricComp = new Comparator<Edge>() {

		@Override
		public int compare(Edge o1, Edge o2) {
			if (o1.metric < o2.metric) return -1;
			if (o1.metric > o2.metric) return 1;
			return 0;
		}
	};

	public EdgeSet() {
		edgeSet = ArrayListMultimap.create();
		edgeOrder = new ArrayList<>();
	}

	/**
	 * Adds an edge to this edge set. The included edges are defined by the local group. The edges
	 * will be presented ordered by leaf feature line, col, and type.
	 */
	public boolean addEdge(Edge edge) {
		long type = edge.leaf.getType();
		edgeSet.put(type, edge);
		if (!mark && edge.leaf.isAfter(edge.root)) {
			edgeOrder.add((long) -2); // marks the root location in the edge set
			mark = true;
		}
		edgeOrder.add(type);
		return true;
	}

	public ArrayListMultimap<Long, Edge> getEdgeSet() {
		return edgeSet;
	}

	public Collection<Edge> getEdges() {
		return edgeSet.values();
	}

	/**
	 * Returns the edges that are of the given leaf type. The edges are pre-ordered by leaf feature
	 * line & col.
	 */
	public List<Edge> getEdges(long leafType) {
		List<Edge> edges = edgeSet.get(leafType);
		return edges;
	}

	public Set<Long> getEdgeTypes() {
		return edgeSet.keySet();
	}

	/** Returns the total number of unique leaf types. */
	public int getTypeCount() {
		return edgeSet.keySet().size();
	}

	/** Returns the total number of edges in this EdgeSet. */
	public int getEdgeCount() {
		return edgeSet.size();
	}

	/**
	 * Returns the effective degree of similary, in the range [0-1], between this (source) edge set
	 * and the given (corpus) edge set.
	 * <p>
	 * Similarity is defined as a function of (1) the intersection and disjunction rates of
	 * equivalent edges; and (2) the relative extent of the optimal alignment of like-typed edges.
	 * <p>
	 * Intersection similarity is a value based on the count of equivalent edges -- edges having the
	 * same leaf type and, except for VAR leafs, the same text -- common to both sets (intersect)
	 * less a discounted value based on the count of non-equivalent edges (disjoint). To fairly
	 * balance in terms of significance, a single intersect edge is weighted equivalent to two
	 * disjoint edges.
	 * <p>
	 * The intersect similarity value for two edge sets must be the maximum similarity that can be
	 * returned for those two sets. Likewise, the identity ordering of two edge sets must represent
	 * the maximum similarity that can be returned for any two sets.
	 */
	public double similarity(EdgeSet cps) {
		double disjointCost = Tool.mgr.getFactors().get(Factor.DISCOUNT);
		double alignWeight = Tool.mgr.getFactors().get(Factor.ORDER);
		double intersectWeight = 1.0 - alignWeight;

		// max intersection similarity basis with reduced penalty for expected disjoints
		double basis = Math.min(getEdgeCount(), cps.getEdgeCount());
		basis += Math.abs(getEdgeCount() - cps.getEdgeCount()) * disjointCost;

		double intersection = intersect(cps) / basis;
		return alignment(cps) * alignWeight + intersection * intersectWeight;
	}

	/** Returns the precise intersection similarity between this and the given edge set. */
	public double intersect(EdgeSet cps) {
		List<Double> hit = cache.get(cps);
		if (hit.isEmpty()) {
			hit = compute(cps);
		}
		return hit.get(0);
	}

	public int intersectCount(EdgeSet cps) {
		List<Double> hit = cache.get(cps);
		if (hit.isEmpty()) {
			hit = compute(cps);
		}
		return hit.get(1).intValue();
	}

	// similarity between two sets of edges
	public double alignment(EdgeSet cps) {
		List<Double> hit = cache.get(cps);
		if (hit.isEmpty()) {
			hit = compute(cps);
		}
		return hit.get(2);
	}

	private List<Double> compute(EdgeSet cps) {
		double sim = 0;
		double cnt = 0;

		// intersect: leaf types that exist in both edge sets
		for (Long key : intersectKeys(cps)) {
			List<Edge> srcEdges = new ArrayList<>(getEdges(key));
			List<Edge> cpsEdges = new ArrayList<>(cps.getEdges(key));
			Collections.sort(srcEdges, MetricComp);
			Collections.sort(cpsEdges, MetricComp);

			int base = Math.min(srcEdges.size(), cpsEdges.size());
			for (int idx = 0; idx < base; idx++) {
				sim += srcEdges.get(idx).similarity(cpsEdges.get(idx));
			}
			cnt += base;
		}

		// determine edge alignment similarity
		double alignSim = Align.similarity(edgeOrder, cps.edgeOrder);

		cache.put(cps, sim);		// intersection similarity
		cache.put(cps, cnt);		// count of intersected edges
		cache.put(cps, alignSim);	// alignment similarity
		return cache.get(cps);
	}

	public Set<Long> intersectKeys(EdgeSet cps) {
		Set<Long> ikeys = new HashSet<>(edgeSet.keySet());
		ikeys.retainAll(cps.edgeSet.keySet());
		return ikeys;
	}

	public Set<Long> disjointKeys(EdgeSet cps) {
		Set<Long> ekeys = new HashSet<>(edgeSet.keySet());
		ekeys.removeAll(cps.edgeSet.keySet());

		Set<Long> okeys = new HashSet<>(cps.edgeSet.keySet());
		okeys.removeAll(edgeSet.keySet());

		ekeys.addAll(okeys);
		return ekeys;
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getTypeCount(), getEdgeCount());
	}
}
