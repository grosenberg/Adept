package net.certiv.adept.java.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.java.parser.gen.JavaParserBaseListener;
import net.certiv.adept.parser.Collector;

public class JavaVisitor extends JavaParserBaseListener {

	private Collector collector;

	public JavaVisitor(Collector collector) {
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
