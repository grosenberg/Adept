package net.certiv.adept.model.util;

import net.certiv.adept.model.tune.Boosts;

public enum Factor {

	ANCESTORS("ancestors", 0.75),

	EDGE_TYPES("edgeTypes", 0.5),
	EDGE_TEXTS("text", 0.5),

	FORMAT("format", 1.0),
	DENTATION("dentation", 1.0),
	DENTSIGN("dentsign", 0.8),
	WEIGHT("weight", 0.5),

	;

	private final String _name;
	private final double _weight;

	Factor(String name, double weight) {
		_name = name;
		_weight = weight;
	}

	public static Boosts getDefaultBoosts() {
		Boosts boosts = new Boosts();
		for (Factor factor : values()) {
			boosts.put(factor, factor.getWeight());
		}
		return boosts;
	}

	public double getWeight() {
		return _weight;
	}

	@Override
	public String toString() {
		return _name;
	}
}
