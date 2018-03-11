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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.prefs.Preferences;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import net.certiv.adept.Tool;
import net.certiv.adept.core.ProcessMgr;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.util.Kind;
import net.certiv.adept.util.Log;
import net.certiv.adept.view.utils.Point;
import net.certiv.adept.vis.components.AbstractBase;
import net.certiv.adept.vis.components.ContentPanel;
import net.certiv.adept.vis.components.FontChoiceBox;
import net.certiv.adept.vis.components.FormatPanel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;

public class FormatView extends AbstractBase {

	private static final String KEY_FONT_NAME = "font_name";
	private static final String KEY_FONT_SIZE = "font_size";
	private static final String KEY_TAB_WIDTH = "tab_width";
	private static final Integer[] SIZES = { 8, 11, 12, 14, 16, 18, 20, 24 };
	private static final Integer[] WIDTHS = { 2, 4, 6, 8 };

	private static final String name = "FormatView";
	private static final String corpusRoot = "../net.certiv.adept.core/corpus";
	private static final String rootDir = "../net.certiv.adept.test/test.snippets";
	private static final String srcExt = ".g4";

	private Tool tool;
	private JComboBox<Item> srcBox;
	private FontChoiceBox fontBox;
	private JComboBox<Integer> sizeBox;
	private JComboBox<Integer> tabBox;
	private ContentPanel contentPanel;
	private FormatPanel formatPanel;

	// key=source line; value=col ordered set of features
	private Map<Integer, TreeSet<Feature>> index;
	public String sourceContent;

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		FormatView view = new FormatView();
		view.loadTool();
	}

	public FormatView() {
		super(name, "format.png");

		SourceListModel model = new SourceListModel(rootDir, srcExt);
		srcBox = new JComboBox<>(model);
		srcBox.setSize(250, 32);
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tool.getPerfData().reset();
				process();
			}
		});

		// "Droid Sans Mono", "DejaVu Sans Mono", "Oxygen Mono", "NanumGothicCoding"
		String fontname = prefs.get(KEY_FONT_NAME, "Droid Sans Mono");
		fontBox = new FontChoiceBox(fontname, Font.PLAIN, true);

		sizeBox = new JComboBox<>(SIZES);
		tabBox = new JComboBox<>(WIDTHS);

		JPanel selectPanel = createPanel("Source");
		selectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		selectPanel.add(new JLabel("    Document: "));
		selectPanel.add(srcBox);
		selectPanel.add(new JLabel("    Font: "));
		selectPanel.add(fontBox);
		selectPanel.add(new JLabel("    Font Size: "));
		selectPanel.add(sizeBox);
		selectPanel.add(new JLabel("    Tab Width: "));
		selectPanel.add(tabBox);

		// ------------------------------------------------------------

		contentPanel = new ContentPanel(400, 400, fontBox, sizeBox, tabBox, "Original Source", "Formatted Source");
		contentPanel.addPropertyChangeListener(ContentPanel.CLICK1_LEFT, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				selectFeatureData((Point) e.getNewValue());
			}
		});

		// ------------------------------------------------------------

		formatPanel = new FormatPanel();
		JPanel fPanel = createPanel("Formatting Information");
		fPanel.add(formatPanel);

		// ------------------------------------------------------------

		content.add(selectPanel, BorderLayout.NORTH);
		content.add(contentPanel, BorderLayout.CENTER);
		content.add(fPanel, BorderLayout.SOUTH);

		setLocation();
		frame.setVisible(true);

		Integer fontsize = prefs.getInt(KEY_FONT_SIZE, 12);
		sizeBox.setSelectedItem(fontsize);

		Integer width = prefs.getInt(KEY_TAB_WIDTH, 4);
		tabBox.setSelectedItem(width);
	}

	@Override
	protected void saveWindowClosingPrefs(Preferences prefs) {
		Font font = (Font) fontBox.getSelectedItem();
		prefs.put(KEY_FONT_NAME, font.getName());
		prefs.putInt(KEY_FONT_SIZE, (int) sizeBox.getSelectedItem());
		prefs.putInt(KEY_TAB_WIDTH, (int) tabBox.getSelectedItem());
	}

	protected void selectFeatureData(Point loc) {
		if (index != null && loc != null) {
			TreeSet<Feature> lfs = index.get(loc.getLine());
			if (lfs != null) {
				for (Feature feature : lfs.descendingSet()) {
					if (loc.getCol() >= feature.getCol()) {
						formatPanel.loadData(loc.getLine(), feature.getCol(), feature);
						return;
					}
				}
			}
		}
		formatPanel.clearData();
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

	private String loadContent(String pathname) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(pathname));
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException e) {
			Log.error(this, "Failed to read source file" + pathname + ": " + e.getMessage());
		}
		return "";
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
				Log.error(this, "Error in format execution", e);
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
		contentPanel.clear();
		formatPanel.clearAll();

		String formatted = tool.getFormatted();
		if (formatted != null && !formatted.isEmpty()) {
			ProcessMgr mgr = tool.getMgr();
			int width = mgr.getDocModel().getDocument().getTabWidth();
			contentPanel.setTabStops(width);

			contentPanel.load(sourceContent, formatted);
			formatPanel.load(tool.getPerfData());

			// create line/features index
			index = new HashMap<>();
			List<Feature> features = tool.getMgr().getDocModel().getFeatures();
			for (Feature feature : features) {
				Integer line = feature.getLine();
				TreeSet<Feature> lfs = index.get(line);
				if (lfs == null) {
					lfs = sortedSet();
					index.put(line, lfs);
				}
				lfs.add(feature);
			}
		}
	}

	// sorted by col & kind; rules last
	private TreeSet<Feature> sortedSet() {
		return new TreeSet<>(new Comparator<Feature>() {

			@Override
			public int compare(Feature o1, Feature o2) {
				if (o1.getCol() < o2.getCol()) return -1;
				if (o1.getCol() > o2.getCol()) return 1;
				if (o1.getKind() != Kind.RULE && o2.getKind() == Kind.RULE) return -1;
				if (o1.getKind() == Kind.RULE && o2.getKind() != Kind.RULE) return 1;
				return 0;
			}
		});
	}
}
