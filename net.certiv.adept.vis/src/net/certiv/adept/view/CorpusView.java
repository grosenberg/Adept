package net.certiv.adept.view;

import java.awt.BorderLayout;
import java.util.List;

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
		// table.addMouseListener(new MouseAdapter() {
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		// int row = table.getSelectedRow();
		// if (row >= 0) {}
		// }
		// });

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
		List<Feature> features = mgr.getCorpusModel().getCorpusFeatures();

		FeatureTableModel model = new FeatureTableModel(features, lang.getRuleNames(), lang.getTokenNames());
		table.setModel(model);
		model.configCols(table);
	}
}
