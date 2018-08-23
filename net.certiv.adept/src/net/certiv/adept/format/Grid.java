package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Grid {

	private FormatterOps ops;
	private int first;
	private int lcnt;
	private List<Col> grid = new ArrayList<>();

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
	}

	public Grid(FormatterOps ops, TreeMultilist<Integer, AdeptToken> lines) {
		this.ops = ops;
		this.first = lines.firstKey();
		this.lcnt = lines.size();
		build(lines);
	}

	private void build(TreeMultilist<Integer, AdeptToken> lines) {
		for (Integer row : lines.keySet()) {
			List<AdeptToken> tokens = lines.get(row);
			addRow(row, tokens);
		}
	}

	private void addRow(int row, List<AdeptToken> tokens) {
		int at = 0;
		for (int idx = 0; idx < tokens.size(); idx++) {
			String mark = tokens.get(idx).getText();
			at = findCol(at, mark);
			Col col = grid.get(at);
			col.add(row, tokens.get(idx));
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
