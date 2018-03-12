package net.certiv.adept.format;

import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Format;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Span {

	private ParseRecord data;
	private Settings settings;
	private List<Token> tokens;

	Token lhs;
	boolean done;
	int end;

	private Token rhs;
	private int eols;
	private int blankLines;
	private int indents;
	private String lead;
	private String trail;
	private Gap gap;

	public Span(ParseRecord data, Settings settings) {
		this.data = data;
		this.settings = settings;

		tokens = data.getTokens();
		if (tokens.size() > 0) {
			rhs = tokens.get(0);
		} else {
			done = true;
		}
	}

	/**
	 * Returns the next span starting at the token having the given index, nominally a real token, and
	 * ending on the next real token. If the initial lhs is not real, fake a real by adjusting the lhs
	 * index.
	 *
	 * <pre>
	        |<-----     span     ----->|
	    ... | lhs real | ws | rhs real | ...
	 * </pre>
	 */
	public Span next(int idx) {
		if (!done && idx < tokens.size()) {
			lhs = rhs;
			rhs = nextReal(idx);
			done = rhs == null;
			if (!done) {
				int beg = lhs.getTokenIndex();
				end = rhs.getTokenIndex();
				if (!isReal(lhs)) beg--;

				Interval interval = new Interval(beg + 1, end - 1);
				String content = data.getTokenStream().getText(interval);
				String[] parts = content.split(Strings.EOL, -1);

				eols = parts.length - 1;
				blankLines = Math.max(eols - 1, 0);
				lead = parts[eols];			// leads rhs
				trail = parts[0];			// trails lhs

				Format formLhs = matchedFormat(lhs);
				Format formRhs = matchedFormat(rhs);
				gap = Gap.define(formLhs, formRhs);
				dump();
			}
		}
		return this;
	}

	protected void dump() {
		String lhsText = lhs.getText();
		String gapWs = Strings.encodeWS(getGapWs());
		String rhsText = rhs != null ? rhs.getText() : "";
		Log.debug(this, String.format("%s%s%s", lhsText, gapWs, rhsText));
	}

	// Returns the first non-WS token in the line after the given index, or <code>null</code>.
	private Token nextReal(int idx) {
		while (idx + 1 < tokens.size()) {
			idx++;
			Token token = tokens.get(idx);
			if (isReal(token)) return token;
		}
		return null;
	}

	private Format matchedFormat(Token token) {
		if (token != null && isReal(token)) {
			Feature docFeature = data.tokenFeatureIndex.get(token);
			if (docFeature != null) {
				Feature matched = docFeature.getMatched();
				if (matched != null) {
					return Format.merge(docFeature, matched);
				}
			}
		}
		return null;
	}

	private boolean isReal(Token token) {
		int type = token.getType();
		return type != data.HWS && type != data.VWS && type != Token.EOF;
	}

	public boolean isAligned() {
		return gap.alignedAbove;
	}

	public int visCol() {
		return gap.visCol;
	}

	public int numWs() {
		return gap.numWs;
	}

	public boolean breaks() {
		Log.debug(this, gap.toString());
		return !gap.noFormat && !gap.join && (gap.lineBreak || eols > 0);
	}

	public String getGapWs() {
		if (gap.noFormat) return lead;
		if (gap.join) {
			if (gap.multWs) return multWs();
			if (gap.ws) return Strings.SPACE;
			return "";
		}
		if (gap.lineBreak || eols > 0) {
			return trailingWs() + eols() + indents();
		}
		if (gap.multWs) return multWs();
		if (gap.ws) return Strings.SPACE;
		return "";
	}

	public String trailingWs() {
		if (settings.removeTrailingWS) return "";
		return trail;
	}

	public String eols() {
		if (eols == 0 && gap.lineBreak) eols++;
		return Strings.getN(eols, Strings.EOL);
	}

	public String indents() {
		if (gap.indented && !gap.alignedAbove) {
			indents = gap.indents;
		} else if (gap.lineBreak) {
			indents = 0;
		}
		return Strings.createIndent(settings.tabWidth, settings.useTabs, indents);
	}

	private String multWs() {
		return Strings.getNSpaces(gap.numWs);
	}

	public int getBlankLines() {
		if (settings.removeBlankLines) {
			blankLines = Math.min(blankLines, settings.keepBlankLines);
		}
		return blankLines;
	}

	@Override
	public String toString() {
		return String.format("'%s' -> '%s' %s", lhs.getText(), rhs.getText(), gap.toString());
	}
}
