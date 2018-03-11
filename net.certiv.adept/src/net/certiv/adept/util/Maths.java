package net.certiv.adept.util;

import java.util.Random;

public class Maths {

	/** The natural logarithm of 2. */
	public static double log2 = Math.log(2);

	/** The small deviation allowed in double comparisons */
	public static double SMALL = 1e-6;

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
		double maxVal = max(vals);
		double minVal = min(vals);
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
	public static int invDelta(int a, int b) {
		return 1 / (1 + Math.abs(a - b));
	}

	public static double invDelta(double a, double b) {
		return 1 / (1 + Math.abs(a - b));
	}

	/**
	 * Returns the inverse value distance of the given values normalized to the given range limit (max =
	 * 1.0).
	 */
	public static double dist(double limit, double a, double b) {
		double dist = Math.min(limit, Math.abs(a - b));
		return (limit - dist) / limit;
	}

	/** Returns the delta as an int */
	public static int idist(double limit, double a, double b) {
		return (int) dist(limit, a, b);
	}

	/**
	 * Returns the correlation coefficient of two double vectors.
	 *
	 * @param y1 double vector 1
	 * @param y2 double vector 2
	 * @param n the length of two double vectors
	 * @return the correlation coefficient
	 */
	public static final double correlation(double y1[], double y2[], int n) {
		int i;
		double av1 = 0.0, av2 = 0.0, y11 = 0.0, y22 = 0.0, y12 = 0.0, c;

		if (n <= 1) return 1.0;
		for (i = 0; i < n; i++) {
			av1 += y1[i];
			av2 += y2[i];
		}
		av1 /= n;
		av2 /= n;
		for (i = 0; i < n; i++) {
			y11 += (y1[i] - av1) * (y1[i] - av1);
			y22 += (y2[i] - av2) * (y2[i] - av2);
			y12 += (y1[i] - av1) * (y2[i] - av2);
		}
		if (y11 * y22 == 0.0) {
			c = 1.0;
		} else {
			c = y12 / Math.sqrt(Math.abs(y11 * y22));
		}
		return c;
	}

	/**
	 * Computes entropy for an array of integers.
	 *
	 * @param counts array of counts
	 * @return - a log2 a - b log2 b - c log2 c + (a+b+c) log2 (a+b+c) when given array [a b c]
	 */
	public static double info(int counts[]) {
		int total = 0;
		double x = 0;
		for (int j = 0; j < counts.length; j++) {
			x -= xlogx(counts[j]);
			total += counts[j];
		}
		return x + xlogx(total);
	}

