package net.certiv.adept.lang.xvisitor.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParserBaseListener;

public class XVFeatureVisitor extends XVisitorParserBaseListener {

	private Builder builder;

	public XVFeatureVisitor(Builder builder) {
		this.builder = builder;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		builder.extractFeatures(ctx);
	}
}
