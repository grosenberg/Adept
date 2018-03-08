package net.certiv.adept.lang.antlr.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

import net.certiv.adept.lang.LexerErrorStrategy;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;

public abstract class AntlrLexerAdaptor extends LexerErrorStrategy {

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
