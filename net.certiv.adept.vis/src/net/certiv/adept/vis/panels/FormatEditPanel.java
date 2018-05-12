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
	private JTextField txtLLoc;
	private JTextField txtLReplLoc;
	private JTextField txtRLoc;
	private JTextField txtRReplLoc;

	public FormatEditPanel() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(25dlu;min)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblLeft = new JLabel("Left");
		add(lblLeft, "4, 2");

		JLabel lblRight = new JLabel("Right");
		add(lblRight, "6, 2");

		JLabel lblFrom = new JLabel("From");
		add(lblFrom, "2, 4, right, default");

		txtLExist = new JTextField();
		txtLExist.setEditable(false);
		add(txtLExist, "4, 4, fill, default");
		txtLExist.setColumns(10);

		txtRExist = new JTextField();
		txtRExist.setEditable(false);
		add(txtRExist, "6, 4, fill, default");
		txtRExist.setColumns(10);

		txtLLoc = new JTextField();
		txtLLoc.setEditable(false);
		add(txtLLoc, "4, 6, fill, default");
		txtLLoc.setColumns(10);

		txtRLoc = new JTextField();
		txtRLoc.setEditable(false);
		add(txtRLoc, "6, 6, fill, default");
		txtRLoc.setColumns(10);

		JLabel lblTo = new JLabel("To");
		add(lblTo, "2, 8, right, default");

		txtLRepl = new JTextField();
		txtLRepl.setEditable(false);
		add(txtLRepl, "4, 8, fill, default");
		txtLRepl.setColumns(10);

		txtRRepl = new JTextField();
		txtRRepl.setEditable(false);
		add(txtRRepl, "6, 8, fill, default");
		txtRRepl.setColumns(10);

		txtLReplLoc = new JTextField();
		txtLReplLoc.setEditable(false);
		add(txtLReplLoc, "4, 10, fill, default");
		txtLReplLoc.setColumns(10);

		txtRReplLoc = new JTextField();
		txtRReplLoc.setEditable(false);
		add(txtRReplLoc, "6, 10, fill, default");
		txtRReplLoc.setColumns(10);
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
