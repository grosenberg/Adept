package net.certiv.adept.vis.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.model.Context;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Maths;
import net.certiv.adept.vis.renderers.AlignCellRenderer;

public class ContextsTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Associates", "Rank", "Total Similarity", "Left Similarity",
			"Right Similarity", "Rank Fraction" };
	private final int[] colWidths = { 10, 300, 80, 80, 80, 80, 80 };

	private Object[][] rowData = new Object[0][];
	private List<Context> matches;

	public ContextsTableModel(List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);
	}

	public void addAll(RefToken srcToken, RefToken matched, double[] maxRanks) {
		Context src = srcToken.contexts.get(0);
		this.matches = matched.contexts;

		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (Context match : matches) {

			double[] score = Context.score(src, match, maxRanks[1]);

			String assoc = tAssoc(matched.type, match);
			int rank = match.rank;
			double lSim = score[0];
			double rSim = score[1];
			double frac = score[2];
			double tSim = lSim + rSim;
			if (tSim < 1) {
				tSim += frac + score[3];
			}

			Object[] row = { num, assoc, rank, Maths.round(tSim, 6), Maths.round(lSim, 6), Maths.round(rSim, 6),
					Maths.round(frac, 6) };
			rows.add(row);
			num++;
		}
		rowData = rows.toArray(new Object[rows.size()][]);
		fireTableRowsInserted(0, rowData.length - 1);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.RIGHT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(1).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		setComparator(sorter, NumComp, 0, 2, 3, 4, 5, 6);

		TableColumnModel cols = table.getColumnModel();
		setColWidths(cols, colWidths);
	}

	public Context getContext(int row) {
		return matches.get(row);
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
