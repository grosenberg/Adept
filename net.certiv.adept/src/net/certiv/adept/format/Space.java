package net.certiv.adept.format;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import net.certiv.adept.util.Log;

public class Space {

	private final TreeSet<Region> subSpaces = new TreeSet<>();

	public void add(Map<Region, TextEdit> edits) throws FormatException {
		for (Entry<Region, TextEdit> entry : edits.entrySet()) {
			Log.debug(this, "Adding: " + entry.getValue().toString());
			if (overlaps(entry.getKey())) throw new FormatException("Overlap", entry.getValue());
			add(entry.getKey());
		}
	}

	// public void addAll(List<TextEdit> edits) throws FormatException {
	// for (TextEdit edit : edits) {
	// Log.debug(this, "Adding: " + edit.toString());
	// Region region = edit.getRegion();
	// if (overlaps(region)) {
	// throw new FormatException("Overlap", edit);
	// }
	// add(region);
	// }
	// }

	private void add(Region region) {
		Region lower = subSpaces.floor(region);
		if (lower != null && lower.contains(region)) return;

		Region upper = subSpaces.ceiling(region);
		if (upper != null && upper.contains(region)) return;

		if (lower != null && lower.adjacent(region)) {
			if (upper != null && upper.adjacent(region)) {
				merge(lower, upper);
			} else {
				merge(lower, region);
			}

		} else if (upper != null && upper.adjacent(region)) {
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

	private boolean overlaps(Region region) {
		Region lower = subSpaces.floor(region);
		if (lower != null && lower.overlaps(region)) return true;

		Region upper = subSpaces.ceiling(region);
		if (upper != null && upper.overlaps(region)) return true;

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
