package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.LinkedHashMap;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.google.common.collect.BiMap;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.Feature;

public class TopoPanel extends JPanel {

	private JTextField txtLine;
	private JTextField txtAspect;
	private JTextField txtNode1;
	private JTextField txtType;
	private JTextField txtDocName;
	private JTextField txtCol;
	private JCheckBox chkVariable;

	private JPanel panel;

	private JTextField txtAspect0;
	private JTextField txtAspect1;
	private JTextField txtAspect2;
	private JTextField txtAspect3;
	private JTextField txtAspect4;
	private JTextField txtAspect5;
	private JTextField txtAspect6;
	private JTextField txtAspect7;
	private JTextField txtAspect8;
	private JTextField txtAspect9;
	private JTextField txtAspect10;
	private JTextField txtAspect11;
	private JTextField txtAspect12;
	private JTextField txtAspect13;
	private JTextField txtAspect14;
	private JTextField txtAspect15;

	private JTextField txtNode13;
	private JTextField txtNode9;
	private JTextField txtNode5;
	private JTextField txtNode14;
	private JTextField txtNode2;
	private JTextField txtNode15;
	private JTextField txtNode6;
	private JTextField txtNode10;
	private JTextField txtNode11;
	private JTextField txtNode7;
	private JTextField txtNode0;
	private JTextField txtNode8;
	private JTextField txtNode12;
	private JTextField txtNode3;
	private JTextField txtNode4;

	private BiMap<Feature, Integer> vertices;
	private LinkedHashMap<Integer, JTextField> nodeMap;
	private Point2D[] initData;

