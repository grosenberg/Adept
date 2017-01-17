package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.model.FeatureType;

public class FeatureTypeCellRenderer extends DefaultTableCellRenderer {

	private FeatureTypeTableModel model;

	public FeatureTypeCellRenderer(FeatureTypeTableModel model) {
		this.model = model;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		int mRow = table.convertRowIndexToModel(row);
		Object kind = model.getValueAt(mRow, 1); // kind

		if (kind.equals(FeatureType.BLOCKCOMMENT.toString())) {
			c.setForeground(Color.green);
		} else if (kind.equals(FeatureType.LINECOMMENT.toString())) {
			c.setForeground(Color.cyan);
		} else if (kind.equals(FeatureType.RULE.toString())) {
			c.setForeground(Color.blue);
		} else {
			c.setForeground(Color.black);
		}
		return c;
	}
}
