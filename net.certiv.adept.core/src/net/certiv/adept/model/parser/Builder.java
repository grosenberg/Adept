package net.certiv.adept.model.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Format;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.util.Group;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Builder extends ParseData {

	private ProcessMgr mgr;
	private List<Integer> exTypes;
	private Group group;

	public Builder() {
		super(null);
	}

	public Builder(ProcessMgr mgr, Document doc) {
		super(doc);
		this.mgr = mgr;

		if (doc != null) doc.setParseData(this);
		exTypes = mgr.excludedLangTypes();
		group = new Group(this);
	}

	public List<Feature> getAllFeatures() {
		return new ArrayList<>(contextFeatureIndex.values());
	}

	public void annotateRule(ParserRuleContext ctx) {
		int rule = ctx.getRuleIndex();
		int type = rule << 16;
		if (exTypes.contains(type)) {
			if (type == ERR_RULE) {
				Log.debug(this, String.format("Skipping %s", Utils.escapeWhitespace(ctx.getText(), false)));
			}
			return;
		}

		Token start = ctx.start;
		Token stop = ctx.stop;
		if (stop.getTokenIndex() < start.getTokenIndex()) {
			stop = start;
		}

		String aspect = parser.getRuleNames()[rule];
		Format format = new Format(this, ctx);
		int length = ruleLength(start, stop);
		Feature feature = Feature.create(mgr, Kind.RULE, aspect, doc.getDocId(), type, start, stop, length, format,
				false, false);
		contextFeatureIndex.put(ctx, feature);
		tokenRuleIndex.put(start, ctx);
		typeSet.add(type);
	}

	public void annotateTerminal(ParserRuleContext ctx, TerminalNode terminal) {
		Token token = terminal.getSymbol();
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				Log.debug(this, String.format("Skipping %s %s", Utils.escapeWhitespace(ctx.getText(), false), token));
			}
			return;
		}

		String aspect = lexer.getVocabulary().getDisplayName(type);
		Format format = new Format(this, terminal);
		Feature feature = Feature.create(mgr, Kind.TERMINAL, aspect, doc.getDocId(), type, token, format, isVar(type));
		feature.setAncestorPath(genAncestorPath(terminal));
		contextFeatureIndex.put(terminal, feature);
		tokenTerminalIndex.put(token, terminal);
		tokenStartFeatureIndex.put(token.getTokenIndex(), feature);
		tokenTypeFeatureIndex.put(token.getType(), feature);
		typeSet.add(type);
	}

	public void annotateComments() {
		for (Token token : tokenStream.getTokens()) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				String aspect = lexer.getVocabulary().getDisplayName(type);
				Format format = new Format(this, token);
				Kind kind = type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT;
				Feature feature = Feature.create(mgr, kind, aspect, doc.getDocId(), type, token, format);
				TerminalNode terminal = new TerminalNodeImpl(token);
				contextFeatureIndex.put(terminal, feature);
				tokenTerminalIndex.put(token, terminal);
				tokenStartFeatureIndex.put(token.getTokenIndex(), feature);
				tokenTypeFeatureIndex.put(token.getType(), feature);
				typeSet.add(type);
			}
		}
	}

	private int ruleLength(Token start, Token stop) {
		int beg = start.getTokenIndex();
		int end = stop.getTokenIndex();
		if (beg == -1 || end == -1) return 2;

		int len = 0;
		for (Token token : getTokenStream().get(beg, end)) {
			if (isWsOrComment(token.getType())) continue;
			len++;
		}
		return len;
	}

	// starts with the node type and ends with the parse tree root rule index
	private List<Integer> genAncestorPath(TerminalNode node) {
		List<Integer> path = new ArrayList<>();
		path.add(node.getSymbol().getType());
		RuleContext parent = (RuleContext) node.getParent();
		while (parent != null) {
			path.add(parent.getRuleIndex());
			parent = parent.getParent();
		}
		Collections.reverse(path);
		return path;
	}

	/** Generate local edge connections for all non-RULE root features. */
	public void genLocalEdges() {
		for (Feature feature : contextFeatureIndex.values()) {
			switch (feature.getKind()) {
				case RULE:
					break;
				case BLOCKCOMMENT:
				case LINECOMMENT:
				case TERMINAL:
					group.addLocalEdges(feature);
					break;
			}
		}
	}

	private boolean isVar(int type) {
		for (int var : VARS) {
			if (var == type) return true;
		}
		return false;
	}

	/** Builds a source line->visual offset->token index */
	public void index() {
		Token begToken = null;
		int line = -1;

		for (Token token : getTokens()) {
			int num = token.getLine() - 1;
			if (num > line) {
				line = num;
				begToken = token;
			}

			List<Token> tokenList = lineTokensIndex.get(line);
			if (tokenList == null) {
				tokenList = new ArrayList<>();
				lineTokensIndex.put(line, tokenList);
			}
			tokenList.add(token);

			int pos = getVisualColumn(begToken, token);
			tokenVisOffsetIndex.put(token, pos);
		}
	}

	private int getVisualColumn(Token start, Token mark) {
		if (start == null || start == mark) return 0;

		int beg = start.getStartIndex();
		int end = mark.getStartIndex() - 1;
		String text = mark.getInputStream().getText(new Interval(beg, end));
		return Strings.measureVisualWidth(text, doc.getTabWidth());
	}
}
