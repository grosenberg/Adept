package net.certiv.adept.topo;

import java.util.LinkedHashSet;
import java.util.Set;

public enum Facet {

	// intrinsic facets

	// INDENTS(0-5)
	// - bits 0-5 encode +/- number of indents
	// value is symmetric around 1<<5 (dec 32)
	// 32 - value = # of indents

	INDENTED(6),		// a line is indented if it does not start at col zero + one tab

	AT_LINE_BEG(7),		// occurs at line begin, ignoring hws and comments
	AT_LINE_END(8),		// occurs at line end, ignoring hws and comments
	WS_BEFORE(9),		// has leading hws, ignoring any in-line block comment
	WS_AFTER(10),		// has trailing hws, ignoring any in-line block comment
	WIDE_BEFORE(11),	// non-minimal leading hws, ignoring any in-line block comment
	WIDE_AFTER(12),		// non-minimal trailing hws, ignoring any in-line block comment

	ALIGNED(13),		// aligned to a similar feature in a prior or following real line

	// extrinsic facets - determinable from the corpus as a whole

	BLANK_ABOVE(14),	// if always present
	BLANK_BELOW(15),

	JOIN_ALWAYS(16),	// always follows some other facet in-line
	JOIN_SHOULD(17),	// majority follows
	JOIN_ALLOW(18),		// can follow
	JOIN_NEVER(19),		// always occurs at line begin

	;

	private static final int SIGF = 20 - 5;	// num significant bits
	private static final double NORM = 0.5 * SIGF * (SIGF - 1);

	static final int MASK = 0x3F; 	// 64 values
	static final int ZERO = 32;		// defines zero

	final int value;

	Facet(int shift) {
		value = 1 << shift;
	}

	/**
	 * Returns a value reflecting the similarity of the two given formats. Similarity calculated as
	 * the Kendall's rank correlation coefficient (or normalized tau distance) between the
	 * significant bits of the given formats; limited to positive values.
	 */
	public static double similarity(int f1, int f2) {
		int nc = Long.bitCount(f1 & f2 & ~MASK);	// concordant pairs
		int nd = SIGF - nc;							// discordant pairs
		double tau = (nc - nd) / NORM;
		return tau > 0 ? tau : 0;
	}

	/** Returns the set of facets that compose the given format. */
	public static Set<Facet> get(int format) {
		Set<Facet> facets = new LinkedHashSet<>();
		if (format > 0) {
			for (Facet facet : Facet.values()) {
				if ((facet.value & format) > 0) facets.add(facet);
			}
		}
		return facets;
	}

	public static int getDentation(int format) {
		if (!INDENTED.isSet(format)) return 0;
		int dents = (format & MASK);
		return dents - ZERO;
	}

	public boolean isSet(int format) {
		return (value & format) > 0;
	}
}
