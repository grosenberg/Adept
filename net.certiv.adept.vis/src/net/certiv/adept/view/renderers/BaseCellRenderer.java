package net.certiv.adept.view.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.view.models.BaseTableModel;

public abstract class BaseCellRenderer extends DefaultTableCellRenderer {

	private BaseTableModel model;
	private int alignment;

	public BaseCellRenderer(BaseTableModel model, int alignment) {
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

		adjustColors(c, table, model, isSelected, hasFocus, row, column);

		return c;
	}

	public abstract Component adjustColors(Component c, JTable table, BaseTableModel model, boolean isSelected,
			boolean hasFocus, int row, int column);
}
