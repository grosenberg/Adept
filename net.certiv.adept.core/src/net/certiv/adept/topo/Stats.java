package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.google.common.primitives.Doubles;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.EdgeKey;
import net.certiv.adept.model.Feature;

public class Stats {

	private Feature feature;

	public int typeCount; // # of unique feature types connected to this feature
	public int edgeCount; // total # of edges connected to this feature

	public double maxSd;		// feature's max edge metric stdDev
	public double minSd;

	public Stats(Feature feature) {
		this.feature = feature;
		edgeCount();
	}

	private void edgeCount() {
		Map<EdgeKey, TreeSet<Edge>> edgeMap = feature.getEdgesMap();
		typeCount = edgeMap.size();

		for (TreeSet<Edge> edges : edgeMap.values()) {
			edgeCount += edges.size();
			List<Double> metrics = new ArrayList<>();
			for (Edge edge : edges) {
				metrics.add(edge.metric);
			}
			StandardDeviation sd = new StandardDeviation();
			double val = sd.evaluate(Doubles.toArray(metrics));
			maxSd = Math.max(maxSd, val);
			minSd = minSd != 0 ? Math.min(maxSd, val) : val;
		}
	}
}
