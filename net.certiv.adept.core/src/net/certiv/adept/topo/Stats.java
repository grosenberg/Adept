package net.certiv.adept.topo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import com.google.common.primitives.Doubles;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.EdgeKey;
import net.certiv.adept.model.EdgeSet;
import net.certiv.adept.model.Feature;

public class Stats {

	private Feature feature;

	public double distance;
	public double selfSimF;
	public double selfSimM;
	public double mutualSim;
	public double featLabelSim;
	public double edgeSetSim;
	public double intersectSim;
	public double disjointSim;

	public int typeCount; 		// # of unique feature types connected to this feature
	public int edgeCount; 		// total # of edgeSet connected to this feature
	public int edgeSetIntersect;
	public int edgeSetDisjoint;
	public int intersectCount;
	public int disjointCount;

	public String intersectTypes;
	public String disjointTypes;

	public double maxSd;		// feature's max edge metric stdDev
	public double minSd;

	public Stats(Feature feature) {
		this.feature = feature;
		edgeStats();
	}

	public Stats(Feature feature, Feature matched) {
		this(feature);
		this.distance = feature.distance(matched);
		this.selfSimF = feature.getSelfSim();
		this.selfSimM = matched.getSelfSim();
		this.mutualSim = feature.similarity(matched);
		this.featLabelSim = feature.featLabelSimilarity(matched);
		this.edgeSetSim = feature.getEdgeSet().similarity(matched.getEdgeSet());
		this.intersectSim = feature.getEdgeSet().intersect(matched.getEdgeSet());
		this.disjointSim = feature.getEdgeSet().disjoint(matched.getEdgeSet());
		this.intersectCount = feature.getEdgeSet().intersectCount(matched.getEdgeSet());
		this.disjointCount = feature.getEdgeSet().disjointCount(matched.getEdgeSet());
		
		Set<EdgeKey> intersectKeys = feature.getEdgeSet().intersectKeys(matched.getEdgeSet());
		Set<EdgeKey> disjointKeys = feature.getEdgeSet().disjointKeys(matched.getEdgeSet());
		this.intersectTypes= intersectKeys.toString();
		this.disjointTypes = disjointKeys.toString();
	}

	private void edgeStats() {
		EdgeSet edgeSet = feature.getEdgeSet();
		typeCount = edgeSet.getTypeCount();
		edgeCount = edgeSet.getEdges().size();

		List<Double> metrics = new ArrayList<>();
		for (Edge edge : edgeSet.getEdges()) {
			metrics.add(edge.metric);
		}
		StandardDeviation sd = new StandardDeviation();
		double val = sd.evaluate(Doubles.toArray(metrics));
		maxSd = Math.max(maxSd, val);
		minSd = minSd != 0 ? Math.min(maxSd, val) : val;
	}
}
