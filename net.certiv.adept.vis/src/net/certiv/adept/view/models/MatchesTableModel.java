package net.certiv.adept.view.models;

import java.util.ArrayList;
import java.util.Collections;
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
import net.certiv.adept.util.TreeMultimap;
import net.certiv.adept.view.renderers.AlignCellRenderer;

public class MatchesTableModel extends BaseTableModel {

	private final String[] columnNames = { "Order", "Similarity", "Left", "Token", "Right", "Weight" };

	private Object[][] rowData;
	private Map<Integer, Feature> index = new HashMap<>();

	public MatchesTableModel(TreeMultimap<Double, Feature> matches, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		List<Object[]> rows = new ArrayList<>();
		int num = 0;

		List<Double> sims = new ArrayList<>(matches.keySet());
		Collections.reverse(sims);
		for (Double sim : sims) {
			for (Feature feature : matches.get(sim)) {
				int line = num + 1;

				String left = evalSide(feature.getSpacingLeft(), feature.getWsLeft(), feature.getTokensLeft(),
						Bias.LEFT);
				String token = evalTokenText(feature.getType(), feature.getText());
				String right = evalSide(feature.getSpacingRight(), feature.getWsRight(), feature.getTokensRight(),
						Bias.RIGHT);
				int weight = feature.getWeight();

				Object[] row = { line, sim, left, token, right, weight };

				rows.add(row);
				index.put(num, feature);
				num++;
			}
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
	}

	public void configCols(JTable table) {
		table.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		table.getColumnModel().getColumn(2).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(2, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(3).setPreferredWidth(300);
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
