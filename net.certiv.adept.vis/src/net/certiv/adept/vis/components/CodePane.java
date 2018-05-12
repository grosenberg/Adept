package net.certiv.adept.vis.components;

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
import net.certiv.adept.vis.utils.TextUtils;

public class CodePane extends JTextPane {

	private Font _font;
	private int _tabWidth;
	private TabSet _tabSet;

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
					return String.format("%s:%s %s  %s", line, col, visCol, Strings.encodeWS(text));
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

	public void changeStyle(Font font, int tabWidth) {
		int oldWidth = _tabWidth;

		_font = font;
		_tabWidth = tabWidth;

		StyledDocument doc = getStyledDocument();
		int len = doc.getLength();
		if (len == 0) return;

		MutableAttributeSet attrs = getInputAttributes();
		StyleConstants.setFontFamily(attrs, font.getFamily());
		StyleConstants.setFontSize(attrs, font.getSize());
		StyleConstants.setItalic(attrs, false);
		StyleConstants.setBold(attrs, false);
		doc.setCharacterAttributes(0, len, attrs, false);

		FontMetrics fm = getFontMetrics(font);
		_tabSet = calcTabSet(tabWidth * fm.charWidth('#'));
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setTabSet(attributes, _tabSet);
		doc.setParagraphAttributes(0, len, attributes, false);

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
