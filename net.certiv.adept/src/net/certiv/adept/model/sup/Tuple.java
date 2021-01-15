package net.certiv.adept.model.sup;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.Record;
import net.certiv.adept.model.Document;
import net.certiv.adept.store.TreeMultilist;
import net.certiv.adept.util.Strings;

public class Tuple {

	CoreMgr mgr;
	Document doc;
	Record rec;
	int tabWidth;

	public Tuple(CoreMgr mgr, Document doc) {
		super();
		this.mgr = mgr;
		this.doc = doc;
		rec = doc.getRecord();
		tabWidth = doc.getTabWidth();

		Field.clearPool();

		buildLines();
	}

	public void buildTuples(ParserRuleContext ctx) {
		List<Integer> ancestors = rec.getAncestorPath(ctx);
		for (ParseTree child : ctx.children) {
			if (child instanceof TerminalNode) {
				AdeptToken token = (AdeptToken) ((TerminalNode) child).getSymbol();
				AdeptToken left = rec.getRealLeft(token.getTokenIndex());
				Field field = Field.get(this, left, token, ancestors);
				rec.tuples.put(left.getType(), token.getType(), field);
			}
		}
	}

	private void buildLines() {
		List<AdeptToken> tokens = rec.getTokens();
		AdeptToken token = null;
		for (int idx = 0, len = tokens.size(); idx < len; idx++) {
			AdeptToken peer = tokens.get(idx);
			if (!rec.isWhitespace(peer)) {
				if (token != null) buildLines(token);
				token = peer;
			}
		}
	}

	private void buildLines(AdeptToken token) {
		Integer lnum = token.getLinePos();
		TreeMultilist<Integer, AdeptToken> line = rec.lines.get(lnum);
		Integer soff = line != null ? line.firstKey() : findStartOffset(token);
		rec.lines.put(lnum, soff, token);

		String lead = rec.getTextSpan(soff, token.getStartIndex());
		int vpos = Strings.measureVisualWidth(lead, tabWidth);
		token.setVisPos(vpos);
	}

	private Integer findStartOffset(AdeptToken left) {
		int soff = left.getStartIndex();
		List<Token> hidden = rec.tokenStream.getHiddenTokensToLeft(left.getTokenIndex());
		for (int idx = hidden.size() - 1; idx >= 0; idx--) {
			if (hidden.get(idx).getType() != rec.HWS) break;
			soff--;
		}
		return soff;
	}
}
