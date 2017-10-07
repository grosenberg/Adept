package net.certiv.adept.model;

import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.util.Info;

public class Format {

	@Expose public boolean indented;		// at line begin and indented
	@Expose public boolean atLineBeg;		// occurs at line begin, ignoring hws and comments
	@Expose public boolean atLineEnd;		// occurs at line end, ignoring hws and comments
	@Expose public boolean wsBefore;		// has leading ws, ignoring any in-line block comment
	@Expose public boolean wsAfter; 		// has trailing ws, ignoring any in-line block comment 
	@Expose public boolean multBefore;		// has multiple leading ws, not at line begin
	@Expose public boolean multAfter; 		// has multiple trailing ws, not at line end 

	@Expose public boolean alignAbove;		// aligned to a node feature in a surrounding real line
	@Expose public boolean alignBelow;

	@Expose public boolean blankAbove;		// blank line separates
	@Expose public boolean blankBelow;

	@Expose public int indents;				// absolute number of indents
	@Expose public int relDents;			// num indents relative to real line above

	@Expose public int numBefore;			// num leading ws, not at line begin
	@Expose public int numAfter; 			// num trailing ws, not at line end 

	@Expose public int alignCol;			// alignment column

	@Expose public boolean joinAlways;
	@Expose public boolean joinShould;
	@Expose public boolean joinNever;
	@Expose public boolean joinAllow;

	@Expose public boolean noFormat;

	// ------------------------------------------------------------------------

	/** Rule */
	public Format(ParseData data, ParserRuleContext ctx) {
		CommonToken start = (CommonToken) ctx.getStart();
		CommonToken stop = (CommonToken) ctx.getStop();
		Document doc = data.getDocument();
		Info begInfo = doc.getInfo(start.getLine() - 1);
		Info endInfo = doc.getInfo(stop.getLine() - 1);
		int len = stop.getStopIndex() - start.getStartIndex() + 1;
		characterize(data, start, stop, begInfo, endInfo, len);
	}

	/** Terminal */
	public Format(ParseData data, TerminalNode terminal) {
		CommonToken token = (CommonToken) terminal.getSymbol();
		Info info = data.getDocument().getInfo(token.getLine() - 1);
		characterize(data, token, token, info, info, token.getText().length());
	}

	/** Comment */
	public Format(ParseData data, Token token) {
		Info info = data.getDocument().getInfo(token.getLine() - 1);
		characterize(data, token, token, info, info, token.getText().length());
	}

	public Format(Format lhs, Format rhs) {
		atLineBeg = lhs.atLineBeg && rhs.atLineBeg;
		atLineEnd = lhs.atLineEnd && rhs.atLineEnd;
		wsBefore = lhs.wsBefore || rhs.wsBefore;
		wsAfter = lhs.wsAfter || rhs.wsAfter;
		multBefore = lhs.multBefore || rhs.multBefore;
		multAfter = lhs.multAfter || rhs.multAfter;
		alignAbove = lhs.alignAbove || rhs.alignAbove;
		alignBelow = lhs.alignBelow || rhs.alignBelow;
		blankAbove = lhs.blankAbove || rhs.blankAbove;
		blankBelow = lhs.blankBelow || rhs.blankBelow;

		indented = atLineBeg && (lhs.indented || rhs.indented);
		indents = atLineBeg ? Math.max(lhs.indents, rhs.indents) : 0;
		relDents = atLineBeg ? Math.max(lhs.relDents, rhs.relDents) : 0;
		numBefore = Math.max(lhs.numBefore, rhs.numBefore);
		numAfter = Math.max(lhs.numAfter, rhs.numAfter);
		alignCol = Math.max(lhs.alignCol, rhs.alignCol);
	}

