package net.certiv.adept.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.TreeMultimap;
import net.certiv.adept.view.components.AbstractBase;
import net.certiv.adept.view.components.FeaturePanel;
import net.certiv.adept.view.components.SimularityPanel;
import net.certiv.adept.view.models.DocTableModel;
import net.certiv.adept.view.models.MatchesTableModel;
import net.certiv.adept.view.models.SourceListModel;
import net.certiv.adept.view.models.SourceListModel.Item;

public class MatchView extends AbstractBase {

	private static final String KEY_SPLIT_HORZ = "frame_split_horz";
	private static final String name = "MatchAnalysis";
	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private JComboBox<Item> srcBox;
	private Tool tool;
	private JSplitPane mainPanel;
	private JTable docTable;
	private JTable matchTable;
	private SimularityPanel simInfo;
	private FeaturePanel docInfo;
	private FeaturePanel matchInfo;
	private List<String> ruleNames;
	private List<String> tokenNames;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}
		MatchView view = new MatchView();
		view.loadTool();
	}

	public MatchView() {
		super(name, "features.gif");

		// ---- Top: control panel ----

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
		select.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
		select.add(createLabel("Document: ", 300, SwingConstants.RIGHT));
		select.add(srcBox);

		// ---- Bottom: info panels ----

		JPanel infoPanel = new JPanel(new GridLayout(1, 2));
		JPanel docPanel = createPanel("Document Feature");
		JPanel matchPanel = createPanel("Matched Corpus Features");
		infoPanel.add(docPanel);
		infoPanel.add(matchPanel);

		docInfo = new FeaturePanel(null);
		docPanel.add(docInfo);

		simInfo = new SimularityPanel(null);
		matchInfo = new FeaturePanel(null);

		matchPanel.add(matchInfo, BorderLayout.NORTH);
		matchPanel.add(simInfo, BorderLayout.CENTER);

		// ---- Middle left: document feature table ----

		docTable = createTable();
		JPanel features = createScrollTable("Feature Set", docTable);
		docTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (row < 0) return;

				row = docTable.convertRowIndexToModel(docTable.getSelectedRow());
				handleFeatureSelect(row);
			}
		});
		docTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int row = docTable.convertRowIndexToModel(docTable.getSelectedRow());
				handleFeatureSelect(row);
			}

		});

		// ---- Middle right: matched feature table ----

		matchTable = createTable();
		JPanel matches = createScrollTable("Matched Features", matchTable);
		matchTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				handleMatchSelect(docTable.getSelectedRow(), matchTable.getSelectedRow());
			}
		});
		matchTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				handleMatchSelect(docTable.getSelectedRow(), matchTable.getSelectedRow());
			}

		});

		// ---- Container ----

		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setLeftComponent(features);
		mainPanel.setRightComponent(matches);
		mainPanel.setDividerLocation(0.5);

		Container container = frame.getContentPane();
		container.add(select, BorderLayout.NORTH);
		container.add(mainPanel, BorderLayout.CENTER);
		container.add(infoPanel, BorderLayout.SOUTH);

		setLocation();
		mainPanel.setDividerLocation(prefs.getInt(KEY_SPLIT_HORZ, 300));
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
		docInfo.clear();
		matchInfo.clear();
		simInfo.clear();

		new Matcher().execute();
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
			createDocumentList();
		}
	}

	// after combo selected document has been parsed
	protected void createDocumentList() {
		CoreMgr mgr = tool.getMgr();
		ruleNames = mgr.getLanguageParser().getRuleNames();
		tokenNames = mgr.getLanguageParser().getTokenNames();
		docInfo.setRuleNames(ruleNames, tokenNames);
		matchInfo.setRuleNames(ruleNames, tokenNames);

		List<Feature> features = mgr.getDocModel().getFeatures();
		DocTableModel model = new DocTableModel(features, ruleNames, tokenNames);
		docTable.setModel(model);
		model.configCols(docTable);
		docTable.changeSelection(0, 0, false, false);
		handleFeatureSelect(0);
	}

	// clicked on the document feature table
	protected void handleFeatureSelect(int row) {

		// get selected feature and update info
		DocTableModel docModel = (DocTableModel) docTable.getModel();
		Feature feature = docModel.getFeature(row);
		Document doc = tool.getMgr().getDocModel().getDocument();
		docInfo.load(doc.getParseData(), doc.getPathname(), feature);

		// find matched feature and update matched info
		TreeMultimap<Double, Feature> matches = tool.getMgr().getMatches(feature);
		MatchesTableModel matModel = new MatchesTableModel(matches, ruleNames, tokenNames);
		matchTable.setModel(matModel);
		matModel.configCols(matchTable);
		matchTable.scrollRectToVisible(matchTable.getCellRect(0, 0, true));
		matchTable.changeSelection(0, 0, false, false);
		handleMatchSelect(row, 0);
	}

	// clicked on the matched feature table
	protected void handleMatchSelect(int docRow, int matchRow) {
		MatchesTableModel matchModel = (MatchesTableModel) matchTable.getModel();
		Feature matched = matchModel.getFeature(matchRow);

		ParseRecord data = tool.getMgr().getDocModel().getDocument().getParseData();
		String pathname = matched.getMgr().getCorpusModel().getPathname(matched.getDocId());
		matchInfo.load(data, pathname, matched);
		// simInfo.load(feature.getStats(matched));
	}
}
