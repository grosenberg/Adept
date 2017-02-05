package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.Tool;
import net.certiv.adept.model.Feature;
import net.certiv.adept.topo.Facet;
import net.certiv.adept.topo.Stats;
import net.certiv.adept.util.Strings;

import javax.swing.JCheckBox;

public class MatchPanel extends JPanel {

	private JTextField textLine;
	private JTextField textAspect;
	private JTextField textText;
	private JTextField textType;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textFacets;
	private JTextField textDistance;
	private JTextField textSelfSimF;
	private JTextField textFormat;
	private JTextField textFeatSim;
	private JTextField textEdgeSim;
	private JTextField textSelfSimM;
	private JTextField textIntersectSim;
	private JTextField textDisjointSim;
	private JTextField textTotTypes;
	private JTextField textTotEdges;
	private JTextField textIntersect;
	private JTextField textDisjoint;
	private JTextField textIntersectTypes;
	private JTextField textDisjointTypes;

	private JCheckBox chkVariable;
	private JCheckBox chkAlignAbove;
	private JCheckBox chkAlignBelow;
	private JTextField textMutualSim;
	private JLabel lblMutualSim;

	public MatchPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(
				new ColumnSpec[] {	FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
									FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
									FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
									FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
									FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
									FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] {	FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
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

		textDocName = new JTextField();
		textDocName.setEditable(false);
		add(textDocName, "5, 4, 15, 1, fill, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "3, 6, right, default");

		textLine = new JTextField();
		textLine.setEditable(false);
		add(textLine, "5, 6, fill, default");

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "7, 6, right, default");

		textCol = new JTextField();
		textCol.setEditable(false);
		add(textCol, "9, 6, fill, default");
		textCol.setColumns(10);

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "3, 8, right, default");

		textAspect = new JTextField();
		textAspect.setEditable(false);
		add(textAspect, "5, 8, 5, 1, fill, default");

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "11, 8, left, default");

		JLabel lblType = new JLabel("Type");
		add(lblType, "3, 10, right, default");

		textType = new JTextField();
		textType.setEditable(false);
		add(textType, "5, 10, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "7, 10, right, default");

		textText = new JTextField();
		textText.setEditable(false);
		add(textText, "9, 10, 5, 1, fill, default");
		textText.setColumns(10);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "3, 12, right, default");

		textFormat = new JTextField();
		textFormat.setEditable(false);
		add(textFormat, "5, 12, fill, default");
		textFormat.setColumns(10);

		JLabel lblAligned = new JLabel("Aligned");
		add(lblAligned, "7, 12, right, default");

		chkAlignAbove = new JCheckBox("Above");
		add(chkAlignAbove, "9, 12, left, default");

