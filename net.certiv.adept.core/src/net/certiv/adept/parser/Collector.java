package net.certiv.adept.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.topo.Form;
import net.certiv.adept.topo.Group;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Span;
import net.certiv.adept.util.Log;

public class Collector extends ParseData {

	private Document doc;
	private List<Integer> exTypes;

	// list of features that represent the document
	private List<Feature> features;
	private Group group;

	public Collector(Document doc) {
		super();
		this.doc = doc;
		if (doc != null) doc.setParse(this);
		features = new ArrayList<>();
		exTypes = Tool.mgr.excludedLangTypes();
		group = new Group(this);
	}

	public Document getDocument() {
		return doc;
	}

	public List<Feature> getFeatures() {
		return features;
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
		Point coords = getCoords(start);
		Span span = getSize(start, stop);
		int format = Form.characterize(this, ctx);
		Feature feature = new Feature(aspect, type, start, stop, coords, span, format);
		feature.setKind(Kind.RULE);
		contextIndex.put(start, ctx);
		ruleIndex.put(ctx, feature);
		add(feature);

		if (ctx.getChildCount() > 0) {
			for (ParseTree child : ctx.children) {
				if (child instanceof TerminalNode) {
					annotateNode(ctx, (TerminalNode) child);
				}
			}
		}
	}

	public void annotateNode(ParserRuleContext ctx, TerminalNode node) {
		// do not revisit nodes
		if (terminalIndex.get(node) != null) return;

		Token token = node.getSymbol();
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type == ERR_TOKEN) {
				Log.debug(this, String.format("Skipping %s %s", Utils.escapeWhitespace(ctx.getText(), false), token));
			}
			return;
		}

		String aspect = lexer.getVocabulary().getDisplayName(type);
		Point coords = getCoords(token);
		Span span = getSize(token, token);
		int format = Form.characterize(this, node);
		Feature feature = new Feature(aspect, type, token, coords, span, format);
		feature.setKind(Kind.NODE);
		feature.setVar(isVar(type));
		nodeIndex.put(token, node);
		terminalIndex.put(node, feature);
		add(feature);
	}

	private boolean isVar(int type) {
		for (int v : VARS) {
			if (v == type) return true;
		}
		return false;
	}

	public void annotateComments() {
		for (Token token : stream.getTokens()) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				String aspect = lexer.getVocabulary().getDisplayName(type);
				Point coords = getCoords(token);
				Span span = getSize(token, token);
				int format = Form.characterize(this, token);
				Feature feature = new Feature(aspect, type, token, coords, span, format);
				feature.setKind(type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT);
				TerminalNode node = new TerminalNodeImpl(token);
				nodeIndex.put(token, node);
				terminalIndex.put(node, feature);
				add(feature);
			}
		}
	}

	private void add(Feature feature) {
		feature.setId(doc.getDocId(), features.size());
		features.add(feature);
	}

	private Point getCoords(Token start) {
		return ((AdeptToken) start).coords();
	}

	private Span getSize(Token start, Token stop) {
		if (start == stop) return new Span(start.getText().length(), 1);

		int height = stop.getLine() - start.getLine() + 1;
		int width = stop.getStopIndex() - start.getStartIndex() + 1;
		return new Span(width, height);
	}

	public void index() {
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

	public void genLocalEdges(int tabWidth) {
		for (Feature feature : features) {
			group.setLocus(feature, tabWidth);
			List<Feature> locals = group.getLocalFeatures();
			for (Feature local : locals) {
				feature.addEdge(local);
				local.addEdge(feature);
			}
		}
	}
}
