package net.certiv.adept.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;

public class Time {

	public static long getLastModified(Path path) {
		try {
			FileTime time = Files.getLastModifiedTime(path);
			return Date.from(time.toInstant()).getTime();
		} catch (IOException e) {
			return 0;
		}
	}

	public static long now() {
		return Date.from(Instant.now()).getTime();
	}
}
