package net.certiv.adept.format;

import java.util.Comparator;

import net.certiv.adept.ITextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.util.Strings;

public class TextEdit implements ITextEdit, Comparator<TextEdit> {

	private static ParseRecord _data;

	private int begIndex;			// beg token index exclusive
	private int endIndex;			// end token index exclusive

	private String existing = "";		// existing text to be replaced
	private String replacement = "";	// the replacement text

	private int priority;			// edit priority level

	private int replOffset;			// starting char offset inclusive
	private int replLen;			// existing length to replacement
	private int replLine;			// starting line
	private int replCol;			// starting col

	private String msg;				// edit type message

	public static void init(ParseRecord data) {
		_data = data;
	}

	/**
	 * Define an edit to replacement the existing text (should be ws only) between the given token
	 * indexes (exclusive) with the new given string value.
	 *
	 * @param begIndex left token index
	 * @param endIndex right token index
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param data parse record data
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws FormatException
	 */
	public static TextEdit create(int begIndex, int endIndex, String existing, String replacement, int priority,
			String msg) throws FormatException {

		return new TextEdit(begIndex, endIndex, existing, replacement, priority, msg);
	}

	private TextEdit(int begIndex, int endIndex, String existing, String replacement, int priority, String msg)
			throws FormatException {

		this.begIndex = begIndex;
		this.endIndex = endIndex;
		this.existing = existing;
		this.replacement = replacement;
		this.priority = priority;
		this.msg = msg != null ? msg : "";

		AdeptToken beg = null;
		AdeptToken end = null;
		if (begIndex > -1) {
			beg = _data.getToken(begIndex);
			if (beg != null) {
				replOffset = beg.getStopIndex() + 1;
				replLine = beg.getLine();
				replCol = beg.getCharPositionInLine();
			}
		}
		if (endIndex > -1) {
			end = _data.getToken(endIndex);
			replLen = end != null ? end.getStartIndex() - replOffset : 0;
		}

		if (beg == null || end == null || replacement == null) {
			throw new FormatException("Malformed", this);
		}
	}

	public Region getRegion() {
		return new Region(this);
	}

	public int begIndex() {
		return begIndex;
	}

	public int endIndex() {
		return endIndex;
	}

	@Override
	public String existing() {
		return existing;
	}

	@Override
	public String replacement() {
		return replacement;
	}

	@Override
	public int priority() {
		return priority;
	}

	@Override
	public int replOffset() {
		return replOffset;
	}

	@Override
	public int replLen() {
		return replLen;
	}

	@Override
	public int replLine() {
		return replLine;
	}

	@Override
	public int replCol() {
		return replCol;
	}

	@Override
	public int compare(TextEdit e1, TextEdit e2) {
		if (e1.begIndex < e2.begIndex) return -1;
		if (e1.begIndex > e2.begIndex) return 1;
		if (e1.endIndex < e2.endIndex) return -1;
		if (e1.endIndex > e2.endIndex) return 1;
		return e1.replacement.compareTo(e2.replacement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begIndex;
		result = prime * result + ((replacement == null) ? 0 : replacement.hashCode());
		result = prime * result + endIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TextEdit other = (TextEdit) obj;
		if (begIndex != other.begIndex) return false;
		if (replacement == null) {
			if (other.replacement != null) return false;
		} else if (!replacement.equals(other.replacement)) return false;
		if (endIndex != other.endIndex) return false;
		return true;
	}

	private static final String Msg = "%6s @%s:%s [%s-%s] %s:%s '%s' -> '%s'";

	@Override
	public String toString() {
		String now = Strings.encodeWS(existing);
		String rep = Strings.encodeWS(replacement);
		return String.format(Msg, msg + ":", replLine + 1, replCol + 1, begIndex, endIndex, replOffset, replLen, now,
				rep);
	}
}
