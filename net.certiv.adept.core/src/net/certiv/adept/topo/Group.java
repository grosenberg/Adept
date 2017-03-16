package net.certiv.adept.topo;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
 * <p>
 * The local space of features is searched to collect the local group of features. Each local space
 * is defined relative to a local space locus feature and containing the features necessary to
 * define the topology of the local space. The definition of the topology, that is the description
 * of each local space, is sufficiently distinctive to allow accurate matching of like topologies as
 * an indication of allowed formatting.
 * <p>
 * In practical terms, each local space is defined as a function of the local space search
 * methodology as applied to a local space locus. The search function considers
 * <ul>
 * <li>features related through parentage, including the direct childeren of each such feature
 * <li>features that are adjacent: on the same line within a defined range
 * <li>features that are alike and aligned: on adjacent non-blank lines within a defined range with
 * the same visual column offset, where alike includes same feature type (same content length?)
 * </ul>
 */
public class Group {

	private static final int ENCLOSURES = 4;
	private static final int ADJACENTS = 2;
	private static final int ALIGNS = 6;

	// sort local features by line, col, and feature type
	private static Comparator<Feature> COMP = new Comparator<Feature>() {

		@Override
		public int compare(Feature a, Feature b) {
			if (a.getLine() < b.getLine()) return -1;
			if (a.getLine() > b.getLine()) return 1;
			if (a.getCol() < b.getCol()) return -1;
			if (a.getCol() > b.getCol()) return 1;
			if (a.getType() < b.getType()) return -1;
			if (a.getType() > b.getType()) return 1;
			return 0;
		}
	};

	private ParseData data;
	private Feature locus;

	public Group(ParseData data) {
		this.data = data;
	}

	/**
	 * Sets the locus feature for the local space to search. The locus is, by definition, a node:
	 * termnial node or comment.
	 */
	public void setLocus(Feature locus) {
		this.locus = locus;
	}

	public Set<Feature> getLocalFeatures() {
		TreeSet<Feature> locals = new TreeSet<>(COMP);
		Token token = data.getTokens().get(locus.getStart());
		TerminalNode node = data.nodeIndex.get(token);
		if (node == null) {
			Log.error(this, "Locus node not found for feature start token: " + token);
			return locals;
		}
		int line = token.getLine() - 1;

		addEnclosing(locals, (ParserRuleContext) node.getParent());
		addAligned(locals, token, line);
		addAdjacent(locals, token, line);
		addSame(locals, token, line);

		return locals;
	}

	// adds the features that are direct ancestors of the locus, excluding the root
	// also adds the direct children of the ancestor features
	private void addEnclosing(TreeSet<Feature> locals, ParserRuleContext ctx) {
		int idx = 0;
		while (ctx != null && idx < ENCLOSURES) {
			if (ctx.getParent() != null) { // no root feature
				Feature feature = data.ruleIndex.get(ctx);
				locals.add(feature);
				addChildren(locals, ctx);
			}
			ctx = ctx.getParent();
			idx++;
		}
	}

	// adds those features that represent the direct children of the current context
	private int addChildren(Set<Feature> locals, ParserRuleContext ctx) {
		int cnt = 0;
		if (ctx.getChildCount() > 0) {
			for (ParseTree child : ctx.children) {
				Feature feature = null;
				if (child instanceof TerminalNode) {
					feature = data.terminalIndex.get(child);
				} else if (child instanceof ParserRuleContext) {
					feature = data.ruleIndex.get(child);
				}
				if (feature != null) {
					locals.add(feature);
					cnt++;
				}
			}
		}
		return cnt;
	}

	private void addAdjacent(TreeSet<Feature> locals, Token token, int line) {
		List<Token> tokens = data.lineIndex.get(line);
		int mid = tokens.indexOf(token);

		// lead
		for (int i = mid - 1, cnt = 0; i >= 0 && cnt < ADJACENTS; i--) {
			Token t = tokens.get(i);
			TerminalNode n = data.nodeIndex.get(t);
			if (n != null) {
				Feature feature = data.terminalIndex.get(n);
				if (feature != null) {
					locals.add(feature);
					cnt++;
				}
			}
		}

		// tail
		for (int i = mid + 1, cnt = 0; i < tokens.size() && cnt < ADJACENTS; i++) {
			Token t = tokens.get(i);
			TerminalNode n = data.nodeIndex.get(t);
			if (n != null) {
				Feature feature = data.terminalIndex.get(n);
				if (feature != null) {
					locals.add(feature);
					cnt++;
				}
			}
		}
	}

	// add aligned token features on the non-blank lines before anad after the current line
	// stops in each direction at the limit of ALIGN_ANY or first real line without an aligned token
	private void addAligned(TreeSet<Feature> locals, Token token, int line) {
		int visCol = data.visIndex.get(token);
		int cnt = 0;
		int next = line;
		while (next != -1 && cnt < ALIGNS) {
			next = addAligned(locals, next, visCol, true);
			cnt++;
		}
		cnt = 0;
		next = line;
		while (next != -1 && cnt < ALIGNS) {
			next = addAligned(locals, next, visCol, false);
			cnt++;
		}
	}

	private int addAligned(TreeSet<Feature> locals, int line, int visCol, boolean asc) {
		int next = nonBlankLine(line, asc ? 1 : -1);
		if (next != -1) {
			List<Token> tokens = data.lineIndex.get(next);
			for (Token token : tokens) {
				int tokCol = data.visIndex.get(token);
				if (tokCol < visCol) continue;
				if (tokCol > visCol) return -1; // end on no-align found

				TerminalNode node = data.nodeIndex.get(token);
				if (node != null) {
					Feature feature = data.terminalIndex.get(node);
					locals.add(feature);
					break;
				}
			}
		}
		return next;
	}

	// add features of same token type found on surrounding lines
	private void addSame(TreeSet<Feature> locals, Token token, int line) {
		int type = token.getType();
		int beg = Math.max(nonBlankLine(line, -1), line);
		int end = Math.max(nonBlankLine(line, 1), line);

		for (int idx = beg; idx <= end; idx++) {
			List<Token> tokens = data.lineIndex.get(idx);
			if (tokens == null) continue;

			for (Token tok : tokens) {
				TerminalNode node = data.nodeIndex.get(tok);
				if (node != null && type == tok.getType()) {
					Feature feature = data.terminalIndex.get(node);
					locals.add(feature);
				}
			}
		}
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
			if (!isWhitespace(token)) return false;
		}
		return true;
	}

	private boolean isWhitespace(Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
	}

	@SuppressWarnings("unused")
	private boolean isComment(Token token) {
		return token.getType() == data.BLOCKCOMMENT || token.getType() == data.LINECOMMENT;
	}
}
