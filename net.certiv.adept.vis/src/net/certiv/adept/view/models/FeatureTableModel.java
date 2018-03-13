package net.certiv.adept.view.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.antlr.v4.runtime.misc.Utils;

import net.certiv.adept.model.Feature;
import net.certiv.adept.view.renderers.FeaturesCellRenderer;

public class FeatureTableModel extends BaseTableModel {

	private final String[] columnNames = { "Line", "Feature", "Kind", "Ancestors", "Token", "Text", "Bias",
			"SpacingLeft", "TokensLeft", "WsLeft", "SpacingRight", "TokensRight", "WsRight", "Weight" };

	private List<Feature> features;
	private Object[][] rowData;

	public FeatureTableModel(List<Feature> features, List<String> ruleNames, List<String> tokenNames) {
		super(ruleNames, tokenNames);

		addAll(features);
	}

	public void addAll(List<Feature> features) {
		this.features = features;
		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Feature feature : features) {
			int id = feature.getId();
			String kind = feature.getKind().toString();
			String ancestors = evalAncestors(feature.getAncestors());
			String name = String.format("%s  (%s)", feature.getNodeName(), feature.getType());
			String text = feature.getText();
			String bias = feature.getBias().toString();
			String spLeft = feature.getSpacingLeft().toString();
			String spRight = feature.getSpacingRight().toString();
			String tokLeft = evalTokens(feature.getTokensLeft(), true);
			String tokRight = evalTokens(feature.getTokensRight(), true);
			String wsLeft = Utils.escapeWhitespace(feature.getWsLeft(), true);
			String wsRight = Utils.escapeWhitespace(feature.getWsRight(), true);
			int weight = feature.getWeight();

			Object[] row = { line, id, kind, ancestors, name, text, bias, spLeft, tokLeft, wsLeft, spRight, tokRight,
					wsRight, weight };
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
		table.setDefaultRenderer(Object.class, new FeaturesCellRenderer(this, SwingConstants.LEFT));
		table.getColumnModel().getColumn(0).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.CENTER));
		table.getColumnModel().getColumn(1).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.RIGHT));
		table.getColumnModel().getColumn(13).setCellRenderer(new FeaturesCellRenderer(this, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(13, NumComp);

		TableColumnModel cols = table.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(10);
		cols.getColumn(2).setPreferredWidth(20);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(20);
		cols.getColumn(5).setPreferredWidth(20);
		cols.getColumn(6).setPreferredWidth(10);
		cols.getColumn(7).setPreferredWidth(10);
		cols.getColumn(8).setPreferredWidth(30);
		cols.getColumn(9).setPreferredWidth(20);
		cols.getColumn(10).setPreferredWidth(30);
		cols.getColumn(11).setPreferredWidth(20);
		cols.getColumn(12).setPreferredWidth(30);
		cols.getColumn(13).setPreferredWidth(10);
	}

	public Feature getFeature(int row) {
		return features.get(row);
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
