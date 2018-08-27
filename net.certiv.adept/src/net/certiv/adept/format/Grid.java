package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Log;
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
 *
 *
 *	| '(' | '|' | '(' | '|' | ')' | ')' |
 *	| '(' |     |     |     | ')' |     |
 *	| '(' |     |     |     | ')' |     |
 *	| '(' |     |     |     | ')' |     |
 *
 *
 *
 * </code>
 * </pre>
 */
public class Grid {

	private FormatterOps ops;
	private int first;
	private int lcnt;
	private final List<Col> grid = new ArrayList<>();

	private class Col {

		// the identifying 'mark' for this column
		final String mark;

		// key=row; value=token
		final Map<Integer, AdeptToken> cells = new HashMap<>();

		Col(String mark) {
			this.mark = mark;
		}

		void add(int row, AdeptToken token) {
			cells.put(row, token);
		}

		@Override
		public String toString() {
			return String.format("Col [mark=%s, cells=%s]", mark, cells);
		}
	}

	public Grid(FormatterOps ops, TreeMultilist<Integer, AdeptToken> lines) {
		this.ops = ops;
		this.first = lines.firstKey();
		this.lcnt = lines.size();
		build(lines);
		// populate(lines.keySet());
	}

	// -----
	//
	// @SuppressWarnings("unused")
	// private void populate(Set<Integer> lnum) {
	// for (Integer num : lnum) {
	// List<AdeptToken> tokens = ops.lineTokensIndex.get(num);
	// mkRow(num, tokens);
	// }
	// }
	//
	// private void mkRow(int row, List<AdeptToken> tokens) {
	// for (int idx = 0; idx < tokens.size(); idx++) {
	// AdeptToken token = tokens.get(idx);
	// put(idx, row, token);
	// }
	// }
	//
	// private void put(int colnum, int row, AdeptToken token) {
	// Col col = grid.get(colnum);
	// if (col == null) {
	// col = new Col(null);
	// grid.add(col);
	// }
	// col.add(row, token);
	// }
	//
	// -----

	private void build(TreeMultilist<Integer, AdeptToken> lines) {
		for (Integer row : lines.keySet()) {
			List<AdeptToken> tokens = lines.get(row);
			addRow(row, tokens);
		}
	}

	private void addRow(int row, List<AdeptToken> tokens) {
		int at = 0;
		for (int idx = 0; idx < tokens.size(); idx++) {
			AdeptToken token = tokens.get(idx);
			String mark = token.getText();
			at = findCol(at, mark);
			Col col = grid.get(at);
			col.add(row, token);
			at++;
		}
	}

	private int findCol(int at, String mark) {
		for (int cnum = at; cnum < grid.size(); cnum++) {
			if (grid.get(cnum).mark.equals(mark)) return cnum;
		}
		grid.add(new Col(mark));
		return grid.size() - 1;
	}

	public AdeptToken get(int cIdx, int row) {
		Col col = grid.get(cIdx);
		return col.cells.get(row);
	}

	public int minTabCol(int cIdx) {
		return Strings.nextTabCol(minCol(cIdx), ops.settings.tabWidth);
	}

	public int minCol(int cIdx) {
		int min = 0;
		Col col = grid.get(cIdx);

		for (int row : col.cells.keySet()) {
			AdeptToken token = col.cells.get(row);
			AdeptToken prior = ops.priorInLine(row, token);

			if (prior != null) {
				min = Math.max(min, prior.getVisPos() + prior.getText().length() + 1);
			} else {
				min = Math.max(min, token.dent().indents * ops.settings.tabWidth);
			}
		}
		return min;
	}

	public boolean isEmpty(int cIdx, int row) {
		return get(cIdx, row) == null;
	}

	/** Returns the number of grid columns. */
	public int size() {
		return grid.size();
	}

	public void debug() {
		StringBuilder sb = new StringBuilder("|");
		for (int idx = 0; idx < size(); idx++) {
			sb.append(String.format(" %3s |", minCol(idx)));
		}
		Log.debug(this, sb.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Strings.EOL);
		for (int row = first; row < first + lcnt; row++) {
			sb.append("|");
			for (Col col : grid) {
				AdeptToken token = col.cells.get(row);
				String txt = (token != null) ? token.getText() : "";
				String msg = !txt.isEmpty() ? " '%1s' |" : " %3s |";
				sb.append(String.format(msg, txt));
			}
			sb.append(Strings.EOL);
		}
		return sb.toString();
	}
}
