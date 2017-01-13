package net.certiv.adept.core;

import java.util.LinkedHashMap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;

public class Index {

	// key=token index; value=source feature
	private LinkedHashMap<Integer, Feature> index;

	public Index(int size) {
		index = new LinkedHashMap<>(size);
	}

	public void add(int idx, Feature feature) {
		Feature existing = index.get(idx);
		if (existing != null) {
			Log.error(this, "Token feature conflict: " + existing.diff(feature));
			return;
		}
		index.put(idx, feature);
	}

	public Feature get(int idx) {
		return index.get(idx);
	}
}
