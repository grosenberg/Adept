package net.certiv.adept.vis;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Maths;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.Time;
import net.certiv.adept.vis.components.FontChoiceBox;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;
import net.certiv.adept.vis.panels.AbstractViewBase;
import net.certiv.adept.vis.panels.FormatContentPanel;
import net.certiv.adept.vis.panels.FormatInfoPanel;
import net.certiv.adept.vis.utils.Point;

public class FormatView extends AbstractViewBase {

	private static final String name = "FormatView";
	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";
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

	private JComboBox<Item> srcBox;
	private FontChoiceBox fontBox;
	private JComboBox<Integer> sizeBox;
	private JComboBox<Integer> tabBox;
	private FormatContentPanel formatPanel;
	private FormatInfoPanel info;
	private JCheckBox fmtCodeBox;
	private JCheckBox fmtBreakBox;
	private JCheckBox fmtHeaderBox;
	private JCheckBox fmtCommentsBox;
	private JCheckBox alignFieldsBox;
	private JCheckBox alignCommentsBox;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		FormatView view = new FormatView();
		view.loadTool();
	}

	public FormatView() {
		super(name, "format.png");

		createSelectPanel();
		createFormatPanel();
		createInfoPanel();

		applyPrefs();
		frame.setVisible(true);

		Integer fontsize = prefs.getInt(KEY_FONT_SIZE, 12);
		sizeBox.setSelectedItem(fontsize);

		Integer width = prefs.getInt(KEY_TAB_WIDTH, 4);
		tabBox.setSelectedItem(width);
	}

	private void createSelectPanel() {
		ActionListener run = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Time.clear();
				if (e.getSource() instanceof JCheckBox) {
					loadTool();
				} else {
					process();
				}
			}
		};

		JPanel selectPanel = createPanel("Source");
		selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));

		srcBox = new JComboBox<>(new SourceListModel(rootDir, srcExt));
		srcBox.setSize(300, 32);
		srcBox.addActionListener(run);
		selectPanel.add(new JLabel("Document: "));
		selectPanel.add(srcBox);

		// "Droid Sans Mono", "DejaVu Sans Mono", "Oxygen Mono", "NanumGothicCoding"
		String fontname = prefs.get(KEY_FONT_NAME, "Droid Sans Mono");
		fontBox = new FontChoiceBox(fontname, Font.PLAIN, true);
		selectPanel.add(new JLabel("Font: "));
		selectPanel.add(fontBox);

		sizeBox = new JComboBox<>(SIZES);
		selectPanel.add(new JLabel("    Font Size: "));
		selectPanel.add(sizeBox);

		tabBox = new JComboBox<>(WIDTHS);
		selectPanel.add(new JLabel("    Tab Width: "));
		selectPanel.add(tabBox);

		fmtCodeBox = new JCheckBox("Format code");
		fmtBreakBox = new JCheckBox("Format long lines");
		fmtHeaderBox = new JCheckBox("Format header");
		fmtCommentsBox = new JCheckBox("Format comments");
		alignFieldsBox = new JCheckBox("Align fields");
		alignCommentsBox = new JCheckBox("Align comments");

		fmtCodeBox.addActionListener(run);
		fmtBreakBox.addActionListener(run);
		fmtHeaderBox.addActionListener(run);
		fmtCommentsBox.addActionListener(run);
		alignFieldsBox.addActionListener(run);
		alignCommentsBox.addActionListener(run);

		selectPanel.add(fmtCodeBox);
		selectPanel.add(fmtBreakBox);
		selectPanel.add(fmtHeaderBox);
		selectPanel.add(fmtCommentsBox);
		selectPanel.add(alignFieldsBox);
		selectPanel.add(alignCommentsBox);

		content.add(selectPanel, BorderLayout.NORTH);
	}

	private void createFormatPanel() {
		formatPanel = new FormatContentPanel(800, 400, fontBox, sizeBox, tabBox, "Source", "Formatted");
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
		JPanel infoPanel = createPanel("Formatting details");
		info = new FormatInfoPanel(this);
		infoPanel.add(info);

		content.add(infoPanel, BorderLayout.SOUTH);
	}

	@Override
	protected void saveCustomPrefs(Preferences prefs) {
		Font font = (Font) fontBox.getSelectedItem();
		prefs.put(KEY_FONT_NAME, font.getName());
		prefs.putInt(KEY_FONT_SIZE, (int) sizeBox.getSelectedItem());
		prefs.putInt(KEY_TAB_WIDTH, (int) tabBox.getSelectedItem());
		prefs.putBoolean(KEY_FMT_CODE, fmtCodeBox.isSelected());
		prefs.putBoolean(KEY_FMT_COMMENTS, fmtCommentsBox.isSelected());
		prefs.putBoolean(KEY_FMT_HEADER, fmtHeaderBox.isSelected());
		prefs.putBoolean(KEY_ALIGN_FIELDS, alignFieldsBox.isSelected());
		prefs.putBoolean(KEY_ALIGN_COMMENTS, alignCommentsBox.isSelected());
	}

	@Override
	protected void applyCustomPrefs() {
		fmtCodeBox.setSelected(prefs.getBoolean(KEY_FMT_CODE, false));
		fmtCommentsBox.setSelected(prefs.getBoolean(KEY_FMT_COMMENTS, false));
		fmtHeaderBox.setSelected(prefs.getBoolean(KEY_FMT_HEADER, false));
		alignFieldsBox.setSelected(prefs.getBoolean(KEY_ALIGN_FIELDS, false));
		alignCommentsBox.setSelected(prefs.getBoolean(KEY_ALIGN_COMMENTS, false));
	}

	protected void selectFeatureData(int line, int col) {
		if (enabled) {
			ParseRecord data = mgr.getDocModel().getParseRecord();
			Integer start = data.lineStartIndex.get(line);
			if (start != null) {
				String text = data.charStream.getText(new Interval(start, start + col - 1));
				int vcol = Strings.measureVisualWidth(text, mgr.getTabWidth());
				AdeptToken token = data.getVisualToken(line, vcol);
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

	private void loadTool() {
		tool = new Tool();
		tool.setCorpusRoot(corpusRoot);
		tool.setLang("antlr");
		tool.setTabWidth(4);
		tool.setRebuild(true);

		tool.setFormat(fmtCodeBox.isSelected());
		tool.setFormatComments(fmtCommentsBox.isSelected());
		tool.setFormatHdrComment(fmtHeaderBox.isSelected());
		tool.setFormatAlignFields(alignFieldsBox.isSelected());
		tool.setFormatAlignComments(alignCommentsBox.isSelected());

		tool.setRemoveCommentBlankLines(true);

		boolean ok = tool.loadResources();
		ok = ok && tool.validateOptions();

		if (!ok) {
			Log.error(this, "Failed to initialize model");
		} else {
			process();
		}
	}

	private void process() {
		SourceListModel model = (SourceListModel) srcBox.getModel();
		pathname = model.getSelectedPathname();
		sourceContent = loadContent(pathname);
		formatPanel.load(sourceContent, "");

		// Time.wait(1);
		FormatWorker worker = new FormatWorker();
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

		@Override
		protected String doInBackground() throws Exception {
			try {
				tool.setSourceFiles(pathname);
				tool.execute();
			} catch (Exception e) {
				Log.error(this, "Error in tool execution", e);
				throw e;
			}
			return null;
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

		formatPanel.load(sourceContent, tool.getFormatted());
		info.loadPerfData(mgr);
		enabled = true;
	}
}
