package net.certiv.adept.lang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.adept.format.plan.Dent;
import net.certiv.adept.format.plan.enums.Place;
import net.certiv.adept.model.Bias;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.RefToken;

public class AdeptToken extends CommonToken {

	private static final String Msg = "[@%s, <%s:%s> (%s) %s='%s', %s, %s:%s:%s]";

	public static final int BOF = Token.EOF;

	// for transfer to Feature/RefToken
	private Kind kind = Kind.TERMINAL;
	private Bias bias = Bias.RIGHT;

	private RefToken ref;

	// for formatter use only
	private int alignCol;
	private int visCol = -1;		// as parsed

	public AdeptToken(int type, String text) {
		super(type, text);
	}

	public AdeptToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
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

	// -------------------------
	// --- Ref Token delgates --

	public RefToken refToken() {
		return ref;
	}

	public void setRefToken(RefToken ref) {
		this.ref = ref;
	}

	public void setNodeName(String name) {
		ref.nodeName = name;
	}

	public int visCol(boolean asParsed) {
		if (asParsed) return this.visCol;
		return visCol();
	}

	public int visCol() {
		return ref.visCol;
	}

	public void setVisCol(int visCol) {
		ref.visCol = visCol;
		if (this.visCol == -1) this.visCol = visCol;
	}

	public int alignCol() {
		return alignCol;
	}

	public void setAlignCol(int alignCol) {
		this.alignCol = alignCol;
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

	@Override
	public String toString() {
		String chanStr = "chan=" + channel;
		if (channel == 0) chanStr = "chan=Def";
		if (channel == 1) chanStr = "chan=Hid";

		if (ref == null) {
			return String.format(Msg, index, start, stop, "", type, getText(), chanStr, getLine(),
					getCharPositionInLine(), Place.ANY);
		} else {
			return String.format(Msg, index, start, stop, ref.nodeName, type, ref.text, chanStr, ref.line, ref.col,
					ref.place);
		}
	}
}