	/**
	 * Returns the kth-smallest value in the array.
	 *
	 * @param array the array of integers
	 * @param k the value of k
	 * @return the kth-smallest value
	 */
	public static double kthSmallestValue(int[] array, int k) {
		int[] index = new int[array.length];
		for (int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		return array[index[select(array, index, 0, array.length - 1, k)]];
	}

	/**
	 * Returns the kth-smallest value in the array
	 *
	 * @param array the array of double
	 * @param k the value of k
	 * @return the kth-smallest value
	 */
	public static double kthSmallestValue(double[] array, int k) {
		int[] index = new int[array.length];
		for (int i = 0; i < index.length; i++) {
			index[i] = i;
		}
		return array[index[select(array, index, 0, array.length - 1, k)]];
	}

	/**
	 * Returns the logarithm of a for base 2.
	 *
	 * @param a a double
	 * @return the logarithm for base 2
	 */
	public static double log2(double a) {
		return Math.log(a) / log2;
	}

	/**
	 * Returns index of maximum element in a given array of doubles. First maximum is returned.
	 *
	 * @param doubles the array of doubles
	 * @return the index of the maximum element
	 */
	public static int maxIndex(double[] doubles) {
		double maximum = 0;
		int maxIndex = 0;
		for (int i = 0; i < doubles.length; i++) {
			if ((i == 0) || (doubles[i] > maximum)) {
				maxIndex = i;
				maximum = doubles[i];
			}
		}
		return maxIndex;
	}

	/**
	 * Returns index of maximum element in a given array of integers. First maximum is returned.
	 *
	 * @param ints the array of integers
	 * @return the index of the maximum element
	 */
	public static int maxIndex(int[] ints) {
		int maximum = 0;
		int maxIndex = 0;
		for (int i = 0; i < ints.length; i++) {
			if ((i == 0) || (ints[i] > maximum)) {
				maxIndex = i;
				maximum = ints[i];
			}
		}
		return maxIndex;
	}

	/**
	 * Computes the max for an array of doubles.
	 *
	 * @param vector the array
	 * @return the maximum
	 */
	public static double max(double[] vals) {
		if (vals == null) throw new IllegalArgumentException("Argument cannot be null");

		double max = Double.MIN_VALUE;
		for (int idx = 0; idx < vals.length; idx++) {
			if (!Double.isNaN(vals[idx])) {
				max = (max > vals[idx]) ? max : vals[idx];
			}
		}
		return max;
	}

	/**
	 * Computes the minimum for an array of doubles.
	 *
	 * @param vector the array
	 * @return the minimum value
	 */
	public static double min(double[] vals) {
		if (vals == null) throw new IllegalArgumentException("Argument cannot be null");

		double min = Double.MAX_VALUE;
		for (int idx = 0; idx < vals.length; idx++) {
			if (!Double.isNaN(vals[idx])) {
				min = (min > vals[idx]) ? vals[idx] : min;
			}
		}
		return min;
	}

	/**
	 * Computes the mean for an array of doubles.
	 *
	 * @param vector the array
	 * @return the mean
	 */
	public static double mean(double[] vector) {
		double sum = 0;
		if (vector.length == 0) {
			return 0;
		}
		for (int i = 0; i < vector.length; i++) {
			sum += vector[i];
		}
		return sum / vector.length;
	}

	/**
	 * Returns index of minimum element in a given array of integers. First minimum is returned.
	 *
	 * @param ints the array of integers
	 * @return the index of the minimum element
	 */
	public static int minIndex(int[] ints) {
		int minimum = 0;
		int minIndex = 0;
		for (int i = 0; i < ints.length; i++) {
			if ((i == 0) || (ints[i] < minimum)) {
				minIndex = i;
				minimum = ints[i];
			}
		}
		return minIndex;
	}

	/**
	 * Returns index of minimum element in a given array of doubles. First minimum is returned.
	 *
	 * @param doubles the array of doubles
	 * @return the index of the minimum element
	 */
	public static int minIndex(double[] doubles) {
		double minimum = 0;
		int minIndex = 0;

		for (int i = 0; i < doubles.length; i++) {
			if ((i == 0) || (doubles[i] < minimum)) {
				minIndex = i;
				minimum = doubles[i];
			}
		}
		return minIndex;
	}

	/**
	 * Normalizes the doubles in the array by their sum.
	 *
	 * @param doubles the array of double
	 * @exception IllegalArgumentException if sum is Zero or NaN
	 */
	public static void normalize(double[] doubles) {
		double sum = 0;
		for (int i = 0; i < doubles.length; i++) {
			sum += doubles[i];
		}
		normalize(doubles, sum);
	}

	/**
	 * Normalizes the doubles in the array using the given value.
	 *
	 * @param doubles the array of double
	 * @param sum the value by which the doubles are to be normalized
	 * @exception IllegalArgumentException if sum is zero or NaN
	 */
	public static void normalize(double[] doubles, double sum) {
		if (Double.isNaN(sum)) {
			throw new IllegalArgumentException("Can't normalize array. Sum is NaN.");
		}
		if (sum == 0) {
			// Maybe this should just be a return.
			throw new IllegalArgumentException("Can't normalize array. Sum is zero.");
		}
		for (int i = 0; i < doubles.length; i++) {
			doubles[i] /= sum;
		}
	}

	/**
	 * Converts an array containing the natural logarithms of probabilities stored in a vector back into
	 * probabilities. The probabilities are assumed to sum to one.
	 *
	 * @param a an array holding the natural logarithms of the probabilities
	 * @return the converted array
	 */
	public static double[] logs2probs(double[] a) {
		double max = a[maxIndex(a)];
		double sum = 0.0;

		double[] result = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = Math.exp(a[i] - max);
			sum += result[i];
		}
		normalize(result, sum);
		return result;
	}

