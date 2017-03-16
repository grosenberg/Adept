package net.certiv.adept.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import com.google.common.collect.TreeMultimap;

import net.certiv.adept.model.Feature;

public class Confidence {

	// private static Feature target;
	private static TreeMultimap<Double, Feature> matches;
	private static Partitioner partitioner;
	private static Comparator<Feature> weightComp = new Comparator<Feature>() {

		@Override
		public int compare(Feature o1, Feature o2) {
			if (o1.getWeight() < o2.getWeight()) return -1;
			if (o1.getWeight() > o2.getWeight()) return 1;
			return 0;
		}
	};

	public static void eval(Feature feature, TreeMultimap<Double, Feature> selected) {
		// target = feature;
		matches = selected;
		partitioner = new Partitioner(selected.keySet());
	}

	/**
	 * Returns the 'best' choice of the given matches. Best is defined as the minimum distance value
	 * in the minimum valued partition. If multiple matches meet this criteria, then the one with
	 * the greatest weight will be chosen.
	 */
	public static Feature best() {
		Double minDistance = matches.keySet().first();
		if (partitioner.size() > 0) {
			minDistance = partitioner.getValues(0)[0];
		}

		NavigableSet<Feature> choices = matches.get(minDistance);
		Feature bestMatch = choices.first();
		if (choices.size() > 1) {
			// TODO: implement heuristics to better select match
			TreeSet<Feature> ts = new TreeSet<>(weightComp);
			ts.addAll(choices);
			bestMatch = ts.last();	// highest weighting
		}
		return bestMatch;
	}

	public static Map<Double, Integer> partitionIndices() {
		Map<Double, Integer> indices = new HashMap<>();
		int num = partitioner.size();
		for (int idx = 0; idx < num; idx++) {
			double[] vals = partitioner.getValues(idx);
			for (double val : vals) {
				indices.put(val, idx);
			}
		}
		return indices;
	}
}
