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

import org.antlr.runtime.Token;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.vis.renderers.AlignCellRenderer;

/** RefTokens for a source document. */
public class SourceRefsTableModel extends BaseTableModel {

	private static final int[] ColWidths = { 10, 150, 300, 60, 80, 250, 250, 150, 60, 20, };
	private final String[] columnNames = { "Num", "Token", "Ancestors", "Place", "Indents", "Associates", "Spacing",
			"Alignment", "Location", "Index" };

	private Object[][] rowData = new Object[0][];
	private Map<Integer, RefToken> refIndex = new HashMap<>();

	public SourceRefsTableModel(List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);
	}

	public void addAll(ParseRecord data) {
		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (AdeptToken token : data.tokenIndex.values()) {
			RefToken ref = token.refToken();
			if (ref.type > Token.EOF) {
				Feature feature = data.index.get(token);

				String tname = tText(ref.type, ref.text);
				String ancestors = evalAncestors(feature.getAncestors());
				String place = tPlace(ref);
				String dents = tIndent(ref);
				String assoc = tAssoc(ref.type, ref.contexts.get(0));
				String space = tSpace(ref);
				String align = tAlign(ref);
				String location = tLocation(token);
				int tIndex = ref.index;

				Object[] row = { num, tname, ancestors, place, dents, assoc, space, align, location, tIndex };
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
		setColWidths(cols, ColWidths);
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
