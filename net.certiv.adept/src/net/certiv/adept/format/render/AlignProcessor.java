package net.certiv.adept.format.render;

import java.util.List;

import net.certiv.adept.format.align.Align;
import net.certiv.adept.format.align.Group;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;

public class AlignProcessor {

	private FormatOps ops;

	public AlignProcessor(FormatOps ops) {
		this.ops = ops;
	}

	public void alignFields() {
		for (Group group : ops.data.groupIndex) {
			TableMultilist<Align, Integer, AdeptToken> members = group.getMembers();
			for (Align align : members.keySet()) {
				TreeMultilist<Integer, AdeptToken> lines = members.get(align);
				switch (align) {
					case PAIR:
						handlePairAlign(lines);
						break;

					case LIST:
					case GROUP:
						handleListAlign(lines);
						break;

					default:
						break;
				}
			}
		}
	}

	public void alignComments() {
		for (Group group : ops.data.groupIndex) {
			TreeMultilist<Integer, AdeptToken> lines = group.get(Align.COMMENT);
			if (lines != null) {
				handleListAlign(lines);
			}
		}
	}

	protected void handlePairAlign(TreeMultilist<Integer, AdeptToken> lines) {
		if (lines.isEmpty()) return;

		TreeMultilist<Integer, AdeptToken> modLines = ops.modLines(lines);
		if (modLines.size() > 1) {
			handleListAlign(lines);
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

	protected void handleListAlign(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> modLines = ops.modLines(lines);
		int maxAligns = ops.maxAligns(modLines);
		for (int idx = 0; idx < maxAligns; idx++) {
			int maxVisCol = 0;
			for (int lineNum : modLines.keySet()) {
				List<AdeptToken> tokens = modLines.get(lineNum);
				if (tokens.size() > idx) {
					AdeptToken alignToken = tokens.get(idx);
					maxVisCol = Math.max(maxVisCol, alignToken.visCol());
				}
			}

			for (int lineNum : modLines.keySet()) {
				List<AdeptToken> tokens = modLines.get(lineNum);
				if (tokens.size() > idx) {
					AdeptToken alignToken = tokens.get(idx);
					int adj = maxVisCol - alignToken.visCol();
					if (adj > 0) {
						ops.createEditAndShiftLine(lineNum, alignToken, maxVisCol);
					}
				}
			}
		}
	}
}
