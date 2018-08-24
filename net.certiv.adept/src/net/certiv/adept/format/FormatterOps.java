/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.format.plan.Group;
import net.certiv.adept.format.plan.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.Record;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Strings;

/** Operations to collect and manage {@code TextEdit}s during the multiple stages of formatting. */
public class FormatterOps {

	Tool tool;
	Document doc;
	Record rec;
	Settings settings;
	String tabEquiv;

	StringBuilder contents;				// the formatted source text

	// ----------------------------------------------------------------

	// key=edit region; value=edit
	TreeMap<Region, TextEdit> edits;	// implementing edits

	// key=line number; value=list of tokens
	TreeMultilist<Integer, AdeptToken> lineTokensIndex;

	// key=token index; value=line number
	TreeMap<Integer, Integer> tokenLineIndex;

	// key=token index; value=align col
	HashMap<Integer, Integer> tokenAlignPosIndex;

	// ----------------------------------------------------------------

	public FormatterOps(DocModel model, Settings settings) {
		this.tool = model.getMgr().getTool();
		this.doc = model.getDocument();
		this.rec = doc.getRecord();
		this.settings = settings;
		this.tabEquiv = Strings.spaces(settings.tabWidth);

		edits = new TreeMap<>();
		contents = new StringBuilder();

		lineTokensIndex = new TreeMultilist<>();
		tokenLineIndex = new TreeMap<>();
		tokenAlignPosIndex = new HashMap<>();
	}

	public void dispose() {
		doc = null;
		rec = null;
		edits.clear();
		edits = null;
		contents = null;
		lineTokensIndex.clear();
		tokenLineIndex.clear();
		tokenAlignPosIndex.clear();
	}

	// -----------------------------------------------------------------------------

	protected void addToAlignIndex(int index, int align) {
		tokenAlignPosIndex.put(index, align);
	}

	protected int getAlign(int index) {
		Integer align = tokenAlignPosIndex.get(index);
		return align != null ? align : 0;
	}

	// -----------------------------------------------------------------------------

	/** Create a meta group for each set of serial groups. */
	protected void collectSerials() {
		Group serials = new Group();
		for (Group group : rec.groupIndex) {
			for (Scheme scheme : group.getSchemes()) {
				if (group.colinear(scheme)) {
					TreeMultilist<Integer, AdeptToken> members = group.get(scheme);
					Integer key = members.firstKey();
					serials.addMembers(Scheme.SERIAL, key, members.get(key));
				}
			}
		}

		TreeMultilist<Integer, AdeptToken> members = serials.get(Scheme.SERIAL);
		if (members == null) return;

		Group group = new Group();
		for (int lnum : members.keySet()) {
			if (group.isEmpty() || group.contiguous(Scheme.SERIAL, lnum)) {
				group.addMembers(Scheme.SERIAL, lnum, members.get(lnum));
			} else {
				rec.groupIndex.add(group);
				group = new Group();
				group.addMembers(Scheme.SERIAL, lnum, members.get(lnum));
			}
		}
		if (!group.isEmpty()) rec.groupIndex.add(group);

	}

	/**
	 * Rebuilds the line indexes to reflect edits between real tokens. Appends each real token to the
	 * lineTokenIndex. Creates new lines as appropriate. Updates the {@code visPos} field of the given
	 * token to refect any formatting edits.
	 */
	protected void buildLinesIndexes() {
		lineTokensIndex.clear();
		tokenLineIndex.clear();

		// examine all real tokens
		AdeptToken prior = null;
		for (AdeptToken token : rec.index.keySet()) {
			String ws = findWsLeft(token);
			int nls = Strings.countVWS(ws);

			int line0 = prior != null ? prior.getLinePos() : 0;
			line0 += nls;
			token.setLine(line0 + 1); // native 1..n

			int from = 0;
			if (prior != null && nls == 0) {
				from = prior.getVisPos() + prior.getText().length();
			}
			int width = Strings.measureVisualWidth(ws, settings.tabWidth, from);
			token.setVisPos(from + width);

			lineTokensIndex.put(line0, token);
			tokenLineIndex.put(token.getTokenIndex(), line0);

			prior = token;
		}
	}

	/**
	 * Define an edit to replace the existing comment text with the new given string value.
	 *
	 * @param token the comment token
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 */
	protected TextEdit updateOrCreateCommentEdit(AdeptToken token, String replacement, int priority, String msg) {
		TextEdit edit = edits.get(Region.key(token, token));
		if (edit != null) {
			edit.setReplacement(replacement);
		} else {
			edit = new TextEdit(token, replacement, priority, msg);
			edits.put(edit.getRegion(), edit);
		}
		return edit;
	}

