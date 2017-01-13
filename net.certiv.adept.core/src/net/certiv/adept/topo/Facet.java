package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;

public enum Facet {
	LINE_BEG(0),
	LINE_END(1),
	WS_BEFORE(2),
	WS_AFTER(3),
	BLANK_ABOVE(4),
	BLANK_BELOW(5),

	INDENT(10),					// is this line indented? Otherwise starts at col zero.
	INDENT1_PRIOR(11, 1),		// indented relative to the prior (real) line?
	INDENT2_PRIOR(12, 2),		// double indented?
	INDENT3_PRIOR(13, 3),		// triple indented?
	INDENT4_PRIOR(14, 4),		// quad indented?
	OUTDENT1_PRIOR(15, -1),		// outdented relative to the prior (real) line?
	OUTDENT2_PRIOR(16, -2),		// double outdented?
	OUTDENT3_PRIOR(17, -3),		// triple outdented?
	OUTDENT4_PRIOR(18, -4),		// quad outdented?

	ALIGNED(20);				// aligned to a similar feature in the prior (real) line?

	public final int value;
	private final int relative;

	Facet(int shift) {
		value = 1 << shift;
		relative = 0;
	}

	Facet(int shift, int num) {
		value = 1 << shift;
		relative = num;
	}

	/** Returns the list of facets that compose the given format */
	public static List<Facet> get(int format) {
		List<Facet> facets = new ArrayList<>();
		for (Facet facet : Facet.values()) {
			if (facet.isSet(format)) facets.add(facet);
		}
		return facets;
	}

	private boolean isSet(int format) {
		if ((value & format) > 0) return true;
		return false;
	}

	public static int relIndent(List<Facet> facets) {
		for (Facet facet : facets) {
			switch (facet) {
				case INDENT1_PRIOR:
				case INDENT2_PRIOR:
				case INDENT3_PRIOR:
				case INDENT4_PRIOR:
				case OUTDENT1_PRIOR:
				case OUTDENT2_PRIOR:
				case OUTDENT3_PRIOR:
				case OUTDENT4_PRIOR:
					return facet.relative;
				default:
			}
		}
		return 0;
	}
}
