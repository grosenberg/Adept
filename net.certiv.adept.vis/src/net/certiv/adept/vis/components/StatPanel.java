package net.certiv.adept.vis.components;

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

import net.certiv.adept.model.util.Stats;

public class StatPanel extends JPanel {

	private JTextField txtSimilarity;
	private JTextField txtAncestorSim;
	private JTextField txtSelfSimF;
	private JTextField txtEdgeTypeSim;
	private JTextField txtSelfSimM;
	private JTextField txtPairSim;
	private JTextField txtEdgeTextSim;
	private JTextField txtFormatSim;
	private JTextField txtWeightSim;

	public StatPanel(Font font) {
		setFont(font);
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(35dlu;default):grow"), FormSpecs.UNRELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblSimularity = new JLabel("Simularity");
		add(lblSimularity, "3, 2, right, default");

		txtSimilarity = new JTextField();
		txtSimilarity.setHorizontalAlignment(SwingConstants.LEFT);
		txtSimilarity.setEditable(false);
		add(txtSimilarity, "5, 2, fill, default");
		txtSimilarity.setColumns(10);

		JLabel lblAncestorSim = new JLabel("Ancestor Sim");
		add(lblAncestorSim, "7, 2, right, default");

		txtAncestorSim = new JTextField();
		txtAncestorSim.setEditable(false);
		add(txtAncestorSim, "9, 2, fill, default");
		txtAncestorSim.setColumns(10);

		JLabel lblSelfSimF = new JLabel("Self Sim (Feature)");
		add(lblSelfSimF, "3, 4, right, default");

		txtSelfSimF = new JTextField();
		txtSelfSimF.setEditable(false);
		add(txtSelfSimF, "5, 4, fill, default");
		txtSelfSimF.setColumns(10);

		JLabel lblEdgeTypeSim = new JLabel("EdgeSet Type Sim");
		add(lblEdgeTypeSim, "7, 4, right, default");

		txtEdgeTypeSim = new JTextField();
		txtEdgeTypeSim.setEditable(false);
		add(txtEdgeTypeSim, "9, 4, fill, default");
		txtEdgeTypeSim.setColumns(10);

		JLabel lblSelfSimM = new JLabel("Self Sim (Matched)");
		add(lblSelfSimM, "3, 6, right, default");

		txtSelfSimM = new JTextField();
		txtSelfSimM.setEditable(false);
		add(txtSelfSimM, "5, 6, fill, default");
		txtSelfSimM.setColumns(10);

		JLabel lblEdgeSetTextSim = new JLabel("EdgeSet Text Sim");
		add(lblEdgeSetTextSim, "7, 6, right, default");

		txtEdgeTextSim = new JTextField();
		txtEdgeTextSim.setEditable(false);
		add(txtEdgeTextSim, "9, 6, fill, default");
		txtEdgeTextSim.setColumns(10);

		JLabel lblPairSim = new JLabel("Pair Simularity");
		add(lblPairSim, "3, 8, right, default");

		txtPairSim = new JTextField();
		txtPairSim.setEditable(false);
		add(txtPairSim, "5, 8, fill, default");
		txtPairSim.setColumns(10);

		JLabel lblFormatSim = new JLabel("Format Sim");
		add(lblFormatSim, "7, 8, right, default");

		txtFormatSim = new JTextField();
		txtFormatSim.setEditable(false);
		add(txtFormatSim, "9, 8, fill, default");
		txtFormatSim.setColumns(10);

		JLabel lblWeightSim = new JLabel("Weight Sim");
		add(lblWeightSim, "7, 10, right, default");

		txtWeightSim = new JTextField();
		txtWeightSim.setEditable(false);
		add(txtWeightSim, "9, 10, fill, default");
		txtWeightSim.setColumns(10);
	}

	public void load(Stats stats) {
		txtSimilarity.setText(String.valueOf(stats.similarity));
		txtSelfSimF.setText(String.valueOf(stats.selfSimF));
		txtSelfSimM.setText(String.valueOf(stats.selfSimM));
		txtPairSim.setText(String.valueOf(stats.pairSim));

		txtAncestorSim.setText(String.valueOf(stats.ancestorSim));
		txtEdgeTypeSim.setText(String.valueOf(stats.edgeTypeSim));
		txtEdgeTextSim.setText(String.valueOf(stats.edgeTextSim));
		txtFormatSim.setText(String.valueOf(stats.formatSim));
		txtWeightSim.setText(String.valueOf(stats.weightSim));
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
