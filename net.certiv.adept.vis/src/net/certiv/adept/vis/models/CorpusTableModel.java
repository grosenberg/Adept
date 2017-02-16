package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;

public class CorpusTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Line", "Kind", "Type", "Name", "Features" };
	private Object[][] rowData;

	public CorpusTableModel(ArrayListMultimap<Long, Feature> typeIndex, List<String> ruleNames,
			List<String> tokenNames) {

		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Long key : typeIndex.keySet()) {
			List<Feature> features = typeIndex.get(key);
			Feature feature = features.get(0);

			String kind = feature.getKind().toString();

			long type = feature.getType();
			String name;
			if (feature.getKind() == Kind.RULE) {
				type = type >>> 32;
				name = type != 0 ? ruleNames.get((int) type) : "adept";
			} else {
				name = type != -1 ? tokenNames.get((int) type) : "EOF";
			}
			int fCnt = features.size();

			Object[] row = { line, kind, type, name, fCnt };
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
