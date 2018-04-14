package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Strings;

/** Operates to collect and manage {@code TextEdit}s during the multiple stages of formatting. */
public class FormatMgr {

	private ParseRecord data;
	private Settings settings;
	private TextEditBuilder editBuilder;

	// key=edit region; value=edit
	private TreeMap<Region, TextEdit> edits;	// implementing edits

	// key=mod line number; value=list of tokens
	public TreeMultilist<Integer, AdeptToken> modLineTokensIndex;

	// key=token index; value=mod line number
	public TreeMap<Integer, Integer> modTokenLineIndex;

	// key=token index; value=align col
	public HashMap<Integer, Integer> tokenAlignColIndex;

	// ----------------------------------------------------------------

	public FormatMgr(ParseRecord data, Settings settings) {
		this.data = data;
		this.settings = settings;

		editBuilder = new TextEditBuilder(this);

		edits = new TreeMap<>();
		modLineTokensIndex = new TreeMultilist<>();
		modTokenLineIndex = new TreeMap<>();
		tokenAlignColIndex = new HashMap<>();
	}

	protected ParseRecord getData() {
		return data;
	}

	protected TextEdit create(int begIndex, int endIndex, String existing, String repl, int priority, String msg) {
		return editBuilder.create(begIndex, endIndex, existing, repl, priority, msg);
	}

	protected TreeMap<Region, TextEdit> edits() {
		return edits;
	}

	public List<TextEdit> getTextEdits() {
		if (edits.isEmpty()) return Collections.emptyList();
		return new ArrayList<>(edits.values());
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

	protected void addAlignIndex(int index, int align) {
		tokenAlignColIndex.put(index, align);
	}

	protected int getAlign(int index) {
		Integer align = tokenAlignColIndex.get(index);
		return align != null ? align : 0;
	}

	// ----------------------------------------------------------------

	protected void handlePairAlign(TreeMultilist<Integer, AdeptToken> lines) {
		if (lines.isEmpty()) return;

		TreeMultilist<Integer, AdeptToken> modLines = modLines(lines);
		if (modLines.size() > 1) {
			handleListAlign(lines);
			return;
		}

		int modLine = lines.lastKey();
		for (AdeptToken token : lines.get(modLine)) {
			int rem = token.visCol() % settings.tabWidth;
			if (rem > 0) {
				int toVisCol = token.visCol() + settings.tabWidth - rem;
				createEditAndShiftLine(modLine, token, toVisCol);
			}
		}
	}

	protected void handleListAlign(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> modLines = modLines(lines);
		int maxAligns = maxAligns(modLines);
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
						createEditAndShiftLine(lineNum, alignToken, maxVisCol);
					}
				}
			}
		}
	}

	// create edit implemeting change to the given visual col and shift remaining tokens
	private void createEditAndShiftLine(int lineNum, AdeptToken token, int toVisCol) {
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

	private int maxAligns(TreeMultilist<Integer, AdeptToken> modLines) {
		int max = 0;
		for (Integer num : modLines.keySet()) {
			max = Math.max(max, modLines.get(num).size());
		}
		return max;
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
			edit = editBuilder.create(prior, token);
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

	/** Returns the left nearest visible token or {@code null}. */
	protected AdeptToken findTokenLeft(AdeptToken token) {
		Entry<Integer, AdeptToken> entry = data.tokenIndex.lowerEntry(token.getTokenIndex());
		return entry != null ? entry.getValue() : null;
	}

	protected int findTokenModLine(AdeptToken token) {
		return modTokenLineIndex.get(token.getTokenIndex());
	}

	protected String getTextBetween(AdeptToken beg, AdeptToken end) {
		return data.getTextBetween(beg.getTokenIndex(), end.getTokenIndex());
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
