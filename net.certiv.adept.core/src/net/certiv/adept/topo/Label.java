package net.certiv.adept.topo;

import java.util.Map;

public enum Label {

	// feature

	TYPE("type", 1.0),
	TEXT("text", 1.0),
	SIZE("size", 1.0),
	WEIGHT("rarity", 1.0),
	EDGES("edges", 1.0),
	EDGE_TYPES("edgeTypes", 1.0),
	FORMAT("format", 1.0),

	// edge set
	DISIM("disim", 0.8),

	// edge

	LTYPE("leafType", 1.0),
	LTEXT("leafText", 1.0),
	METRIC("metric", 1.0),
	RARITY("rarity", 1.0),

	;

	private final String name;
	private final double defValue;

	Label(String text, double def) {
		name = text;
		defValue = def;
	}

	public static void loadDefaults(Map<String, Double> labelBoosts) {
		for (Label label : Label.values()) {
			labelBoosts.put(label.name, label.defValue);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
