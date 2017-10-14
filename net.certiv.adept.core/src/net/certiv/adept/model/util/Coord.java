package net.certiv.adept.model.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Defines a point in a non-standard coordinate system.
 * 
 * The 'pos' value represents the signed sequential (stream offset delta) unit separation of this
 * point from a referent. The 'horz' value represents the signed horizontal unit separation of this
 * point from the referent. The 'vert' value represents the signed vertical unit separation of this
 * point from the referent. The 'len' value represents the positive unit length of this point.
 */
public class Coord implements Comparable<Coord> {

	@Expose public int pos; // position: stream offset; before +
	@Expose public int horz; // horizontal: visCol delta; left +
	@Expose public int vert; // vertical: line delta; up +
	@Expose public int len; // length

	public Coord(int pos, int horz, int vert, int len) {
		this.pos = pos;
		this.horz = horz;
		this.vert = vert;
		this.len = len;
	}

	public double distance() {
		return sign() * Math.sqrt(pos * pos + vert * vert + len * len);
	}

	public int sign() {
		return pos >= 0 && vert >= 0 ? 1 : -1;
	}

	public boolean isBefore(Coord o) {
		if (pos > o.pos || vert > o.vert) return true;
		if (pos < o.pos || vert < o.vert) return false;
		if (len > o.len) return true;
		return false;
	}

	public List<Aspect> getEdgeAspects() {
		List<Aspect> seq = new ArrayList<>();
		if (vert > 0) seq.add(Aspect.ABOVE);
		if (vert < 0) seq.add(Aspect.BELOW);
		if (vert == 0) seq.add(Aspect.ALIGNED_HORZ);
		if (horz > 0) seq.add(Aspect.BEFORE);
		if (horz < 0) seq.add(Aspect.AFTER);
		if (horz == 0) seq.add(Aspect.ALIGNED_VERT);
		return seq;
	}

	public int quadrant() {
		if (pos >= 0 && vert >= 0) {
			return 1;
		} else if (pos < 0 && vert >= 0) {
			return 2;
		} else if (pos >= 0 && vert < 0) {
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
		result = prime * result + pos;
		result = prime * result + vert;
		result = prime * result + len;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Coord other = (Coord) o;
		return pos == other.pos && vert == other.vert && len == other.len;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s, %s)", pos, vert, len);
	}
}
