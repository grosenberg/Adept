package net.certiv.adept.vis.graph.components;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.core.PerfData;
import net.certiv.adept.core.PerfData.DocPerf;
import net.certiv.adept.model.Feature;
import net.certiv.adept.format.misc.Format;

public class FormatPanel extends JPanel {

	private PerfData data;

	private JTextField txtTotal;
	private JTextField txtLoad;
	private JTextField txtRebuild;
	private JTextField txtFormating;

	private JTextField txtCorpusFeatures;
	private JTextField txtCorpusTypes;
	private JTextField txtDocFeatures;
	private JTextField txtDocReals;

	private JTextField txtDocCol;
	private JTextField txtDocRow;
	private JTextField txtDocAspect;
	private JCheckBox chkDocVariable;
	private JTextField txtDocVisCol;
	private JTextField txtDocFormat;

	private JTextField txtMatName;
	private JTextField txtMatLine;
	private JTextField txtMatCol;
	private JTextField txtMatAspect;
	private JCheckBox chkMatVariable;
	private JTextField txtMatVisCol;
	private JTextField txtMatFormat;
	private JTextField txtMatDents;
	private JTextField txtDocTypes;
	private JTextField txtMerged;
	private JTextField txtMatching;

	public FormatPanel() {
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(30dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;min)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow(2)"),
				ColumnSpec.decode("7dlu:grow(3)"), },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblPerformance = new JLabel("Performance");
		add(lblPerformance, "2, 2, 3, 1, left, default");

		JLabel lblSourceFeature = new JLabel("Source Feature");
		add(lblSourceFeature, "10, 2");

		JLabel lblTotalTime = new JLabel("Total Time");
		add(lblTotalTime, "2, 4, right, default");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		add(txtTotal, "4, 4, 3, 1, fill, default");

		JLabel lblDocLine = new JLabel("Line");
		add(lblDocLine, "10, 4, right, default");

