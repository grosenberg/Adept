package net.certiv.adept.tool;

public enum ErrorKind {
	INFO("info"),
	WARNING("warning"),
	ERROR("error"),
	// FATAL("fatal"),
	;

	public final String text;

	private ErrorKind(String text) {
		this.text = text;
	}
}
