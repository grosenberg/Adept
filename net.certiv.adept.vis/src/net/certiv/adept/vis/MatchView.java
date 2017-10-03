package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
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
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ISourceParser;
import net.certiv.adept.model.util.Chunk;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.TreeMultimap;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.FeaturePanel;
import net.certiv.adept.vis.components.MatchPanel;
import net.certiv.adept.vis.models.DocTableModel;
import net.certiv.adept.vis.models.MatchesTableModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.SrcItem;

public class MatchView extends AbstractBase {

	private static final String KEY_SPLIT_HORZ = "frame_split_horz";
	private static final String name = "MatchAnalysis";
	private static final String corpusRoot = "../net.certiv.adept.core/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private Tool tool;
	private JComboBox<SrcItem> srcBox;
	private JSplitPane mainPanel;
	private JTable featTable;
	private JTable matchTable;
	private MatchPanel matchPanel;
	private FeaturePanel featurePanel;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		MatchView view = new MatchView();
		view.loadTool();
	}

	public MatchView() {
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

		JPanel cntlPanel = createPanel("Features");
		cntlPanel.add(srcBox);

		// ----

		JPanel dataPanel = new JPanel(new GridLayout(1, 2));
		featurePanel = new FeaturePanel(null);
		matchPanel = new MatchPanel(null);
		dataPanel.add(featurePanel);
		dataPanel.add(matchPanel);

		// ----

		featTable = new JTable();
		featTable.setFillsViewportHeight(true);
		featTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				createMatchList(featTable.getSelectedRow());
			}
		});

		JScrollPane featScroll = new JScrollPane(featTable);

		// ----

		matchTable = new JTable();
		matchTable.setFillsViewportHeight(true);
		matchTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				createMatchData(featTable.getSelectedRow(), matchTable.getSelectedRow());
			}
		});

		JScrollPane matchScroll = new JScrollPane(matchTable);

		// ----

		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setLeftComponent(featScroll);
		mainPanel.setRightComponent(matchScroll);

		Dimension minimumSize = new Dimension(100, 100);
		featScroll.setMinimumSize(minimumSize);
		matchScroll.setMinimumSize(minimumSize);
		mainPanel.setDividerLocation(0.5);

		Container content = frame.getContentPane();
		content.add(cntlPanel, BorderLayout.NORTH);
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(dataPanel, BorderLayout.SOUTH);

		setLocation();

		int split = prefs.getInt(KEY_SPLIT_HORZ, 300);
		mainPanel.setDividerLocation(split);

		frame.setVisible(true);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_HORZ, mainPanel.getDividerLocation());
	}

	private void loadTool() {
		tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
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

	// selected target source changed
	private void process() {
		new Matcher().execute();
		featurePanel.clear();
		matchPanel.clear();
	}

	private class Matcher extends SwingWorker<String, Object> {

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
			ProcessMgr mgr = tool.getMgr();
			List<Feature> features = mgr.getDocModel().getFeatures();
			DocTableModel model = new DocTableModel(features);
			featTable.setModel(model);

			featTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			matchTable.setRowSorter(sorter);
			sorter.setComparator(0, NumComp);
			sorter.setComparator(1, NumComp);
			sorter.setComparator(2, NumComp);

			TableColumnModel cols = featTable.getColumnModel();
			cols.getColumn(0).setPreferredWidth(10);
			cols.getColumn(1).setPreferredWidth(10);
			cols.getColumn(2).setPreferredWidth(10);
			cols.getColumn(3).setPreferredWidth(60);
			cols.getColumn(4).setPreferredWidth(60);
			cols.getColumn(5).setPreferredWidth(300);
		}
	}

	// clicked on the target feature table
	protected void createMatchList(int row) {
		DocTableModel m = (DocTableModel) featTable.getModel();
		Feature feature = m.getFeature(row);
		TreeMultimap<Double, Feature> matches = tool.getMgr().getMatchSet(feature);
		Chunk.eval(matches);
		Map<Double, Integer> indices = Chunk.partitionIndices();
		MatchesTableModel model = new MatchesTableModel(matches, indices);
		matchTable.setModel(model);

		matchTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		matchTable.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(2, NumComp);

		TableColumnModel cols = matchTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(30);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(3).setPreferredWidth(150);
		cols.getColumn(4).setPreferredWidth(200);

		matchTable.scrollRectToVisible(matchTable.getCellRect(0, 0, true));

		Document doc = tool.getMgr().getDocModel().getDocument();
		featurePanel.load(doc, feature);
		matchPanel.clear();
	}

	protected void createMatchData(int featureRow, int matchRow) {
		DocTableModel f = (DocTableModel) featTable.getModel();
		Feature feature = f.getFeature(featureRow);
		TableModel mod = matchTable.getModel();
		if (mod instanceof MatchesTableModel) {
			MatchesTableModel m = (MatchesTableModel) mod;
			Feature matched = m.getFeature(matchRow);
			ISourceParser lang = tool.getMgr().getLanguageParser();
			matchPanel.load(feature, lang, matched);
		}
	}
}
