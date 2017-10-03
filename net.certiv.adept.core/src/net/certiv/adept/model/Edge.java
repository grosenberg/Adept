package net.certiv.adept.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.annotations.Expose;

import net.certiv.adept.model.util.Coord;

public class Edge implements Comparable<Edge> {

	// X=root Id; Y=leaf Id; value=connecting edge
	private static final Table<Long, Long, Edge> cache = HashBasedTable.create();

	@Expose public long leafId;
	@Expose public long rootId;
	@Expose public EdgeType type;
	@Expose public int len;
	@Expose public Coord coord;

	public Feature root;
	public Feature leaf;

	public static Edge create(Feature root, Feature leaf, EdgeType kind, int len) {
		Edge edge = cache.get(root.getId(), leaf.getId());
		if (edge == null) {
			edge = new Edge(root, leaf, kind, len);
			cache.put(root.getId(), leaf.getId(), edge);
		}
		return edge;
	}

	private Edge(Feature root, Feature leaf, EdgeType kind, int len) {
		this.root = root;
		this.leaf = leaf;
		this.type = kind;
		this.len = len;

		rootId = root.getId();
		leafId = leaf.getId();

		int vert = root.getLine() - leaf.getLine();
		coord = new Coord(len, vert, leaf.getLength());
	}

	Edge() {
		this.type = EdgeType.ROOT;
		this.coord = new Coord(0, 0, 0);
	}

	/** Coordinate-based comparison order by default. */
	@Override
	public int compareTo(Edge o) {
		return coord.compareTo(o.coord);
	}

	public boolean isEquivalent(Edge o) {
		if (leaf.getType() != o.leaf.getType()) return false;
		if (compareTo(o) != 0) return false;
		return true;
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
		result = prime * result + len;
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s {%s -> %s}", coord, root.getAspect(), leaf.getAspect());
	}
}
