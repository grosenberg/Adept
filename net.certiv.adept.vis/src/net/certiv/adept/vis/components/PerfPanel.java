package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

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

	public JTextField textFieldLoad;
	public JTextField textFieldRebuild;
	public JTextField textFieldFormating;
	public JTextField textFieldFeatures;
	public JTextField textFieldTotalCnt;
	public JTextField textFieldTypeCnt;
	public JTextField textFieldTerminals;
	public JTextField textFieldName;
	public JTextField textFieldLocation;
	private JTextField textFieldId;
	private JTextField textFieldFacets;
	private JTextField textFieldMatFeature;
	private JTextField textFieldMatFormat;
	private JTextField textFieldMatFacets;
	private JTextField textFieldFormat;
	// private PerfData perfData;

	public PerfPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(
				new ColumnSpec[] {	FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("150dlu:grow"),
									ColumnSpec.decode("25dlu"), FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("150dlu:grow"),
									FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] {	FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblPerformance = new JLabel("Performance");
		add(lblPerformance, "2, 2, left, default");

		JLabel lblOther = new JLabel("Feature Data");
		add(lblOther, "6, 2, left, default");

		JLabel lblLoad = new JLabel("Loading");
		add(lblLoad, "2, 4, right, default");

		textFieldLoad = new JTextField();
		textFieldLoad.setEditable(false);
		add(textFieldLoad, "4, 4, fill, default");

		JLabel lblLocation = new JLabel("Location");
		add(lblLocation, "6, 4, right, default");

		textFieldLocation = new JTextField();
		textFieldLocation.setEditable(false);
		add(textFieldLocation, "8, 4, fill, default");
		textFieldLocation.setColumns(10);

		JLabel lblRebuildTime = new JLabel("Rebuilding");
		add(lblRebuildTime, "2, 6, right, default");

		textFieldRebuild = new JTextField();
		textFieldRebuild.setEditable(false);
		add(textFieldRebuild, "4, 6, fill, default");

		JLabel lblId = new JLabel("Feature");
		add(lblId, "6, 6, right, default");

		textFieldId = new JTextField();
		textFieldId.setEditable(false);
		add(textFieldId, "8, 6, fill, default");
		textFieldId.setColumns(10);

		JLabel lblFormatting = new JLabel("Formatting");
		add(lblFormatting, "2, 8, right, default");

		textFieldFormating = new JTextField();
		textFieldFormating.setEditable(false);
		add(textFieldFormating, "4, 8, fill, default");
		textFieldFormating.setColumns(10);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "6, 8, right, default");

		textFieldFormat = new JTextField();
		textFieldFormat.setEditable(false);
		add(textFieldFormat, "8, 8, fill, default");
		textFieldFormat.setColumns(10);

		JLabel lblFeatures = new JLabel("Corpus Features");
		add(lblFeatures, "2, 10, left, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "6, 10, right, default");

		textFieldFacets = new JTextField();
		textFieldFacets.setEditable(false);
		add(textFieldFacets, "8, 10, fill, default");
		textFieldFacets.setColumns(10);

		JLabel lblFeatureCount = new JLabel("Total");
		add(lblFeatureCount, "2, 12, right, default");

		textFieldTotalCnt = new JTextField();
		textFieldTotalCnt.setEditable(false);
		add(textFieldTotalCnt, "4, 12, fill, default");

		JLabel lblMatched = new JLabel("Matched");
		add(lblMatched, "6, 12");

		JLabel lblTypeCount = new JLabel("Types");
		add(lblTypeCount, "2, 14, right, default");

		textFieldTypeCnt = new JTextField();
		textFieldTypeCnt.setEditable(false);
		add(textFieldTypeCnt, "4, 14, fill, default");

		JLabel lblMatFeature = new JLabel("Feature");
		add(lblMatFeature, "6, 14, right, default");

		textFieldMatFeature = new JTextField();
		textFieldMatFeature.setEditable(false);
		add(textFieldMatFeature, "8, 14, fill, default");
		textFieldMatFeature.setColumns(10);

		JLabel lblDocument = new JLabel("Document");
		add(lblDocument, "2, 16, left, default");

		JLabel lblMatFormat = new JLabel("Format");
		add(lblMatFormat, "6, 16, right, default");

		textFieldMatFormat = new JTextField();
		textFieldMatFormat.setEditable(false);
		add(textFieldMatFormat, "8, 16, fill, default");
		textFieldMatFormat.setColumns(10);

		JLabel lblName = new JLabel("Name");
		add(lblName, "2, 18, right, default");

		textFieldName = new JTextField();
		textFieldName.setEditable(false);
		add(textFieldName, "4, 18, fill, default");

		JLabel lblMatFacets = new JLabel("Facets");
		add(lblMatFacets, "6, 18, right, default");

		textFieldMatFacets = new JTextField();
		textFieldMatFacets.setEditable(false);
		add(textFieldMatFacets, "8, 18, fill, default");
		textFieldMatFacets.setColumns(10);

		JLabel lblDocFeatures = new JLabel("Features");
		add(lblDocFeatures, "2, 20, right, default");

		textFieldFeatures = new JTextField();
		textFieldFeatures.setEditable(false);
		add(textFieldFeatures, "4, 20, fill, default");

		JLabel lblTerminals = new JLabel("Terminals");
		add(lblTerminals, "2, 22, right, default");

		textFieldTerminals = new JTextField();
		textFieldTerminals.setEditable(false);
		add(textFieldTerminals, "4, 22, fill, default");
	}

	public void load(PerfData perfData) {
		textFieldLoad.setText(String.valueOf(perfData.load.toMillis()) + " ms");
		textFieldRebuild.setText(String.valueOf(perfData.rebuild.toMillis()) + " ms");

		textFieldTotalCnt.setText(String.valueOf(perfData.corpusFeatureCnt));
		textFieldTypeCnt.setText(String.valueOf(perfData.corpusFeatureTypeCnt));

		DocPerf dp = perfData.getDoc(0);
		textFieldName.setText(dp.docName);
		textFieldFeatures.setText(String.valueOf(dp.docFeatureCnt));
		textFieldTerminals.setText(String.valueOf(dp.docTerminalCnt));

		textFieldFormating.setText(String.valueOf(dp.docFormat.toMillis()) + " ms");
	}

	public void clicked(String loc) {
		textFieldLocation.setText(loc);
	}

	public void loadData(Feature feature) {
		Kind kind = feature.getKind();
		int type = feature.getType();
		if (kind == Kind.RULE) {
			type = type >> 10;
		}
		List<Facet> facets = Facet.get(feature.getFormat());
		String was = Strings.join(facets, ", ");

		textFieldId.setText(String.format("%d %s (%s:%d) '%s' [%d]", //
				feature.getId(), feature.getAspect(), kind, type, feature.getText(), feature.dimensionality()));
		textFieldFormat.setText(String.valueOf(feature.getFormat()));
		textFieldFacets.setText(was);

		Feature matched = feature.getMatched();
		if (matched != null) {
			Kind mkind = matched.getKind();
			int mtype = matched.getType();
			if (mkind == Kind.RULE) {
				mtype = mtype >> 10;
			}
			facets = Facet.get(matched.getFormat());
			String now = Strings.join(facets, ", ");

			textFieldMatFeature.setText(String.format("%d %s (%s:%d) '%s' [%d]", //
					matched.getId(), matched.getAspect(), mkind, mtype, matched.getText(), matched.dimensionality()));
			textFieldMatFormat.setText(String.valueOf(matched.getFormat()));
			textFieldMatFacets.setText(now);
		} else {
			clearMatched();
			textFieldMatFeature.setText("No match.");
		}
	}

	public void clearAll() {
		textFieldLoad.setText("");
		textFieldRebuild.setText("");
		textFieldTotalCnt.setText("");
		textFieldTypeCnt.setText("");
		textFieldName.setText("");
		textFieldFeatures.setText("");
		textFieldTerminals.setText("");
		textFieldFormating.setText("");

		clearData();
	}

	public void clearData() {
		textFieldId.setText("");
		textFieldFormat.setText("");
		textFieldFacets.setText("");

		clearMatched();
	}

	private void clearMatched() {
		textFieldMatFeature.setText("");
		textFieldMatFormat.setText("");
		textFieldMatFacets.setText("");
	}
}
