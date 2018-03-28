package net.certiv.adept.format;

import java.util.List;
import java.util.TreeSet;

public class Space {

	private final TreeSet<Region> subSpaces = new TreeSet<>();

	public void addAll(List<TextEdit> edits) throws FormatException {
		for (TextEdit edit : edits) {
			Region region = edit.getRegion();
			if (overlaps(region)) throw new FormatException("Overlap", edit);
			add(region);
		}
	}

	public void add(Region region) {
		Region lower = subSpaces.floor(region);
		if (lower.contains(region)) return;

		Region upper = subSpaces.ceiling(region);
		if (upper.contains(region)) return;

		if (lower.adjacent(region)) {
			if (upper.adjacent(region)) {
				merge(lower, upper);
			} else {
				merge(lower, region);
			}

		} else if (upper.adjacent(region)) {
			merge(region, upper);

		} else {
			subSpaces.add(region);
		}

	}

	private void merge(Region lower, Region upper) {
		subSpaces.remove(lower);
		subSpaces.remove(upper);
		subSpaces.add(new Region(lower, upper));
	}

	public boolean overlaps(Region region) {

		Region lower = subSpaces.floor(region);
		if (lower.overlaps(region)) return true;

		Region upper = subSpaces.ceiling(region);
		if (upper.overlaps(region)) return true;

		return false;
	}

	public void dispose() {
		subSpaces.clear();
	}

	@Override
	public String toString() {
		return subSpaces.toString();
	}
}
