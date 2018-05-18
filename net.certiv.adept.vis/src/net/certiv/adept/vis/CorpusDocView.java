/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.HashMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.models.CorpusRefsTableModel;
import net.certiv.adept.vis.models.FeatureTableModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;
import net.certiv.adept.vis.panels.AbstractViewBase;

/**
 * View the features present in any of the individual documents present within the set of corpus
 * documents.
 */
public class CorpusDocView extends AbstractViewBase {

	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept/corpus/antlr";
	private static final String srcExt = ".g4";

	private CoreMgr mgr;
	private ISourceParser lang;

	private int selectedSource;
	private JSplitPane split;
	private JTable featTable;
	private JTable refsTable;

	private HashMultilist<Integer, Feature> docFeatures;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		CorpusDocView view = new CorpusDocView();
		view.createFeatureData();
	}

	public CorpusDocView() {
		super("Corpus Documents Analysis", "features.gif");

		JPanel select = createSelectPanel();
		split = createSplitPane(VERT);
		content.add(select, BorderLayout.NORTH);
		content.add(split, BorderLayout.CENTER);

		createFeatureTable();
		createRefTokensTable();

		applyPrefs();
		split.setDividerLocation(prefs.getInt(KEY_SPLIT_HORZ, 300));
		frame.setVisible(true);
	}

	@Override
	protected void saveCustomPrefs(Preferences prefs) {
		prefs.putInt(KEY_SPLIT_VERT, split.getDividerLocation());
	}

	private JPanel createSelectPanel() {
		SourceListModel model = new SourceListModel(rootDir, srcExt);
		JComboBox<Item> srcBox = new JComboBox<>(model);
		srcBox.setMinimumSize(new Dimension(300, 0));
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSource = model.getIndexOfSelected();
				showUnfiltered();
			}
		});

		JPanel selectPanel = createPanel("Source");
		selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
		selectPanel.add(createLabel("Document: ", 300, SwingConstants.RIGHT));
		selectPanel.add(srcBox);
		return selectPanel;
	}

	private void createFeatureTable() {
		featTable = createTable();
		JPanel panel = createScrollTable("Feature Set", featTable);
		featTable.addMouseListener(new MouseAdapter() {

			private boolean filtered;

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = featTable.convertRowIndexToModel(featTable.getSelectedRow());
					showTokenRefs(row);
					if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
						if (!filtered) {
							filterToRow(row);
						} else {
							showUnfiltered();
						}
						filtered = !filtered;
					}
				}
			}
		});

		split.setLeftComponent(panel);
	}

	private void createRefTokensTable() {
		refsTable = createTable();
		JPanel panel = createScrollTable("Ref Tokens", refsTable);
		split.setRightComponent(panel);
	}

	protected void createFeatureData() {
		Tool tool = new Tool();
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
		docFeatures = mgr.getCorpusModel().getDocFeatures();
		Integer key = docFeatures.keyList().get(0);
		List<Feature> features = docFeatures.get(key);

		FeatureTableModel model = new FeatureTableModel(features, lang.getRuleNames(), lang.getTokenNames());
		featTable.setModel(model);
		model.configCols(featTable);
	}

	protected void showTokenRefs(int row) {
		FeatureTableModel model = (FeatureTableModel) featTable.getModel();
		Feature feature = model.getFeature(row);
		List<RefToken> refs = feature.getRefs();

		TableModel baseModel = refsTable.getModel();
		if (baseModel instanceof CorpusRefsTableModel) {
			CorpusRefsTableModel refsModel = (CorpusRefsTableModel) baseModel;
			refsModel.removeAllRows();
			if (refs.isEmpty()) {
				Log.error(this, "Has no refs: " + feature.toString());
				return;
			}
			refsModel.addAll(refs);

		} else {
			CorpusRefsTableModel refsModel = new CorpusRefsTableModel(refs, lang.getRuleNames(), lang.getTokenNames());
			refsTable.setModel(refsModel);
			refsModel.configCols(refsTable);
		}
	}

	protected void filterToRow(int row) {
		FeatureTableModel model = (FeatureTableModel) featTable.getModel();
		Feature feature = model.getFeature(row);
		int type = feature.getType();

		showFilteredFeatures(type);
	}

	protected void showUnfiltered() {
		showFilteredFeatures(-1);
	}

	protected void showFilteredFeatures(int type) {
		FeatureTableModel model = (FeatureTableModel) featTable.getModel();
		model.removeAllRows();

		Integer key = docFeatures.keyList().get(selectedSource);
		List<Feature> features = docFeatures.get(key);
		if (type > 0) {
			features = features.stream().filter(f -> f.getType() == type).collect(Collectors.toList());
		}
		model.addAll(features);
	}
}
