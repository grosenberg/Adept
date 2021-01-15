/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.store.HashMultilist;

/** Document formatter. */
public class Formatter extends FormatterOps {

	private SpacingProcessor spcProc;
	private WrapProcessor wrpProc;
	private AlignProcessor algProc;
	private CommentProcessor cmtProc;

	public Formatter(DocModel model, Settings settings) {
		super(model, settings);

		spcProc = new SpacingProcessor(this);
		wrpProc = new WrapProcessor(this);
		algProc = new AlignProcessor(this);
		cmtProc = new CommentProcessor(this);
	}

	@Override
	public void dispose() {
		spcProc.dispose();
		wrpProc.dispose();
		algProc.dispose();
		cmtProc.dispose();
		super.dispose();
	}

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
		if (settings.formatComments) cmtProc.format();
		if (settings.format) spcProc.formatSpacing();
		if (settings.breakLongLines) wrpProc.wrapLines();
		if (settings.alignFields) algProc.alignFields();
		if (settings.alignComments) algProc.alignComments();

		return getTextEdits();
	}

	public boolean applyEdits(List<TextEdit> edits) {
		HashMultilist<Integer, TextEdit> edIdx = new HashMultilist<>();
		for (TextEdit edit : edits) {
			edIdx.put(edit.begIndex(), edit);
		}
		edIdx.sort(TextEdit.Comp);

		// process all tokens, applying edits
		List<AdeptToken> tokens = rec.getTokens();
		for (int idx = 0, len = tokens.size() - 1; idx < len;) {
			AdeptToken token = tokens.get(idx);
			List<TextEdit> edlist = edIdx.get(token.getTokenIndex());
			if (edlist != null) {
				for (TextEdit edit : edlist) {
					if (edit.getRegion().range() == 1) {
						contents.append(edit.replacement());
						idx++;

					} else {
						if (idx == token.getTokenIndex()) {
							contents.append(token.getText());
						}
					}

					if (edit.getRegion().range() > 1) {
						contents.append(edit.replacement());
						idx = edit.endIndex();
					}
				}

			} else {
				contents.append(token.getText());
				idx++;
			}
		}

		doc.setModified(contents.toString());
		return true;
	}

	public List<TextEdit> getTextEdits() {
		if (edits.isEmpty()) return Collections.emptyList();
		return new ArrayList<>(edits.values());
	}

	public String getFormatted() {
		return contents.toString();
	}
}
