package net.certiv.adept.util;

import java.lang.reflect.Array;
import java.util.HashSet;

public class Collect {

	/** To initialize from an array {@code int[] { value1, value2, ...} } */
	public static HashSet<Integer> toSet(int[] in) {
		HashSet<Integer> set = new HashSet<>();
		for (int idx = 0; idx < in.length; idx++) {
			set.add(in[idx]);
		}
		return set;
	}

	/** To initialize from an array {@code int[][] { { key, value1, value2, ...}, ... } } */
	public static HashMultilist<Integer, Integer> toMap(int[][] in) {
		HashMultilist<Integer, Integer> map = new HashMultilist<>();
		for (int idx = 0; idx < in.length; idx++) {
			int key = in[idx][0];
			for (int jdx = 1; jdx < in[idx].length; jdx++) {
				map.put(key, in[idx][jdx]);
			}
		}
		return map;
	}

	/** Returns the concatenation of the two given arrays. */
	public static <T> T[] concatenate(final T[] a, final T[] b) {
		int aLen = a.length;
		int bLen = b.length;

		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
		System.arraycopy(a, 0, result, 0, aLen);
		System.arraycopy(b, 0, result, aLen, bLen);
		return result;
	}
}
