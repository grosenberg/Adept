package net.certiv.adept.util;

import org.apache.commons.math3.stat.StatUtils;

public class Norm {

	/** Returns the normalized sum of the given values. */
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
}
