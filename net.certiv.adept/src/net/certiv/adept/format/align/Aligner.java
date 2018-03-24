package net.certiv.adept.format.align;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Collect;
import net.certiv.adept.util.Maths;

public class Aligner {

	private final Map<ParserRuleContext, Group> groups = new HashMap<>();
	private ParseRecord data;

	public Aligner(ParseRecord data) {
		this.data = data;
	}

	public void groupBeg(ParserRuleContext ctx) {
		Group group = groups.get(ctx);
		if (group != null) throw new IllegalStateException("Invalid inLine group parentage.");

		groups.put(ctx, new Group(ctx));
	}

	/**
	 * Aligns tokens.
	 * <p>
	 * If they occur on both the current line and the previous line, they may be aligned vertically by
	 * like type. If on different lines, they may be aligned vertically with each other. May be part of
	 * a Group, typically as the begin and end elements.
	 *
	 * @param ctx the relevant group context
	 * @param tokens the token array
	 */
	public void align(Align align, ParserRuleContext ctx, TerminalNode... nodes) {
		align(align, ctx, Arrays.asList(nodes));
	}

	/**
	 * Aligns a related cluster of tokens. For non-GROUP inLine, automatically begins and ends an
	 * implicit group for the current context.
	 *
	 * @param ctx the relevant group context
	 * @param tokens the token collection
	 */
	public void align(Align align, ParserRuleContext ctx, List<TerminalNode> nodes) {
		if (align != Align.GROUP) groupBeg(ctx);

		Group group = groups.get(ctx);
		for (AdeptToken token : symbols(nodes)) {
			group.addGroupMembers(align, token.getLine(), token);
		}

		if (align != Align.GROUP) groupEnd(ctx);
	}

	/**
	 * For each group of alignment present, add a marker to each participating token identifying
	 * <ul>
	 * <li>the alignment group
	 * <li>the inLine of the token in the containing group line relative to other participating tokens
	 *
	 * @param ctx
	 */
	public void groupEnd(ParserRuleContext ctx) {
		Group group = groups.get(ctx);
		if (group == null) throw new IllegalStateException("Non-existant inLine group.");

		TableMultilist<Align, Integer, AdeptToken> members = group.getMembers();

		for (Align align : members.keySet()) {
			TreeMultilist<Integer, AdeptToken> lines = members.get(align);
			Gap gap = findGap(lines);
			int total = lines.valuesSize();
			for (AdeptToken token : lines.values()) {
				Place[] place = findPlace(lines, token);
				token.refToken().setAlign(align, gap, place, total);
			}
		}
		groups.remove(ctx);
	}

	public void clear() {
		groups.clear();
	}

	// --------------------------------------------------------------------

	private List<AdeptToken> symbols(List<TerminalNode> nodes) {
		List<AdeptToken> tokens = new ArrayList<>();
		for (TerminalNode node : nodes) {
			tokens.add((AdeptToken) node.getSymbol());
		}
		return tokens;
	}

	// characterize gap within the lines
	private Gap findGap(TreeMultilist<Integer, AdeptToken> lines) {
		List<Double> reals = new ArrayList<>();
		for (Integer line : lines.keySet()) {
			List<AdeptToken> aligns = lines.get(line);
			AdeptToken[] tokens = aligns.toArray(new AdeptToken[aligns.size()]);
			for (int idx = 1; idx < tokens.length; idx++) {
				int beg = tokens[idx - 1].getTokenIndex() + 1;
				int end = tokens[idx].getTokenIndex() - 1;
				reals.add((double) data.getRealTokenCount(beg, end));
			}
		}

		double[] vector = Collect.toPrimitiveArray(reals);
		if (Maths.stdDeviation(vector) > 2) return Gap.VARIABLE;

		double mean = Maths.mean(vector);
		if (mean < 1) return Gap.NONE;
		if (mean < 2) return Gap.ONE;
		if (mean < 5) return Gap.SOME;
		return Gap.MANY;
	}

	private Place[] findPlace(TreeMultilist<Integer, AdeptToken> lines, AdeptToken token) {
		Place[] result = new Place[2];

		int num = token.getLine();
		if (num == lines.firstKey()) {
			result[0] = Place.BEG;
		} else if (num == lines.lastKey()) {
			result[0] = Place.END;
		} else {
			result[0] = Place.MID;
		}

		List<AdeptToken> line = lines.get(num);
		int len = line.size();
		switch (len) {
			case 1:
				result[1] = Place.SOLO;
			default:
				int idx = line.indexOf(token);
				if (idx == 0) {
					result[1] = Place.BEG;
				} else if (idx == len - 1) {
					result[1] = Place.END;
				} else {
					result[1] = Place.MID;
				}
		}
		return result;
	}

	// ===================================================================

	// private static final int[] reach = new int[] { 0, 1, -1, 2, -2, 3, -3, 4, -4, 5 };

	// private final ParseRecord data;
	// private final int tabWidth;

	// key=stopCol; values=line/group marks
	// private final TreeMultiset<Integer, Mark> stops = new TreeMultiset<>();

