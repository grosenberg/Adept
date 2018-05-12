package net.certiv.adept.vis.panels;

import java.awt.Component;
import java.awt.Font;
import java.util.List;

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

import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textNode;
	private JTextField textText;
	private JTextField textAncestors;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textDents;
	private JTextField textWeight;

	private JCheckBox chkComment;
	private JLabel lblWeight;
	private JSeparator separator;

	private List<String> ruleNames;
	@SuppressWarnings("unused") private List<String> tokenNames;

	public FeaturePanel(Font font) {
		setFont(font);
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;min):grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:default"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow(2)"),
						FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "2, 2, right, default");

		textDocName = new JTextField();
		textDocName.setEditable(false);
		add(textDocName, "4, 2, 13, 1, fill, default");

		JLabel lblNode = new JLabel("Name");
		add(lblNode, "2, 4, right, default");

		textNode = new JTextField();
		textNode.setColumns(60);
		textNode.setEditable(false);
		add(textNode, "4, 4, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "6, 4, right, default");

		textText = new JTextField();
		textText.setEditable(false);
		add(textText, "8, 4, 11, 1, fill, default");
		textText.setColumns(10);

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "2, 6, right, default");

		textLine = new JTextField();
		textLine.setHorizontalAlignment(SwingConstants.TRAILING);
		textLine.setColumns(5);
		textLine.setEditable(false);
		add(textLine, "4, 6, left, default");

		JLabel lblAncestors = new JLabel("Ancestors");
		add(lblAncestors, "6, 6, right, default");

		textAncestors = new JTextField();
		textAncestors.setEditable(false);
		add(textAncestors, "8, 6, 11, 1, fill, default");

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "2, 8, right, default");

		textCol = new JTextField();
		textCol.setHorizontalAlignment(SwingConstants.TRAILING);
		textCol.setEditable(false);
		add(textCol, "4, 8, left, default");
		textCol.setColumns(5);

		JLabel lblRefs = new JLabel("Refs");
		add(lblRefs, "6, 8, right, default");

		textDents = new JTextField();
		textDents.setEditable(false);
		add(textDents, "8, 8, fill, default");
		textDents.setColumns(5);

		lblWeight = new JLabel("Weight");
		add(lblWeight, "10, 8, right, default");

		textWeight = new JTextField();
		textWeight.setHorizontalAlignment(SwingConstants.TRAILING);
		textWeight.setEditable(false);
		add(textWeight, "12, 8, left, default");
		textWeight.setColumns(5);

		chkComment = new JCheckBox("Comment");
		add(chkComment, "14, 8");

		separator = new JSeparator();
		add(separator, "2, 14, 17, 1");
	}

	public void setRuleNames(List<String> ruleNames, List<String> tokenNames) {
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
	}

	public void load(ParseRecord data, String pathname, Feature feature) {
		textDocName.setText(pathname.replace('\\', '/'));
		textNode.setText(String.format("%s  (%s)", feature.getNodeName(), feature.getType()));
		// textText.setText(feature.getText());
		textAncestors.setText(evalAncestors(feature.getAncestors()));
		chkComment.setSelected(feature.getKind().isComment());

		// textLine.setText(String.valueOf(feature.getLine() + 1));
		// textCol.setText(
		// String.format("%s (%s)", String.valueOf(feature.getCol() + 1),
		// String.valueOf(feature.getVisCol())));
		textWeight.setText(String.valueOf(feature.getWeight()));
	}

	private String evalAncestors(List<Integer> ancestors) {
		int[] rules = ancestors.stream().mapToInt(i -> i).toArray();
		StringBuilder sb = new StringBuilder();
		for (int rule : rules) {
			sb.append(ruleNames.get(rule) + " > ");
		}
		sb.setLength(sb.length() - 3);
		return sb.toString();
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
