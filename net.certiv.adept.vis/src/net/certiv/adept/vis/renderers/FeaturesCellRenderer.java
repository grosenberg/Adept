package net.certiv.adept.vis.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.model.Kind;
import net.certiv.adept.vis.models.CorpusTableModel;

public class FeaturesCellRenderer extends DefaultTableCellRenderer {

	private CorpusTableModel model;

	public FeaturesCellRenderer(CorpusTableModel model) {
		this.model = model;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		int mRow = table.convertRowIndexToModel(row);
		Object kind = model.getValueAt(mRow, 1); // kind

		if (kind.equals(Kind.BLOCKCOMMENT.toString())) {
			c.setForeground(Color.green);
		} else if (kind.equals(Kind.LINECOMMENT.toString())) {
			c.setForeground(Color.cyan);
		} else if (kind.equals(Kind.RULE.toString())) {
			c.setForeground(Color.blue);
		} else {
			c.setForeground(Color.black);
		}
		return c;
	}
}
