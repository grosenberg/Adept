/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.format.plan.Aligner;
import net.certiv.adept.format.plan.Indenter;
import net.certiv.adept.format.plan.Place;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Strings;

public class Builder extends Record {

	private static final String LineMsg = "%3d: %2d > %s";

	private CoreMgr mgr;
	private List<Integer> exTypes;

	private Tool tool;

	public Builder() {
		super(null);
	}

	public Builder(CoreMgr mgr, Document doc) {
		super(doc);
		this.mgr = mgr;
		this.tool = mgr.getTool();

		if (doc != null) doc.setBuilder(this);
		exTypes = mgr.excludedLangTypes();
	}

	// ---------------------------------------------------------------------

	/** Create indexes prior to feature construction. */
	public void index() {
		int tabWidth = doc.getTabWidth();
		int current = -1;			// current line (0..n)
		AdeptToken start = null;	// start of current line

		for (AdeptToken token : getTokens()) {
			int line = token.getLinePos();
			if (line > current) {	// track line changes
				current = line;
				start = token;
				lineStartIndex.put(current, token.getStartIndex());
				blanklines.put(current, true);	// assume blank
			}

			int type = token.getType();
			token.setKind(evalKind(type));
			if (!token.isWhitespace()) {			// formattable only
				token.setRefToken(new RefToken(doc.getDocId(), token));
				token.setVisPos(calcVisualPosition(start, token, tabWidth));

				tokenIndex.put(token.getTokenIndex(), token);
				lineTokensIndex.put(current, token);
				blanklines.put(current, false);	// correct assumption

				if (token.isComment()) commentIndex.add(token);
				if (token.isLineComment()) aligner.comment(current, token);
			}
		}

		for (Entry<Integer, List<AdeptToken>> entry : lineTokensIndex.entrySet()) {
			List<AdeptToken> tokens = entry.getValue();
			if (tokens.size() == 1) {
				tokens.get(0).setPlace(Place.SOLO);
			} else {
				int len = tokens.size();
				tokens.get(0).setPlace(Place.BEG);
				for (int idx = 1; idx < len - 1; idx++) {
					tokens.get(idx).setPlace(Place.MID);
				}
				tokens.get(tokens.size() - 1).setPlace(Place.END);
			}
		}
		// checkLineIndex();
	}

	// ---- Indent and aligner operations -----

	public Indenter indenter() {
		return indenter;
	}

	public Aligner aligner() {
		return aligner;
	}

	// ---- Feature recognition operations -----