	public TopoPanel(Font font) {
		setFont(font);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(35dlu;default):grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.UNRELATED_GAP_ROWSPEC, }));

		JLabel lblDesc = new JLabel("Topology Description");
		add(lblDesc, "2, 2, 3, 1, left, default");

		JLabel lblType = new JLabel("Type");
		add(lblType, "3, 4, right, default");

		txtType = new JTextField();
		txtType.setEditable(false);
		add(txtType, "5, 4, fill, default");

		JLabel lblLine = new JLabel("Line");
		add(lblLine, "7, 4, right, default");

		txtLine = new JTextField();
		txtLine.setColumns(4);
		txtLine.setEditable(false);
		add(txtLine, "9, 4, fill, default");

		JLabel lblCol = new JLabel("Col");
		add(lblCol, "11, 4, right, default");

		txtCol = new JTextField();
		txtCol.setEditable(false);
		add(txtCol, "13, 4, fill, default");
		txtCol.setColumns(4);

		JLabel lblAspect = new JLabel("Aspect");
		add(lblAspect, "15, 4, right, default");

		txtAspect = new JTextField();
		txtAspect.setEditable(false);
		add(txtAspect, "17, 4, fill, default");

		chkVariable = new JCheckBox("Variable");
		add(chkVariable, "19, 4, left, default");

		JLabel lblDoc = new JLabel("Doc Name");
		add(lblDoc, "3, 6, right, default");

		txtDocName = new JTextField();
		txtDocName.setEditable(false);
		add(txtDocName, "5, 6, 13, 1, fill, default");

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Vertex Values",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "5, 8, 15, 21, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblEdgeTypes = new JLabel("Node 0");
		panel.add(lblEdgeTypes, "2, 2, right, default");

		txtAspect0 = new JTextField();
		txtAspect0.setEditable(false);
		panel.add(txtAspect0, "4, 2, fill, default");
		txtAspect0.setColumns(14);
		txtAspect0.setName("100");

		txtNode0 = new JTextField();
		panel.add(txtNode0, "6, 2");
		txtNode0.setEditable(false);
		txtNode0.setColumns(10);
		txtNode0.setName("0");

		JLabel lblOverlap = new JLabel("Node 8");
		panel.add(lblOverlap, "8, 2, right, default");

		txtAspect8 = new JTextField();
		txtAspect8.setEditable(false);
		panel.add(txtAspect8, "10, 2, fill, default");
		txtAspect8.setColumns(14);
		txtAspect8.setName("108");

		txtNode8 = new JTextField();
		panel.add(txtNode8, "12, 2");
		txtNode8.setEditable(false);
		txtNode8.setColumns(10);
		txtNode8.setName("8");

		JLabel lblText = new JLabel("Node 1");
		panel.add(lblText, "2, 4, right, default");

		txtAspect1 = new JTextField();
		txtAspect1.setEditable(false);
		panel.add(txtAspect1, "4, 4, fill, default");
		txtAspect1.setColumns(10);
		txtAspect1.setName("101");

		txtNode1 = new JTextField();
		panel.add(txtNode1, "6, 4");
		txtNode1.setEditable(false);
		txtNode1.setColumns(10);
		txtNode1.setName("1");

		JLabel lblDents = new JLabel("Node 9");
		panel.add(lblDents, "8, 4, right, default");

		txtAspect9 = new JTextField();
		txtAspect9.setEditable(false);
		panel.add(txtAspect9, "10, 4, fill, default");
		txtAspect9.setColumns(10);
		txtAspect9.setName("109");

		txtNode9 = new JTextField();
		panel.add(txtNode9, "12, 4");
		txtNode9.setEditable(false);
		txtNode9.setColumns(10);
		txtNode9.setName("9");

		JLabel lblSelfSim = new JLabel("Node 2");
		panel.add(lblSelfSim, "2, 6, right, default");

		txtAspect2 = new JTextField();
		txtAspect2.setEditable(false);
		panel.add(txtAspect2, "4, 6, fill, default");
		txtAspect2.setColumns(10);
		txtAspect2.setName("102");

		txtNode2 = new JTextField();
		panel.add(txtNode2, "6, 6");
		txtNode2.setEditable(false);
		txtNode2.setColumns(10);
		txtNode2.setName("2");

		JLabel lblMutualSim = new JLabel("Node 10");
		panel.add(lblMutualSim, "8, 6, right, default");

		txtAspect10 = new JTextField();
		txtAspect10.setEditable(false);
		panel.add(txtAspect10, "10, 6, fill, default");
		txtAspect10.setColumns(10);
		txtAspect10.setName("110");

		txtNode10 = new JTextField();
		panel.add(txtNode10, "12, 6");
		txtNode10.setEditable(false);
		txtNode10.setColumns(10);
		txtNode10.setName("10");

		JLabel lblIntersectTypes = new JLabel("Node 3");
		panel.add(lblIntersectTypes, "2, 8, right, default");

		txtAspect3 = new JTextField();
		txtAspect3.setEditable(false);
		panel.add(txtAspect3, "4, 8, fill, default");
		txtAspect3.setColumns(10);
		txtAspect3.setName("103");

		txtNode3 = new JTextField();
		panel.add(txtNode3, "6, 8");
		txtNode3.setEditable(false);
		txtNode3.setColumns(10);
		txtNode3.setName("3");

		JLabel lblIntersect = new JLabel("Node 11");
		panel.add(lblIntersect, "8, 8, right, default");

		txtAspect11 = new JTextField();
		txtAspect11.setEditable(false);
		panel.add(txtAspect11, "10, 8, fill, default");
		txtAspect11.setColumns(10);
		txtAspect11.setName("111");

		txtNode11 = new JTextField();
		panel.add(txtNode11, "12, 8");
		txtNode11.setEditable(false);
		txtNode11.setColumns(10);
		txtNode11.setName("11");

		JLabel lblDisjointTypes = new JLabel("Node 4");
		panel.add(lblDisjointTypes, "2, 10, right, default");

		txtAspect4 = new JTextField();
		txtAspect4.setEditable(false);
		panel.add(txtAspect4, "4, 10, fill, default");
		txtAspect4.setColumns(10);
		txtAspect4.setName("104");

		txtNode4 = new JTextField();
		panel.add(txtNode4, "6, 10");
		txtNode4.setEditable(false);
		txtNode4.setColumns(10);
		txtNode4.setName("4");

		JLabel lblDisjoint = new JLabel("Node 12");
		panel.add(lblDisjoint, "8, 10, right, default");

		txtAspect12 = new JTextField();
		txtAspect12.setEditable(false);
		panel.add(txtAspect12, "10, 10, fill, default");
		txtAspect12.setColumns(10);
		txtAspect12.setName("112");

		txtNode12 = new JTextField();
		panel.add(txtNode12, "12, 10");
		txtNode12.setEditable(false);
		txtNode12.setColumns(10);
		txtNode12.setName("12");

		JLabel lblFormat = new JLabel("Node 5");
		panel.add(lblFormat, "2, 12, right, default");

		txtAspect5 = new JTextField();
		txtAspect5.setEditable(false);
		panel.add(txtAspect5, "4, 12, fill, default");
		txtAspect5.setColumns(10);
		txtAspect5.setName("105");

		txtNode5 = new JTextField();
		panel.add(txtNode5, "6, 12");
		txtNode5.setEditable(false);
		txtNode5.setColumns(10);
		txtNode5.setName("5");

		JLabel lblFacets = new JLabel("Node 13");
		panel.add(lblFacets, "8, 12, right, default");

		txtAspect13 = new JTextField();
		txtAspect13.setEditable(false);
		panel.add(txtAspect13, "10, 12, fill, default");
		txtAspect13.setColumns(10);
		txtAspect13.setName("113");

		txtNode13 = new JTextField();
		panel.add(txtNode13, "12, 12");
		txtNode13.setEditable(false);
		txtNode13.setColumns(10);
		txtNode13.setName("13");

		JLabel lblSelfSimM = new JLabel("Node 6");
		panel.add(lblSelfSimM, "2, 14, right, default");

		txtAspect6 = new JTextField();
		txtAspect6.setEditable(false);
		panel.add(txtAspect6, "4, 14, fill, default");
		txtAspect6.setColumns(10);
		txtAspect6.setName("106");

		txtNode6 = new JTextField();
		panel.add(txtNode6, "6, 14");
		txtNode6.setEditable(false);
		txtNode6.setColumns(10);
		txtNode6.setName("6");

		JLabel lblFeatSim = new JLabel("Node 14");
		panel.add(lblFeatSim, "8, 14, right, default");

		txtAspect14 = new JTextField();
		txtAspect14.setEditable(false);
		panel.add(txtAspect14, "10, 14, fill, default");
		txtAspect14.setColumns(10);
		txtAspect14.setName("114");

		txtNode14 = new JTextField();
		panel.add(txtNode14, "12, 14");
		txtNode14.setEditable(false);
		txtNode14.setColumns(10);
		txtNode14.setName("14");

		JLabel lblEdgeDis = new JLabel("Node 7");
		panel.add(lblEdgeDis, "2, 16, right, default");

		txtAspect7 = new JTextField();
		txtAspect7.setEditable(false);
		panel.add(txtAspect7, "4, 16, fill, default");
		txtAspect7.setColumns(10);
		txtAspect7.setName("107");

		txtNode7 = new JTextField();
		panel.add(txtNode7, "6, 16");
		txtNode7.setEditable(false);
		txtNode7.setColumns(10);
		txtNode7.setName("7");

		JLabel lblEdgeSim = new JLabel("Node 15");
		panel.add(lblEdgeSim, "8, 16, right, default");

		txtAspect15 = new JTextField();
		txtAspect15.setEditable(false);
		panel.add(txtAspect15, "10, 16, fill, default");
		txtAspect15.setColumns(10);
		txtAspect15.setName("115");

		txtNode15 = new JTextField();
		panel.add(txtNode15, "12, 16");
		txtNode15.setEditable(false);
		txtNode15.setColumns(10);
		txtNode15.setName("15");
	}

	public void load(BiMap<Feature, Integer> vertices, Feature feature) {
		this.vertices = vertices;
		CorpusModel model = feature.getMgr().getCorpusModel();

		txtType.setText(String.valueOf(feature.getType()));
		txtLine.setText(String.valueOf(feature.getLine()));
		txtCol.setText(String.valueOf(feature.getCol()));
		txtAspect.setText(feature.getAspect());
		chkVariable.setSelected(feature.isVar());
		txtDocName.setText(model.getPathname(feature.getDocId()));
	}

	public void setInitLocations(Point2D[] xydata) {
		initData = new Point2D[xydata.length];
		for (int idx = 0; idx < xydata.length; idx++) {
			initData[idx] = (Point2D) xydata[idx].clone();
		}

		if (nodeMap == null) {
			nodeMap = new LinkedHashMap<>();
			for (Component child : panel.getComponents()) {
				if (child instanceof JTextField) {
					JTextField txtField = (JTextField) child;
					int n = Integer.valueOf(txtField.getName()).intValue();
					nodeMap.put(n, txtField);
				}
			}
		}
	}

	public void setStepLocations(Point2D[] stepData) {
		for (int idx = 0; idx < stepData.length; idx++) {
			JTextField txtAspect = nodeMap.get(idx + 100);
			JTextField txtField = nodeMap.get(idx);
			Feature feature = vertices.inverse().get(idx);
			String aspect = feature.getAspect();
			Point2D i = initData[idx];
			Point2D s = stepData[idx];
			String msg = String.format("%4.0fx:%4.0fy => %4.0fx:%4.0fy", i.getX(), i.getY(), s.getX(), s.getY());

			txtAspect.setText(aspect);
			txtField.setText(msg);
		}
	}

	public void clear() {
		clear(this);
	}

	private void clear(JPanel panel) {
		for (Component child : panel.getComponents()) {
			if (child instanceof JTextField) {
				((JTextField) child).setText("");
			} else if (child instanceof JCheckBox) {
				((JCheckBox) child).setSelected(false);
			} else if (child instanceof JPanel) {
				clear((JPanel) child);
			}
		}
	}
}
