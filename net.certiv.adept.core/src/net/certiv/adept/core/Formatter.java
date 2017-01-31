package net.certiv.adept.core;

import java.util.List;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.Tool;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Form;
import net.certiv.adept.util.Strings;

public class Formatter {

	private DocModel model;
	private Document doc;
	private ParseData data;
	private Index index;
	private OutputBuilder buffer;

	private int currIndent;
	private int priorIndent;
	private int priorBlankCnt;

	public Formatter(DocModel model) {
		this.model = model;
		this.doc = model.getDocument();
		this.data = doc.getParse();
		this.buffer = new OutputBuilder();
	}

	public OutputBuilder process() {
		if (!doc.getContent().isEmpty()) {

			index = new Index(data.getTokenStream().size() - 1);
			for (Feature feature : model.getFeatures()) {
				if (feature.isRule()) continue;
				index.add(feature.getStart(), feature);
			}

			TokenLine line = new TokenLine(data);
			for (Token tok : data.getTokens()) {
				AdeptToken token = (AdeptToken) tok;
				line.add(token);
				if (token.getType() == data.VWS) {
					processLine(line);
					line.clear();
				}
			}
			if (!line.isEmpty()) {
				processLine(line);
			}
		}

		return buffer;
	}

	private void processLine(TokenLine line) {
		buffer.add(line);
		LineInfo info = line.getInfo();
		if (info.isBlank()) {
			if (Tool.settings.removeBlankLines) {
				if (priorBlankCnt >= Tool.settings.keepBlankLines) return;
			}
			buffer.add(Strings.EOL);
			priorBlankCnt++;
			return;
		}
		priorBlankCnt = 0;
		processIndent(line, info);
		processTokens(line, info);
		processTerminal(line, info);
		priorIndent = currIndent;
	}

	private void processIndent(TokenLine line, LineInfo info) {
		AdeptToken token = line.get(info.first);
		int format = getFormat(token);
		List<Facet> facets = Facet.get(format);
		currIndent = 0;
		if (facets.contains(Facet.INDENTED)) {
			currIndent = priorIndent + Facet.getDentation(facets);
			currIndent = currIndent > 0 ? currIndent : 0;
		}

		if (currIndent > 0) {
			String indent = Strings.createIndent(Tool.settings.tabWidth, Tool.settings.useTabs, currIndent);
			buffer.add(indent);
		}
	}

	private void processTokens(TokenLine line, LineInfo info) {
		for (int idx = info.first; idx < info.terminator; idx++) {
			AdeptToken tokenCurr = line.get(idx);
			if (tokenCurr.getType() == data.HWS) continue;

			int formCurr = getFormat(tokenCurr);

			AdeptToken tokenNext = line.getNextReal(idx);
			int formNext = getFormat(tokenNext);

			List<Facet> facets = Form.resolveOverlap(formCurr, formNext);

			buffer.add(tokenCurr.getText());
			if (facets.contains(Facet.ALIGNED_ABOVE)) {
				// TODO: calc ws to provide alignment for next real
				// look at prior line tokens to find new alignment position
				buffer.add(line.getNextHws(idx).getText());
			} else if (facets.contains(Facet.WS_AFTER)) {
				buffer.add(" ");
			}
		}
	}

	private void processTerminal(TokenLine line, LineInfo info) {
		if (info.terminator > -1) {
			// TODO: contitional join
			// TODO: line width split
			AdeptToken terminal = line.get(info.terminator);
			buffer.add(terminal.getText());
		}
	}

	private int getFormat(AdeptToken token) {
		if (token != null) {
			Feature feature = index.get(token.getTokenIndex());
			if (feature != null) {
				Feature matched = feature.getMatched();
				if (matched != null) {
					return matched.getFormat();
				}
			}
		}
		return -1;
	}
}
