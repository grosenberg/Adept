package net.certiv.adept.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.model.EdgeType;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ParseData;

/**
 * Identifies rule, terminal, and comment features that exist within a well-defined local group. The
 * 'local' scope is defined for terminal node root features, including tokens and comments.
 * 
 * The local scope includes
 * <ul>
 * <li>SIB: other children of the immediate parent
 * <li>RENT: parent, parent^n, for n = [2..LEVELS]
 * <li>KIN: and children of parent^n
 * <li>BLA: comments before, after, above, and below
 * </ul>
 */
public class Group {

	private static final int LEVELS = 4;

	private ParseData data;
	private Feature root;

	public Group(ParseData data) {
		this.data = data;
	}

	/**
	 * Adds edges to the root feature for those leaf features within the local space. The root is, by
	 * definition, a node: terminal or comment.
	 */
	public void addLocalEdges(Feature root) {
		this.root = root;
		ParseTree ctx = data.contextFeatureIndex.inverse().get(root);
		addEnclosing(ctx.getParent());
		addComments(ctx);
	}

	// adds the features that are direct ancestors of the root, excluding the root;
	// also adds the direct children of the ancestor features
	private void addEnclosing(ParseTree ctx) {
		for (int level = 1; ctx != null && ctx.getParent() != null && level < LEVELS; level++) {
			Feature feature = data.contextFeatureIndex.get(ctx);
			root.addEdge(feature, level == 1 ? EdgeType.SIB : EdgeType.RENT);
			addChildren(ctx);
			ctx = ctx.getParent();
		}
	}

	// adds those features that represent the direct children of the current context
	private void addChildren(ParseTree ctx) {
		for (int idx = 0; idx < ctx.getChildCount(); idx++) {
			ParseTree child = ctx.getChild(idx);
			Feature kin = data.contextFeatureIndex.get(child);
			if (kin != null) {
				root.addEdge(kin, EdgeType.KIN);
			}
		}
	}

	private void addComments(ParseTree ctx) {
		Token token = data.tokenTerminalIndex.inverse().get(ctx);
		int line = token.getLine() - 1;

		List<Token> tokens = data.lineTokensIndex.get(line);
		int dot = tokens.indexOf(token);
		if (dot > -1) addComments(line, dot);
		if (!isBlank(line - 1)) {
			addComments(line - 1, -1);
		}
		if (!isBlank(line + 1)) {
			addComments(line + 1, 0);
		}
	}

	private void addComments(int line, int dot) {
		List<Token> tokens = data.lineTokensIndex.get(line);
		if (tokens == null) return;
		if (dot == -1) dot = tokens.size();

		// lead
		List<Token> lead = new ArrayList<>(tokens.subList(0, dot));
		Collections.reverse(lead);
		for (Token tok : lead) {
			if (isHorzWS(tok)) continue;
			if (isComment(tok)) {
				Feature comment = data.tokenStartFeatureIndex.get(tok);
				root.addEdge(comment, EdgeType.COMMENT);
			}
			break;
		}

		// trail
		if (dot < tokens.size() - 1) {
			List<Token> trail = tokens.subList(dot + 1, tokens.size());
			for (Token tok : trail) {
				if (isHorzWS(tok)) continue;
				if (isComment(tok)) {
					Feature comment = data.tokenStartFeatureIndex.get(tok);
					root.addEdge(comment, EdgeType.COMMENT);
				}
				break;
			}
		}
	}

	private boolean isBlank(int line) {
		List<Token> tokens = data.lineTokensIndex.get(line);
		if (tokens == null) return true;
		for (Token token : tokens) {
			if (!isWhitespace(token)) return false;
		}
		return true;
	}

	private boolean isWhitespace(Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
	}

	private boolean isHorzWS(Token token) {
		return token.getType() == data.HWS;
	}

	private boolean isComment(Token token) {
		return token.getType() == data.BLOCKCOMMENT || token.getType() == data.LINECOMMENT;
	}
}
