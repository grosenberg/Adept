package net.certiv.adept.format;

import java.util.List;
import java.util.TreeMap;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.Settings;
import net.certiv.adept.format.Region;
import net.certiv.adept.format.Region.RegionException;
import net.certiv.adept.format.TextEdit;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Document;
import net.certiv.adept.util.Log;

public class OutputBuilder {

	private final TreeMap<Region, TextEdit> children = new TreeMap<>();

	private ParseRecord data;
	@SuppressWarnings("unused") private int tabWidth;
	@SuppressWarnings("unused") private Settings settings;

	private List<Token> tokens;

	public OutputBuilder(Document doc, Settings settings) {
		this.data = doc.getParseRecord();
		this.tabWidth = doc.getTabWidth();
		this.settings = settings;

		this.tokens = data.getTokens();
	}

	public void add(TextEdit edit) {
		try {
			children.put(edit.getRegion(), edit);
		} catch (RegionException e) {
			Log.error(this, e.getMessage());
		}
	}

	public void addAll(List<TextEdit> edits) {
		for (TextEdit edit : edits) {
			add(edit);
		}
	}

	public boolean apply() {
		StringBuilder content = new StringBuilder(data.getDocument().getContent());
		for (TextEdit edit : children.descendingMap().values()) {
			if (!apply(content, edit)) return false;
		}
		data.getDocument().setModified(content.toString());
		return true;
	}

	private boolean apply(StringBuilder content, TextEdit edit) {
		int beg = tokens.get(edit.left()).getStopIndex() + 1;
		int end = tokens.get(edit.right()).getStartIndex();
		try {
			if (beg == end) {
				content.insert(beg, edit.replacement());
			} else if (beg < end) {
				content.replace(beg, end, edit.replacement());
			}
			return true;
		} catch (StringIndexOutOfBoundsException e) {}
		return false;
	}

	public boolean confictsWith(TextEdit edit) {
		Region region = edit.getRegion();
		Region floor = children.floorKey(region);
		if (floor != null && floor.overlaps(region)) return true;
		Region ceil = children.ceilingKey(region);
		if (ceil != null && ceil.overlaps(region)) return true;
		return false;
	}

	public void clear() {
		children.clear();
	}

	// /** Add a token to the align buffer for alignment at the given visual column. */
	// public void aligned(Token token, int visCol, int numWs) {
	// buffer.aligned(token, visCol, numWs);
	// }
	//
	// /** Add a token to the align buffer. */
	// public void add(Token token) {
	// buffer.add(token);
	// }
	//
	// public void eol(String trailingWs, String eols, String indents) {
	// String ws = !settings.removeTrailingWS ? trailingWs : "";
	// buffer.eol(ws, eols, indents);
	// }
	//
	// /** Add a string to the align buffer. */
	// public void add(String text) {
	// buffer.add(text);
	// }
	//
	// /** Appends the content of the AlignBuffer to this builder. */
	// public void append(String content) {
	// sb.append(content);
	// }
	//
	// public void flush() {
	// buffer.flush();
	// if (settings.forceLastLineBlank) {
	// int dot = sb.lastIndexOf(Strings.EOL);
	// dot = Math.max(dot, 0);
	// String last = sb.substring(dot).trim();
	// if (!last.isEmpty()) {
	// sb.append(Strings.EOL);
	// }
	// }
	// }
	//
	// public boolean isEmpty() {
	// return sb.length() == 0;
	// }
	//
	// public void clear() {
	// sb.setLength(0);
	// }
	//
	// @Override
	// public String toString() {
	// return sb.toString();
	// }
}
