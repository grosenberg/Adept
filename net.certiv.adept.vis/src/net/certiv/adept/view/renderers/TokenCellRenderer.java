package net.certiv.adept.view.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.view.models.TokenTableModel;

public class TokenCellRenderer extends DefaultTableCellRenderer {

	private TokenTableModel model;

	public TokenCellRenderer(TokenTableModel model) {
		this.model = model;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Object val = model.getValueAt(row, 1); // hidden

		if (val.equals("Hdn")) {
			c.setForeground(Color.BLUE);
		} else {
			c.setForeground(Color.BLACK);
		}
		return c;
	}
}
