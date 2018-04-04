package net.certiv.adept.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.view.components.AbstractViewBase;
import net.certiv.adept.view.models.MatchesTableModel;
import net.certiv.adept.view.models.SourceListModel;
import net.certiv.adept.view.models.SourceListModel.Item;
import net.certiv.adept.view.models.SourceRefsTableModel;

public class MatchView extends AbstractViewBase {

	private static final String name = "MatchAnalysis";
	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private Tool tool;
	private JComboBox<Item> srcBox;

	private JSplitPane split;
	private JTable sourceTable;
	private JTable matchTable;
	private ParseRecord data;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		MatchView view = new MatchView();
		view.loadTool();
	}

	public MatchView() {
		super(name, "features.gif");

		split = createSplitPane(VERT);
		content.add(split, BorderLayout.CENTER);

		createSelectPanel();
		createSourceTable();
		createMatchTable();

		setLocation();
		split.setDividerLocation(prefs.getInt(KEY_SPLIT_VERT, 300));
		frame.setVisible(true);
	}

	// ---- Top: control panel ----
	private void createSelectPanel() {
		SourceListModel model = new SourceListModel(rootDir, srcExt);
		srcBox = new JComboBox<>(model);
		srcBox.setMinimumSize(new Dimension(300, 0));
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				process();
			}
		});

		JPanel select = createPanel("Source");
		select.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
		select.add(createLabel("Document: ", 300, SwingConstants.RIGHT));
		select.add(srcBox);

		content.add(select, BorderLayout.NORTH);
	}

	// ---- Top: source table ----
	private void createSourceTable() {
		sourceTable = createTable();
		JPanel source = createScrollTable("Source RefTokens", sourceTable);
		split.setTopComponent(source);
		sourceTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (row < 0) return;

				row = sourceTable.convertRowIndexToModel(sourceTable.getSelectedRow());
				handleSrcRefSelect(row);
			}
		});
		sourceTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int row = sourceTable.convertRowIndexToModel(sourceTable.getSelectedRow());
				handleSrcRefSelect(row);
			}

		});

	}

	// ---- Bottom: matched table ----
	private void createMatchTable() {
		matchTable = createTable();
		JPanel match = createScrollTable("Matched RefTokens", matchTable);
		split.setBottomComponent(match);

		// matchTable.addMouseListener(new MouseAdapter() {
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		// handleMatchSelect(sourceTable.getSelectedRow(), matchTable.getSelectedRow());
		// }
		// });
		// matchTable.addKeyListener(new KeyAdapter() {
		//
		// @Override
		// public void keyReleased(KeyEvent e) {
		// handleMatchSelect(sourceTable.getSelectedRow(), matchTable.getSelectedRow());
		// }
		//
		// });
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_HORZ, split.getDividerLocation());
	}

	private void loadTool() {
		tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang("antlr");
		tool.setTabWidth(4);
		tool.setRebuild(true);
		tool.setFormat(false);

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
			createSrcRefsList();
		}
	}

	// after combo selected document has been parsed
	// display table of all formattable tokens, nominally ordered by token index
	protected void createSrcRefsList() {
		data = tool.getMgr().getDocModel().getParseRecord();

		SourceRefsTableModel model = new SourceRefsTableModel(data, data.getRuleNames(), data.getTokenNames());
		sourceTable.setModel(model);
		model.configCols(sourceTable);
		sourceTable.changeSelection(0, 0, false, false);
		handleSrcRefSelect(0);
	}

	// clicked on the document feature table
	protected void handleSrcRefSelect(int row) {
		SourceRefsTableModel srcModel = (SourceRefsTableModel) sourceTable.getModel();
		RefToken ref = srcModel.getRefToken(row);
		AdeptToken token = data.tokenIndex.get(ref.index);
		Feature feature = data.index.get(token);
		TreeMultiset<Double, RefToken> matches = tool.getMgr().getMatches(feature, ref);

		if (!(matchTable.getModel() instanceof MatchesTableModel)) {
			MatchesTableModel matModel = new MatchesTableModel(data.getRuleNames(), data.getTokenNames());
			matchTable.setModel(matModel);
			matModel.configCols(matchTable);
		}

		MatchesTableModel matModel = (MatchesTableModel) matchTable.getModel();
		if (matches.isEmpty()) {
			matModel.removeAllRows();
		} else {
			matModel.addAll(ref, matches);
		}

		matchTable.scrollRectToVisible(matchTable.getCellRect(0, 0, true));
	}

	// // ---- Bottom: info panels ----
	// private void createInfoTable() {
	//
	// JSplitPane splitPane = createSplitPane(VERT);
	//
	// JPanel docPanel = createPanel("Document RefToken");
	// JPanel matchPanel = createPanel("Matched RefTokens");
	// infoPanel.add(docPanel);
	// infoPanel.add(matchPanel);
	//
	// docInfo = new FeaturePanel(null);
	// docPanel.add(docInfo);
	//
	// simInfo = new SimularityPanel(null);
	// matchInfo = new FeaturePanel(null);
	//
	// matchPanel.add(matchInfo, BorderLayout.NORTH);
	// matchPanel.add(simInfo, BorderLayout.CENTER);
	//
	// content.add(matchPanel, BorderLayout.SOUTH);
	// }
}
