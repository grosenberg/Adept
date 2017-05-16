package net.certiv.adept.model;

public enum Kind {
	RULE("Rule"),
	NODE("Node"),
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
