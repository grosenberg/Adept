package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.primitives.Longs;

public class EdgeSeq {

	/**
	 * Return the similarity of two sequences of elements. Similarity reflects the size of the
	 * optimally ordered longest common subsequence present in both sequences. Normalized to the
	 * range [0,1].
	 * <p>
	 * TODO: use a faster algorithm, such as Hirschberg or Myer; size is not really the issue, since
	 * all sequences are expected to be quite short (<10).
	 */
	public static int similarity(List<Long> s1, List<Long> s2) {
		int max = Math.max(s1.size(), s2.size());
		if (max == 0) return 1;
		int sim = max - editDistance(s1, s2);
		return sim;
	}

	public static int editDistance(List<Long> s1, List<Long> s2) {
		return s1.size() + s2.size() - (2 * findLCS(s1, s2).size());
	}

	/**
	 * Finds a list of longest common subsequences (lcs) of given two texts.
	 */
	public static List<Long> findLCS(List<Long> seq1, List<Long> seq2) {
		long[] s1 = Longs.toArray(seq1);
		long[] s2 = Longs.toArray(seq2);

		int cnt1 = s1.length;
		int cnt2 = s2.length;

		int[][] matrix = new int[cnt1 + 1][cnt2 + 1];

		for (int idx = cnt1 - 1; idx >= 0; idx--) {
			for (int jdx = cnt2 - 1; jdx >= 0; jdx--) {
				if (s1[idx] == s2[jdx]) {
					matrix[idx][jdx] = matrix[idx + 1][jdx + 1] + 1;
				} else {
					matrix[idx][jdx] = Math.max(matrix[idx + 1][jdx], matrix[idx][jdx + 1]);
				}
			}
		}

		List<Long> result = new ArrayList<>();
		int idx = 0;
		int jdx = 0;
		while (idx < cnt1 && jdx < cnt2) {
			if (s1[idx] == s2[jdx]) {
				result.add(s2[jdx]);
				idx++;
				jdx++;
			} else if (matrix[idx + 1][jdx] >= matrix[idx][jdx + 1]) {
				idx++;
			} else {
				jdx++;
			}
		}
		return result;
	}
}
