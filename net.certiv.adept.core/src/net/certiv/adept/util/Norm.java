package net.certiv.adept.util;

import org.apache.commons.math3.stat.StatUtils;

public class Norm {

	/** Returns the sum of the given values. */
	public static double sum(double[] vals) {
		double sum = 0;
		for (int idx = 0; idx < vals.length; idx++) {
			sum += vals[idx];
		}
		return sum;
	}

	/** Returns the normalized sum of the given values. */
	public static double nsum(double[] vals) {
		double maxVal = StatUtils.max(vals);
		double minVal = StatUtils.min(vals);
		if (maxVal == minVal) return vals.length;

		double sum = 0;
		for (int idx = 0; idx < vals.length; idx++) {
			sum += (vals[idx] - minVal) / (maxVal - minVal);
		}
		return sum;
	}

	/**
	 * Returns the inversed range normalized value delta of the given values.
	 * <p>
	 * Calculated as n = 1/(1+abs(a-b))
	 */
	public static double delta(double a, double b) {
		return 1 / (1 + Math.abs(a - b));
	}

	/**
	 * Returns the inverse value distance of the given values normalized to the given range limit.
	 */
	public static double dist(double limit, double a, double b) {
		double dist = Math.min(limit, Math.abs(a - b));
		return (limit - dist) / limit;
	}

	/** Returns the delta as an int */
	public static int idist(double limit, double a, double b) {
		return (int) dist(limit, a, b);
	}
}
