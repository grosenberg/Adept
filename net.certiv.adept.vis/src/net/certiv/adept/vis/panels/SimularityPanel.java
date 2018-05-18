/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.panels;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class SimularityPanel extends JPanel {

	private JTextField txtSimilarity;
	private JTextField txtAncestorSim;
	private JTextField txtSelfSimF;
	private JTextField txtEdgeTypeSim;
	private JTextField txtSelfSimM;
	private JTextField txtMutualSim;
	private JTextField txtEdgeTextSim;
	private JTextField txtFormatLineSim;
	private JTextField txtWeightSim;
	private JTextField txtAspectsSim;
	private JLabel lblFormatWsSm;
	private JLabel lblFormatStyleSim;
	private JTextField txtFormatWsSim;
	private JTextField txtFormatStyleSim;

	public SimularityPanel(Font font) {
		setFont(font);
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblSimularity = new JLabel("Simularity");
		add(lblSimularity, "2, 2, right, default");

		txtSimilarity = new JTextField();
		txtSimilarity.setHorizontalAlignment(SwingConstants.LEFT);
		txtSimilarity.setEditable(false);
		add(txtSimilarity, "4, 2, fill, default");
		txtSimilarity.setColumns(10);

		JLabel lblAncestorSim = new JLabel("Ancestor Sim");
		add(lblAncestorSim, "6, 2, right, default");

		txtAncestorSim = new JTextField();
		txtAncestorSim.setEditable(false);
		add(txtAncestorSim, "8, 2, fill, default");
		txtAncestorSim.setColumns(10);

		JLabel lblFormatSim = new JLabel("Format Line Sim");
		add(lblFormatSim, "10, 2, right, default");

		txtFormatLineSim = new JTextField();
		txtFormatLineSim.setEditable(false);
		add(txtFormatLineSim, "12, 2, fill, default");
		txtFormatLineSim.setColumns(10);

		JLabel lblSelfSimF = new JLabel("Feature Self Sim");
		add(lblSelfSimF, "2, 4, right, default");

		txtSelfSimF = new JTextField();
		txtSelfSimF.setEditable(false);
		add(txtSelfSimF, "4, 4, fill, default");
		txtSelfSimF.setColumns(10);

		JLabel lblEdgeTypeSim = new JLabel("Edges Type Sim");
		add(lblEdgeTypeSim, "6, 4, right, default");

		txtEdgeTypeSim = new JTextField();
		txtEdgeTypeSim.setEditable(false);
		add(txtEdgeTypeSim, "8, 4, fill, default");
		txtEdgeTypeSim.setColumns(10);

		lblFormatWsSm = new JLabel("Format Ws Sm");
		add(lblFormatWsSm, "10, 4, right, default");

		txtFormatWsSim = new JTextField();
		txtFormatWsSim.setEditable(false);
		add(txtFormatWsSim, "12, 4, fill, default");
		txtFormatWsSim.setColumns(10);

		JLabel lblSelfSimM = new JLabel("Matched Self Sim");
		add(lblSelfSimM, "2, 6, right, default");

		txtSelfSimM = new JTextField();
		txtSelfSimM.setEditable(false);
		add(txtSelfSimM, "4, 6, fill, default");
		txtSelfSimM.setColumns(10);

		JLabel lblAspectsSim = new JLabel("Edge Aspects Sim");
		add(lblAspectsSim, "6, 6, right, default");

		txtAspectsSim = new JTextField();
		txtAspectsSim.setEditable(false);
		add(txtAspectsSim, "8, 6, fill, default");
		txtAspectsSim.setColumns(10);

		lblFormatStyleSim = new JLabel("Format Style Sim");
		add(lblFormatStyleSim, "10, 6, right, default");

		txtFormatStyleSim = new JTextField();
		txtFormatStyleSim.setEditable(false);
		add(txtFormatStyleSim, "12, 6, fill, default");
		txtFormatStyleSim.setColumns(10);

		JLabel lblMutualSim = new JLabel("Mutual Sim");
		add(lblMutualSim, "2, 8, right, default");

		txtMutualSim = new JTextField();
		txtMutualSim.setEditable(false);
		add(txtMutualSim, "4, 8, fill, default");
		txtMutualSim.setColumns(10);

		JLabel lblEdgeSetTextSim = new JLabel("Edges Text Sim");
		add(lblEdgeSetTextSim, "6, 8, right, default");

		txtEdgeTextSim = new JTextField();
		txtEdgeTextSim.setEditable(false);
		add(txtEdgeTextSim, "8, 8, fill, default");
		txtEdgeTextSim.setColumns(10);

		JLabel lblWeightSim = new JLabel("Weight Sim");
		add(lblWeightSim, "10, 8, right, default");

		txtWeightSim = new JTextField();
		txtWeightSim.setEditable(false);
		add(txtWeightSim, "12, 8, fill, default");
		txtWeightSim.setColumns(10);
	}

	// public void load(MatchData matchData) {
	// txtSimilarity.setText(String.valueOf(matchData.similarity));
	// txtSelfSimF.setText(String.valueOf(matchData.selfSimF));
	// txtSelfSimM.setText(String.valueOf(matchData.selfSimM));
	// txtMutualSim.setText(String.valueOf(matchData.mutualSim));
	//
	// txtAncestorSim.setText(String.valueOf(matchData.ancestorSim));
	// txtEdgeTypeSim.setText(String.valueOf(matchData.edgeTypeSim));
	// txtAspectsSim.setText(String.valueOf(matchData.edgeAspectsSim));
	// txtEdgeTextSim.setText(String.valueOf(matchData.edgeTextSim));
	// txtFormatLineSim.setText(String.valueOf(matchData.formatLineSim));
	// txtFormatWsSim.setText(String.valueOf(matchData.formatWsSim));
	// txtFormatStyleSim.setText(String.valueOf(matchData.formatStyleSim));
	// txtWeightSim.setText(String.valueOf(matchData.weightSim));
	// }

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
