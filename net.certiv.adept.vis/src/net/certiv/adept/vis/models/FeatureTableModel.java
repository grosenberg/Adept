package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.topo.Stats;

public class FeatureTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Line", "End Types", "Edges per", "Delta StdDev" };
	private Object[][] rowData;

	public FeatureTableModel(Map<Integer, List<Feature>> index, ISourceParser lang, int key) {
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Feature feature : index.get(key)) {
			Stats stats = feature.getStats();

			int tc = stats.typeCount;
			int ec = stats.edgeCount;
			int ep = tc > 0 ? ec / tc : ec;

			int diff = (int) Math.round(stats.maxSd - stats.minSd);

			Object[] row = { line, tc, ep, diff };
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
