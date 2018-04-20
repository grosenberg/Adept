package net.certiv.adept.format;

import java.util.List;

import net.certiv.adept.format.plan.Group;
import net.certiv.adept.format.plan.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Strings;

public class AlignProcessor extends AbstractProcessor {

	public AlignProcessor(FormatterOps ops) {
		super(ops);
	}

	public void alignFields() {
		for (Group group : ops.data.groupIndex) {
			group.update(ops);

			TableMultilist<Scheme, Integer, AdeptToken> members = group.getMembers();
			for (Scheme align : members.keySet()) {
				TreeMultilist<Integer, AdeptToken> lines = members.get(align);
				switch (align) {
					case PAIR:
						handlePairAlign(lines);
						break;

					case LIST:
						handleListAlign(lines, false);
						break;

					case GROUP:
						handleListAlign(lines, false);
						break;

					default:
						break;
				}
			}
		}
	}

	/** Align on common nearest, non-overlapping tab stop. */
	public void alignComments() {
		for (Group group : ops.data.groupIndex) {
			group.update(ops);

			TreeMultilist<Integer, AdeptToken> lines = group.get(Scheme.COMMENT);
			if (lines != null) {
				handleListAlign(lines, true);
			}
		}
	}

	/**
	 * Align on common nearest minimum, non-overlapping position.
	 * <p>
	 * TODO: for a single line pair, need to consider the prior line to determine alignment cols.
	 */
	private void handlePairAlign(TreeMultilist<Integer, AdeptToken> lines) {
		if (lines.isEmpty()) return;
		if (lines.size() > 1) {
			handleListAlign(lines, false);
			return;
		}

		int modLine = lines.lastKey();
		for (AdeptToken token : lines.get(modLine)) {
			int rem = token.visCol() % ops.settings.tabWidth;
			if (rem > 0) {
				int toVisCol = token.visCol() + ops.settings.tabWidth - rem;
				ops.createEditAndShiftLine(modLine, token, toVisCol);
			}
		}
	}

	private void handleListAlign(TreeMultilist<Integer, AdeptToken> alignables, boolean tabAlignFirst) {
		int colsToAlign = countMaxCols(alignables);

		for (int col = 0; col < colsToAlign; col++) {
			int alignCol = 0;
			if (col == 0 && tabAlignFirst) {
				alignCol = tabColAlign(alignables, col);
			} else {
				alignCol = minColAlign(alignables, col);
			}

			for (int lineNum : alignables.keySet()) {
				List<AdeptToken> tokens = alignables.get(lineNum);
				if (tokens.size() > col) {
					AdeptToken alignToken = tokens.get(col);
					if (alignCol != alignToken.visCol()) {
						ops.createEditAndShiftLine(lineNum, alignToken, alignCol);
					}
				}
			}
		}
	}

	private int tabColAlign(TreeMultilist<Integer, AdeptToken> alignables, int col) {
		int alignCol = 0;
		for (int line : alignables.keySet()) {
			List<AdeptToken> alignTokens = alignables.get(line);
			if (alignTokens.size() > col) {
				AdeptToken alignable = alignTokens.get(col);
				int idx = ops.findInModLine(line, alignable);
				if (idx == -1) continue;
				if (idx == 0) {
					alignCol = alignCol > 0 ? alignCol : alignable.visCol();
				} else {
					int tabCol = Strings.nearestTabCol(alignable.visCol(), ops.settings.tabWidth);
					AdeptToken prior = ops.findInModLine(line, idx - 1);
					if (tabCol <= prior.visCol() + prior.getText().length() + 1) {
						tabCol += ops.settings.tabWidth;
					}
					alignCol = Math.max(alignCol, tabCol);
				}
			}
		}
		return alignCol;
	}

	private int minColAlign(TreeMultilist<Integer, AdeptToken> alignables, int col) {
		int alignCol = 0;
		for (int line : alignables.keySet()) {
			List<AdeptToken> alignTokens = alignables.get(line);
			if (alignTokens.size() > col) {
				AdeptToken alignable = alignTokens.get(col);
				int idx = ops.findInModLine(line, alignable);
				if (idx == -1) continue;
				if (idx == 0) {
					alignCol = alignCol > 0 ? alignCol : alignable.visCol();
				} else {
					AdeptToken prior = ops.findInModLine(line, idx - 1);
					int minCol = prior.visCol() + prior.getText().length();
					if (ops.findSpacingLeft(alignable) != Spacing.NONE) minCol++;
					alignCol = Math.max(alignCol, minCol);
				}
			}
		}
		return alignCol;
	}

	private int countMaxCols(TreeMultilist<Integer, AdeptToken> alignables) {
		int max = 0;
		for (Integer num : alignables.keySet()) {
			max = Math.max(max, alignables.get(num).size());
		}
		return max;
	}
}
