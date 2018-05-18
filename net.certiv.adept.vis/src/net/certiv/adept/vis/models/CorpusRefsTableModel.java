/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
import net.certiv.adept.unit.Pair;
import net.certiv.adept.vis.renderers.RefsCellRenderer;

/** RefTokens for a corpus document. */
public class CorpusRefsTableModel extends BaseTableModel {

	private final String[] columnNames = { "Line", "RefNum", "Token", "Place", "Indents", "AssocNum", "Associates",
			"Rank", "Spacing", "Alignment", "Rank" };

	private List<Pair<RefToken, Context>> expanded;
	private Object[][] rowData;

	public CorpusRefsTableModel(List<RefToken> refs, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		addAll(refs);
	}

	public void addAll(List<RefToken> refs) {
		this.expanded = new ArrayList<>();
		List<Object[]> rows = new ArrayList<>();
		int line = 1;

		int refNum = 0;
		for (RefToken ref : refs) {
			refNum++;
			int ctxNum = 0;
			for (Context context : ref.contexts) {
				ctxNum++;
				expanded.add(new Pair<>(ref, context));

				String tokName = tText(ref.type, ref.text);
				String place = tPlace(ref);
				String dents = tIndent(ref);
				String assoc = tAssoc(ref.type, context);
				int arank = context.rank;
				String space = tSpace(ref);
				String align = tAlign(ref);
				int rank = ref.rank;

				Object[] row = { line, refNum, tokName, place, dents, ctxNum, assoc, arank, space, align, rank };
				rows.add(row);
				line++;
			}
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
		table.getColumnModel().getColumn(1).setCellRenderer(new RefsCellRenderer(this, SwingConstants.CENTER));
		table.getColumnModel().getColumn(5).setCellRenderer(new RefsCellRenderer(this, SwingConstants.CENTER));
		table.getColumnModel().getColumn(7).setCellRenderer(new RefsCellRenderer(this, SwingConstants.RIGHT));
		table.getColumnModel().getColumn(10).setCellRenderer(new RefsCellRenderer(this, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(5, NumComp);
		sorter.setComparator(7, NumComp);
		sorter.setComparator(10, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(10);
		cols.getColumn(2).setPreferredWidth(60);
		cols.getColumn(3).setPreferredWidth(40);
		cols.getColumn(4).setPreferredWidth(80);
		cols.getColumn(5).setPreferredWidth(10);
		cols.getColumn(6).setPreferredWidth(300);
		cols.getColumn(7).setPreferredWidth(10);
		cols.getColumn(8).setPreferredWidth(300);
		cols.getColumn(9).setPreferredWidth(200);
		cols.getColumn(10).setPreferredWidth(20);
	}

	public Pair<RefToken, Context> getRefPair(int row) {
		return expanded.get(row);
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
