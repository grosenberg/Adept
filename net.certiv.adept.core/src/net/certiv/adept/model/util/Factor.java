package net.certiv.adept.model.util;

import net.certiv.adept.model.tune.Boosts;

public enum Factor {

	ANCESTORS("ancestors", 0.75),
	EDGE_TYPES("types", 0.75),
	EDGE_ASPECTS("aspects", 0.75),
	EDGE_TEXTS("text", 0.5),
	FORMAT_LINE("format", 0.75),
	FORMAT_WS("format", 0.75),
	FORMAT_STYLE("format", 0.5),
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
