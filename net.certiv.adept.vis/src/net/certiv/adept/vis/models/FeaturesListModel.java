package net.certiv.adept.vis.models;

import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.vis.models.FeaturesListModel.FeatureListItem;

public class FeaturesListModel extends DefaultComboBoxModel<FeatureListItem> {

	public static class FeatureListItem {

		public int line;
		public int index;
		public String type;
		public int featureCnt;

		public FeatureListItem(int line, int index, String type, int featureCnt) {
			super();
			this.line = line;
			this.index = index;
			this.type = type;
			this.featureCnt = featureCnt;
		}

		@Override
		public String toString() {
			return String.format("%s: %s", line, type);
		}
	}

	private List<String> ruleNames;
	private List<String> tokenNames;
	private Map<Integer, List<Feature>> index;

	public FeaturesListModel(List<String> ruleNames, List<String> tokenNames) {
		super();
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
	}

	public void addElements(Map<Integer, List<Feature>> index) {
		this.index = index;

		int line = 1;
		for (Integer key : index.keySet()) {
			List<Feature> features = index.get(key);

			int tIdx = key;
			String type;
			if (features.get(0).getKind() == Kind.RULE) {
				tIdx = tIdx >> 10;
				if (tIdx == 0) continue; // do not include "adept"
				type = ruleNames.get(tIdx);
			} else {
				type = tIdx != -1 ? tokenNames.get(tIdx) : "EOF";
			}
			int fCnt = features.size();

			FeatureListItem item = new FeatureListItem(line, key, type, fCnt);
			addElement(item);
			line++;
		}

		setSelectedItem(getElementAt(0));
	}

	public List<Feature> getSelectedFeatures() {
		FeatureListItem item = (FeatureListItem) super.getSelectedItem();
		return index.get(item.index);
	}
}
