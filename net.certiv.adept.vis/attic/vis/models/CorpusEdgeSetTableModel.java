package net.certiv.adept.vis.graph.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.TreeMultimap;

public class CorpusEdgeSetTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Index", "Leaf Types", "Edges" };
	private Object[][] rowData;

	public CorpusEdgeSetTableModel(TreeMultimap<Integer, Feature> typeIndex, int type) {
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Feature feature : typeIndex.get(type)) {
			int tc = feature.getEdgeSet().sizeTypes();
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
