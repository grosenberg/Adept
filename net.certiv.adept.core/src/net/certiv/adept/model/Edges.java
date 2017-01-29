package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

public class Edges {

	// defines connections between this feature and others in the local group
	// key=type of feature connected; value=edges to unique features of type
	@Expose private Map<Integer, List<Edge>> edges;

	public Edges() {
		edges = new LinkedHashMap<>();
	}

	public void addEdge(Edge edge) {
		int type = edge.leaf.getType();
		List<Edge> value = edges.get(type);
		if (value == null) {
			value = new ArrayList<>();
			edges.put(type, value);
		}
		value.add(edge);
	}

	public Map<Integer, List<Edge>> getEdges() {
		return edges;
	}

	/** Returns the edges that are of the given edge leaf type */
	public List<Edge> getEdges(int type) {
		List<Edge> typedEdges = edges.get(type);
		if (typedEdges == null) return Collections.emptyList();
		return typedEdges;
	}

	/** Returns the total number of edge leaf types */
	public int typeSize() {
		return edges.keySet().size();
	}

	/** Returns the total number of edges */
	public int size() {
		int size = 0;
		for (List<Edge> value : edges.values()) {
			size += value.size();
		}
		return size;
	}

	/**
	 * Determines whether the given edge is present in the current map of edges. An edge is present
	 * if an edge having the same type and terminating in the same leaf feature is present in the
	 * map.
	 */
	public boolean contains(Edge edge) {
		int type = edge.leaf.getType();
		List<Edge> value = edges.get(type);
		if (value == null) return false;
		for (Edge v : value) {
			if (v.leaf.equals(edge.leaf)) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("Edges [types=%s edges=%s]", typeSize(), size());
	}
}
