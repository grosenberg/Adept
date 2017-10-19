package net.certiv.adept.format;

import net.certiv.adept.Settings;
import net.certiv.adept.format.align.AlignBuffer;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.parser.ParseData;

/**
 * Document stream formatter. Sequentially examines token spans
 * ({@code LhsToken -- WS -- RhsToken}), emitting tokens and WS to a builder, until {@code RhsToken}
 * is {@code null}. The {@code Format} of each document real token is merged with that of its
 * matched corpus feature. In merging formats, precedence is selectively given to the matched
 * feature format. The merged formats of the lhs and rhs tokens are then mutually considered to
 * determine the gap Ws.
 * <p>
 * Possibly switch to an incremental formatter. See,
 * https://github.com/eclipse/eclipse.platform.text/blob/master/org.eclipse.text/src/org/eclipse/text/edits/TextEdit.java
 */
public class Formatter {

	private Document doc;
	private ParseData data;
	private Settings settings;
	private OutputBuilder builder;
	private AlignBuffer buffer;

	public Formatter(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseData();
		this.settings = settings;
		this.builder = new OutputBuilder();
		this.buffer = new AlignBuffer(builder, doc.getTabWidth());

	}

	public OutputBuilder process() {
		if (!doc.getContent().isEmpty()) {
			Span span = new Span(data, settings);
			for (int idx = 0; !span.done; idx = span.end) {
				span = span.next(idx);
				if (span.isAligned()) {
					buffer.addAligned(span.lhs, span.visCol());
				} else {
					buffer.add(span.lhs);
				}
				if (span.breaks()) {
					buffer.add(span.trailingWs());
					buffer.add(span.eols());
					buffer.add(span.indents());
				} else {
					buffer.add(span.getGapWs());
				}
			}
			builder.flush();
			if (settings.forceLastLineBlank) {
				builder.ensureEndEol();
			}
		}
		return builder;
	}
}
