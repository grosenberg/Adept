package net.certiv.adept.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.topo.Point;

@SuppressWarnings("serial")
public class AdeptToken extends CommonToken {

	private int _mode;

	public AdeptToken(int type, String text) {
		super(type, text);
	}

	public AdeptToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
	}

	public Point location() {
		return new Point(getCharPositionInLine(), getLine());
	}

	public void setMode(int mode) {
		_mode = mode;
	}

	@Override
	public String toString() {
		String chanStr = "chan=" + channel;
		if (channel == 0) chanStr = "chan=Default";
		if (channel == 1) chanStr = "chan=Hidden";
		String modeStr = "mode=" + Antlr4Lexer.modeNames[_mode];
		if (_mode == 0) modeStr = "mode=Default";
		String txt = getText();
		if (txt != null) {
			txt = txt.replaceAll("\n", "\\n");
			txt = txt.replaceAll("\r", "\\r");
			txt = txt.replaceAll("\t", "\\t");
		} else {
			txt = "<no text>";
		}
		String tokenName = "<EOF>";
		if (type > -1) tokenName = Antlr4Lexer.VOCABULARY.getDisplayName(type);
		return "[@" + getTokenIndex() + ", <" + start + ":" + stop + "> " + tokenName + "(" + type + ")='" + txt + "'"
				+ ", " + chanStr + ", " + modeStr + ", " + line + ":" + getCharPositionInLine() + "]";
	}
}
