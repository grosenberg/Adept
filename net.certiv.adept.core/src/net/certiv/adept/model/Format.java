package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.gson.annotations.Expose;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.util.Info;
import net.certiv.adept.util.Strings;

public class Format {

	@Expose public boolean indented;		// at line begin and indented

	@Expose public boolean atLineBeg;		// occurs at line begin, ignoring hws and comments
	@Expose public boolean atLineEnd;		// occurs at line end, ignoring hws and comments

	@Expose public boolean wsBefore;		// has leading ws, ignoring any in-line block comment
	@Expose public boolean wsAfter; 		// has trailing ws, ignoring any in-line block comment
	@Expose public boolean multBefore;		// has multiple leading ws
	@Expose public boolean multAfter; 		// has multiple trailing ws

	@Expose public boolean alignedAbove;	// visually aligned to a node feature in the prior line
	@Expose public boolean blankAbove;		// blank line separates

	@Expose public int indents;				// absolute number of indents
	@Expose public int relDents;			// num indents relative to real line above

	@Expose public int widthBefore;			// num leading ws
	@Expose public int widthAfter; 			// num trailing ws
	@Expose public int visCol;				// visual column

	@Expose public boolean joinAlways;		// derived from corpus as a whole
	@Expose public boolean joinShould;
	@Expose public boolean joinAllow;
	@Expose public boolean joinNever;

	public boolean join;					// {@code true} to keep or force line joining
	public boolean noFormat;				// no match found in corpus

	// ------------------------------------------------------------------------

	private ParseRecord data;

	public AdeptToken token;
	public Spacing spacing;
	public String trailingWS;

	// ------------------------------------------------------------------------

	/**
	 * Merge the format of a source document feature with the format of its matched feature. Precedence
	 * is given to the matched feature format aspects with the source format aspects are considered as
	 * hinting.
	 */
	public static Format merge(Feature docFeature, Feature matched) {
		Format merged = new Format();

		// merged.indented = matched.indented;
		//
		// merged.atLineBeg = matched.atLineBeg;
		// merged.atLineEnd = matched.atLineEnd;
		//
		// merged.wsBefore = matched.wsBefore;
		// merged.wsAfter = matched.wsAfter;
		// merged.multBefore = matched.multBefore;
		// merged.multAfter = matched.multAfter;
		//
		// merged.alignedAbove = docFeature.alignedAbove || matched.alignedAbove;
		// merged.blankAbove = docFeature.blankAbove && matched.blankAbove;
		//
		// merged.indents = matched.indents;
		// merged.relDents = matched.relDents;
		//
		// merged.widthBefore = Math.max(docFeature.widthBefore, matched.widthBefore);
		// merged.widthAfter = Math.max(docFeature.widthAfter, matched.widthAfter);
		// merged.visCol = Math.max(docFeature.visCol, matched.visCol);
		//
		// merged.joinAlways = matched.joinAlways;
		// merged.joinShould = matched.joinShould;
		// merged.joinAllow = matched.joinAllow;
		// merged.joinNever = matched.joinNever;
		//
		// merged.join = matched.joinAlways;
		// merged.noFormat = matched.noFormat;

		return merged;
	}

	// ------------------------------------------------------------------------

	/** Restore */
	public Format() {}

	/** Terminal */
	public Format(ParseRecord data, TerminalNode terminal) {
		this(data, terminal.getSymbol());
	}

	/** Comment */
	public Format(ParseRecord data, Token token) {
		this.data = data;
		Info info = data.getDocument().getInfo(token.getLine() - 1);
		characterize(token, info);
	}

	public Format(AdeptToken token, Spacing spacing, String trailingWS) {
		this.token = token;
		this.spacing = spacing;
		this.trailingWS = trailingWS;
	}

	public double similarityLine(Format o) {
		double sim = 0;
		sim += boolSim(atLineBeg, o.atLineBeg);
		sim += boolSim(atLineEnd, o.atLineEnd);
		sim += boolSim(indented, o.indented);

		if (indented && o.indented) {
			if (Integer.signum(relDents) == Integer.signum(o.relDents)) {
				sim += 1;
				if (relDents == o.relDents) {
					sim += 1;
				} else {
					double tdent = Math.abs(relDents);
					double odent = Math.abs(o.relDents);
					sim += Math.min(tdent, odent) / Math.max(tdent, odent);
				}
			}
		}
		return sim / 5;
	}

	public double similarityWs(Format o) {
		double sim = 0;
		sim += boolSim(wsBefore, o.wsBefore);
		sim += boolSim(wsAfter, o.wsAfter);
		sim += boolSim(multBefore, o.multBefore);
		sim += boolSim(multAfter, o.multAfter);
		return sim / 4;
	}

	public double similarityStyle(Format o) {
		double sim = 0;
		sim += boolSim(alignedAbove, o.alignedAbove);
		sim += boolSim(blankAbove, o.blankAbove);
		return sim / 2;
	}

	private double boolSim(boolean one, boolean two) {
		return one == two ? 1 : 0;
	}

