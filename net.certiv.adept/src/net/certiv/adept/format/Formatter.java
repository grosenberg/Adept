package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
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
		List<AdeptToken> srcTokens = data.getTokens();

		TreeMap<AdeptToken, Feature> index = doc.getModel().getIndex();
		NavigableSet<AdeptToken> tokens = index.navigableKeySet();

		for (AdeptToken token : tokens) {
			Feature cur = index.get(token);
			RefToken existing = cur.getRef(token.getTokenIndex());

			RefToken matched = existing.matched;
			if (matched != null) {
				AdeptToken lToken = existing.lIndex > 0 ? srcTokens.get(existing.lIndex) : null;
				Feature bef = lToken != null ? index.get(lToken) : null;

				AdeptToken rToken = existing.rIndex > 0 ? srcTokens.get(existing.rIndex) : null;
				Feature aft = rToken != null ? index.get(rToken) : null;

				List<TextEdit> edits = createEdits(bef, cur, aft, matched);

				for (TextEdit edit : edits) {
					Region region = edit.getRegion();
					if (space.overlaps(region)) throw new FormatterException(token, region, results);
					results.add(edit);
					space.add(region);
				}

			}
		}

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

	/**
	 * <pre>
	 * ------|--------|--------|-------
	 *
	 * ... b.Idx    b.rIdx
	 *     c.lIdx   c.Idx    c.rIdx
	 *              a.lIdx   a.Idx  ...
	 * </pre>
	 */
	private List<TextEdit> createEdits(Feature bef, Feature cur, Feature aft, RefToken matched) {
		// TODO: indents
		// TODO: aligns
		// TODO: line breaks

		List<TextEdit> results = new ArrayList<>();

		if (bef.isComment() || cur.isComment()) {
			String repl = eval(matched.lSpacing, matched.lActual);
			TextEdit left = new TextEdit(matched.lIndex, matched.index, repl);
			results.add(left);
		}

		if (!aft.isComment()) {
			String repl = eval(matched.rSpacing, matched.rActual);
			TextEdit right = new TextEdit(matched.index, matched.rIndex, repl);
			results.add(right);
		}

		return results;
	}

	private String eval(Spacing spacing, String actual) {
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

	private String vertFlex(String actual) {
		int lines = Math.min(countVert(actual), settings.keepBlankLines);
		return Strings.getN(lines, Strings.EOL);
	}

	private int countVert(String txt) {
		if (txt == null || txt.isEmpty()) return 0;
		return txt.split("\\R", -1).length;
	}
}
