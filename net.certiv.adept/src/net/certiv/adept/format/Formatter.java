package net.certiv.adept.format;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.certiv.adept.Settings;
import net.certiv.adept.format.align.Align;
import net.certiv.adept.format.align.Group;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Log;

/** Document formatter. */
public class Formatter extends FormatterBase {

	private FormatMgr fMgr;
	private StringBuilder contents;				// the formatted source text
	private CommentProcessor cmtFormatter;

	public Formatter(DocModel model, Settings settings) {
		super(model, settings);

		fMgr = new FormatMgr(data, settings);
		cmtFormatter = new CommentProcessor(settings);
		contents = new StringBuilder();
	}

	// -------------------------------------------------------------------------

	/**
	 * Executes the formatter. The final results is then accessible from the {@code modified} field of
	 * the document.
	 *
	 * @return {@code true} if the source document is modified by formatting.
	 */
	public boolean execute() {
		List<TextEdit> edits = createEdits();
		if (edits.isEmpty()) return false;

		return applyEdits(edits);
	}

	public List<TextEdit> createEdits() {
		if (settings.format) {
			adjustLineSpacing();
			if (settings.breakLongLines) breakLongLines();
			if (settings.alignFields) alignFields();
			if (settings.alignComments) alignComments();
			if (settings.formatComments) formatComments();
		}

		return fMgr.getTextEdits();
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

	public List<TextEdit> getTextEdits() {
		return fMgr.getTextEdits();
	}

	public String getFormattedContents() {
		return contents.toString();
	}

	// -------------------------------------------------------------------------

	// initial TextEdit creation pass; also creates
	// the initial modified line/token index in the FormatMgr
	private void adjustLineSpacing() {
		for (AdeptToken token : data.index.keySet()) { // visible tokens
			RefToken present = srcRefFor(token.getTokenIndex());

			if (present != null) {
				RefToken prior = srcRefFor(present.lIndex);
				RefToken next = srcRefFor(present.rIndex);
				RefToken matched = present.matched;
				showWhere(prior, next, matched);

				if (matched != null) {
					try {
						Map<Region, TextEdit> editSet = adjustLineSpacing(prior, present, next, matched);
						if (!editSet.isEmpty()) {
							for (Entry<Region, TextEdit> entry : editSet.entrySet()) {
								Region region = entry.getKey();
								TextEdit edit = entry.getValue();

								if (fMgr.edits().containsKey(region)) {
									TextEdit existing = fMgr.edits().get(region);
									if (existing.priority() > edit.priority()) {
										Log.debug(this, "P: " + existing.toString());	// priority/presesrve
									} else {
										Log.debug(this, "R: " + edit.toString());		// remove/replace
										if (edit.existing().equals(edit.replacement())) {
											fMgr.edits().remove(region);
										} else {
											fMgr.edits().put(region, edit);
										}
									}
								} else {
									if (!edit.existing().equals(edit.replacement())) {
										Log.debug(this, "A: " + edit.toString());		// add
										fMgr.edits().put(region, edit);
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

			fMgr.append(token);
		}
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
	private Map<Region, TextEdit> adjustLineSpacing(RefToken prior, RefToken current, RefToken next, RefToken matched)
			throws FormatException {

		Map<Region, TextEdit> edits = new HashMap<>();

		// priority 1: left nominal
		if (current.lIndex > AdeptToken.BOF) {
			String repl = calcSpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
			TextEdit edit = fMgr.create(current.lIndex, current.index, current.lActual, repl, 1, "L Nom");
			edits.put(edit.getRegion(), edit);
		}

		// priority 2: right nominal
		if (current.index > AdeptToken.EOF) {
			String repl = calcSpacing(matched.rSpacing, matched.rActual, matched.dent.indents);
			TextEdit edit = fMgr.create(current.index, current.rIndex, current.rActual, repl, 2, "R Nom");
			edits.put(edit.getRegion(), edit);
		}

		// priority 3: comments
		if ((prior != null && prior.isComment()) || current.isComment()) {
			String repl = calcSpacing(matched.lSpacing, matched.lActual, matched.dent.indents);
			TextEdit edit = fMgr.create(current.lIndex, current.index, current.lActual, repl, 3, "L Cmt");
			edits.put(edit.getRegion(), edit);
		}

		// priority 4: bol
		if (current.lIndex > AdeptToken.BOF && (matched.atBol() || current.atBol())) {
			String repl = calcIndent(current.lActual, matched.lActual, current.dent.indents);
			TextEdit edit = fMgr.create(current.lIndex, current.index, current.lActual, repl, 4, "L Bol");
			edits.put(edit.getRegion(), edit);
		}

		return edits;
	}

	// -------------------------------------------------------------------------
	// subsequent/optional formatting phases

	private void breakLongLines() {
		// TODO
	}

	private void alignFields() {
		for (Group group : data.groupIndex) {
			TableMultilist<Align, Integer, AdeptToken> members = group.getMembers();
			for (Align align : members.keySet()) {
				TreeMultilist<Integer, AdeptToken> lines = members.get(align);
				switch (align) {
					case PAIR:
						fMgr.handlePairAlign(lines);
						break;

					case LIST:
					case GROUP:
						fMgr.handleListAlign(lines);
						break;

					default:
						break;
				}
			}
		}
	}

	private void alignComments() {
		for (Group group : data.groupIndex) {
			TreeMultilist<Integer, AdeptToken> lines = group.get(Align.COMMENT);
			if (lines != null) {
				fMgr.handleListAlign(lines);
			}
		}
	}

	private void formatComments() {
		int firstTokenIndex = data.index.firstKey().getTokenIndex(); // header, if present
		for (AdeptToken comment : data.commentIndex) {
			if (comment.getTokenIndex() == firstTokenIndex && !settings.formatHdrComment) continue;

			if (cmtFormatter.process(comment)) {
				comment.setText(cmtFormatter.getResult());
			}
		}
	}
}
