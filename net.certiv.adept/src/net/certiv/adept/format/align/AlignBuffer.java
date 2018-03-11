package net.certiv.adept.format.align;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.format.OutputBuilder;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.TreeMultimap;

public class AlignBuffer {

	// key=line idx; value=list of parts
	private final TreeMultimap<Integer, Part> buffer = new TreeMultimap<>();
	private final OutputBuilder builder;
	private final int tabWidth;
	private final Aligner aligner;

	private int lineIdx;

	public AlignBuffer(OutputBuilder builder, int tabWidth) {
		this.builder = builder;
		this.tabWidth = tabWidth;
		buffer.put(lineIdx, new Part(tabWidth, Text.PACK, 0));
		aligner = new Aligner(buffer, tabWidth);
	}

	public void eol(String trailingWs, String eols, String indents) {
		add(trailingWs);
		add(eols);
		if (!indents.isEmpty()) {
			add(indents);
			buffer.put(lineIdx, new Part(tabWidth, Text.INDENT, 0));
		}
	}

	/**
	 * Add gap text. For gaps containg line breaks, any trailing ws, eols, and indents should be added
	 * separately.
	 */
	public void add(String text) {
		append(text);
		switch (Strings.count(text, Strings.EOL)) {
			case 0:
				return;
			case 1:
				lineIdx++;
				buffer.put(lineIdx, new Part(tabWidth, Text.PACK, 0));
				break;
			default:
				flush();
		}
	}

	/**
	 * Add a real token that to be aligned near the given visCol. The first char of the token text is
	 * used as the alignment identifier type.
	 */
	public void aligned(Token token, int visCol, int numWs) {
		Part last = buffer.get(lineIdx).last();
		int minLength = last.visCol + last.vWidth + numWs + 1;
		Part part = new Part(tabWidth, Text.ALIGN, Math.max(minLength, visCol));
		part.append(token.getText());
		buffer.put(lineIdx, part);
	}

	/** Add a real token that is not otherwise aligned. */
	public void add(Token token) {
		append(token.getText());
	}

	private void append(String text) {
		Part last = buffer.get(buffer.lastKey()).last();
		last.append(text);
	}

	public void flush() {
		aligner.alignBuffer();

		// Log.debug(this, "Flush: " + buffer.toString());
		for (Integer key : buffer.keySet()) {
			for (Part part : buffer.get(key)) {
				builder.append(part.content());
			}
		}

		aligner.clear();
		buffer.clear();
		lineIdx = 0;
		buffer.put(lineIdx, new Part(tabWidth, Text.PACK, 0));
	}
}
