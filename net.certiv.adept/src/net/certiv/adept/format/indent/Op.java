package net.certiv.adept.format.indent;

// Mark operations
public enum Op {
	ROOT,	// 0 indent level (force)

	IN,		// indent
	OUT,	// dedent

	BEG,	// wrappable statement
	END;
}
