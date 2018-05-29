package net.certiv.adept.lang;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.Tool;
import net.certiv.adept.tool.ErrorDesc;

public abstract class SourceParser implements ISourceParser {

	protected Tool tool;
	protected Builder builder;
	protected int errCount;

	public SourceParser() {}

	@Override
	public ParseTree getParseTree() {
		return builder.tree;
	}

	@Override
	public ParseRecord getParseData() {
		return builder;
	}

	@Override
	public List<String> getRuleNames() {
		if (builder == null) {
			builder = new Builder();
			setup(CommonTokenFactory.DEFAULT, "");
		}
		return Arrays.asList(builder.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (builder == null) {
			builder = new Builder();
			setup(CommonTokenFactory.DEFAULT, "");
		}
		return Arrays.asList(builder.lexer.getTokenNames());
	}

	@Override
	public void reportRecognitionError(Parser parser, Token offendingToken, int errorIdx, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		builder.errCount++;
		tool.syntaxError(this, ErrorDesc.SYNTAX_ERROR, parser.getSourceName(), offendingToken, e, msg);
	}

	@Override
	public void reportToken(Parser parser, List<String> ruleStack, List<String> tokenStack, Token offendingToken,
			String msg) {

		String rules = String.join("->", ruleStack);
		String tokes = String.join("=>", tokenStack);

		tool.toolInfo(this, String.format("%s: %s\n\tRules  : %s\n\tTokens : %s\n", msg,
				((CommonToken) offendingToken).toString(parser), rules, tokes));
	}

	@Override
	public void reportError(String err) {
		tool.toolInfo(this, err);
	}
}
