package net.certiv.adept.format.align;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.certiv.adept.util.ArraySet;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.TreeMultimap;

public class Aligner {

	// key=stopCol; values=line/group marks
	private final TreeMultimap<Integer, Mark> stops = new TreeMultimap<>();
	private final TreeMultimap<Integer, Part> buffer;
	private final int tabWidth;
	private final int[] reach; // = { 0, 1, -1, 2, -2, 3, -3, 4, -4, 5 };

	public Aligner(TreeMultimap<Integer, Part> buffer, int tabWidth) {
		this.buffer = buffer;
		this.tabWidth = tabWidth;

		reach = new int[tabWidth * 2 + 2];
		reach[0] = 0;
		for (int idx = 1, jdx = 1; idx < reach.length; idx++) {
			reach[idx] = jdx;
			idx++;
			if (idx != reach.length) {
				reach[idx] = jdx * -1;
			}
			jdx++;
		}
	}

	public void alignBuffer() {
		locateStops();
		dumpMarks();
		alignParts();
	}

	public void clear() {
		stops.clear();
		buffer.clear();
	}

	/**
	 * <pre>
	 *    minStart minCol                 maxCol
	 * 		--->|    |<--------------------->|
	 * </pre>
	 */
	private void locateStops() {
		for (Integer line : buffer.keySet()) {
			int group = 0;
			for (Part part : buffer.get(line)) {
				if (part.type != Text.PACK) {
					addToMarks(part.visCol, line, group);
				}
				group++;
			}
		}
	}

	private void dumpMarks() {
		for (Integer line : buffer.keySet()) {
			ArraySet<Mark> marks = stops.get(line);
			if (marks != null) Log.debug(this, "Stops %s: %s", line, marks);
		}
	}

	// add mark to marks group within reach or create new group; return the mark
	private Mark addToMarks(int visCol, int line, int group) {
		search:
		for (int at : reach) {
			int idx = visCol + at;
			if (idx < 0) continue;
			Set<Mark> marks = stops.get(idx);
			if (marks == null) continue; // miss

			// two groups on the same line cannot be at the same stop
			for (Mark mark : marks) {
				if (mark.line == line) break search;
			}
			Mark mark = new Mark(visCol, line, group);
			marks.add(mark);
			return mark;
		}

		Mark mark = new Mark(visCol, line, group);
		stops.put(visCol, mark);
		return mark;
	}

	private void alignParts() {
		// key=line; value=last updated group num
		HashMap<Integer, Integer> updates = new HashMap<>();
		for (Integer col : stops.keySet()) {
			ArraySet<Mark> marks = stops.get(col);
			updateLines(updates, marks);
			int alignCol = preferred(marks);
			Log.debug(this, "Aligning on %s (%s)", alignCol, marks);

			for (Mark mark : marks) {
				mark.stopCol = alignCol;
				alignParts(mark);
			}
		}
	}

	private void updateLines(HashMap<Integer, Integer> updates, ArraySet<Mark> marks) {
		for (Mark mark : marks) {
			Integer lastGroup = updates.get(mark.line);
			if (lastGroup == null) {
				lastGroup = 0;
			}

			for (int idx = lastGroup; idx <= mark.group; idx++) {
				ArraySet<Part> parts = buffer.get(mark.line);
				for (int group = 1; group <= mark.group && group < parts.size(); group++) {
					Part part = parts.get(group);
					Part prior = parts.get(group - 1);
					part.visCol = prior.visCol + prior.visLen;
				}
			}
			updates.put(mark.line, mark.group);
		}
	}

	/*
	 * Update intervening parts and extend trailing whitespace of the prior part to reach the desired
	 * alignment column.
	 */
	private void alignParts(Mark mark) {
		ArraySet<Part> parts = buffer.get(mark.line);
		for (int group = 1; group < mark.group; group++) {
			Part part = parts.get(group);
			Part prior = parts.get(group - 1);
			part.visCol = prior.visCol + prior.visLen;
		}
		Part part = parts.get(mark.group);
		Part prior = parts.get(mark.group - 1);
		part.visCol = mark.stopCol;
		prior.extendWs(mark.stopCol);
	}

	// find the preferred alignment column for the set of marks;
	private int preferred(ArraySet<Mark> marks) {
		int minEndCol = 0; // viability limit
		for (Mark mark : marks) {
			ArraySet<Part> parts = buffer.get(mark.line);
			Part part = parts.get(mark.group);
			minEndCol = Math.max(part.visCol + part.vWidth, minEndCol);
			Log.debug(this, String.format("MinEndCol %s (%s)", minEndCol, part));
		}

		// 1) the stopCol of the first type, if viable
		Mark first = marks.get(0);
		if (first.stopCol > minEndCol) return first.stopCol;

		// 2) the stopCol shared by the largest set (n>1) of viable marks
		// key=col; value=popularity
		Map<Integer, Integer> buckets = new HashMap<>();
		for (Mark mark : marks) {
			if (mark.stopCol > minEndCol) {
				Integer cnt = buckets.get(mark.stopCol);
				if (cnt == null) {
					buckets.put(mark.stopCol, 1);
				} else {
					buckets.put(mark.stopCol, cnt + 1);
				}
			}
		}

		int bucket = 0;
		int count = 0;
		for (Integer col : buckets.keySet()) {
			int cnt = buckets.get(col);
			if (cnt > count) {
				bucket = col;
				count = cnt;
			}
		}
		if (count > 1) return bucket;

		// 3) a tab aligned average of the viable visCols (n > 1)
		int num = 0;
		count = 0;
		for (Mark mark : marks) {
			if (mark.stopCol > minEndCol) {
				num += mark.stopCol;
				count++;
			}
		}
		if (count > 1) {
			num = (int) Math.round((double) num / count);
			if (num % tabWidth == 0) return num;
			return Strings.measureVisualWidth("\\t", tabWidth, num) + num;
		}

		// 4) the stopCol tab aligned greater than the viable limit
		return Strings.measureVisualWidth("\\t", tabWidth, minEndCol) + minEndCol;
	}
}
