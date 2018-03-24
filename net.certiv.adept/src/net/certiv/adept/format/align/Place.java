package net.certiv.adept.format.align;

public enum Place {

	SOLO,

	BEG,
	MID,
	END,

	ANY; // un-aligned/inapplicable

	public boolean atBOL() {
		return this == SOLO || this == BEG;
	}

	public boolean atEOL() {
		return this == END;
	}
}
