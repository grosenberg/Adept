package net.certiv.adept.model.util;

import net.certiv.adept.model.Feature;

public class Stats {

	public Feature feature;
	public int edgeCount; 		// size of edgeSet 
	public int typeCount; 		// number of unique edgeSet types 

	public double similarity;
	public double selfSimF;
	public double selfSimM;
	public double mutualSim;

	public double ancestorSim;
	public double edgeTypeSim;
	public double edgeTextSim;
	public double formatSim;
	public double weightSim;

	public Stats(Feature source) {
		this.feature = source;
		this.edgeCount = source.getEdgeSet().size();
		this.typeCount = source.getEdgeSet().sizeTypes();
	}

	public Stats(Feature source, Feature matched) {
		this(source);
		this.similarity = source.similarity(matched);
		this.selfSimF = source.getSelfSim();
		this.selfSimM = matched.getSelfSim();
		this.mutualSim = source.mutualSimilarity(matched);

		this.ancestorSim = source.ancestorSimilarity(matched);
		this.edgeTypeSim = source.edgeSetTypeSimilarity(matched);
		this.edgeTextSim = source.edgeSetTextSimilarity(matched);
		this.formatSim = source.formatSimilarity(matched);
		this.weightSim = source.weightSimilarity(matched);
	}
}
