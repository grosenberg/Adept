package net.certiv.adept.model.util;

import java.util.List;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.ArraySet;
import net.certiv.adept.util.TreeMultimap;

public class Matcher {

	public static TreeMultimap<Double, Feature> score(ArraySet<Feature> comparables, Feature feature) {
		TreeMultimap<Double, Feature> scored = new TreeMultimap<>();
		List<Integer> ancestors = feature.getAncestorPath();
		for (Feature comp : comparables) {
			List<Integer> compAncestors = comp.getAncestorPath();
			double dist = DamerauAlignment.distance(compAncestors, ancestors);
			double score = DamerauAlignment.simularity(dist, compAncestors.size(), ancestors.size());
			score += Spacing.simularity(comp.getSpacing(), feature.getSpacing());
			scored.put(score / 2, comp);
		}
		return scored;
	}

	public static Feature bestMatch(List<Feature> corpus, ArraySet<Feature> possibles) {
		switch (possibles.size()) {
			case 0:
				return null;
			case 1:
				return possibles.get(0);
			default:
				// something
				return possibles.get(0);
		}
	}
}
