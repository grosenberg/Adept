package net.certiv.adept.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.model.Bias;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Builder extends ParseRecord {

	private CoreMgr mgr;
	private List<Integer> exTypes;

	public Builder() {
		super(null);
	}

	public Builder(CoreMgr mgr, Document doc) {
		super(doc);
		this.mgr = mgr;

		if (doc != null) doc.setParseData(this);
		exTypes = mgr.excludedLangTypes();
	}

	/**
	 * Evaluates a rule context to build the corresponding set of features. Called from the parse-tree
	 * walker.
	 *
	 * @param ctx rule context
	 */
	public void evaluateRuleContext(ParserRuleContext ctx) {
		if (ctx.getChildCount() > 0) {
			List<ParseTree> parents = getAncestors(ctx);
			List<ParseTree> lead = new ArrayList<>();
			for (ParseTree child : ctx.children) {
				if (child instanceof ErrorNode) {
					String err = ((ErrorNode) child).getText();
					String msg = String.format("Failed to parse: %s", Utils.escapeWhitespace(err, false));
					Log.debug(this, msg);

				} else if (child instanceof TerminalNode) {
					TerminalNode node = (TerminalNode) child;
					AdeptToken token = (AdeptToken) node.getSymbol();
					token.setTerminal(node);
					defineNodeFeature(parents, lead, token);

					AdeptToken left = getCommentLeft(token);
					AdeptToken right = getCommentRight(token);
					defineCommentFeatures(parents, lead, left, token, right);
				}
				lead.add(0, child);
			}
			return;
		}
		// glorious abundance of caution
		String rule = parser.getRuleNames()[ctx.getRuleIndex()];
		Log.error(this, rule + " has no children.");
	}

	// ---------------------------------------------------------------------

	/**
	 * Define a new feature focused on a given feature node. All lists ordered from closest to farthest
	 * relative to the feature node.
	 *
	 * @param parents ancestor list
	 * @param lead leading nodes in the current context
	 * @param node the feature node
	 */
	private void defineNodeFeature(List<ParseTree> parents, List<ParseTree> lead, AdeptToken token) {

		// details of the current context
		ParserRuleContext ctx = (ParserRuleContext) parents.get(0);
		int rule = ctx.getRuleIndex();
		int ruleType = rule << 16;
		if (exTypes.contains(ruleType)) {
			if (ruleType == ERR_RULE) {
				Log.debug(this, String.format("Error rule: %s", Utils.escapeWhitespace(ctx.getText(), false)));
			}
			return;
		}

		// details of the target token
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				Log.debug(this, String.format("Error token: %s", Utils.escapeWhitespace(token.getText(), false)));
			}
			return;
		}

		token.setKind(vars.contains(type) ? Kind.VAR : Kind.TERMINAL);
		token.setNodeName(lexer.getVocabulary().getDisplayName(type));
		token.setVisCol(tokenVisColIndex.get(token));

		Token next = findNextRight(token.getTokenIndex());
		token.setSpacingRight(evalSpacing(token.getTokenIndex(), next.getTokenIndex()));
		token.setWsRight(findWsRight(token.getTokenIndex()));

		Feature feature = Feature.create(mgr, doc.getDocId(), genPath(lead, parents), token, Bias.RIGHT);

		tokenFeatureIndex.put(token, feature);
		typeSet.add(type);
	}

	private void defineCommentFeatures(List<ParseTree> parents, List<ParseTree> lead, AdeptToken left, AdeptToken real,
			AdeptToken right) {

		List<Integer> ancestors = genPath(lead, parents);

		if (left != null) {
			int type = left.getType();
			left.setKind(type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT);
			left.setVisCol(tokenVisColIndex.get(left));

			left.setSpacingRight(evalSpacing(left.getTokenIndex(), real.getTokenIndex()));
			left.setWsRight(findWsRight(left.getTokenIndex()));

			Feature feature = Feature.create(mgr, doc.getDocId(), ancestors, left, Bias.RIGHT);

			tokenFeatureIndex.put(left, feature);
			typeSet.add(type);
		}

		if (right != null) {
			int type = right.getType();
			right.setKind(type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT);
			right.setVisCol(tokenVisColIndex.get(right));

			right.setSpacingLeft(evalSpacing(real.getTokenIndex(), right.getTokenIndex()));
			right.setWsLeft(findWsLeft(right.getTokenIndex()));

			Feature feature = Feature.create(mgr, doc.getDocId(), ancestors, right, Bias.LEFT);

			tokenFeatureIndex.put(right, feature);
			typeSet.add(type);
		}
	}

	// ---------------------------------------------------------------------

	private AdeptToken getCommentLeft(AdeptToken real) {
		List<Token> hidden = tokenStream.getHiddenTokensToLeft(real.getTokenIndex());
		Collections.reverse(hidden);
		AdeptToken comment = findComment(hidden);
		if (comment == null) return null;

		Interval range = new Interval(comment.getTokenIndex(), real.getTokenIndex());
		comment.setSpacingRight(evalSpacing(range));
		comment.setWsRight(tokenStream.getText(range));
		return comment;
	}

	private AdeptToken getCommentRight(AdeptToken real) {
		List<Token> hidden = tokenStream.getHiddenTokensToRight(real.getTokenIndex());
		AdeptToken comment = findComment(hidden);
		if (comment == null) return null;

		Interval range = new Interval(real.getTokenIndex(), comment.getTokenIndex());
		comment.setSpacingLeft(evalSpacing(range));
		comment.setWsLeft(tokenStream.getText(range));
		return comment;
	}

	private AdeptToken findComment(List<Token> hidden) {
		for (Token token : hidden) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				AdeptToken comment = (AdeptToken) token;
				Kind kind = type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT;
				comment.setKind(kind);
				return comment;
			}
		}
		return null;
	}

	private Spacing evalSpacing(Interval range) {
		return evalSpacing(range.a, range.b);
	}

	// determine the Spacing between two tokens as defined by their token indexes in the lexer stream
	private Spacing evalSpacing(int from, int to) {
		if (from < to) {
			if (from + 1 == to) return Spacing.NONE;

			int hws = 0;
			int vws = 0;
			int tabWidth = doc.getTabWidth();
			for (Token token : tokenStream.get(from + 1, to - 1)) {
				int type = token.getType();
				if (type == HWS) {
					hws += Strings.measureVisualWidth(token.getText(), tabWidth);
				} else if (type == VWS) {
					vws++;
				}
			}

			if (vws > 1) return Spacing.VFLEX;
			if (vws == 1) return Spacing.VLINE;
			if (hws > 1) return Spacing.HFLEX;
			if (hws == 1) return Spacing.HSPACE;
		}
		return Spacing.UNKNOWN;
	}

	private List<Integer> genPath(List<?>... nodesArray) {
		List<Integer> path = new ArrayList<>();
		for (List<?> nodes : nodesArray) {
			for (Object node : nodes) {
				if (node instanceof ParserRuleContext) {
					path.add(((ParserRuleContext) node).getRuleIndex() << 16);
				} else if (node instanceof TerminalNode) {
					path.add(((TerminalNode) node).getSymbol().getType());
				} else if (node instanceof Token) {
					path.add(((Token) node).getType());
				}
			}
		}
		return path;
	}

	private String findWsLeft(int idx) {
		StringBuilder sb = new StringBuilder();
		for (Token token : tokenStream.getHiddenTokensToLeft(idx)) {
			int type = token.getType();
			if (type != BLOCKCOMMENT && type != LINECOMMENT) {
				sb.append(token.getText());
			}
		}
		return sb.toString();
	}

	private String findWsRight(int idx) {
		StringBuilder sb = new StringBuilder();
		for (Token token : tokenStream.getHiddenTokensToRight(idx)) {
			int type = token.getType();
			if (type != BLOCKCOMMENT && type != LINECOMMENT) {
				sb.append(token.getText());
			}
		}
		return sb.toString();
	}

	// ---------------------------------------------------------------------

	private Token findNextRight(int idx) {
		int hidden = tokenStream.getHiddenTokensToRight(idx).size();
		int next = idx + hidden + 1;
		next = next < tokenStream.size() ? next : tokenStream.size() - 1;
		return tokenStream.get(next);
	}

	/** Builds a source line->visual offset->token index. Built prior to feature extraction. */
	public void index() {
		int tabWidth = doc.getTabWidth();
		Token start = null; // at start of current line
		int line = -1;		// current line (0..n)

		for (Token token : getTokens()) {
			int num = token.getLine() - 1;
			if (num > line) {
				line = num;
				start = token;
			}

			List<Token> tokenList = lineTokensIndex.get(line);
			if (tokenList == null) {
				tokenList = new ArrayList<>();
				lineTokensIndex.put(line, tokenList);
			}
			tokenList.add(token);

			int visCol = getVisualColumn(start, token, tabWidth);
			tokenVisColIndex.put(token, visCol);
		}
	}

	// parents of given context, including the current context
	// ordered from the current context to farthest parent context
	private List<ParseTree> getAncestors(ParserRuleContext ctx) {
		List<ParseTree> parents = new ArrayList<>();
		ParserRuleContext parent = ctx;
		while (parent != null) {
			parents.add(parent);
			parent = parent.getParent();
		}
		return parents;
	}

	private int getVisualColumn(Token start, Token mark, int tabWidth) {
		if (start == null || start == mark) return 0;

		int beg = start.getStartIndex();
		int end = mark.getStartIndex() - 1;
		String text = mark.getInputStream().getText(new Interval(beg, end));
		return Strings.measureVisualWidth(text, tabWidth);
	}
}