		chkAlignBelow = new JCheckBox("Below");
		add(chkAlignBelow, "11, 12, left, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "3, 14, right, default");

		textFacets = new JTextField();
		textFacets.setEditable(false);
		add(textFacets, "5, 14, 15, 1, fill, default");
		textFacets.setColumns(10);

		JLabel lblDistance = new JLabel("Distance");
		add(lblDistance, "3, 16, right, default");

		textDistance = new JTextField();
		textDistance.setEditable(false);
		add(textDistance, "5, 16, 3, 1, fill, default");
		textDistance.setColumns(10);

		lblMutualSim = new JLabel("Mutual Sim");
		add(lblMutualSim, "9, 16, right, default");

		textMutualSim = new JTextField();
		textMutualSim.setEditable(false);
		add(textMutualSim, "11, 16, 3, 1, fill, default");
		textMutualSim.setColumns(10);

		JLabel lblSelfSim = new JLabel("Self Sim Feature");
		add(lblSelfSim, "3, 18, right, default");

		textSelfSimF = new JTextField();
		textSelfSimF.setEditable(false);
		add(textSelfSimF, "5, 18, 3, 1, fill, default");
		textSelfSimF.setColumns(10);

		JLabel lblFeatSim = new JLabel("Feature Label Sim");
		add(lblFeatSim, "9, 18, right, default");

		textFeatSim = new JTextField();
		textFeatSim.setEditable(false);
		add(textFeatSim, "11, 18, 3, 1, fill, default");
		textFeatSim.setColumns(10);

		JLabel lblSelfSimM = new JLabel("Self Sim Matched");
		add(lblSelfSimM, "3, 20, right, default");

		textSelfSimM = new JTextField();
		textSelfSimM.setEditable(false);
		add(textSelfSimM, "5, 20, 3, 1, fill, default");
		textSelfSimM.setColumns(10);

		JLabel lblEdgeSim = new JLabel("Edge Set Sim");
		add(lblEdgeSim, "9, 20, right, default");

		textEdgeSim = new JTextField();
		textEdgeSim.setEditable(false);
		add(textEdgeSim, "11, 20, 3, 1, fill, default");
		textEdgeSim.setColumns(10);

		JLabel lblEdgeTypes = new JLabel("Edge Types");
		add(lblEdgeTypes, "3, 22, right, default");

		textTotTypes = new JTextField();
		textTotTypes.setEditable(false);
		add(textTotTypes, "5, 22, fill, default");
		textTotTypes.setColumns(10);

		JLabel lblIntersect = new JLabel("Intersect Sim");
		add(lblIntersect, "9, 22, right, default");

		textIntersectSim = new JTextField();
		textIntersectSim.setEditable(false);
		add(textIntersectSim, "11, 22, 3, 1, fill, default");
		textIntersectSim.setColumns(10);

		JLabel lblCount = new JLabel("Total Edges");
		add(lblCount, "3, 24, right, default");

		textTotEdges = new JTextField();
		textTotEdges.setEditable(false);
		add(textTotEdges, "5, 24, fill, default");
		textTotEdges.setColumns(10);

		JLabel lblEdgeDis = new JLabel("Disjoint Sim");
		add(lblEdgeDis, "9, 24, right, default");

		textDisjointSim = new JTextField();
		textDisjointSim.setEditable(false);
		add(textDisjointSim, "11, 24, 3, 1, fill, default");
		textDisjointSim.setColumns(10);

		JLabel lblOverlap = new JLabel("Intersects");
		add(lblOverlap, "3, 26, right, default");

		textIntersect = new JTextField();
		textIntersect.setEditable(false);
		add(textIntersect, "5, 26, fill, default");
		textIntersect.setColumns(10);

		JLabel lblIntersectTypes = new JLabel("Intersect Types");
		add(lblIntersectTypes, "7, 26, right, default");

		textIntersectTypes = new JTextField();
		textIntersectTypes.setEditable(false);
		add(textIntersectTypes, "9, 26, 11, 1, fill, default");
		textIntersectTypes.setColumns(10);

		JLabel lblDisjoint = new JLabel("Disjoints");
		add(lblDisjoint, "3, 28, right, default");

		textDisjoint = new JTextField();
		textDisjoint.setEditable(false);
		add(textDisjoint, "5, 28, fill, default");
		textDisjoint.setColumns(10);

		JLabel lblDisjointTypes = new JLabel("Disjoint Types");
		add(lblDisjointTypes, "7, 28, right, default");

		textDisjointTypes = new JTextField();
		textDisjointTypes.setEditable(false);
		add(textDisjointTypes, "9, 28, 11, 1, fill, default");
		textDisjointTypes.setColumns(10);
	}

	public void load(Feature feature, Feature matched) {
		textDocName.setText(Tool.mgr.getCorpusModel().getPathname(matched.getDocId()));
		textLine.setText(String.valueOf(matched.getY()));
		textCol.setText(String.valueOf(matched.getX()));
		textAspect.setText(matched.getAspect());
		chkVariable.setSelected(matched.isVar());
		textType.setText(String.valueOf(matched.getType()));
		textText.setText(matched.getText());
		textFormat.setText(String.valueOf(matched.getFormat()));
		chkAlignAbove.setSelected(matched.isAlignedAbove());
		chkAlignBelow.setSelected(matched.isAlignedBelow());

		textFacets.setText(Strings.join(Facet.get(matched.getFormat()), ", "));

		Stats stats = feature.getStats(matched);
		textDistance.setText(String.valueOf(stats.distance));
		textSelfSimF.setText(String.valueOf(stats.selfSimF));
		textSelfSimM.setText(String.valueOf(stats.selfSimM));
		textMutualSim.setText(String.valueOf(stats.mutualSim));
		textFeatSim.setText(String.valueOf(stats.featLabelSim));
		textEdgeSim.setText(String.valueOf(stats.edgeSetSim));
		textIntersectSim.setText(String.valueOf(stats.intersectSim));
		textDisjointSim.setText(String.valueOf(stats.disjointSim));

		textTotTypes.setText(String.valueOf(stats.typeCount));
		textTotEdges.setText(String.valueOf(stats.edgeCount));
		textIntersect.setText(String.valueOf(stats.intersectCount));
		textDisjoint.setText(String.valueOf(stats.disjointCount));

		textIntersectTypes.setText(stats.intersectTypes);
		textDisjointTypes.setText(stats.disjointTypes);
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