		txtDocRow = new JTextField();
		txtDocRow.setColumns(5);
		txtDocRow.setEditable(false);
		add(txtDocRow, "12, 4, fill, default");

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "14, 4, right, default");

		txtDocAspect = new JTextField();
		txtDocAspect.setEditable(false);
		add(txtDocAspect, "16, 4, 5, 1, fill, default");

		JLabel lblLoad = new JLabel("Loading");
		add(lblLoad, "2, 6, right, default");

		txtLoad = new JTextField();
		txtLoad.setEditable(false);
		add(txtLoad, "4, 6, 3, 1, fill, default");

		JLabel lblVisCol = new JLabel("Visual Col");
		add(lblVisCol, "10, 6, right, default");

		txtDocVisCol = new JTextField();
		txtDocVisCol.setColumns(5);
		txtDocVisCol.setEditable(false);
		add(txtDocVisCol, "12, 6, fill, default");

		chkDocVariable = new JCheckBox("Variable");
		add(chkDocVariable, "16, 6, left, default");

		JLabel lblRebuildTime = new JLabel("Rebuilding");
		add(lblRebuildTime, "2, 8, right, default");

		txtRebuild = new JTextField();
		txtRebuild.setEditable(false);
		add(txtRebuild, "4, 8, 3, 1, fill, default");

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "8, 2, 1, 28");

		JLabel lblDocCol = new JLabel("Col");
		add(lblDocCol, "10, 8, right, default");

		txtDocCol = new JTextField();
		txtDocCol.setColumns(5);
		txtDocCol.setEditable(false);
		add(txtDocCol, "12, 8, fill, default");

		JLabel lblMatching = new JLabel("Matching");
		add(lblMatching, "2, 10, right, default");

		txtMatching = new JTextField();
		txtMatching.setEditable(false);
		add(txtMatching, "4, 10, 3, 1, fill, default");
		txtMatching.setColumns(10);

		JLabel lblDocFormat = new JLabel("Format");
		add(lblDocFormat, "10, 10, right, default");

		txtDocFormat = new JTextField();
		txtDocFormat.setEditable(false);
		add(txtDocFormat, "12, 10, 15, 1, fill, default");

		JLabel lblFormatting = new JLabel("Formatting");
		add(lblFormatting, "2, 12, right, default");

		txtFormating = new JTextField();
		txtFormating.setEditable(false);
		add(txtFormating, "4, 12, 3, 1, fill, default");
		txtFormating.setColumns(10);

		JLabel lblDocument = new JLabel("Source");
		add(lblDocument, "2, 14, left, default");

		JLabel lblMerged = new JLabel("Merged");
		add(lblMerged, "10, 14, left, default");

		txtMerged = new JTextField();
		txtMerged.setEditable(false);
		add(txtMerged, "12, 14, 15, 1, fill, default");
		txtMerged.setColumns(10);

		JLabel lblDocReals = new JLabel("Reals");
		add(lblDocReals, "2, 16, right, default");

		txtDocReals = new JTextField();
		txtDocReals.setColumns(5);
		txtDocReals.setEditable(false);
		add(txtDocReals, "4, 16, fill, default");

		// --

		JLabel lblMatched = new JLabel("Matched");
		add(lblMatched, "10, 16");

		JLabel lblDocFeatures = new JLabel("Features");
		add(lblDocFeatures, "2, 18, right, default");

		txtDocFeatures = new JTextField();
		txtDocFeatures.setColumns(5);
		txtDocFeatures.setEditable(false);
		add(txtDocFeatures, "4, 18, fill, default");

		JLabel lblMatFormat = new JLabel("Format");
		add(lblMatFormat, "10, 18, right, default");

		txtMatFormat = new JTextField();
		txtMatFormat.setEditable(false);
		add(txtMatFormat, "12, 18, 15, 1, fill, top");

		JLabel lblDocTypes = new JLabel("Types");
		add(lblDocTypes, "2, 20, right, default");

		txtDocTypes = new JTextField();
		txtDocTypes.setColumns(5);
		txtDocTypes.setEditable(false);
		add(txtDocTypes, "4, 20, fill, default");

		JLabel lblMatLine = new JLabel("Line");
		add(lblMatLine, "10, 20, right, default");

		txtMatLine = new JTextField();
		txtMatLine.setEditable(false);
		add(txtMatLine, "12, 20, fill, default");
		txtMatLine.setColumns(5);

		JLabel lblMatAspect = new JLabel("Aspect");
		add(lblMatAspect, "14, 20, right, default");

		txtMatAspect = new JTextField();
		txtMatAspect.setEditable(false);
		add(txtMatAspect, "16, 20, 5, 1, fill, default");

		JLabel lblMatDents = new JLabel("Dents");
		add(lblMatDents, "22, 20, right, default");

		txtMatDents = new JTextField();
		txtMatDents.setColumns(5);
		txtMatDents.setEditable(false);
		add(txtMatDents, "24, 20, fill, default");

		JLabel lblCorpus = new JLabel("Corpus");
		add(lblCorpus, "2, 22, left, default");

		JLabel lblMatVisCol = new JLabel("Visual Col");
		add(lblMatVisCol, "10, 22, right, default");

		txtMatVisCol = new JTextField();
		txtMatVisCol.setEditable(false);
		add(txtMatVisCol, "12, 22, fill, default");

		chkMatVariable = new JCheckBox("Variable");
		add(chkMatVariable, "16, 22, left, default");

		JLabel lblCorpusFeatures = new JLabel("Features");
		add(lblCorpusFeatures, "2, 24, right, default");

		txtCorpusFeatures = new JTextField();
		txtCorpusFeatures.setColumns(5);
		txtCorpusFeatures.setEditable(false);
		add(txtCorpusFeatures, "4, 24, fill, default");

		JLabel lblMatCol = new JLabel("Col");
		add(lblMatCol, "10, 24, right, default");

		txtMatCol = new JTextField();
		txtMatCol.setEditable(false);
		add(txtMatCol, "12, 24, fill, default");
		txtMatCol.setColumns(5);

		JLabel lblCorpusTypes = new JLabel("Types");
		add(lblCorpusTypes, "2, 26, right, default");

		txtCorpusTypes = new JTextField();
		txtCorpusTypes.setColumns(5);
		txtCorpusTypes.setEditable(false);
		add(txtCorpusTypes, "4, 26, fill, default");

		JLabel lblMatName = new JLabel("Name");
		add(lblMatName, "10, 26, right, default");

		txtMatName = new JTextField();
		txtMatName.setEditable(false);
		add(txtMatName, "12, 26, 5, 1, fill, default");
		txtMatName.setColumns(10);
	}

	public void load(PerfData data) {
		this.data = data;

		DocPerf performance = data.getDoc(0);
		long rebuild = data.rebuild.toMillis();
		long load = data.load.toMillis() - rebuild;
		long matching = performance.docMatch.toMillis();
		long formatting = performance.docFormat.toMillis() - matching;
		long total = load + rebuild + matching + formatting;

		txtTotal.setText(String.valueOf(total) + " ms");
		txtLoad.setText(String.valueOf(load) + " ms");
		txtRebuild.setText(String.valueOf(rebuild) + " ms");
		txtMatching.setText(String.valueOf(matching) + " ms");
		txtFormating.setText(String.valueOf(formatting) + " ms");

		txtDocFeatures.setText(String.valueOf(performance.docFeatureCnt));
		txtDocTypes.setText(String.valueOf(performance.docTypeCnt));
		txtDocReals.setText(String.valueOf(performance.docTerminalCnt));

		txtCorpusFeatures.setText(String.valueOf(data.corpusFeatureCnt));
		txtCorpusTypes.setText(String.valueOf(data.corpusFeatureTypeCnt));
	}

	public void loadData(int line, int col, Feature feature) {
		txtDocRow.setText(String.valueOf(line + 1));
		txtDocVisCol.setText(String.valueOf(feature.getVisCol()));
		txtDocCol.setText(String.valueOf(col + 1));
		txtDocAspect.setText(feature.getAspect());
		chkDocVariable.setSelected(feature.isVar());
		Format docFormat = feature.getFormat();
		txtDocFormat.setText(docFormat.toString());

		Feature matched = feature.getMatched();
		if (matched != null) {
			Format matchedFormat = matched.getFormat();
			txtMatFormat.setText(matchedFormat.toString());
			txtMatDents.setText(String.valueOf(matchedFormat.relDents));

			txtMatLine.setText(String.valueOf(matched.getLine() + 1));
			txtMatVisCol.setText(String.valueOf(matched.getVisCol()));
			txtMatCol.setText(String.valueOf(matched.getCol() + 1));
			txtMatAspect.setText(matched.getAspect());
			chkMatVariable.setSelected(matched.isVar());

			String corpusDocName = data.corpusDocIndex.get(matched.getDocId());
			Path pathname = Paths.get(corpusDocName);
			txtMatName.setText(pathname.getFileName().toString());

			Format mergedFormat = Format.merge(docFormat, matchedFormat);
			txtMerged.setText(mergedFormat.toString());

		} else {
			clearMatched();
		}
	}

	public void clearAll() {
		txtTotal.setText("");
		txtLoad.setText("");
		txtRebuild.setText("");
		txtFormating.setText("");
		txtCorpusFeatures.setText("");
		txtCorpusTypes.setText("");

		clearData();
	}

	public void clearData() {
		txtDocRow.setText("");
		txtDocCol.setText("");
		txtDocAspect.setText("");
		chkDocVariable.setSelected(false);
		txtDocVisCol.setText("");
		txtDocFormat.setText("");

		clearMatched();
	}

	private void clearMatched() {
		txtMatName.setText("");
		txtMatLine.setText("");
		txtMatCol.setText("");
		txtMatAspect.setText("");
		chkMatVariable.setSelected(false);
		txtMatVisCol.setText("");
		txtMatFormat.setText("");
	}
}
