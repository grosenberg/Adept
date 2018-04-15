package net.certiv.adept.vis.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.certiv.adept.model.Feature;

public class CollUtil {

	public static void sortLineCol(List<Feature> features) {
		Collections.sort(features, new Comparator<Feature>() {

			@Override
			public int compare(Feature o1, Feature o2) {
				if (o1.getLine() < o2.getLine()) return -1;
				if (o1.getLine() > o2.getLine()) return 1;
				if (o1.getCol() < o2.getCol()) return -1;
				if (o1.getCol() < o2.getCol()) return 1;
				return 0;
			}
		});
	}
}
