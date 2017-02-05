package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.parser.Collector;

public class DocModel {

	private Document doc;
	private List<Feature> features;

	// key = feature type; value = corresponding features
	private Map<Integer, List<Feature>> index = new HashMap<>();

	/** Creates a nascent model for the given doc */
	public DocModel(Collector collector) {
		doc = collector.getDocument();
		doc.setModel(this);
		this.features = collector.getNonRuleFeatures();
		buildIndex();
	}

	public Document getDocument() {
		return doc;
	}

	public List<Feature> getFeatures() {
		return features;
	}


	/** Returns a map, keyed by feature type, of all features in the document model */
	public Map<Integer, List<Feature>> getFeatureIndex() {
		if (index.isEmpty()) buildIndex();
		return index;
	}

	private void buildIndex() {
		for (Feature f : features) {
			List<Feature> fSet = index.get(f.getType());
			if (fSet == null) {
				fSet = new ArrayList<>();
				index.put(f.getType(), fSet);
			}
			fSet.add(f);
		}
	}
}
