/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.List;

import net.certiv.adept.format.plan.Group;
import net.certiv.adept.format.plan.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Maths;
import net.certiv.adept.util.Strings;

/**
 * Aligns identified source elements: fields and comments. Alignment results are realized as edits
 * left of the aligned tokens. Updates the {@code visCol} of the aligned element and that of all
 * real tokens to the right on the same line.
 * <p>
 * Depends on the {@code visCol} comment token value being accurate and on the formatter line
 * indexes.
 */
public class AlignProcessor extends AbstractProcessor {

	public AlignProcessor(FormatterOps ops) {
		super(ops);
	}

	public void alignFields() {
		ops.buildLinesIndexes();
		ops.collectSerials();

		for (Group group : ops.rec.groupIndex) {
			TableMultilist<Scheme, Integer, AdeptToken> members = group.getMembers();
			for (Scheme align : members.keySet()) {
				TreeMultilist<Integer, AdeptToken> lines = members.get(align);
				lines = update(lines);
				switch (align) {
					case PAIR:
						pairAlign(lines);
						break;

					case LIST:
						listAlign(lines, false);
						break;

					case GROUP:
						listAlign(lines, false);
						break;

					case SERIAL:
						gridAlign(lines, false);
						break;

					default:
						break;
				}
			}
		}
	}

	/** Align on common nearest, non-overlapping tab stop. */
	public void alignComments() {
		ops.buildLinesIndexes();

		for (Group group : ops.rec.groupIndex) {
			TreeMultilist<Integer, AdeptToken> lines = group.get(Scheme.COMMENT);
			if (lines != null) {
				lines = update(lines);
				listAlign(lines, true);
			}
		}
	}

	/**
	 * Align on common nearest minimum, non-overlapping position.
	 * <p>
	 * For an isolated single line pair, do nothing. Where affiliated with other single line pairs, need
	 * to consider the sequential set of lines to determine alignment cols.
	 */
	private void pairAlign(TreeMultilist<Integer, AdeptToken> lines) {
		if (lines.isEmpty()) return;
		if (lines.size() > 1) {
			listAlign(lines, false);
		}
	}

	private void listAlign(TreeMultilist<Integer, AdeptToken> lines, boolean tabFirst) {
		int colsToAlign = countMaxCols(lines);

		for (int col = 0; col < colsToAlign; col++) {
			int alignCol = 0;
			if (col == 0 && tabFirst) {
				alignCol = minTabCol(lines, col);
			} else {
				alignCol = minCol(lines, col);
			}

			for (int lineNum : lines.keySet()) {
				List<AdeptToken> tokens = lines.get(lineNum);
				if (tokens.size() > col) {
					AdeptToken alignToken = tokens.get(col);
					if (alignCol != alignToken.getVisPos()) {
						ops.prepEditAndShiftLine(lineNum, alignToken, alignCol);
					}
				}
			}
		}
	}

	private void gridAlign(TreeMultilist<Integer, AdeptToken> lines, boolean tabFirst) {
		Grid grid = new Grid(ops, lines);

		for (int col = 0; col < grid.size(); col++) {
			int alignCol = 0;
			if (col == 0 && tabFirst) {
				alignCol = grid.minTabCol(col);
			} else {
				alignCol = grid.minCol(col);
			}

			for (int lnum : lines.keySet()) {
				if (!grid.isEmpty(col, lnum)) {
					AdeptToken token = grid.get(col, lnum);
					boolean mid = col != 0 && col != grid.size() - 1;
					int excess = Maths.delta(alignCol, token.getVisPos());
					if (!mid || (mid && excess <= ops.settings.tabWidth)) {
						ops.prepEditAndShiftLine(lnum, token, alignCol);
					}
				}
			}
		}
	}

	private int minTabCol(TreeMultilist<Integer, AdeptToken> alignables, int col) {
		int min = minCol(alignables, col);
		return Strings.nextTabCol(min, ops.settings.tabWidth);
	}

	private int minCol(TreeMultilist<Integer, AdeptToken> alignables, int col) {
		int min = 0;
		for (int line : alignables.keySet()) {
			List<AdeptToken> alignTokens = alignables.get(line);
			if (alignTokens.size() > col) {
				AdeptToken alignable = alignTokens.get(col);
				AdeptToken prior = ops.priorInLine(line, alignable);

				if (prior != null) {
					min = Math.max(min, prior.getVisPos() + prior.getText().length() + 1);
				} else {
					min = Math.max(min, alignable.dent().indents * ops.settings.tabWidth);
				}
			}
		}
		return min;
	}

	private int countMaxCols(TreeMultilist<Integer, AdeptToken> alignables) {
		int max = 0;
		for (Integer num : alignables.keySet()) {
			max = Math.max(max, alignables.get(num).size());
		}
		return max;
	}

	// Update line numers to account for edits
	private TreeMultilist<Integer, AdeptToken> update(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> updated = new TreeMultilist<>();
		// updated.setValueComparator(AdeptComp.Instance);
		for (AdeptToken token : lines.valuesAll()) {
			Integer modLine = ops.tokenLineIndex.get(token.getTokenIndex());
			updated.put(modLine, token);
		}
		return updated;
	}
}
