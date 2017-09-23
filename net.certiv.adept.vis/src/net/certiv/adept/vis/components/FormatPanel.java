package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Font;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.core.PerfData;
import net.certiv.adept.core.PerfData.DocPerf;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Kind;
import net.certiv.adept.model.topo.Facet;
import net.certiv.adept.util.Strings;

public class FormatPanel extends JPanel {

	private PerfData perfData;

	private JTextField txtTotal;
	private JTextField txtLoad;
	private JTextField txtRebuild;
	private JTextField txtFormating;

	private JTextField txtCorpusFeatureCount;
	private JTextField txtCorpusTypeCount;

	private JTextField txtDocName;
	private JTextField txtDocFeatureCount;
	private JTextField txtDocTerminalCount;

	private JTextField txtDocCol;
	private JTextField txtDocRow;
	private JTextField txtDocAspect;
	private JCheckBox chkDocVariable;
	private JTextField txtDocText;
	private JTextField txtDocFacets;

	private JTextField txtMatName;
	private JTextField txtMatLine;
	private JTextField txtMatCol;
	private JTextField txtMatAspect;
	private JCheckBox chkMatVariable;
	private JTextField txtMatText;
	private JTextField txtMatFacets;
	private JTextField txtMatDents;
	private JTextField txtDocTypeCount;
	private JTextField textMatMerged;

