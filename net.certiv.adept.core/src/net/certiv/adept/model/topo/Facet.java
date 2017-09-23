package net.certiv.adept.model.topo;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.HashBasedTable;

public enum Facet {

	// intrinsic facets - considered for similarity

	// INDENTS(0-5)
	// - bits 0-5 encode number of indents
	// - value is symmetric around 1<<5 : 32 - value = # of indents

	INDENTED(6),		// a line is indented if it does not start within col zero + one tab

	AT_LINE_BEG(7),		// occurs at line begin, ignoring hws and comments
	AT_LINE_END(8),		// occurs at line end, ignoring hws and comments
	WS_BEFORE(9),		// has leading hws, ignoring any in-line block comment
	WS_AFTER(10),		// has trailing hws, ignoring any in-line block comment
	WIDE_BEFORE(11),	// non-minimal leading hws, ignoring any in-line block comment
	WIDE_AFTER(12),		// non-minimal trailing hws, ignoring any in-line block comment

	ALIGNED(13),		// aligned to a node feature in a surrounding real line
	ALIGNED_SAME(14),	// to same feature type (adds to alignment weight)

	// extrinsic facets - determinable from the corpus as a whole

	BLANK_ABOVE(15),	// if always present
	BLANK_BELOW(16),

	JOIN_ALWAYS(17),	// always follows some other facet in-line
	JOIN_SHOULD(18),	// majority follows
	JOIN_ALLOW(19),		// can follow
	JOIN_NEVER(20),		// always occurs at line begin

	// nil facets

	NO_FORMAT(21),
	UNALIGNED(22),

	;

	// -, - | 0, 0 | +, + : 1.0
	// +, 0 : 0.7
	// -, 0 : 0.5
	// -, + : 0.2
	// #TODO: control by parameters, not constants
	private static final double[][] SignTable = new double[][] { //
			{ 1.0, 0.7, 0.2 }, //
			{ 0.7, 1.0, 0.5 }, //
			{ 0.2, 0.5, 1.0 } //
	};

	// #TODO: control by parameters, not constants
	private static final HashBasedTable<Facet, Facet, Double> ScoreTable = HashBasedTable.create();
	static {
		// source facet, corpus facet, value
		ScoreTable.put(INDENTED, INDENTED, 1.0);
		ScoreTable.put(AT_LINE_BEG, AT_LINE_BEG, 1.0);
		ScoreTable.put(AT_LINE_END, AT_LINE_END, 1.0);
		ScoreTable.put(WS_BEFORE, WS_BEFORE, 1.0);
		ScoreTable.put(WS_AFTER, WS_AFTER, 1.0);
		ScoreTable.put(WIDE_BEFORE, WIDE_BEFORE, 1.0);
		ScoreTable.put(WIDE_AFTER, WIDE_AFTER, 1.0);
		ScoreTable.put(ALIGNED, ALIGNED, 1.0);
		ScoreTable.put(ALIGNED_SAME, ALIGNED_SAME, 1.0);

		ScoreTable.put(AT_LINE_BEG, INDENTED, 0.7);
		ScoreTable.put(WS_BEFORE, WIDE_BEFORE, 0.3);
		ScoreTable.put(WS_AFTER, WIDE_AFTER, 0.5);
		ScoreTable.put(ALIGNED, ALIGNED_SAME, 0.5);
		ScoreTable.put(ALIGNED, UNALIGNED, 0.3);
		ScoreTable.put(UNALIGNED, ALIGNED, 0.5);
		ScoreTable.put(UNALIGNED, ALIGNED_SAME, 0.2);
	}

	static final double DNTS = 6;		// num significant dent bits
	static final int FMASK = 0x3FC0;	// bits 6-14
	static final int DMASK = 0x3F; 		// bits 0-5 (64bit values)
	static final int ZERO = 32;			// defines zero

	final int value;

	Facet(int shift) {
		value = 1 << shift;
	}

	/**
	 * Returns a value reflecting the similarity of the two given formats. Similarity calculated
	 * using a scoring matrix that defines weighted relationships between facet pairs.
	 */
	public static double similarity(int src, int cps) {
		Set<Facet> a = get(src);
		Set<Facet> b = get(cps);
		Set<Facet> c = get(src | cps);

		double basis = score(c, c);
		return score(a, b) / basis;
	}

	private static double score(Set<Facet> a, Set<Facet> b) {
		double s = 0;
		for (Facet fa : a) {
			for (Facet fb : b) {
				Double result = ScoreTable.get(fa, fb);
				s += result != null ? result : 0;
			}
		}
		return s;
	}

	/**
	 * Returns the similarity of indetation levels. Based on normalized inverse difference of
	 * dentation and relative sign.
	 */
	public static double dentSimilarity(int src, int cps) {
		boolean si = INDENTED.isSet(src);
		boolean ci = INDENTED.isSet(cps);
		if (si && ci) {
			return sim(DNTS, getDentation(src), getDentation(cps));
		} else if (!si && !ci) {
			return 0.7;
		} else if (!si && ci) {
			return 0.5;
		} else {
			return 0.3;
		}
	}

	private static double sim(double limit, double a, double b) {
		double factor = SignTable[sign(a)][sign(b)];
		double distance = factor * Math.min(limit, Math.abs(a - b));
		return (limit - distance) / limit;
	}

	private static int sign(double value) {
		if (value > 0) return 0;
		if (value == 0) return 1;
		return 2;
	}

	/** Returns the set of facets that compose the given format. */
	public static Set<Facet> get(int format) {
		Set<Facet> facets = new LinkedHashSet<>();
		if (format == 0) {
			facets.add(NO_FORMAT);
			facets.add(UNALIGNED);
		} else {
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
