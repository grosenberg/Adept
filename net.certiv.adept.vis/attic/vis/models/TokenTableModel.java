package net.certiv.adept.vis.graph.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;

public class TokenTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Index", "Hidden", "Type", "Text", "Line", "Col" };
	private Object[][] rowData;

	public TokenTableModel(final List<Token> tokens, final String[] tokenNames) {
		List<String[]> rows = new ArrayList<>();
		for (Token token : tokens) {
			int type = token.getType();

			String index = String.valueOf(token.getTokenIndex());
			String hide = token.getChannel() > 0 ? "Hdn" : "";
			String name = type == Token.EOF ? "EOF" : tokenNames[type];
			String text = Utils.escapeWhitespace(token.getText(), true);
			String line = String.valueOf(token.getLine());
			String col = String.valueOf(token.getCharPositionInLine() + 1);

			String[] row = { index, hide, name, text, line, col };
			rows.add(row);
		}
		this.rowData = rows.toArray(new String[rows.size()][]);
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
