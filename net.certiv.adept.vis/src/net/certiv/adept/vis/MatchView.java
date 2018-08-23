/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis;

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
import java.nio.file.Path;
import java.nio.file.Paths;
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
import net.certiv.adept.lang.Record;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.models.ContextsTableModel;
import net.certiv.adept.vis.models.MatchRefsTableModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;
import net.certiv.adept.vis.models.SourceRefsTableModel;
import net.certiv.adept.vis.panels.AbstractViewBase;

public class MatchView extends AbstractViewBase {

	private static final String name = "MatchAnalysis";
	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private Tool tool;
	private Record data;

	private JComboBox<Item> srcBox;
	private JSplitPane split;
	private JSplitPane split2;

	private JTable srcTable;
	private SourceRefsTableModel srcModel;

	private JTable refTable;
	private MatchRefsTableModel refModel;

	private JTable ctxTable;
	private ContextsTableModel ctxModel;

	// current source selections
	private RefToken ref;
	private AdeptToken token;
	private Feature feature;
	private TreeMultiset<Double, RefToken> matches;
	private double[] maxRanks;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		MatchView view = new MatchView();
		view.loadTool();
	}

	public MatchView() {
		super(name, "features.gif");

		createSelectPanel();

		split = createSplitPane(VERT);
		content.add(split, BorderLayout.CENTER);
		createSourceTable();

		split2 = createSplitPane(VERT);
		split.setBottomComponent(split2);
		createMatchTable();

		applyPrefs();
		split.setDividerLocation(prefs.getInt(KEY_SPLIT_VERT, 300));
		split2.setDividerLocation(prefs.getInt(KEY_SPLIT_VERT + "2", 300));
		frame.setVisible(true);
	}

	// ---- Top: control panel ----
	private void createSelectPanel() {
		Path dir = Paths.get(rootDir, "antlr");
		SourceListModel model = new SourceListModel(dir, srcExt);
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
		srcTable = createTable();
		JPanel source = createScrollTable("Source RefTokens", srcTable);
		split.setTopComponent(source);
		srcTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (row < 0) return;

				row = srcTable.convertRowIndexToModel(srcTable.getSelectedRow());
				handleSrcSelect(row);
			}
		});
		srcTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int row = srcTable.convertRowIndexToModel(srcTable.getSelectedRow());
				handleSrcSelect(row);
			}

		});

	}

	// ---- Bottom: matched table ----
	private void createMatchTable() {
		refTable = createTable();
		JPanel refs = createScrollTable("Matched Corpus RefTokens", refTable);
		split2.setTopComponent(refs);
		refTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (row < 0) return;

				row = refTable.convertRowIndexToModel(refTable.getSelectedRow());
				handleMatSelect(row);
			}
		});
		refTable.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				int row = refTable.convertRowIndexToModel(refTable.getSelectedRow());
				handleMatSelect(row);
			}
		});

		ctxTable = createTable();
		JPanel contexts = createScrollTable("Selected Corpus RefToken Contexts", ctxTable);
		split2.setBottomComponent(contexts);
	}

	@Override
	protected void saveCustomPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_VERT, split.getDividerLocation());
		prefs.putInt(KEY_SPLIT_VERT + "2", split2.getDividerLocation());
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
			data = tool.getMgr().getDocModel().getParseRecord();
			installModels();
			createSrcRefsList();
		}
	}

	protected void installModels() {
		srcModel = new SourceRefsTableModel(data.getRuleNames(), data.getTokenNames());
		srcTable.setModel(srcModel);
		srcModel.configCols(srcTable);

		refModel = new MatchRefsTableModel(data.getRuleNames(), data.getTokenNames());
		refTable.setModel(refModel);
		refModel.configCols(refTable);

		ctxModel = new ContextsTableModel(data.getRuleNames(), data.getTokenNames());
		ctxTable.setModel(ctxModel);
		ctxModel.configCols(ctxTable);
	}

	// after combo selected document has been parsed
	// display table of all formattable tokens, nominally ordered by token index
	protected void createSrcRefsList() {
		srcModel.addAll(data);
		srcTable.changeSelection(0, 0, false, false);
		handleSrcSelect(0);
	}

	// clicked on the document feature table
	protected void handleSrcSelect(int row) {
		ref = srcModel.getRefToken(row);
		token = data.tokenIndex.get(ref.index);
		feature = data.index.get(token);
		matches = tool.getMgr().getMatches(feature, ref);
		maxRanks = tool.getMgr().getMatchingFeature(feature).maxRank();

		if (matches.isEmpty()) {
			refModel.removeAllRows();
			ctxModel.removeAllRows();
		} else {
			refModel.addAll(ref, matches, maxRanks);
			refTable.scrollRectToVisible(refTable.getCellRect(0, 0, true));
			handleMatSelect(0);
		}
	}

	// clicked on the matched ref token table
	protected void handleMatSelect(int row) {
		RefToken matRef = refModel.getRef(row);
		ctxModel.addAll(ref, matRef, maxRanks);
		ctxTable.scrollRectToVisible(ctxTable.getCellRect(0, 0, true));
	}
}
