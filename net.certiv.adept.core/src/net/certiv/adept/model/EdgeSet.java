package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.primitives.Ints;
import com.google.gson.annotations.Expose;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.util.DamerauAlignment;

/**
 * The unique set of edges connecting a set of leaf features to an owning root feature. The edge set
 * is ordered by leaf coordinate relative to the root. Multiple leaf features, differing by feature
 * type and possibly text, may occur at the same coordinate position.
 */
public class EdgeSet {

	//	public static final String RootText = "@$#$%root%$#@";
	public static final int RootMarkType = -3;

	// edges sorted by coord, mapped to type
	@Expose private TreeMap<Edge, Integer> edgeSet;

	@SuppressWarnings("unused") private ProcessMgr mgr;

	public EdgeSet() {
		edgeSet = new TreeMap<>();
		edgeSet.put(Edge.RootMark, RootMarkType); 		// supply a root coord
	}

	/** Adds an edge, pre-constrained to the local group, to this edge set. */
	public boolean addEdge(Edge edge) {
		edgeSet.put(edge, edge.leaf.getType());
		return true;
	}

	public Set<Edge> getEdges() {
		return edgeSet.keySet();
	}

	/** Returns the edges that are of the given leaf type. The edges are ordered by leaf coord. */
	public List<Edge> getEdges(long leafType) {
		List<Edge> edges = new ArrayList<>();
		for (Entry<Edge, Integer> entry : edgeSet.entrySet()) {
			if (entry.getValue() == leafType) {
				edges.add(entry.getKey());
			}
		}
		return edges;
	}

	/** Returns the sequence of leaf types ordered by leaf coord. */
	public List<Integer> getEdgeSequence() {
		return new ArrayList<>(edgeSet.values());
	}

	/** Returns the unique set of edge types in this edge set. */
	public Set<Integer> getEdgeTypes() {
		LinkedHashSet<Integer> types = new LinkedHashSet<>(getEdgeSequence());
		types.remove(RootMarkType);
		return types;
	}

	/**
	 * Returns the sequence of the encoded text of the edge connected leafs, excluding rule, var, and
	 * comment leafs.
	 */
	public List<Integer> getEdgeText() {
		List<Integer> result = new ArrayList<>();
		for (Edge edge : getEdges()) {
			if (edge == Edge.RootMark) {
				result.add(RootMarkType);
			} else if (edge.leaf.isTerminal() && !edge.leaf.isVar() && !edge.leaf.isComment()) {
				result.add(edge.leaf.getText().hashCode());
			}
		}
		return result;
	}

	/** Returns the total number of edges in this EdgeSet. */
	public int size() {
		return edgeSet.size();
	}

	/** Returns the number of unique edges in this EdgeSet. */
	public int sizeTypes() {
		return getEdgeTypes().size();
	}

	public boolean isEmpty() {
		return edgeSet.size() < 2;
	}

	public void setMgr(ProcessMgr mgr) {
		this.mgr = mgr;
	}

	/**
	 * Returns the effective degree of similary, in the range [0-1], between this (document) edge set
	 * and the given (corpus) edge set.
	 * 
	 * Similarity is computed as the normed edit distance between the ordered type-sets of the two edge
	 * sets.
	 */
	public double typeSimilarity(EdgeSet o) {
		List<Integer> tseq = getEdgeSequence();
		List<Integer> oseq = o.getEdgeSequence();
		int[][] tsqx = split(tseq);
		int[][] osqx = split(oseq);
		double dist1 = DamerauAlignment.distance(tsqx[0], osqx[0]);
		double dist2 = DamerauAlignment.distance(tsqx[1], osqx[1]);

		double sim = DamerauAlignment.simularity(dist1 + dist2, tseq.size(), oseq.size());
		return sim;		//* mgr.getBoost(Factor.EDGES);
	}

