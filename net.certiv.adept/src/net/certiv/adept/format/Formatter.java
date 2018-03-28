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
	private String spTab;

	// the source text to format
	private StringBuilder contents;

	public Formatter(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.spTab = Strings.getNSpaces(settings.tabWidth);

		contents = new StringBuilder(data.getDocument().getContent());
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
						List<TextEdit> edits = createEdits(prior, present, next, matched);
						space.addAll(edits);
						results.addAll(edits);
					} catch (FormatException e) {
						Log.error(this, "Formatter Err: " + e.getMessage(), e);
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
			editSet.put(edit.left(), edit);
		}

		List<AdeptToken> tokens = data.getTokens();
		for (int idx = 0, len = tokens.size(); idx < len;) {
			AdeptToken token = tokens.get(idx);
			contents.append(token.getText());
			TextEdit edit = editSet.get(token.getTokenIndex());
			if (edit != null) {
				contents.append(edit.replacement());
				idx = edit.right();
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

	// translate a token index to a known good feature ref token
	private RefToken fromFeature(int index) {
		AdeptToken token = index > -1 ? data.tokenIndex.get(index) : null;
		Feature feature = token != null ? data.index.get(token) : null;
		return feature != null ? feature.getRefFor(token.getTokenIndex()) : null;
	}

	/**
	 * <pre>
	 * ------|--------|--------|-------
	 *
	 * ... b.Idx    b.rIdx
	 *     c.lIdx   c.Idx    c.rIdx
	 *              a.lIdx   a.Idx  ...
	 * </pre>
	 *
	 * TODO: aligns <br>
	 * TODO: line breaks
	 */
	private List<TextEdit> createEdits(RefToken prior, RefToken now, RefToken next, RefToken matched)
			throws FormatException {

		List<TextEdit> results = new ArrayList<>();

		// handle left
		if (matched.atBol()) {
			// force new line and create full indent
			String repl = evalIndent(matched.lActual, now.dent.getIndents());
			TextEdit left = new TextEdit(now.lIndex, now.index, repl);
			results.add(left);

		} else if (now.atBol()) {
			// preserve new line and create full indent
			String repl = evalIndent(now.lActual, now.dent.getIndents());
			TextEdit left = new TextEdit(now.lIndex, now.index, repl);
			results.add(left);

		} else if ((prior != null && prior.isComment()) || now.isComment()) {
			// use cur left
			String repl = evalSpacing(matched.lSpacing, matched.lActual);
			TextEdit left = new TextEdit(now.lIndex, now.index, repl);
			results.add(left);
		}

		// handle right
		if (next == null || !next.isComment()) {
			// use cur right
			String repl = evalSpacing(matched.rSpacing, matched.rActual);
			TextEdit right = new TextEdit(now.index, now.rIndex, repl);
			results.add(right);
		}

		return results;
	}

	// produce replacement string including any leading newlines
	private String evalIndent(String text, int indents) {
		String indentStr = vertFlex(text);
		indentStr += Strings.getN(indents, '\t');
		return indentStr;
	}

	private String evalSpacing(Spacing spacing, String actual) {
		switch (spacing) {
			case HFLEX:
				return horzFlex(actual);
			case HSPACE:
				return Strings.SPACE;
			case NONE:
				return "";
			case VLINE:
				return Strings.EOL;
			case VFLEX:
				return vertFlex(actual);
			case UNKNOWN:
			default:
				return actual;
		}
	}

	private String horzFlex(String actual) {
		String result = actual.replace(spTab, "\\t");
		return result.replaceAll("\\t[ ]+(?=\\t)", "\\t");
	}

	private String vertFlex(String text) {
		int lines = Strings.countLines(text);
		if (settings.removeBlankLines) {
			lines = Math.min(lines, settings.keepBlankLines);
		}
		return Strings.getN(lines, Strings.EOL);
	}
}
