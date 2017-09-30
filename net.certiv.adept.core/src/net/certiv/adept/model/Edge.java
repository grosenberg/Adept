package net.certiv.adept.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.annotations.Expose;

import net.certiv.adept.util.Coord;

public class Edge implements Comparable<Edge> {

	// X=root Id; Y=leaf Id; value=connecting edge
	private static final Table<Long, Long, Edge> cache = HashBasedTable.create();

	public static final Edge RootMark = new Edge();

	@Expose public long leafId;
	@Expose public long rootId;
	@Expose public EdgeType kind;
	@Expose public Coord coord;

	public Feature root;
	public Feature leaf;

	public static Edge create(Feature root, Feature leaf, EdgeType kind) {
		Edge edge = cache.get(root.getId(), leaf.getId());
		if (edge == null) {
			edge = new Edge(root, leaf, kind);
			cache.put(root.getId(), leaf.getId(), edge);
		}
		return edge;
	}

	private Edge(Feature root, Feature leaf, EdgeType kind) {
		this.root = root;
		this.leaf = leaf;
		this.kind = kind;

		rootId = root.getId();
		leafId = leaf.getId();

		int offset = root.offsetDistance(leaf);
		int vert = root.getLine() - leaf.getLine();
		coord = new Coord(offset, vert, leaf.getLength());
	}

	private Edge() {
		this.rootId = 0;
		this.leafId = 0;
		this.kind = EdgeType.ROOT;
		this.coord = new Coord(0, 0, 0);
	}

	/** Coordinate-based comparison order by default. */
	@Override
	public int compareTo(Edge o) {
		return coord.compareTo(o.coord);
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
		return String.format("%s {%s -> %s}", coord, root.getAspect(), leaf.getAspect());
	}

	//	/** Identity-based comparison order: considers only type and text of the edges. */
	//	@Override
	//	public int compareTo(Edge o) {
	//		if (leaf.getType() < o.leaf.getType()) return -1;
	//		if (leaf.getType() > o.leaf.getType()) return 1;
	//		if (leaf.isVar()) return 0;
	//		return leaf.getText().compareTo(o.leaf.getText());
	//	}
}