	/**
	 * Returns the effective degree of similary, in the range [0-1], between selected edge connected
	 * leaf texts and those of the given edge set.
	 * 
	 * Similarity is computed as the normed edit distance between the ordered encoded texts of the two
	 * edge sets.
	 */
	public double textSimilarity(EdgeSet o) {
		List<Integer> ttxt = getEdgeText();
		List<Integer> otxt = o.getEdgeText();
		int[][] ttx = split(ttxt);
		int[][] otx = split(otxt);
		double dist1 = DamerauAlignment.distance(ttx[0], otx[0]);
		double dist2 = DamerauAlignment.distance(ttx[1], otx[1]);

		double sim = DamerauAlignment.simularity(dist1 + dist2, ttxt.size(), otxt.size());
		return sim;		//* mgr.getBoost(Factor.TEXT);
	}

	// split an ordered type-set at the position of the root marker 
	private int[][] split(List<Integer> seq) {
		int[][] result = new int[2][];
		int dot = seq.indexOf(RootMarkType);
		if (dot > -1) {
			result[0] = Ints.toArray(seq.subList(0, dot));
			result[1] = Ints.toArray(seq.subList(dot + 1, seq.size()));
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edgeSet == null) ? 0 : edgeSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		EdgeSet other = (EdgeSet) o;
		return getEdgeSequence().equals(other.getEdgeSequence());
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getEdgeTypes().size(), getEdges().size());
	}

	//
	//	/**
	//	 * Returns the effective degree of similary, in the range [0-1], between this (source) edge set and
	//	 * the given (corpus) edge set.
	//	 * <p>
	//	 * Similarity is defined as a function of (1) the intersection and disjunction rates of like-typed
	//	 * edges; and (2) the relative extent of the optimal alignment of like-typed edges.
	//	 * <p>
	//	 * Intersection similarity is a value based on the count of equivalent edges -- edges having the
	//	 * same leaf type common to both sets (intersect) less a discounted value based on the count of
	//	 * non-equivalent edges (disjoint). To fairly balance in terms of significance, a single intersect
	//	 * edge is weighted equivalent to two disjoint edges.
	//	 * <p>
	//	 * The intersect similarity value for two edge sets must be the maximum similarity that can be
	//	 * returned for those two sets. Likewise, the identity ordering of two edge sets must represent the
	//	 * maximum similarity that can be returned for any two sets.
	//	 */
	//	public double similarityX(EdgeSet cps) {
	//		double intersect = getMgr().getBoost(Factor.INTERSECT) * intersect(cps);
	//		double alignment = getMgr().getBoost(Factor.ORDER) * alignment(cps);
	//		double ortho = getMgr().getBoost(Factor.EDGES) * orthoSim(cps);
	//
	//		return alignment + intersect + ortho;
	//	}
	//
	//	/** Returns a value representing the similarity of two edges connected to the same root. */
	//	public double similarity(Edge o) {
	//		if (this == o) return 1;
	//		if (rootId != o.rootId || leafId != o.leafId) return 0;
	//		if (!leaf.getText().equals(o.leaf.getText())) return 0;
	//
	//		double[] vals = new double[3];
	//		ProcessMgr mgr = root.getMgr();
	//		vals[0] = mgr.getBoost(Factor.METRIC) * Maths.invDelta(offset, o.offset);
	//		vals[1] = mgr.getBoost(Factor.ORTHO) * Maths.invDelta(vert, o.vert);
	//		vals[2] = mgr.getBoost(Factor.EDGE_TEXTS) * (leaf.getText().equals(o.leaf.getText()) ? 1 : 0);
	//		return Maths.sum(vals) / vals.length;
	//	}
	//
	//	/** Returns the precise intersection similarity between this and the given edge set. */
	//	public double intersect(EdgeSet cps) {
	//		List<Double> hit = cache.get(cps);
	//		if (hit.isEmpty()) {
	//			hit = compute(cps);
	//		}
	//		return hit.get(0);
	//	}
	//
	//	public int formatSim(EdgeSet cps) {
	//		List<Double> hit = cache.get(cps);
	//		if (hit.isEmpty()) {
	//			hit = compute(cps);
	//		}
	//		return hit.get(1).intValue();
	//	}
	//
	//	public int excessCount(EdgeSet cps) {
	//		List<Double> hit = cache.get(cps);
	//		if (hit.isEmpty()) {
	//			hit = compute(cps);
	//		}
	//		return hit.get(2).intValue();
	//	}
	//
	//	// alignment similarity between two sets of edges
	//	public double alignment(EdgeSet cps) {
	//		List<Double> hit = cache.get(cps);
	//		if (hit.isEmpty()) {
	//			hit = compute(cps);
	//		}
	//		return hit.get(3);
	//	}
	//
	//	// alignment similarity between two sets of edges
	//	public double orthoSim(EdgeSet cps) {
	//		List<Double> hit = cache.get(cps);
	//		if (hit.isEmpty()) {
	//			hit = compute(cps);
	//		}
	//		return hit.get(4);
	//	}
	//
	//	public Set<Long> intersectKeys(EdgeSet cps) {
	//		Set<Long> ikeys = new HashSet<>(edgeSet.keySet());
	//		ikeys.retainAll(cps.edgeSet.keySet());
	//		return ikeys;
	//	}
	//
	//	public Set<Long> disjointKeys(EdgeSet cps) {
	//		Set<Long> ekeys = new HashSet<>(edgeSet.keySet());
	//		ekeys.removeAll(cps.edgeSet.keySet());
	//
	//		Set<Long> okeys = new HashSet<>(cps.edgeSet.keySet());
	//		okeys.removeAll(edgeSet.keySet());
	//
	//		ekeys.addAll(okeys);
	//		return ekeys;
	//	}
	//
	//	private List<Double> compute(EdgeSet cps) {
	//		double sim = 0;
	//		double cnt = 0;
	//		double xcs = 0;
	//		double smo = 0;
	//
	//		// intersect: leaf types that exist in both edge sets
	//		Set<Long> keys = intersectKeys(cps);
	//		for (Long key : keys) {
	//			List<Edge> srcEdges = new ArrayList<>(getEdges(key));
	//			List<Edge> cpsEdges = new ArrayList<>(cps.getEdges(key));
	//			Collections.sort(srcEdges, MetricComp);
	//			Collections.sort(cpsEdges, MetricComp);
	//
	//			int max = Math.max(srcEdges.size(), cpsEdges.size());
	//			int min = Math.min(srcEdges.size(), cpsEdges.size());
	//			for (int idx = 0; idx < min; idx++) {
	//				sim += srcEdges.get(idx).similarity(cpsEdges.get(idx));
	//			}
	//			cnt += min;
	//			xcs += max - min;
	//
	//			double srcOrtho = orthoDistance(srcEdges);
	//			double cpsOrtho = orthoDistance(cpsEdges);
	//			smo += Maths.invDelta(srcOrtho, cpsOrtho);
	//		}
	//
	//		// max intersection similarity basis with reduced penalty for expected disjoints
	//		double basis = Math.min(getEdgeCount(), cps.getEdgeCount());
	//		basis += Math.abs(getEdgeCount() - cps.getEdgeCount()) * getMgr().getBoost(Factor.DISCOUNT);
	//		sim = sim / basis;
	//
	//		// determine edge alignment similarity
	//		double alignSim = Align.similarity(edgeOrder, cps.edgeOrder);
	//		// determine similarity of ortho distances related by type
	//		double orthoSim = smo / keys.size();
	//
	//		cache.put(cps, sim);		// intersection similarity
	//		cache.put(cps, cnt);		// count of matched edges (for Stats)
	//		cache.put(cps, xcs);		// count of excess edges (for Stats)
	//		cache.put(cps, alignSim);	// alignment similarity
	//		cache.put(cps, orthoSim);	// ortho similarity
	//		return cache.get(cps);
	//	}
	//
	//	private double orthoDistance(List<Edge> edges) {
	//		double ortho = 0;
	//		for (Edge edge : edges) {
	//			ortho += edge.vert;
	//		}
	//		return ortho / edges.size();
	//	}
}
