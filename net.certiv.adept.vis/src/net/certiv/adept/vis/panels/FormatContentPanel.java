/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.antlr.v4.runtime.RecognitionException;

import net.certiv.adept.core.CoreMgr;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.vis.components.CodePane;
import net.certiv.adept.vis.components.FontChoiceBox;
import net.certiv.adept.vis.components.LineRuler;
import net.certiv.adept.vis.components.TabRuler;
import net.certiv.adept.vis.diff.Action;
import net.certiv.adept.vis.diff.Delta;
import net.certiv.adept.vis.diff.Myers;
import net.certiv.adept.vis.diff.Transform;
import net.certiv.adept.vis.utils.Point;
import net.certiv.adept.vis.utils.Synchronizer;
import net.certiv.adept.vis.utils.TextUtils;

public class FormatContentPanel extends JPanel {

	public static final String CLICK_LEFT = "click_left";

	private int width;
	private int height;

	private String lhsTitle;
	private String rhsTitle;
	private CodePane lhs;
	private CodePane rhs;

	private CoreMgr mgr;

	public FormatContentPanel(int width, int height, FontChoiceBox fontBox, JComboBox<Integer> sizeBox,
			JComboBox<Integer> tabBox, String lhsTitle, String rhsTitle) {

		super();
		this.width = width;
		this.height = height;
		this.lhsTitle = lhsTitle;
		this.rhsTitle = rhsTitle;

		createComponents();
		createComponentListeners(fontBox, sizeBox, tabBox);
	}

	private void createComponents() {
		JPanel lhsPanel = new JPanel();
		JPanel rhsPanel = new JPanel();
		lhsPanel.setLayout(new BorderLayout());
		rhsPanel.setLayout(new BorderLayout());

		lhs = new CodePane();
		lhs.setEditable(false);
		lhs.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				if (!lhs.isFocusOwner()) return;

				try {
					Document doc = lhs.getDocument();
					int line = TextUtils.getLineOfOffset(doc, e.getDot()); 			// 0..n
					int col = e.getDot() - TextUtils.getLineStartOffset(doc, line); // 0..n
					firePropertyChange(CLICK_LEFT, null, new Point(col, line));
				} catch (BadLocationException ex) {}
			}
		});

		rhs = new CodePane();
		rhs.setEditable(false);

		JScrollPane lhsScroll = new JScrollPane(lhs);
		lhsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		lhsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		LineRuler lLineRuler = new LineRuler(lhs);
		lhsScroll.setRowHeaderView(lLineRuler);

		TabRuler lTagRuler = new TabRuler(lhs);
		lhsScroll.setColumnHeaderView(lTagRuler);

		JScrollPane rhsScroll = new JScrollPane(rhs);
		rhsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		rhsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		LineRuler rLineRuler = new LineRuler(rhs);
		rhsScroll.setRowHeaderView(rLineRuler);

		TabRuler rTabRuler = new TabRuler(rhs);
		rhsScroll.setColumnHeaderView(rTabRuler);

		Synchronizer.link(lhsScroll, rhsScroll);

		JLabel lhsLabel = new JLabel(lhsTitle, SwingConstants.CENTER);
		lhsLabel.setForeground(Color.black);

		JLabel rhsLabel = new JLabel(rhsTitle, SwingConstants.CENTER);
		rhsLabel.setForeground(Color.black);

		lhsPanel.add(lhsLabel, BorderLayout.NORTH);
		lhsPanel.add(lhsScroll, BorderLayout.CENTER);

		rhsPanel.add(rhsLabel, BorderLayout.NORTH);
		rhsPanel.add(rhsScroll, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setSize(width, height);
		splitPane.setResizeWeight(0.5);
		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerSize(8);
		splitPane.setContinuousLayout(true);

		splitPane.setLeftComponent(lhsPanel);
		splitPane.setRightComponent(rhsPanel);
		setLayout(new BorderLayout());
		add(splitPane);
	}

	private void createComponentListeners(FontChoiceBox fontBox, JComboBox<Integer> sizeBox,
			JComboBox<Integer> tabBox) {

		fontBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Font font = fontBox.getSelectedFont();
				float size = (int) sizeBox.getSelectedItem();
				font = font.deriveFont(size);
				lhs.changeStyle(font);
				rhs.changeStyle(font);
			}
		});
		sizeBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				float size = (int) e.getItem();
				Font font = fontBox.getSelectedFont();
				font = font.deriveFont(size);
				lhs.changeStyle(font);
				rhs.changeStyle(font);
			}
		});
		tabBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				int width = (int) e.getItem();
				lhs.changeStyle(width);
				rhs.changeStyle(width);
			}
		});
	}

	public void setMgr(CoreMgr mgr) {
		this.mgr = mgr;
	}

	public void load(String lhsContent, String rhsContent) {
		clear();
		lhs.setText(lhsContent);
		rhs.setText(rhsContent);

		if (!lhsContent.isEmpty() && !rhsContent.isEmpty()) {
			diff();
		}
	}

	private void diff() {
		if (mgr != null) {
			ISourceParser parser = mgr.getLanguageParser();

			String lhsText = lhs.getText();
			String rhsText = rhs.getText();

			try {
				List<AdeptToken> lhsTokens = parser.lex(lhsText);
				List<AdeptToken> rhsTokens = parser.lex(rhsText);

				Transform transform = Myers.INST.diff(lhsTokens, rhsTokens);
				for (Delta delta : transform) {
					int[][] locs = transform.getLocations(delta);
					// Log.debug(this, String.format("@%s:%s", locs[0][0], locs[0][1]));

					Action action = delta.getAction();
					if (locs[0][1] > 0) lhs.changeStyle(action, locs[0]);
					if (locs[1][1] > 0) rhs.changeStyle(action, locs[1]);
				}

			} catch (RecognitionException e) {}
		}
	}

	/** Returns the formatted content. */
	public String getContent() {
		return rhs.getText();
	}

	public void clear() {
		lhs.clear();
		rhs.clear();
	}
}
