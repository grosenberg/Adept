/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.util.HashMap;
import java.util.Map;

import net.certiv.adept.store.HashMultilist;

/** For keeping running counts of 'things'. */
public class Calc {

	private static final HashMultilist<Enum<?>, Double> deltas = new HashMultilist<>();
	private static final Map<Enum<?>, Double> totals = new HashMap<>();

	/** Add the given delta to the value of the given id. */
	public static <E extends Enum<E>> void delta(E id, double delta) {
		deltas.put(id, delta);
		add(id, delta);
	}

	/** Returns the maximum absolute valued delta for the given id. */
	public static <E extends Enum<E>> double max(E id) {
		if (!deltas.containsKey(id)) return 0;

		double max = 0;
		for (double delta : deltas.get(id)) {
			max = Math.max(max, Math.abs(delta));
		}
		return max;
	}

	/** Returns the mean value for the deltas for the given id. */
	public static <E extends Enum<E>> double mean(E id) {
		if (!deltas.containsKey(id)) return 0;

		return Maths.mean(Utils.toPrimitiveArray(deltas.get(id)));
	}

	/** Returns the median value for the deltas for the given id. */
	public static <E extends Enum<E>> double median(E id) {
		if (!deltas.containsKey(id)) return 0;

		return Maths.median(Utils.toPrimitiveArray(deltas.get(id)));
	}

	/** Increment the total value of the given id. */
	public static <E extends Enum<E>> void inc(E id) {
		add(id, 1.0);
	}

	/** Decrement the total value of the given id. */
	public static <E extends Enum<E>> void dec(E id) {
		add(id, -1.0);
	}

	/** Add the given value to the total value of the given id. */
	public static <E extends Enum<E>> void add(E id, double value) {
		if (totals.containsKey(id)) {
			totals.put(id, totals.get(id) + value);
		} else {
			totals.put(id, value);
		}
	}

	/** Subtract the given value from the total value of the given id. */
	public static <E extends Enum<E>> void sub(E id, double value) {
		add(id, -1 * value);
	}

	/** Returns the total value for the given id. */
	public static <E extends Enum<E>> double total(E id) {
		return totals.get(id);
	}

	/** Returns the total value for the given id with the given precision. */
	public static <E extends Enum<E>> double total(E id, int precision) {
		return Maths.round(totals.get(id), precision);
	}

	/** Returns the total value for the given id using the given format. */
	public static <E extends Enum<E>> String total(E id, String format) {
		return String.format(format, totals.get(id));
	}

	/** Removes the delta and total value for the given id. */
	public static <E extends Enum<E>> void clear(E id) {
		deltas.removeKey(id);
		totals.remove(id);
	}

	/** Removes all deltas and totals. */
	public static void clear() {
		deltas.clear();
		totals.clear();
	}
}
