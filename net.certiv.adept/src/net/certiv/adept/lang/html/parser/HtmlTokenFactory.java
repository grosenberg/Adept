package net.certiv.adept.lang.html.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.lang.html.parser.gen.HtmlLexer;

public class HtmlTokenFactory implements TokenFactory<HtmlToken> {

	public HtmlTokenFactory() {
		super();
	}

	@Override
	public HtmlToken create(int type, String text) {
		HtmlToken token = new HtmlToken(type, text);
		return token;

	}

	@Override
	public HtmlToken create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start,
			int stop, int line, int charPositionInLine) {
		HtmlToken token = new HtmlToken(source, type, channel, start, stop);
		token.setLine(line);
		token.setCharPositionInLine(charPositionInLine);
		TokenSource tsrc = token.getTokenSource();
		token.setMode(((HtmlLexer) tsrc)._mode);
		return token;
	}
}
