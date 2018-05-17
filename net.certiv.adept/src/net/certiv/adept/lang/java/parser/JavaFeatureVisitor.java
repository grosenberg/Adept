package net.certiv.adept.lang.java.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.java.parser.gen.JavaParserBaseListener;

public class JavaFeatureVisitor extends JavaParserBaseListener {

	private Builder builder;

	public JavaFeatureVisitor(Builder builder) {
		this.builder = builder;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		builder.extractFeatures(ctx);
	}
}
