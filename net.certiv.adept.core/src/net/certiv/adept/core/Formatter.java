package net.certiv.adept.core;

import java.util.Set;

import net.certiv.adept.Tool;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.topo.Facet;
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
		this.data = doc.getParse();
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
		Set<Facet> facets = span.getFacets();
		if (facets.contains(Facet.AT_LINE_BEG)) {
			return true;
		} else if (facets.contains(Facet.JOIN_ALWAYS)) {
			return false;
		}
		return span.insertsTerminal();
	}

	private void processIndent(Span span) {
		Set<Facet> facets = span.getFacets();
		if (facets.contains(Facet.NO_FORMAT)) {
			buffer.add(span.getLead());
		} else {
			insertIndent(facets, span.getDentation());
		}
	}

	private void processGap(Span span) {
		Set<Facet> facets = span.getFacets();
		if (facets.contains(Facet.NO_FORMAT)) {
			buffer.add(span.getLead());
		} else if (facets.contains(Facet.ALIGNED_ABOVE) || facets.contains(Facet.ALIGNED_BELOW)) {
			// TODO: calc actual alignment for next real
			buffer.add(span.getLead());
		} else if (facets.contains(Facet.WIDE_AFTER)) {
			buffer.add(span.getLead());
		} else if (facets.contains(Facet.WS_AFTER)) {
			buffer.add(" ");
		}
	}

	private void insertIndent(Set<Facet> facets, int dents) {
		if (facets.contains(Facet.INDENTED)) {
			currIndent = priorIndent + dents;
			currIndent = currIndent > 0 ? currIndent : 0;
		} else if (facets.contains(Facet.AT_LINE_BEG)) { // <-
			currIndent = 0;
		}

		if (currIndent > 0) {
			String indent = Strings.createIndent(Tool.settings.tabWidth, Tool.settings.useTabs, currIndent);
			buffer.add(indent);
		}
		priorIndent = currIndent;
	}
}
