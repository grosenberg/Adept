package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.topo.Stats;

public class FeaturesTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Line", "Kind", "Index", "Type", "Features", "Edges per" };
	private Object[][] rowData;

	public FeaturesTableModel(Map<Integer, List<Feature>> index, List<String> ruleNames, List<String> tokenNames) {

		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Integer key : index.keySet()) {
			List<Feature> features = index.get(key);
			Feature feature = features.get(0);

			String kind = feature.getKind().toString();

			int tIdx = key;
			String type;
			if (feature.getKind() == Kind.RULE) {
				tIdx = tIdx >> 10;
				type = tIdx != 0 ? ruleNames.get(tIdx) : "adept";
			} else {
				type = tIdx != -1 ? tokenNames.get(tIdx) : "EOF";
			}

			int fCnt = features.size();
			int eCnt = 0;
			for (Feature f : features) {
				Stats stats = f.getStats();
				eCnt += stats.edgeCount;
			}

			Object[] row = { line, kind, tIdx, type, fCnt, eCnt / fCnt };
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
