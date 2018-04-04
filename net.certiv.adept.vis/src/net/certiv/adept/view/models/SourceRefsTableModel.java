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

import org.antlr.runtime.Token;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.view.renderers.AlignCellRenderer;

/** RefTokens for a source document. */
public class SourceRefsTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Ancestors", "Token", "Place", "Indents", "Associates", "Spacing",
			"Alignment", "Location", "Index" };

	private Object[][] rowData;
	private Map<Integer, RefToken> refIndex = new HashMap<>();

	public SourceRefsTableModel(ParseRecord data, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (AdeptToken token : data.tokenIndex.values()) {
			RefToken ref = token.refToken();
			if (ref.type > Token.EOF) {
				Feature feature = data.index.get(token);

				String ancestors = evalAncestors(feature.getAncestors());
				String tname = tText(ref.type, ref.text);
				String place = tPlace(ref);
				String dents = tIndent(ref);
				String assoc = tAssoc(ref.type, ref.contexts.get(0));
				String space = tSpace(ref);
				String align = tAlign(ref);
				String location = tLocation(ref);
				int tIndex = ref.index;

				Object[] row = { num, ancestors, tname, place, dents, assoc, space, align, location, tIndex };
				rows.add(row);
				refIndex.put(num, ref);
				num++;
			}
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(9).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(9, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(300);
		cols.getColumn(2).setPreferredWidth(150);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(80);
		cols.getColumn(5).setPreferredWidth(250);
		cols.getColumn(6).setPreferredWidth(250);
		cols.getColumn(7).setPreferredWidth(150);
		cols.getColumn(8).setPreferredWidth(60);
		cols.getColumn(9).setPreferredWidth(20);
	}

	public RefToken getRefToken(int row) {
		return refIndex.get(row);
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
