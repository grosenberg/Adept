package net.certiv.adept.format;

import java.util.List;

import net.certiv.adept.lang.AdeptToken;

/** Unchecked exception thrown to indicate an error in the formatting operation. */
public class FormatterException extends RuntimeException {

	private String desc;
	private TextEdit edit;

	public FormatterException(TextEdit edit) {
		this.edit = edit;
	}

	public FormatterException(AdeptToken key, Region region, List<TextEdit> edits) {}

	public String getDescription() {
		return desc;
	}

	public String getEdit() {
		return edit.toString();
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(desc);
		if (edit != null) sb.append(" " + getEdit());
		return sb.toString();
	}
}
