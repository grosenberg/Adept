package net.certiv.adept.view;

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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.antlr.v4.runtime.misc.Interval;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;
import net.certiv.adept.util.Time;
import net.certiv.adept.view.components.AbstractViewBase;
import net.certiv.adept.view.components.FontChoiceBox;
import net.certiv.adept.view.components.FormatContentPanel;
import net.certiv.adept.view.components.FormatInfoPanel;
import net.certiv.adept.view.models.SourceListModel;
import net.certiv.adept.view.models.SourceListModel.Item;
import net.certiv.adept.view.utils.Point;

public class FormatView extends AbstractViewBase {

	private static final String name = "FormatView";
	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private Tool tool;
	private JComboBox<Item> srcBox;
	private FontChoiceBox fontBox;
	private JComboBox<Integer> sizeBox;
	private JComboBox<Integer> tabBox;
	private FormatContentPanel formatPanel;
	private FormatInfoPanel info;

	// key=source line; value=list of features
	// private TreeMultiset<Integer, Feature> lineFeaturesIndex;
	public String sourceContent;
	private boolean enabled;

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

		setLocation();
		frame.setVisible(true);

		Integer fontsize = prefs.getInt(KEY_FONT_SIZE, 12);
		sizeBox.setSelectedItem(fontsize);

		Integer width = prefs.getInt(KEY_TAB_WIDTH, 4);
		tabBox.setSelectedItem(width);
	}

	private void createSelectPanel() {
		JPanel selectPanel = createPanel("Source");
		selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));

		srcBox = new JComboBox<>(new SourceListModel(rootDir, srcExt));
		srcBox.setSize(300, 32);
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Time.clear();
				process();
			}
		});
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

		content.add(selectPanel, BorderLayout.NORTH);
	}

	private void createFormatPanel() {
		formatPanel = new FormatContentPanel(400, 400, fontBox, sizeBox, tabBox, "Source", "Formatted");
		formatPanel.addPropertyChangeListener(FormatContentPanel.CLICK1_LEFT, new PropertyChangeListener() {

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
		info = new FormatInfoPanel();
		infoPanel.add(info);

		content.add(infoPanel, BorderLayout.SOUTH);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		Font font = (Font) fontBox.getSelectedItem();
		prefs.put(KEY_FONT_NAME, font.getName());
		prefs.putInt(KEY_FONT_SIZE, (int) sizeBox.getSelectedItem());
		prefs.putInt(KEY_TAB_WIDTH, (int) tabBox.getSelectedItem());
	}

	protected void selectFeatureData(int line, int col) {
		if (enabled) {
			ParseRecord rec = tool.getMgr().getDocModel().getParseRecord();
			int start = rec.lineStartIndex.get(line);
			String text = rec.charStream.getText(new Interval(start, start + col - 1));
			int vcol = Strings.measureVisualWidth(text, tool.getMgr().getTabWidth());
			AdeptToken token = rec.getVisualToken(line, vcol);
			if (token != null) {
				Feature feature = rec.getFeature(token);
				RefToken ref = feature.getRefFor(token.getTokenIndex());
				info.loadData(tool.getMgr(), feature, ref);
			} else {
				info.clearData();
			}
		}
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

	private void process() {
		new Formatter().execute();
	}

	private class Formatter extends SwingWorker<String, Object> {

		@Override
		protected String doInBackground() throws Exception {
			try {
				SourceListModel model = (SourceListModel) srcBox.getModel();
				String pathname = model.getSelectedPathname();
				sourceContent = loadContent(pathname);
				tool.setSourceFiles(pathname);
				tool.execute();
			} catch (Exception e) {
				Log.error(this, "Error in tool execution", e);
				throw e;
			}
			return null;
		}

		protected String loadContent(String pathname) {
			try {
				byte[] bytes = Files.readAllBytes(Paths.get(pathname));
				return new String(bytes, StandardCharsets.UTF_8);
			} catch (IOException e) {
				Log.error(this, "Failed to read source file" + pathname + ": " + e.getMessage());
			}
			return "";
		}

		@Override
		protected void done() {
			displayResults();
		}
	}

	protected void displayResults() {
		enabled = false;
		formatPanel.clear();
		info.clearAll();

		String formatted = tool.getFormatted();
		if (formatted != null && !formatted.isEmpty()) {
			CoreMgr mgr = tool.getMgr();
			int width = mgr.getDocModel().getDocument().getTabWidth();
			formatPanel.setTabStops(width);

			formatPanel.load(sourceContent, formatted);
			info.loadPerfData(mgr);
		}
		enabled = true;
	}
}
