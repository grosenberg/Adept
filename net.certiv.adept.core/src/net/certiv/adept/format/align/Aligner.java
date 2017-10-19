package net.certiv.adept.format.align;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;

import net.certiv.adept.util.ArraySet;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.TreeMultimap;

public class Aligner {

	protected static final int PACK = -1;	// not subject to alignment

	// key=visCol; values=line/group marks
	private final HashMultimap<Integer, Mark> stops = HashMultimap.create();
	private final TreeMultimap<Integer, Part> buffer;
	private final int[] reach; // = { 0, 1, -1, 2, -2, 3, -3, 4, -4, 5 };

	private int tabWidth;

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

	public boolean put(int visCol, int line, int group) {
		// find existing stop within reach
		for (int at : reach) {
			int idx = visCol + at;
			if (idx < 0) continue;
			Set<Mark> marks = stops.get(idx);
			if (marks != null) {
				for (Mark mark : marks) {
					// two groups on the same line cannot be at the same stop
					if (mark.line == line) break;
				}
				return marks.add(new Mark(visCol, line, group));
			}
		}

		// must be new
		return stops.put(visCol, new Mark(visCol, line, group));
	}

	public void alignBuffer() {
		correctStops();
		align();
	}

	/**
	 * <pre>
	 *    minStart minCol                 maxCol
	 * 		--->|    |<--------------------->|
	 * </pre>
	 */
	private void correctStops() {
		for (Integer line : buffer.keySet()) {
			int group = 0;
			for (Part part : buffer.asList(line)) {
				if (part.mark != PACK) {
					put(part.visCol, line, group);
				}
				group++;
			}
		}

		Set<Integer> stopCols = stops.keySet();
		for (Integer col : stopCols) {
			Set<Mark> marks = stops.get(col);
			int maxStopCol = maxCol(marks);
			int minStopCol = minCol(marks);
			int prefCol = preferred(marks, maxStopCol, minStopCol);

			if (stops.get(prefCol) != null) {
				throw new RuntimeException("Impossible?");
			}

			stops.removeAll(col);
			stops.putAll(prefCol, marks);
		}
	}

	/*
	 * Take the corrected stops and add whitespace to the buffer to make the content match. Done by
	 * getting the end horizontal ws of the prior part and extending it to reach the desired startIndex
	 * of the aligned part.
	 */
	private void align() {
		for (Integer alignCol : stops.keySet()) {
			Set<Mark> marks = stops.get(alignCol);
			for (Mark mark : marks) {
				int width = getWidth(mark.line, mark.group);
				if (width + 1 == alignCol) continue;

				Part prior = getPart(mark.line, mark.group - 1);
				if (prior != null) {
					prior.extendWs(tabWidth, alignCol);
				}
			}
		}

		int alignStop = 0;
		boolean done = false;
		int visCol = 0;
		while (!done) {
			done = true;
			for (Integer key : buffer.keySet()) {
				List<Part> parts = buffer.asList(key);
				if (alignStop < parts.size()) {
					done = false;
					Part part = parts.get(alignStop);
					if (part.mark != PACK) {
						visCol = Math.max(visCol, part.visCol);
					}
				}
			}

			alignStop++;
		}
	}

	private int getWidth(int line, int end) {
		ArraySet<Part> parts = buffer.get(line);
		int width = 0;
		for (int idx = 0; idx < end; idx++) {
			Part part = parts.get(idx);
			part.visCol = width + 1;
			part.width = Strings.measureVisualWidth(part.sb, tabWidth);
			width += part.width;
		}
		return width;
	}

	private Part getPart(int line, int i) {
		return null;
	}

	private int preferred(Set<Mark> marks, int max, int min) {
		Map<Integer, Integer> buckets = new HashMap<>();
		for (Mark mark : marks) {
			if (mark.visCol >= min && mark.visCol <= max) {
				Integer cnt = buckets.get(mark.visCol);
				if (cnt == null) {
					buckets.put(mark.visCol, 1);
				} else {
					buckets.put(mark.visCol, cnt + 1);
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
		return bucket;
	}

	private int maxCol(Set<Mark> marks) {
		int maxCol = 0;
		for (Mark mark : marks) {
			maxCol = Math.max(mark.visCol, maxCol);
		}
		return maxCol;
	}

	private int minCol(Set<Mark> marks) {
		int minStart = 0;
		int minCol = 0;
		for (Mark mark : marks) {
			minStart = Math.max(startIndex(mark.line, mark.group), minStart);
			minCol = Math.min(mark.visCol, minCol);
		}
		return Math.max(minStart + 1, minCol);
	}

	private int startIndex(int line, int group) {
		int startIndex = 0;
		int idx = 0;

		for (Part part : buffer.get(line)) {
			if (idx < group) {
				startIndex += Strings.measureVisualWidth(part.sb.toString(), tabWidth);
			}
		}
		return startIndex;
	}

}
