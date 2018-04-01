package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/** Document stream formatter. */
public class Formatter {

	private Document doc;
	private ParseRecord data;
	private Settings settings;
	private String tabEquiv;

	// the source text to format
	private StringBuilder contents;

	public Formatter(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.tabEquiv = Strings.getNSpaces(settings.tabWidth);

		contents = new StringBuilder();
	}

	// -------------------------------------------------------------------------

	public boolean execute() {
		List<TextEdit> edits = createEdits();
		if (edits == null) return false;

		return applyEdits(edits);
	}

	public List<TextEdit> createEdits() {
		List<TextEdit> results = new ArrayList<>();
		Space space = new Space();

		for (AdeptToken token : data.index.keySet()) {
			RefToken present = fromFeature(token.getTokenIndex());

			if (present != null) {
				RefToken matched = present.matched;

				if (matched != null) {
					RefToken prior = fromFeature(present.lIndex);
					RefToken next = fromFeature(present.rIndex);

					try {
						Map<Region, TextEdit> edits = createEdits(prior, present, next, matched);
						if (!edits.isEmpty()) {
							space.add(edits);
							results.addAll(edits.values());
						}
					} catch (FormatException e) {
						Log.error(this, "Formatter Err: " + e.getMessage());
					} catch (RegionException e) {
						Log.error(this, "Region Err: " + e.getMessage());
					} catch (Exception e) {
						Log.error(this, "Unexpected Err: " + e.getMessage(), e);
					}
				}
			}
		}

		space.dispose();
		return results;

	}

	public boolean applyEdits(List<TextEdit> edits) {
		Map<Integer, TextEdit> editSet = new HashMap<>();
		for (TextEdit edit : edits) {
			editSet.put(edit.leftIndex(), edit);
		}

		List<AdeptToken> tokens = data.getTokens();
		for (int idx = 0, len = tokens.size() - 1; idx < len;) {
			AdeptToken token = tokens.get(idx);
			contents.append(token.getText());
			TextEdit edit = editSet.get(token.getTokenIndex());
			if (edit != null) {
				contents.append(edit.replacement());
				idx = edit.rightIndex();
			} else {
				idx++;
			}
		}

		doc.setModified(contents.toString());
		return true;
	}

	public String result() {
		return contents.toString();
	}

	// -------------------------------------------------------------------------

	// converts a token index to a known good feature ref token
	private RefToken fromFeature(int index) {
		AdeptToken token = index > -1 ? data.tokenIndex.get(index) : null;
		Feature feature = token != null ? data.index.get(token) : null;
		return feature != null ? feature.getRefFor(token.getTokenIndex()) : null;
	}

	/**
	 * Create the edits implementing the consequences of the matched RefTokens.
	 * <p>
	 * Edit conditions are prioritized: {@code <1>...<n>} (low to high)
	 * <ol>
	 * <li>impose current matched spacing on left edge of current
	 * <li>impose current matched spacing on right edge of current
	 * <li>when current is comment, impose current matched spacing on the left edge of current
	 * <li>when current (existing or matched) is Bol, impose current matched spacing/dent (blank lines,
	 * new line, and indent) on left edge of current (retain initial, i.e., prior line trailing, HWs
	 * depending on settings)
	 * <li>TODO: aligns
	 * <li>TODO: long-line breaks
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
	 * @param matched for existing current RefToken
	 * @return list of implementing edits
	 * @throws FormatException
	 */
	private Map<Region, TextEdit> createEdits(RefToken prior, RefToken current, RefToken next, RefToken matched)
			throws FormatException {

		Map<Region, TextEdit> edits = new HashMap<>();

		// priority 1: left nominal
		if (current.lIndex > AdeptToken.BOF) {
			String repl = evalSpacing(matched.lSpacing, matched.lActual);
			if (!repl.equals(current.lActual)) {
				TextEdit edit = new TextEdit(current.lIndex, current.index, repl);
				edits.put(edit.getRegion(), edit);
			}
		}

		// priority 2: right nominal
		if (current.index > AdeptToken.EOF) {
			String repl = evalSpacing(matched.rSpacing, matched.rActual);
			if (!repl.equals(current.rActual)) {
				TextEdit edit = new TextEdit(current.index, current.rIndex, repl);
				edits.put(edit.getRegion(), edit);
			}
		}

		// priority 3: comments
		if ((prior != null && prior.isComment()) || current.isComment()) {
			String repl = evalSpacing(matched.lSpacing, matched.lActual);
			if (!repl.equals(current.lActual)) {
				TextEdit edit = new TextEdit(current.lIndex, current.index, repl);
				edits.put(edit.getRegion(), edit);
			}
		}

		// priority 4: bol
		if (matched.atBol() || current.atBol()) {
			String repl = evalIndent(current.lActual, matched.lActual, current.dent.indents);
			if (!repl.equals(current.lActual)) {
				TextEdit edit = new TextEdit(current.lIndex, current.index, repl);
				edits.put(edit.getRegion(), edit);
			}
		}

		return edits;
	}

	private String evalSpacing(Spacing spacing, String existing) {
		switch (spacing) {
			case HFLEX:
				return horzFlex(existing);
			case HSPACE:
				return Strings.SPACE;
			case NONE:
				return "";
			case VLINE:
				return Strings.EOL;
			case VFLEX:
				return vertFlex(existing);
			default:
				return existing;
		}
	}

	// produce replacement indent string including any leading newlines
	private String evalIndent(String existing, String matched, int indents) {
		String indentStr = "";
		if (!settings.removeTrailingWS) {
			indentStr += Strings.leadHWS(existing);
		}
		int lines = Math.max(vertCount(existing), vertCount(matched));
		indentStr += Strings.getN(lines, Strings.EOL);
		indentStr += Strings.createIndent(settings.tabWidth, settings.useTabs, indents);
		return indentStr;
	}

	private int vertCount(String text) {
		int lines = Strings.countLines(text);
		if (settings.removeBlankLines) {
			lines = Math.min(lines, settings.keepBlankLines + 1);
		}
		return lines;
	}

	private String vertFlex(String text) {
		int lines = vertCount(text);
		return Strings.getN(lines, Strings.EOL);
	}

	private String horzFlex(String actual) {
		String result = actual.replace(tabEquiv, "\t");
		return result.replaceAll("\\t[ ]+(?=\\t)", "\t");
	}
}
