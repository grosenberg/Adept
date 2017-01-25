package net.certiv.adept.topo;

import java.awt.geom.Point2D;

public class Position extends Point2D {

	private double x_relativeToRoot;
	private double y_relativeToRoot;

	public Position(double x_relativeToRoot, double y_relativeToRoot) {
		setLocation(x_relativeToRoot, y_relativeToRoot);
	}

	@Override
	public double getX() {
		return x_relativeToRoot;
	}

	@Override
	public double getY() {
		return y_relativeToRoot;
	}

	@Override
	public void setLocation(double x_relativeToRoot, double y_relativeToRoot) {
		this.x_relativeToRoot = x_relativeToRoot;
		this.y_relativeToRoot = y_relativeToRoot;
	}
}
