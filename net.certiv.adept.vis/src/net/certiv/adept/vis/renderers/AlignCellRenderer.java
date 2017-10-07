package net.certiv.adept.vis.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class AlignCellRenderer extends DefaultTableCellRenderer {

	private int alignment;

	public AlignCellRenderer(int alignment) {
		this.alignment = alignment;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		setHorizontalAlignment(alignment);
		switch (alignment) {
			case SwingConstants.LEFT:
				setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
				break;
			case SwingConstants.RIGHT:
				setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
				break;
			default:
		}
		return c;
	}
}
