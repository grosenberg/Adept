package net.certiv.adept.model;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.parser.Collector;

public class DocModel implements IModel {

	private CoreMgr mgr;
	private Document doc;
	private List<Feature> features;

	// key = feature type; value = corresponding features
	private ArrayListMultimap<Long, Feature> typeIndex = ArrayListMultimap.create();

	/** Creates a nascent model for the given doc. */
	public DocModel(CoreMgr mgr, Collector collector) {
		this.mgr = mgr;
		this.features = collector.getNonRuleFeatures();
		doc = collector.getDocument();
		doc.setModel(this);
		buildIndex();
	}

	@Override
	public CoreMgr getMgr() {
		return mgr;
	}

	@Override
	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	public Document getDocument() {
		return doc;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	/** Returns a map, keyed by feature type, of all features in the document model */
	public ArrayListMultimap<Long, Feature> getFeatureIndex() {
		if (typeIndex.isEmpty()) buildIndex();
		return typeIndex;
	}

	private void buildIndex() {
		for (Feature f : features) {
			typeIndex.put(f.getType(), f);
		}
	}
}
