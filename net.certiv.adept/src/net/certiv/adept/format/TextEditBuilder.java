package net.certiv.adept.format;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;

public class TextEditBuilder {

	private FormatMgr mgr;
	private ParseRecord data;

	public TextEditBuilder(FormatMgr mgr) {
		this.mgr = mgr;
		this.data = mgr.getData();
	}

	/**
	 * Define an edit for the existing text (should be ws only) between the given tokens (exclusive).
	 *
	 * @param beg left token
	 * @param end right token
	 * @throws IllegalArgumentException
	 */
	public TextEdit create(AdeptToken beg, AdeptToken end) {
		String existing = mgr.getTextBetween(beg, end);
		return create(beg, end, existing, existing, 0, "");
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given token indexes
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token index
	 * @param end right token index
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	public TextEdit create(int begIndex, int endIndex, String existing, String replacement, int priority, String msg) {
		AdeptToken beg = begIndex > -1 ? data.getToken(begIndex) : null;
		AdeptToken end = endIndex > -1 ? data.getToken(endIndex) : null;
		return create(beg, end, existing, replacement, priority, msg);
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given tokens
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token
	 * @param end right token
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	public TextEdit create(AdeptToken beg, AdeptToken end, String existing, String replacement, int priority,
			String msg) {

		if ((beg == null && end == null) || existing == null || replacement == null) {
			throw new IllegalArgumentException("Malformed text edit create request.");
		}
		if (end == null) end = data.getToken(data.getTokenStream().size() - 1);
		if (msg == null) msg = "";
		return new TextEdit(beg, end, existing, replacement, priority, msg);
	}
}
