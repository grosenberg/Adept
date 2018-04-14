package net.certiv.adept.format.plan.enums;

public enum Place {

	SOLO(0),

	BEG(1),
	MID(2),
	END(3),

	ANY(5);		// un-aligned/inapplicable

	private final int _div;

	private Place(int div) {
		_div = div;
	}

	public boolean atBOL() {
		return this == SOLO || this == BEG;
	}

	public boolean atEOL() {
		return this == END;
	}

	// ANY -> MID = 0.0
	// SOL -> BEG = 0.5
	// BEG -> MID = 0.6
	// BEG -> END = 0.3
	// ANY -> ANY = 1.0
	public static double score(Place a, Place b) {
		if (a == b) return 1;
		if (a == ANY || b == ANY) return 0;
		if (a == SOLO || b == SOLO) {
			if (a == BEG || b == BEG || a == END || b == END) return 0.5;
		}

		double dist = 3 - Math.abs(a._div - b._div);
		return dist / 3;
	}
}
