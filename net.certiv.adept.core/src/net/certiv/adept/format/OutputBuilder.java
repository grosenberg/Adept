package net.certiv.adept.format;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.Settings;
import net.certiv.adept.format.align.AlignBuffer;
import net.certiv.adept.util.Strings;

public class OutputBuilder {

	private final StringBuilder sb = new StringBuilder();
	private final Settings settings;
	private final AlignBuffer buffer;

	public OutputBuilder(int tabWidth, Settings settings) {
		this.settings = settings;
		this.buffer = new AlignBuffer(this, tabWidth);
	}

	/** Add a token to the align buffer for alignment at the given visual column. */
	public void aligned(Token token, int visCol, int numWs) {
		buffer.aligned(token, visCol, numWs);
	}

	/** Add a token to the align buffer. */
	public void add(Token token) {
		buffer.add(token);
	}

	public void eol(String trailingWs, String eols, String indents) {
		String ws = !settings.removeTrailingWS ? trailingWs : "";
		buffer.eol(ws, eols, indents);
	}

	/** Add a string to the align buffer. */
	public void add(String text) {
		buffer.add(text);
	}

	/** Appends the content of the AlignBuffer to this builder. */
	public void append(String content) {
		sb.append(content);
	}

	public void flush() {
		buffer.flush();
		if (settings.forceLastLineBlank) {
			int dot = sb.lastIndexOf(Strings.EOL);
			dot = Math.max(dot, 0);
			String last = sb.substring(dot).trim();
			if (!last.isEmpty()) {
				sb.append(Strings.EOL);
			}
		}
	}

	public boolean isEmpty() {
		return sb.length() == 0;
	}

	public void clear() {
		sb.setLength(0);
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
