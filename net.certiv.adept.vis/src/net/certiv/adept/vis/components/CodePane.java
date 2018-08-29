/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

import net.certiv.adept.util.Strings;
import net.certiv.adept.vis.diff.Action;
import net.certiv.adept.vis.utils.TextUtils;

public class CodePane extends JTextPane {

	private Font _font;
	private int _tabWidth;
	private TabSet _tabSet;

	private static final SimpleAttributeSet NORMAL;
	private static final SimpleAttributeSet HIGHLIGHT;
	private static final SimpleAttributeSet ACT_ADD;
	private static final SimpleAttributeSet ACT_DEL;
	private static final SimpleAttributeSet ACT_MOD;

	static {
		NORMAL = new SimpleAttributeSet();
		StyleConstants.setForeground(NORMAL, Color.BLACK); // font
		StyleConstants.setBackground(NORMAL, Color.WHITE); // field
		StyleConstants.setBold(NORMAL, false);
		StyleConstants.setItalic(NORMAL, false);

		HIGHLIGHT = new SimpleAttributeSet();
		StyleConstants.setForeground(HIGHLIGHT, Color.BLACK);
		StyleConstants.setBackground(HIGHLIGHT, Color.LIGHT_GRAY);
		StyleConstants.setBold(HIGHLIGHT, false);
		StyleConstants.setItalic(HIGHLIGHT, false);

		ACT_ADD = new SimpleAttributeSet();
		StyleConstants.setForeground(ACT_ADD, Color.BLUE);
		StyleConstants.setBackground(ACT_ADD, new Color(127, 192, 224));
		StyleConstants.setBold(ACT_ADD, false);
		StyleConstants.setItalic(ACT_ADD, false);

		ACT_DEL = new SimpleAttributeSet();
		StyleConstants.setForeground(ACT_DEL, Color.RED);
		StyleConstants.setBackground(ACT_DEL, new Color(255, 192, 192));
		StyleConstants.setBold(ACT_DEL, true);
		StyleConstants.setItalic(ACT_DEL, false);

		ACT_MOD = new SimpleAttributeSet();
		StyleConstants.setForeground(ACT_MOD, new Color(0, 192, 0));
		StyleConstants.setBackground(ACT_MOD, new Color(224, 224, 224));
		StyleConstants.setBold(ACT_MOD, true);
		StyleConstants.setItalic(ACT_MOD, false);
	}

	public CodePane() {
		super();

		_font = getFont();
		_tabWidth = 4;
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	@Override
	public String getToolTipText(MouseEvent e) {
		int offset = viewToModel(e.getPoint());
		if (offset >= 0) {
			StyledDocument doc = (StyledDocument) getDocument();
			try {
				int line = TextUtils.getLineOfOffset(doc, offset); // 0..n
				int start = TextUtils.getLineStartOffset(doc, line);

				if (e.isControlDown()) {
					int len = TextUtils.getLineEndOffset(doc, line) - start;
					String text = doc.getText(start, len);
					return Strings.encodeWS(text);

				} else {
					int col = offset - start;
					String text = doc.getText(start, col);
					int visCol = Strings.measureVisualWidth(text, _tabWidth);
					return String.format("%s:%s %s  %s", line + 1, col, visCol, Strings.encodeWS(text));
				}

			} catch (BadLocationException e1) {}
		}
		return null;
	}

	@Override
	public void setText(String t) {
		super.setText(t);
		changeStyle(_font, _tabWidth);
		setCaretPosition(0);
	}

	public void clear() {
		super.setText("");
	}

	public void changeStyle(Font font) {
		changeStyle(font, _tabWidth);
	}

	public void changeStyle(int tabWidth) {
		changeStyle(_font, tabWidth);
	}

	public TabSet getTabSet() {
		return _tabSet;
	}

	public void changeStyle(Action action, int[] loc) {
		changeStyle(action, loc[0], loc[1]);
	}

	private void changeStyle(Action action, int offset, int len) {
		StyledDocument doc = getStyledDocument();
		if (doc.getLength() < offset + len) return;

		switch (action) {
			case ADD:
				doc.setCharacterAttributes(offset, len, ACT_ADD, false);
				break;
			case DEL:
				doc.setCharacterAttributes(offset, len, ACT_DEL, false);
				break;
			case MOD:
				doc.setCharacterAttributes(offset, len, ACT_MOD, false);
				break;
			default:
				doc.setCharacterAttributes(offset, len, NORMAL, false);
		}
	}

	public void changeStyle(Font font, int tabWidth) {
		int oldWidth = _tabWidth;

		_font = font;
		_tabWidth = tabWidth;

		StyledDocument doc = getStyledDocument();
		int len = doc.getLength();
		if (len == 0) return;

		MutableAttributeSet mattrs = getInputAttributes();
		StyleConstants.setFontFamily(mattrs, font.getFamily());
		StyleConstants.setFontSize(mattrs, font.getSize());
		StyleConstants.setItalic(mattrs, false);
		StyleConstants.setBold(mattrs, false);
		doc.setCharacterAttributes(0, len, mattrs, false);

		FontMetrics fm = getFontMetrics(font);
		_tabSet = calcTabSet(tabWidth * fm.charWidth('#'));
		SimpleAttributeSet sattrs = new SimpleAttributeSet();
		StyleConstants.setTabSet(sattrs, _tabSet);
		doc.setParagraphAttributes(0, len, sattrs, false);

		setFont(_font);
		if (oldWidth != _tabWidth) firePropertyChange("tabWidth", oldWidth, _tabWidth);
	}

	private TabSet calcTabSet(int width) {
		TabStop[] stops = new TabStop[20];
		for (int idx = 0; idx < 20; idx++) {
			stops[idx] = new TabStop((idx + 1) * width);
		}
		return new TabSet(stops);
	}

	// turns off wrapping
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return getUI().getPreferredSize(this).width <= getParent().getSize().width;
	}
}
