package net.certiv.adept.vis.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;

public class FeatureTypeTableModel extends AbstractTableModel {

	private static final int CUTOFF = (1 << 10) - 1;

	private final String[] columnNames = { "Line", "Kind", "Index", "Type", "Features" };
	private Object[][] rowData;

	public FeatureTypeTableModel(Map<Integer, List<Feature>> index, List<String> ruleNames, List<String> tokenNames) {

		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Integer key : index.keySet()) {
			List<Feature> features = index.get(key);
			Feature feature = features.get(0);

			String kind = feature.getFeatureType().toString();

			int tIdx = key;
			String type;
			if (tIdx > CUTOFF) {
				tIdx = tIdx >> 10;
				type = ruleNames.get(tIdx);
			} else {
				type = tIdx != -1 ? tokenNames.get(tIdx) : "EOF";
			}

			int fCnt = features.size();

			Object[] row = { line, kind, tIdx, type, fCnt };
			rows.add(row);
			line++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}

	@Override
	public int getRowCount() {
		return rowData.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return rowData[row][col];
	}
}
