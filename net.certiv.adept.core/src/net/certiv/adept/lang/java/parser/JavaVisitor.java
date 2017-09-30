package net.certiv.adept.lang.java.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.java.parser.gen.JavaParserBaseListener;
import net.certiv.adept.model.parser.Builder;

public class JavaVisitor extends JavaParserBaseListener {

	private Builder builder;

	public JavaVisitor(Builder builder) {
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
