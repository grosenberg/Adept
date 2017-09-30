package net.certiv.adept.model.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;

public class AdeptTokenFactory implements TokenFactory<AdeptToken> {

	public CharStream input;

	public AdeptTokenFactory(CharStream input) {
		this.input = input;
	}

	@Override
	public AdeptToken create(int type, String text) {
		return new AdeptToken(type, text);
	}

	@Override
	public AdeptToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start,
			int stop, int line, int charPositionInLine) {
		AdeptToken token = new AdeptToken(source, type, channel, start, stop);
		token.setLine(line);
		token.setCharPositionInLine(charPositionInLine);
		TokenSource tsrc = token.getTokenSource();
		token.setMode(((Antlr4Lexer) tsrc)._mode);
		return token;
	}
}
