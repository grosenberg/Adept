package net.certiv.adept.parser;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public interface IParseErrorReporter {

	void reportRecognitionError(Token offendingToken, int errorIdx, int line, int charPositionInLine, String msg,
			RecognitionException e);
}
