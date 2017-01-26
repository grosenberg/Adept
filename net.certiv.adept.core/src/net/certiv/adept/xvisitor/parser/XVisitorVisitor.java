package net.certiv.adept.xvisitor.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.parser.Collector;
import net.certiv.adept.xvisitor.parser.gen.XVisitorParserBaseListener;

public class XVisitorVisitor extends XVisitorParserBaseListener {

	private Collector collector;

	public XVisitorVisitor(Collector collector) {
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
