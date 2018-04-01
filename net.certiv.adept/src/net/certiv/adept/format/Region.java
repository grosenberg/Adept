package net.certiv.adept.format;

import java.util.Comparator;

public class Region implements Comparator<Region>, Comparable<Region> {

	public final int min;
	public final int max;

	public Region(int a, int b) {
		this.min = Math.min(a, b);
		this.max = Math.max(a, b);
	}

	public Region(Region lower, Region upper) {
		this.min = Math.min(lower.min, upper.min);
		this.max = Math.max(lower.max, upper.max);
	}

	public boolean overlaps(Region region) {
		if (max > region.min && max < region.max) return true;
		if (min > region.min && min < region.max) return true;
		return false;
	}

	public boolean contains(Region region) {
		return contains(region.min) && contains(region.max) ? true : false;
	}

	public boolean contains(int val) {
		return val >= min && val <= max ? true : false;
	}

	public boolean adjacent(Region region) {
		return region.min == max || region.max == min;
	}

	@Override
	public int compareTo(Region o) {
		return compare(this, o);
	}

	@Override
	public int compare(Region o1, Region o2) {
		if (o1.min == o2.min && o1.max == o2.max) return 0;
		if (o1.max <= o2.min) return -1;
		if (o1.min >= o2.max) return 1;
		throw new RegionException(o1, o2, "Overlapping");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Region other = (Region) obj;
		if (max != other.max) return false;
		if (min != other.min) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("<%s:%s>", min, max);
	}
}
