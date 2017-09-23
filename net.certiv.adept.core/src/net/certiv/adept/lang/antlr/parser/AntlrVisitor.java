package net.certiv.adept.lang.antlr.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.antlr.parser.gen.Antlr4ParserBaseListener;
import net.certiv.adept.model.load.parser.FeatureFactory;

public class AntlrVisitor extends Antlr4ParserBaseListener {

	private FeatureFactory featureFactory;

	public AntlrVisitor(FeatureFactory featureFactory) {
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
