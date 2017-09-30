package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;

public class MatchesTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Order", "Distance", "Partition", "Feature", "Facets" };
	private Object[][] rowData;

	private Map<Integer, Feature> index = new HashMap<>();

	public MatchesTableModel(net.certiv.adept.util.TreeMultimap<Double, Feature> matches,
			Map<Double, Integer> indices) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (Double key : matches.keySet()) {
			String dist = String.valueOf(key);
			Integer partition = indices.get(key);
			String part = partition != null ? String.valueOf(partition) : "None";

			for (Feature feature : matches.get(key)) {
				String facets = feature.getFormat().toString();

				Object[] row = { num + 1, dist, part, feature.toString(), facets };
				rows.add(row);
				index.put(num, feature);
				num++;
			}
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

	public Feature getFeature(int row) {
		return index.get(row);
	}
}
