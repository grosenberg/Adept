package net.certiv.adept.topo;

import java.util.Map;

public enum Factor {

	// feature labels
	FORMAT("format", 1.0),
	DENTATION("dentation", 1.0),
	TEXT("text", 1.0),
	WEIGHT("weight", 0.5),
	EDGE_TYPES("edgeTypes", 0.1),
	EDGE_CNT("edgeCount", 0.1),

	// edge labels
	METRIC("metric", 0.8),

	// edge sets
	ORDER("order", 0.6),		// fraction of 1.0 allocated to order significance
	DISCOUNT("discount", 0.4),	// for expected disjoints

	// not used
	RARITY("rarity", 1.0),
	TYPE("type", 1.0),
	SIZE("size", 1.0),

	;

	private final String name;
	private final double defValue;

	Factor(String text, double def) {
		name = text;
		defValue = def;
	}

	public static void loadDefaults(Map<Factor, Double> labelBoosts) {
		for (Factor factor : Factor.values()) {
			labelBoosts.put(factor, factor.defValue);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
