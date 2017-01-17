package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TokenCellRenderer extends DefaultTableCellRenderer {

	private TokenTableModel model;

	public TokenCellRenderer(TokenTableModel model) {
		this.model = model;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Object val = model.getValueAt(row, 1); // hidden

		if (val.equals("Hdn")) {
			c.setForeground(Color.gray);
		} else {
			c.setForeground(Color.black);
		}
		return c;
	}
}
