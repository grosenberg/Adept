package net.certiv.adept.core;

import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Form;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Span {

	private ParseData data;
	private List<Token> tokens;

	private AdeptToken lhs;
	private AdeptToken rhs;
	private boolean done;

	private int beg;
	private int end;
	private String lead;
	private String trail;
	private int terminals;
	private int blankLines;
	private Set<Facet> facets;
	private int formLhs;
	private int formRhs;
	private int dents;

	public Span(ParseData data) {
		this.data = data;

		tokens = data.getTokens();
		if (tokens.size() > 0) {
			rhs = (AdeptToken) tokens.get(0);
		} else {
			done = true;
		}
	}

	/**
	 * Returns the span starting at the token having the given index and ending on the next real
	 * token.
	 */
	public Span next(int idx) {
		if (!done && idx < tokens.size()) {
			lhs = rhs;
			rhs = nextReal(idx);
			done = rhs == null;
			if (!done) {
				beg = lhs.getTokenIndex();
				end = rhs.getTokenIndex();

				Interval interval = new Interval(beg + 1, end - 1);
				String content = data.getTokenStream().getText(interval);
				String[] parts = content.split(Strings.EOL, -1);

				terminals = parts.length - 1;
				blankLines = Math.max(terminals - 1, 0);
				lead = parts[terminals];	// leads rhs
				trail = parts[0];			// trails lhs

				formLhs = getMatchedFormat(lhs);
				formRhs = getMatchedFormat(rhs);
				facets = Form.resolveOverlap(formLhs, formRhs);
				dents = Facet.getDentation(formRhs);
			}
		}
		return this;
	}

	// Returns the first non-WS token in the line after the given index, or <code>null</code>.
	private AdeptToken nextReal(int idx) {
		while (idx + 1 < tokens.size()) {
			AdeptToken token = (AdeptToken) tokens.get(++idx);
			if (isReal(token)) return token;
		}
		return null;
	}

	private boolean isReal(AdeptToken token) {
		return token.getType() != data.HWS && token.getType() != data.VWS;
	}

	private int getMatchedFormat(AdeptToken token) {
		if (token == null) return -1;
		if (token.getType() == data.HWS || token.getType() == data.VWS) {
			Log.error(this, "Invalid token to match: " + token.toString());
			return -1;
		}

		Feature feature = data.tokenIndex.get(token.getTokenIndex());
		if (feature != null) {
			Feature matched = feature.getMatched();
			if (matched != null) {
				return matched.getFormat();
			}
		}
		return -1;
	}

	public AdeptToken getLhs() {
		return lhs;
	}

	public AdeptToken getRhs() {
		return rhs;
	}

	public int getFormLhs() {
		return formLhs;
	}

	public int getFormRhs() {
		return formRhs;
	}

	public int getBeg() {
		return beg;
	}

	public int getEnd() {
		return end;
	}

	public String getLead() {
		return lead;
	}

	public String getTrail() {
		if (Tool.settings.removeTrailingWS) return "";
		return trail;
	}

	public boolean insertsTerminal() {
		return terminals > 0;
	}

	public String getTerminals() {
		terminals = getBlankLines() + 1;
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < terminals; idx++) {
			sb.append(Strings.EOL);
		}
		return sb.toString();
	}

	public int getBlankLines() {
		if (Tool.settings.removeBlankLines) {
			blankLines = Math.min(blankLines, Tool.settings.keepBlankLines);
		}
		return blankLines;
	}

	public Set<Facet> getFacets() {
		return facets;
	}

	public int getDentation() {
		return dents;
	}

	public boolean done() {
		return done;
	}

	@Override
	public String toString() {
		return String.format("'%s' -> '%s' %s", lhs.getText(), rhs.getText(), facets);
	}
}
