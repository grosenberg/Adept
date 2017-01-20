package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.model.Document;
import net.certiv.adept.parser.Collector;

public class Form {

	public static final int ABOVE = 1 << 0;
	public static final int BELOW = 1 << 1;
	public static final int BEFORE = 1 << 2;
	public static final int AFTER = 1 << 3;

	/** Resolve formatting overlap between two real tokens in a line */
	public static List<Facet> resolveOverlap(int formCurr, int formNext) {
		List<Facet> result = new ArrayList<>();
		List<Facet> curr = Facet.get(formCurr);
		List<Facet> next = Facet.get(formNext);
		if (curr.contains(Facet.WS_AFTER) || next.contains(Facet.WS_BEFORE)) {
			result.add(Facet.WS_AFTER);
		}
		if (next.contains(Facet.ALIGNED)) {
			result.add(Facet.ALIGNED);
		}
		return result;
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
		if (col == startInfo.beg) format |= Facet.LINE_BEG.value;
		if (len == stopInfo.len) format |= Facet.LINE_END.value;
		if (followsWSToken(collector, idxStart)) format |= Facet.WS_BEFORE.value;
		if (leadsWSToken(collector, idxStop)) format |= Facet.WS_AFTER.value;
		if (startInfo.blankAbove) format |= Facet.BLANK_ABOVE.value;
		if (stopInfo.blankBelow) format |= Facet.BLANK_BELOW.value;

		if (startInfo.indents > 0) {
			format |= Facet.INDENT.value;
			switch (startInfo.indents - startInfo.priorIndents) {
				case 1:
					format |= Facet.INDENT1_PRIOR.value;
					break;
				case 2:
					format |= Facet.INDENT2_PRIOR.value;
					break;
				case 3:
					format |= Facet.INDENT3_PRIOR.value;
					break;
				case -1:
					format |= Facet.OUTDENT1_PRIOR.value;
					break;
				case -2:
					format |= Facet.OUTDENT2_PRIOR.value;
					break;
				case -3:
					format |= Facet.OUTDENT3_PRIOR.value;
					break;
				default:
					if (startInfo.indents > startInfo.priorIndents) {
						format |= Facet.INDENT4_PRIOR.value;
					} else {
						format |= Facet.OUTDENT4_PRIOR.value;
					}
			}
		}
		return format;
	}

	private static boolean followsWSToken(Collector collector, int idx) {
		List<Token> leftHidden = collector.stream.getHiddenTokensToLeft(idx);
		if (leftHidden != null) {
			for (Token left : leftHidden) {
				if (left.getType() == collector.BLOCKCOMMENT) continue;
				if (left.getType() == collector.LINECOMMENT) continue;
				if (left.getType() == collector.HWS) return true;
			}
		}
		return false;
	}

	private static boolean leadsWSToken(Collector collector, int idx) {
		List<Token> rightHidden = collector.stream.getHiddenTokensToRight(idx);
		if (rightHidden != null) {
			for (Token right : rightHidden) {
				if (right.getType() == collector.BLOCKCOMMENT) continue;
				if (right.getType() == collector.LINECOMMENT) continue;
				if (right.getType() == collector.HWS) return true;
			}
		}
		return false;
	}
}
