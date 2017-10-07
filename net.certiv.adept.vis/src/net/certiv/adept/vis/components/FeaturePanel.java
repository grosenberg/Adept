package net.certiv.adept.vis.components;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Format;
import net.certiv.adept.model.parser.ParseData;
import net.certiv.adept.util.Strings;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textAspect;
	private JTextField textText;
	private JTextField textAncestors;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textDents;
	private JTextField textFormat;

	private JTextField textTotEdges;
	private JTextField textEdges;
	private JTextField textTypes;

	private JCheckBox chkVariable;
	private JCheckBox chkAlign;
	private JLabel lblViscol;
	private JTextField textViscol;
	private JTextField textEncoded;
	private JLabel lblEncoded;

	public FeaturePanel(Font font) {
		setFont(font);
		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"),
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:default"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:default"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, ColumnSpec.decode("8dlu:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "3, 2, right, default");

		textDocName = new JTextField();
		textDocName.setEditable(false);
		add(textDocName, "5, 2, 17, 1, fill, default");

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "3, 4, right, default");

		textAspect = new JTextField();
		textAspect.setColumns(20);
		textAspect.setEditable(false);
		add(textAspect, "5, 4, 3, 1, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "9, 4, right, default");

		textText = new JTextField();
		textText.setEditable(false);
		add(textText, "11, 4, 7, 1, fill, default");
		textText.setColumns(10);

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "19, 4, 3, 1, left, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "11, 6, right, default");

		textLine = new JTextField();
		textLine.setColumns(5);
		textLine.setEditable(false);
		add(textLine, "13, 6, fill, default");

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "15, 6, right, default");

		textCol = new JTextField();
		textCol.setEditable(false);
		add(textCol, "17, 6, fill, default");
		textCol.setColumns(5);

		lblViscol = new JLabel("VisCol");
		add(lblViscol, "19, 6, right, default");

		textViscol = new JTextField();
		textViscol.setEditable(false);
		add(textViscol, "21, 6, fill, default");
		textViscol.setColumns(5);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "3, 8, right, default");

		textFormat = new JTextField();
		textFormat.setEditable(false);
		add(textFormat, "5, 8, 17, 1, fill, default");
		textFormat.setColumns(10);

		lblEncoded = new JLabel("Encoded");
		add(lblEncoded, "11, 10, right, default");

		textEncoded = new JTextField();
		textEncoded.setEditable(false);
		add(textEncoded, "13, 10, fill, default");
		textEncoded.setColumns(5);

		JLabel lblDents = new JLabel("Dents");
		add(lblDents, "15, 10, right, default");

		textDents = new JTextField();
		textDents.setEditable(false);
		add(textDents, "17, 10, fill, default");
		textDents.setColumns(5);

		chkAlign = new JCheckBox("Aligned");
		add(chkAlign, "19, 10, 3, 1, left, default");

		JLabel lblEdges = new JLabel("Edges");
		add(lblEdges, "3, 12, right, default");

		textEdges = new JTextField();
		textEdges.setEditable(false);
		add(textEdges, "5, 12, 17, 1, fill, default");
		textEdges.setColumns(10);

		JLabel lblTotalEdges = new JLabel("Total");
		add(lblTotalEdges, "15, 14, right, default");

		textTotEdges = new JTextField();
		textTotEdges.setEditable(false);
		add(textTotEdges, "17, 14, fill, default");
		textTotEdges.setColumns(5);

		JLabel lblTypes = new JLabel("Types");
		add(lblTypes, "19, 14, right, default");

		textTypes = new JTextField();
		textTypes.setEditable(false);
		add(textTypes, "21, 14, fill, default");
		textTypes.setColumns(5);

		JLabel lblAncestors = new JLabel("Ancestors");
		add(lblAncestors, "3, 16, right, default");

		textAncestors = new JTextField();
		textAncestors.setEditable(false);
		add(textAncestors, "5, 16, 17, 1, fill, default");
	}

	public void load(ParseData data, String pathname, Feature feature) {
		textDocName.setText(pathname);
		textAspect.setText(feature.getAspect());
		textText.setText(feature.getText());
		chkVariable.setSelected(feature.isVar());
		textLine.setText(String.valueOf(feature.getLine()));
		textCol.setText(String.valueOf(feature.getCol()));
		textViscol.setText(String.valueOf(feature.getVisCol()));

		Format format = feature.getFormat();
		textFormat.setText(format.toString());
		textEncoded.setText(format.encode());
		textDents.setText(String.valueOf(format.relDents));
		chkAlign.setSelected(feature.isAligned());

		List<String> v = ruleList(data, feature.getEdgeSet().getEdgeTypes());
		textEdges.setText(Strings.join(v, ", "));
		textTotEdges.setText(String.valueOf(feature.getEdgeSet().size()));
		textTypes.setText(String.valueOf(feature.getEdgeSet().getEdgeTypes().size()));

		v = ruleList(data, feature.getAncestorPath());
		textAncestors.setText(Strings.join(v, " > "));
	}

	private List<String> ruleList(ParseData data, Collection<Integer> values) {
		List<String> ruleNames = data.getRuleNames();
		List<String> tokenNames = data.getTokenNames();
		List<String> names = new ArrayList<>();
		for (Integer type : values) {
			if (type.intValue() == 0) {
				names.add("[root]");
			} else {
				int rule = type.intValue() >>> 16;
				if (rule > 0) {
					names.add(ruleNames.get(rule));
				} else {
					names.add(tokenNames.get(type));
				}
			}
		}
		return names;
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
