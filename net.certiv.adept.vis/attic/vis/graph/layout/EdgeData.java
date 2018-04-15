package net.certiv.adept.vis.graph.graph.layout;

import java.awt.geom.Point2D;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.util.Position;

public class EdgeData {

	public static EdgeData create(Edge edge) {
		return new EdgeData(edge);
	}

	public int rootStart;
	public int leafStart;
	public double metric;
	public int weight;

	public int rootX;
	public int rootY;
	public int leafX;
	public int leafY;

	public Position deltaXY;

	public EdgeData(Edge edge) {
		this.rootStart = edge.root.getStart();
		this.leafStart = edge.leaf.getStart();
		this.metric = edge.coord.distance();
		this.weight = edge.leaf.getEquivalentWeight();

		rootX = edge.root.getCol();
		rootY = edge.root.getLine();
		leafX = edge.leaf.getCol();
		leafY = edge.leaf.getLine();

		this.deltaXY = new Position(leafX - rootX, leafY - rootY);
	}

	/** Return a leaf point relative positioned relative to the given root point. */
	public Point2D getRelative(Point2D root) {
		double x = leafX - root.getX();
		double y = leafY - root.getY();
		return new Point2D.Double(x, y);
	}
}
