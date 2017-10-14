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
import javax.swing.JSeparator;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textAspect;
	private JTextField textText;
	private JTextField textAncestors;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textDents;
	private JTextField textFormat;
	private JTextField textEdges;

	private JCheckBox chkVariable;
	private JLabel lblViscol;
	private JTextField textViscol;
	private JTextField textEncoded;
	private JLabel lblEncoded;
	private JCheckBox chkAlign;
	private JSeparator separator;

	public FeaturePanel(Font font) {
		setFont(font);
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(40dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("left:default"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow(2)"),
						FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "2, 2, right, default");

		textDocName = new JTextField();
		textDocName.setEditable(false);
		add(textDocName, "4, 2, 13, 1, fill, default");

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "2, 4, right, default");

		textAspect = new JTextField();
		textAspect.setColumns(20);
		textAspect.setEditable(false);
		add(textAspect, "4, 4, 3, 1, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "8, 4, right, default");

		textText = new JTextField();
		textText.setEditable(false);
		add(textText, "10, 4, 5, 1, fill, default");
		textText.setColumns(10);

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "16, 4, left, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "2, 6, right, default");

		textLine = new JTextField();
		textLine.setColumns(5);
		textLine.setEditable(false);
		add(textLine, "4, 6, fill, default");

		JLabel lblAncestors = new JLabel("Ancestors");
		add(lblAncestors, "6, 6, right, default");

		textAncestors = new JTextField();
		textAncestors.setEditable(false);
		add(textAncestors, "8, 6, 11, 1, fill, default");

		lblViscol = new JLabel("VisCol");
		add(lblViscol, "2, 8, right, default");

		textViscol = new JTextField();
		textViscol.setEditable(false);
		add(textViscol, "4, 8, fill, default");
		textViscol.setColumns(5);

		JLabel lblEdges = new JLabel("Edges");
		add(lblEdges, "6, 8, right, default");

		textEdges = new JTextField();
		textEdges.setEditable(false);
		add(textEdges, "8, 8, 11, 1, fill, default");
		textEdges.setColumns(10);

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "2, 10, right, default");

		textCol = new JTextField();
		textCol.setEditable(false);
		add(textCol, "4, 10, fill, default");
		textCol.setColumns(5);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "6, 10, right, default");

		textFormat = new JTextField();
		textFormat.setEditable(false);
		add(textFormat, "8, 10, 11, 1, fill, default");
		textFormat.setColumns(10);

		JLabel lblDents = new JLabel("Indents");
		add(lblDents, "6, 12, right, default");

		textDents = new JTextField();
		textDents.setEditable(false);
		add(textDents, "8, 12, fill, default");
		textDents.setColumns(5);

		lblEncoded = new JLabel("Encoded");
		add(lblEncoded, "10, 12, right, default");

		textEncoded = new JTextField();
		textEncoded.setEditable(false);
		add(textEncoded, "12, 12, fill, default");
		textEncoded.setColumns(8);

		chkAlign = new JCheckBox("Aligned");
		add(chkAlign, "14, 12");

		separator = new JSeparator();
		add(separator, "2, 14, 17, 1");
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
