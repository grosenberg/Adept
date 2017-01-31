package net.certiv.adept.model;

import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.annotations.Expose;

/**
 * The collection of edges that defines the connections between a root feature and other features in
 * the local group.
 */
public class Edges {

	private static final TreeSet<Edge> emptyTreeSet = (TreeSet<Edge>) Collections.unmodifiableSet(new TreeSet<Edge>());

	// key=edge key; value=edges
	@Expose private TreeMap<EdgeKey, TreeSet<Edge>> edges;
	// total count of edges
	@Expose private int edgeCnt;

	public Edges() {
		edges = new TreeMap<>();
	}

	public void addEdge(Edge edge) {
		int type = edge.leaf.getType();
		String text = edge.leaf.getText();
		EdgeKey key = EdgeKey.create(type, text);
		TreeSet<Edge> value = edges.get(key);
		if (value == null) {
			value = new TreeSet<>();
			edges.put(key, value);
		}
		value.add(edge);
		edgeCnt++;
	}

	public TreeMap<EdgeKey, TreeSet<Edge>> getEdges() {
		return edges;
	}

	/** Returns the edges that are of the given edge leaf type/text */
	public TreeSet<Edge> getEdges(int type, String text) {
		return getEdges(EdgeKey.create(type, text));
	}

	/** Returns the edges that are of the given edge key type */
	public TreeSet<Edge> getEdges(EdgeKey key) {
		TreeSet<Edge> typedEdges = edges.get(key);
		if (typedEdges == null) return emptyTreeSet;
		return typedEdges;
	}

	/** Returns the total number of edge leaf type/text combinations */
	public int typeSize() {
		return edges.size();
	}

	/** Returns the total number of edges */
	public int getEdgeCount() {
		return edgeCnt;
	}

	/**
	 * Determines whether the given edge is present in the current map of edges. An edge is present
	 * if an edge having the same type and terminating in the same leaf feature is present in the
	 * map.
	 */
	public boolean contains(Edge edge) {
		int type = edge.leaf.getType();
		String text = edge.leaf.getText();
		EdgeKey key = EdgeKey.create(type, text);
		TreeSet<Edge> value = edges.get(key);
		if (value == null) return false;
		if (value.contains(edge)) return true;
		return false;
	}

	@Override
	public String toString() {
		return String.format("Edges [types=%s edges=%s]", typeSize(), getEdgeCount());
	}
}
