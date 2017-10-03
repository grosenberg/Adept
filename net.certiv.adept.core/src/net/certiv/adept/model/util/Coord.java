package net.certiv.adept.model.util;

import com.google.gson.annotations.Expose;

/**
 * Defines a point in a non-standard coordinate system.
 * 
 * The 'X' value represents the signed sequential unit separation of this point from a referent. The
 * 'Y' value represents the signed vertical unit separation of this point from the referent. The 'L'
 * value represents the positive unit length of this point.
 */
public class Coord implements Comparable<Coord> {

	@Expose public int x;
	@Expose public int y;
	@Expose public int l;

	public Coord(int x, int y, int l) {
		this.x = x;
		this.y = y;
		this.l = l;
	}

	public double distance() {
		return sign() * Math.sqrt(x * x + y * y + l * l);
	}

	public int sign() {
		return x >= 0 && y >= 0 ? 1 : -1;
	}

	public boolean isBefore(Coord o) {
		if (x > o.x || y > o.y) return true;
		if (x < o.x || y < o.y) return false;
		if (l > o.l) return true;
		return false;
	}

	public int quadrant() {
		if (x >= 0 && y >= 0) {
			return 1;
		} else if (x < 0 && y >= 0) {
			return 2;
		} else if (x >= 0 && y < 0) {
			return 3;
		}
		return 4;
	}

	@Override
	public int compareTo(Coord o) {
		if (isBefore(o)) return -1;
		if (equals(o)) return 0;
		return 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + l;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Coord other = (Coord) o;
		return x == other.x && y == other.y && l == other.l;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s)", x, y, l);
	}
}
