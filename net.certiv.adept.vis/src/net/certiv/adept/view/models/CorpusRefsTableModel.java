package net.certiv.adept.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.model.RefToken;
import net.certiv.adept.view.renderers.RefsCellRenderer;

/** RefTokens for a corpus document. */
public class CorpusRefsTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Token", "Place", "Indents", "Spacing", "Alignment", "Rank" };

	private List<RefToken> refs;
	private Object[][] rowData;

	public CorpusRefsTableModel(List<RefToken> refs, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		addAll(refs);
	}

	public void addAll(List<RefToken> refs) {
		this.refs = refs;
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (RefToken ref : refs) {
			String tokName = tText(ref.type, ref.text);
			String place = tPlace(ref);
			String dents = tIndent(ref);
			String space = tSpace(ref);
			String align = tAlign(ref);

			int rank = ref.rank;

			Object[] row = { line, tokName, place, dents, space, align, rank };
			rows.add(row);
			line++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
		fireTableRowsInserted(0, rowData.length - 1);
	}

	public void removeAllRows() {
		int cnt = getRowCount();
		if (cnt > 0) {
			rowData = new Object[0][0];
			fireTableRowsDeleted(0, cnt - 1);
		}
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new RefsCellRenderer(this, SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new RefsCellRenderer(this, SwingConstants.CENTER));
		table.getColumnModel().getColumn(5).setCellRenderer(new RefsCellRenderer(this, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(6, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(60);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(300);
		cols.getColumn(5).setPreferredWidth(100);
		cols.getColumn(6).setPreferredWidth(30);
	}

	public RefToken getRef(int row) {
		return refs.get(row);
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
