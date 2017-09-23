package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.topo.Facet;
import net.certiv.adept.util.Strings;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textAspect;
	private JTextField textText;
	private JTextField textType;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textFormat;
	private JTextField textDents;
	private JTextField textFacets;

	private JTextField textTotEdges;
	private JTextField textEdges;
	private JTextField textTypeCnt;

	private JCheckBox chkVariable;
	private JCheckBox chkAlign;
	private JCheckBox chkAlignSame;

	public FeaturePanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, },
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

		JLabel lblTargetDesc = new JLabel("Target Feature Description");
		add(lblTargetDesc, "2, 2, 3, 1, left, default");

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
		add(textAspect, "5, 8, 7, 1, fill, default");

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "13, 8, left, default");

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

		JLabel lblDents = new JLabel("Dents");
		add(lblDents, "7, 12, right, default");

		textDents = new JTextField();
		textDents.setEditable(false);
		add(textDents, "9, 12, fill, default");
		textDents.setColumns(10);

		JLabel lblAligned = new JLabel("Aligned");
		add(lblAligned, "11, 12, right, default");

		chkAlign = new JCheckBox("Around");
		add(chkAlign, "13, 12, left, default");

		chkAlignSame = new JCheckBox("Same");
		add(chkAlignSame, "15, 12, left, default");

		JLabel lblFacets = new JLabel("Facets");
		add(lblFacets, "3, 14, right, default");

		textFacets = new JTextField();
		textFacets.setEditable(false);
		add(textFacets, "5, 14, 15, 1, fill, default");
		textFacets.setColumns(10);

		JLabel lblTotalEdges = new JLabel("Total Edges");
		add(lblTotalEdges, "3, 16, right, default");

		textTotEdges = new JTextField();
		textTotEdges.setEditable(false);
		add(textTotEdges, "5, 16, fill, default");
		textTotEdges.setColumns(10);

		JLabel lblTypeCnt = new JLabel("Type Cnt");
		add(lblTypeCnt, "7, 16, right, default");

		textTypeCnt = new JTextField();
		textTypeCnt.setEditable(false);
		add(textTypeCnt, "9, 16, fill, default");
		textTypeCnt.setColumns(10);

		JLabel lblEdges = new JLabel("Edges");
		add(lblEdges, "3, 18, right, default");

		textEdges = new JTextField();
		textEdges.setEditable(false);
		add(textEdges, "5, 18, 15, 1, fill, default");
		textEdges.setColumns(10);
	}

	public void load(Document doc, Feature feature) {
		String docName = doc.getPathname();
		textDocName.setText(docName);
		textLine.setText(String.valueOf(feature.getLine()));
		textCol.setText(String.valueOf(feature.getCol()));
		textAspect.setText(feature.getAspect());
		chkVariable.setSelected(feature.isVar());
		textType.setText(String.valueOf(feature.getType()));
		textText.setText(feature.getText());
		textFormat.setText(String.valueOf(feature.getFormat()));
		textDents.setText(String.valueOf(Facet.getDentation(feature.getFormat())));
		chkAlign.setSelected(feature.isAligned());
		chkAlignSame.setSelected(feature.isAlignedSame());

		textFacets.setText(Strings.join(Facet.get(feature.getFormat()), ", "));
		textTotEdges.setText(String.valueOf(feature.getEdgeSet().getEdgeCount()));
		textTypeCnt.setText(String.valueOf(feature.getEdgeSet().getTypeCount()));

		List<String> ruleNames = doc.getParseData().getRuleNames();
		List<String> tokenNames = doc.getParseData().getTokenNames();
		List<String> v = new ArrayList<>();
		for (Long key : feature.getEdgeSet().getEdgeTypes()) {
			if (key.longValue() == 0) {
				v.add("Adept");
			} else {
				int rule = (int) (key >>> 32);
				int type = key.intValue();
				if (rule > 0) {
					v.add(ruleNames.get(rule));
				} else {
					v.add(tokenNames.get(type));
				}
			}
		}
		textEdges.setText(Strings.join(v, ", "));

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
