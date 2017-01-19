package net.certiv.adept.model;

public enum Kind {
	RULE("Rule"),
	NODE("Node"),
	BLOCKCOMMENT("Block Comment"),
	LINECOMMENT("Line Comment");

	private String fName;

	Kind(String name) {
		fName = name;
	}

	@Override
	public String toString() {
		return fName;
	}
}
