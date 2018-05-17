package net.certiv.adept.vis.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.format.TextEdit;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Strings;

public class FormatEditPanel extends JPanel {

	private JTextField txtRExist;
	private JTextField txtRRepl;
	private JTextField txtLExist;
	private JTextField txtLRepl;
	private JLabel lblRArrow;
	private JLabel lblLArrow;

	public FormatEditPanel() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(25dlu;min)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblLeft = new JLabel("Left");
		add(lblLeft, "2, 2, right, default");

		txtLExist = new JTextField();
		txtLExist.setEditable(false);
		add(txtLExist, "4, 2, fill, default");
		txtLExist.setColumns(10);

		lblLArrow = new JLabel("=>");
		add(lblLArrow, "6, 2, right, default");

		txtLRepl = new JTextField();
		txtLRepl.setEditable(false);
		add(txtLRepl, "8, 2, fill, default");
		txtLRepl.setColumns(10);

		JLabel lblRight = new JLabel("Right");
		add(lblRight, "2, 4, right, default");

		txtRExist = new JTextField();
		txtRExist.setEditable(false);
		add(txtRExist, "4, 4, fill, default");
		txtRExist.setColumns(10);

		lblRArrow = new JLabel("=>");
		add(lblRArrow, "6, 4, right, default");

		txtRRepl = new JTextField();
		txtRRepl.setEditable(false);
		add(txtRRepl, "8, 4, fill, default");
		txtRRepl.setColumns(10);
	}

	public void loadData(RefToken ref, RefToken matched, TextEdit ledit, TextEdit redit) {
		if (ledit != null) {
			txtLExist.setText(Strings.encodeWS(ledit.existing()));
			txtLRepl.setText(Strings.encodeWS(ledit.replacement()));
		} else {
			txtLExist.setText("");
			txtLRepl.setText("");
		}

		if (redit != null) {
			txtRExist.setText(Strings.encodeWS(redit.existing()));
			txtRRepl.setText(Strings.encodeWS(redit.replacement()));
		} else {
			txtRExist.setText("");
			txtRRepl.setText("");
		}
	}
}
