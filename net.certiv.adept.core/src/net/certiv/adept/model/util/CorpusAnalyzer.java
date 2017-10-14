package net.certiv.adept.model.util;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Format;

public class CorpusAnalyzer {

	private int total;
	private int atLineBeg;
	private int atLineEnd;
	private int blankAbove;
	private int blankBelow;

	public CorpusAnalyzer() {
		super();
	}

	// analyze based on type to set document wide format aspects
	public void accum(Feature feature) {
		Format format = feature.getFormat();
		if (format.atLineBeg) atLineBeg++;
		if (format.atLineEnd) atLineEnd++;
		if (format.blankAbove) blankAbove++;
		if (format.blankBelow) blankBelow++;
		total++;
	}

	public void prepare() {
		atLineBeg = atLineBeg * 100 / total;
		atLineEnd = atLineEnd * 100 / total;
		blankAbove = blankAbove * 100 / total;
		blankBelow = blankBelow * 100 / total;
	}

	public void apply(Feature feature) {
		Format format = feature.getFormat();

		if (atLineBeg > 98) {
			format.joinAlways = true;
		} else if (atLineBeg > 60) {
			format.joinShould = true;
		} else if (atLineBeg < 2) {
			format.joinNever = true;
		} else {
			format.joinAllow = true;
		}

		//		if (blankAbove > 98) {
		//			format |= Facet.BLANK_ABOVE.value();
		//		} else {
		//			format &= ~Facet.BLANK_ABOVE.value();
		//		}
		//
		//		if (blankBelow > 98) {
		//			format |= Facet.BLANK_BELOW.value();
		//		} else {
		//			format &= ~Facet.BLANK_BELOW.value();
		//		}

		feature.setFormat(format);
	}
}
