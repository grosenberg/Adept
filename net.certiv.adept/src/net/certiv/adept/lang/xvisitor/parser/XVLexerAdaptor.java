package net.certiv.adept.lang.xvisitor.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.Interval;

public abstract class XVLexerAdaptor extends Lexer {

	public XVLexerAdaptor(CharStream input) {
		super(input);
	}

	/**
	 * Predicate qualifier for default mode line comments - necessary to distinguish from the 'any'
	 * separator
	 *
	 * @return true if line comment allowed
	 */
	public boolean lcPrefix() {
		CodePointCharStream ais = (CodePointCharStream) _input;
		int offset = ais.index();
		boolean ws = false;
		for (int dot = -1; dot > -offset; dot--) {
			char c = (char) ais.LA(dot);
			switch (c) {
				case '\t':
				case '\r':
				case '\n':
				case ' ':
					ws = true;
					break;
				default:
					if (!ws || (ws && c == ':')) return false; // not a line comment
					return true;
			}
		}
		return true;
	}

	/**
	 * Predicate qualifier for default mode block comments - necessary to distinguish from the '/*'
	 * separator & wildcard combination
	 *
	 * @return true if block comment allowed
	 */
	public boolean bcSuffix() {
		CodePointCharStream ais = (CodePointCharStream) _input;
		int offset = ais.index();
		Interval i = new Interval(offset, offset + 2);
		String la = ais.getText(i);
		if (la.equals("/*/")) return false;
		return true;
	}
}
