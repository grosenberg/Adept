package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.util.Strings;
import net.certiv.adept.vis.utils.CollUtil;

public class DocTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Num", "Line", "Col", "Aspect", "Text", "Facets" };
	private Object[][] rowData;

	private Map<Integer, Feature> index = new HashMap<>();

	public DocTableModel(List<Feature> features) {
		CollUtil.sortLineCol(features);

		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (Feature feature : features) {
			if (feature.getKind() == Kind.RULE) continue;

			int line = feature.getY();
			int col = feature.getX();
			String aspect = feature.getAspect();
			String text = feature.getText();
			String facets = Strings.join(Facet.get(feature.getFormat()), ", ");

			Object[] row = { num + 1, line, col, aspect, text, facets };
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
