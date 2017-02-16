package net.certiv.adept.core;

import java.util.Set;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.Tool;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Form;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

/**
 * Eclipse TextEdit at
 * https://github.com/eclipse/eclipse.platform.text/blob/master/org.eclipse.text/src/org/eclipse/text/edits/TextEdit.java
 */
public class Formatter {

	private DocModel docModel;
	private Document doc;
	private ParseData data;
	private Index index;
	private OutputBuilder buffer;

	private int currIndent;
	private int priorIndent;
	private int priorBlankCnt;

	public Formatter(DocModel model) {
		this.docModel = model;
		this.doc = model.getDocument();
		this.data = doc.getParse();
		this.buffer = new OutputBuilder();
	}

	public OutputBuilder process() {
		if (!doc.getContent().isEmpty()) {

			// doc features indexed by starting token offset
			index = new Index(data.getTokenStream().size() - 1);
			for (Feature feature : docModel.getFeatures()) {
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
				line.clear();
			}
			if (Tool.settings.forceLastLineBlank) {
				buffer.ensureLastLineTerminated();
			}
		}
		return buffer;
	}

	private void processLine(TokenLine line) {
		// Log.debug(this, String.format("Process line %s: %s", buffer.size(), line.content(true)));
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
		AdeptToken token = line.get(info.first); // first real token
		int format = getMatchedFormat(token);
		if (format != -1) {
			currIndent = 0;
			Set<Facet> facets = Facet.get(format);
			if (facets.contains(Facet.INDENTED)) {
				currIndent = priorIndent + Facet.getDentation(format);
				currIndent = currIndent > 0 ? currIndent : 0;
			}

			if (currIndent > 0) {
				String indent = Strings.createIndent(Tool.settings.tabWidth, Tool.settings.useTabs, currIndent);
				buffer.add(indent);
			}
		}
	}

	private void processTokens(TokenLine line, LineInfo info) {
		for (int idx = info.first; idx < info.terminator; idx++) {
			AdeptToken tokenCurr = line.get(idx);
			if (tokenCurr.getType() == data.HWS) continue;

			AdeptToken tokenNext = line.getNextReal(idx);
			int formCurr = getMatchedFormat(tokenCurr);
			int formNext = getMatchedFormat(tokenNext);
			Set<Facet> facets = Form.resolveOverlap(formCurr, formNext);

			buffer.add(tokenCurr.getText());
			// if (facets.isEmpty()) {
			// buffer.add(" ");
			// } else
			if (facets.contains(Facet.ALIGNED_ABOVE) || facets.contains(Facet.ALIGNED_BELOW)) {
				// TODO: calc actual alignment for next real
				buffer.add(line.getNextHws(idx).getText());
			} else if (facets.contains(Facet.WIDE_AFTER)) {
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

	private int getMatchedFormat(AdeptToken token) {
		if (token == null) return -1;
		if (token.getType() == data.HWS || token.getType() == data.VWS) {
			Log.error(this, "Invalid token to match: " + token.toString());
			return -1;
		}

		Feature feature = index.get(token.getTokenIndex());
		if (feature != null) {
			Feature matched = feature.getMatched();
			if (matched != null) {
				return matched.getFormat();
			}
		}
		return -1;
	}
}
