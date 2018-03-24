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

import net.certiv.adept.model.Feature;
import net.certiv.adept.view.renderers.AlignCellRenderer;
import net.certiv.adept.view.utils.CollUtil;

public class DocTableModel extends BaseTableModel {

	private final String[] columnNames = { "Num", "Ancestors", "Token", "Spacing", "Alignment", "Text", "Location",
			"Feature Id" };

	private Object[][] rowData;
	private Map<Integer, Feature> index = new HashMap<>();

	public DocTableModel(List<Feature> features, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		CollUtil.sortLineCol(features);
		List<Object[]> rows = new ArrayList<>();
		int num = 0;
		for (Feature feature : features) {
			String ancestors = evalAncestors(feature.getAncestors());
			String token = fType(feature.getType());
			String space = tSpace(feature.getRef(0));
			String align = tAlign(feature.getRef(0));
			String text = feature.getText();
			String location = tLocation(feature.getRef(0));

			int id = feature.getId();

			Object[] row = { num, ancestors, token, space, align, text, location, id };
			rows.add(row);
			index.put(num, feature);
			num++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(7).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(7, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(200);
		cols.getColumn(2).setPreferredWidth(80);
		cols.getColumn(3).setPreferredWidth(150);
		cols.getColumn(4).setPreferredWidth(150);
		cols.getColumn(5).setPreferredWidth(100);
		cols.getColumn(6).setPreferredWidth(80);
		cols.getColumn(7).setPreferredWidth(20);
	}

	public Feature getFeature(int row) {
		return index.get(row);
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
