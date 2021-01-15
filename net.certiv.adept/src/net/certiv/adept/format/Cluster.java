package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.store.TreeMultilist;
import net.certiv.adept.util.Strings;

/**
 * <pre>
 * <code>
 *	 IntegerLiteral
 *		:	('0' | [1-9] ( Digits? | '_'+ Digits )) [lL]?				// decimal
 *		|	'0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?		// hex
 *		|	'0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?						// octal
 *		|	'0' [bB] [01] ([01_]* [01])? [lL]?							// binary
 *		;
 * </code>
 * </pre>
 */
public class Cluster {

	private final FormatterOps ops;
	private final TreeMultilist<Integer, AdeptToken> lines;

	public Cluster(FormatterOps ops, TreeMultilist<Integer, AdeptToken> lines) {
		this.ops = ops;
		this.lines = lines;
	}

	public void eval(boolean tabFirst, int limit) {
		TreeSet<AdeptToken> marks = new TreeSet<>(AdeptToken.CompVPos);
		marks.addAll(lines.valuesAll());

		while (!marks.isEmpty()) {
			List<AdeptToken> related = collect(marks, limit);
			marks.removeAll(related);

			int alignCol = minCol(related);
			if (tabFirst) {
				int tw = ops.settings.tabWidth;
				int rem = alignCol % tw;
				if (rem > 0) alignCol += tw - rem;
				tabFirst = false;
			}

			for (AdeptToken token : related) {
				ops.prepEditAndShiftLine(token.getLinePos(), token, alignCol);
			}
		}
	}

	private List<AdeptToken> collect(TreeSet<AdeptToken> marks, int limit) {
		List<AdeptToken> related = new ArrayList<>();
		AdeptToken first = marks.first();
		int margin = define(first, limit);

		List<Integer> lnIdx = new ArrayList<>();
		int vpos = first.getVisPos();
		for (AdeptToken near : marks) {
			if (near.getVisPos() - vpos >= margin) break;
			if (!lnIdx.contains(near.getLinePos())) {
				lnIdx.add(near.getLinePos());
				related.add(near);
			}
		}
		return related;
	}

	private int define(AdeptToken mark, int limit) {
		List<AdeptToken> line = lines.get(mark.getLinePos());
		int idx = line.indexOf(mark);
		AdeptToken next = null;
		try {
			next = line.get(idx + 1);
		} catch (IndexOutOfBoundsException e) {
			return limit;
		}

		if (mark.getType() != next.getType()) return limit;
		return Math.min(limit, next.getVisPos() - mark.getVisPos());
	}

	// minimum next visual tab-aligned column for the given tokens
	private int minTabCol(List<AdeptToken> tokens) {
		return Strings.nextTabCol(minCol(tokens), ops.settings.tabWidth);
	}

	// minimum visual column for the given tokens
	private int minCol(List<AdeptToken> tokens) {
		int col = -1;
		for (AdeptToken token : tokens) {
			col = Math.max(col, minPos(token));
		}
		return col;
	}

	// minimum visual position of the given token
	private int minPos(AdeptToken token) {
		int lnum = ops.tokenLineIndex.get(token.getTokenIndex());
		AdeptToken prior = ops.priorInLine(lnum, token);
		if (prior != null) {
			return prior.getVisPos() + prior.getText().length() + 1;
		}
		return token.dent().indents * ops.settings.tabWidth;
	}
}
