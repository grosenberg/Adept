package net.certiv.adept.lang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.format.plan.Dent;
import net.certiv.adept.format.plan.Place;
import net.certiv.adept.model.Bias;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Strings;

public class AdeptToken extends CommonToken {

	public static final int BOF = Token.EOF;

	// for transfer to Feature/RefToken
	private Kind kind = Kind.WHITESPACE;
	private Bias bias = Bias.RIGHT;

	private String nodeName = "";
	private int visCol = -1;	// 0..n-1
	private RefToken ref;

	// for formatter use only
	/** Initial source line value */
	public final int iLine;
	/** Initial source column value */
	public final int iCol;
	/** Initial source visual column value */
	public int iVisCol = -1;

	public AdeptToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
		iLine = getLine();
		iCol = charPositionInLine;
	}

	public AdeptToken(int type, String text) {
		super(type, text);
		iLine = getLine();
		iCol = charPositionInLine;
	}

	/**
	 * Corrected: returns the line number in the range 0..n-1
	 *
	 * @see org.antlr.v4.runtime.CommonToken#getLine()
	 */
	@Override
	public int getLine() {
		return super.getLine() - 1;
	}

	public Kind kind() {
		return kind;
	}

	public Bias bias() {
		return bias;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
		this.bias = kind == Kind.LINECOMMENT ? Bias.LEFT : Bias.RIGHT;
	}

	public boolean isComment() {
		return kind == Kind.LINECOMMENT || kind == Kind.BLOCKCOMMENT;
	}

	public boolean isLineComment() {
		return kind == Kind.LINECOMMENT;
	}

	public boolean isWhitespace() {
		return kind == Kind.WHITESPACE;
	}

	// -------------------------
	// --- Ref Token delgates --

	public RefToken refToken() {
		return ref;
	}

	public void setRefToken(RefToken ref) {
		this.ref = ref;
	}

	public void setNodeName(String name) {
		this.nodeName = name;
	}

	public int iVisCol() {
		return iVisCol;
	}

	public int visCol() {
		return visCol;
	}

	public void setVisCol(int visCol) {
		this.visCol = visCol;
		if (iVisCol == -1) iVisCol = visCol;
	}

	public Place place() {
		return ref.place;
	}

	public void setPlace(Place place) {
		ref.place = place;
	}

	public boolean atBol() {
		return ref.place == Place.SOLO || ref.place == Place.BEG;
	}

	public boolean atEol() {
		return ref.place == Place.SOLO || ref.place == Place.END;
	}

	public Dent dent() {
		return ref.dent;
	}

	public void setDent(Dent dent) {
		ref.dent = dent;
	}

	// -------------------------

	private static final String Msg = "@%s <%s:%s - %s %s> %s[%s]='%s'";

	@Override
	public String toString() {
		String pos = getLine() + ":" + charPositionInLine + " (" + visCol + ")";
		String txt = ref != null ? Strings.encodeWS(ref.text) : Strings.encodeWS(getText());
		String place = ref != null ? ref.place.toString() : Place.ANY.toString();

		return String.format(Msg, index, start, stop, pos, place, nodeName, type, txt);
	}
}
