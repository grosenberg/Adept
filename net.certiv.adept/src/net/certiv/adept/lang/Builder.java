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
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Builder extends ParseRecord {

	private static final int LIMIT = 6; // ancestor path limit

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
		if (ctx.getChildCount() == 0) {
			// glorious abundance of caution
			String rule = getRuleName(ctx.getRuleIndex());
			Log.error(this, rule + " has no children.");
			return;
		}

		List<ParseTree> parents = getAncestors(ctx);
		for (ParseTree child : ctx.children) {
			if (child instanceof ErrorNode) {
				String err = ((ErrorNode) child).getText();
				String msg = String.format("Failed to parse: %s", Utils.escapeWhitespace(err, false));
				Log.debug(this, msg);

			} else if (child instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) child;
				AdeptToken token = (AdeptToken) node.getSymbol();
				if (token.getType() == Token.EOF) continue;

				defineFeature(parents, token);

				AdeptToken left = findCommentLeft(token);
				if (left != null) defineFeature(parents, left);
				AdeptToken right = findCommentRight(token);
				if (right != null) defineFeature(parents, right);
			}
		}
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
	private void defineFeature(List<ParseTree> parents, AdeptToken token) {

		// current context
		ParserRuleContext ctx = (ParserRuleContext) parents.get(0);
		int rule = ctx.getRuleIndex();
		int ruleType = rule << 16;
		if (exTypes.contains(ruleType)) {
			if (ruleType == ERR_RULE) {
				Log.debug(this, String.format("Error rule: %s", Utils.escapeWhitespace(ctx.getText(), false)));
			}
			return;
		}

		// target token
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				Log.debug(this, String.format("Error token: %s", Utils.escapeWhitespace(token.getText(), false)));
			}
			return;
		}

		token.setKind(evalKind(type));
		token.setNodeName(lexer.getVocabulary().getDisplayName(type));
		token.setVisCol(tokenVisColIndex.get(token));

		int idx = token.getTokenIndex();
		Token left = findRealLeft(idx);
		if (left != null) {
			token.setTokenLeft(left.getType());
			token.setSpacingLeft(evalSpacing(left.getTokenIndex(), idx));
			token.setWsLeft(findWsLeft(idx));
		}

		Token right = findRealRight(idx);
		if (right != null) {
			token.setTokenRight(right.getType());
			token.setSpacingRight(evalSpacing(idx, right.getTokenIndex()));
			token.setWsRight(findWsRight(idx));
		}

		Feature feature = Feature.create(mgr, doc.getDocId(), genPath(parents), token);

		featureIndex.put(token, feature);
		typeSet.add(type);
	}



	// ---------------------------------------------------------------------

	private AdeptToken findCommentLeft(AdeptToken token) {
		List<Token> hidden = getHiddenTokensToLeft(token.getTokenIndex());
		Collections.reverse(hidden);
		return findComment(hidden);
	}

	private AdeptToken findCommentRight(AdeptToken token) {
		List<Token> hidden = getHiddenTokensToRight(token.getTokenIndex());
		return findComment(hidden);
	}

	private AdeptToken findComment(List<Token> hidden) {
		for (Token token : hidden) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				return (AdeptToken) token;
			}
		}
		return null;
	}

	private Kind evalKind(int type) {
		if (type == BLOCKCOMMENT) {
			return Kind.BLOCKCOMMENT;
		} else if (type == LINECOMMENT) {
			return Kind.LINECOMMENT;
		} else {
			return vars.contains(type) ? Kind.VAR : Kind.TERMINAL;
		}
	}

	// characterize the white space between two tokens (exclusive)
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
		for (Token token : getHiddenTokensToLeft(idx)) {
			int type = token.getType();
			if (type != BLOCKCOMMENT && type != LINECOMMENT) {
				sb.append(token.getText());
			}
		}
		return sb.toString();
	}

	private String findWsRight(int idx) {
		StringBuilder sb = new StringBuilder();
		for (Token token : getHiddenTokensToRight(idx)) {
			int type = token.getType();
			if (type != BLOCKCOMMENT && type != LINECOMMENT) {
				sb.append(token.getText());
			}
		}
		return sb.toString();
	}

	private Token findRealLeft(int idx) {
		for (int jdx = idx - 1; jdx > -1; jdx--) {
			Token left = tokenStream.get(jdx);
			if (left.getChannel() == Token.DEFAULT_CHANNEL) return left;
		}
		return null;
	}

	private Token findRealRight(int idx) {
		for (int jdx = idx + 1, len = tokenStream.size(); jdx < len; jdx++) {
			Token right = tokenStream.get(jdx);
			if (right.getChannel() == Token.DEFAULT_CHANNEL) return right;
		}
		return null;
	}

	// ---------------------------------------------------------------------

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
		for (int idx = 0; parent != null && idx < LIMIT; idx++) {
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
