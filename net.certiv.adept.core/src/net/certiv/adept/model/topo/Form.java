package net.certiv.adept.model.topo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Document;
import net.certiv.adept.model.load.parser.DocParseData;

public class Form {

	/**
	 * Resolve the formatting overlap between two consecutive real tokens. The rhs token can be -1,
	 * indicating that the lhs is the last real token in the stream.
	 * <p>
	 * The resolved result descibes the WS between the tokens.
	 */
	public static Set<Facet> resolveOverlap(int lhs, int rhs) {
		Set<Facet> resolved = new LinkedHashSet<>();
		Eval lhsEval = new Eval(lhs);
		Eval rhsEval = new Eval(rhs);

		if (lhsEval.all(Facet.NO_FORMAT).and(rhsEval.all(Facet.NO_FORMAT)).result()) {
			resolved.add(Facet.NO_FORMAT);
			return resolved;
		}

		// decide spacing
		if (rhsEval.any(Facet.INDENTED).result()) {
			resolved.add(Facet.INDENTED);
		}

		if (lhsEval.any(Facet.WS_AFTER, Facet.WIDE_AFTER).or(rhsEval.any(Facet.WS_BEFORE, Facet.WIDE_BEFORE))
				.result()) {
			if (lhsEval.all(Facet.WIDE_AFTER).or(rhsEval.all(Facet.WIDE_BEFORE)).result()) {
				resolved.add(Facet.WIDE_AFTER);
			} else {
				resolved.add(Facet.WS_AFTER);
			}
		}

		// decide alignment
		if (lhsEval.all(Facet.ALIGNED).result()) {
			resolved.add(Facet.ALIGNED);
		}
		if (lhsEval.all(Facet.ALIGNED_SAME).result()) {
			resolved.add(Facet.ALIGNED_SAME);
		}

		// decide splitting
		if (lhsEval.all(Facet.AT_LINE_END).or(rhsEval.all(Facet.AT_LINE_BEG)).result()) {
			resolved.add(Facet.AT_LINE_BEG);
		}

		// decide joining
		// if (lhsEval.all(Facet.AT_LINE_END).and(rhsEval.all(Facet.JOIN_NEVER)).result()) {
		// resolved.add(Facet.JOIN_NEVER);
		// }
		return resolved;
	}

	/** Characterize a rule */
	public static int characterize(DocParseData data, ParserRuleContext ctx) {
		CommonToken start = (CommonToken) ctx.getStart();
		CommonToken stop = (CommonToken) ctx.getStop();
		Document doc = data.getDocument();
		Info begInfo = doc.getInfo(start.getLine() - 1);
		Info endInfo = doc.getInfo(stop.getLine() - 1);
		int len = stop.getStopIndex() - start.getStartIndex() + 1;

		return characterize(data, start, stop, begInfo, endInfo, len);
	}

	/** Characterize a node */
	public static int characterize(DocParseData data, TerminalNode node) {
		CommonToken token = (CommonToken) node.getSymbol();
		Info info = data.getDocument().getInfo(token.getLine() - 1);
		return characterize(data, token, token, info, info, token.getText().length());
	}

	/** Characterize a comment */
	public static int characterize(DocParseData data, Token token) {
		Info info = data.getDocument().getInfo(token.getLine() - 1);
		return characterize(data, token, token, info, info, token.getText().length());
	}

	private static int characterize(DocParseData data, Token start, Token stop, Info begInfo, Info endInfo, int len) {

		int idxStart = start.getTokenIndex();
		int idxStop = stop.getTokenIndex();
		int col = start.getCharPositionInLine();

		int format = 0;

		if (col == begInfo.beg) {
			format |= Facet.AT_LINE_BEG.value;
			if (begInfo.indents > 0) {
				format |= Facet.INDENTED.value;
				int dents = begInfo.indents - begInfo.priorIndents;
				format |= (dents + Facet.ZERO);
			}
		} else {
			switch (lefHtWS(data, idxStart).length()) {
				case 0:
					break;
				case 1:
					format |= Facet.WS_BEFORE.value;
					break;
				default:
					format |= Facet.WIDE_BEFORE.value;
			}
		}
		if (len == endInfo.len) {
			format |= Facet.AT_LINE_END.value;
		} else {
			switch (rightWS(data, idxStop).length()) {
				case 0:
					break;
				case 1:
					format |= Facet.WS_AFTER.value;
					break;
				default:
					format |= Facet.WIDE_AFTER.value;
			}
		}
		if (begInfo.blankAbove) format |= Facet.BLANK_ABOVE.value;
		if (endInfo.blankBelow) format |= Facet.BLANK_BELOW.value;

		int line = start.getLine() - 1;
		format |= aligned(data, start, nonBlankLine(data, line, -1));
		format |= aligned(data, start, nonBlankLine(data, line, 1));

		return format;
	}

