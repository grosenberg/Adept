package net.certiv.adept.model;

public enum Kind {
	TERMINAL("Node"),
	VAR("Variable"),
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
