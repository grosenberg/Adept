package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.Feature;
import net.certiv.adept.parser.ISourceParser;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Stats;
import net.certiv.adept.util.Strings;

public class MatchPanel extends JPanel {

	private JTextField txtLine;
	private JTextField txtAspect;
	private JTextField txtText;
	private JTextField txtType;
	private JTextField txtDocName;
	private JTextField txtCol;
	private JTextField txtFacets;
	private JTextField txtDistance;
	private JTextField txtDents;
	private JTextField txtFormat;
	private JTextField txtFeatSim;
	private JTextField txtSelfSimF;
	private JTextField txtEdgeSim;
	private JTextField txtSelfSimM;
	private JTextField txtMutualSim;
	private JTextField txtIntersectSim;
	private JTextField txtTotTypes;
	private JTextField txtTotEdges;
	private JTextField txtIntersect;
	private JTextField txtIntersectTypes;
	private JTextField txtDisjointTypes;

	private JCheckBox chkVariable;
	private JCheckBox chkAlign;
	private JCheckBox chkAlignSame;

	public MatchPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(35dlu;default):grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblMatchedDesc = new JLabel("Matched Feature Description");
		add(lblMatchedDesc, "2, 2, 3, 1, left, default");

		JLabel lblDoc = new JLabel("Doc Name");
		add(lblDoc, "3, 4, right, default");

		txtDocName = new JTextField();
		txtDocName.setEditable(false);
		add(txtDocName, "5, 4, 15, 1, fill, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "3, 6, right, default");

		txtLine = new JTextField();
		txtLine.setEditable(false);
		add(txtLine, "5, 6, fill, default");

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "7, 6, right, default");

