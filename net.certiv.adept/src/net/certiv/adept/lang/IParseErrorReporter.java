/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.List;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public interface IParseErrorReporter {

	void reportRecognitionError(Parser parser, Token offendingToken, int errorIdx, int line, int charPositionInLine,
			String msg, RecognitionException e);

	void reportToken(Parser parser, List<String> ruleStack, List<String> tokenStack, Token offendingToken, String msg);

	void reportError(String err);
}
