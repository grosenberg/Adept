package net.certiv.adept.view.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Maths;
import net.certiv.adept.view.renderers.AlignCellRenderer;

public class MatchesTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Similarity", "Token", "Place", "Indents", "Spacing", "Alignment",
			"Rank" };

	private Object[][] rowData = new Object[0][];
	private Map<Integer, RefToken> index = new HashMap<>();

	public MatchesTableModel(List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);
	}

	public void addAll(TreeMultiset<Double, RefToken> matches) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;

		List<Double> sims = new ArrayList<>(matches.keySet());
		for (Double sim : sims) {
			for (RefToken ref : matches.get(sim)) {

				String token = tText(ref.type, ref.text);
				String place = tPlace(ref);
				String dents = tIndent(ref);
				String space = tSpace(ref);
				String align = tAlign(ref);
				int rank = ref.rank;

				Object[] row = { num, Maths.round(sim, 6), token, place, dents, space, align, rank };
				rows.add(row);
				index.put(num, ref);
				num++;
			}
		}
		rowData = rows.toArray(new Object[rows.size()][]);
		fireTableRowsInserted(0, rowData.length - 1);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(1).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));
		table.getColumnModel().getColumn(7).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(7, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(100);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(80);
		cols.getColumn(5).setPreferredWidth(300);
		cols.getColumn(6).setPreferredWidth(200);
		cols.getColumn(7).setPreferredWidth(20);
	}

	public RefToken getRef(int row) {
		return index.get(row);
	}

	public void removeAllRows() {
		int cnt = getRowCount();
		if (cnt > 0) {
			rowData = new Object[0][0];
			fireTableRowsDeleted(0, cnt - 1);
		}
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
