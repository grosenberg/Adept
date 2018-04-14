package net.certiv.adept.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Damerau implements the unrestricted edit distance group of Damerau-Levenshtein. The
 * computed distance represents the number of edits neccessary to make the input integer sequences
 * equal. Each edit is counted as an insertion, deletion, substitution of a single element, or a
 * transposition of adjacent elements. The greater the edit distance, the lower the similarity.
 */
public class Damerau {

	public static double simularity(double dist, int srcLen, int tgtLen) {
		int mLen = Math.max(srcLen, tgtLen);
		double d_norm = mLen > 0 ? dist / mLen : 0;
		return 1 - d_norm;
	}

	public static double distance(List<Integer> source, List<Integer> target) {
		int[] src = source.stream().mapToInt(i -> i).toArray();
		int[] tgt = target.stream().mapToInt(i -> i).toArray();
		return distance(src, tgt);
	}

	public static double distance(int[] src, int[] tgt) {
		if (src == null) throw new NullPointerException("src must not be null");
		if (tgt == null) throw new NullPointerException("tgt must not be null");
		if (Arrays.equals(src, tgt)) return 0;

		// INFinite distance is the max possible distance
		int inf = src.length + tgt.length;

		// initialize the array indices
		Map<Integer, Integer> da = new HashMap<>();
		for (int d = 0; d < src.length; d++) {
			da.put(src[d], 0);
		}
		for (int d = 0; d < tgt.length; d++) {
			da.put(tgt[d], 0);
		}

		// Create the distance matrix H[0 .. s1.length+1][0 .. s2.length+1]
		int[][] h = new int[src.length + 2][tgt.length + 2];

		// initialize the left and top edges of H
		for (int i = 0; i <= src.length; i++) {
			h[i + 1][0] = inf;
			h[i + 1][1] = i;
		}

		for (int j = 0; j <= tgt.length; j++) {
			h[0][j + 1] = inf;
			h[1][j + 1] = j;
		}

		// fill in the distance matrix H
		for (int i = 1; i <= src.length; i++) {
			int db = 0;
			for (int j = 1; j <= tgt.length; j++) {
				int i1 = da.get(tgt[j - 1]);
				int j1 = db;
				int cost = 1;
				if (src[i - 1] == tgt[j - 1]) {
					cost = 0;
					db = j;
				}

				h[i + 1][j + 1] = min3(h[i][j] + cost,		// substitution
						h[i + 1][j] + 1,					// insertion
						h[i][j + 1] + 1, 					// deletion
						h[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1));
			}
			da.put(src[i - 1], i);
		}

		return h[src.length + 1][tgt.length + 1];
	}

	private static int min3(final int a, final int b, final int c, final int d) {
		return Math.min(a, Math.min(b, Math.min(c, d)));
	}
}
