package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
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
import net.certiv.adept.util.Strings;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textAspect;
	private JTextField textText;
	private JTextField textType;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textFacets;
	private JTextField textFormat;

	private JCheckBox chkVariable;
	private JCheckBox chkAlignAbove;
	private JCheckBox chkAlignBelow;

	public FeaturePanel(Font font) {
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
	}

	public void load(Feature feature) {
		textDocName.setText(Tool.mgr.getCorpusModel().getPathname(feature.getDocId()));
		textLine.setText(String.valueOf(feature.getY()));
		textCol.setText(String.valueOf(feature.getX()));
		textAspect.setText(feature.getAspect());
		chkVariable.setSelected(feature.isVar());
		textType.setText(String.valueOf(feature.getType()));
		textText.setText(feature.getText());
		textFormat.setText(String.valueOf(feature.getFormat()));
		chkAlignAbove.setSelected(feature.isAlignedAbove());
		chkAlignBelow.setSelected(feature.isAlignedBelow());

		textFacets.setText(Strings.join(Facet.get(feature.getFormat()), ", "));
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
