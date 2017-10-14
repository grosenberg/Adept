package net.certiv.adept.vis.models;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.util.Kind;
import net.certiv.adept.vis.models.CorpusListModel.FeatureListItem;

public class CorpusListModel extends DefaultComboBoxModel<FeatureListItem> {

	public static class FeatureListItem {

		public int line;
		public int type;
		public String name;
		public int featureCnt;

		public FeatureListItem(int line, int type, String name, int featureCnt) {
			super();
			this.line = line;
			this.type = type;
			this.name = name;
			this.featureCnt = featureCnt;
		}

		@Override
		public String toString() {
			return String.format("%s: %s", line, name);
		}
	}

	private List<String> ruleNames;
	private List<String> tokenNames;
	private ArrayListMultimap<Integer, Feature> typeIndex;

	public CorpusListModel(List<String> ruleNames, List<String> tokenNames) {
		super();
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
	}

	public void addElements(ArrayListMultimap<Integer, Feature> typeIndex) {
		this.typeIndex = typeIndex;

		int line = 1;
		for (Integer key : typeIndex.keySet()) {
			List<Feature> features = typeIndex.get(key);

			int type = key;
			String name;
			if (features.get(0).getKind() == Kind.RULE) {
				type = type >>> 32;
				if (type == 0) continue; // do not include "adept"
				name = ruleNames.get((int) type);
			} else {
				name = type != -1 ? tokenNames.get((int) type) : "EOF";
			}

			FeatureListItem item = new FeatureListItem(line, type, name, features.size());
			addElement(item);
			line++;
		}

		setSelectedItem(getElementAt(0));
	}

	public List<Feature> getSelectedFeatures() {
		FeatureListItem item = (FeatureListItem) super.getSelectedItem();
		return typeIndex.get(item.type);
	}
}
