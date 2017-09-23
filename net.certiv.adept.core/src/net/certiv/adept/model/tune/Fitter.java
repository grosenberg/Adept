package net.certiv.adept.model.tune;

import java.util.HashMap;
import java.util.Map;

import net.certiv.adept.Tune;
import net.certiv.adept.model.topo.Factor;

public class Fitter {

	private Hypercube cube;
	private Map<Double, Boosts> fits;
	private double best = 0;

	public Fitter(int samples) {
		cube = new Hypercube(Tune.DIMS, samples);
		fits = new HashMap<>();
	}

	public Boosts getBoosts(int setNum) {
		double[] row = cube.getSampleSet(setNum);
		return  new Boosts(row);
	}

	public void put(double fitness, Boosts boosts) {
		fits.put(fitness, boosts);
	}

	public void put(double fitness, Map<Factor, Double> boosts) {
		fits.put(fitness, new Boosts(boosts));
	}

	public int size() {
		return fits.size();
	}

	public boolean isEmpty() {
		return fits.isEmpty();
	}

	public void clear() {
		fits.clear();
	}

	/**
	 * Returns the boost set corresponding to the best fit of boost values to desired formatting
	 * result.
	 */
	public Boosts bestFit() {
		for (double fit : fits.keySet()) {
			best = Math.max(best, fit);
		}
		return fits.get(best);
	}

	public double bestFittness() {
		return best;
	}
}
