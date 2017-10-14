package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.google.common.collect.ArrayListMultimap;

import net.certiv.adept.Tool;
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.util.Kind;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.models.CorpusEdgeSetTableModel;
import net.certiv.adept.vis.models.CorpusEdgeTableModel;
import net.certiv.adept.vis.models.CorpusFeaturesTableModel;
import net.certiv.adept.vis.renderers.EdgeCellRenderer;
import net.certiv.adept.vis.renderers.EdgeSetCellRenderer;
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
	private JSplitPane centPane;
	private JTable fsTable;
	private JTable fxTable;
	private JTable egTable;

	private ProcessMgr mgr;
	private ISourceParser lang;
	private ArrayListMultimap<Integer, Feature> typeIndex;
	private int typeKey;

	public CorpusView() {
		super("CorpusDocs Features Analysis", "features.gif");

		// ---
		// left

		fsTable = createTable();
		fsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = fsTable.getSelectedRow();
				if (row >= 0) {
					CorpusFeaturesTableModel m = (CorpusFeaturesTableModel) fsTable.getModel();
					typeKey = (int) m.getValueAt(row, 2);
					String kind = (String) m.getValueAt(row, 1);
					if (kind.equals(Kind.RULE.toString())) {
						typeKey = typeKey << 32;
					}
					createDependentData(typeKey);
					clearEdgeData();
				}
			}
		});

		JScrollPane fsScroll = createScrollPane(fsTable);
		JPanel fsPanel = createPanel("Feature Set");
		fsPanel.add(fsScroll);

		// ---
		// center

		fxTable = createTable();
		fxTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int row = fxTable.getSelectedRow();
				if (row >= 0) {
					CorpusEdgeSetTableModel m = (CorpusEdgeSetTableModel) fxTable.getModel();
					int index = (int) m.getValueAt(row, 0);
					createEdgeData(index - 1);
				}
			}
		});

		JScrollPane fxScroll = createScrollPane(fxTable);
		JPanel fxPanel = createPanel("Edge Set");
		fxPanel.add(fxScroll);

		// ---
		// right

		egTable = createTable();
		JScrollPane egScroll = createScrollPane(egTable);
		JPanel egPanel = createPanel("Edge");
		egPanel.add(egScroll);

		// ---

		mainPane = createSplitPane(0.33);
		centPane = createSplitPane(0.5);

		mainPane.setLeftComponent(fsPanel);
		mainPane.setRightComponent(centPane);
		centPane.setLeftComponent(fxPanel);
		centPane.setRightComponent(egPanel);

		content.add(mainPane, BorderLayout.CENTER);

		setLocation();
		int pos = prefs.getInt(qual + KEY_SPLIT_HORZ, 300);
		mainPane.setDividerLocation(pos);
		pos = prefs.getInt(qual + KEY_SPLIT_HORZ1, 300);
		centPane.setDividerLocation(pos);

		frame.setVisible(true);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(qual + KEY_SPLIT_HORZ, mainPane.getDividerLocation());
		prefs.putInt(qual + KEY_SPLIT_HORZ1, centPane.getDividerLocation());
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

		mgr = tool.getMgr();
		lang = mgr.getLanguageParser();
		typeIndex = mgr.getCorpusModel().getFeatureIndex();

		// feature type fsTable
		CorpusFeaturesTableModel model = new CorpusFeaturesTableModel(typeIndex, lang.getRuleNames(),
				lang.getTokenNames());
		fsTable.setModel(model);

		fsTable.setDefaultRenderer(Object.class, new FeaturesCellRenderer(model, SwingConstants.LEFT));
		fsTable.getColumnModel().getColumn(0).setCellRenderer(new FeaturesCellRenderer(model, SwingConstants.CENTER));
		fsTable.getColumnModel().getColumn(2).setCellRenderer(new FeaturesCellRenderer(model, SwingConstants.RIGHT));
		fsTable.getColumnModel().getColumn(4).setCellRenderer(new FeaturesCellRenderer(model, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(fsTable.getModel());
		fsTable.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(2, NumComp);
		sorter.setComparator(4, NumComp);

		TableColumnModel cols = fsTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(3).setPreferredWidth(60);
		cols.getColumn(4).setPreferredWidth(10);
	}

	protected void createDependentData(int typeKey) {
		CorpusEdgeSetTableModel model = new CorpusEdgeSetTableModel(typeIndex, typeKey);
		fxTable.setModel(model);

		fxTable.setDefaultRenderer(Object.class, new EdgeSetCellRenderer(model, SwingConstants.LEFT));
		fxTable.getColumnModel().getColumn(0).setCellRenderer(new EdgeSetCellRenderer(model, SwingConstants.CENTER));
		fxTable.getColumnModel().getColumn(1).setCellRenderer(new EdgeSetCellRenderer(model, SwingConstants.RIGHT));
		fxTable.getColumnModel().getColumn(2).setCellRenderer(new EdgeSetCellRenderer(model, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		fxTable.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(2, NumComp);

		TableColumnModel cols = fxTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(20);
		cols.getColumn(2).setPreferredWidth(20);
	}

	protected void clearEdgeData() {
		TableModel model = egTable.getModel();
		if (model instanceof CorpusEdgeTableModel) {
			((CorpusEdgeTableModel) model).clear();
		}
	}

	protected void createEdgeData(int line) {
		CorpusEdgeTableModel model = new CorpusEdgeTableModel(mgr, typeIndex, lang, typeKey, line);
		egTable.setModel(model);

		egTable.setDefaultRenderer(Object.class, new EdgeCellRenderer(model, SwingConstants.LEFT));
		egTable.getColumnModel().getColumn(0).setCellRenderer(new EdgeCellRenderer(model, SwingConstants.CENTER));
		egTable.getColumnModel().getColumn(3).setCellRenderer(new EdgeCellRenderer(model, SwingConstants.RIGHT));
		egTable.getColumnModel().getColumn(6).setCellRenderer(new EdgeCellRenderer(model, SwingConstants.RIGHT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		egTable.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(3, NumComp);
		sorter.setComparator(6, NumComp);

		TableColumnModel cols = egTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(50);
		cols.getColumn(2).setPreferredWidth(50);
		cols.getColumn(3).setPreferredWidth(10);
		cols.getColumn(4).setPreferredWidth(80);
		cols.getColumn(5).setPreferredWidth(50);
		cols.getColumn(6).setPreferredWidth(70);
	}
}
