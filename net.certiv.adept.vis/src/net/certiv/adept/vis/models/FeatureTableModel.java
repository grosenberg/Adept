/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.model.Feature;
import net.certiv.adept.vis.renderers.FeaturesCellRenderer;

public class FeatureTableModel extends BaseTableModel {

	private final String[] columnNames = { "Line", "Feature", "Kind", "Ancestors", "Token", "Token Refs", "Weight" };

	private List<Feature> features;
	private Object[][] rowData;

	public FeatureTableModel(List<Feature> features, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		addAll(features);
	}

	public void addAll(List<Feature> features) {
		this.features = features;
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Feature feature : features) {
			int id = feature.getId();
			String kind = feature.getKind().toString();
			String ancestors = evalAncestors(feature.getAncestors());
			String name = String.format("%s  (%s)", feature.getNodeName(), feature.getType());
			int refCnt = feature.getRefs().size();
			int weight = feature.getWeight();

			Object[] row = { line, id, kind, ancestors, name, refCnt, weight };
			rows.add(row);
			line++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
		fireTableRowsInserted(0, rowData.length - 1);
	}

	public void removeAllRows() {
		int cnt = getRowCount();
		if (cnt > 0) {
			rowData = new Object[0][0];
			fireTableRowsDeleted(0, cnt - 1);
		}
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new FeaturesCellRenderer(this, SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.CENTER));
		table.getColumnModel().getColumn(1).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.RIGHT));
		table.getColumnModel().getColumn(6).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.RIGHT));
		table.getColumnModel().getColumn(7).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(5, NumComp);
		sorter.setComparator(6, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(10);
		cols.getColumn(2).setPreferredWidth(40);
		cols.getColumn(3).setPreferredWidth(300);
		cols.getColumn(4).setPreferredWidth(80);
		cols.getColumn(5).setPreferredWidth(30);
		cols.getColumn(6).setPreferredWidth(40);
	}

	public Feature getFeature(int row) {
		return features.get(row);
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
