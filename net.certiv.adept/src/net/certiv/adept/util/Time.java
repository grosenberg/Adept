package net.certiv.adept.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.certiv.adept.unit.Pair;

public class Time {

	// ------------------------------------------------
	// For measuring process times

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
		double time = elapsed(id);
		return String.format(format, time);
	}

	public static <E extends Enum<E>> void clear(E id) {
		times.remove(id);
	}

	public static void clearAll() {
		times.clear();
	}

	// ------------------------------------------------
	// For file modification checking

	public static long now() {
		return Date.from(Instant.now()).getTime();
	}

	public static long getLastModified(Path path) {
		try {
			FileTime time = Files.getLastModifiedTime(path);
			return Date.from(time.toInstant()).getTime();
		} catch (IOException e) {
			return 0;
		}
	}

}
