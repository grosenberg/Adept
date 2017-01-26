package net.certiv.adept.topo;

import java.awt.geom.Point2D;

import com.google.gson.annotations.Expose;

public class Position extends Point2D {

	@Expose private double x;
	@Expose private double y;

	public Position(double x, double y) {
		setLocation(x, y);
	}

	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	public String toString() {
		return String.format("Position X=%s Y=%s", x, y);
	}
}
