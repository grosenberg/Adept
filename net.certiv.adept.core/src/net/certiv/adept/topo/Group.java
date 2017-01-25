package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

/**
 * Identifies features that exist within a well-defined local group. The 'local' scope is defined
 * only for a locus feature representing a node. A node feature includes tokens and comments.
 */
public class Group {

	private static final int NODE2COMMENT = 4;
	private static final int NODE2RULE = 6;
	private static final int NODE2NODE = 4;

	private Feature locus;
	private ParseData data;
	private int tabWidth;

	public Group(ParseData data) {
		this.data = data;
	}

	public void setLocus(Feature locus, int tabWidth) {
		this.locus = locus;
		this.tabWidth = tabWidth;
	}

	public List<Feature> getLocalFeatures() {
		List<Feature> locals = new ArrayList<>();
		Token token = data.getTokens().get(locus.getStart());
		switch (locus.getKind()) {
			case RULE:
				ParserRuleContext ctx = data.contextIndex.get(token);
				if (ctx != null) {
					addChildren(locals, ctx);
				}
				break;
			default:
				TerminalNode node = data.nodeIndex.get(token);
				ParserRuleContext parent = (ParserRuleContext) node.getParent();
				if (parent != null) {
					int line = token.getLine() - 1;
					addEnclosing(locals, parent);
					addAdjacent(locals, token, line);
					addFromPrior(locals, token, line);
				}
		}

		return locals;
	}

	private void addChildren(List<Feature> locals, ParserRuleContext parent) {
		if (parent.getChildCount() > 0) {
			for (ParseTree child : parent.children) {
				if (child instanceof TerminalNode) {
					Feature feature = data.terminalIndex.get(child);
					if (feature != null) {
						locals.add(feature);
					}
				}
			}
		}
	}

	private void addEnclosing(List<Feature> locals, ParserRuleContext parent) {
		int idx = 0;
		while (parent != null && idx < NODE2RULE) {
			Feature feature = data.ruleIndex.get(parent);
			locals.add(feature);
			parent = parent.getParent();
			idx++;
		}
	}

	private void addAdjacent(List<Feature> locals, Token token, int line) {
		int idx = token.getTokenIndex();
		List<Token> tokens = data.lineIndex.get(line);
		for (Token t : tokens) {
			TerminalNode n = data.nodeIndex.get(t);
			if (n != null) {
				int dist = Math.abs(t.getTokenIndex() - idx);
				if (dist != 0 && (isComment(t) && dist < NODE2COMMENT || dist < NODE2NODE)) {
					Feature feature = data.terminalIndex.get(n);
					if (feature == null) Log.error(this, "Null adjacent node");
					locals.add(feature);
				}
			} else {
				ParserRuleContext ctx = data.contextIndex.get(t);
				if (ctx != null) {
					addChildren(locals, ctx);
				}
			}
		}
	}

	// add same token type from prior line; also assess alignment
	private void addFromPrior(List<Feature> locals, Token token, int line) {
		if (!isBlank(line)) {
			int type = token.getType();
			List<Token> currTokens = data.lineIndex.get(line);
			int currPos = getVisualColumn(currTokens.get(0), token);
			List<Token> prevTokens = prevLine(line);
			for (Token t : prevTokens) {
				TerminalNode n = data.nodeIndex.get(t);
				if (n != null && type == t.getType()) {
					Feature feature = data.terminalIndex.get(n);
					locals.add(feature);

					if (currPos == getVisualColumn(prevTokens.get(0), t)) {
						locus.setAligned(Form.ABOVE);
						feature.setAligned(Form.BELOW);
					}
				}
			}
		}
	}

	private int getVisualColumn(Token start, Token mark) {
		int beg = start.getStartIndex();
		int end = mark.getStartIndex() - 1;
		String text = mark.getInputStream().getText(new Interval(beg, end));
		return Strings.measureVisualWidth(text, tabWidth);
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

	private List<Token> prevLine(int line) {
		while (line - 1 >= 0) {
			line--;
			if (!isBlank(line)) return data.lineIndex.get(line);
		}
		return null;
	}
}
