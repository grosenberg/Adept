package net.certiv.adept.format;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

/** Operations to collect and manage {@code TextEdit}s during the multiple stages of formatting. */
public class FormatterOps {

	Document doc;
	ParseRecord data;
	Settings settings;
	String tabEquiv;

	StringBuilder contents;				// the formatted source text

	// ----------------------------------------------------------------

	// key=edit region; value=edit
	TreeMap<Region, TextEdit> edits;	// implementing edits

	// key=mod line number; value=list of tokens
	TreeMultilist<Integer, AdeptToken> modLineTokensIndex;

	// key=token index; value=mod line number
	TreeMap<Integer, Integer> modTokenLineIndex;

	// key=token index; value=align col
	HashMap<Integer, Integer> tokenAlignColIndex;

	// ----------------------------------------------------------------

	public FormatterOps(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.tabEquiv = Strings.spaces(settings.tabWidth);

		edits = new TreeMap<>();
		contents = new StringBuilder();

		modLineTokensIndex = new TreeMultilist<>();
		modTokenLineIndex = new TreeMap<>();
		tokenAlignColIndex = new HashMap<>();
	}

	public void dispose() {
		doc = null;
		data = null;
		edits.clear();
		edits = null;
		contents = null;
		modLineTokensIndex.clear();
		modTokenLineIndex.clear();
		tokenAlignColIndex.clear();
	}

	// -----------------------------------------------------------------------------

	/**
	 * Appends the given token to the modLineTokenIndex. Creates new lines as appropriate. Updates the
	 * {@code visCol} field of the given token to refect any formatting edits.
	 */
	protected void append(AdeptToken token) {
		String ws = findWsLeft(token);
		int incVert = Strings.countVWS(ws);

		int line = 0;
		try {
			line = modLineTokensIndex.lastKey();
		} catch (NoSuchElementException e) {}
		line += incVert;

		shift(findTokenLeft(token), token);		// sets visCol
		modLineTokensIndex.put(line, token);	// sets line (effective)
		modTokenLineIndex.put(token.getTokenIndex(), line);
	}

	// -----------------------------------------------------------------------------

	protected void addToAlignIndex(int index, int align) {
		tokenAlignColIndex.put(index, align);
	}

	protected int getAlign(int index) {
		Integer align = tokenAlignColIndex.get(index);
		return align != null ? align : 0;
	}

	// -----------------------------------------------------------------------------

	/**
	 * Create an edit implementing the necessary whitespace change to move the given token to the given
	 * visual column. Update the visual column of any tokens to the right of the given token.
	 *
	 * @param lineNum
	 * @param token
	 * @param toVisCol
	 */
	protected void createEditAndShiftLine(int lineNum, AdeptToken token, int toVisCol) {
		if (token.visCol() == toVisCol) return;

		List<AdeptToken> fullLine = modLineTokensIndex.get(lineNum);
		int pos = fullLine.indexOf(token);
		updateOrCreateEditLeft(token, pos, toVisCol);

		ListIterator<AdeptToken> remainder = fullLine.listIterator(pos + 1);
		AdeptToken prior = token;
		while (remainder.hasNext()) {
			AdeptToken next = remainder.next();
			shift(prior, next);
			prior = next;
		}
	}

	protected void updateOrCreateEditLeft(AdeptToken token, int pos, int toVisCol) {
		TextEdit edit = findOrCreateEditLeft(token);
		if (pos == 0) { // at BOL
			int idx = Strings.lastNonHWS(edit.replacement());
			String revised = edit.replacement().substring(0, idx + 1);
			revised += createWs(1, toVisCol);
			edit.replUpdate(revised);
		} else {
			AdeptToken prior = data.tokenIndex.get(edit.begIndex());
			int from = prior.visCol() + prior.getText().length();
			if (from > toVisCol) {
				String msg = String.format("Err: shift %s to %s: %s", from, toVisCol, token.toString());
				Log.error(this, msg);
				return;
			}
			edit.replUpdate(createWs(from, toVisCol));
		}
		token.setVisCol(toVisCol);
	}

	private void shift(AdeptToken prior, AdeptToken token) {
		int from = prior != null ? prior.visCol() + prior.getText().length() : 1;
		String ws = findWsLeft(token);
		int visCol = Strings.measureVisualWidth(ws, settings.tabWidth, from);
		token.setVisCol(visCol);
	}

