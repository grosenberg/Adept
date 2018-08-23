/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.format.TextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.Record;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Maths;
import net.certiv.adept.util.Strings;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.panels.AbstractViewBase;
import net.certiv.adept.vis.panels.FormatContentPanel;
import net.certiv.adept.vis.panels.FormatInfoPanel;
import net.certiv.adept.vis.panels.FormatSelectPanel;
import net.certiv.adept.vis.utils.Point;

public class FormatView extends AbstractViewBase {

	public static final String name = "FormatView";
	public static final String corpusRoot = "../net.certiv.adept/corpus";
	public static final String rootDir = "../net.certiv.adept.test/test.snippets";
	public static final String srcExt = ".g4";

	private static final String KEY_FMT_CODE = "format_code";
	private static final String KEY_FMT_COMMENTS = "format_comments";
	private static final String KEY_FMT_HEADER = "format_header";
	private static final String KEY_ALIGN_FIELDS = "align_fields";
	private static final String KEY_ALIGN_COMMENTS = "align_comments";

	private Tool tool;
	private CoreMgr mgr;
	private Document doc;
	private String pathname;
	private String sourceContent;
	private boolean enabled;

	private FormatSelectPanel selectPanel;
	private FormatContentPanel formatPanel;
	private FormatInfoPanel info;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		new FormatView();

	}

	public FormatView() {
		super(name, "format.png");

		createSelectPanel();
		createFormatPanel();
		createInfoPanel();

		applyPrefs();
		frame.setVisible(true);

		String lang = (String) selectPanel.langBox.getSelectedItem();
		loadTool(lang);
	}

	private void createSelectPanel() {
		selectPanel = new FormatSelectPanel(this);
		content.add(selectPanel, BorderLayout.NORTH);
	}

	private void createFormatPanel() {
		formatPanel = new FormatContentPanel(800, 400, selectPanel.fontBox, selectPanel.sizeBox, selectPanel.tabBox,
				"Source", "Formatted");
		formatPanel.addPropertyChangeListener(FormatContentPanel.CLICK_LEFT, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				Point loc = (Point) e.getNewValue();
				if (loc != null) selectFeatureData(loc.getLine(), loc.getCol());
			}
		});

		content.add(formatPanel, BorderLayout.CENTER);
	}

	private void createInfoPanel() {
		JPanel infoPanel = createPanel();
		info = new FormatInfoPanel(this);
		infoPanel.add(info);

		content.add(infoPanel, BorderLayout.SOUTH);
	}

	@Override
	protected void saveCustomPrefs(Preferences prefs) {
		Font font = (Font) selectPanel.fontBox.getSelectedItem();
		prefs.put(KEY_FONT_NAME, font.getName());
		prefs.putInt(KEY_FONT_SIZE, (int) selectPanel.sizeBox.getSelectedItem());
		prefs.putInt(KEY_TAB_WIDTH, (int) selectPanel.tabBox.getSelectedItem());
		prefs.putBoolean(KEY_FMT_CODE, selectPanel.chkFormatCode.isSelected());
		prefs.putBoolean(KEY_FMT_COMMENTS, selectPanel.chkFormatComments.isSelected());
		prefs.putBoolean(KEY_FMT_HEADER, selectPanel.chkFormatHeader.isSelected());
		prefs.putBoolean(KEY_ALIGN_FIELDS, selectPanel.chkAlignFields.isSelected());
		prefs.putBoolean(KEY_ALIGN_COMMENTS, selectPanel.chkAlignComments.isSelected());
	}

	@Override
	protected void applyCustomPrefs() {
		selectPanel.sizeBox.setSelectedItem(prefs.getInt(KEY_FONT_SIZE, 12));
		selectPanel.tabBox.setSelectedItem(prefs.getInt(KEY_TAB_WIDTH, 4));

		selectPanel.chkFormatCode.setSelected(prefs.getBoolean(KEY_FMT_CODE, false));
		selectPanel.chkFormatComments.setSelected(prefs.getBoolean(KEY_FMT_COMMENTS, false));
		selectPanel.chkFormatHeader.setSelected(prefs.getBoolean(KEY_FMT_HEADER, false));
		selectPanel.chkAlignFields.setSelected(prefs.getBoolean(KEY_ALIGN_FIELDS, false));
		selectPanel.chkAlignComments.setSelected(prefs.getBoolean(KEY_ALIGN_COMMENTS, false));
	}

	protected void selectFeatureData(int line, int col) {
		if (enabled) {
			Record data = mgr.getDocModel().getParseRecord();
			Integer start = data.lineStartIndex.get(line);
			if (start != null) {
				// get text up to start of pointed to character!
				int end = Math.max(start, start + col - 2);
				String text = data.charStream.getText(new Interval(start, end));
				int vcol = Strings.measureVisualWidth(text, mgr.getTabWidth());
				AdeptToken token = findToken(line, vcol);
				if (token != null && token.getType() != Token.EOF) {
					Feature feature = data.getFeature(token);
					RefToken ref = token.refToken();
					RefToken matched = ref.matched;

					TreeMultiset<Double, RefToken> matches = tool.getMgr().getMatches(feature, ref);
					double sim = Maths.round(matches.firstKey(), 6);

					TextEdit ledit = doc.getEditLeft(token.getTokenIndex());
					TextEdit redit = doc.getEditRight(token.getTokenIndex());

					info.loadData(feature, token, ref, matched, sim, ledit, redit);
					return;
				}
			}

			info.clearData();
		}
	}

	/** Returns the token on the given line (0..n-1) that overlaps the given visual column (0..n-1). */
	private AdeptToken findToken(int line, int vcol) {
		Record data = mgr.getDocModel().getParseRecord();
		List<AdeptToken> tokens = data.lineTokensIndex.get(line);
		if (tokens == null || tokens.isEmpty()) return null;
		if (tokens.size() == 1) return tokens.get(0);

		for (int idx = 0, len = tokens.size(); idx < len - 1; idx++) {
			AdeptToken token = tokens.get(idx);
			AdeptToken tnext = tokens.get(idx + 1);
			int beg = token.getVisPos();
			int end = tnext.getVisPos();

			if (idx == 0 && vcol < beg) return tokens.get(idx);
			if (beg <= vcol && vcol < end) return tokens.get(idx);
		}
		return tokens.get(tokens.size() - 1);
	}

	public void loadTool(String lang) {
		tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang(lang);
		tool.setTabWidth(4);
		tool.setRebuild(true);

		tool.setFormat(selectPanel.chkFormatCode.isSelected());
		tool.setFormatComments(selectPanel.chkFormatComments.isSelected());
		tool.setFormatHdrComment(selectPanel.chkFormatHeader.isSelected());
		tool.setFormatAlignFields(selectPanel.chkAlignFields.isSelected());
		tool.setFormatAlignComments(selectPanel.chkAlignComments.isSelected());

		tool.setRemoveCommentBlankLines(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
		} else {
			process();
		}
	}

	public void process() {
		SourceListModel model = (SourceListModel) selectPanel.srcBox.getModel();
		pathname = model.getSelectedPathname();
		sourceContent = loadContent(pathname);
		formatPanel.load(sourceContent, "");

		FormatWorker worker = new FormatWorker();
		worker.execute();
	}

	public void reprocess() {
		sourceContent = formatPanel.getContent();
		if (sourceContent == null || sourceContent.isEmpty()) {
			process();
			return;
		}

		formatPanel.load(sourceContent, "");
		FormatWorker worker = new FormatWorker();
		worker.setContent(sourceContent);
		worker.execute();
	}

	private String loadContent(String pathname) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(pathname));
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Log.error(this, "Failed to read source file" + pathname + ": " + e.getMessage());
		}
		return "";
	}

	private class FormatWorker extends SwingWorker<String, Object> {

		private String content = null;

		@Override
		protected String doInBackground() throws Exception {
			try {
				if (content == null) {
					tool.setSourceFiles(pathname);
				} else {
					tool.setSource(pathname, content);
				}
				tool.execute();
			} catch (Exception e) {
				Log.error(this, "Error in tool execution", e);
				throw e;
			}
			return null;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		protected void done() {
			displayResults();
		}
	}

	protected void displayResults() {
		enabled = false;
		mgr = tool.getMgr();
		doc = mgr.getDocModel().getDocument();

		formatPanel.setMgr(mgr);
		formatPanel.load(sourceContent, tool.getFormatted());
		info.loadPerfData(mgr);
		enabled = true;
	}
}
