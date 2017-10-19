package net.certiv.adept.model.util;

import java.util.List;

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

	public void process(List<Feature> features) {
		for (Feature feature : features) {
			accum(feature);
		}
		calc();
		for (Feature feature : features) {
			apply(feature);
		}
		clear();
	}

	// analyze based on type to set document wide format aspects
	// TODO: consider weights?
	private void accum(Feature feature) {
		Format format = feature.getFormat();
		if (format.atLineBeg) atLineBeg++;
		if (format.atLineEnd) atLineEnd++;
		if (format.blankAbove) blankAbove++;
		if (format.blankBelow) blankBelow++;
		total++;
	}

	private void calc() {
		atLineBeg = atLineBeg * 100 / total;
		atLineEnd = atLineEnd * 100 / total;
		blankAbove = blankAbove * 100 / total;
		blankBelow = blankBelow * 100 / total;
	}

	private void apply(Feature feature) {
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
	}

	private void clear() {
		total = 0;
		atLineBeg = 0;
		atLineEnd = 0;
		blankAbove = 0;
		blankBelow = 0;
	}
}
