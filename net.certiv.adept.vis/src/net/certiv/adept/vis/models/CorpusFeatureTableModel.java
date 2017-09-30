package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.model.Feature;

public class CorpusFeatureTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Line", "Leafs", "Edges" };
	private Object[][] rowData;

	public CorpusFeatureTableModel(ArrayListMultimap<Integer, Feature> typeIndex, int type) {
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Feature feature : typeIndex.get(type)) {
			int tc = feature.getEdgeSet().getEdgeTypes().size();
			int ec = feature.getEdgeSet().size();

			Object[] row = { line, tc, ec };
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
