package net.certiv.adept.model.util;

import net.certiv.adept.model.Feature;

public class MatchData {

	public Feature feature;
	public int edgeCount; 		// size of edgeSet 
	public int typeCount; 		// number of unique edgeSet types 

	public double similarity;
	public double selfSimF;
	public double selfSimM;
	public double mutualSim;

	public double ancestorSim;
	public double edgeTypeSim;
	public double edgeAspectsSim;
	public double edgeTextSim;
	public double formatLineSim;
	public double formatWsSim;
	public double formatStyleSim;
	public double weightSim;

	public MatchData(Feature feature) {
		this.feature = feature;
		this.edgeCount = feature.getEdgeSet().size();
		this.typeCount = feature.getEdgeSet().sizeTypes();
	}

	public MatchData(Feature feature, Feature matched) {
		this(feature);
		this.similarity = feature.similarity(matched);
		this.selfSimF = feature.getSelfSim();
		this.selfSimM = matched.getSelfSim();
		this.mutualSim = feature.mutualSimilarity(matched);

		this.ancestorSim = feature.ancestorSimilarity(matched);
		this.edgeTypeSim = feature.edgeSetTypeSimilarity(matched);
		this.edgeAspectsSim = feature.edgeSetAspectsSimilarity(matched);
		this.edgeTextSim = feature.edgeSetTextSimilarity(matched);
		this.formatLineSim = feature.formatLineSimilarity(matched);
		this.formatWsSim = feature.formatWsSimilarity(matched);
		this.formatStyleSim = feature.formatStyleSimilarity(matched);
		this.weightSim = feature.weightSimilarity(matched);
	}
}
