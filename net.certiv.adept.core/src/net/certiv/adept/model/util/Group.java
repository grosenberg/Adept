package net.certiv.adept.model.util;

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
	private static final int EDGES = 20;
	private static final int LINES = 4;

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
			int real = realTokenDistance(root.getStart(), feature.getStart(), false);
			root.addEdge(level == 1 ? EdgeType.SIB : EdgeType.RENT, feature, real);

			addChildren(ctx);
			if (root.getEdgeSet().size() > EDGES) break;
			ctx = ctx.getParent();
		}
	}

	// adds those features that represent the direct children of the current context
	private void addChildren(ParseTree ctx) {
		for (int idx = 0; idx < ctx.getChildCount(); idx++) {
			ParseTree child = ctx.getChild(idx);
			Feature kin = data.contextFeatureIndex.get(child);
			if (kin != null) {
				int real = realTokenDistance(root.getStart(), kin.getStart(), false);
				root.addEdge(EdgeType.KIN, kin, real);
			}
		}
	}

	private void addComments(ParseTree ctx) {
		Token token = data.tokenTerminalIndex.inverse().get(ctx);
		addComments(token, -1);
		addComments(token, +1);
	}

	private void addComments(Token token, int dir) {
		List<Token> tokens;
		if (dir == -1) {
			tokens = data.tokenStream.get(0, token.getTokenIndex());
			Collections.reverse(tokens);
		} else {
			tokens = data.tokenStream.get(token.getTokenIndex(), data.tokenStream.size());
		}

		boolean isComment = data.isComment(token.getType());
		int lines = 0;
		for (Token tok : tokens) {
			if (data.isVertWS(tok.getType())) {
				lines++;
			} else if (data.isHorzWS(tok.getType())) {
				; // skip
			} else if (data.isComment(tok.getType())) {
				Feature comment = data.tokenStartFeatureIndex.get(tok.getTokenIndex());
				int len = realTokenDistance(root.getStart(), comment.getStart(), true);
				root.addEdge(EdgeType.COMMENT, comment, len);
			} else if (tok != token) {
				if (isComment) {
					// add surrounding comments and reals
					Feature real = data.tokenStartFeatureIndex.get(tok.getTokenIndex());
					if (real != null) {
						int len = realTokenDistance(root.getStart(), real.getStart(), true);
						root.addEdge(EdgeType.NEAR, real, len);
					}
				} else {
					break;	// stop at first significant real
				}
			}
			if (root.getEdgeSet().size() > EDGES) break;
			if (lines >= LINES) break;
		}
	}

	private int realTokenDistance(int beg, int end, boolean inclComments) {
		if (beg == -1 || end == -1) return -99;

		int sign = 1;
		List<Token> tokens;
		if (beg > end) {
			tokens = data.getTokenStream().get(end, beg);
		} else {
			tokens = data.getTokenStream().get(beg, end);
			sign = -1;
		}

		int offset = 0;
		for (Token token : tokens) {
			if (data.isWhitespace(token.getType())) continue;
			if (!inclComments) {
				if (data.isComment(token.getType())) continue;
			}
			offset++;
		}
		return sign * offset;
	}
}
