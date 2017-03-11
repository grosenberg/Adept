package net.certiv.adept.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.topo.Form;
import net.certiv.adept.topo.Group;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Collector extends ParseData {

	private List<Integer> exTypes;

	// unique list of features that represent the parsed document
	private HashSet<Feature> features;
	private Group group;

	public Collector(Document doc) {
		super(doc);
		if (doc != null) doc.setParse(this);
		features = new LinkedHashSet<>();
		exTypes = Tool.mgr.excludedLangTypes();
		group = new Group(this);
	}

	public List<Feature> getFeatures() {
		return new ArrayList<>(features);
	}

	public List<Feature> getNonRuleFeatures() {
		List<Feature> nonRules = new ArrayList<>();
		for (Feature feature : features) {
			if (feature.getKind() == Kind.RULE) continue;
			nonRules.add(feature);
		}
		return nonRules;
	}

	public void annotateRule(ParserRuleContext ctx) {
		int rule = ctx.getRuleIndex();
		long type = ((long) rule) << 32;
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
		int format = Form.characterize(this, ctx);
		Feature feature = Feature.create(Kind.RULE, aspect, doc.getDocId(), type, start, stop, format, false);
		features.add(feature);
		contextIndex.put(start, ctx);
		ruleIndex.put(ctx, feature);
		typeSet.add(type);
	}

	public void annotateNode(ParserRuleContext ctx, TerminalNode node) {
		Token token = node.getSymbol();
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				Log.debug(this, String.format("Skipping %s %s", Utils.escapeWhitespace(ctx.getText(), false), token));
			}
			return;
		}

		String aspect = lexer.getVocabulary().getDisplayName(type);
		int format = Form.characterize(this, node);
		Feature feature = Feature.create(Kind.NODE, aspect, doc.getDocId(), type, token, format, isVar(type));
		features.add(feature);
		nodeIndex.put(token, node);
		terminalIndex.put(node, feature);
		tokenIndex.put(token.getTokenIndex(), feature);
		typeSet.add((long) type);
	}

	public void annotateComments() {
		for (Token token : stream.getTokens()) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				String aspect = lexer.getVocabulary().getDisplayName(type);
				int format = Form.characterize(this, token);
				Kind kind = type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT;
				Feature feature = Feature.create(kind, aspect, doc.getDocId(), type, token, format);
				TerminalNode node = new TerminalNodeImpl(token);
				features.add(feature);
				nodeIndex.put(token, node);
				terminalIndex.put(node, feature);
				tokenIndex.put(token.getTokenIndex(), feature);
				typeSet.add((long) type);
			}
		}
	}

	/** Generate local edge connections for all non-RULE root features. */
	public void genLocalEdges() {
		createTokenIndex();
		for (Feature root : features) {
			if (root.getKind() == Kind.RULE) continue;

			group.setLocus(root);
			Set<Feature> leafs = group.getLocalFeatures(); // ordered by line, col
			for (Feature leaf : leafs) {
				root.addEdge(leaf);
			}
		}
	}

	private boolean isVar(int type) {
		for (int v : VARS) {
			if (v == type) return true;
		}
		return false;
	}

	/** Builds a source line->visual offset->token index */
	private void createTokenIndex() {
		Token begToken = null;
		int line = -1;

		for (Token token : getTokens()) {
			int num = token.getLine() - 1;
			if (num > line) {
				line = num;
				begToken = token;
			}

			List<Token> tokenList = lineIndex.get(line);
			if (tokenList == null) {
				tokenList = new ArrayList<>();
				lineIndex.put(line, tokenList);
			}
			tokenList.add(token);

			int pos = getVisualColumn(begToken, token);
			visIndex.put(token, pos);
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
