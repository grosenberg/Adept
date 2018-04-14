package net.certiv.adept.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.DocModel;

/** Document formatter. */
public class Formatter extends FormatterOps {

	private SpacingProcessor spacer;
	private LineBreakProcessor breaker;
	private AlignProcessor aligner;
	private CommentProcessor commenter;

	public Formatter(DocModel model, Settings settings) {
		super(model, settings);

		spacer = new SpacingProcessor(this);
		breaker = new LineBreakProcessor(this);
		aligner = new AlignProcessor(this);
		commenter = new CommentProcessor(this);
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
		if (settings.format) {
			spacer.adjustLineSpacing();
			if (settings.breakLongLines) breaker.breakLongLines();
			if (settings.alignFields) aligner.alignFields();
			if (settings.alignComments) aligner.alignComments();
			if (settings.formatComments) commenter.formatComments();
		}
		return getTextEdits();
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
		if (edits.isEmpty()) return Collections.emptyList();
		return new ArrayList<>(edits.values());
	}

	public String getFormatted() {
		return contents.toString();
	}
}