	public FormatPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min):grow"), FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"),
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;min)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"), FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"),
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"),
				FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));

		JLabel lblPerformance = new JLabel("Performance");
		add(lblPerformance, "2, 2, left, default");

		JLabel lblTotalTime = new JLabel("Total Time");
		add(lblTotalTime, "2, 4, right, default");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		add(txtTotal, "4, 4, fill, default");

		JLabel lblLoad = new JLabel("Loading");
		add(lblLoad, "6, 4, right, default");

		txtLoad = new JTextField();
		txtLoad.setEditable(false);
		add(txtLoad, "8, 4, fill, default");

		JLabel lblRebuildTime = new JLabel("Rebuilding");
		add(lblRebuildTime, "10, 4, right, default");

		txtRebuild = new JTextField();
		txtRebuild.setEditable(false);
		add(txtRebuild, "12, 4, fill, default");

		JLabel lblFormatting = new JLabel("Formatting");
		add(lblFormatting, "14, 4, right, default");

		txtFormating = new JTextField();
		txtFormating.setEditable(false);
		add(txtFormating, "16, 4, fill, default");
		txtFormating.setColumns(10);

		JLabel lblCorpus = new JLabel("Corpus ");
		add(lblCorpus, "2, 6, left, default");

		JLabel lblCorpusFeatureCount = new JLabel("FeatureSet");
		add(lblCorpusFeatureCount, "2, 8, right, default");

		txtCorpusFeatureCount = new JTextField();
		txtCorpusFeatureCount.setEditable(false);
		add(txtCorpusFeatureCount, "4, 8, fill, default");

		JLabel lblCorpusTypeCount = new JLabel("Types");
		add(lblCorpusTypeCount, "6, 8, right, default");

		txtCorpusTypeCount = new JTextField();
		txtCorpusTypeCount.setEditable(false);
		add(txtCorpusTypeCount, "8, 8, fill, default");

		JLabel lblDocument = new JLabel("Document");
		add(lblDocument, "2, 10, left, default");

		JLabel lblDocName = new JLabel("Name");
		add(lblDocName, "2, 12, right, default");

		txtDocName = new JTextField();
		txtDocName.setEditable(false);
		add(txtDocName, "4, 12, 13, 1, fill, default");

		JLabel lblDocFeatureCount = new JLabel("FeatureSet");
		add(lblDocFeatureCount, "18, 12, right, default");

		txtDocFeatureCount = new JTextField();
		txtDocFeatureCount.setEditable(false);
		add(txtDocFeatureCount, "20, 12, fill, default");

		JLabel lblDocTypes = new JLabel("Types");
		add(lblDocTypes, "22, 12, right, default");

		txtDocTypeCount = new JTextField();
		txtDocTypeCount.setEditable(false);
		add(txtDocTypeCount, "24, 12, fill, default");

		JLabel lblDocLine = new JLabel("Line");
		add(lblDocLine, "2, 14, right, default");

		txtDocRow = new JTextField();
		txtDocRow.setEditable(false);
		add(txtDocRow, "4, 14, fill, default");

		JLabel lblDocCol = new JLabel("Col");
		add(lblDocCol, "6, 14, right, default");

		txtDocCol = new JTextField();
		txtDocCol.setEditable(false);
		add(txtDocCol, "8, 14, fill, default");

		// --

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "10, 14, right, default");

		txtDocAspect = new JTextField();
		txtDocAspect.setEditable(false);
		add(txtDocAspect, "12, 14, 3, 1, fill, default");

		chkDocVariable = new JCheckBox("Variable");
		add(chkDocVariable, "16, 14, left, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "18, 14, right, default");

		txtDocText = new JTextField();
		txtDocText.setEditable(false);
		add(txtDocText, "20, 14, fill, default");

		JLabel lblDocTerminalCount = new JLabel("Reals");
		add(lblDocTerminalCount, "22, 14, right, default");

		txtDocTerminalCount = new JTextField();
		txtDocTerminalCount.setColumns(5);
		txtDocTerminalCount.setEditable(false);
		add(txtDocTerminalCount, "24, 14, fill, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "2, 16, right, default");

		txtDocFacets = new JTextField();
		txtDocFacets.setEditable(false);
		add(txtDocFacets, "4, 16, 17, 1, fill, default");

		// --

		JLabel lblMatched = new JLabel("Matched");
		add(lblMatched, "2, 18");

		JLabel lblMatName = new JLabel("Name");
		add(lblMatName, "2, 20, right, default");

		txtMatName = new JTextField();
		txtMatName.setEditable(false);
		add(txtMatName, "4, 20, 13, 1, fill, default");
		txtMatName.setColumns(10);

		JLabel lblMatLine = new JLabel("Line");
		add(lblMatLine, "2, 22, right, default");

		txtMatLine = new JTextField();
		txtMatLine.setEditable(false);
		add(txtMatLine, "4, 22, fill, default");
		txtMatLine.setColumns(10);

		JLabel lblMatCol = new JLabel("Col");
		add(lblMatCol, "6, 22, right, default");

		txtMatCol = new JTextField();
		txtMatCol.setEditable(false);
		add(txtMatCol, "8, 22, fill, default");
		txtMatCol.setColumns(10);

		JLabel lblMatAspect = new JLabel("Aspect");
		add(lblMatAspect, "10, 22, right, default");

		txtMatAspect = new JTextField();
		txtMatAspect.setEditable(false);
		add(txtMatAspect, "12, 22, 3, 1, fill, default");

		chkMatVariable = new JCheckBox("Variable");
		add(chkMatVariable, "16, 22, left, default");

		JLabel lblMatText = new JLabel("Text");
		add(lblMatText, "18, 22, right, default");

		txtMatText = new JTextField();
		txtMatText.setEditable(false);
		add(txtMatText, "20, 22, fill, default");

		JLabel lblMatFacets = new JLabel("Facets");
		add(lblMatFacets, "2, 24, right, default");

		txtMatFacets = new JTextField();
		txtMatFacets.setEditable(false);
		add(txtMatFacets, "4, 24, 17, 1, fill, top");

		JLabel lblMatDents = new JLabel("Dents");
		add(lblMatDents, "22, 24, right, default");

		txtMatDents = new JTextField();
		txtMatDents.setEditable(false);
		add(txtMatDents, "24, 24, fill, default");

		JLabel lblMerged = new JLabel("Merged");
		add(lblMerged, "2, 26, right, default");

		textMatMerged = new JTextField();
		textMatMerged.setEditable(false);
		add(textMatMerged, "4, 26, 17, 1, fill, default");
		textMatMerged.setColumns(10);
	}

	public void load(PerfData perfData) {
		this.perfData = perfData;

		DocPerf dp = perfData.getDoc(0);
		long load = perfData.load.toMillis();
		long rebuild = perfData.rebuild.toMillis();
		long formatting = dp.docFormat.toMillis();
		long total = load + rebuild + formatting;

		txtTotal.setText(String.valueOf(total) + " ms");
		txtLoad.setText(String.valueOf(load) + " ms");
		txtRebuild.setText(String.valueOf(rebuild) + " ms");
		txtFormating.setText(String.valueOf(formatting) + " ms");

		txtCorpusFeatureCount.setText(String.valueOf(perfData.corpusFeatureCnt));
		txtCorpusTypeCount.setText(String.valueOf(perfData.corpusFeatureTypeCnt));

		txtDocName.setText(dp.docName);
		txtDocFeatureCount.setText(String.valueOf(dp.docFeatureCnt));
		txtDocTypeCount.setText(String.valueOf(dp.docTypeCnt));
		txtDocTerminalCount.setText(String.valueOf(dp.docTerminalCnt));
	}

	public void loadData(int line, int col, Feature feature) {
		Kind kind = feature.getKind();
		long type = feature.getType();
		if (kind == Kind.RULE) {
			type = type >>> 32;
		}
		Set<Facet> facets = Facet.get(feature.getFormat());
		txtDocFacets.setText(Strings.join(facets, ", "));
		txtDocRow.setText(String.valueOf(line + 1));
		txtDocCol.setText(String.valueOf(col + 1));
		txtDocAspect.setText(feature.getAspect());
		chkDocVariable.setSelected(feature.isVar());
		txtDocText.setText(feature.getText());

		Feature matched = feature.getMatched();
		if (matched != null) {
			String corpusDocName = perfData.corpusDocIndex.get(matched.getDocId());
			txtMatName.setText(corpusDocName);
			Kind mkind = matched.getKind();
			long mtype = matched.getType();
			if (mkind == Kind.RULE) {
				mtype = mtype >>> 32;
			}

			txtMatLine.setText(String.valueOf(matched.getLine() + 1));
			txtMatCol.setText(String.valueOf(matched.getCol() + 1));
			txtMatAspect.setText(matched.getAspect());
			chkMatVariable.setSelected(matched.isVar());
			txtMatText.setText(matched.getText());

			int format = matched.getFormat();
			txtMatFacets.setText(Strings.join(Facet.get(format), ", "));
			txtMatDents.setText(String.valueOf(Facet.getDentation(format)));

		} else {
			clearMatched();
			txtMatName.setText("No match.");
		}
	}

	public void clearAll() {
		txtTotal.setText("");
		txtLoad.setText("");
		txtRebuild.setText("");
		txtFormating.setText("");
		txtCorpusFeatureCount.setText("");
		txtCorpusTypeCount.setText("");
		txtDocName.setText("");

		clearData();
	}

	public void clearData() {
		txtDocRow.setText("");
		txtDocCol.setText("");
		txtDocAspect.setText("");
		chkDocVariable.setSelected(false);
		txtDocText.setText("");
		txtDocFacets.setText("");

		clearMatched();
	}

	private void clearMatched() {
		txtMatName.setText("");
		txtMatLine.setText("");
		txtMatCol.setText("");
		txtMatAspect.setText("");
		chkMatVariable.setSelected(false);
		txtMatText.setText("");
		txtMatFacets.setText("");
	}
}
