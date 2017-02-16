package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.models.CorpusFeatureTableModel;
import net.certiv.adept.vis.models.CorpusTableModel;
import net.certiv.adept.vis.models.EdgeTableModel;
import net.certiv.adept.vis.renderers.EdgeCellRenderer;
import net.certiv.adept.vis.renderers.FeatureCellRenderer;
import net.certiv.adept.vis.renderers.FeaturesCellRenderer;

public class CorpusView extends AbstractBase {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		CorpusView view = new CorpusView();
		view.createFeaturesData();
	}

	private static final String KEY_SPLIT_HORZ = "frame_split_horz";
	private static final String KEY_SPLIT_HORZ1 = "frame_split_horz1";
	private static final String corpusRoot = "../net.certiv.adept.core/corpus";

	private JSplitPane mainPane;
	private JSplitPane subPane;
	private JTable fsTable;
	private JTable fxTable;
	private JTable egTable;

	private ISourceParser lang;
	private ArrayListMultimap<Long, Feature> typeIndex;
	private long typeKey;

	private Comparator<Integer> intComparator = new Comparator<Integer>() {

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2) return -1;
			if (o1 > o2) return 1;
			return 0;
		}
	};

	public CorpusView() {
		super("Corpus Features Analysis", "features.gif");

		fsTable = new JTable();
		fsTable.setFillsViewportHeight(true);
		fsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = fsTable.getSelectedRow();
				if (row >= 0) {
					CorpusTableModel m = (CorpusTableModel) fsTable.getModel();
					typeKey = (long) m.getValueAt(row, 2);
					String kind = (String) m.getValueAt(row, 1);
					if (kind.equals(Kind.RULE.toString())) {
						typeKey = typeKey << 32;
					}
					createDependentData(typeKey);
					clearEdgeData();
				}
			}
		});

		JScrollPane fsScroll = new JScrollPane(fsTable);

		fxTable = new JTable();
		fxTable.setFillsViewportHeight(true);
		fxTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = fxTable.getSelectedRow();
				if (row >= 0) {
					CorpusFeatureTableModel m = (CorpusFeatureTableModel) fxTable.getModel();
					int index = (int) m.getValueAt(row, 0);
					createEdgeData(index - 1);
				}
			}
		});

		JScrollPane fxScroll = new JScrollPane(fxTable);

		egTable = new JTable();
		egTable.setFillsViewportHeight(true);
		JScrollPane egScroll = new JScrollPane(egTable);

		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		subPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		mainPane.setLeftComponent(fsScroll);
		mainPane.setRightComponent(subPane);
		subPane.setLeftComponent(fxScroll);
		subPane.setRightComponent(egScroll);

		Dimension minimumSize = new Dimension(100, 100);
		fsScroll.setMinimumSize(minimumSize);
		fxScroll.setMinimumSize(minimumSize);
		egScroll.setMinimumSize(minimumSize);
		mainPane.setDividerLocation(0.5);
		subPane.setDividerLocation(0.5);

		Container content = frame.getContentPane();
		content.add(mainPane, BorderLayout.CENTER);

		setLocation();

		int split = prefs.getInt(KEY_SPLIT_HORZ, 300);
		mainPane.setDividerLocation(split);
		split = prefs.getInt(KEY_SPLIT_HORZ1, 300);
		subPane.setDividerLocation(split);

		frame.setVisible(true);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_HORZ, mainPane.getDividerLocation());
		prefs.putInt(KEY_SPLIT_HORZ1, subPane.getDividerLocation());
	}

	protected void createFeaturesData() {
		Tool tool = new Tool();
		tool.setCheck(true);
		tool.setCorpusRoot(corpusRoot);
		tool.setLang("antlr");
		tool.setTabWidth(4);

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
			return;
		}

		lang = Tool.mgr.getLanguageParser();
		typeIndex = Tool.mgr.getCorpusModel().getFeatureIndex();

		// feature type fsTable
		CorpusTableModel model = new CorpusTableModel(typeIndex, lang.getRuleNames(), lang.getTokenNames());
		fsTable.setModel(model);

		fsTable.setDefaultRenderer(Object.class, new FeaturesCellRenderer(model));

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(fsTable.getModel());
		fsTable.setRowSorter(sorter);
		sorter.setComparator(0, intComparator);
		sorter.setComparator(2, intComparator);
		sorter.setComparator(4, intComparator);

		TableColumnModel cols = fsTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(10);
	}

	protected void createDependentData(long typeKey) {
		CorpusFeatureTableModel model = new CorpusFeatureTableModel(typeIndex, typeKey);
		fxTable.setModel(model);

		fxTable.setDefaultRenderer(Object.class, new FeatureCellRenderer(model));

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		fxTable.setRowSorter(sorter);
		sorter.setComparator(0, intComparator);
		sorter.setComparator(1, intComparator);
		sorter.setComparator(2, intComparator);

		TableColumnModel cols = fxTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(20);
		cols.getColumn(2).setPreferredWidth(20);
	}

	protected void clearEdgeData() {
		TableModel model = egTable.getModel();
		if (model instanceof EdgeTableModel) {
			((EdgeTableModel) model).clear();
		}
	}

	protected void createEdgeData(int line) {
		EdgeTableModel model = new EdgeTableModel(typeIndex, lang, typeKey, line);
		egTable.setModel(model);

		egTable.setDefaultRenderer(Object.class, new EdgeCellRenderer(model));

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		egTable.setRowSorter(sorter);
		sorter.setComparator(0, intComparator);
		sorter.setComparator(5, intComparator);

		TableColumnModel cols = egTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(50);
		cols.getColumn(2).setPreferredWidth(50);
		cols.getColumn(3).setPreferredWidth(50);
		cols.getColumn(4).setPreferredWidth(50);
		cols.getColumn(5).setPreferredWidth(10);
	}
}
