package net.certiv.adept.core;

import java.util.TreeMap;

import org.apache.commons.math3.stat.StatUtils;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;

/**
 * TODO: implement conformal prediction to determine the confidence interval for the current
 * selection set.
 */
public class Confidence {

	private static Feature target;
	private static TreeMap<Double, Feature> matches;
	private static Cluster1D cluster;

	public static void eval(Feature feature, TreeMap<Double, Feature> selected) {
		target = feature;
		matches = selected;
		cluster = new Cluster1D(selected.keySet());
	}

	public static boolean inRange() {
		// Log.debug(Confidence.class, cluster.clusterDescriptions());
		int num = cluster.numClusters();
		if (num == 0) {
			Log.info(Confidence.class, String.format("No confidence in %s matches for %s", matches.size(), target));
		}
		return num > 0;
	}

	public static Feature best() {
		double[] values = cluster.getClusterValues(0);
		double best = StatUtils.min(values);
		Feature matched = matches.get(best);
		// Log.debug(Confidence.class, String.format("Best: %8.5f : %s", best, matched.toString()));
		return matched;
	}
}
