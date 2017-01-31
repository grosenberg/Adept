package net.certiv.adept.vis.layout;

import java.awt.geom.Point2D;

import net.certiv.adept.model.Edge;
import net.certiv.adept.topo.Position;

public class EdgeData {

	public static EdgeData create(Edge edge) {
		return new EdgeData(edge);
	}

	public int frs;
	public int fls;
	public double metric;
	public double weight;
	public Position deltaXY;
	// public double angle;

	public EdgeData(Edge edge) {
		this.frs = edge.root.getStart();
		this.fls = edge.leaf.getStart();
		this.metric = edge.metric;
		this.weight = edge.rarity;

		int frx = edge.root.getX();
		int fry = edge.root.getY();
		int flx = edge.leaf.getX();
		int fly = edge.leaf.getY();

		this.deltaXY = new Position(flx - frx, fly - fry);
		// angle = Math.atan2(deltaXY.getY(), deltaXY.getX());
	}

	/**
	 * Return a point defining the position of a leaf relative to the given point represeting a
	 * root.
	 */
	public Point2D getRelative(Point2D first) {
		if (frs < fls) {
			double x = first.getX() + deltaXY.getX();
			double y = first.getY() + deltaXY.getY();
			return new Position(x, y);
		}
		double x = first.getX() - deltaXY.getX();
		double y = first.getY() - deltaXY.getY();
		return new Position(x, y);
	}

	/**
	 * Return a point defining the position of a root relative to the given point represeting a
	 * leaf.
	 */
	public Point2D getRelativeInv(Point2D second) {
		if (frs < fls) {
			double x = second.getX() - deltaXY.getX();
			double y = second.getY() - deltaXY.getY();
			return new Position(x, y);
		}
		double x = second.getX() + deltaXY.getX();
		double y = second.getY() + deltaXY.getY();
		return new Position(x, y);
	}
}
