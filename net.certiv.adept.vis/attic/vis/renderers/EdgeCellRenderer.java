package net.certiv.adept.vis.graph.renderers;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.certiv.adept.vis.graph.models.CorpusEdgeTableModel;

public class EdgeCellRenderer extends DefaultTableCellRenderer {

	@SuppressWarnings("unused") private CorpusEdgeTableModel model;
	private int alignment;

	public EdgeCellRenderer(CorpusEdgeTableModel model, int alignment) {
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

		// int mRow = table.convertRowIndexToModel(row);
		// Object kind = model.getValueAt(mRow, 1); // kind
		//
		// if (kind.equals(Kind.BLOCKCOMMENT.toString())) {
		// c.setForeground(Color.green);
		// } else if (kind.equals(Kind.LINECOMMENT.toString())) {
		// c.setForeground(Color.cyan);
		// } else if (kind.equals(Kind.RULE.toString())) {
		// c.setForeground(Color.blue);
		// } else {
		// c.setForeground(Color.black);
		// }

		return c;
	}
}
