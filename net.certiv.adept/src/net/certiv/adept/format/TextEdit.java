package net.certiv.adept.format;

import java.util.Comparator;

import org.antlr.v4.runtime.Token;

public class TextEdit implements Comparator<TextEdit> {

	private static final String Msg = "@%s:%s '%s'";

	private int left = Token.EOF;
	private int right = Token.EOF;
	private String replace = "";

	/**
	 * Replace the existing text (should be ws only) between (exclusive) the given token indexes with
	 * the new given string value.
	 *
	 * @param left token index to the left
	 * @param right token index to the right
	 * @param replace replacement string
	 */
	public TextEdit(int left, int right, String replace) throws FormatException {
		this.left = left;
		this.right = right;
		this.replace = replace;
		if (left < 0 || right < 0 || replace == null) throw new FormatException("Malformed", this);
	}

	public Region getRegion() {
		return new Region(left, right);
	}

	public int left() {
		return left;
	}

	public int right() {
		return right;
	}

	public String replacement() {
		return replace;
	}

	public void setReplacement(String replace) {
		this.replace = replace;
	}

	@Override
	public int compare(TextEdit e1, TextEdit e2) {
		if (e1.left < e2.left) return -1;
		if (e1.left > e2.left) return 1;
		if (e1.right < e2.right) return -1;
		if (e1.right > e2.right) return 1;
		return e1.replace.compareTo(e2.replace);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + left;
		result = prime * result + ((replace == null) ? 0 : replace.hashCode());
		result = prime * result + right;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TextEdit other = (TextEdit) obj;
		if (left != other.left) return false;
		if (replace == null) {
			if (other.replace != null) return false;
		} else if (!replace.equals(other.replace)) return false;
		if (right != other.right) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(Msg, left, right, replace);
	}
}
