/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.antlr.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;

public abstract class AntlrLexerAdaptor extends Lexer {

	private boolean rarrow;

	public AntlrLexerAdaptor(CharStream input) {
		super(input);
	}

	@Override
	public Token emit() {
		switch (_type) {
			case Antlr4Lexer.RARROW:
				rarrow = true;
				break;
			case Antlr4Lexer.PUSHMODE:
			case Antlr4Lexer.POPMODE:
			case Antlr4Lexer.TYPE:
				if (!rarrow) _type = Antlr4Lexer.ID;
				break;
			case Antlr4Lexer.SEMI:
				rarrow = false;
				break;
		}
		return super.emit();
	}

	protected void handleEndAction() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(Antlr4Lexer.ACT_CONTENT);
		}
	}

	protected void handleEndArgs() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(Antlr4Lexer.ARG_CONTENT);
		}
	}
}
