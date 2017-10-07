package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.certiv.adept.Tool;
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.TreeMultimap;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.FeaturePanel;
import net.certiv.adept.vis.components.StatPanel;
import net.certiv.adept.vis.models.DocTableModel;
import net.certiv.adept.vis.models.MatchesTableModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.SrcItem;
import net.certiv.adept.vis.renderers.AlignCellRenderer;

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
	private StatPanel matchedPanel;
	private FeaturePanel featurePanel1;
	private FeaturePanel featurePanel2;

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

		JPanel select = createPanel("Feature source");
		select.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		select.add(new JLabel("      Document: "));
		select.add(srcBox);

		// ----

		JPanel dataPanel = new JPanel(new GridLayout(1, 2));
		JPanel featureTitle = createPanel("Selected Document Feature");
		JPanel matchedTitle = createPanel("Selected Matched Feature");
		dataPanel.add(featureTitle);
		dataPanel.add(matchedTitle);

		featurePanel1 = new FeaturePanel(null);
		featureTitle.add(featurePanel1);

		matchedPanel = new StatPanel(null);
		featurePanel2 = new FeaturePanel(null);
		matchedTitle.add(featurePanel2, BorderLayout.NORTH);
		matchedTitle.add(matchedPanel, BorderLayout.CENTER);

		// ----

		featTable = createTable();
		featTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				createMatchList(featTable.getSelectedRow());
			}
		});

		JPanel features = createPanel("Feature Set");
		JScrollPane featScroll = createScrollPane(featTable);
		features.add(featScroll);

		// ----

		matchTable = createTable();
		matchTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				createMatchData(featTable.getSelectedRow(), matchTable.getSelectedRow());
			}
		});

		JPanel matches = createPanel("Matched Features");
		JScrollPane matchScroll = createScrollPane(matchTable);
		matches.add(matchScroll);

		// ----

		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setLeftComponent(features);
		mainPanel.setRightComponent(matches);

		Dimension minimumSize = new Dimension(100, 100);
		featScroll.setMinimumSize(minimumSize);
		matchScroll.setMinimumSize(minimumSize);
		mainPanel.setDividerLocation(0.5);

		Container content = frame.getContentPane();
		content.add(select, BorderLayout.NORTH);
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
		featurePanel1.clear();
		featurePanel2.clear();
		matchedPanel.clear();
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

			featTable.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
			featTable.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
			featTable.getColumnModel().getColumn(1).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
			featTable.getColumnModel().getColumn(2).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
			featTable.getColumnModel().getColumn(3).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));
			featTable.getColumnModel().getColumn(4).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));
			featTable.getColumnModel().getColumn(5).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			matchTable.setRowSorter(sorter);
			sorter.setComparator(0, NumComp);
			sorter.setComparator(1, NumComp);
			sorter.setComparator(2, NumComp);
			sorter.setComparator(3, NumComp);

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
		TreeMultimap<Double, Feature> matches = tool.getMgr().getMatches(feature);
		MatchesTableModel model = new MatchesTableModel(matches);
		matchTable.setModel(model);

		matchTable.setDefaultRenderer(Object.class, new AlignCellRenderer(SwingConstants.LEFT));
		matchTable.getColumnModel().getColumn(0).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		matchTable.getColumnModel().getColumn(1).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));
		matchTable.getColumnModel().getColumn(2).setCellRenderer(new AlignCellRenderer(SwingConstants.CENTER));
		matchTable.getColumnModel().getColumn(3).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));
		matchTable.getColumnModel().getColumn(4).setCellRenderer(new AlignCellRenderer(SwingConstants.LEFT));

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		matchTable.setRowSorter(sorter);
		sorter.setComparator(0, NumComp);
		sorter.setComparator(1, NumComp);
		sorter.setComparator(2, NumComp);

		TableColumnModel cols = matchTable.getColumnModel();
		cols.getColumn(0).setPreferredWidth(10);
		cols.getColumn(1).setPreferredWidth(60);
		cols.getColumn(2).setPreferredWidth(10);
		cols.getColumn(3).setPreferredWidth(140);
		cols.getColumn(4).setPreferredWidth(200);

		matchTable.scrollRectToVisible(matchTable.getCellRect(0, 0, true));

		Document doc = tool.getMgr().getDocModel().getDocument();
		featurePanel1.load(doc.getParseData(), doc.getPathname(), feature);
		featurePanel2.clear();
		matchedPanel.clear();
	}

	protected void createMatchData(int featureRow, int matchRow) {
		DocTableModel f = (DocTableModel) featTable.getModel();
		Feature feature = f.getFeature(featureRow);
		TableModel mod = matchTable.getModel();
		if (mod instanceof MatchesTableModel) {
			MatchesTableModel m = (MatchesTableModel) mod;
			Feature matched = m.getFeature(matchRow);
			ParseData data = tool.getMgr().getDocModel().getDocument().getParseData();
			String pathname = matched.getMgr().getCorpusModel().getPathname(matched.getDocId());
			featurePanel2.load(data, pathname, matched);
			matchedPanel.load(feature.getStats(matched));
		}
	}
}
