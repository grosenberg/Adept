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

	private static Comparator<Feature> COMP = new Comparator<Feature>() {

		@Override
		public int compare(Feature f1, Feature f2) {
			if (f1.getLine() < f2.getLine()) return -1;
			if (f1.getLine() > f2.getLine()) return 1;
			if (f1.getCol() < f2.getCol()) return -1;
			if (f1.getCol() > f2.getCol()) return 1;
			return 0;
		}
	};

	private ParseData data;
	private Feature locus;
	private TreeSet<Feature> locals;

	public Group(ParseData data) {
		this.data = data;
		locals = new TreeSet<>(COMP);
	}

	/**
	 * Sets the locus feature for the local space to search. The locus is, by definition, a node:
	 * termnial node or comment.
	 */
	public void setLocus(Feature locus) {
		this.locus = locus;
		locals.clear();
	}

	public Set<Feature> getLocalFeatures() {
		Token token = data.getTokens().get(locus.getStart());
		TerminalNode node = data.nodeIndex.get(token);
		if (node == null) {
			Log.error(this, "Locus node not found for feature start token: " + token);
			return locals;
		}
		int line = token.getLine() - 1;

		addEnclosing((ParserRuleContext) node.getParent());
		addAdjacent(token, line);
		addSame(token, line);

		return locals;
	}

	// adds the features that are direct ancestors of the locus, excluding the root
	// also adds the direct children of the ancestor features
	private void addEnclosing(ParserRuleContext ctx) {
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

	private void addAdjacent(Token token, int line) {
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

	// add same token type from prior line
	private void addSame(Token token, int line) {
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

	@SuppressWarnings("unused")
	private boolean isComment(Token token) {
		return token.getType() == data.BLOCKCOMMENT || token.getType() == data.LINECOMMENT;
	}
}
