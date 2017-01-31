package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;

public enum Facet {

	// intrinsic facets

	AT_LINE_BEG(0),		// occurs at line begin, ignoring hws and comments
	AT_LINE_END(1),		// occurs at line end, ignoring hws and comments
	WS_BEFORE(2),		// has leading hws or in-line block comment
	WS_AFTER(3),		// has trailing hws or in-line block comment
	INDENTED(4),		// a line is indented if it does not start at col zero + one tab
	ALIGNED_ABOVE(5),	// aligned to a similar feature in the prior real line
	ALIGNED_BELOW(6),

	INDENT1(7, 1),		// levels of indenting dents to the prior real line
	INDENT2(8, 2),
	INDENT3(9, 3),
	INDENT4(10, 4),
	OUTDENT1(11, -1),	// levels of outdenting
	OUTDENT2(12, -2),
	OUTDENT3(13, -3),
	OUTDENT4(14, -4),

	// extrinsic facets - determinable from the corpus as a whole

	BLANK_ABOVE(15),	// if always present
	BLANK_BELOW(16),

	JOIN_ALWAYS(17),	// always follows some other facet in-line
	JOIN_SHOULD(18),	// majority follows
	JOIN_ALLOW(19),		// can follow
	JOIN_NEVER(20),		// always occurs at line begin

	;

	private static final int SIGF = 20;
	private static final double NORM = 0.5 * SIGF * (SIGF - 1);
	// private static final int MASK = 0xFFFF << SIGF + 1;

	public final int value;
	private final int dents;

	Facet(int shift) {
		value = 1 << shift;
		dents = 0;
	}

	Facet(int shift, int num) {
		value = 1 << shift;
		dents = num;
	}

	/**
	 * Returns a value reflecting the similarity of the two given formats. Similarity calculated as
	 * the Kendall's rank correlation coefficient (or normalized tau distance) between the
	 * significant bits of the given formats; limited to positive values.
	 */
	public static double similarity(int f1, int f2) {
		int nc = Long.bitCount(f1 & f2);	// concordant pairs
		int nd = SIGF - nc;					// discordant pairs
		double tau = (nc - nd) / NORM;
		return tau > 0 ? tau : 0;
	}

	/** Returns the list of facets that compose the given format. */
	public static List<Facet> get(int format) {
		List<Facet> facets = new ArrayList<>();
		if (format > 0) {
			for (Facet facet : Facet.values()) {
				if ((facet.value & format) > 0) facets.add(facet);
			}
		}
		return facets;
	}

	public static int getDentation(List<Facet> facets) {
		for (Facet facet : facets) {
			switch (facet) {
				case INDENT1:
				case INDENT2:
				case INDENT3:
				case INDENT4:
				case OUTDENT1:
				case OUTDENT2:
				case OUTDENT3:
				case OUTDENT4:
					return facet.dents;
				default:
			}
		}
		return 0;
	}
}
