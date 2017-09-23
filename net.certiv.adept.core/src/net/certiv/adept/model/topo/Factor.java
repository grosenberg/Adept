package net.certiv.adept.model.topo;

import net.certiv.adept.model.tune.Boosts;

public enum Factor {

	// feature labels
	FORMAT("format", 1.0),
	DENTATION("dentation", 1.0),
	DENTSIGN("dentsign", 0.8),
	TEXT("text", 0.5),
	WEIGHT("weight", 0.5),
	EDGE_TYPES("edgeTypes", 0.5),
	EDGE_CNT("edgeCount", 0.5),

	// edge labels
	METRIC("metric", 0.7),
	ORTHO("ortho", 1.0),

	// edge sets
	ORDER("order", 0.6),		// for alignment
	INTERSECT("order", 0.6),	// for intersection
	DISCOUNT("discount", 0.4),	// for expected disjoints
	ORTHOSIM("orthoSim", 1.0),

	;

	private final String name;
	private final double defValue;

	Factor(String text, double def) {
		name = text;
		defValue = def;
	}

	public static Boosts getDefaultBoosts() {
		Boosts boosts = new Boosts();
		for (Factor factor : values()) {
			boosts.put(factor, factor.getDefault());
		}
		return boosts;
	}

	public double getDefault() {
		return defValue;
	}

	@Override
	public String toString() {
		return name;
	}
}
