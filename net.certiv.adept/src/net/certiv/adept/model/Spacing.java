package net.certiv.adept.model;

public enum Spacing {

	HFLEX("HFlex", 1),
	HSPACE("HSpace", 2),
	NONE("None", 3),
	VLINE("VLine", 4),
	VFLEX("VFlex", 5),

	UNKNOWN("", 99);

	private final String _name;
	private final int _id;

	private Spacing(String name, int id) {
		_name = name;
		_id = id;
	}

	public int id() {
		return _id;
	}

	public boolean terminal() {
		return this == VLINE || this == VFLEX;
	}

	@Override
	public String toString() {
		return _name;
	}
}
