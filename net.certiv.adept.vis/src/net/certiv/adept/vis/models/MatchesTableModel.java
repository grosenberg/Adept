package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.util.Strings;

public class MatchesTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Order", "Distance", "Feature", "Facets" };
	private Object[][] rowData;

	private Map<Integer, Feature> index = new HashMap<>();

	public MatchesTableModel(TreeMap<Double, Feature> matches) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (Double key : matches.keySet()) {
			String dist = String.valueOf(key);
			Feature feature = matches.get(key);
			String facets = Strings.join(Facet.get(feature.getFormat()), ", ");

			Object[] row = { num + 1, dist, feature.toString(), facets };
			rows.add(row);
			index.put(num, feature);
			num++;
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
