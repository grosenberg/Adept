package net.certiv.adept.core;

import java.util.TreeMap;

import org.apache.commons.math3.stat.StatUtils;

import net.certiv.adept.model.Feature;

public class Confidence {

	private static TreeMap<Double, Feature> matches;
	private static Cluster1D cluster;

	public static void eval(Feature feature, TreeMap<Double, Feature> selected) {
		matches = selected;
		cluster = new Cluster1D(selected.keySet());
	}

	public static boolean inRange() {
		return cluster.numClusters() < 3;
	}

	public static Feature best() {
		double b = StatUtils.max(cluster.getValues());
		return matches.get(b);
	}
}
