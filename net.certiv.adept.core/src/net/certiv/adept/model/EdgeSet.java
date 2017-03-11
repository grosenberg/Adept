package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Factor;

/**
 * The collection of edges, typed by leaf, that defines the connections between a root feature and
 * its leaf features in the local group. The EdgeSet can contain multiple edges to same leaf type
 * features.
 */
public class EdgeSet {

	// private static final Comparator<Edge> equivComp = new Comparator<Edge>() {
	//
	// @Override
	// public int compare(Edge e1, Edge e2) {
	// return e1.equiv().compareTo(e2.equiv());
	// }
	// };

	// total count of edges
	@Expose private int edgeCnt;
	// key=leaf type; value=equivalent edges
	@Expose private ArrayListMultimap<Long, Edge> edgeSet;
	// edges, specified by type, ordered by line, col
	@Expose private List<Long> headEdgeOrder;
	@Expose private List<Long> tailEdgeOrder;

	// cache for calculated values between this and other edge sets
	private final ArrayListMultimap<EdgeSet, Double> cache = ArrayListMultimap.create();

	public EdgeSet() {
		edgeSet = ArrayListMultimap.create();
		headEdgeOrder = new ArrayList<>();
		tailEdgeOrder = new ArrayList<>();
	}

	public boolean addEdge(Edge edge) {
		edgeSet.put(edge.leaf.getType(), edge);
		if (edge.leaf.isBefore(edge.root)) {
			headEdgeOrder.add(edge.leaf.getType());
		} else {
			tailEdgeOrder.add(edge.leaf.getType());
		}
		edgeCnt++;
		return true;
	}

	public ArrayListMultimap<Long, Edge> getEdgeSet() {
		return edgeSet;
	}

	public Collection<Edge> getEdges() {
		return edgeSet.values();
	}

	/** Returns the edges that are of the given leaf type. */
	public List<Edge> getEdges(long leafType) {
		List<Edge> edges = edgeSet.get(leafType);
		return edges;
	}

	public Set<Long> getEdgeTypes() {
		return edgeSet.keySet();
	}

	/** Returns the total number of unique leaf types. */
	public int getTypeCount() {
		return edgeSet.size();
	}

	/** Returns the total number of edges in this EdgeSet. */
	public int getEdgeCount() {
		return edgeCnt;
	}

	/** Returns the total similarity of this and the given edge set. */
	public double similarity(EdgeSet o) {
		double factor = Tool.mgr.getFactors().get(Factor.DISCOUNT);
		double ins = intersect(o);
		double dsj = disjoint(o);
		// Log.debug(this, " - Intersection Sim: " + String.valueOf(sim));
		// Log.debug(this, " - Disjoint Sim : " + String.valueOf(dis));
		double joint = ins - (factor * dsj);

		double ofactor = Tool.mgr.getFactors().get(Factor.ORDER);
		double ord = editDistance(o);
		return ofactor * ord + (1.0 - ofactor) * joint;
	}

	/** Returns the precise intersection similarity between this and the given edge set. */
	public double intersect(EdgeSet o) {
		List<Double> hit = cache.get(o);
		if (hit.isEmpty()) {
			hit = compute(o);
		}
		return hit.get(0);
	}

	/** Returns the estimated dissimilarity for the disjoint set of this and the given edge set. */
	public double disjoint(EdgeSet o) {
		List<Double> hit = cache.get(o);
		if (hit.isEmpty()) {
			hit = compute(o);
		}
		return hit.get(1);
	}

	public int intersectCount(EdgeSet o) {
		List<Double> hit = cache.get(o);
		if (hit.isEmpty()) {
			hit = compute(o);
		}
		return hit.get(2).intValue();
	}

	public int disjointCount(EdgeSet o) {
		List<Double> hit = cache.get(o);
		if (hit.isEmpty()) {
			hit = compute(o);
		}
		return hit.get(3).intValue();
	}

	// similarity based on edit distance between two sets of edges
	public double editDistance(EdgeSet o) {
		List<Double> hit = cache.get(o);
		if (hit.isEmpty()) {
			hit = compute(o);
		}
		return hit.get(4);
	}

	private List<Double> compute(EdgeSet o) {
		double intersectSim = 0;
		int totIntersectEdges = 0;
		int totDisjointEdges = 0;
		double orderSim = 0;

		// intersect: leaf types that exist in both edge sets
		for (Long key : intersectKeys(o)) {
			List<Edge> eedges = getEdges(key);
			List<Edge> oedges = o.getEdges(key);

			int max = Math.max(eedges.size(), oedges.size());
			int min = Math.min(eedges.size(), oedges.size());
			totIntersectEdges += min;
			totDisjointEdges += max - min;

			// max product of similarity
			for (Edge edge : eedges) {
				double sim = 0;
				for (Edge odge : oedges) {
					sim = Math.max(sim, edge.similarity(odge));
				}
				intersectSim += sim;
			}
		}

		for (Long key : disjointKeys(o)) {
			List<Edge> eedges = getEdges(key);
			if (!eedges.isEmpty()) totDisjointEdges += eedges.size();
			List<Edge> oedges = o.getEdges(key);
			if (!oedges.isEmpty()) totDisjointEdges += oedges.size();
		}

		// estimate disjoint edge similarity
		double perIntersectEdge = totIntersectEdges != 0 ? intersectSim / totIntersectEdges : 0;
		double disjointSim = perIntersectEdge * totDisjointEdges;

		// determine edge alignment similarity
		int hlen = Math.max(headEdgeOrder.size(), o.headEdgeOrder.size());
		int tlen = Math.max(tailEdgeOrder.size(), o.tailEdgeOrder.size());
		int clen = Math.max(hlen + tlen, 1);

		orderSim = EdgeSeq.similarity(headEdgeOrder, o.headEdgeOrder) * hlen / clen;
		orderSim += EdgeSeq.similarity(tailEdgeOrder, o.tailEdgeOrder) * tlen / clen;

		cache.put(o, intersectSim);
		cache.put(o, disjointSim);
		cache.put(o, (double) totIntersectEdges);
		cache.put(o, (double) totDisjointEdges);
		cache.put(o, orderSim);
		return cache.get(o);
	}

	public Set<Long> intersectKeys(EdgeSet other) {
		Set<Long> ikeys = new HashSet<>(edgeSet.keySet());
		ikeys.retainAll(other.edgeSet.keySet());
		return ikeys;
	}

	public Set<Long> disjointKeys(EdgeSet other) {
		Set<Long> ekeys = new HashSet<>(edgeSet.keySet());
		ekeys.removeAll(other.edgeSet.keySet());

		Set<Long> okeys = new HashSet<>(other.edgeSet.keySet());
		okeys.removeAll(edgeSet.keySet());

		ekeys.addAll(okeys);
		return ekeys;
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getTypeCount(), getEdgeCount());
	}
}
