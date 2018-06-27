/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import net.certiv.adept.util.Strings;

public class ParserErrorListener extends BaseErrorListener {

	private boolean debug = false;
	private int lastErrorIdx = -1;
	private IParseErrorReporter reporter;

	public ParserErrorListener(IParseErrorReporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {

		Parser parser = (Parser) recognizer;
		TokenStream tokens = parser.getInputStream();

		Token offendingToken = (Token) offendingSymbol;
		int thisErrorIdx = offendingToken.getTokenIndex();
		if (offendingToken.getType() == -1 && thisErrorIdx == tokens.size() - 1) {
			reporter.reportError("Incorrect error message: " + msg);
		}

		reporter.reportRecognitionError(parser, offendingToken, thisErrorIdx, line, charPositionInLine, msg, e);

		if (debug) {
			if (thisErrorIdx > lastErrorIdx + 10) {
				lastErrorIdx = thisErrorIdx - 10;
			}
			List<String> tokenStack = new ArrayList<>();
			for (int idx = lastErrorIdx + 1; idx <= thisErrorIdx; idx++) {
				Token token = tokens.get(idx);
				String name = recognizer.getVocabulary().getDisplayName(token.getType());
				String text = Strings.shorten(token.getText(), 12);
				tokenStack.add(String.format("@%s %s[%s] %s:%s", token.getTokenIndex(), name, text, token.getLine(),
						token.getCharPositionInLine()));
			}
			lastErrorIdx = thisErrorIdx;

			List<String> ruleStack = parser.getRuleInvocationStack();
			Collections.reverse(ruleStack);

			reporter.reportToken(parser, ruleStack, tokenStack, offendingToken, msg);
		}
	}
}
