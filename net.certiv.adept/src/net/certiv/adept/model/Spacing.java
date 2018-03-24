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

	public boolean terminal() {
		return value == VLINE.ordinal() || value == VFLEX.ordinal();
	}
}
