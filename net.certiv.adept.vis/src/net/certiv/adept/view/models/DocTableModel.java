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

import net.certiv.adept.model.Bias;
import net.certiv.adept.model.Feature;
import net.certiv.adept.view.renderers.AlignCellRenderer;
import net.certiv.adept.view.utils.CollUtil;

public class DocTableModel extends BaseTableModel {

	private final String[] columnNames = { "Offset", "Line", "Col", "Left", "Token", "Right", "Weight" };

	private Object[][] rowData;
	private Map<Integer, Feature> index = new HashMap<>();

	public DocTableModel(List<Feature> features, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		CollUtil.sortLineCol(features);
		List<Object[]> rows = new ArrayList<>();
		int offset = 0;
		for (Feature feature : features) {
			int line = feature.getLine();
			int col = feature.getCol();
			String left = evalSide(feature.getSpacingLeft(), feature.getWsLeft(), feature.getTokensLeft(), Bias.LEFT);
			String token = evalTokenText(feature.getType(), feature.getText());
			String right = evalSide(feature.getSpacingRight(), feature.getWsRight(), feature.getTokensRight(),
					Bias.RIGHT);
			int weight = feature.getWeight();

			Object[] row = { offset, line, col, left, token, right, weight };
			rows.add(row);
			index.put(offset, feature);
			offset++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(1).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));
		table.getColumnModel().getColumn(2).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));
		table.getColumnModel().getColumn(6).setCellRenderer(new AlignCellRenderer(SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(2, NumComp);
		sorter.setComparator(6, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(20);
		cols.getColumn(1).setPreferredWidth(20);
		cols.getColumn(2).setPreferredWidth(20);
		cols.getColumn(3).setPreferredWidth(200);
		cols.getColumn(4).setPreferredWidth(150);
		cols.getColumn(5).setPreferredWidth(200);
		cols.getColumn(6).setPreferredWidth(20);
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