	// check for alignment on any column after the first non-blank; consider alignment of (1) token
	// types of a defined alignment set to any others of the set (ALIGNED), and (2) token types of a
	// defined mutual alignment set to to same types (ALIGNED_SAME)
	private static int aligned(DocParseData data, Token start, int line) {
		if (line != -1) {
			int visCol = data.visIndex.get(start);
			Info info = data.doc.getInfo(line);
			if (visCol > info.beg) {
				List<Token> tokens = data.lineIndex.get(line);
				for (Token token : tokens) {
					int tokCol = data.visIndex.get(token);
					if (tokCol < visCol) continue;
					if (tokCol > visCol) break;
					if (tokCol == visCol) {
						if (alignSame(data, start.getType(), token.getType())) {
							return Facet.ALIGNED.value | Facet.ALIGNED_SAME.value;
						} else if (aligned(data, start.getType(), token.getType())) {
							return Facet.ALIGNED.value;
						}
					}
				}
			}
		}
		return 0;
	}

	public static boolean alignSame(DocParseData data, int type1, int type2) {
		if (type1 == type2) {
			for (int i : data.ALIGN_SAME) {
				if (i == type1) return true;
			}
		}
		return false;
	}

	public static boolean aligned(DocParseData data, int type1, int type2) {
		return alignable(data, type1) && alignable(data, type2);
	}

	private static boolean alignable(DocParseData data, int type) {
		for (int i : data.ALIGN_ANY) {
			if (i == type) return true;
		}
		return false;
	}

	private static String lefHtWS(DocParseData data, int idx) {
		List<Token> leftHidden = data.stream.getHiddenTokensToLeft(idx);
		if (leftHidden != null) {
			for (Token left : leftHidden) {
				if (left.getType() == data.BLOCKCOMMENT) continue;
				if (left.getType() == data.HWS) return left.getText();
			}
		}
		return "";
	}

	private static String rightWS(DocParseData data, int idx) {
		List<Token> rightHidden = data.stream.getHiddenTokensToRight(idx);
		if (rightHidden != null) {
			for (Token right : rightHidden) {
				if (right.getType() == data.BLOCKCOMMENT) continue;
				if (right.getType() == data.LINECOMMENT) return "";
				if (right.getType() == data.HWS) return right.getText();
			}
		}
		return "";
	}

	private static int nonBlankLine(DocParseData data, int line, int dir) {
		while (line + dir >= 0 && line + dir < data.lineIndex.size()) {
			line += dir;
			if (!isBlank(data, line)) return line;
		}
		return -1;
	}

	private static boolean isBlank(DocParseData data, int line) {
		List<Token> tokens = data.lineIndex.get(line);
		if (tokens == null) return true;
		for (Token token : tokens) {
			if (!isWhitespace(data, token)) return false;
		}
		return true;
	}

	private static boolean isWhitespace(DocParseData data, Token token) {
		return token.getType() == data.HWS || token.getType() == data.VWS;
	}

	// private static boolean inBalancedBlock(DocParseData data, Token token) {
	// Feature feature = data.tokenIndex.get(token.getTokenIndex());
	// Collection<Edge> edges = feature.getEdgeSet().getRuleEdges();
	// for (Edge edge : edges) {
	// if (data.getAsym().contains(edge.leaf.getType())) return false;
	// if (data.getSymm().contains(edge.leaf.getType())) return true;
	// }
	// return false;
	// }
}
