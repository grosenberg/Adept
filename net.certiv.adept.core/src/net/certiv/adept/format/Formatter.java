package net.certiv.adept.format;

import net.certiv.adept.Tool;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Format;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.util.Strings;

/**
 * Eclipse TextEdit at
 * https://github.com/eclipse/eclipse.platform.text/blob/master/org.eclipse.text/src/org/eclipse/text/edits/TextEdit.java
 */
public class Formatter {

	private Document doc;
	private ParseData data;
	private OutputBuilder buffer;

	private int currIndent;
	private int priorIndent;

	public Formatter(DocModel model) {
		this.doc = model.getDocument();
		this.data = doc.getParseData();
		this.buffer = new OutputBuilder();
	}

	public OutputBuilder process() {
		if (!doc.getContent().isEmpty()) {
			Span span = new Span(data);
			for (int idx = 0; !span.done(); idx = span.getEnd()) {
				span = span.next(idx);
				process(span);
			}
			if (Tool.settings.forceLastLineBlank) {
				buffer.ensureLastLineTerminated();
			}
		}
		return buffer;
	}

	private void process(Span span) {
		buffer.add(span.getLhs().getText());
		if (insertsTerminal(span)) {
			buffer.add(span.getTrail());
			buffer.add(span.getTerminals());
			processIndent(span);
		} else {
			processGap(span);
		}
	}

	private boolean insertsTerminal(Span span) {
		Format format = span.getFormat();
		if (format.atLineBeg) {
			return true;
		} else if (format.joinAlways) {
			return false;
		}
		return span.insertsTerminal();
	}

	private void processIndent(Span span) {
		Format format = span.getFormat();
		if (format.noFormat) {
			buffer.add(span.getLead());
		} else {
			insertIndent(format, span.getDentation());
		}
	}

	private void processGap(Span span) {
		Format format = span.getFormat();
		if (format.noFormat) {
			buffer.add(span.getLead());
		} else if (format.alignAbove || format.alignBelow) {
			// TODO: calc actual alignment for next real
			buffer.add(span.getLead());
		} else if (format.multAfter) {
			buffer.add(span.getLead());
		} else if (format.wsAfter) {
			buffer.add(" ");
		}
	}

	private void insertIndent(Format format, int dents) {
		if (format.indented) {
			currIndent = priorIndent + dents;
			currIndent = currIndent > 0 ? currIndent : 0;
		} else if (format.atLineBeg) { // <-
			currIndent = 0;
		}

		if (currIndent > 0) {
			String indent = Strings.createIndent(Tool.settings.tabWidth, Tool.settings.useTabs, currIndent);
			buffer.add(indent);
		}
		priorIndent = currIndent;
	}
}
