/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.format.TextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.util.Refs;

public class FormatRealPanel extends JPanel {

	private JTextField txtLine;
	private JTextField txtSimularity;
	private JTextField txtAncestors;
	private JTextField txtToken;
	private JTextField txtDent;
	private JTextField txtPlace;
	private JTextField txtSpacing;
	private JTextField txtAlignment;

	private JLabel lblSimilarity;
	private JLabel lblDocLine;
	private JLabel lblAncestors;

	public FormatRealPanel() {
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		lblDocLine = new JLabel("Ln/Col/Vis");
		add(lblDocLine, "2, 2, right, default");

		txtLine = new JTextField();
		txtLine.setHorizontalAlignment(SwingConstants.CENTER);
		txtLine.setColumns(8);
		txtLine.setEditable(false);
		add(txtLine, "4, 2, fill, default");

		lblSimilarity = new JLabel("Similarity");
		add(lblSimilarity, "2, 4, right, default");

		txtSimularity = new JTextField();
		txtSimularity.setHorizontalAlignment(SwingConstants.TRAILING);
		txtSimularity.setEditable(false);
		add(txtSimularity, "4, 4, fill, default");

		lblAncestors = new JLabel("Ancestors");
		add(lblAncestors, "2, 6, right, default");

		txtAncestors = new JTextField();
		txtAncestors.setEditable(false);
		add(txtAncestors, "4, 6, 5, 1, fill, default");

		JLabel lblNewLabel = new JLabel("Token");
		add(lblNewLabel, "2, 8, right, default");

		txtToken = new JTextField();
		txtToken.setColumns(12);
		txtToken.setEditable(false);
		add(txtToken, "4, 8, fill, default");

		txtPlace = new JTextField();
		txtPlace.setEditable(false);
		add(txtPlace, "6, 8, fill, default");
		txtPlace.setColumns(8);

		txtDent = new JTextField();
		txtDent.setColumns(20);
		txtDent.setEditable(false);
		add(txtDent, "8, 8, fill, default");

		JLabel lblSpacing = new JLabel("Spacing");
		add(lblSpacing, "2, 10, right, default");

		txtSpacing = new JTextField();
		txtSpacing.setEditable(false);
		add(txtSpacing, "4, 10, 5, 1, fill, default");
		txtSpacing.setColumns(10);

		JLabel lblAlignment = new JLabel("Alignment");
		add(lblAlignment, "2, 12, right, default");

		txtAlignment = new JTextField();
		txtAlignment.setEditable(false);
		add(txtAlignment, "4, 12, 5, 1, fill, default");
		txtAlignment.setColumns(10);
	}

	public void hideLineCol() {
		lblDocLine.setVisible(false);
		txtLine.setVisible(false);
		FormLayout fm = (FormLayout) getLayout();
		fm.removeRow(3);
	}

	public void hideSim() {
		lblSimilarity.setVisible(false);
		txtSimularity.setVisible(false);
		FormLayout fm = (FormLayout) getLayout();
		fm.removeRow(5);
	}

	public void hideAncestors() {
		lblAncestors.setVisible(false);
		txtAncestors.setVisible(false);
		FormLayout fm = (FormLayout) getLayout();
		fm.removeRow(7);
	}

	public void loadData(int type, Feature feature, AdeptToken token, RefToken ref, RefToken matched, double sim,
			TextEdit ledit, TextEdit redit) {

		switch (type) {
			case 1:
				txtLine.setText(String.format("%s:%s %s", token.iLine + 1, token.iCol + 1, token.iVisCol + 1));
				txtAncestors.setText(Refs.evalAncestors(feature.getAncestors()));
				txtToken.setText(Refs.fType(ref.type));
				txtPlace.setText(Refs.tPlace(ref));
				txtDent.setText(ref.dent.toString());
				txtAlignment.setText(Refs.tAlign(ref));
				txtSpacing.setText(Refs.tSpace(ref));
				break;

			case 2:
				if (matched != null) {
					txtSimularity.setText(String.valueOf(sim));
					txtToken.setText(Refs.fType(matched.type));
					txtPlace.setText(Refs.tPlace(matched));
					txtDent.setText(matched.dent.toString());
					txtAlignment.setText(Refs.tAlign(matched));
					txtSpacing.setText(Refs.tSpace(matched));
				} else {
					clearData();
				}
				break;
		}
	}

	public void clearData() {
		txtLine.setText("");
		txtPlace.setText("");
		txtDent.setText("");
		txtAncestors.setText("");
		txtToken.setText("");
		txtAlignment.setText("");
		txtSpacing.setText("");
	}
}
