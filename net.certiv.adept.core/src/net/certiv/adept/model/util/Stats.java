package net.certiv.adept.model.util;

import net.certiv.adept.model.Feature;

public class Stats {

	public Feature feature;
	public int edgeCount; 		// size of edgeSet 
	public int typeCount; 		// number of unique edgeSet types 

	public double similarity;
	public double selfSimF;
	public double selfSimM;
	public double pairSim;

	public double ancestorSim;
	public double edgeTypeSim;
	public double edgeTextSim;
	public double formatSim;
	public double weightSim;

	public Stats(Feature feature) {
		this.feature = feature;
		this.edgeCount = feature.getEdgeSet().size();
		this.typeCount = feature.getEdgeSet().sizeTypes();
	}

	public Stats(Feature feature, Feature matched) {
		this(feature);
		this.similarity = feature.similarity(matched);
		this.selfSimF = feature.getSelfSim();
		this.selfSimM = matched.getSelfSim();
		this.pairSim = feature.pairSimilarity(matched);

		this.ancestorSim = feature.ancestorSimilarity(matched);
		this.edgeTypeSim = feature.edgeSetTypeSimilarity(matched);
		this.edgeTextSim = feature.edgeSetTextSimilarity(matched);
		this.formatSim = feature.formatSimilarity(matched);
		this.weightSim = feature.weightSimilarity(matched);
	}
}
