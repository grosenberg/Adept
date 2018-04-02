package net.certiv.adept.format;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import net.certiv.adept.util.Log;

public class Space {

	private final TreeSet<Region> subSpaces = new TreeSet<>();

	public void add(Map<Region, TextEdit> edits) throws FormatException {
		for (Entry<Region, TextEdit> entry : edits.entrySet()) {
			Region region = entry.getKey();
			TextEdit edit = entry.getValue();

			try {
				add(region);
				Log.debug(this, "A: " + edit.toString());
			} catch (RegionException e) {
				Log.error(this, "I: " + edit.toString());
			}
		}
	}

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

	protected boolean overlaps(Region region) {
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
