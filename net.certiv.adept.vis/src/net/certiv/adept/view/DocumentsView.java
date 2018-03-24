package net.certiv.adept.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import net.certiv.adept.Tool;
import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Feature;
import net.certiv.adept.unit.HashMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.view.components.AbstractViewBase;
import net.certiv.adept.view.models.FeatureTableModel;
import net.certiv.adept.view.models.SourceListModel;
import net.certiv.adept.view.models.SourceListModel.Item;

public class DocumentsView extends AbstractViewBase {

	private static final String corpusRoot = "../net.certiv.adept/corpus";
	private static final String rootDir = "../net.certiv.adept/corpus/antlr";
	private static final String srcExt = ".g4";

	private CoreMgr mgr;
	private ISourceParser lang;
	private HashMultilist<Integer, Feature> docFeatures;
	private JTable table;
	protected int selectedSource;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (Exception e) {}

		DocumentsView view = new DocumentsView();
		view.createFeaturesData();
	}

	public DocumentsView() {
		super("Documents Feature Analysis", "features.gif");

		SourceListModel model = new SourceListModel(rootDir, srcExt);
		JComboBox<Item> srcBox = new JComboBox<>(model);
		srcBox.setMinimumSize(new Dimension(300, 0));
		srcBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSource = model.getIndexOfSelected();
				showDocumentFeatures();
			}
		});

		JPanel select = createPanel("Source");
		select.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
		select.add(createLabel("Document: ", 300, SwingConstants.RIGHT));
		select.add(srcBox);

		// ------------------------------------------------------------

		table = createTable();
		JPanel fsPanel = createScrollTable("Feature Set", table);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if (row < 0) return;

				if (e.getClickCount() == 2) {
					row = table.convertRowIndexToModel(table.getSelectedRow());
					filterToRow(row);
				}
			}
		});

		content.add(select, BorderLayout.NORTH);
		content.add(fsPanel, BorderLayout.CENTER);
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
		docFeatures = mgr.getCorpusModel().getDocFeatures();
		Integer key = docFeatures.keyList().get(0);
		List<Feature> features = docFeatures.get(key);

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

		Integer key = docFeatures.keyList().get(selectedSource);
		List<Feature> features = docFeatures.get(key);
		if (type > 0) {
			features = features.stream().filter(f -> f.getType() == type).collect(Collectors.toList());
		}
		model.addAll(features);
	}
}
