package net.certiv.adept.view.components;

import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.antlr.v4.runtime.misc.Utils;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.Spacing;

public class FeaturePanel extends JPanel {

	private JTextField textLine;
	private JTextField textNode;
	private JTextField textText;
	private JTextField textAncestors;
	private JTextField textDocName;
	private JTextField textCol;
	private JTextField textDents;
	private JTextField textFormat;
	private JTextField textContext;
	private JTextField textWeight;
	private JTextField textViscol;
	private JTextField textEncoded;

	private JCheckBox chkComment;
	private JCheckBox chkVariable;
	private JCheckBox chkAlign;

	private JLabel lblViscol;
	private JLabel lblEncoded;
	private JLabel lblWeight;
	private JSeparator separator;

	private List<String> ruleNames;
	private List<String> tokenNames;

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

		JLabel lblNode = new JLabel("Node");
		add(lblNode, "2, 4, right, default");

		textNode = new JTextField();
		textNode.setColumns(60);
		textNode.setEditable(false);
		add(textNode, "4, 4, fill, default");

		JLabel lblText = new JLabel("Text");
		add(lblText, "6, 4, right, default");

		textText = new JTextField();
		textText.setEditable(false);
		add(textText, "8, 4, 5, 1, fill, default");
		textText.setColumns(10);

		chkComment = new JCheckBox("Comment");
		add(chkComment, "14, 4");

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "16, 4, left, default");

		chkAlign = new JCheckBox("Aligned");
		add(chkAlign, "18, 4");

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

		lblViscol = new JLabel("VisCol");
		add(lblViscol, "2, 8, right, default");

		textViscol = new JTextField();
		textViscol.setHorizontalAlignment(SwingConstants.TRAILING);
		textViscol.setEditable(false);
		add(textViscol, "4, 8, left, default");
		textViscol.setColumns(5);

		JLabel lblContext = new JLabel("Context");
		add(lblContext, "6, 8, right, default");

		textContext = new JTextField();
		textContext.setEditable(false);
		add(textContext, "8, 8, 11, 1, fill, default");
		textContext.setColumns(10);

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "2, 10, right, default");

		textCol = new JTextField();
		textCol.setHorizontalAlignment(SwingConstants.TRAILING);
		textCol.setEditable(false);
		add(textCol, "4, 10, left, default");
		textCol.setColumns(5);

		JLabel lblFormat = new JLabel("Format");
		add(lblFormat, "6, 10, right, default");

		textFormat = new JTextField();
		textFormat.setEditable(false);
		add(textFormat, "8, 10, 11, 1, fill, default");
		textFormat.setColumns(10);

		lblWeight = new JLabel("Weight");
		add(lblWeight, "2, 12, right, default");

		textWeight = new JTextField();
		textWeight.setHorizontalAlignment(SwingConstants.TRAILING);
		textWeight.setEditable(false);
		add(textWeight, "4, 12, left, default");
		textWeight.setColumns(5);

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
		textText.setText(feature.getText());
		textAncestors.setText(evalAncestors(feature.getAncestors()));
		chkComment.setSelected(feature.isComment());
		chkVariable.setSelected(feature.isVar());

		textLine.setText(String.valueOf(feature.getLine() + 1));
		textCol.setText(String.valueOf(feature.getCol() + 1));
		textViscol.setText(String.valueOf(feature.getVisCol() + 1));
		textWeight.setText(String.valueOf(feature.getWeight()));

		textFormat.setText(evalFormat(feature));
	}

	private String evalFormat(Feature feature) {
		String spLeft = feature.getSpacingLeft() != Spacing.UNKNOWN ? feature.getSpacingLeft().toString().toLowerCase()
				: "";
		String wsLeft = Utils.escapeWhitespace(feature.getWsLeft(), true);
		String tokLeft = evalTokens(feature.getTokensLeft(), false);
		String left = String.format("{%s} %s[%s]", tokLeft, spLeft, wsLeft);

		String spRight = feature.getSpacingRight() != Spacing.UNKNOWN
				? feature.getSpacingRight().toString().toLowerCase()
				: "";
		String wsRight = Utils.escapeWhitespace(feature.getWsRight(), true);
		String tokRight = evalTokens(feature.getTokensRight(), false);
		String right = String.format("%s[%s] {%s}", spRight, wsRight, tokRight);

		return left + "  <->  " + right;
	}

	private String evalAncestors(List<Integer> ancestors) {
		int[] rules = ancestors.stream().mapToInt(i -> i >>> 16).toArray();
		StringBuilder sb = new StringBuilder();
		for (int rule : rules) {
			sb.append(ruleNames.get(rule) + " > ");
		}
		sb.setLength(sb.length() - 3);
		return sb.toString();
	}

	private String evalTokens(Set<Integer> indexes, boolean showType) {
		StringBuilder sb = new StringBuilder();
		for (int index : indexes) {
			String name = "";
			switch (index) {
				case -1:
					name = "EOF";
					break;
				case 0:
					name = "";
					break;
				default:
					name = tokenNames.get(index);
			}
			sb.append(name);
			if (showType) sb.append(String.format(" [%s], ", index));
			sb.append(" | ");
		}
		if (sb.length() > 1) sb.setLength(sb.length() - 3);
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
