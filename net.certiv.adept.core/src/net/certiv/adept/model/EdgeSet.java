package net.certiv.adept.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.annotations.Expose;

import net.certiv.adept.Tool;
import net.certiv.adept.topo.Factor;

/**
 * The collection of edges, typed by EdgeKey, that defines the connections between a root feature
 * and leaf features in the local group. The EdgeSet can contain multiple edges to equivalent leaf
 * features.
 */
public class EdgeSet {

	private static final Comparator<Edge> edgeComp = new Comparator<Edge>() {

		@Override
		public int compare(Edge e1, Edge e2) {
			return e1.equiv().compareTo(e2.equiv());
		}
	};

	// key=edge key; value=edges
	@Expose private ArrayListMultimap<EdgeKey, Edge> edgeSet;
	// total count of edges
	@Expose private int edgeCnt;

	// cache for calculated values between this and other edge sets
	private final ArrayListMultimap<EdgeSet, Double> cache = ArrayListMultimap.create();

	public EdgeSet() {
		edgeSet = ArrayListMultimap.create();
	}

	public boolean addEdge(Edge edge) {
		EdgeKey key = edge.edgeKey;
		List<Edge> edges = edgeSet.get(key);
		if (edges.contains(edge)) return false; // uses Edge#equals
		edges.add(edge);
		Collections.sort(edges, edgeComp);
		edgeCnt++;
		return true;
	}

	public ArrayListMultimap<EdgeKey, Edge> getEdgeSet() {
		return edgeSet;
	}

	public Collection<Edge> getEdges() {
		return edgeSet.values();
	}

	/** Returns the edges that are of the given EdgeKey type. */
	public List<Edge> getEdges(EdgeKey key) {
		List<Edge> edges = edgeSet.get(key);
		return edges;
	}

	public Set<EdgeKey> getEdgeTypes() {
		return edgeSet.keySet();
	}

	/** Returns the total number of EdgeKey types. */
	public int getTypeCount() {
		return edgeSet.size();
	}

	/** Returns the total number of edges in this EdgeSet. */
	public int getEdgeCount() {
		return edgeCnt;
	}

	/**
	 * Determines whether an edge equivalent to the given edge is present in the edgeSet. An edge is
	 * equivalent if their roots and leafs, respectively, have the same type and text. Except for
	 * variable types; then the text attribute is ingored.
	 */
	public boolean containsEquiv(Edge edge) {
		EdgeKey key = edge.edgeKey;
		List<Edge> edges = edgeSet.get(key);
		for (Edge e : edges) {
			if (e.equiv().equals(edge.equiv())) return true;
		}
		return false;
	}

	/** Returns the total similarity of this and the given edge set. */
	public double similarity(EdgeSet o) {
		double factor = Tool.mgr.getFactors().get(Factor.DISIM);
		double sim = intersect(o);
		double dis = factor * disjoint(o);
		// Log.debug(this, " - Intersection Sim: " + String.valueOf(sim));
		// Log.debug(this, " - Disjoint Sim : " + String.valueOf(dis));
		return sim - dis;
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

	private List<Double> compute(EdgeSet o) {
		double intersectSim = 0;
		int totIntersectEdges = 0;
		int totDisjointEdges = 0;

		for (EdgeKey key : intersectKeys(o)) {
			List<Edge> eedges = getEdges(key);
			List<Edge> oedges = o.getEdges(key);

			int minSize = Math.min(eedges.size(), oedges.size());
			for (int idx = 0; idx < minSize; idx++) {
				intersectSim += eedges.get(idx).similarity(oedges.get(idx));
				totIntersectEdges++;
			}
			for (int idx = minSize; idx < eedges.size(); idx++) {
				totDisjointEdges++;
			}
			for (int idx = minSize; idx < oedges.size(); idx++) {
				totDisjointEdges++;
			}
		}

		for (EdgeKey key : disjointKeys(o)) {
			List<Edge> eedges = getEdges(key);
			if (!eedges.isEmpty()) totDisjointEdges += eedges.size();
			List<Edge> oedges = o.getEdges(key);
			if (!oedges.isEmpty()) totDisjointEdges += oedges.size();
		}

		// estimate disjoint edge similarity
		double perIntersectEdge = totIntersectEdges != 0 ? intersectSim / totIntersectEdges : 0;
		double disjointSim = perIntersectEdge * totDisjointEdges;

		cache.put(o, intersectSim);
		cache.put(o, disjointSim);
		cache.put(o, (double) totIntersectEdges);
		cache.put(o, (double) totDisjointEdges);
		return cache.get(o);
	}

	public Set<EdgeKey> intersectKeys(EdgeSet other) {
		Set<EdgeKey> ikeys = new HashSet<>(edgeSet.keySet());
		ikeys.retainAll(other.edgeSet.keySet());
		return ikeys;
	}

	public Set<EdgeKey> disjointKeys(EdgeSet other) {
		Set<EdgeKey> ekeys = new HashSet<>(edgeSet.keySet());
		ekeys.removeAll(other.edgeSet.keySet());

		Set<EdgeKey> okeys = new HashSet<>(other.edgeSet.keySet());
		okeys.removeAll(edgeSet.keySet());

		ekeys.addAll(okeys);
		return ekeys;
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getTypeCount(), getEdgeCount());
	}
}
