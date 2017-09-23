package net.certiv.adept.lang.java.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.java.parser.gen.JavaParserBaseListener;
import net.certiv.adept.model.load.parser.FeatureFactory;

public class JavaVisitor extends JavaParserBaseListener {

	private FeatureFactory featureFactory;

	public JavaVisitor(FeatureFactory featureFactory) {
		this.featureFactory = featureFactory;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		featureFactory.annotateRule(ctx);
		for (ParseTree child : ctx.children) {
			if (child instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) child;
				featureFactory.annotateNode(ctx, node);
			}
		}
	}
}
