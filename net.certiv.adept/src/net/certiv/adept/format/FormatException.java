package net.certiv.adept.format;

/** Checked exception thrown to indicate an error in the formatting operation. */
public class FormatException extends Exception {

	private TextEdit edit;

	public FormatException(String msg, TextEdit edit) {
		super(msg);
		this.edit = edit;
	}

	public TextEdit getEdit() {
		return edit;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getMessage());
		if (edit != null) sb.append(": " + edit.toString());
		return sb.toString();
	}
}
