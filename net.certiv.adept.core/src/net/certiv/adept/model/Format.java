package net.certiv.adept.model;

import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.gson.annotations.Expose;

import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.model.util.Info;
import net.certiv.adept.util.Maths;

public class Format {

	@Expose public boolean indented;		// at line begin and indented

	@Expose public boolean atLineBeg;		// occurs at line begin, ignoring hws and comments
	@Expose public boolean atLineEnd;		// occurs at line end, ignoring hws and comments

	@Expose public boolean wsBefore;		// has leading ws, ignoring any in-line block comment
	@Expose public boolean wsAfter; 		// has trailing ws, ignoring any in-line block comment 
	@Expose public boolean multBefore;		// has multiple leading ws
	@Expose public boolean multAfter; 		// has multiple trailing ws 

	@Expose public boolean alignedAbove;	// to a node feature in a surrounding real line
	@Expose public boolean alignedBelow;

	@Expose public boolean blankAbove;		// blank line separates
	@Expose public boolean blankBelow;

	@Expose public int indents;				// absolute number of indents
	@Expose public int relDents;			// num indents relative to real line above

	@Expose public int numBefore;			// num leading ws
	@Expose public int numAfter; 			// num trailing ws
	@Expose public int visCol;				// visual column

	@Expose public boolean joinAlways;		// derived from corpus as a whole
	@Expose public boolean joinShould;
	@Expose public boolean joinAllow;
	@Expose public boolean joinNever;

	public boolean aligned;					// whether visually aligned
	public boolean join;					// {@code true} to keep or force line joining
	public boolean noFormat;				// no match found in corpus

	// ------------------------------------------------------------------------
	/**
	 * Merge the format of a source document feature with the format of its matched feature. Precedence
	 * is given to the matched feature format aspects with the source format aspects are considered as
	 * hinting.
	 */
	public static Format merge(Format src, Format matched) {
		Format merged = new Format();

		merged.indented = matched.indented;

		merged.atLineBeg = matched.atLineBeg;
		merged.atLineEnd = matched.atLineEnd;

		merged.wsBefore = matched.wsBefore;
		merged.wsAfter = matched.wsAfter;
		merged.multBefore = matched.multBefore;
		merged.multAfter = matched.multAfter;

		merged.alignedAbove = src.alignedAbove || matched.alignedAbove;
		merged.alignedBelow = src.alignedBelow || matched.alignedBelow;

		merged.blankAbove = src.blankAbove && matched.blankAbove;
		merged.blankBelow = src.blankBelow && matched.blankBelow;

		merged.indents = matched.indents;
		merged.relDents = matched.relDents;

		merged.numBefore = Math.max(src.numBefore, matched.numBefore);
		merged.numAfter = Math.max(src.numAfter, matched.numAfter);
		merged.visCol = Maths.ave(src.visCol, matched.visCol);

		merged.joinAlways = matched.joinAlways;
		merged.joinShould = matched.joinShould;
		merged.joinAllow = matched.joinAllow;
		merged.joinNever = matched.joinNever;

		merged.aligned = merged.alignedAbove || merged.alignedBelow;
		merged.join = matched.joinAlways;
		merged.noFormat = matched.noFormat;

		return merged;
	}

	// ------------------------------------------------------------------------

	public Format() {}

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
		sim += boolSim(alignedAbove || alignedBelow, o.alignedAbove || o.alignedBelow);
		sim += boolSim(alignedAbove, o.alignedAbove);
		sim += boolSim(alignedBelow, o.alignedBelow);
		sim += boolSim(blankAbove, o.blankAbove);
		sim += boolSim(blankBelow, o.blankBelow);
		return sim / 5;
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
				alignedBelow, blankAbove, blankBelow, (Integer.signum(relDents) >= 0), (Math.abs(relDents) > 0) };
	}

	private void characterize(ParseData data, Token start, Token stop, Info begInfo, Info endInfo, int len) {
		int idxStart = start.getTokenIndex();
		int idxStop = stop.getTokenIndex();
		int line = start.getLine() - 1; // make zero indexed

		atLineBeg = start.getCharPositionInLine() == begInfo.beg;
		atLineEnd = stop.getCharPositionInLine() == endInfo.end;

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

		visCol = aligned(data, start, nonBlankLine(data, line, -1));
		alignedAbove = visCol > 0;

		int visBelow = aligned(data, start, nonBlankLine(data, line, 1));
		alignedBelow = visBelow > 0;
		if (!alignedAbove) visCol = visBelow;

		aligned = alignedAbove || alignedBelow;
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
					if (tokCol == visCol && start.getType() == token.getType()) {
						return visCol;
					}
				}
			}
		}
		return 0;
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
		if (aligned) builder.append("aligned, ");
		if (alignedAbove) builder.append("alignedAbove, ");
		if (alignedBelow) builder.append("alignedBelow, ");
		if (blankAbove) builder.append("blankAbove, ");
		if (blankBelow) builder.append("blankBelow, ");
		if (indents > 0) builder.append("indents=" + indents + ", ");
		if (relDents != 0) builder.append("relDents=" + relDents + ", ");
		if (numBefore > 1) builder.append("numBefore=" + numBefore + ", ");
		if (numAfter > 1) builder.append("numAfter=" + numAfter + ", ");
		if (visCol != 0) builder.append("visCol=" + visCol + ", ");
		if (joinAlways) builder.append("joinAlways, ");
		if (joinShould) builder.append("joinShould, ");
		if (joinAllow) builder.append("joinAllow, ");
		if (joinNever) builder.append("joinNever, ");
		if (aligned) builder.append("aligned, ");
		if (join) builder.append("join, ");
		if (noFormat) builder.append("noFormat, ");

		int dot = builder.lastIndexOf(",");
		if (dot > -1) builder.setLength(dot);
		return builder.toString();
	}
}