	/**
	 * Returns the log-odds for a given probabilitiy.
	 *
	 * @param prob the probabilitiy
	 * @return the log-odds after the probability has been mapped to [Utils.SMALL, 1-Utils.SMALL]
	 */
	public static double probToLogOdds(double prob) {
		if (gr(prob, 1) || (sm(prob, 0))) {
			throw new IllegalArgumentException("probToLogOdds: probability must " + "be in [0,1] " + prob);
		}
		double p = SMALL + (1.0 - 2 * SMALL) * prob;
		return Math.log(p / (1 - p));
	}

	/**
	 * Tests if a is smaller or equal to b.
	 *
	 * @param a a double
	 * @param b a double
	 */
	public static boolean smOrEq(double a, double b) {
		return (a - b < SMALL);
	}

	/**
	 * Tests if a is greater or equal to b.
	 *
	 * @param a a double
	 * @param b a double
	 */
	public static boolean grOrEq(double a, double b) {
		return (b - a < SMALL);
	}

	/**
	 * Tests if a is smaller than b.
	 *
	 * @param a a double
	 * @param b a double
	 */
	public static boolean sm(double a, double b) {
		return (b - a > SMALL);
	}

	/**
	 * Tests if a is greater than b.
	 *
	 * @param a a double
	 * @param b a double
	 */
	public static boolean gr(double a, double b) {
		return (a - b > SMALL);
	}

	/** Returns the average of the given values, rounded to an int. */
	public static int ave(double... vals) {
		return round(sum(vals) / vals.length);
	}

	/**
	 * Rounds a double to the next nearest integer value. The JDK version of it doesn't work properly.
	 *
	 * @param value the double value
	 * @return the resulting integer value
	 */
	public static int round(double value) {
		int roundedValue = value > 0 ? (int) (value + 0.5) : -(int) (Math.abs(value) + 0.5);
		return roundedValue;
	}

	/**
	 * Rounds a double to the next nearest integer value in a probabilistic fashion (e.g. 0.8 has a 20%
	 * chance of being rounded down to 0 and a 80% chance of being rounded up to 1). In the limit, the
	 * average of the rounded numbers generated by this procedure should converge to the original
	 * double.
	 *
	 * @param value the double value
	 * @param rand the random number generator
	 * @return the resulting integer value
	 */
	public static int probRound(double value, Random rand) {
		if (value >= 0) {
			double lower = Math.floor(value);
			double prob = value - lower;
			if (rand.nextDouble() < prob) {
				return (int) lower + 1;
			} else {
				return (int) lower;
			}
		} else {
			double lower = Math.floor(Math.abs(value));
			double prob = Math.abs(value) - lower;
			if (rand.nextDouble() < prob) {
				return -((int) lower + 1);
			} else {
				return -(int) lower;
			}
		}
	}

	/**
	 * Rounds a double to the given number of decimal places.
	 *
	 * @param value the double value
	 * @param afterDecimalPoint the number of digits after the decimal point
	 * @return the double rounded to the given precision
	 */
	public static double roundDouble(double value, int afterDecimalPoint) {
		double mask = Math.pow(10.0, afterDecimalPoint);
		return (Math.round(value * mask)) / mask;
	}

	/**
	 * Computes the variance for an array of doubles.
	 *
	 * @param vector the array
	 * @return the variance
	 */
	public static double variance(double[] vector) {
		double sum = 0, sumSquared = 0;
		if (vector.length <= 1) return 0;
		for (int i = 0; i < vector.length; i++) {
			sum += vector[i];
			sumSquared += (vector[i] * vector[i]);
		}
		double result = (sumSquared - (sum * sum / vector.length)) / (vector.length - 1);

		// We don't like negative variance
		if (result < 0) {
			return 0;
		} else {
			return result;
		}
	}

