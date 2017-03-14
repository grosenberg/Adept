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

	/**
	 * Returns the total similarity of this source edge set and the given edge set from the corpus.
	 */
	public double similarity(EdgeSet corp) {
		double ins = intersect(corp);
		double dsj = disjoint(corp);
		// Log.debug(this, " - Intersection Sim: " + String.valueOf(sim));
		// Log.debug(this, " - Disjoint Sim : " + String.valueOf(dis));
		double factor = Tool.mgr.getFactors().get(Factor.DISCOUNT);
		double joint = ins - (factor * dsj);

		double ofactor = Tool.mgr.getFactors().get(Factor.ORDER);
		double ord = orderDistance(corp);
		return ofactor * ord + (1.0 - ofactor) * joint;
	}

	/** Returns the precise intersection similarity between this and the given edge set. */
	public double intersect(EdgeSet corp) {
		List<Double> hit = cache.get(corp);
		if (hit.isEmpty()) {
			hit = compute(corp);
		}
		return hit.get(0);
	}

	/** Returns the estimated dissimilarity for the disjoint set of this and the given edge set. */
	public double disjoint(EdgeSet corp) {
		List<Double> hit = cache.get(corp);
		if (hit.isEmpty()) {
			hit = compute(corp);
		}
		return hit.get(1);
	}

	public int intersectCount(EdgeSet corp) {
		List<Double> hit = cache.get(corp);
		if (hit.isEmpty()) {
			hit = compute(corp);
		}
		return hit.get(2).intValue();
	}

	public int disjointCount(EdgeSet corp) {
		List<Double> hit = cache.get(corp);
		if (hit.isEmpty()) {
			hit = compute(corp);
		}
		return hit.get(3).intValue();
	}

	// similarity based on edit distance between two sets of edges
	public double orderDistance(EdgeSet corp) {
		List<Double> hit = cache.get(corp);
		if (hit.isEmpty()) {
			hit = compute(corp);
		}
		return hit.get(4);
	}

	private List<Double> compute(EdgeSet corp) {
		double intersectSim = 0;
		int totIntersectEdges = 0;
		int totDisjointEdges = 0;
		double orderSim = 0;

		// intersect: leaf types that exist in both edge sets
		for (Long key : intersectKeys(corp)) {
			List<Edge> srcEdges = getEdges(key);
			List<Edge> corpEdges = corp.getEdges(key);

			/*
			 * Max product of similarity: for edges of the same type, similarity is based on a best
			 * match of the edges. Approximated by selecting edges as matched based on highest
			 * similarity. Referenced to the source set of edges.
			 */
			int srcCnt = srcEdges.size();
			int corpCnt = corpEdges.size();

			for (Edge srcEdge : srcEdges) {
				double sim = 0;
				for (Edge corpEdge : corpEdges) {
					sim = Math.max(sim, srcEdge.similarity(corpEdge));
				}
				intersectSim += sim / srcCnt;
			}
			totIntersectEdges += Math.min(srcCnt, corpCnt);
		}

		// disjoint: leaf types that exist in only one edge set
		for (Long key : disjointKeys(corp)) {
			totDisjointEdges += getEdges(key).size() + corp.getEdges(key).size();
		}

		// estimate disjoint edge similarity
		double perEdge = totIntersectEdges > 0 ? intersectSim / totIntersectEdges : 0;
		double disjointSim = perEdge * totDisjointEdges;

		// determine edge alignment similarity
		int hlen = Math.max(headEdgeOrder.size(), corp.headEdgeOrder.size());
		int tlen = Math.max(tailEdgeOrder.size(), corp.tailEdgeOrder.size());
		int clen = Math.max(hlen + tlen, 1);

		orderSim = EdgeSeq.similarity(headEdgeOrder, corp.headEdgeOrder) * hlen / clen;
		orderSim += EdgeSeq.similarity(tailEdgeOrder, corp.tailEdgeOrder) * tlen / clen;

		cache.put(corp, intersectSim);
		cache.put(corp, disjointSim);
		cache.put(corp, (double) totIntersectEdges);
		cache.put(corp, (double) totDisjointEdges);
		cache.put(corp, orderSim);
		return cache.get(corp);
	}

	public Set<Long> intersectKeys(EdgeSet corp) {
		Set<Long> ikeys = new HashSet<>(edgeSet.keySet());
		ikeys.retainAll(corp.edgeSet.keySet());
		return ikeys;
	}

	public Set<Long> disjointKeys(EdgeSet corp) {
		Set<Long> ekeys = new HashSet<>(edgeSet.keySet());
		ekeys.removeAll(corp.edgeSet.keySet());

		Set<Long> okeys = new HashSet<>(corp.edgeSet.keySet());
		okeys.removeAll(edgeSet.keySet());

		ekeys.addAll(okeys);
		return ekeys;
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getTypeCount(), getEdgeCount());
	}
}
