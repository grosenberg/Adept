package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.Utils;

import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;

/** Array of tokens representing a source line, including HWS. */
public class TokenLine {

	private List<AdeptToken> line = new ArrayList<>();
	private ParseData data;
	private LineInfo info;

	public TokenLine(ParseData data) {
		this.data = data;
	}

	public boolean add(AdeptToken token) {
		info = null;
		return line.add(token);
	}

	public LineInfo getInfo() {
		if (info == null) {
			info = new LineInfo(data, line);
		}
		return info;
	}

	public List<AdeptToken> get() {
		return line;
	}

	public AdeptToken get(int index) {
		return line.get(index);
	}

	/**
	 * Return the next non-VWS/HWS token in the line after the given index, or <code>null</code>.
	 */
	public AdeptToken getNextReal(int idx) {
		while (idx + 1 < line.size()) {
			AdeptToken token = line.get(++idx);
			if (isReal(token)) return token;
		}
		return null;
	}

	/**
	 * Return the next HWS token in the line after the given index, or <code>null</code>.
	 */
	public AdeptToken getNextHws(int idx) {
		for (int ptr = idx + 1; ptr < line.size(); ptr++) {
			AdeptToken token = line.get(ptr);
			if (token.getType() == data.HWS) return token;
		}
		return null;
	}

	/**
	 * Return the prior HWS token in the line before the given index, or <code>null</code>.
	 */
	public AdeptToken getPriorHws(int idx) {
		for (int ptr = idx - 1; ptr >= 0; ptr--) {
			AdeptToken token = line.get(ptr);
			if (token.getType() == data.HWS) return token;
		}
		return null;
	}

	public boolean isReal(AdeptToken token) {
		return token.getType() != data.HWS && token.getType() != data.VWS;
	}

	public boolean isComment(AdeptToken token) {
		return token.getType() == data.BLOCKCOMMENT || token.getType() != data.LINECOMMENT;
	}

	public boolean isLineComment(AdeptToken token) {
		return token.getType() == data.LINECOMMENT;
	}

	public boolean isBlank() {
		return getInfo().isBlank();
	}

	public int indexOf(Object o) {
		return line.indexOf(o);
	}

	public boolean contains(Object o) {
		return line.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return line.containsAll(c);
	}

	public boolean isEmpty() {
		return line.isEmpty();
	}

	public void clear() {
		line.clear();
	}

	public int size() {
		return line.size();
	}

	public String content(boolean escape) {
		String content = "";
		if (!line.isEmpty()) {
			AdeptToken first = line.get(0);
			AdeptToken last = line.get(line.size() - 1);
			content = first.getInputStream().getText(new Interval(first.getStartIndex(), last.getStopIndex()));
		}
		if (escape) content = Utils.escapeWhitespace(content, true);
		return content;
	}

	@Override
	public String toString() {
		return String.format("TokenLine: %s", content(true));
	}
}
