package net.certiv.adept.view.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.model.Kind;
import net.certiv.adept.view.models.FeatureTableModel;

public class FeaturesCellRenderer extends DefaultTableCellRenderer {

	private FeatureTableModel model;
	private int alignment;

	public FeaturesCellRenderer(FeatureTableModel model, int alignment) {
		this.model = model;
		this.alignment = alignment;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(alignment);
		switch (alignment) {
			case SwingConstants.LEFT:
				setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				break;
			case SwingConstants.RIGHT:
				setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				break;
			default:
		}

		if (isSelected) {
			c.setForeground(Color.white);
			c.setBackground(new Color(100, 150, 250));

		} else {
			int mRow = table.convertRowIndexToModel(row);
			Object kind = model.getValueAt(mRow, 2); // kind

			if (kind.equals(Kind.BLOCKCOMMENT.toString())) {
				c.setForeground(new Color(45, 175, 25));
			} else if (kind.equals(Kind.LINECOMMENT.toString())) {
				c.setForeground(new Color(0, 145, 75));
			} else if (kind.equals(Kind.TERMINAL.toString())) {
				c.setForeground(new Color(20, 20, 20));
			} else if (kind.equals(Kind.VAR.toString())) {
				c.setForeground(new Color(15, 115, 205));
			} else {
				c.setForeground(Color.black);
			}
			c.setBackground(Color.white);
		}
		return c;
	}
}
