/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.certiv.adept.unit.Pair;

/** For keeping running measurements of 'time'. */
public class Time {

	private static final Map<Enum<?>, Pair<Instant, Instant>> times = new HashMap<>();

	/** Start measuring time for the given id. */
	public static <E extends Enum<E>> void start(E id) {
		times.put(id, new Pair<>(Instant.now(), Instant.EPOCH));
	}

	/** Stop measuring time for the given id. */
	public static <E extends Enum<E>> void stop(E id) {
		Pair<Instant, Instant> time = times.get(id);
		if (time != null) time.b = Instant.now();
	}

	/** Returns the elapsed time for the given id in milliseconds with a decimal precision of 2. */
	public static <E extends Enum<E>> double elapsed(E id) {
		double millis = 0;
		Pair<Instant, Instant> time = times.get(id);
		if (time != null) {
			if (time.b == Instant.EPOCH) {
				millis = Duration.between(time.a, Instant.now()).toMillis();
			} else {
				millis = Duration.between(time.a, time.b).toMillis();
			}
		}
		return Maths.round(millis, 2);
	}

	public static <E extends Enum<E>> String elapsed(E id, String format) {
		return String.format(format, elapsed(id));
	}

	public static <E extends Enum<E>> void clear(E id) {
		times.remove(id);
	}

	public static void clear() {
		times.clear();
	}

	// ------------------------------

	public static boolean wait(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
