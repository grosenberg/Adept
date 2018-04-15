package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.vis.renderers.TokenCellRenderer;

public class TokenTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Index", "Hidden", "Type", "Text", "Line", "Col" };
	private Object[][] rowData;

	public TokenTableModel(final List<AdeptToken> tokens, final List<String> tokenNames) {
		List<String[]> rows = new ArrayList<>();
		for (AdeptToken token : tokens) {
			int type = token.getType();

			String index = String.valueOf(token.getTokenIndex());
			String hide = token.getChannel() > 0 ? "Hdn" : "";
			String name = type == Token.EOF ? "EOF" : tokenNames.get(type);
			String text = Utils.escapeWhitespace(token.getText(), true);
			String line = String.valueOf(token.getLine());
			String col = String.valueOf(token.getCharPositionInLine() + 1);

			String[] row = { index, hide, name, text, line, col };
			rows.add(row);
		}
		this.rowData = rows.toArray(new String[rows.size()][]);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new TokenCellRenderer(this));
		TableColumnModel model = table.getColumnModel();
		model.getColumn(0).setPreferredWidth(10);
		model.getColumn(1).setPreferredWidth(10);
		model.getColumn(2).setPreferredWidth(100);
		model.getColumn(4).setPreferredWidth(10);
		model.getColumn(5).setPreferredWidth(10);
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}

	@Override
	public int getRowCount() {
		return rowData.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return rowData[row][col];
	}
}