	/**
	 * Evaluates a rule context to build the corresponding set of features. Called from the parse-tree
	 * walker.
	 *
	 * @param ctx the current rule context
	 */
	public void extractFeatures(ParserRuleContext ctx) {
		if (ctx.getChildCount() == 0) {
			// glorious abundance of caution
			String rule = getRuleName(ctx.getRuleIndex());
			tool.toolInfo(this, rule + " has no children.");
			return;
		}

		List<ParseTree> ancestors = getAncestors(ctx);
		int start = ctx.getStart().getTokenIndex();
		int stop = ctx.getStop().getTokenIndex();
		if (stop > start) contextIndex.put(start, stop, ancestors);

		for (ParseTree child : ctx.children) {
			if (child instanceof ErrorNode) {
				String err = ((ErrorNode) child).getText();
				String msg = String.format("Failed to parse: %s", Utils.escapeWhitespace(err, false));
				tool.toolInfo(this, msg);

			} else if (child instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) child;
				AdeptToken token = (AdeptToken) node.getSymbol();

				// parse tree feature
				if (token.getType() != Token.EOF) defineFeature(ancestors, token);
			}
		}
	}

	public void extractCommentFeatures() {
		for (AdeptToken comment : commentIndex) {
			int idx = comment.getTokenIndex();
			List<ParseTree> ancestors = contextIndex.encloses(idx, idx);
			if (ancestors == null) {
				ancestors = Collections.emptyList();
			}

			// comment feature
			defineFeature(ancestors, comment);
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
		if (!parents.isEmpty()) {
			ParserRuleContext ctx = (ParserRuleContext) parents.get(0);
			int rule = ctx.getRuleIndex();
			int ruleType = rule << 16;
			if (exTypes.contains(ruleType)) {
				if (ruleType == ERR_RULE) {
					tool.toolInfo(this, String.format("Error rule: %s", Utils.escapeWhitespace(ctx.getText(), false)));
				}
				return;
			}
		}

		// target token
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				tool.toolInfo(this, String.format("Error token: %s", Utils.escapeWhitespace(token.getText(), false)));
			}
			return;
		}

		token.setKind(evalKind(type));
		token.setNodeName(getTokenName(type));
		int tokenIdx = token.getTokenIndex();
		token.setDent(indenter.getDent(tokenIdx));

		RefToken ref = token.refToken();
		List<Integer> lAssocs = leftAssociates(token);
		List<Integer> rAssocs = rightAssociates(token);
		ref.createContext(lAssocs, rAssocs);

		AdeptToken left = getRealLeft(tokenIdx);
		if (left != null) {
			String ws = findWsLeft(tokenIdx);
			Spacing spacing = evalSpacing(left.getTokenIndex(), tokenIdx);
			ref.setLeft(left, spacing, ws);
		}

		AdeptToken right = getRealRight(tokenIdx);
		if (right != null) {
			String ws = findWsRight(tokenIdx);
			Spacing spacing = evalSpacing(tokenIdx, right.getTokenIndex());
			ref.setRight(right, spacing, ws);
		}

		Feature feature = Feature.create(mgr, doc, toRulePath(parents), token);
		index.put(token, feature);
		featureIndex.put(feature.getId(), feature);
	}

	// ---------------------------------------------------------------------

	// characterize the kind of token as identified by type
	private Kind evalKind(int type) {
		if (type == BLOCKCOMMENT) {
			return Kind.BLOCKCOMMENT;
		} else if (type == LINECOMMENT) {
			return Kind.LINECOMMENT;
		} else if (type == HWS || type == VWS) {
			return Kind.WHITESPACE;
		} else {
			return Kind.TERMINAL;
		}
	}

	// characterize the white space between two tokens (exclusive)
	private Spacing evalSpacing(int from, int to) {
		if (from < to) {
			if (from + 1 == to) return Spacing.NONE;
			return Spacing.characterize(getTextBetween(from, to), doc.getTabWidth());
		}
		return Spacing.UNKNOWN;
	}

	// convert ancestor list to integers
	private List<Integer> toRulePath(List<ParseTree> nodes) {
		List<Integer> path = new ArrayList<>();
		for (ParseTree node : nodes) {
			try {
				path.add(((ParserRuleContext) node).getRuleIndex());
			} catch (Exception e) {
				throw new IllegalArgumentException("Ancestors must be rules.", e);
			}
		}
		return path;
	}

	private String findWsLeft(int tokenIndex) {
		AdeptToken left = getRealLeft(tokenIndex);
		return getTextBetween(left.getTokenIndex(), tokenIndex);
	}

	private String findWsRight(int tokenIndex) {
		AdeptToken right = getRealRight(tokenIndex);
		return getTextBetween(tokenIndex, right.getTokenIndex());
	}

	private List<Integer> leftAssociates(AdeptToken token) {
		int line = token.getLinePos();
		List<AdeptToken> tokens = new ArrayList<>(lineTokensIndex.get(line));
		int idx = tokens.indexOf(token);
		try {
			tokens.subList(idx, tokens.size()).clear();
		} catch (Exception e) {
			tool.toolInfo(this, "Invalid 'linesTokenIndex'.");
		}

		while (tokens.size() < AssocLimit && line > 0) {
			line--;
			List<AdeptToken> prior = lineTokensIndex.get(line);
			if (prior != null) tokens.addAll(0, prior);
		}
		Collections.reverse(tokens);

		tokens.subList(Math.min(AssocLimit, tokens.size()), tokens.size()).clear();
		return ttypes(tokens);
	}

	private List<Integer> rightAssociates(AdeptToken token) {
		int line = token.getLinePos();
		List<AdeptToken> tokens = new ArrayList<>(lineTokensIndex.get(line));
		int idx = tokens.indexOf(token);
		tokens.subList(0, idx + 1).clear();

		int lines = Collections.max(lineTokensIndex.keySet());
		while (tokens.size() < AssocLimit && line < lines) {
			line++;
			List<AdeptToken> next = lineTokensIndex.get(line);
			if (next != null) tokens.addAll(next);
		}

		tokens.subList(Math.min(AssocLimit, tokens.size()), tokens.size()).clear();
		return ttypes(tokens);
	}

	// list of tokens -> list of token types
	private List<Integer> ttypes(List<AdeptToken> tokens) {
		List<Integer> ttypes = new ArrayList<>();
		for (AdeptToken token : tokens) {
			ttypes.add(token.getType());
		}
		return ttypes;
	}

	// ---------------------------------------------------------------------

	// parents of given context, including the current context
	// ordered from the current context to farthest parent context
	private List<ParseTree> getAncestors(ParserRuleContext ctx) {
		List<ParseTree> parents = new ArrayList<>();
		ParserRuleContext parent = ctx;
		for (int idx = 0; parent != null && idx < AncesLimit; idx++) {
			parents.add(parent);
			parent = parent.getParent();
		}
		return parents;
	}

	// 'start' is token at BOL - returns the visual offset of 'token' (0..n+)
	private int calcVisualPosition(Token start, Token token, int tabWidth) {
		if (start == null || start == token) return 0;

		int beg = start.getStartIndex();
		int end = token.getStartIndex() - 1;
		String text = token.getInputStream().getText(Interval.of(beg, end));
		int vpos = Strings.measureVisualWidth(text, tabWidth);
		return vpos;
	}

	// --------------------

	protected void checkLineIndex() {
		int lines = blanklines.size();
		for (int line = 0; line < lines; line++) {
			List<AdeptToken> tokens = lineTokensIndex.get(line);
			if (tokens == null) {
				String status = blanklines.get(line) ? "Blank" : "Does not agree with blanklines index";
				tool.toolInfo(this, String.format(LineMsg, line + 1, 0, status));

			} else {
				StringBuilder sb = new StringBuilder();
				for (AdeptToken token : tokens) {
					sb.append(String.format("%s(%s) ", token.place(), token.refToken().text));
				}
				tool.toolInfo(this, String.format(LineMsg, line + 1, tokens.size(), sb.toString()));
			}
		}
	}
}
