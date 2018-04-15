package net.certiv.adept.model;

public enum Kind {
	TERMINAL("Terminal"),
	BLOCKCOMMENT("Block Comment"),
	LINECOMMENT("Line Comment"),
	WHITESPACE("White Space");

	private String kind;

	Kind(String name) {
		kind = name;
	}

	@Override
	public String toString() {
		return kind;
	}

	public boolean isComment() {
		return this == Kind.BLOCKCOMMENT || this == LINECOMMENT;
	}
}
