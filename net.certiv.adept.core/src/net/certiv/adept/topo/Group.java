package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.util.Log;

/**
 * Identifies features that exist within a well-defined local group. The 'local' scope is defined
 * only for a locus feature representing a node. A node feature includes tokens and comments.
 */
public class Group {

	private static final int NODE2COMMENT = 1;
	private static final int NODE2RULE = 6;
	private static final int NODE2NODE = 4;

	private Feature locus;
	private ParseData data;

	public Group(ParseData data) {
		this.data = data;
	}

	public void setLocus(Feature locus) {
		this.locus = locus;
	}

	public List<Feature> getLocalFeatures() {
		List<Feature> locals = new ArrayList<>();
		if (locus.getKind() == Kind.RULE) return locals;

		Token token = data.getTokens().get(locus.getStart());
		TerminalNode node = data.nodeIndex.get(token);
		if (node == null) {
			Log.error(this, "Node null");
		}
		ParserRuleContext parent = (ParserRuleContext) node.getParent();

		if (parent != null) {
			int line = token.getLine() - 1;

			addEnclosing(locals, parent, token);
			addAdjacent(locals, token, line);
			addFromPrior(locals, token, line);
		}

		return locals;
	}

	private void addEnclosing(List<Feature> locals, ParserRuleContext parent, Token token) {
		int idx = 0;
		while (parent != null && idx < NODE2RULE) {
			Feature feature = data.ruleIndex.get(parent);
			locals.add(feature);
			parent = parent.getParent();
			idx++;
		}
	}

	private void addAdjacent(List<Feature> locals, Token token, int line) {
		List<Token> tokens = data.lineIndex.get(line);
		if (tokens == null) {
			Log.error(this, "Line " + line);
		}
		int idx = token.getTokenIndex();
		for (Token t : tokens) {
			TerminalNode n = data.nodeIndex.get(line);
			if (n != null) {
				int dist = Math.abs(t.getTokenIndex() - idx);
				if (isComment(t) && dist < NODE2COMMENT || dist < NODE2NODE) {
					Feature feature = data.terminalIndex.get(n);
					locals.add(feature);
				}
			}
		}
	}

	// add same token type from prior line; also assess alignment
	private void addFromPrior(List<Feature> locals, Token token, int line) {
		if (!isBlank(line)) {
			int type = token.getType();
			List<Token> tokens = priorLine(line);
			for (Token t : tokens) {
				TerminalNode n = data.nodeIndex.get(line);
				if (n != null && type == t.getType()) {
					Feature feature = data.terminalIndex.get(n);
					locals.add(feature);

					if (token.getCharPositionInLine() == t.getCharPositionInLine()) {
						locus.setAligned(Form.ABOVE);
						feature.setAligned(Form.BELOW);
					}
				}
			}
		}
	}

	private boolean isComment(Token token) {
		return token.getType() == data.LINECOMMENT || token.getType() == data.BLOCKCOMMENT;
	}

	private boolean isWhitespace(Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
	}

	private boolean isBlank(int line) {
		List<Token> tokens = data.lineIndex.get(line);
		for (Token token : tokens) {
			if (!isWhitespace(token)) return true;
		}
		return false;
	}

	private List<Token> priorLine(int line) {
		while (line - 1 >= 0) {
			line--;
			if (!isBlank(line)) return data.lineIndex.get(line);
		}
		return null;
	}
}
