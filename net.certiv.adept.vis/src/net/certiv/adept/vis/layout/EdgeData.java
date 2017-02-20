package net.certiv.adept.vis.layout;

import java.awt.geom.Point2D;

import net.certiv.adept.model.Edge;
import net.certiv.adept.topo.Position;

public class EdgeData {

	public static EdgeData create(Edge edge) {
		return new EdgeData(edge);
	}

	public int rootStart;
	public int leafStart;
	public double metric;
	public double weight;

	public int rootX;
	public int rootY;
	public int leafX;
	public int leafY;

	public Position deltaXY;
	// public double angle;

	public EdgeData(Edge edge) {
		this.rootStart = edge.root.getStart();
		this.leafStart = edge.leaf.getStart();
		this.metric = edge.metric;

		rootX = edge.root.getCol();
		rootY = edge.root.getLine();
		leafX = edge.leaf.getCol();
		leafY = edge.leaf.getLine();

		this.deltaXY = new Position(leafX - rootX, leafY - rootY);
		// angle = Math.atan2(deltaXY.getY(), deltaXY.getX());
	}

	/**
	 * Return a point defining the position of a leaf relative to the given point represeting a
	 * root.
	 */
	public Point2D getRelative(Point2D root) {
		double x = leafX - root.getX();
		double y = leafY - root.getY();
		return new Point2D.Double(x, y);
	}

	// /**
	// * Return a point defining the position of a root relative to the given point represeting a
	// * leaf.
	// */
	// public Point2D getRelativeInv(Point2D second) {
	// if (rootStart < leafStart) {
	// double x = second.getX() - deltaXY.getX();
	// double y = second.getY() - deltaXY.getY();
	// return new Position(x, y);
	// }
	// double x = second.getX() + deltaXY.getX();
	// double y = second.getY() + deltaXY.getY();
	// return new Position(x, y);
	// }
}
