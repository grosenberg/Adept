package net.certiv.adept.vis.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.model.util.Kind;
import net.certiv.adept.vis.models.CorpusFeaturesTableModel;

public class FeaturesCellRenderer extends DefaultTableCellRenderer {

	private CorpusFeaturesTableModel model;
	private int alignment;

	public FeaturesCellRenderer(CorpusFeaturesTableModel model, int alignment) {
		this.model = model;
		this.alignment = alignment;
	}

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

		int mRow = table.convertRowIndexToModel(row);
		Object kind = model.getValueAt(mRow, 1); // kind

		if (kind.equals(Kind.BLOCKCOMMENT.toString())) {
			c.setForeground(new Color(40, 80, 60));
		} else if (kind.equals(Kind.LINECOMMENT.toString())) {
			c.setForeground(new Color(0, 0, 192));
		} else if (kind.equals(Kind.RULE.toString())) {
			c.setForeground(new Color(127, 0, 85));
		} else {
			c.setForeground(Color.black);
		}
		return c;
	}
}
