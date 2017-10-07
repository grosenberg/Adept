package net.certiv.adept.model.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.TreeMultimap;

/** Performs a ranked cluster analysis. */
public class Chunk {

	// key=total similarity, value=feature
	private static TreeMultimap<Double, Feature> _matches;
	private static Partitioner _partitioner;

	public static void eval(TreeMultimap<Double, Feature> matches) {
		_matches = matches;
		_partitioner = new Partitioner(matches.keySet());
	}

	/**
	 * Returns the collection {@code key=similarity; value=features} of best possible choices. Includes
	 * the features in the max valued partition.
	 */
	public static TreeMultimap<Double, Feature> bestMatches() {
		TreeMultimap<Double, Feature> matches = new TreeMultimap<>();
		int maxIdx = _partitioner.getMaxValuedPartitionIndex();
		if (maxIdx == -1) {
			return _matches;
		}

		double[] maxSims = _partitioner.getValues(maxIdx);
		for (double sim : maxSims) {
			matches.put(sim, _matches.get(sim));
		}
		return matches;
	}

	/**
	 * Returns a single 'best' choice of the given matches. Best is defined as the maximum similarity
	 * value in the minimum valued partition. If multiple matches meet this criteria, then the one with
	 * the greatest frequency of occurrence in corpus will be chosen.
	 */
	public static Feature bestMatch() {
		TreeMultimap<Double, Feature> matches = bestMatches();
		Set<Feature> bests = matches.get(matches.lastKey());
		if (bests.isEmpty()) return null;

		Feature best = null;
		for (Feature feature : bests) {
			if (best == null || best.getEquivalentWeight() < feature.getEquivalentWeight()) {
				best = feature;
			}
		}
		return best;
	}

	/** Returns the partition map of {@code key=similarity, value=partition}. */
	public static Map<Double, Integer> partitionIndices() {
		Map<Double, Integer> indices = new HashMap<>();
		int num = _partitioner.size();
		for (int idx = 0; idx < num; idx++) {
			double[] vals = _partitioner.getValues(idx);
			for (double val : vals) {
				indices.put(val, idx);
			}
		}
		return indices;
	}
}
