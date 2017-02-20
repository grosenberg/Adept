package net.certiv.adept.topo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.util.Log;

/**
 * Identifies features that exist within a well-defined local group. The 'local' scope is defined
 * only for a locus feature representing a node. A node feature includes tokens and comments.
 */
public class Group {

	private static final int NODE2RULE = 4;
	private static final int NODE2NODE = 2;

	private Feature locus;
	private ParseData data;

	public Group(ParseData data) {
		this.data = data;
	}

	public void setLocus(Feature locus) {
		this.locus = locus;
	}

	public Set<Feature> getLocalFeatures() {
		Set<Feature> locals = new LinkedHashSet<>();
		Token token = data.getTokens().get(locus.getStart());
		TerminalNode node = data.nodeIndex.get(token);
		if (node == null) {
			Log.error(this, "Node not found for token " + token);
			return locals;
		}

		int line = token.getLine() - 1;
		ParserRuleContext parent = (ParserRuleContext) node.getParent();
		if (parent != null) {
			addEnclosing(locals, parent);
		}
		addAdjacent(locals, token, line);
		addSame(locals, token, line);

		return locals;
	}

	private void addEnclosing(Set<Feature> locals, ParserRuleContext parent) {
		int idx = 0;
		while (parent != null && idx < NODE2RULE) {
			addChildren(locals, parent, false);
			if (parent.getParent() != null) { // no root feature
				Feature feature = data.ruleIndex.get(parent);
				locals.add(feature);
			}
			parent = parent.getParent();
			idx++;
		}
	}

	private void addAdjacent(Set<Feature> locals, Token token, int line) {
		List<Token> tokens = data.lineIndex.get(line);
		int ofs = tokens.indexOf(token);

		// prior
		int cnt = 0;
		for (int i = ofs - 1; i >= 0; i--) {
			Token t = tokens.get(i);
			TerminalNode n = data.nodeIndex.get(t);
			if (n != null) {
				Feature feature = data.terminalIndex.get(n);
				if (feature != null) {
					locals.add(feature);
					cnt++;
				} else {
					Log.error(this, "Null adjacent node");
				}
			} else {
				ParserRuleContext ctx = data.contextIndex.get(t);
				if (ctx != null) {
					cnt += addChildren(locals, ctx, false);
				}
			}
			if (cnt > NODE2NODE) break;
		}

		// following
		cnt = 0;
		for (int i = ofs + 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			TerminalNode n = data.nodeIndex.get(t);
			if (n != null) {
				Feature feature = data.terminalIndex.get(n);
				if (feature != null) {
					locals.add(feature);
					cnt++;
				} else {
					Log.error(this, "Null adjacent node");
				}
			} else {
				ParserRuleContext ctx = data.contextIndex.get(t);
				if (ctx != null) {
					cnt += addChildren(locals, ctx, false);
				}
			}
			if (cnt > NODE2NODE) break;
		}
	}

	// add same token type from prior line
	private void addSame(Set<Feature> locals, Token token, int line) {
		int type = token.getType();
		int vPos = data.visIndex.get(token);

		int priorLine = nonBlankLine(line, -1);
		if (priorLine > -1) {
			List<Token> tokens = data.lineIndex.get(priorLine);
			for (Token t : tokens) {
				TerminalNode n = data.nodeIndex.get(t);
				if (n != null && type == t.getType()) {
					Feature feature = data.terminalIndex.get(n);
					locals.add(feature);

					if (vPos == data.visIndex.get(t)) {
						locus.setAligned(Facet.ALIGNED_ABOVE);
						feature.setAligned(Facet.ALIGNED_BELOW);
					}
				}
			}
		}

		int nextLine = nonBlankLine(line, 1);
		if (nextLine > -1) {
			List<Token> tokens = data.lineIndex.get(nextLine);
			for (Token t : tokens) {
				TerminalNode n = data.nodeIndex.get(t);
				if (n != null && type == t.getType()) {
					Feature feature = data.terminalIndex.get(n);
					locals.add(feature);

					if (vPos == data.visIndex.get(t)) {
						locus.setAligned(Facet.ALIGNED_BELOW);
						feature.setAligned(Facet.ALIGNED_ABOVE);
					}
				}
			}
		}
	}

	private int addChildren(Set<Feature> locals, ParserRuleContext parent, boolean inclVars) {
		int cnt = 0;
		if (parent.getChildCount() > 0) {
			for (ParseTree child : parent.children) {
				if (child instanceof TerminalNode) {
					Feature feature = data.terminalIndex.get(child);
					if (feature != null) {
						if (inclVars | !feature.isVar()) {
							locals.add(feature);
							cnt++;
						}
					}
				}
			}
		}
		return cnt;
	}

	private int nonBlankLine(int line, int dir) {
		while (line + dir >= 0 && line + dir < data.lineIndex.size()) {
			line += dir;
			if (!isBlank(line)) return line;
		}
		return -1;
	}

	private boolean isBlank(int line) {
		List<Token> tokens = data.lineIndex.get(line);
		if (tokens == null) return true;
		for (Token token : tokens) {
			if (!isWhitespace(token)) return true;
		}
		return false;
	}

	private boolean isWhitespace(Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
	}
}
