package net.certiv.adept.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.apache.commons.math3.stat.StatUtils;

import com.google.common.collect.TreeMultimap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;

/**
 * Confidence is established by testing if there are sufficient closely matched examples in the
 * corpus.
 * <p>
 * TODO: implement conformal prediction to determine the confidence interval for the current
 * selection set.
 */
public class Confidence {

	private static Feature target;
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
		target = feature;
		matches = selected;
		partitioner = new Partitioner(selected.keySet());
	}

	public static boolean inRange() {
		// Log.debug(Confidence.class, partitioner.clusterDescriptions());
		boolean ok = true;
		int num = partitioner.size();
		if (num == 0) {
			ok = false;
			Log.info(Confidence.class, String.format("No confidence in %s matches for %s", matches.size(), target));
		} else {
			double[] vals = partitioner.getValues();
			double min = StatUtils.min(vals);

			double[] pvals = partitioner.getValues(0);
			double best = StatUtils.min(pvals);
			ok = min == best;
			if (!ok) Log.info(Confidence.class, String.format("No confidence in best match for %s", target));
		}
		return ok;
	}

	/**
	 * Returns the 'best' choice of the given matches. Best is defined as the minimum distance value
	 * in the minimum valued partition. If multiple matches meet this criteria, then the one with
	 * the greatest weight will be chosen.
	 */
	public static Feature best() {
		double[] values = partitioner.getValues(0);
		double best = StatUtils.min(values);
		NavigableSet<Feature> group = matches.get(best);
		if (group.size() == 1) return group.first();

		TreeSet<Feature> ts = new TreeSet<>(weightComp);
		ts.addAll(group);
		return ts.last();	// highest weighting
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
