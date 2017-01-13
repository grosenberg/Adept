package net.certiv.adept.model;

import java.util.List;

import net.certiv.adept.parser.Collector;

public class DocModel {

	private Document doc;
	private List<Feature> features;

	/** Creates a nascent model for the given doc */
	public DocModel(Collector collector) {
		doc = collector.getDocument();
		doc.setModel(this);
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public Document getDocument() {
		return doc;
	}
}
