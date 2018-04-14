package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.certiv.adept.ITextEdit;
import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

/** Document formatter. */
public class Formatter {

	private Document doc;
	private ParseRecord data;
	private Settings settings;
	private String tabEquiv;

	private StringBuilder contents;				// the formatted source text
	private TreeMap<Region, TextEdit> edits;	// the implementing edits

	public Formatter(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.tabEquiv = Strings.getNSpaces(settings.tabWidth);

		contents = new StringBuilder();

		TextEdit.init(data);
	}

	// -------------------------------------------------------------------------

	public boolean execute() {
		List<TextEdit> edits = createEdits();
		if (edits == null) return false;

		return applyEdits(edits);
	}

	public List<TextEdit> createEdits() {
		edits = new TreeMap<>();

		for (AdeptToken token : data.index.keySet()) {
			RefToken present = srcRefFor(token.getTokenIndex());

			if (present != null) {
				RefToken prior = srcRefFor(present.lIndex);
				RefToken next = srcRefFor(present.rIndex);
				RefToken matched = present.matched;
				showWhere(prior, next, matched);

				if (matched != null) {
					try {
						Map<Region, TextEdit> editSet = createEdits(prior, present, next, matched);
						if (!editSet.isEmpty()) {
							for (Entry<Region, TextEdit> entry : editSet.entrySet()) {
								Region region = entry.getKey();
								TextEdit edit = entry.getValue();

								if (edits.containsKey(region)) {
									TextEdit existing = edits.get(region);
									if (existing.priority() > edit.priority()) {
										Log.debug(this, "P: " + existing.toString());	// priority/presesrve
									} else {
										Log.debug(this, "R: " + edit.toString());		// remove/replace
										if (edit.existing().equals(edit.replacement())) {
											edits.remove(region);
										} else {
											edits.put(region, edit);
										}
									}
								} else {
									if (!edit.existing().equals(edit.replacement())) {
										Log.debug(this, "A: " + edit.toString());		// add
										edits.put(region, edit);
									}
								}
							}
						}
					} catch (FormatException e) {
						Log.error(this, "Formatter Err: " + e.getMessage());
					} catch (Exception e) {
						Log.error(this, "Unexpected Err: " + e.getMessage(), e);
					}
				}
			}
		}

		return new ArrayList<>(edits.values());
	}

	private void showWhere(RefToken prior, RefToken next, RefToken matched) {
		String pname = prior != null ? data.getTokenName(prior.type) : "Null";
		String mname = matched != null ? data.getTokenName(matched.type) : "Null";
		String nname = next != null ? data.getTokenName(next.type) : "Null";
		Log.debug(this, "   %s > %s >%s", pname, mname, nname);
	}

	public boolean applyEdits(List<TextEdit> edits) {
		Map<Integer, TextEdit> editSet = new HashMap<>();
		for (TextEdit edit : edits) {
			editSet.put(edit.begIndex(), edit);
		}

		List<AdeptToken> tokens = data.getTokens();
		for (int idx = 0, len = tokens.size() - 1; idx < len;) {
			AdeptToken token = tokens.get(idx);
			contents.append(token.getText());
			TextEdit edit = editSet.get(token.getTokenIndex());
			if (edit != null) {
				contents.append(edit.replacement());
				idx = edit.endIndex();
			} else {
				idx++;
			}
		}

		doc.setModified(contents.toString());
		return true;
	}

	public List<ITextEdit> getTextEdits() {
		return new ArrayList<>(edits.values());
	}

	public String getFormattedContents() {
		return contents.toString();
	}

	// -------------------------------------------------------------------------

	// converts a token index to a known good feature ref token
	private RefToken srcRefFor(int index) {
		AdeptToken token = index > -1 ? data.tokenIndex.get(index) : null;
		Feature feature = token != null ? data.index.get(token) : null;
		return feature != null ? feature.getRefFor(token.getTokenIndex()) : null;
	}

