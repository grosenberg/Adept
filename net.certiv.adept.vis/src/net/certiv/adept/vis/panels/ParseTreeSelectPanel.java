/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
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
import net.certiv.adept.vis.ParseTreeGraph;
import net.certiv.adept.vis.models.LanguageListModel;
import net.certiv.adept.vis.models.SourceListModel;
import net.certiv.adept.vis.models.SourceListModel.Item;

public class ParseTreeSelectPanel extends JPanel {

	private ParseTreeGraph graph;
	private LanguageListModel langModel;
	private SourceListModel srcModel;

	public JComboBox<String> langBox;
	public JComboBox<Item> srcBox;
	public JButton btnCapture;

	/**
	 * Create the panel.
	 */
	public ParseTreeSelectPanel(ParseTreeGraph graph) {
		setBorder(new TitledBorder(null, "Source", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		initPanel(graph);

		setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.UNRELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(50dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(150dlu;default)"), FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, }));

		JLabel lblLanguage = new JLabel("Language");
		add(lblLanguage, "2, 1");

		langBox = new JComboBox<>();
		add(langBox, "4, 1, fill, default");

		JLabel lblDoc = new JLabel("Document");
		add(lblDoc, "6, 1, right, default");

		srcBox = new JComboBox<>();
		add(srcBox, "8, 1, fill, default");

		btnCapture = new JButton("Capture");
		add(btnCapture, "11, 1");

		complete();
	}

	private void initPanel(ParseTreeGraph graph) {
		this.graph = graph;
	}

	private void complete() {
		langModel = new LanguageListModel(FormatView.corpusRoot);
		langBox.setModel(langModel);

		Path dir = Paths.get(FormatView.rootDir, langModel.getSelected());
		srcModel = new SourceListModel(dir, langModel.getSelectedExt());
		srcBox.setModel(srcModel);

		ActionListener run = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Time.clear();

				if (e.getSource() == srcBox) {
					graph.process();

				} else {
					if (e.getSource() == langBox) {
						Path dir = Paths.get(FormatView.rootDir, langModel.getSelected());
						srcModel = new SourceListModel(dir, langModel.getSelectedExt());
						srcBox.setModel(srcModel);
					}
					graph.loadTool(langModel.getSelected());
				}
			}
		};

		langBox.addActionListener(run);
		srcBox.addActionListener(run);
	}
}
