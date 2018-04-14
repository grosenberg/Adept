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
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TreeMultilist;
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

	// -----------------------------------------------------------------------------

	protected void addToAlignIndex(int index, int align) {
		tokenAlignColIndex.put(index, align);
	}

	protected int getAlign(int index) {
		Integer align = tokenAlignColIndex.get(index);
		return align != null ? align : 0;
	}

	/**
	 * Appends the given token to the modLineTokenIndex. Creates new lines as appropriate. Updates the
	 * {@code visCol} field of the given token to refect any formatting edits.
	 */
	protected void append(AdeptToken token) {
		Region region = Region.key(token.getTokenIndex());
		Entry<Region, TextEdit> lower = edits.lowerEntry(region);
		if (region.adjacent(lower.getKey())) {
			TextEdit edit = lower.getValue();
			append(Strings.countVWS(edit.replacement()), token);

		} else if (token.atBol()) {
			AdeptToken left = findTokenLeft(token);
			int prior = left != null ? left.getLine() : 0;
			append(token.getLine() - prior, token);

		} else {
			append(0, token);
		}
	}

	private void append(int inc, AdeptToken token) {
		int line = 0;
		try {
			line = modLineTokensIndex.lastKey();
		} catch (NoSuchElementException e) {}
		line += inc;

		AdeptToken prior = findTokenLeft(token);
		shift(prior, token);

		modLineTokensIndex.put(line, token);
		modTokenLineIndex.put(token.getTokenIndex(), line);
		modLineTokensIndex.put(line, token);
	}

	// -----------------------------------------------------------------------------

	// create edit implemeting change to the given visual col and shift remaining tokens
	protected void createEditAndShiftLine(int lineNum, AdeptToken token, int toVisCol) {
		updateOrCreateEditLeft(token, toVisCol);

		List<AdeptToken> fullLine = modLineTokensIndex.get(lineNum);
		int idx = fullLine.indexOf(token);
		ListIterator<AdeptToken> itr = fullLine.listIterator(idx + 1);
		AdeptToken prior = token;
		while (itr.hasNext()) {
			AdeptToken next = itr.next();
			shift(prior, next);
			prior = next;
		}
	}

	private void shift(AdeptToken prior, AdeptToken mark) {
		String text = prior.getText();
		TextEdit edit = edits.get(Region.key(prior.getTokenIndex(), mark.getTokenIndex()));
		if (edit != null) {
			text += edit.replacement();
		} else {
			text += getTextBetween(prior, mark);
		}
		int visCol = Strings.measureVisualWidth(text, settings.tabWidth, prior.visCol());
		mark.setVisCol(visCol);
	}

	protected void updateOrCreateEditLeft(AdeptToken token, int toVisCol) {
		TextEdit edit = findOrCreateEditLeft(token);
		AdeptToken prior = data.tokenIndex.get(edit.begIndex());
		int beg = prior.visCol() + prior.getText().length();
		if (settings.useTabs) {
			edit.replUpdate(Strings.createVisualWs(settings.tabWidth, beg, toVisCol));
		} else {
			edit.replAppend(Strings.spaces(toVisCol - beg));
		}
		token.setVisCol(toVisCol);
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
		Region key = Region.key(token);
		Entry<Region, TextEdit> entry = edits.lowerEntry(key);
		if (entry == null || !entry.getKey().adjacent(key)) return null;
		return entry.getValue();
	}

	protected Spacing findSpacingLeft(AdeptToken token) {
		TextEdit edit = findEditLeft(token);
		if (edit != null) return Spacing.characterize(edit.replacement(), settings.tabWidth);

		RefToken ref = token.refToken();
		if (ref.matched != null) return ref.matched.lSpacing;
		if (ref.lSpacing != Spacing.UNKNOWN) return ref.lSpacing;

		String existing = getTextBetween(findTokenLeft(token), token);
		return Spacing.characterize(existing, settings.tabWidth);
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
	protected int getInModLine(int line, AdeptToken token) {
		return modLineTokensIndex.get(line).indexOf(token);
	}

	/**
	 * Returns the token in the given current line at the given index. Returns {@code null} if no token
	 * exists at the given line and index.
	 */
	protected AdeptToken getInModLine(int line, int idx) {
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

	/** Change parsed lines to modified lines. */
	public TreeMultilist<Integer, AdeptToken> modLines(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> modLines = new TreeMultilist<>();
		modLines.setValueComparator(AdeptComp.Instance);
		for (AdeptToken token : lines.valuesAll()) {
			Integer modLine = modTokenLineIndex.get(token.getTokenIndex());
			modLines.put(modLine, token);
		}
		return modLines;
	}

	protected void dispose() {
		edits.clear();
		modLineTokensIndex.clear();
		modTokenLineIndex.clear();
		tokenAlignColIndex.clear();
	}
}
