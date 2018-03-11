package net.certiv.adept.model;

public enum Spacing {

	HFLEX(1),
	HSPACE(2),
	NONE(3),
	VLINE(4),
	VFLEX(5),

	UNKNOWN(99);

	private final int value;

	private Spacing(int id) {
		value = id;
	}

	public int id() {
		return value;
	}

	public static double simularity(Spacing a, Spacing b) {
		double dist = Math.abs(a.value - b.value);
		double norm = Math.min(5, dist) / 5;
		return 1 - norm;
	}
}