	public String encode() {
		boolean[] vector = simVector();
		int value = 0;
		for (int idx = 0; idx < vector.length; idx++) {
			if (vector[idx]) {
				int bit = (1 << idx);
				value = value | bit;
			}
		}
		return String.format("0x%04X", value & 0xFFFFFFFF);
	}

	private boolean[] simVector() {
		return new boolean[] { indented, atLineBeg, atLineEnd, wsBefore, wsAfter, multBefore, multAfter, alignedAbove,
				blankAbove, (Integer.signum(relDents) >= 0), (Math.abs(relDents) > 0) };
	}

	private void characterize(Token token, Info info) {
		int tokenIdx = token.getTokenIndex();
		int line = token.getLine() - 1; // make zero indexed

		atLineBeg = token.getCharPositionInLine() == info.beg;
		atLineEnd = token.getCharPositionInLine() == info.end;

		if (atLineBeg) {
			indented = info.indents > 0;
			indents = info.indents;
			relDents = info.indents - info.priorIndents;
		}

		widthBefore = leftWSVisualWidth(tokenIdx);
		wsBefore = widthBefore > 0;
		multBefore = widthBefore > 1;

		widthAfter = rightWSVisualWidth(tokenIdx);
		wsAfter = widthAfter > 0;
		multAfter = widthAfter > 1;

		visCol = data.tokenVisColIndex.get(token);
		alignedAbove = aligned(token, line - 1);
		blankAbove = info.blankAbove;
	}

	// returns whether the token is subject to alignment
	private boolean aligned(Token token, int prior) {
		if (prior > 0 && !data.isBlankLine(prior)) {
			int visCol = data.tokenVisColIndex.get(token);
			Token aligned = data.getVisualToken(prior, visCol);
			if (aligned != null) {
				int ttype = token.getType();
				int atype = aligned.getType();
				if (ttype == atype) {
					if (data.align_ident.contains(ttype)) return true;
				} else {
					if (data.align_pair.containsEntry(ttype, atype)) return true;
				}
			}
		}
		return false;
	}

	private int leftWSVisualWidth(int idx) {
		List<Token> tokens = data.tokenStream.getHiddenTokensToLeft(idx);
		return wsVisualWidth(tokens, true);
	}

	private int rightWSVisualWidth(int idx) {
		List<Token> tokens = data.tokenStream.getHiddenTokensToRight(idx);
		return wsVisualWidth(tokens, false);
	}

	private int wsVisualWidth(List<Token> tokens, boolean reverse) {
		if (tokens == null || tokens.isEmpty()) return 0;

		List<Token> ws = new ArrayList<>();
		if (reverse) Collections.reverse(tokens);
		if (tokens != null) {
			for (Token token : tokens) {
				if (token.getType() == data.BLOCKCOMMENT) break;
				if (token.getType() == data.LINECOMMENT) break;
				if (token.getType() == data.VWS) break;
				if (token.getType() == data.HWS) ws.add(token);
			}
		}
		if (ws.isEmpty()) return 0;
		if (reverse) Collections.reverse(ws);
		Token first = ws.get(0);
		Token last = ws.get(ws.size() - 1);
		String content = data.tokenStream.getText(first, last);
		Integer start = data.tokenVisColIndex.get(first);
		return Strings.measureVisualWidth(content, data.doc.getTabWidth(), start);
	}

	public boolean equivalentTo(Format o) {
		if (indented != o.indented) return false;
		if (atLineBeg != o.atLineBeg) return false;
		if (atLineEnd != o.atLineEnd) return false;
		if (wsBefore != o.wsBefore) return false;
		if (wsAfter != o.wsAfter) return false;
		if (multBefore != o.multBefore) return false;
		if (multAfter != o.multAfter) return false;
		if (relDents != o.relDents) return false;
		if (noFormat != o.noFormat) return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (indented) builder.append("indented, ");
		if (atLineBeg) builder.append("atLineBeg, ");
		if (atLineEnd) builder.append("atLineEnd, ");
		if (wsBefore) builder.append("wsBefore, ");
		if (wsAfter) builder.append("wsAfter, ");
		if (multBefore) builder.append("multBefore, ");
		if (multAfter) builder.append("multAfter, ");
		if (alignedAbove) builder.append("alignedAbove, ");
		if (blankAbove) builder.append("blankAbove, ");
		if (indents > 0) builder.append("indents=" + indents + ", ");
		if (relDents != 0) builder.append("relDents=" + relDents + ", ");
		if (widthBefore > 1) builder.append("widthBefore=" + widthBefore + ", ");
		if (widthAfter > 1) builder.append("widthAfter=" + widthAfter + ", ");
		if (visCol != 0) builder.append("visCol=" + visCol + ", ");
		if (joinAlways) builder.append("joinAlways, ");
		if (joinShould) builder.append("joinShould, ");
		if (joinAllow) builder.append("joinAllow, ");
		if (joinNever) builder.append("joinNever, ");
		if (join) builder.append("join, ");
		if (noFormat) builder.append("noFormat, ");

		int dot = builder.lastIndexOf(",");
		if (dot > -1) builder.setLength(dot);
		return builder.toString();
	}
}
