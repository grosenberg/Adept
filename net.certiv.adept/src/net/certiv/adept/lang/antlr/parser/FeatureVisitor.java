package net.certiv.adept.lang.antlr.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4ParserBaseListener;

public class FeatureVisitor extends Antlr4ParserBaseListener {

	private Builder builder;

	public FeatureVisitor(Builder builder) {
		this.builder = builder;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		builder.extractFeatures(ctx);
	}
}
