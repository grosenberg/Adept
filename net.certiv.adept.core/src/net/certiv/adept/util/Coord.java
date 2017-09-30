package net.certiv.adept.util;

/**
 * Defines a point in a non-standard coordinate system.
 * 
 * The 'X' value represents the signed sequential unit separation of the point from some referent
 * point. The 'Y' value represents the signed vertical unit separation of the point from the
 * referent point. The 'L' value represents a positive unit length of the point.
 */
public class Coord implements Comparable<Coord> {

	public final int x;
	public final int y;
	public final int l;

	public Coord(int x, int y, int l) {
		this.x = x;
		this.y = y;
		this.l = l;
	}

	public double distance() {
		return sign() * Math.pow(x * x + y * y, 0.5);
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

	public int sign() {
		if (x >= 0 && y >= 0) return 1;
		return -1;
	}

	public boolean isBefore(Coord o) {
		if (x >= o.x && y >= o.y && (l >= o.l)) {
			return true;
		}
		return false;
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
		return String.format("(%s, %s)", x, y);
	}
}