	/**
	 * Create edits implementing the consequences of a matched RefToken.
	 * <p>
	 * Resulting edit(s) are prioritized: {@code <1>...<n>} (low to high)
	 * <ol>
	 * <li>impose current matched spacing on left edge of current
	 * <li>impose current matched spacing on right edge of current
	 * <li>when current is comment, impose current matched spacing & dent on left edge of current
	 * <li>when current actual or matched is BOL, apply matched spacing & dent on left edge of current
	 * </ol>
	 *
	 * <pre>
	 * ------|--------|--------|-------
	 *
	 * ... p.Idx    p.rIdx
	 *     c.lIdx   c.Idx    c.rIdx
	 *              n.lIdx   n.Idx  ...
	 * </pre>
	 *
	 * @param prior existing prior RefToken
	 * @param current existing current RefToken
	 * @param next existing next RefToken
	 * @param matched for current RefToken
	 * @return list of implementing edits
	 * @throws FormatException
	 */
	private Map<Region, TextEdit> createEdits(RefToken prior, RefToken current, RefToken next, RefToken matched)
			throws FormatException {

		Map<Region, TextEdit> edits = new HashMap<>();

		// priority 1: left nominal
		if (current.lIndex > AdeptToken.BOF) {
			String repl = applySpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
			TextEdit edit = TextEdit.create(current.lIndex, current.index, current.lActual, repl, 1, "L Nom");
			edits.put(edit.getRegion(), edit);
		}

		// priority 2: right nominal
		if (current.index > AdeptToken.EOF) {
			String repl = applySpacing(matched.rSpacing, matched.rActual, matched.dent.indents);
			TextEdit edit = TextEdit.create(current.index, current.rIndex, current.rActual, repl, 2, "R Nom");
			edits.put(edit.getRegion(), edit);
		}

		// priority 3: comments
		if ((prior != null && prior.isComment()) || current.isComment()) {
			String repl = applySpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
			TextEdit edit = TextEdit.create(current.lIndex, current.index, current.lActual, repl, 3, "L Cmt");
			edits.put(edit.getRegion(), edit);
		}

		// priority 4: bol
		if (current.lIndex > AdeptToken.BOF && (matched.atBol() || current.atBol())) {
			String repl = applyBolIndent(current.lActual, matched.lActual, current.dent.indents);
			TextEdit edit = TextEdit.create(current.lIndex, current.index, current.lActual, repl, 4, "L Bol");
			edits.put(edit.getRegion(), edit);
		}

		return edits;
	}

	private String applySpacing(Spacing spacing, String existing, int indents) {
		switch (spacing) {
			case HFLEX:				// TODO: visCol?
				String hflex = existing.replace(tabEquiv, "\t");
				return hflex.replaceAll("\\t[ ]+(?=\\t)", "\t");

			case HSPACE:
				return Strings.SPACE;

			case NONE:
				return "";

			case VLINE:
				return Strings.EOL + Strings.createIndent(settings.tabWidth, settings.useTabs, indents);

			case VFLEX:
				String vflex = Strings.getN(vertCount(existing), Strings.EOL);
				return vflex + Strings.createIndent(settings.tabWidth, settings.useTabs, indents);

			default:
				return existing;
		}
	}

	// produce replacement indent string including any leading newlines for BOL
	private String applyBolIndent(String existing, String matched, int indents) {
		String indentStr = "";
		if (!settings.removeTrailingWS) {
			indentStr += Strings.leadHWS(existing);
		}
		int eline = vertCount(existing);
		int mline = vertCount(matched);
		int lines = Math.max(eline, mline);
		indentStr += Strings.getN(lines, Strings.EOL);
		indentStr += Strings.createIndent(settings.tabWidth, settings.useTabs, indents);
		return indentStr;
	}

	private int vertCount(String text) {
		int lines = Strings.countVWS(text);
		if (settings.removeBlankLines) {
			lines = Math.min(lines, settings.keepBlankLines + 1);
		}
		return lines;
	}
}
