package net.certiv.adept.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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
}