	/**
	 * Create an edit implementing the necessary whitespace change to move the given token to the given
	 * visual column. Update the visual column of any tokens to the right of the given token.
	 *
	 * @param lineNum
	 * @param token
	 * @param toVisPos
	 */
	protected void prepEditAndShiftLine(int lnum, AdeptToken token, int toVisPos) {
		int vpos = calcVisPosition(token);
		if (vpos == toVisPos) return;

		List<AdeptToken> fullLine = lineTokensIndex.get(lnum);
		int idx = fullLine.indexOf(token);
		updateOrCreateEditLeft(token, idx, toVisPos);
		updateLineVisPositions(token);
	}

	// -----------------------------------------------------------------------------

	protected int calcVisPosition(AdeptToken token) {
		int lnum = tokenLineIndex.get(token.getTokenIndex());
		List<AdeptToken> reals = lineTokensIndex.get(lnum);
		int idx = reals.indexOf(token);

		StringBuilder sb = new StringBuilder();
		for (AdeptToken real : reals.subList(0, idx)) {
			sb.append(findWsLeft(real));
			sb.append(real.getText());
		}
		sb.append(findWsLeft(token));
		return Strings.measureVisualWidth(sb, settings.tabWidth);
	}

	protected void updateLineVisPositions(AdeptToken token) {
		int col = calcVisPosition(token) + token.getText().length();

		int lnum = tokenLineIndex.get(token.getTokenIndex());
		List<AdeptToken> fullLine = lineTokensIndex.get(lnum);
		int idx = fullLine.indexOf(token);
		ListIterator<AdeptToken> reals = fullLine.listIterator(idx + 1);
		while (reals.hasNext()) {
			AdeptToken real = reals.next();
			String lead = findWsLeft(real);
			col += Strings.measureVisualWidth(lead, settings.tabWidth, col);
			real.setVisPos(col);
			col += real.getText().length();
		}
	}

	// -----------------------------------------------------------------------------

	protected void updateOrCreateEditLeft(AdeptToken token, int pos, int toVisPos) {
		TextEdit edit = findOrCreateEditLeft(token);
		if (pos == 0) { // at BOL
			String ws = createWs(0, toVisPos);
			edit.replUpdate(ws);
		} else {
			AdeptToken prior = rec.tokenIndex.get(edit.begIndex());
			int from = prior.getVisPos() + prior.getText().length();
			if (from > toVisPos) {
				String msg = String.format("Err: shift %s to %s: %s", from, toVisPos, token.toString());
				tool.toolInfo(this, msg);
				return;
			}
			edit.replUpdate(createWs(from, toVisPos));
		}
		token.setVisPos(toVisPos);
	}

	private String createWs(int from, int to) {
		if (settings.useTabs) {
			return Strings.createVisualWs(settings.tabWidth, from, to);
		}
		return Strings.spaces(to - from);
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
		AdeptToken left = rec.getRealLeft(token.getTokenIndex());
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

	/** Returns the left nearest real (non-ws) token or {@code null}. */
	protected AdeptToken findTokenLeft(AdeptToken token) {
		Entry<Integer, AdeptToken> entry = rec.tokenIndex.lowerEntry(token.getTokenIndex());
		return entry != null ? entry.getValue() : null;
	}

	/** Returns the current line of the given token. */
	protected int findLine(AdeptToken token) {
		return tokenLineIndex.get(token.getTokenIndex());
	}

	/**
	 * Returns the position in the given current line of the given token. Returns {@code -1} if the
	 * token is not found on the given line.
	 */
	protected int indexInLine(int line, AdeptToken token) {
		return lineTokensIndex.get(line).indexOf(token);
	}

	/**
	 * Returns the token in the given line at the given index. Returns {@code null} if no token exists
	 * at the given line and index.
	 */
	protected AdeptToken getFromLine(int line, int idx) {
		List<AdeptToken> tokens = lineTokensIndex.get(line);
		if (tokens.size() > idx) return tokens.get(idx);
		return null;
	}

	/**
	 * Returns the token prior to the given token in the given line. Returns {@code null} if no such
	 * token exists.
	 */
	protected AdeptToken priorInLine(int line, AdeptToken token) {
		int idx = indexInLine(line, token);
		if (idx < 1) return null;
		return getFromLine(line, idx - 1);
	}

	// -----------------------------------------------------------------------------

	/**
	 * Creates an edit for the existing text (will be ws only) between the given tokens (exclusive).
	 *
	 * @param prior left token
	 * @param token right token
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(AdeptToken prior, AdeptToken token) {
		int priorIdx = prior != null ? prior.getTokenIndex() : 0;
		String existing = rec.getTextBetween(priorIdx, token.getTokenIndex());
		return createEdit(prior, token, existing, existing, 0, "");
	}

	/**
	 * Creates an edit to replace the existing text (should be ws only) between the given token indexes
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
		AdeptToken beg = begIndex > -1 ? rec.getToken(begIndex) : null;
		AdeptToken end = endIndex > -1 ? rec.getToken(endIndex) : null;
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
		if (end == null) end = rec.getToken(rec.getTokenStream().size() - 1);
		if (msg == null) msg = "";
		return new TextEdit(beg, end, existing, replacement, priority, msg);
	}
}
