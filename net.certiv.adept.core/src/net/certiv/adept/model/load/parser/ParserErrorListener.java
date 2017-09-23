package net.certiv.adept.model.load.parser;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import net.certiv.adept.util.Log;

public class ParserErrorListener extends BaseErrorListener {

	private boolean debug = true;
	private int lastErrorIdx = -1;
	private IParseErrorReporter problemReporter;

	public ParserErrorListener(IParseErrorReporter problemReporter) {
		this.problemReporter = problemReporter;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {

		Parser parser = (Parser) recognizer;
		TokenStream tokens = parser.getInputStream();

		Token offendingToken = (Token) offendingSymbol;
		int thisErrorIdx = offendingToken.getTokenIndex();
		if (offendingToken.getType() == -1 && thisErrorIdx == tokens.size() - 1) {
			Log.debug(this, "Incorrect error message: " + msg);
		}

		problemReporter.reportRecognitionError(offendingToken, thisErrorIdx, line, charPositionInLine, msg, e);

		if (debug) {
			if (thisErrorIdx > lastErrorIdx + 20) {
				lastErrorIdx = thisErrorIdx - 20;
			}
			for (int idx = lastErrorIdx + 1; idx <= thisErrorIdx; idx++) {
				Token token = tokens.get(idx);
				if (token.getChannel() != Token.HIDDEN_CHANNEL) Log.error(this, token.toString());
			}
			lastErrorIdx = thisErrorIdx;

			List<String> stack = parser.getRuleInvocationStack();
			Collections.reverse(stack);

			Log.error(this, "rule stack: " + stack);
		}
		Log.error(this, "line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg);
	}
}