		txtCol = new JTextField();
		txtCol.setEditable(false);
		add(txtCol, "9, 6, fill, default");
		txtCol.setColumns(10);

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "3, 8, right, default");

		txtAspect = new JTextField();
		txtAspect.setEditable(false);
		add(txtAspect, "5, 8, 3, 1, fill, default");

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "9, 8, left, default");

		JLabel lblType = new JLabel("Type");
		add(lblType, "3, 10, right, default");

		txtType = new JTextField();
		txtType.setEditable(false);
		add(txtType, "5, 10, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "7, 10, right, default");

		txtText = new JTextField();
		txtText.setEditable(false);
		add(txtText, "9, 10, fill, default");
		txtText.setColumns(10);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "3, 12, right, default");

		txtFormat = new JTextField();
		txtFormat.setEditable(false);
		add(txtFormat, "5, 12, fill, default");
		txtFormat.setColumns(10);

		JLabel lblDents = new JLabel("Dents");
		add(lblDents, "7, 12, right, default");

		txtDents = new JTextField();
		txtDents.setEditable(false);
		add(txtDents, "9, 12, fill, default");
		txtDents.setColumns(10);

		JLabel lblAligned = new JLabel("Aligned");
		add(lblAligned, "11, 12, right, default");

		chkAlign = new JCheckBox("Above");
		add(chkAlign, "13, 12, left, default");

		chkAlignSame = new JCheckBox("Below");
		add(chkAlignSame, "15, 12, left, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "3, 14, right, default");

		txtFacets = new JTextField();
		txtFacets.setEditable(false);
		add(txtFacets, "5, 14, 15, 1, fill, default");
		txtFacets.setColumns(10);

		JLabel lblDistance = new JLabel("Distance");
		add(lblDistance, "13, 16, right, default");

		txtDistance = new JTextField();
		txtDistance.setHorizontalAlignment(SwingConstants.LEFT);
		txtDistance.setEditable(false);
		add(txtDistance, "15, 16, fill, default");
		txtDistance.setColumns(10);

		JLabel lblFeatSim = new JLabel("Label Sim");
		add(lblFeatSim, "17, 16, right, default");

		txtFeatSim = new JTextField();
		txtFeatSim.setEditable(false);
		add(txtFeatSim, "19, 16, fill, default");
		txtFeatSim.setColumns(10);

		JLabel lblSelfSim = new JLabel("Self Sim Feature");
		add(lblSelfSim, "13, 18, right, default");

		txtSelfSimF = new JTextField();
		txtSelfSimF.setEditable(false);
		add(txtSelfSimF, "15, 18, fill, default");
		txtSelfSimF.setColumns(10);

		JLabel lblEdgeSim = new JLabel("Edge Set Sim");
		add(lblEdgeSim, "17, 18, right, default");

		txtEdgeSim = new JTextField();
		txtEdgeSim.setEditable(false);
		add(txtEdgeSim, "19, 18, fill, default");
		txtEdgeSim.setColumns(10);

		JLabel lblEdgeTypes = new JLabel("Edge Types");
		add(lblEdgeTypes, "3, 20, right, default");

		txtTotTypes = new JTextField();
		txtTotTypes.setEditable(false);
		add(txtTotTypes, "5, 20, fill, default");
		txtTotTypes.setColumns(10);

		JLabel lblSelfSimM = new JLabel("Self Sim Matched");
		add(lblSelfSimM, "13, 20, right, default");

		txtSelfSimM = new JTextField();
		txtSelfSimM.setEditable(false);
		add(txtSelfSimM, "15, 20, fill, default");
		txtSelfSimM.setColumns(10);

		JLabel lblIntersect = new JLabel("Intersect Sim");
		add(lblIntersect, "17, 20, right, default");

		txtIntersectSim = new JTextField();
		txtIntersectSim.setEditable(false);
		add(txtIntersectSim, "19, 20, fill, default");
		txtIntersectSim.setColumns(10);

		JLabel lblCount = new JLabel("Total Edges");
		add(lblCount, "3, 22, right, default");

		txtTotEdges = new JTextField();
		txtTotEdges.setEditable(false);
		add(txtTotEdges, "5, 22, fill, default");
		txtTotEdges.setColumns(10);

		JLabel lblMutualSim = new JLabel("Mutual Sim");
		add(lblMutualSim, "13, 22, right, default");

		txtMutualSim = new JTextField();
		txtMutualSim.setEditable(false);
		add(txtMutualSim, "15, 22, fill, default");
		txtMutualSim.setColumns(10);

		JLabel lblEdgeDis = new JLabel("Disjoint Sim");
		add(lblEdgeDis, "17, 22, right, default");

		JLabel lblOverlap = new JLabel("Intersects");
		add(lblOverlap, "3, 24, right, default");

		txtIntersect = new JTextField();
		txtIntersect.setEditable(false);
		add(txtIntersect, "5, 24, fill, default");
		txtIntersect.setColumns(10);

		JLabel lblIntersectTypes = new JLabel("Types");
		add(lblIntersectTypes, "7, 24, right, default");

		txtIntersectTypes = new JTextField();
		txtIntersectTypes.setEditable(false);
		add(txtIntersectTypes, "9, 24, 11, 1, fill, default");
		txtIntersectTypes.setColumns(10);

		JLabel lblDisjoint = new JLabel("Disjoints");
		add(lblDisjoint, "3, 26, right, default");

		JLabel lblDisjointTypes = new JLabel("Types");
		add(lblDisjointTypes, "7, 26, right, default");

		txtDisjointTypes = new JTextField();
		txtDisjointTypes.setEditable(false);
		add(txtDisjointTypes, "9, 26, 11, 1, fill, default");
		txtDisjointTypes.setColumns(10);
	}

	public void load(Feature feature, ISourceParser lang, Feature matched) {
		CorpusModel model = feature.getMgr().getCorpusModel();
		txtDocName.setText(model.getPathname(matched.getDocId()));
		txtLine.setText(String.valueOf(matched.getLine()));
		txtCol.setText(String.valueOf(matched.getCol()));
		txtAspect.setText(matched.getAspect());
		chkVariable.setSelected(matched.isVar());
		txtType.setText(String.valueOf(matched.getType()));
		txtText.setText(matched.getText());
		chkAlign.setSelected(matched.isAligned());
		chkAlignSame.setSelected(matched.isAlignedSame());

		int format = matched.getFormat();
		int dents = Facet.getDentation(format);
		txtFormat.setText("0x" + Integer.toHexString(format).toUpperCase());
		txtDents.setText(String.valueOf(dents));
		txtFacets.setText(Strings.join(Facet.get(format), ", "));

		Stats stats = feature.getStats(matched);
		stats.setLanguage(lang);

		txtDistance.setText(String.valueOf(stats.distance));
		txtSelfSimF.setText(String.valueOf(stats.selfSimF));
		txtSelfSimM.setText(String.valueOf(stats.selfSimM));
		txtMutualSim.setText(String.valueOf(stats.mutualSim));
		txtFeatSim.setText(String.valueOf(stats.featLabelSim));
		txtEdgeSim.setText(String.valueOf(stats.edgeSetSim));
		txtIntersectSim.setText(String.valueOf(stats.intersectSim));

		txtTotTypes.setText(String.valueOf(stats.typeCount));
		txtTotEdges.setText(String.valueOf(stats.edgeCount));
		txtIntersect.setText(String.valueOf(stats.intersectCount));

		txtIntersectTypes.setText(stats.intersectTypes);
		txtDisjointTypes.setText(stats.disjointTypes);
	}

	public void clear() {
		for (Component child : getComponents()) {
			if (child instanceof JTextField) {
				((JTextField) child).setText("");
			} else if (child instanceof JCheckBox) {
				((JCheckBox) child).setSelected(false);
			}
		}
	}
}
