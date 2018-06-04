package net.certiv.adept.lang.stg.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;

public abstract class STLexerAdaptor extends Lexer {

	public char lDelim = '<';
	public char rDelim = '>';

	public int subtemplateDepth;

	public STLexerAdaptor(CharStream input) {
		super(input);
	}

	// look for "{ args ID (',' ID)* '|' ..."
	public boolean startsSubTemplate() {
		subtemplateDepth++;
		return false;
	}

	// if last RBrace, continue with mode Outside
	public boolean endsSubTemplate() {
		if (subtemplateDepth > 0) {
			subtemplateDepth--;
			mode(1); // STLexer.Inside
		}
		return true;
	}

	public void setDelimiters(char lDelim, char rDelim) {
		this.lDelim = lDelim;
		this.rDelim = rDelim;
	}

	public boolean isLDelim() {
		return lDelim == _input.LA(1) ? true : false;
	}

	public boolean isRDelim() {
		return rDelim == _input.LA(1) ? true : false;
	}

	public boolean isLTmplComment() {
		if (isLDelim() && _input.LA(2) == '!') return true;
		return false;
	}

	public boolean isRTmplComment() {
		if (isRDelim() && _input.LA(-1) == '!') return true;
		return false;
	}

	public boolean adjText() {
		int c1 = _input.LA(1);
		if (c1 == '\\') {
			int c2 = _input.LA(2);
			if (c2 == '\\') {
				_input.consume(); // convert \\ to \
			} else if (c2 == lDelim || c2 == '}') {
				_input.consume();
			}
		}
		return true;
	}
}
