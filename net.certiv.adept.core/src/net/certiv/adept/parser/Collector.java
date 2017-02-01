package net.certiv.adept.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
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

public class Collector extends ParseData {

	private Document doc;
	private List<Integer> exTypes;

	// unique list of features that represent the parsed document
	private HashSet<Feature> features;
	private Group group;

	public Collector(Document doc) {
		super();
		this.doc = doc;
		if (doc != null) doc.setParse(this);
		features = new LinkedHashSet<>();
		exTypes = Tool.mgr.excludedLangTypes();
		group = new Group(this);
	}

	public Document getDocument() {
		return doc;
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
		int type = rule << 10;
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
		Feature feature = Feature.create(Kind.RULE, aspect, doc.getDocId(), features.size(), type, start, stop, format,
				false);
		features.add(feature);
		contextIndex.put(start, ctx);
		ruleIndex.put(ctx, feature);
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
		Feature feature = Feature.create(Kind.NODE, aspect, doc.getDocId(), features.size(), type, token, format,
				isVar(type));
		features.add(feature);
		nodeIndex.put(token, node);
		terminalIndex.put(node, feature);
	}

	public void annotateComments() {
		for (Token token : stream.getTokens()) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				String aspect = lexer.getVocabulary().getDisplayName(type);
				int format = Form.characterize(this, token);
				Kind kind = type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT;
				Feature feature = Feature.create(kind, aspect, doc.getDocId(), features.size(), type, token, format);
				TerminalNode node = new TerminalNodeImpl(token);
				features.add(feature);
				nodeIndex.put(token, node);
				terminalIndex.put(node, feature);
			}
		}
	}

	private boolean isVar(int type) {
		for (int v : VARS) {
			if (v == type) return true;
		}
		return false;
	}

	/** Builds a source line->tokens indexing map */
	public void createLineTokenIndex() {
		for (Token token : getTokens()) {
			int num = token.getLine() - 1;
			List<Token> line = lineIndex.get(num);
			if (line == null) {
				line = new ArrayList<>();
				lineIndex.put(num, line);
			}
			line.add(token);
		}
	}

	/** Generate edge connections for all non-RULE root features. */
	public void genLocalEdges(int tabWidth) {
		for (Feature feature : features) {
			if (feature.getKind() == Kind.RULE) continue;

			group.setLocus(feature, tabWidth);
			List<Feature> locals = group.getLocalFeatures();
			for (Feature local : locals) {
				feature.addEdge(local);
			}
		}
	}
}
