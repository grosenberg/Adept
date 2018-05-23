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
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.certiv.adept.util.Time;
import net.certiv.adept.vis.FormatView;
import net.certiv.adept.vis.components.FontChoiceBox;
import net.certiv.adept.vis.models.LanguageListModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;

public class FormatSelectPanel extends JPanel {

	private FormatView view;
	private String fontname;

	private SourceListModel srcModel;
	private LanguageListModel langModel;

	public JComboBox<String> langBox;
	public JComboBox<Item> srcBox;
	public FontChoiceBox fontBox;
	public JComboBox<Integer> sizeBox;
	public JComboBox<Integer> tabBox;

	public JSeparator separator;

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
				new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(150dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(40dlu;default)"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.UNRELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblLanguage = new JLabel("Language");
		add(lblLanguage, "2, 1");

		langBox = new JComboBox<>();
		add(langBox, "4, 1, fill, default");

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "6, 1, right, default");

		srcBox = new JComboBox<>();
		add(srcBox, "8, 1, fill, default");

		JLabel lblTabWidth = new JLabel("Tab Width");
		add(lblTabWidth, "10, 1, right, default");

		tabBox = new JComboBox<>();
		add(tabBox, "12, 1, fill, default");

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		add(separator, "14, 1, 1, 3");

		chkFormatCode = new JCheckBox("Format Code");
		add(chkFormatCode, "16, 1");

		chkFormatComments = new JCheckBox("Format Comments");
		add(chkFormatComments, "18, 1");

		chkFormatHeader = new JCheckBox("Format Header");
		add(chkFormatHeader, "20, 1");

		JLabel lblFont = new JLabel("Font");
		add(lblFont, "6, 3, right, default");

		fontBox = new FontChoiceBox(fontname, Font.PLAIN, true);
		add(fontBox, "8, 3, fill, default");

		JLabel lblFontSize = new JLabel("Font Size");
		add(lblFontSize, "10, 3, right, default");

		sizeBox = new JComboBox<>();
		add(sizeBox, "12, 3, fill, default");

		chkAlignFields = new JCheckBox("Align Fields");
		add(chkAlignFields, "16, 3");

		chkAlignComments = new JCheckBox("Align Comments");
		add(chkAlignComments, "18, 3");

		chkBreakLines = new JCheckBox("Break Long Lines");
		add(chkBreakLines, "20, 3");

		complete();
	}

	private void initPanel(FormatView view) {
		this.view = view;

		// "Droid Sans Mono", "DejaVu Sans Mono", "Oxygen Mono", "NanumGothicCoding"
		fontname = view.prefs.get(FormatView.KEY_FONT_NAME, "Droid Sans Mono");
	}

	private void complete() {
		langModel = new LanguageListModel(FormatView.corpusRoot);
		langBox.setModel(langModel);

		Path dir = Paths.get(FormatView.rootDir, langModel.getSelected());
		srcModel = new SourceListModel(dir, langModel.getSelectedExt());
		srcBox.setModel(srcModel);

		tabBox.setModel(new DefaultComboBoxModel<>(FormatView.WIDTHS));
		sizeBox.setModel(new DefaultComboBoxModel<>(FormatView.SIZES));

		ActionListener run = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Time.clear();

				if (e.getSource() == srcBox) {
					view.process();

				} else {
					if (e.getSource() == langBox) {
						Path dir = Paths.get(FormatView.rootDir, langModel.getSelected());
						srcModel = new SourceListModel(dir, langModel.getSelectedExt());
						srcBox.setModel(srcModel);
					}
					view.loadTool(langModel.getSelected());
				}
			}
		};

		langBox.addActionListener(run);
		srcBox.addActionListener(run);

		chkFormatCode.addActionListener(run);
		chkBreakLines.addActionListener(run);
		chkFormatHeader.addActionListener(run);
		chkFormatComments.addActionListener(run);
		chkAlignFields.addActionListener(run);
		chkAlignComments.addActionListener(run);
	}
}
