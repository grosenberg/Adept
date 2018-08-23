package net.certiv.adept.lang;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.adept.Tool;
import net.certiv.adept.tool.ErrorDesc;
import net.certiv.adept.util.Strings;

public abstract class SourceParser implements ISourceParser {

	protected Tool tool;
	protected Builder builder;
	private AdeptTokenFactory factory;
	protected int errCount;

	protected final ANTLRErrorListener syntaxErrListener = new BaseErrorListener() {

		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
				String msg, RecognitionException e) {
			errCount++;
			throw e;
		}
	};

	public SourceParser() {}

	@Override
	public TokenFactory<?> getTokenFactory() {
		if (factory == null) {
			factory = new AdeptTokenFactory();
		}
		return factory;
	}

	@Override
	public ParseTree getParseTree() {
		return builder.tree;
	}

	@Override
	public Record getParseData() {
		return builder;
	}

	@Override
	public List<String> getRuleNames() {
		if (builder == null) {
			builder = new Builder();
			setup(getTokenFactory(), "");
		}
		return Arrays.asList(builder.parser.getRuleNames());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTokenNames() {
		if (builder == null) {
			builder = new Builder();
			setup(getTokenFactory(), "");
		}
		return Arrays.asList(builder.lexer.getTokenNames());
	}

	@Override
	public void reportRecognitionError(Parser parser, Token offendingToken, int errorIdx, int line,
			int charPositionInLine, String msg, RecognitionException e) {
		builder.errCount++;

		if (e != null) {
			IntervalSet expected = e.getExpectedTokens();
			if (expected != null) {
				String txt = "Expected: " + expected.toString(parser.getVocabulary());
				msg += Strings.EOL + Strings.wrap(txt, 100, "\t", Strings.EOL, " ");
			}
		}
		tool.syntaxError(this, ErrorDesc.SYNTAX_ERROR, parser.getSourceName(), offendingToken, null, msg);
	}

	@Override
	public void reportToken(Parser parser, List<String> ruleStack, List<String> tokenStack, Token offendingToken,
			String msg) {

		String rules = String.join("->", ruleStack);
		String tokens = String.join("=>", Strings.encodeWS(tokenStack));

		tool.toolInfo(this, String.format("%s: %s\n\tRules  : %s\n\tTokens : %s\n", msg,
				((CommonToken) offendingToken).toString(parser), rules, tokens));
	}

	@Override
	public void reportError(String err) {
		tool.toolInfo(this, err);
	}
}
