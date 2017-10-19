package net.certiv.adept.format.align;

import org.antlr.v4.runtime.Token;

import net.certiv.adept.format.OutputBuilder;
import net.certiv.adept.util.ArraySet;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.TreeMultimap;

public class AlignBuffer {

	// key=line idx; value=list of parts
	private final TreeMultimap<Integer, Part> buffer = new TreeMultimap<>();
	private final Aligner aligner;

	private int lineIdx;
	private OutputBuilder builder;

	public AlignBuffer(OutputBuilder builder, int tabWidth) {
		this.builder = builder;
		buffer.put(lineIdx, new Part(Aligner.PACK, 0));
		aligner = new Aligner(buffer, tabWidth);
	}

	/**
	 * Add gap text. For gaps containg line breaks, any trailing ws, eols, and indents should be added
	 * separately.
	 */
	public void add(String text) {
		last().sb.append(text);
		switch (Strings.count(text, Strings.EOL)) {
			case 0:
				return;
			case 1:
				lineIdx++;
				buffer.put(lineIdx, new Part(Aligner.PACK, 0));
				break;
			default:
				flush();
		}
	}

	/** Add a real token that is not otherwise aligned. */
	public void add(Token token) {
		last().sb.append(token.getText());
	}

	/**
	 * Add a real token that to be aligned near the given visCol. The first char of the token text is
	 * used as the alignment identifier mark.
	 */
	public void addAligned(Token token, int visCol) {
		buffer.put(lineIdx, new Part(token.getText().charAt(0), visCol));
		last().sb.append(token.getText());
	}

	private Part last() {
		ArraySet<Part> parts = buffer.get(buffer.lastKey());
		return parts.last();
	}

	public void flush() {
		aligner.alignBuffer();

		for (Integer key : buffer.keySet()) {
			for (Part part : buffer.get(key)) {
				builder.add(part.sb.toString());
			}
		}

		buffer.clear();
		lineIdx = 0;
		buffer.put(lineIdx, new Part(Aligner.PACK, 0));
	}
}
