package net.certiv.adept.view.utils;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ColumnResizer {

	public static void adjustColumnPreferredWidths(JTable table) {
		TableColumnModel columnModel = table.getColumnModel();
		for (int col = 0; col < table.getColumnCount(); col++) {
			int maxwidth = 0;
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer rend = table.getCellRenderer(row, col);
				Object value = table.getValueAt(row, col);
				Component comp = rend.getTableCellRendererComponent(table, value, false, false, row, col);
				maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
			}

			TableColumn column = columnModel.getColumn(col);
			TableCellRenderer renderer = column.getHeaderRenderer();
			if (renderer == null) renderer = table.getTableHeader().getDefaultRenderer();
			Object value = column.getHeaderValue();
			Component cell = renderer.getTableCellRendererComponent(table, value, false, false, 0, col);
			maxwidth = Math.max(maxwidth, cell.getPreferredSize().width);
			column.setPreferredWidth(maxwidth);
		}
	}
}
