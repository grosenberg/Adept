package net.certiv.adept.model.util;

public enum Kind {
	RULE("Rule"),
	TERMINAL("Node"),
	BLOCKCOMMENT("Block Comment"),
	LINECOMMENT("Line Comment");

	private String kind;

	Kind(String name) {
		kind = name;
	}

	@Override
	public String toString() {
		return kind;
	}
}
