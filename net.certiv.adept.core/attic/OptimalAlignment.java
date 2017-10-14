package net.certiv.adept.model.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Ints;

/**
 * Optimal Alignment implements a restricted edit distance form of Damerau-Levenshtein.
 * 
 * The computed distance represents the number of edits neccessary to make the input integer
 * sequences equal, provided no subsequence is edited more than once. Each edit is counted as an
 * insertion, deletion, substitution of a single element, or a transposition of adjacent elements.
 * 
 * The greater the edit distance, the lower the similarity.
 */
public class OptimalAlignment {

	public static double toSimularity(double dist, int srcLen, int tgtLen) {
		double d_norm = dist / Math.max(srcLen, tgtLen);
		return 1 - d_norm;
	}

	public static double distance(List<Integer> source, List<Integer> target) {
		int[] src = Ints.toArray(source);
		int[] tgt = Ints.toArray(target);
		return distance(src, tgt);
	}

	public static double distance(int[] src, int[] tgt) {
		if (src == null) throw new NullPointerException("src must not be null");
		if (tgt == null) throw new NullPointerException("tgt must not be null");
		if (Arrays.equals(src, tgt)) return 0;

		int n = src.length;
		int m = tgt.length;
		if (n == 0) return m;
		if (m == 0) return n;

		// build matrix
		int[][] d = new int[n + 2][m + 2];

		// init matrix
		for (int i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// compute matrix
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {

				// compute 
				int cost = (src[i - 1] != tgt[j - 1]) ? 1 : 0;
				d[i][j] = min3(d[i - 1][j - 1] + cost,	// substitution
						d[i][j - 1] + 1,				// insertion
						d[i - 1][j] + 1 				// deletion
				);

				// transposition 
				if (i > 1 && j > 1 && src[i - 1] == tgt[j - 2] && src[i - 2] == tgt[j - 1]) {
					d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
				}
			}
		}
		return d[n][m];
	}

	private static int min3(final int a, final int b, final int c) {
		return Math.min(a, Math.min(b, c));
	}
}
