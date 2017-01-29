package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.models.DocTableModel;
import net.certiv.adept.vis.models.MatchesTableModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.SrcItem;

public class MatchesView extends AbstractBase {

	private static final String KEY_SPLIT_HORZ = "frame_split_horz";
	private static final String name = "MatchAnalysis";
	private static final String rootDir = "D:/DevFiles/Eclipse/Adept/net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";
	private static final Comparator<Integer> intComparator = new Comparator<Integer>() {

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 < o2) return -1;
			if (o1 > o2) return 1;
			return 0;
		}
	};

	private Tool tool;
	private JComboBox<SrcItem> srcBox;
	private JSplitPane mainPane;
	private JTable fsTable;
	private JTable mxTable;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		MatchesView view = new MatchesView();
		view.loadTool();
	}

	public MatchesView() {
		super(name, "features.gif");

		SourceListModel model = new SourceListModel(rootDir, srcExt);
		srcBox = new JComboBox<>(model);
		srcBox.setSize(250, 32);
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				process();
			}
		});

		JPanel cntlPanel = new JPanel();
		cntlPanel.add(srcBox);

		fsTable = new JTable();
		fsTable.setFillsViewportHeight(true);
		fsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				createMatchData(fsTable.getSelectedRow());
			}
		});

		JScrollPane fsScroll = new JScrollPane(fsTable);

		mxTable = new JTable();
		mxTable.setFillsViewportHeight(true);

		JScrollPane mxScroll = new JScrollPane(mxTable);

		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPane.setLeftComponent(fsScroll);
		mainPane.setRightComponent(mxScroll);

		Dimension minimumSize = new Dimension(100, 100);
		fsScroll.setMinimumSize(minimumSize);
		mxScroll.setMinimumSize(minimumSize);
		mainPane.setDividerLocation(0.5);

		Container content = frame.getContentPane();
		content.add(cntlPanel, BorderLayout.NORTH);
		content.add(mainPane, BorderLayout.CENTER);

		setLocation();

		int split = prefs.getInt(KEY_SPLIT_HORZ, 300);
		mainPane.setDividerLocation(split);

		frame.setVisible(true);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_HORZ, mainPane.getDividerLocation());
	}

	private void loadTool() {
		tool = new Tool();
		tool.setCorpusRoot("../net.certiv.adept.core/corpus");
		tool.setLang("antlr");
		tool.setTabWidth(4);

		tool.setRebuild(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
		} else {
			process();
		}
	}

	private void process() {
		new Formatter().execute();
	}

	private class Formatter extends SwingWorker<String, Object> {

		@Override
		protected String doInBackground() throws Exception {
			SourceListModel model = (SourceListModel) srcBox.getModel();
			String pathname = model.getSelectedPathname();
			tool.setSourceFiles(pathname);
			tool.execute();
			return null;
		}

		@Override
		protected void done() {
			List<Feature> features = Tool.mgr.getDocModel().getFeatures();
			DocTableModel model = new DocTableModel(features);
			fsTable.setModel(model);

			fsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			mxTable.setRowSorter(sorter);
			sorter.setComparator(0, intComparator);
			sorter.setComparator(1, intComparator);
			sorter.setComparator(2, intComparator);

			TableColumnModel cols = fsTable.getColumnModel();
			cols.getColumn(0).setPreferredWidth(10);
			cols.getColumn(1).setPreferredWidth(10);
			cols.getColumn(2).setPreferredWidth(10);
			cols.getColumn(3).setPreferredWidth(60);
			cols.getColumn(4).setPreferredWidth(60);
			cols.getColumn(5).setPreferredWidth(300);
		}
	}

	protected void createMatchData(int row) {
		DocTableModel m = (DocTableModel) fsTable.getModel();
		Feature feature = m.getFeature(row);
		TreeMap<Double, Feature> matches = tool.getMgr().getMatchSet(feature);
		MatchesTableModel model = new MatchesTableModel(matches);
		mxTable.setModel(model);

		mxTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		mxTable.setRowSorter(sorter);
		sorter.setComparator(0, intComparator);
		sorter.setComparator(1, intComparator);

		TableColumnModel cols = mxTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(30);
		cols.getColumn(2).setPreferredWidth(60);
		cols.getColumn(3).setPreferredWidth(300);
	}
}
