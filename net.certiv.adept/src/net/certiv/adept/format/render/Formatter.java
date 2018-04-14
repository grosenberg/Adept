package net.certiv.adept.format.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.Settings;
import net.certiv.adept.format.TextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;

/** Document formatter. */
public class Formatter {

	private Document doc;
	private FormatOps ops;

	private SpacingProcessor spacer;
	private LineBreakProcessor breaker;
	private AlignProcessor aligner;
	private CommentProcessor commenter;

	public Formatter(DocModel model, Settings settings) {
		doc = model.getDocument();
		ops = new FormatOps(doc.getParseRecord(), settings);

		spacer = new SpacingProcessor(ops);
		breaker = new LineBreakProcessor(ops);
		aligner = new AlignProcessor(ops);
		commenter = new CommentProcessor(ops);
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
		if (ops.settings.format) {
			spacer.adjustLineSpacing();
			if (ops.settings.breakLongLines) breaker.breakLongLines();
			if (ops.settings.alignFields) aligner.alignFields();
			if (ops.settings.alignComments) aligner.alignComments();
			if (ops.settings.formatComments) commenter.formatComments();
		}
		return ops.getTextEdits();
	}

	public boolean applyEdits(List<TextEdit> edits) {
		Map<Integer, TextEdit> editSet = new HashMap<>();
		for (TextEdit edit : edits) {
			editSet.put(edit.begIndex(), edit);
		}

		List<AdeptToken> tokens = ops.data.getTokens();
		for (int idx = 0, len = tokens.size() - 1; idx < len;) {
			AdeptToken token = tokens.get(idx);
			ops.contents.append(token.getText());
			TextEdit edit = editSet.get(token.getTokenIndex());
			if (edit != null) {
				ops.contents.append(edit.replacement());
				idx = edit.endIndex();
			} else {
				idx++;
			}
		}

		doc.setModified(ops.contents.toString());
		return true;
	}

	public List<TextEdit> getTextEdits() {
		return ops.getTextEdits();
	}

	public String getFormattedContents() {
		return ops.toString();
	}
}
