package net.certiv.adept.antlr.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.antlr.parser.gen.Antlr4ParserBaseListener;
import net.certiv.adept.parser.Collector;

public class AntlrVisitor extends Antlr4ParserBaseListener {

	private Collector collector;

	public AntlrVisitor(Collector collector) {
		this.collector = collector;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		collector.annotateRule(ctx);
		for (ParseTree child : ctx.children) {
			if (child instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) child;
				collector.annotateNode(ctx, node);
			}
		}
	}
}
