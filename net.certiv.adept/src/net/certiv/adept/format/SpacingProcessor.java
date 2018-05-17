package net.certiv.adept.format;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class SpacingProcessor extends AbstractProcessor {

	public SpacingProcessor(FormatterOps ops) {
		super(ops);
	}

	// consider prior right match and current left match
	// favor left over right
	public void formatWhiteSpace() {
		ops.buildLinesIndexes();

		for (AdeptToken token : ops.data.index.keySet()) { // real tokens
			RefToken current = token.refToken();

			if (current.matched != null) {
				TextEdit edit = createEditLeft(current);
				if (edit != null) {
					ops.edits.put(edit.getRegion(), edit);
				}

			} else {
				RefToken prior = ops.data.getTokenRef(current.lIndex);
				if (prior != null && prior.matched != null) {
					TextEdit edit = createEditRight(prior);
					if (edit != null) {
						ops.edits.put(edit.getRegion(), edit);
					}
				}
			}
		}
	}

	private TextEdit createEditLeft(RefToken ref) {
		TextEdit edit = null;

		if (ref.lIndex > AdeptToken.BOF && (ref.matched.atBol() || ref.atBol())) {
			String repl = calcIndent(ref.lActual, ref.matched.lActual, ref.dent.indents);
			edit = ops.createEdit(ref.lIndex, ref.index, ref.lActual, repl, 4, "L Bol");

		} else if (ref.lIndex > AdeptToken.BOF) {
			String repl = calcSpacing(ref.matched.lSpacing, ref.matched.lActual, ref.matched.dent.indents);
			edit = ops.createEdit(ref.lIndex, ref.index, ref.lActual, repl, 1, "L Nom");
		}

		return edit;
	}

	private TextEdit createEditRight(RefToken ref) {
		if (ref.index > AdeptToken.EOF) {
			String repl = calcSpacing(ref.matched.rSpacing, ref.matched.rActual, ref.matched.dent.indents);
			return ops.createEdit(ref.index, ref.rIndex, ref.rActual, repl, 2, "R Nom");
		}

		return null;
	}

	// given desired spacing and existing ws, return replacement string
	protected String calcSpacing(Spacing spacing, String existing, int indents) {
		switch (spacing) {
			case HFLEX:
				String hflex = existing.replace(ops.tabEquiv, "\t");
				return hflex.replaceAll("\\t[ ]+(?=\\t)", "\t");

			case HSPACE:
				return Strings.SPACE;

			case NONE:
				return "";

			case VLINE:
				return Strings.EOL + Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);

			case VFLEX:
				String vflex = Strings.getN(adjVertSpacing(existing), Strings.EOL);
				return vflex + Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);

			default:
				return existing;
		}
	}

	// produce replacement indent string including any leading newlines for BOL
	protected String calcIndent(String existing, String matched, int indents) {
		String indentStr = "";
		if (!ops.settings.removeTrailingWS) {
			indentStr += Strings.leadHWs(existing);
		}
		int eline = adjVertSpacing(existing);
		int mline = adjVertSpacing(matched);
		int lines = Math.max(eline, mline);
		indentStr += Strings.getN(lines, Strings.EOL);
		indentStr += Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);
		return indentStr;
	}

	protected int adjVertSpacing(String text) {
		int lines = Strings.countVWS(text);
		if (ops.settings.removeBlankLines) {
			lines = Math.min(lines, ops.settings.keepNumBlankLines + 1);
		}
		return lines;
	}

	protected void showWhere(RefToken prior, RefToken present, RefToken matched, RefToken next) {
		String pname = prior != null ? ops.data.getTokenName(prior.type) : "{Nil}";
		String nname = next != null ? ops.data.getTokenName(next.type) : "{Nil}";
		String cname = ops.data.getTokenName(present.type);
		cname += matched != null ? "+" : "-";

		Log.debug(this, "   %s > %s > %s", pname, cname, nname);
	}

	// public void formatWhiteSpaceX() {
	// ops.buildLinesIndexes();
	//
	// for (AdeptToken token : ops.data.index.keySet()) { // real tokens
	// RefToken present = token.refToken();
	// if (present == null) {
	// Log.error(this, "Ref is null for " + token.toString());
	// continue;
	// }
	// RefToken prior = ops.data.getTokenRef(present.lIndex);
	// RefToken next = ops.data.getTokenRef(present.rIndex);
	// RefToken matched = present.matched;
	// showWhere(prior, present, matched, next);
	//
	// if (matched != null) {
	// try {
	// Map<Region, TextEdit> editSet = adjustLineSpacing(prior, present, next, matched);
	// if (!editSet.isEmpty()) {
	// for (Entry<Region, TextEdit> entry : editSet.entrySet()) {
	// Region region = entry.getKey();
	// TextEdit edit = entry.getValue();
	//
	// boolean exists = false;
	// try {
	// exists = ops.edits.containsKey(region);
	// } catch (RegionException e) {
	// Log.error(this, "Region Err: " + e.getMessage());
	// }
	//
	// if (exists) {
	// TextEdit existing = ops.edits.get(region);
	// if (existing.priority() > edit.priority()) {
	// Log.debug(this, "P: " + existing.toString()); // priority/presesrve
	// } else {
	// Log.debug(this, "R: " + edit.toString()); // remove/replace
	// if (edit.existing().equals(edit.replacement())) {
	// ops.edits.remove(region);
	// } else {
	// ops.edits.put(region, edit);
	// }
	// }
	// } else {
	// if (!edit.existing().equals(edit.replacement())) {
	// Log.debug(this, "A: " + edit.toString()); // add
	// ops.edits.put(region, edit);
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// Log.error(this, "Unexpected Err: " + e.getMessage(), e);
	// }
	// }
	// }
	// }
	//
	// /**
	// * Create edits implementing the consequences of a matched RefToken.
	// * <p>
	// * Resulting edit(s) are prioritized: {@code <1>...<n>} (low to high)
	// * <ol>
	// * <li>impose current matched spacing on left edge of current
	// * <li>impose current matched spacing on right edge of current
	// * <li>when current is comment, impose current matched spacing & dent on left edge of current
	// * <li>when current actual or matched is BOL, apply matched spacing & dent on left edge of current
	// * </ol>
	// *
	// * <pre>
	// * ------|--------|--------|-------
	// *
	// * ... p.Idx p.rIdx
	// * c.lIdx c.Idx c.rIdx
	// * n.lIdx n.Idx ...
	// * </pre>
	// *
	// * @param prior existing prior RefToken
	// * @param current existing current RefToken
	// * @param next existing next RefToken
	// * @param matched for current RefToken
	// * @return list of implementing edits
	// */
	// private Map<Region, TextEdit> adjustLineSpacing(RefToken prior, RefToken current, RefToken next,
	// RefToken matched) {
	//
	// Map<Region, TextEdit> edits = new HashMap<>();
	//
	// // priority 1: left nominal
	// if (current.lIndex > AdeptToken.BOF) {
	// String repl = calcSpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
	// TextEdit edit = ops.createEdit(current.lIndex, current.index, current.lActual, repl, 1, "L Nom");
	// edits.put(edit.getRegion(), edit);
	// }
	//
	// // priority 2: right nominal
	// if (current.index > AdeptToken.EOF) {
	// String repl = calcSpacing(matched.rSpacing, matched.rActual, matched.dent.indents);
	// TextEdit edit = ops.createEdit(current.index, current.rIndex, current.rActual, repl, 2, "R Nom");
	// edits.put(edit.getRegion(), edit);
	// }
	//
	// // priority 3: comments
	// if ((prior != null && prior.isComment()) || current.isComment()) {
	// String repl = calcSpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
	// TextEdit edit = ops.createEdit(current.lIndex, current.index, current.lActual, repl, 3, "L Cmt");
	// edits.put(edit.getRegion(), edit);
	// }
	//
	// // priority 4: bol
	// if (current.lIndex > AdeptToken.BOF && (matched.atBol() || current.atBol())) {
	// String repl = calcIndent(current.lActual, matched.lActual, current.dent.indents);
	// TextEdit edit = ops.createEdit(current.lIndex, current.index, current.lActual, repl, 4, "L Bol");
	// edits.put(edit.getRegion(), edit);
	// }
	//
	// return edits;
	// }

	// // converts a token index to a known good feature ref token
	// protected RefToken srcRefFor(int index) {
	// AdeptToken token = index > -1 ? ops.data.tokenIndex.get(index) : null;
	// Feature feature = token != null ? ops.data.index.get(token) : null;
	// return feature != null ? feature.getRefFor(token.getTokenIndex()) : null;
	// }
}
