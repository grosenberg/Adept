package net.certiv.adept.model.util;

import java.util.List;

import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Context;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Refs;

public class Analyzer {

	private static ParseRecord _data;
	private static List<Feature> _features;

	private Analyzer() {}

	public static void evaluate(ParseRecord data, List<Feature> features) {
		_data = data;
		_features = features;

		checkContexts();
	}

	private static void checkContexts() {
		Refs.setup(_data.getRuleNames(), _data.getTokenNames());
		for (Feature feature : _features) {
			for (RefToken ref : feature.getRefs()) {

				int tIndex = ref.index;
				String name = Refs.tText(ref.type, ref.text);
				String place = Refs.tPlace(ref);
				String loc = Refs.tLocation(ref);
				// String ancestors = Refs.evalAncestors(feature.getAncestors());
				// String dents = Refs.tIndent(ref);
				// String space = Refs.tSpace(ref);
				// String align = Refs.tAlign(ref);

				if (ref.contexts == null) {
					String msg = String.format("Null: %3d %s %s %s", tIndex, name, loc, place);
					Log.debug(Analyzer.class, msg);

				} else if (ref.contexts.isEmpty()) {
					String msg = String.format("None: %3d %s %s %s", tIndex, name, loc, place);
					Log.debug(Analyzer.class, msg);

				} else {
					for (Context context : ref.contexts) {
						if (context.lAssocs.isEmpty() || context.rAssocs.isEmpty()) {
							String assoc = Refs.tAssoc(ref.type, context);
							String msg = String.format("One.: %3d %s %s %s\t%s", tIndex, name, loc, place, assoc);
							Log.debug(Analyzer.class, msg);
						}
					}
				}
			}
		}
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
