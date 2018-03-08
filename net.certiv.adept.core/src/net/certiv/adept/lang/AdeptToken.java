package net.certiv.adept.lang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.lang.antlr.parser.gen.Antlr4Lexer;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.Spacing;

public class AdeptToken extends CommonToken {

	private static final String Msg = "[@%s, <%s:%s> (%s) %s='%s', %s, %s:%s]";

	protected Feature feature;
	protected TerminalNode node;

	private Kind kind = Kind.TERMINAL;
	private String nodeName = "";
	private int visCol;

	private Spacing spacingLeft = Spacing.UNKNOWN;
	private Spacing spacingRight = Spacing.UNKNOWN;
	private String wsLeft = "";
	private String wsRight = "";

	public AdeptToken(int type, String text) {
		super(type, text);
	}

	public AdeptToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public void setTerminal(TerminalNode node) {
		this.node = node;
	}

	public Kind kind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public String nodeName() {
		return nodeName;
	}

	public void setNodeName(String name) {
		this.nodeName = name;
	}

	public int visCol() {
		return visCol;
	}

	public void setVisCol(int visCol) {
		this.visCol = visCol;
	}

	public Spacing spacingLeft() {
		return spacingLeft;
	}

	public void setSpacingLeft(Spacing spacing) {
		this.spacingLeft = spacing;
	}

	public Spacing spacingRight() {
		return spacingRight;
	}

	public void setSpacingRight(Spacing spacing) {
		this.spacingRight = spacing;
	}

	public String wsLeft() {
		return wsLeft;
	}

	public void setWsLeft(String ws) {
		this.wsLeft = ws;
	}

	public String wsRight() {
		return wsRight;
	}

	public void setWsRight(String ws) {
		this.wsRight = ws;
	}

	@Override
	public String toString() {
		String chanStr = "chan=" + channel;
		if (channel == 0) chanStr = "chan=Default";
		if (channel == 1) chanStr = "chan=Hidden";
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

		return String.format(Msg, index, start, stop, tokenName, type, txt, chanStr, line, charPositionInLine);
	}
}
