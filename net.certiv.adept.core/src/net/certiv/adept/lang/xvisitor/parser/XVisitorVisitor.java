package net.certiv.adept.lang.xvisitor.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParserBaseListener;
import net.certiv.adept.model.parser.Builder;

public class XVisitorVisitor extends XVisitorParserBaseListener {

	private Builder builder;

	public XVisitorVisitor(Builder builder) {
		this.builder = builder;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		builder.annotateRule(ctx);
		for (ParseTree child : ctx.children) {
			if (child instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) child;
				builder.annotateTerminal(ctx, node);
			}
		}
	}
}
