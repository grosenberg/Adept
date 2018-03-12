package net.certiv.adept.view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Feature;
import net.certiv.adept.util.Log;
import net.certiv.adept.view.components.AbstractBase;
import net.certiv.adept.view.models.FeatureTableModel;

public class CorpusView extends AbstractBase {

	private static final String corpusRoot = "../net.certiv.adept/corpus";

	private CoreMgr mgr;
	private ISourceParser lang;
	private JTable table;
	private boolean filtered;
	private List<Feature> features;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		CorpusView view = new CorpusView();
		view.createFeaturesData();
	}

	public CorpusView() {
		super("Corpus Feature Analysis", "features.gif");

		table = createTable();
		JPanel panel = createScrollTable("Feature Set", table);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (!filtered) {
						int row = table.convertRowIndexToModel(table.getSelectedRow());
						filterToRow(row);
					} else {
						showDocumentFeatures();
					}
					filtered = !filtered;
				}
			}
		});

		content.add(panel, BorderLayout.CENTER);
		setLocation();
		frame.setVisible(true);
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
		table.setModel(model);
		model.configCols(table);
	}

	protected void filterToRow(int row) {
		FeatureTableModel model = (FeatureTableModel) table.getModel();
		Feature feature = model.getFeature(row);
		int type = feature.getType();

		showDocumentFeatures(type);
	}

	protected void showDocumentFeatures() {
		showDocumentFeatures(-1);
	}

	protected void showDocumentFeatures(int type) {
		FeatureTableModel model = (FeatureTableModel) table.getModel();
		model.removeAllRows();

		List<Feature> features = this.features;
		if (type > 0) {
			features = features.stream().filter(f -> f.getType() == type).collect(Collectors.toList());
		}
		model.addAll(features);
	}

}
