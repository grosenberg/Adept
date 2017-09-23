package net.certiv.adept.model.topo;

import java.util.Set;

import net.certiv.adept.model.Feature;

public class Analyzer {

	private int total;

	private int atLineBeg;
	// private int atLineEnd;
	private int blankAbove;
	private int blankBelow;

	public Analyzer() {
		super();
	}

	// analyze based on type to adjust format
	public void accum(Feature tf) {
		Set<Facet> facets = Facet.get(tf.getFormat());
		if (facets.contains(Facet.AT_LINE_BEG)) atLineBeg++;
		// if (facets.contains(Facet.AT_LINE_END)) atLineEnd++;
		if (facets.contains(Facet.BLANK_ABOVE)) blankAbove++;
		if (facets.contains(Facet.BLANK_BELOW)) blankBelow++;
		total++;
	}

	public void prepare() {
		atLineBeg = atLineBeg * 100 / total;
		// atLineEnd = atLineEnd * 100 / total;
		blankAbove = blankAbove * 100 / total;
		blankBelow = blankBelow * 100 / total;
	}

	public void apply(Feature tf) {
		int format = tf.getFormat();

		if (atLineBeg > 98) {
			format |= Facet.JOIN_ALWAYS.value();
		} else if (atLineBeg > 60) {
			format |= Facet.JOIN_SHOULD.value();
		} else if (atLineBeg < 2) {
			format |= Facet.JOIN_NEVER.value();
		} else {
			format |= Facet.JOIN_ALLOW.value();
		}

		if (blankAbove > 98) {
			format |= Facet.BLANK_ABOVE.value();
		} else {
			format &= ~Facet.BLANK_ABOVE.value();
		}

		if (blankBelow > 98) {
			format |= Facet.BLANK_BELOW.value();
		} else {
			format &= ~Facet.BLANK_BELOW.value();
		}

		tf.setFormat(format);
	}
}
