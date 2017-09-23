package net.certiv.adept.model;

public enum EdgeType {
	KIN,		// parent/child related
	PAIR,		// pair member
	SAME_TYPE,	// constrained identity

	ADJACENT,	// visually before/after on the same line
	ALIGNED,	// vertical visual position

	;
}
