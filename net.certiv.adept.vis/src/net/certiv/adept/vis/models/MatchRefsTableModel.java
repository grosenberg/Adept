package net.certiv.adept.vis.models;

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
import net.certiv.adept.vis.renderers.AlignCellRenderer;

public class MatchRefsTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Similarity", "Token", "Place", "Indents", "Spacing", "Alignment",
			"Rank" };

	private final int[] colWidths = { 10, 60, 80, 60, 60, 300, 300, 20 };

	private Object[][] rowData = new Object[0][];
	private Map<Integer, RefToken> index = new HashMap<>();

	public MatchRefsTableModel(List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);
	}

	public void addAll(RefToken srcRef, TreeMultiset<Double, RefToken> matches, double[] maxRanks) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;

		for (Double sim : matches.keySet()) {
			for (RefToken matRef : matches.get(sim)) {
				String token = tText(matRef.type, matRef.text);
				String place = tPlace(matRef);
				String dents = tIndent(matRef);
				String space = tSpace(matRef);
				String align = tAlign(matRef);
				int rank = matRef.rank;

				Object[] row = { num, Maths.round(sim, 6), token, place, dents, space, align, rank };
				rows.add(row);
				index.put(num, matRef);
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
		setColWidths(cols, colWidths);
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
