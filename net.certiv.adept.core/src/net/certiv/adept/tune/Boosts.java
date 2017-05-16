package net.certiv.adept.tune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.annotations.Expose;

import net.certiv.adept.Tune;
import net.certiv.adept.topo.Factor;

public class Boosts {

	// key=factor; value=boost value
	@Expose private Map<Factor, Double> boosts;

	public Boosts() {
		boosts = new HashMap<>();
	}

	public Boosts(Map<Factor, Double> boosts) {
		this();
		boosts.putAll(boosts);
	}

	public Boosts(double[] values) {
		this();

		Factor[] factors = Factor.values();
		for (int idx = 0; idx < Tune.DIMS; idx++) {
			boosts.put(factors[idx], values[idx]);
		}
	}

	public void put(Factor factor, double value) {
		boosts.put(factor, value);
	}

	public Map<Factor, Double> getBoosts() {
		return boosts;
	}

	public double get(Factor factor) {
		return boosts.get(factor);
	}

	public boolean isEmpty() {
		return boosts.isEmpty();
	}

	@Override
	public String toString() {
		List<String> s = new ArrayList<>();
		for (Entry<Factor, Double> e : boosts.entrySet()) {
			s.add(String.format("%s %01.4f", e.getKey(), e.getValue()));
		}
		return s.toString();
	}
}
