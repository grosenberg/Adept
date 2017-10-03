package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.primitives.Ints;
import com.google.gson.annotations.Expose;

import net.certiv.adept.model.util.DamerauAlignment;

/**
 * The unique set of edges connecting a set of leaf features to an owning root feature. The edge set
 * is ordered by leaf coordinate relative to the root. Multiple leaf features, differing by feature
 * type and possibly text, may occur at the same coordinate position.
 */
public class EdgeSet {

	//	public static final String RootText = "@$#$%root%$#@";
	private static final Edge RootMark = new Edge();
	private static final int RootMarkType = -3;

	// edges sorted by coord, mapped to type
	@Expose private TreeMap<Edge, Integer> edgeSet;

	public EdgeSet() {
		edgeSet = new TreeMap<>();
	}

	/** Adds unique edge, pre-constrained to the local group, to this edge set. */
	public boolean addEdge(Edge edge) {
		for (Edge exist : edgeSet.keySet()) {
			if (exist.leafId == edge.leafId) return false;
		}
		edgeSet.put(edge, edge.leaf.getType());
		return true;
	}

	public void remove(Edge edge) {
		edgeSet.remove(edge);
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

	/** Returns the unique set of edge types in this edge set. */
	public Set<Integer> getEdgeTypes() {
		LinkedHashSet<Integer> types = new LinkedHashSet<>(getTypeSequence());
		types.remove(RootMarkType);
		return types;
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
		return edgeSet.isEmpty();
	}

	/**
	 * Returns the effective degree of similary, in the range [0-1], between this (document) edge set
	 * and the given (corpus) edge set.
	 * 
	 * Similarity is computed as the normed edit distance between the ordered type-sets of the two edge
	 * sets.
	 */
	public double typeSimilarity(EdgeSet o) {
		List<Integer> tseq = getTypeSequence();
		List<Integer> oseq = o.getTypeSequence();
		int[][] tsqx = split(tseq);
		int[][] osqx = split(oseq);
		double dist1 = DamerauAlignment.distance(tsqx[0], osqx[0]);
		double dist2 = DamerauAlignment.distance(tsqx[1], osqx[1]);

		double sim = DamerauAlignment.simularity(dist1 + dist2, tseq.size(), oseq.size());
		return sim;
	}

	/**
	 * Returns the effective degree of similary, in the range [0-1], between selected edge connected
	 * leaf texts and those of the given edge set.
	 * 
	 * Similarity is computed as the normed edit distance between the ordered encoded texts of the two
	 * edge sets.
	 */
	public double textSimilarity(EdgeSet o) {
		List<Integer> ttxt = getTextSequence();
		List<Integer> otxt = o.getTextSequence();
		int[][] ttx = split(ttxt);
		int[][] otx = split(otxt);
		double dist1 = DamerauAlignment.distance(ttx[0], otx[0]);
		double dist2 = DamerauAlignment.distance(ttx[1], otx[1]);

		double sim = DamerauAlignment.simularity(dist1 + dist2, ttxt.size(), otxt.size());
		return sim;
	}

	/* Returns the sequence of leaf types ordered by leaf coord. */
	private List<Integer> getTypeSequence() {
		TreeMap<Edge, Integer> tmp = new TreeMap<>(edgeSet);
		tmp.put(RootMark, RootMarkType); 		// supply a root coord
		return new ArrayList<>(tmp.values());
	}

	/* Returns the sequence of the encoded text of the edge connected leafs, excluding rule, var, and
	 * comment leafs. */
	private List<Integer> getTextSequence() {
		TreeMap<Edge, Integer> tmp = new TreeMap<>(edgeSet);
		tmp.put(RootMark, RootMarkType); 		// supply a root coord
		List<Integer> result = new ArrayList<>();
		for (Edge edge : tmp.keySet()) {
			if (edge == RootMark) {
				result.add(RootMarkType);
			} else if (edge.leaf.isTerminal() && !edge.leaf.isVar() && !edge.leaf.isComment()) {
				result.add(edge.leaf.getText().hashCode());
			}
		}
		return result;
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

	public boolean equivalentTo(EdgeSet o) {
		return getEdgeTypes().equals(o.getEdgeTypes());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		EdgeSet other = (EdgeSet) o;
		return getTypeSequence().equals(other.getTypeSequence());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edgeSet == null) ? 0 : edgeSet.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return String.format("EdgeSet [types=%s edges=%s]", getEdgeTypes().size(), getEdges().size());
	}
}
