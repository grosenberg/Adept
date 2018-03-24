package net.certiv.adept.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.TreeMultiset;

public class Matcher {

	public static TreeMultiset<Double, Feature> score(Set<Feature> comparables, Feature feature) {
		TreeMultiset<Double, Feature> scored = new TreeMultiset<>();
		if (comparables != null) {
			List<Integer> ancestors = feature.getAncestors();
			for (Feature comp : comparables) {
				List<Integer> compAncestors = comp.getAncestors();
				double dist = DamerauAlignment.distance(compAncestors, ancestors);
				double score = DamerauAlignment.simularity(dist, compAncestors.size(), ancestors.size());
				score += simularity(comp, feature);
				scored.put(score / 2, comp);
			}
		}
		return scored;
	}

	public static double simularity(Feature comp, Feature feature) {
		int cnt = 2 * comp.getRefs().size() * feature.getRefs().size();
		double sim = 0;
		for (RefToken fRef : feature.getRefs()) {
			for (RefToken cRef : comp.getRefs()) {
				sim += simularity(cRef.lSpacing, fRef.lSpacing);
				sim += simularity(cRef.rSpacing, fRef.rSpacing);
			}
		}
		return sim / cnt;
	}

	public static double simularity(Spacing a, Spacing b) {
		double dist = Math.abs(a.id() - b.id());
		double norm = Math.min(5, dist) / 5;
		return 1 - norm;
	}

	public static Feature bestMatch(List<Feature> corpus, Set<Feature> possibles) {
		ArrayList<Feature> choices = new ArrayList<>(possibles);
		switch (possibles.size()) {
			case 0:
				return null;
			case 1:
				return choices.get(0);
			default:
				// something
				return choices.get(0);
		}
	}
}
