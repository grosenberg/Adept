package net.certiv.adept.topo;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Document;
import net.certiv.adept.parser.Collector;

public class Form {

	/**
	 * Resolve the formatting overlap between two tokens in a line. The given lhs token will be a
	 * real token. The rhs token will be real or -1, indicating that the lhs is the last real token
	 * before the terminator.
	 * <p>
	 * Between any two tokens, the resolved result deterines whether there is zero, one, wide or
	 * aligned spacing. For the last real token, the resolved result deterines how the next line is
	 * joined.
	 */
	public static Set<Facet> resolveOverlap(int lhs, int rhs) {
		Set<Facet> resolved = new LinkedHashSet<>();
		Eval lhsEval = new Eval(lhs);
		Eval rhsEval = new Eval(rhs);

		// decide spacing
		if (lhsEval.all(Facet.WS_AFTER).or(rhsEval.all(Facet.WS_BEFORE)).result()) {
			if (lhsEval.all(Facet.WIDE_AFTER).or(rhsEval.all(Facet.WIDE_BEFORE)).result()) {
				resolved.add(Facet.WIDE_AFTER);
			} else {
				resolved.add(Facet.WS_AFTER);
			}
		}

		if (rhsEval.all(Facet.ALIGNED_ABOVE).result()) {
			resolved.add(Facet.ALIGNED_ABOVE);
		}
		if (rhsEval.all(Facet.ALIGNED_BELOW).result()) {
			resolved.add(Facet.ALIGNED_BELOW);
		}

		// decide joining
		if (lhsEval.all(Facet.AT_LINE_END).and(rhsEval.all(Facet.JOIN_NEVER)).result()) {
			resolved.add(Facet.JOIN_NEVER);
		}
		return resolved;
	}

	/** Characterize a rule */
	public static int characterize(Collector collector, ParserRuleContext ctx) {
		CommonToken start = (CommonToken) ctx.getStart();
		CommonToken stop = (CommonToken) ctx.getStop();
		Document doc = collector.getDocument();
		FormInfo startInfo = doc.getInfo(start.getLine() - 1);
		FormInfo stopInfo = doc.getInfo(stop.getLine() - 1);
		int idxStart = start.getTokenIndex();
		int idxStop = stop.getTokenIndex();
		int col = start.getCharPositionInLine();
		int len = stop.getCharPositionInLine() + stop.getText().length();

		return characterize(collector, startInfo, stopInfo, idxStart, idxStop, col, len);
	}

	/** Characterize a node */
	public static int characterize(Collector collector, TerminalNode node) {
		CommonToken token = (CommonToken) node.getSymbol();
		FormInfo formInfo = collector.getDocument().getInfo(token.getLine() - 1);
		int idx = token.getTokenIndex();
		int col = token.getCharPositionInLine();
		int len = token.getText().length();

		return characterize(collector, formInfo, formInfo, idx, idx, col, len);
	}

	/** Characterize a comment */
	public static int characterize(Collector collector, Token token) {
		FormInfo formInfo = collector.getDocument().getInfo(token.getLine() - 1);
		int idx = token.getTokenIndex();
		int col = token.getCharPositionInLine();
		int len = token.getText().length();

		return characterize(collector, formInfo, formInfo, idx, idx, col, len);
	}

	private static int characterize(Collector collector, FormInfo startInfo, FormInfo stopInfo, int idxStart,
			int idxStop, int col, int len) {

		int format = 0;
		if (col == startInfo.beg) {
			format |= Facet.AT_LINE_BEG.value;
			if (startInfo.indents > 0) {
				format |= Facet.INDENTED.value;
				int dents = startInfo.indents - startInfo.priorIndents;
				format |= (dents + Facet.ZERO);
			}
		} else {
			switch (lefHtWS(collector, idxStop).length()) {
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
			switch (rightWS(collector, idxStop).length()) {
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

	private static String lefHtWS(Collector collector, int idx) {
		List<Token> leftHidden = collector.stream.getHiddenTokensToLeft(idx);
		if (leftHidden != null) {
			for (Token left : leftHidden) {
				if (left.getType() == collector.BLOCKCOMMENT) continue;
				if (left.getType() == collector.HWS) return left.getText();
			}
		}
		return "";
	}

	private static String rightWS(Collector collector, int idx) {
		List<Token> rightHidden = collector.stream.getHiddenTokensToRight(idx);
		if (rightHidden != null) {
			for (Token right : rightHidden) {
				if (right.getType() == collector.BLOCKCOMMENT) continue;
				if (right.getType() == collector.LINECOMMENT) return "";
				if (right.getType() == collector.HWS) return right.getText();
			}
		}
		return "";
	}
}
