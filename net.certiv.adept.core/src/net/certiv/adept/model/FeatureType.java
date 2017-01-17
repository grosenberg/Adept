package net.certiv.adept.model;

public enum FeatureType {
	RULE("Rule"),
	NODE("Node"),
	BLOCKCOMMENT("Block Comment"),
	LINECOMMENT("Line Comment");

	private String fName;

	FeatureType(String name) {
		fName = name;
	}

	@Override
	public String toString() {
		return fName;
	}
}
