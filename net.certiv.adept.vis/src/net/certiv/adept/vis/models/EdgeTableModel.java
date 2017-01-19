package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;

public class EdgeTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Line", "Root", "Root text", "Leaf", "Leaf text", "Metric", "Weight" };
	private Object[][] rowData;

	public EdgeTableModel(Map<Integer, List<Feature>> index, ISourceParser lang, int key, int idx) {

		List<Feature> features = index.get(key);
		Feature feature = features.get(idx);
		Collection<List<Edge>> edgeSets = feature.getEdges().values();

		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (List<Edge> edges : edgeSets) {
			for (Edge edge : edges) {
				String root = edge.root.getAspect();
				String rTxt = edge.root.getText();
				String leaf = edge.leafAspect;
				String lTxt = edge.leaf.getText();
				double metric = edge.metric;
				double weight = edge.weight;

				Object[] row = { line, root, rTxt, leaf, lTxt, metric, weight };
				rows.add(row);
				line++;
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
}
