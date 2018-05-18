/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Log;
import net.certiv.adept.vis.models.CorpusRefsTableModel;
import net.certiv.adept.vis.models.FeatureTableModel;
import net.certiv.adept.vis.panels.AbstractViewBase;

/**
 * View the combined set of features present in the corpus documents.
 */
public class CorpusView extends AbstractViewBase {

	private static final String corpusRoot = "../net.certiv.adept/corpus";

	private CoreMgr mgr;
	private ISourceParser lang;

	private JTable featTable;
	private JTable refsTable;
	private JSplitPane split;

	private List<Feature> features;
	private boolean filtered;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		CorpusView view = new CorpusView();
		view.createFeaturesData();
	}

	public CorpusView() {
		super("Corpus Feature Analysis", "features.gif");

		split = createSplitPane(VERT);
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

	private void createFeatureTable() {
		featTable = createTable();
		JPanel panel = createScrollTable("Feature Set", featTable);
		featTable.addMouseListener(new MouseAdapter() {

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
		features = mgr.getCorpusModel().getCorpusFeatures();

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

		List<Feature> features = this.features;
		if (type > 0) {
			features = features.stream().filter(f -> f.getType() == type).collect(Collectors.toList());
		}
		model.addAll(features);
	}

}
