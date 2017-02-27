package net.certiv.adept.topo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Document;
import net.certiv.adept.parser.ParseData;

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

		if (lhsEval.all(Facet.ALIGNED_ABOVE).result()) {
			resolved.add(Facet.ALIGNED_ABOVE);
		}
		if (lhsEval.all(Facet.ALIGNED_BELOW).result()) {
			resolved.add(Facet.ALIGNED_BELOW);
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
	public static int characterize(ParseData data, ParserRuleContext ctx) {
		CommonToken start = (CommonToken) ctx.getStart();
		CommonToken stop = (CommonToken) ctx.getStop();
		Document doc = data.getDocument();
		FormInfo startInfo = doc.getInfo(start.getLine() - 1);
		FormInfo stopInfo = doc.getInfo(stop.getLine() - 1);
		int idxStart = start.getTokenIndex();
		int idxStop = stop.getTokenIndex();
		int col = start.getCharPositionInLine();
		int len = stop.getCharPositionInLine() + stop.getText().length();

		return characterize(data, startInfo, stopInfo, idxStart, idxStop, col, len);
	}

	/** Characterize a node */
	public static int characterize(ParseData data, TerminalNode node) {
		CommonToken token = (CommonToken) node.getSymbol();
		FormInfo formInfo = data.getDocument().getInfo(token.getLine() - 1);
		int idx = token.getTokenIndex();
		int col = token.getCharPositionInLine();
		int len = token.getText().length();

		return characterize(data, formInfo, formInfo, idx, idx, col, len);
	}

	/** Characterize a comment */
	public static int characterize(ParseData data, Token token) {
		FormInfo formInfo = data.getDocument().getInfo(token.getLine() - 1);
		int idx = token.getTokenIndex();
		int col = token.getCharPositionInLine();
		int len = token.getText().length();

		return characterize(data, formInfo, formInfo, idx, idx, col, len);
	}

	private static int characterize(ParseData data, FormInfo startInfo, FormInfo stopInfo, int idxStart, int idxStop,
			int col, int len) {

		int format = 0;
		if (col == startInfo.beg) {
			format |= Facet.AT_LINE_BEG.value;
			if (startInfo.indents > 0) {
				format |= Facet.INDENTED.value;
				int dents = startInfo.indents - startInfo.priorIndents;
				format |= (dents + Facet.ZERO);
			}
		} else {
			switch (lefHtWS(data, idxStop).length()) {
				case 0:
					break;
				case 1:
					format |= Facet.WS_BEFORE.value;
					break;
				default:
					format |= Facet.WIDE_BEFORE.value;
			}
		}
		if (len == stopInfo.len) {
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
		if (startInfo.blankAbove) format |= Facet.BLANK_ABOVE.value;
		if (stopInfo.blankBelow) format |= Facet.BLANK_BELOW.value;
		return format;
	}

	private static String lefHtWS(ParseData data, int idx) {
		List<Token> leftHidden = data.stream.getHiddenTokensToLeft(idx);
		if (leftHidden != null) {
			for (Token left : leftHidden) {
				if (left.getType() == data.BLOCKCOMMENT) continue;
				if (left.getType() == data.HWS) return left.getText();
			}
		}
		return "";
	}

	private static String rightWS(ParseData data, int idx) {
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

	// private static boolean inBalancedBlock(ParseData data, Token token) {
	// Feature feature = data.tokenIndex.get(token.getTokenIndex());
	// Collection<Edge> edges = feature.getEdgeSet().getRuleEdges();
	// for (Edge edge : edges) {
	// if (data.getAsym().contains(edge.leaf.getType())) return false;
	// if (data.getSymm().contains(edge.leaf.getType())) return true;
	// }
	// return false;
	// }
}
