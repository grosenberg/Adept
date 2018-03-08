package net.certiv.adept.format.align;

import net.certiv.adept.util.Strings;

public class Part implements Comparable<Part> {

	private final StringBuilder sb = new StringBuilder();
	private final int tabWidth;

	public final Text type;	// type of part
	public int visCol;		// starting visual column of part
	public int visLen;		// visual length of part
	public int vWidth;		// without trailing ws

	public Part(int tabWidth, Text type, int visCol) {
		this.tabWidth = tabWidth;
		this.type = type;
		this.visCol = visCol;
	}

	public String content() {
		return sb.toString();
	}

	public void append(String text) {
		sb.append(text);
		visLen += Strings.measureVisualWidth(text, tabWidth, visCol + visLen);
		boolean snws = containsNonWS(sb);
		boolean tnws = containsNonWS(text);
		if (!snws && !tnws || tnws) {
			vWidth = visLen;
		}
	}

	private boolean containsNonWS(CharSequence text) {
		for (int idx = 0; idx < text.length(); idx++) {
			if (!Character.isWhitespace(text.charAt(idx))) return true;
		}
		return false;
	}

	public void extendWs(int alignCol) {
		String ws = trimTrailingWs();
		ws = Strings.createVisualWs(tabWidth, visCol + visLen, alignCol);
		append(ws);
	}

	/** Trims and returns the trailing whitespace. */
	private String trimTrailingWs() {
		for (int dot = sb.length() - 1; dot > -1; dot--) {
			if (!Character.isWhitespace(sb.charAt(dot))) {
				dot++;
				if (dot == sb.length()) return "";
				String hws = sb.substring(dot);
				sb.setLength(dot);
				visLen = Strings.measureVisualWidth(sb, tabWidth, visCol);
				return hws;
			}
		}
		String hws = sb.toString();
		sb.setLength(0);
		visLen = 0;
		return hws;
	}

	@Override
	public int compareTo(Part p) {
		if (visCol < p.visCol) return -1;
		if (visCol > p.visCol) return 1;
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String content = Strings.abbreviate(sb.toString(), 50);
		builder.append("[");
		builder.append(type + " ");
		builder.append("@" + visCol);
		builder.append(":" + visLen + "(" + vWidth + ") ");
		builder.append("'" + Strings.encodeWS(content) + "'");
		builder.append("]");
		return builder.toString();
	}
}
