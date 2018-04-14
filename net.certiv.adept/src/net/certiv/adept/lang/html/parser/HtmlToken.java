package net.certiv.adept.lang.html.parser;

import java.util.Formatter;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.lang.html.parser.gen.HtmlLexer;
import net.certiv.adept.util.Strings;

public class HtmlToken extends CommonToken {

	private int _mode;

	public HtmlToken(int type, String text) {
		super(type, text);
	}

	public HtmlToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
	}

	public void setMode(int mode) {
		_mode = mode;
	}

	@Override
	public String toString() {
		String chanStr = (channel == 0) ? "" : "chan=" + channel;
		String modeStr = (_mode == 0) ? "" : HtmlLexer.modeNames[_mode];

		String mcStr = chanStr + " " + modeStr;
		mcStr = mcStr.trim();

		String txt = getText();
		if (txt != null) {
			txt = txt.replaceAll("\n", "\\\\n");
			txt = txt.replaceAll("\r", "\\\\r");
			txt = txt.replaceAll("\t", "\\\\t");
		} else {
			txt = "<no text>";
		}
		String tokenName = HtmlLexer.VOCABULARY.getDisplayName(type);

		try (Formatter fmt = new Formatter()) {
			fmt.format("[@%-2d %2d:%-2d <%d-%d> (%d) %s %s='%s']", //
					getTokenIndex(), line, getCharPositionInLine(), start, stop, type, mcStr, tokenName, txt);
			return fmt.toString() + Strings.EOL;
		}
	}
}
