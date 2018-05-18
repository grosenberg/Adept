/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.util.List;

/**
 * Calculates the optimal inLine similarity between two arrays of values using the
 * Smith-Waterman-Gotoh algorithm and a linear Gap penalty.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm">Wikipedia -
 *      Smith-Waterman algorithm</a>
 */
public final class Waterman {

	private static final double Match = 1.0f;
	private static final double Miss = -2.0f;
	private static final double Gap = -0.5f;

	private Waterman() {}

	/**
	 * Returns the effective degree of similary between the given source and target sequences of values.
	 * The degree of similary is reported in the range [0-1], with 1 indicating identity.
	 */
	public static double similarity(List<Long> s, List<Long> t) {
		long[] src = s.stream().mapToLong(i -> i).toArray();
		long[] tgt = t.stream().mapToLong(i -> i).toArray();
		return similarity(src, tgt);
	}

	public static double similarity(String s, String t) {
		return similarity(toLongArray(s), toLongArray(t));
	}

	public static double similarity(long[] s, long[] t) {
		if (s.length == 0 && t.length == 0) return 1.0;
		if (s.length == 0 || t.length == 0) return 0.0;

		double maxMatch = Math.min(s.length, t.length) * max(Match, Gap);
		return match(s, t) / maxMatch;
	}

	private static double match(long[] s, long[] t) {
		double[] m0 = new double[t.length]; // matrix scoring rows
		double[] m1 = new double[t.length];

		m0[0] = max(0, Gap, eval(s[0], t[0]));
		double sim = m0[0];
		for (int j = 1; j < m0.length; j++) {
			m0[j] = max(0, m0[j - 1] + Gap, eval(s[0], t[j]));
			sim = max(sim, m0[j]);
		}

		// determine scored inLine similarity
		for (int i = 1; i < s.length; i++) {
			m1[0] = max(0, m0[0] + Gap, eval(s[i], t[0]));
			sim = max(sim, m1[0]);
			for (int j = 1; j < m0.length; j++) {
				m1[j] = max(0, m0[j] + Gap, m1[j - 1] + Gap, m0[j - 1] + eval(s[i], t[j]));
				sim = max(sim, m1[j]);
			}
			for (int j = 0; j < m0.length; j++) {
				m0[j] = m1[j];
			}
		}
		return sim;
	}

	private static double eval(long a, long b) {
		return a == b ? Match : Miss;
	}

	private static long[] toLongArray(String s) {
		if (s == null) return new long[0];

		long[] r = new long[s.length()];
		for (int idx = 0; idx < s.length(); idx++) {
			r[idx] = s.charAt(idx);
		}
		return r;
	}

	private static double max(double a, double b) {
		return Math.max(a, b);
	}

	private static double max(double a, double b, double c) {
		return Math.max(Math.max(a, b), c);
	}

	private static double max(double a, double b, double c, double d) {
		return Math.max(Math.max(Math.max(a, b), c), d);
	}
}