	public double similarity(Format o) {
		boolean[] tvec = simVector();
		boolean[] ovec = o.simVector();
		int len = tvec.length;
		int match = 0;
		for (int idx = 0; idx < len; idx++) {
			if (tvec[idx] == ovec[idx]) match++;
		}

		// Jaccard (like) similarity
		return match / (2 * len - match);
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
		return new boolean[] { indented, atLineBeg, atLineEnd, wsBefore, wsAfter, multBefore, multAfter, alignAbove,
				alignBelow, blankAbove, blankBelow, relDents > -1, Math.abs(relDents) > 0 };
	}

	private void characterize(ParseData data, Token start, Token stop, Info begInfo, Info endInfo, int len) {
		int idxStart = start.getTokenIndex();
		int idxStop = stop.getTokenIndex();
		int line = start.getLine() - 1;

		atLineBeg = start.getCharPositionInLine() == begInfo.beg;
		atLineEnd = len == endInfo.end;

		if (atLineBeg) {
			indented = begInfo.indents > 0;
			indents = begInfo.indents;
			relDents = begInfo.indents - begInfo.priorIndents;
		}

		numBefore = leftWS(data, idxStart).length();
		wsBefore = numBefore > 0;
		multBefore = numBefore > 1;

		numAfter = rightWS(data, idxStop).length();
		wsAfter = numAfter > 0;
		multAfter = numAfter > 1;

		blankAbove = begInfo.blankAbove;
		blankBelow = endInfo.blankBelow;

		alignCol = aligned(data, start, nonBlankLine(data, line, -1));
		alignAbove = alignCol > 0;

		int tmp = aligned(data, start, nonBlankLine(data, line, 1));
		alignBelow = tmp > 0;
		if (!alignAbove) alignCol = tmp;
	}

	// returns the alignment column after the first non-blank; -1 if not aligned
	private static int aligned(ParseData data, Token start, int line) {
		if (line != -1) {
			int visCol = data.tokenVisOffsetIndex.get(start);
			Info info = data.doc.getInfo(line);
			if (visCol > info.beg) {
				List<Token> tokens = data.lineTokensIndex.get(line);
				for (Token token : tokens) {
					int tokCol = data.tokenVisOffsetIndex.get(token);
					if (tokCol < visCol) continue;
					if (tokCol > visCol) break;
					if (tokCol == visCol) return visCol;
				}
			}
		}
		return -1;
	}

	private static String leftWS(ParseData data, int idx) {
		List<Token> leftHidden = data.tokenStream.getHiddenTokensToLeft(idx);
		if (leftHidden != null) {
			for (Token left : leftHidden) {
				if (left.getType() == data.BLOCKCOMMENT) continue;
				if (left.getType() == data.HWS) return left.getText();
			}
		}
		return "";
	}

	private static String rightWS(ParseData data, int idx) {
		List<Token> rightHidden = data.tokenStream.getHiddenTokensToRight(idx);
		if (rightHidden != null) {
			for (Token right : rightHidden) {
				if (right.getType() == data.BLOCKCOMMENT) continue;
				if (right.getType() == data.LINECOMMENT) return "";
				if (right.getType() == data.HWS) return right.getText();
			}
		}
		return "";
	}

	private static int nonBlankLine(ParseData data, int line, int dir) {
		while (line + dir >= 0 && line + dir < data.lineTokensIndex.size()) {
			line += dir;
			if (!isBlank(data, line)) return line;
		}
		return -1;
	}

	private static boolean isBlank(ParseData data, int line) {
		List<Token> tokens = data.lineTokensIndex.get(line);
		if (tokens == null) return true;
		for (Token token : tokens) {
			if (!isWhitespace(data, token)) return false;
		}
		return true;
	}

	private static boolean isWhitespace(ParseData data, Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
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
		if (alignAbove) builder.append("alignAbove, ");
		if (alignBelow) builder.append("alignBelow, ");
		if (blankAbove) builder.append("blankAbove, ");
		if (blankBelow) builder.append("blankBelow, ");
		int dot = builder.lastIndexOf(",");
		if (dot > -1) builder.setLength(dot);
		return builder.toString();
	}
}
