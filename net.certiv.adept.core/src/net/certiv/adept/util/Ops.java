package net.certiv.adept.util;

import java.lang.reflect.Array;

public class Ops {

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
