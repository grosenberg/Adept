package net.certiv.adept.view.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;

import net.certiv.adept.view.models.BaseTableModel;
import net.certiv.adept.view.models.RefsTableModel;

public class RefsCellRenderer extends BaseCellRenderer {

	public RefsCellRenderer(RefsTableModel model, int alignment) {
		super(model, alignment);
	}

	@Override
	public Component adjustColors(Component c, JTable table, BaseTableModel model, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			c.setForeground(Color.white);
			c.setBackground(new Color(100, 150, 250));

		} else {
			// int mRow = table.convertRowIndexToModel(row);
			// Object kind = model.getValueAt(mRow, 2); // kind

			c.setForeground(Color.black);
			c.setBackground(Color.white);
		}
		return c;
	}
}
