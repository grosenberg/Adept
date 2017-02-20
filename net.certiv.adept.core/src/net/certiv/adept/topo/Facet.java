package net.certiv.adept.topo;

import java.util.LinkedHashSet;
import java.util.Set;

public enum Facet {

	// intrinsic facets - considered for similarity

	// INDENTS(0-5)
	// - bits 0-5 encode number of indents
	// - value is symmetric around 1<<5 : 32 - value = # of indents

	INDENTED(6),		// a line is indented if it does not start at col zero + one tab

	AT_LINE_BEG(7),		// occurs at line begin, ignoring hws and comments
	AT_LINE_END(8),		// occurs at line end, ignoring hws and comments
	WS_BEFORE(9),		// has leading hws, ignoring any in-line block comment
	WS_AFTER(10),		// has trailing hws, ignoring any in-line block comment
	WIDE_BEFORE(11),	// non-minimal leading hws, ignoring any in-line block comment
	WIDE_AFTER(12),		// non-minimal trailing hws, ignoring any in-line block comment

	ALIGNED_ABOVE(13),	// aligned to a similar feature in a prior real line
	ALIGNED_BELOW(14),	// following

	// extrinsic facets - determinable from the corpus as a whole

	BLANK_ABOVE(15),	// if always present
	BLANK_BELOW(16),

	JOIN_ALWAYS(17),	// always follows some other facet in-line
	JOIN_SHOULD(18),	// majority follows
	JOIN_ALLOW(19),		// can follow
	JOIN_NEVER(20),		// always occurs at line begin

	;

	private static final double DNTS = 6;			// num significant dent bits
	private static final double SIGF = 14 - DNTS;	// num significant facet bits

	static final int FMASK = 0x3FC0;	// bits 6-14
	static final int DMASK = 0x3F; 		// bits 0-5 (64bit values)
	static final int ZERO = 32;			// defines zero

	final int value;

	Facet(int shift) {
		value = 1 << shift;
	}

	/**
	 * Returns a value reflecting the similarity of the two given formats. Base similarity
	 * calculated as the Kendall's rank correlation coefficient (or normalized tau distance) between
	 * the significant enum bits of the given formats; limited to positive values. A normalized
	 * difference of dentation is added if both of the given formats have INDENTED set.
	 */
	public static double similarity(int f1, int f2) {
		double nc = Long.bitCount(~(f1 ^ f2) & FMASK);	// concordant pairs
		double nd = SIGF - nc;							// discordant pairs
		double tau = (nc - nd) / SIGF;
		tau = tau > 0 ? tau : 0;

		if (INDENTED.isSet(f1) && INDENTED.isSet(f2)) {
			double dc = Long.bitCount(~f1 & ~f2 & DMASK);
			double dd = DNTS - dc;
			double delta = (dc - dd) / DNTS;
			tau += delta > 0 ? delta : 0;
		}
		return tau;
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
		int dents = (format & DMASK);
		return dents - ZERO;
	}

	public int value() {
		return value;
	}

	public boolean isSet(int format) {
		return (value & format) > 0;
	}
}