	/**
	 * Computes the sum of the elements of an array of integers.
	 *
	 * @param ints the array of integers
	 * @return the sum of the elements
	 */
	public static int sum(int[] ints) {
		int sum = 0;
		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}
		return sum;
	}

	/**
	 * Returns c*log2(c) for a given integer value c.
	 *
	 * @param c an integer value
	 * @return c*log2(c) (but is careful to return 0 if c is 0)
	 */
	public static double xlogx(int c) {
		if (c == 0) return 0.0;
		return c * log2(c);
	}

	/**
	 * Partitions the instances around a pivot. Used by quicksort and kthSmallestValue.
	 *
	 * @param array the array of doubles to be sorted
	 * @param index the index into the array of doubles
	 * @param l the first index of the subset
	 * @param r the last index of the subset
	 * @return the index of the middle element
	 */
	private static int partition(double[] array, int[] index, int l, int r) {
		double pivot = array[index[(l + r) / 2]];
		int help;

		while (l < r) {
			while ((array[index[l]] < pivot) && (l < r)) {
				l++;
			}
			while ((array[index[r]] > pivot) && (l < r)) {
				r--;
			}
			if (l < r) {
				help = index[l];
				index[l] = index[r];
				index[r] = help;
				l++;
				r--;
			}
		}
		if ((l == r) && (array[index[r]] > pivot)) {
			r--;
		}
		return r;
	}

	/**
	 * Partitions the instances around a pivot. Used by quicksort and kthSmallestValue.
	 *
	 * @param array the array of integers to be sorted
	 * @param index the index into the array of integers
	 * @param l the first index of the subset
	 * @param r the last index of the subset
	 * @return the index of the middle element
	 */
	private static int partition(int[] array, int[] index, int l, int r) {
		double pivot = array[index[(l + r) / 2]];
		int help;

		while (l < r) {
			while ((array[index[l]] < pivot) && (l < r)) {
				l++;
			}
			while ((array[index[r]] > pivot) && (l < r)) {
				r--;
			}
			if (l < r) {
				help = index[l];
				index[l] = index[r];
				index[r] = help;
				l++;
				r--;
			}
		}
		if ((l == r) && (array[index[r]] > pivot)) {
			r--;
		}
		return r;
	}

	/**
	 * Implements computation of the kth-smallest element according to Manber's "Introduction to
	 * Algorithms".
	 *
	 * @param array the array of double
	 * @param index the index into the array of doubles
	 * @param left the first index of the subset
	 * @param right the last index of the subset
	 * @param k the value of k
	 * @return the index of the kth-smallest element
	 */
	// @ requires 0 <= first && first <= right && right < array.length;
	private static int select(double[] array, int[] index, int left, int right, int k) {
		if (left == right) {
			return left;
		} else {
			int middle = partition(array, index, left, right);
			if ((middle - left + 1) >= k) {
				return select(array, index, left, middle, k);
			} else {
				return select(array, index, middle + 1, right, k - (middle - left + 1));
			}
		}
	}

	/**
	 * Implements computation of the kth-smallest element according to Manber's "Introduction to
	 * Algorithms".
	 *
	 * @param array the array of integers
	 * @param index the index into the array of integers
	 * @param left the first index of the subset
	 * @param right the last index of the subset
	 * @param k the value of k
	 * @return the index of the kth-smallest element
	 */
	// @ requires 0 <= first && first <= right && right < array.length;
	private static int select(int[] array, int[] index, int left, int right, int k) {
		if (left == right) {
			return left;
		} else {
			int middle = partition(array, index, left, right);
			if ((middle - left + 1) >= k) {
				return select(array, index, left, middle, k);
			} else {
				return select(array, index, middle + 1, right, k - (middle - left + 1));
			}
		}
	}
}
