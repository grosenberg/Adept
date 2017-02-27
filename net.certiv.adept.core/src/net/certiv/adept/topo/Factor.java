package net.certiv.adept.topo;

import java.util.Map;

public enum Factor {

	// feature labels
	FORMAT("format", 1.0),
	WEIGHT("weight", 1.0),
	EDGE_TYPES("edgeTypes", 1.0),
	EDGE_CNT("edgeCount", 1.0),

	// edge labels
	METRIC("metric", 1.0),
	RARITY("rarity", 1.0),

	// edge sets
	EDGE_SET("disim", 0.8),

	// not used
	TYPE("type", 1.0),
	TEXT("text", 1.0),
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
