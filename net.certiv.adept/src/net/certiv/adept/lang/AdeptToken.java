/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang;

import java.util.Comparator;

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

/**
 * <pre>
 * Token#getCharPosInLine: 0..n-1
 * Token#setCharPosInLine: 0..n-1
 * AdeptToken#getLinePos.: 0..n-1
 * AdeptToken#setLinePos.: 0..n-1
 * AdeptToken#getVisPos..: 0..n+ (tab expanded)
 * AdeptToken#setVisPos..: 0..n+ (tab expanded)
 *
 * Token#getLine.........: 1..n
 * Token#setLine.........: 1..n
 * AdeptToken#getCol.....: 1..n
 * AdeptToken#setCol.....: 1..n
 * </pre>
 */
public class AdeptToken extends CommonToken implements Comparable<AdeptToken>, Comparator<AdeptToken> {

	public static final int BOF = Token.EOF;

	// for transfer to Feature/RefToken
	private Kind kind = Kind.WHITESPACE;
	private Bias bias = Bias.RIGHT;

	private int vPos = -1;	// 0..n+
	private String nodeName = "";
	private RefToken ref;

	/** Initial source line/char pos - formatter use only */
	public final int[] pos;

	public AdeptToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
		pos = new int[] { line, charPositionInLine };
	}

	public AdeptToken(int type, String text) {
		super(type, text);
		pos = new int[] { line, charPositionInLine };
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

	public void setNodeName(String name) {
		this.nodeName = name;
	}

	/** Returns the line number in the range 0..n-1 */
	public int getLinePos() {
		return super.getLine() - 1;
	}

	/** Sets the line number in the range 0..n-1 */
	public void setLinePos(int pos) {
		super.setLine(pos + 1);
	}

	/** Returns the column in the range 1..n */
	public int getCol() {
		return super.getCharPositionInLine() + 1;
	}

	/** Sets the column in the range 1..n */
	public void setCol(int pos) {
		super.setCharPositionInLine(pos - 1);
	}

	/** Returns the visual position in the range 0..n+ */
	public int getVisPos() {
		return vPos;
	}

	/** Sets the visual position in the range 0..n+ */
	public void setVisPos(int vPos) {
		this.vPos = vPos;
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
	public int compare(AdeptToken o1, AdeptToken o2) {
		if (o1.getTokenIndex() < o2.getTokenIndex()) return -1;
		if (o1.getTokenIndex() > o2.getTokenIndex()) return 1;
		return 0;
	}

	@Override
	public int compareTo(AdeptToken token) {
		return compare(this, token);
	}

	public boolean equivTo(AdeptToken token) {
		if (this == token) return true;
		if (token == null) return false;

		if (type != token.type) return false;
		if (!getText().equals(token.getText())) return false;
		return true;
	}

	// -------------------------

	private static final String Msg = "%s[%s]='%s' @%s %s <%s %s:%s>";

	@Override
	public String toString() {
		String pos = getLine() + ":" + getCol() + "(" + vPos + 1 + ")";
		String txt = ref != null ? Strings.encodeWS(ref.text) : Strings.encodeWS(getText());
		String place = ref != null ? ref.place.toString() : Place.ANY.toString();

		return String.format(Msg, nodeName, type, txt, pos, place, index, start, stop);
	}
}
