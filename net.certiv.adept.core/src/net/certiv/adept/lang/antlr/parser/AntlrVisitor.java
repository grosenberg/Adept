package net.certiv.adept.lang.antlr.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.antlr.parser.gen.Antlr4ParserBaseListener;
import net.certiv.adept.model.parser.Builder;

public class AntlrVisitor extends Antlr4ParserBaseListener {

	private Builder builder;

	public AntlrVisitor(Builder builder) {
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
