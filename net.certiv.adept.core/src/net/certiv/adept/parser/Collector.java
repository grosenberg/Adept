package net.certiv.adept.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.topo.Form;
import net.certiv.adept.topo.Group;
import net.certiv.adept.topo.Point;
import net.certiv.adept.topo.Size;
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

	public void annotateRule(ParserRuleContext ctx) {
		int rule = ctx.getRuleIndex();
		int type = rule << 10;
		if (exTypes.contains(type)) {
			Log.info(this, "Skipping " + ctx.getText());
			return;
		}

		Token start = ctx.start;
		Token stop = ctx.stop;
		if (stop.getTokenIndex() < start.getTokenIndex()) {
			stop = start;
		}

		contextIndex.put(start, ctx);

		String aspect = parser.getRuleNames()[rule];
		Point location = getLocation(start, stop);
		Size size = getSize(start, stop);
		int format = Form.characterize(this, ctx);
		Feature feature = new Feature(aspect, type, start, stop, location, size, format);
		feature.setKind(Kind.RULE);
		add(feature);
	}

	public void annotateNode(ParserRuleContext ctx, TerminalNode node) {
		Token token = node.getSymbol();
		int type = token.getType();
		if (exTypes.contains(type)) {
			if (type != -1) Log.info(this, "Skipping " + node.getText() + token.toString());
			return;
		}

		nodeIndex.put(token, node);

		String aspect = lexer.getVocabulary().getDisplayName(type);
		Point location = getLocation(token, token);
		Size size = getSize(token, token);
		int format = Form.characterize(this, node);
		Feature feature = new Feature(aspect, type, token, location, size, format);
		feature.setKind(Kind.NODE);
		add(feature);
	}

	public void annotateComments() {
		for (Token token : stream.getTokens()) {
			int type = token.getType();
			if (type == BLOCKCOMMENT || type == LINECOMMENT) {
				String aspect = lexer.getVocabulary().getDisplayName(type);
				Point location = getLocation(token, token);
				Size size = getSize(token, token);
				int format = Form.characterize(this, token);
				Feature feature = new Feature(aspect, type, token, location, size, format);
				feature.setKind(type == BLOCKCOMMENT ? Kind.BLOCKCOMMENT : Kind.LINECOMMENT);
				add(feature);
			}
		}
	}

	private void add(Feature feature) {
		feature.setId(doc.getDocId(), features.size());
		features.add(feature);

		group.setLocus(feature);
		List<Feature> locals = group.getLocalFeatures();
		for (Feature local : locals) {
			feature.addEdge(local);
			local.addEdge(feature);
		}
	}

	private Point getLocation(Token start, Token stop) {
		if (start == stop) return ((AdeptToken) start).location();

		int mid = stop.getTokenIndex() - start.getTokenIndex();
		mid /= 2;
		mid += start.getTokenIndex();
		return ((AdeptToken) stream.get(mid)).location();
	}

	private Size getSize(Token start, Token stop) {
		if (start == stop) return new Size(start.getText().length(), 1);

		int height = stop.getLine() - start.getLine() + 1;
		int width = stop.getStopIndex() - start.getStartIndex() + 1;
		return new Size(width, height);
	}
}
