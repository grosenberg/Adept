package net.certiv.adept.vis.models;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Edge;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.util.Location;

public class CorpusEdgeTableModel extends AbstractTableModel {

	private final String[] columnNames = { "Index", "Relation", "Leaf", "Type", "Location", "Text", "Metric" };
	private Object[][] rowData;

	public CorpusEdgeTableModel(ProcessMgr mgr, ArrayListMultimap<Integer, Feature> typeIndex, ISourceParser lang,
			int key, int idx) {

		List<Feature> features = typeIndex.get(key);
		Feature feature = features.get(idx);
		Collection<Edge> edges = feature.getEdgeSet().getEdges();

		List<Object[]> rows = new ArrayList<>();
		int line = 1;
		for (Edge edge : edges) {
			if (edge.root != null) {
				String filepath = mgr.getCorpusModel().getPathname(edge.leaf.getDocId());
				Path pathname = Paths.get(filepath);
				String docname = pathname.getFileName().toString();
				int dot = docname.lastIndexOf('.');
				if (dot > -1) {
					docname = docname.substring(0, dot);
				}
				Location loc = edge.leaf.getLocation();
				String coord = edge.coord.toString();

				String relation = edge.type.name();
				String leaf = edge.leaf.getAspect();
				int type = edge.leaf.getType();
				String lloc = String.format("%s @%s:%s %s", docname, loc.line, loc.col, coord);
				String lTxt = edge.leaf.getText();
				double metric = edge.coord.distance();

				Object[] row = { line, relation, leaf, type, lloc, lTxt, metric };
				rows.add(row);
			}
			line++;
		}
		this.rowData = rows.toArray(new Object[rows.size()][]);
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

	public void clear() {
		int old = getRowCount();
		if (old == 0) return;

		rowData = new Object[0][columnNames.length];
		fireTableRowsDeleted(0, old - 1);
	}
}