	private String createWs(int from, int to) {
		if (settings.useTabs) {
			return Strings.createVisualWs(settings.tabWidth, from, to);
		} else {
			return Strings.spaces(to - from);
		}
	}

	/** Returns the left adjacent TextEdit . */
	protected TextEdit findOrCreateEditLeft(AdeptToken token) {
		TextEdit edit = findEditLeft(token);
		if (edit == null) {
			AdeptToken prior = findTokenLeft(token);
			edit = createEdit(prior, token);
			edits.put(edit.getRegion(), edit);
		}
		return edit;
	}

	/** Returns the left adjacent TextEdit or {@code null}. */
	protected TextEdit findEditLeft(AdeptToken token) {
		AdeptToken left = data.getRealLeft(token.getTokenIndex());
		return edits.get(Region.key(left, token));
	}

	protected Spacing findSpacingLeft(AdeptToken token) {
		String text = findWsLeft(token);
		return Spacing.characterize(text, settings.tabWidth);
	}

	protected String findWsLeft(AdeptToken token) {
		TextEdit edit = findEditLeft(token);
		if (edit != null) return edit.replacement();

		return token.refToken().lActual;
	}

	/** Returns the left nearest visible token or {@code null}. */
	protected AdeptToken findTokenLeft(AdeptToken token) {
		Entry<Integer, AdeptToken> entry = data.tokenIndex.lowerEntry(token.getTokenIndex());
		return entry != null ? entry.getValue() : null;
	}

	/** Returns the current line of the given token. */
	protected int findTokenModLine(AdeptToken token) {
		return modTokenLineIndex.get(token.getTokenIndex());
	}

	/**
	 * Returns the position in the given current line of the given token. Returns {@code -1} if the
	 * token is not found on the given line.
	 */
	protected int findInModLine(int line, AdeptToken token) {
		return modLineTokensIndex.get(line).indexOf(token);
	}

	/**
	 * Returns the token in the given current line at the given index. Returns {@code null} if no token
	 * exists at the given line and index.
	 */
	protected AdeptToken findInModLine(int line, int idx) {
		List<AdeptToken> tokens = modLineTokensIndex.get(line);
		if (tokens.size() > idx) return tokens.get(idx);
		return null;
	}

	// -----------------------------------------------------------------------------

	/**
	 * Define an edit for the existing text (should be ws only) between the given tokens (exclusive).
	 *
	 * @param beg left token
	 * @param end right token
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(AdeptToken beg, AdeptToken end) {
		String existing = getTextBetween(beg, end);
		return createEdit(beg, end, existing, existing, 0, "");
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given token indexes
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token index
	 * @param end right token index
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(int begIndex, int endIndex, String existing, String replacement, int priority,
			String msg) {
		AdeptToken beg = begIndex > -1 ? data.getToken(begIndex) : null;
		AdeptToken end = endIndex > -1 ? data.getToken(endIndex) : null;
		return createEdit(beg, end, existing, replacement, priority, msg);
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given tokens
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token
	 * @param end right token
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(AdeptToken beg, AdeptToken end, String existing, String replacement, int priority,
			String msg) {

		if ((beg == null && end == null) || existing == null || replacement == null) {
			throw new IllegalArgumentException("Malformed text edit create request.");
		}
		if (end == null) end = data.getToken(data.getTokenStream().size() - 1);
		if (msg == null) msg = "";
		return new TextEdit(beg, end, existing, replacement, priority, msg);
	}

	// -----------------------------------------------------------------------------

	protected String getTextBetween(AdeptToken beg, AdeptToken end) {
		int begIdx = beg != null ? beg.getTokenIndex() : 0;
		return data.getTextBetween(begIdx, end.getTokenIndex());
	}

	/** Update parsed lines to formatting modified lines. */
	public TreeMultilist<Integer, AdeptToken> updateLinesIndex(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> modLines = new TreeMultilist<>();
		modLines.setValueComparator(AdeptComp.Instance);
		for (AdeptToken token : lines.valuesAll()) {
			Integer modLine = modTokenLineIndex.get(token.getTokenIndex());
			modLines.put(modLine, token);
		}
		return modLines;
	}
}
