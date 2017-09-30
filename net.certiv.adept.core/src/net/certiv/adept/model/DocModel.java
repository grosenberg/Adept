package net.certiv.adept.model;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.parser.Builder;

public class DocModel {

	private ProcessMgr mgr;
	private Document doc;
	private List<Feature> features;

	// key = feature type; value = corresponding features
	private final ArrayListMultimap<Integer, Feature> index = ArrayListMultimap.create();

	/** Creates a nascent model for the given doc. */
	public DocModel(ProcessMgr mgr, Builder builder) {
		this.mgr = mgr;
		this.features = builder.getNonRuleFeatures();
		doc = builder.getDocument();
		doc.setModel(this);
		buildIndex();
	}

	public ProcessMgr getMgr() {
		return mgr;
	}

	public void setMgr(ProcessMgr mgr) {
		this.mgr = mgr;
	}

	public Document getDocument() {
		return doc;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	/** Returns a map, keyed by feature type, of all features in the document model */
	public ArrayListMultimap<Integer, Feature> getFeatureIndex() {
		if (index.isEmpty()) buildIndex();
		return index;
	}

	private void buildIndex() {
		for (Feature feature : features) {
			index.put(feature.getType(), feature);
		}
	}
}
