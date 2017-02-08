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
import net.certiv.adept.topo.Facet;
import net.certiv.adept.util.Strings;

public class PerfPanel extends JPanel {

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
	private PerfData perfData;

	public PerfPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"), FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"),
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;min)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"), FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;min)"),
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.PARAGRAPH_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.PARAGRAPH_GAP_ROWSPEC, }));

		JLabel lblPerformance = new JLabel("Performance");
		add(lblPerformance, "2, 2, left, default");

		JLabel lblCorpus = new JLabel("Corpus ");
		add(lblCorpus, "18, 2, left, default");

		JLabel lblTotalTime = new JLabel("Total Time");
		add(lblTotalTime, "2, 4, right, default");

		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		add(txtTotal, "4, 4, fill, default");
		txtTotal.setColumns(10);

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

		JLabel lblCorpusFeatureCount = new JLabel("Features");
		add(lblCorpusFeatureCount, "18, 4, right, default");

		txtCorpusFeatureCount = new JTextField();
		txtCorpusFeatureCount.setEditable(false);
		add(txtCorpusFeatureCount, "20, 4, fill, default");

		JLabel lblCorpusTypeCount = new JLabel("Types");
		add(lblCorpusTypeCount, "22, 4, right, default");

		txtCorpusTypeCount = new JTextField();
		txtCorpusTypeCount.setEditable(false);
		add(txtCorpusTypeCount, "24, 4, fill, default");

		JLabel lblDocument = new JLabel("Document");
		add(lblDocument, "2, 6, left, default");

		JLabel lblDocName = new JLabel("Name");
		add(lblDocName, "2, 8, right, default");

		txtDocName = new JTextField();
		txtDocName.setEditable(false);
		add(txtDocName, "4, 8, 13, 1, fill, default");

		JLabel lblDocFeatureCount = new JLabel("Features");
		add(lblDocFeatureCount, "18, 8, right, default");

		txtDocFeatureCount = new JTextField();
		txtDocFeatureCount.setEditable(false);
		add(txtDocFeatureCount, "20, 8, fill, default");

		JLabel lblDocTerminalCount = new JLabel("Terminals");
		add(lblDocTerminalCount, "22, 8, right, default");

		txtDocTerminalCount = new JTextField();
		txtDocTerminalCount.setColumns(10);
		txtDocTerminalCount.setEditable(false);
		add(txtDocTerminalCount, "24, 8, fill, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "2, 10, right, default");

		txtDocRow = new JTextField();
		txtDocRow.setEditable(false);
		add(txtDocRow, "4, 10, fill, default");
		txtDocRow.setColumns(10);

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "6, 10, right, default");

		txtDocCol = new JTextField();
		txtDocCol.setEditable(false);
		add(txtDocCol, "8, 10, fill, default");
		txtDocCol.setColumns(10);

		// --

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "10, 10, right, default");

		txtDocAspect = new JTextField();
		txtDocAspect.setEditable(false);
		add(txtDocAspect, "12, 10, 3, 1, fill, default");

		chkDocVariable = new JCheckBox("Variable");
		add(chkDocVariable, "16, 10, left, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "18, 10, right, default");

		txtDocText = new JTextField();
		txtDocText.setEditable(false);
		add(txtDocText, "20, 10, 5, 1, fill, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "2, 12, right, default");

		txtDocFacets = new JTextField();
		txtDocFacets.setEditable(false);
		add(txtDocFacets, "4, 12, 21, 1, fill, default");

		// --

		JLabel lblMatched = new JLabel("Matched");
		add(lblMatched, "2, 14");

		JLabel lblMatName = new JLabel("Name");
		add(lblMatName, "2, 16, right, default");

		txtMatName = new JTextField();
		txtMatName.setEditable(false);
		add(txtMatName, "4, 16, 13, 1, fill, default");
		txtMatName.setColumns(10);

		JLabel lblMatLine = new JLabel("Line");
		add(lblMatLine, "2, 18, right, default");

		txtMatLine = new JTextField();
		txtMatLine.setEditable(false);
		add(txtMatLine, "4, 18, fill, default");
		txtMatLine.setColumns(10);

		JLabel lblMatCol = new JLabel("Col");
		add(lblMatCol, "6, 18, right, default");

		txtMatCol = new JTextField();
		txtMatCol.setEditable(false);
		add(txtMatCol, "8, 18, fill, default");
		txtMatCol.setColumns(10);

		JLabel lblMatAspect = new JLabel("Aspect");
		add(lblMatAspect, "10, 18, right, default");

		txtMatAspect = new JTextField();
		txtMatAspect.setEditable(false);
		add(txtMatAspect, "12, 18, 3, 1, fill, default");

		chkMatVariable = new JCheckBox("Variable");
		add(chkMatVariable, "16, 18, left, default");

		JLabel lblMatText = new JLabel("Text");
		add(lblMatText, "18, 18, right, default");

		txtMatText = new JTextField();
		txtMatText.setEditable(false);
		add(txtMatText, "20, 18, fill, default");

		JLabel lblMatFacets = new JLabel("Facets");
		add(lblMatFacets, "2, 20, right, default");

		txtMatFacets = new JTextField();
		txtMatFacets.setEditable(false);
		add(txtMatFacets, "4, 20, 21, 1, fill, top");
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

		txtCorpusFeatureCount.setText(String.valueOf(dp.docFeatureCnt));
		txtCorpusTypeCount.setText(String.valueOf(perfData.corpusFeatureTypeCnt));

		txtDocName.setText(dp.docName);
		txtDocFeatureCount.setText(String.valueOf(dp.docFeatureCnt));
		txtDocTerminalCount.setText(String.valueOf(dp.docTerminalCnt));
	}

	public void loadData(int line, int col, Feature feature) {
		Kind kind = feature.getKind();
		int type = feature.getType();
		if (kind == Kind.RULE) {
			type = type >> 10;
		}
		Set<Facet> facets = Facet.get(feature.getFormat());
		txtDocFacets.setText(Strings.join(facets, ", "));
		txtDocRow.setText(String.valueOf(line));
		txtDocCol.setText(String.valueOf(col));
		txtDocAspect.setText(feature.getAspect());
		chkDocVariable.setSelected(feature.isVar());
		txtDocText.setText(feature.getText());

		Feature matched = feature.getMatched();
		if (matched != null) {
			String corpusDocName = perfData.corpusDocIndex.get(matched.getDocId());
			txtMatName.setText(corpusDocName);
			Kind mkind = matched.getKind();
			int mtype = matched.getType();
			if (mkind == Kind.RULE) {
				mtype = mtype >> 10;
			}
			facets = Facet.get(matched.getFormat());
			txtMatFacets.setText(Strings.join(facets, ", "));
			txtMatLine.setText(String.valueOf(matched.getY()));
			txtMatCol.setText(String.valueOf(matched.getX()));
			txtMatAspect.setText(matched.getAspect());
			chkMatVariable.setSelected(matched.isVar());
			txtMatText.setText(matched.getText());

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
