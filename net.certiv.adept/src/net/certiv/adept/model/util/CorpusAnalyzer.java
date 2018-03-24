package net.certiv.adept.model.util;

import net.certiv.adept.lang.ParseRecord;

public class CorpusAnalyzer {

	@SuppressWarnings("unused")
	private final ParseRecord data;

	public CorpusAnalyzer(ParseRecord data) {
		super();
		this.data = data;
	}

	public void execute() {
		processByType();
	}

	// analyze based on terminal feature type to set document wide format aspects
	private void processByType() {
		// int atLineBeg = 0;
		// int atLineEnd = 0;
		// int blankAbove = 0;
		// int total = 0;
		//
		// for (Integer type : data.tokenTypeFeatureIndex.keySet()) {
		// ArraySet<Feature> features = data.tokenTypeFeatureIndex.get(type);
		// for (Feature feature : features) {
		// Format format = feature.getFormat();
		// int rank = feature.getEquivalentWeight();
		// if (format.atLineBeg) atLineBeg += rank;
		// if (format.atLineEnd) atLineEnd += rank;
		// if (format.blankAbove) blankAbove += rank;
		// total += rank;
		// }
		//
		// atLineBeg = atLineBeg * 100 / total;
		// atLineEnd = atLineEnd * 100 / total;
		// blankAbove = blankAbove * 100 / total;
		//
		// for (Feature feature : features) {
		// Format format = feature.getFormat();
		// if (atLineBeg > 98) {
		// format.joinNever = true;
		// } else if (atLineBeg < 20) {
		// format.joinShould = true;
		// } else if (atLineBeg < 2) {
		// format.joinAlways = true;
		// } else {
		// format.joinAllow = true;
		// }
		// }
		//
		// total = 0;
		// atLineBeg = 0;
		// atLineEnd = 0;
		// blankAbove = 0;
		// }
	}
}
