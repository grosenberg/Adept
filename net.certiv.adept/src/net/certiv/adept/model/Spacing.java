package net.certiv.adept.model;

public enum Spacing {

	HFLEX("HFlex", 1),
	HSPACE("HSpace", 2),
	NONE("None", 3),
	VLINE("VLine", 4),
	VFLEX("VFlex", 5),

	UNKNOWN("", 99);

	private final String _name;
	private final int _div;

	private Spacing(String name, int div) {
		_name = name;
		_div = div;
	}

	public boolean terminal() {
		return this == VLINE || this == VFLEX;
	}

	public static double score(Spacing a, Spacing b) {
		if (a == UNKNOWN || b == UNKNOWN) return 0;

		double dist = 4 - Math.abs(a._div - b._div);
		return dist / 4;
	}

	@Override
	public String toString() {
		return _name;
	}
}
