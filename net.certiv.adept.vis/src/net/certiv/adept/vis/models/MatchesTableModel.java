package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.util.TreeMultimap;

public class MatchesTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Order", "Similarity", "Weight", "Feature", "Format" };
	private Object[][] rowData;

	private Map<Integer, Feature> index = new HashMap<>();

	public MatchesTableModel(TreeMultimap<Double, Feature> matches) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;

		List<Double> sims = new ArrayList<>(matches.keySet());
		Collections.reverse(sims);
		for (Double sim : sims) {
			for (Feature feature : matches.get(sim)) {
				String facets = feature.getFormat().toString();

				Object[] row = { num + 1, sim, feature.getEquivalentWeight(), feature.toString(), facets };
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
