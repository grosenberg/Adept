/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.util.Time;
import net.certiv.adept.vis.FormatView;
import net.certiv.adept.vis.components.FontChoiceBox;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;

public class FormatSelectPanel extends JPanel {

	private FormatView view;
	private String fontname;

	public JComboBox<Item> srcBox;
	public FontChoiceBox fontBox;
	public JComboBox<Integer> sizeBox;
	public JComboBox<Integer> tabBox;

	public JCheckBox chkFormatCode;
	public JCheckBox chkFormatComments;
	public JCheckBox chkFormatHeader;
	public JCheckBox chkAlignFields;
	public JCheckBox chkAlignComments;
	public JCheckBox chkBreakLines;

	/**
	 * Create the panel.
	 */
	public FormatSelectPanel(FormatView view) {
		setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		initPanel(view);

		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(200dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(40dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "2, 1, right, default");

		srcBox = new JComboBox<>();
		add(srcBox, "4, 1, fill, default");

		JLabel lblTabWidth = new JLabel("Tab Width");
		add(lblTabWidth, "6, 1, right, default");

		tabBox = new JComboBox<>();
		add(tabBox, "8, 1, fill, default");

		chkFormatCode = new JCheckBox("Format Code");
		add(chkFormatCode, "12, 1");

		chkFormatComments = new JCheckBox("Format Comments");
		add(chkFormatComments, "14, 1");

		chkFormatHeader = new JCheckBox("Format Header");
		add(chkFormatHeader, "16, 1");

		JLabel lblFont = new JLabel("Font");
		add(lblFont, "2, 3, right, default");

		fontBox = new FontChoiceBox(fontname, Font.PLAIN, true);
		add(fontBox, "4, 3, fill, default");

		JLabel lblFontSize = new JLabel("Font Size");
		add(lblFontSize, "6, 3, right, default");

		sizeBox = new JComboBox<>();
		add(sizeBox, "8, 3, fill, default");

		chkAlignFields = new JCheckBox("Align Fields");
		add(chkAlignFields, "12, 3");

		chkAlignComments = new JCheckBox("Align Comments");
		add(chkAlignComments, "14, 3");

		chkBreakLines = new JCheckBox("Break Long Lines");
		add(chkBreakLines, "16, 3");

		complete();
	}

	private void initPanel(FormatView view) {
		this.view = view;

		// "Droid Sans Mono", "DejaVu Sans Mono", "Oxygen Mono", "NanumGothicCoding"
		fontname = view.prefs.get(FormatView.KEY_FONT_NAME, "Droid Sans Mono");
	}

	private void complete() {
		srcBox.setModel(new SourceListModel(FormatView.rootDir, FormatView.srcExt));
		tabBox.setModel(new DefaultComboBoxModel<>(FormatView.WIDTHS));
		sizeBox.setModel(new DefaultComboBoxModel<>(FormatView.SIZES));

		ActionListener run = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Time.clear();
				if (e.getSource() instanceof JCheckBox) {
					view.loadTool();
				} else {
					view.process();
				}
			}
		};

		srcBox.addActionListener(run);
		chkFormatCode.addActionListener(run);
		chkBreakLines.addActionListener(run);
		chkFormatHeader.addActionListener(run);
		chkFormatComments.addActionListener(run);
		chkAlignFields.addActionListener(run);
		chkAlignComments.addActionListener(run);
	}
}
