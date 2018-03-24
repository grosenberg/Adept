package net.certiv.adept.lang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

public class AdeptTokenFactory implements TokenFactory<AdeptToken> {

	public AdeptTokenFactory() {}

	@Override
	public AdeptToken create(int type, String text) {
		return new AdeptToken(type, text);
	}

	@Override
	public AdeptToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start,
			int stop, int line, int charPositionInLine) {

		AdeptToken token = new AdeptToken(source, type, channel, start, stop);
		token.setText(text);
		token.setLine(line);
		token.setCharPositionInLine(charPositionInLine);
		return token;
	}
}