	// /**
	// * <pre>
	// * minStart minCol maxCol
	// * --->| |<--------------------->|
	// * </pre>
	// */
	// private void locateStops() {
	// for (Integer line : buffer.keySet()) {
	// int group = 0;
	// for (Part part : buffer.get(line)) {
	// if (part.type != Text.PACK) {
	// addToMarks(part.visCol, line, group);
	// }
	// group++;
	// }
	// }
	// }
	//
	// private void dumpMarks() {
	// for (Integer line : buffer.keySet()) {
	// ArraySet<Mark> marks = stops.get(line);
	// if (marks != null) Log.debug(this, "Stops %s: %s", line, marks);
	// }
	// }
	//
	// // add mark to marks group within reach or create new group; return the mark
	// private Mark addToMarks(int visCol, int line, int group) {
	// search: for (int at : reach) {
	// int idx = visCol + at;
	// if (idx < 0) continue;
	// Set<Mark> marks = stops.get(idx);
	// if (marks == null) continue; // miss
	//
	// // two groups on the same line cannot be at the same stop
	// for (Mark mark : marks) {
	// if (mark.line == line) break search;
	// }
	// Mark mark = new Mark(visCol, line, group);
	// marks.add(mark);
	// return mark;
	// }
	//
	// Mark mark = new Mark(visCol, line, group);
	// stops.put(visCol, mark);
	// return mark;
	// }
	//
	// private void alignParts() {
	// // key=line; value=last updated group num
	// HashMap<Integer, Integer> updates = new HashMap<>();
	// for (Integer col : stops.keySet()) {
	// ArraySet<Mark> marks = stops.get(col);
	// updateLines(updates, marks);
	// int alignCol = preferred(marks);
	// Log.debug(this, "Aligning on %s (%s)", alignCol, marks);
	//
	// for (Mark mark : marks) {
	// mark.stopCol = alignCol;
	// alignParts(mark);
	// }
	// }
	// }
	//
	// private void updateLines(HashMap<Integer, Integer> updates, ArraySet<Mark> marks) {
	// for (Mark mark : marks) {
	// Integer lastGroup = updates.get(mark.line);
	// if (lastGroup == null) {
	// lastGroup = 0;
	// }
	//
	// for (int idx = lastGroup; idx <= mark.group; idx++) {
	// ArraySet<Part> parts = buffer.get(mark.line);
	// for (int group = 1; group <= mark.group && group < parts.size(); group++) {
	// Part part = parts.get(group);
	// Part prior = parts.get(group - 1);
	// part.visCol = prior.visCol + prior.visLen;
	// }
	// }
	// updates.put(mark.line, mark.group);
	// }
	// }
	//
	// /*
	// * Update intervening parts and extend trailing whitespace of the prior part to reach the desired
	// * inLine column.
	// */
	// private void alignParts(Mark mark) {
	// ArraySet<Part> parts = buffer.get(mark.line);
	// for (int group = 1; group < mark.group; group++) {
	// Part part = parts.get(group);
	// Part prior = parts.get(group - 1);
	// part.visCol = prior.visCol + prior.visLen;
	// }
	// Part part = parts.get(mark.group);
	// Part prior = parts.get(mark.group - 1);
	// part.visCol = mark.stopCol;
	// prior.extendWs(mark.stopCol);
	// }
	//
	// // find the preferred inLine column for the set of marks;
	// private int preferred(ArraySet<Mark> marks) {
	// int minEndCol = 0; // viability limit
	// for (Mark mark : marks) {
	// ArraySet<Part> parts = buffer.get(mark.line);
	// Part part = parts.get(mark.group);
	// minEndCol = Math.max(part.visCol + part.vWidth, minEndCol);
	// Log.debug(this, String.format("MinEndCol %s (%s)", minEndCol, part));
	// }
	//
	// // 1) the stopCol of the first type, if viable
	// Mark first = marks.get(0);
	// if (first.stopCol > minEndCol) return first.stopCol;
	//
	// // 2) the stopCol shared by the largest set (n>1) of viable marks
	// // key=col; value=popularity
	// Map<Integer, Integer> buckets = new HashMap<>();
	// for (Mark mark : marks) {
	// if (mark.stopCol > minEndCol) {
	// Integer cnt = buckets.get(mark.stopCol);
	// if (cnt == null) {
	// buckets.put(mark.stopCol, 1);
	// } else {
	// buckets.put(mark.stopCol, cnt + 1);
	// }
	// }
	// }
	//
	// int bucket = 0;
	// int count = 0;
	// for (Integer col : buckets.keySet()) {
	// int cnt = buckets.get(col);
	// if (cnt > count) {
	// bucket = col;
	// count = cnt;
	// }
	// }
	// if (count > 1) return bucket;
	//
	// // 3) a tab aligned average of the viable visCols (n > 1)
	// int num = 0;
	// count = 0;
	// for (Mark mark : marks) {
	// if (mark.stopCol > minEndCol) {
	// num += mark.stopCol;
	// count++;
	// }
	// }
	// if (count > 1) {
	// num = (int) Math.round((double) num / count);
	// if (num % tabWidth == 0) return num;
	// return Strings.measureVisualWidth("\\t", tabWidth, num) + num;
	// }
	//
	// // 4) the stopCol tab aligned greater than the viable limit
	// return Strings.measureVisualWidth("\\t", tabWidth, minEndCol) + minEndCol;
	// }
}
