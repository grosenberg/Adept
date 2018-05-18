/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

import net.certiv.adept.vis.utils.TextUtils;

/**
 * This class will display line numbers for a related text editor. The text editor must use the same
 * line height for each line. LineRuler supports wrapped lines and will highlight the line number of
 * the current line in the text editor. This class was designed to be used as a editor added to the
 * row header of a JScrollPane.
 */
public class LineRuler extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {

	public final static float LEFT = 0.0f;
	public final static float CENTER = 0.5f;
	public final static float RIGHT = 1.0f;

	private final static int HEIGHT = Integer.MAX_VALUE - 1000000;
	private final static Border EDGE = new MatteBorder(0, 0, 0, 2, Color.GRAY);

	// Text editor to sync with
	private JEditorPane editor;

	// Properties that can be changed
	private int borderGap;
	private Color currentLineForeground;
	private float digitAlignment;
	private int minDigits = 2;
	private String numbersFormat = "%3d";

	// Used to reduce the number of times the editor needs to be repainted
	private int lastDigits;
	private int lastHeight;
	private int lastLine;

	/**
	 * Create a line number editor for a text editor. This minimum display width will be based on 3
	 * digits.
	 *
	 * @param editor the related text editor
	 */
	public LineRuler(JEditorPane editor) {
		this(editor, 3);
	}

	/**
	 * Create a line number editor for a text editor.
	 *
	 * @param editor the related text editor
	 * @param minDigits the number of digits used to calculate the minimum width of the editor
	 */
	public LineRuler(JEditorPane editor, int minDigits) {
		this.editor = editor;

		setFont(editor.getFont());

		setBorderGap(5);
		setCurrentLineForeground(Color.LIGHT_GRAY);
		setDigitAlignment(RIGHT);
		setMinimumDisplayDigits(minDigits);

		editor.getDocument().addDocumentListener(this);
		editor.addCaretListener(this);
		editor.addPropertyChangeListener("font", this);
	}

	/**
	 * Gets the border gap
	 *
	 * @return the border gap in pixels
	 */
	public int getBorderGap() {
		return borderGap;
	}

	/**
	 * The border gap is used in calculating the left and right insets of the border. Default value is
	 * 5.
	 *
	 * @param borderGap the gap in pixels
	 */
	public void setBorderGap(int borderGap) {
		this.borderGap = borderGap;
		Insets ein = editor.getInsets();
		Border inner = new EmptyBorder(ein.top, borderGap, ein.bottom, borderGap);
		setBorder(new CompoundBorder(EDGE, inner));
		lastDigits = 0;
		setPreferredWidth(true);
	}

	/**
	 * Gets the current line rendering Color
	 *
	 * @return the Color used to render the current line number
	 */
	public Color getCurrentLineForeground() {
		return currentLineForeground == null ? getForeground() : currentLineForeground;
	}

	/**
	 * The Color used to render the current line digits. Default is Coolor.RED.
	 *
	 * @param currentLineForeground the Color used to render the current line
	 */
	public void setCurrentLineForeground(Color currentLineForeground) {
		this.currentLineForeground = currentLineForeground;
	}

	/**
	 * Gets the digit inLine
	 *
	 * @return the inLine of the painted digits
	 */
	public float getDigitAlignment() {
		return digitAlignment;
	}

	/**
	 * Specify the horizontal inLine of the digits within the editor. Common values would be:
	 * <ul>
	 * <li>LineRuler.LEFT
	 * <li>LineRuler.CENTER
	 * <li>LineRuler.RIGHT (default)
	 * </ul>
	 *
	 * @param currentLineForeground the Color used to render the current line
	 */
	public void setDigitAlignment(float digitAlignment) {
		this.digitAlignment = digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
	}

	/**
	 * Gets the minimum display digits
	 *
	 * @return the minimum display digits
	 */
	public int getMinimumDisplayDigits() {
		return minDigits;
	}

	/**
	 * Specify the mimimum number of digits used to calculate the preferred width of the editor. Default
	 * is 3.
	 *
	 * @param minDigits the number digits used in the preferred width calculation
	 */
	public void setMinimumDisplayDigits(int minimumDisplayDigits) {
		this.minDigits = minimumDisplayDigits;
		setPreferredWidth(true);
	}

	/**
	 * Calculate the width needed to display the maximum line number
	 */
	private void setPreferredWidth(boolean force) {
		int lines = TextUtils.getLineCount(editor.getDocument());
		int digits = Math.max(String.valueOf(lines).length(), minDigits);

		// Update sizes when number of digits in the line number changes
		if (force || lastDigits != digits) {
			lastDigits = digits;
			numbersFormat = "%" + digits + "d";
			FontMetrics fontMetrics = getFontMetrics(getFont());
			int width = fontMetrics.charWidth('0') * digits;
			Insets insets = getInsets();
			int preferredWidth = insets.left + insets.right + width;

			Dimension d = getPreferredSize();
			d.setSize(preferredWidth, HEIGHT);
			setPreferredSize(d);
			setSize(d);
		}
	}

	/** Draw the line numbers */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Document doc = editor.getDocument();
		int currentLine = TextUtils.getLineNumber(doc, editor.getCaretPosition());
		int maxLines = TextUtils.getLineCount(doc);

		FontMetrics fontMetrics = getFontMetrics(editor.getFont());
		int lh = fontMetrics.getHeight();

		TextUtils.setRenderingHits((Graphics2D) g);
		Rectangle clip = g.getClip().getBounds();
		int topLine = (int) (clip.getY() / lh);
		int botLine = Math.min(maxLines, (int) (clip.getHeight() + lh - 1) / lh + topLine + 1);

		Insets insets = getInsets();

		for (int line = topLine; line < botLine; line++) {
			String lineNumber = String.format(numbersFormat, line + 1);
			int y = line * lh + insets.top;
			int yt = y + fontMetrics.getAscent();
			if (line == currentLine) {
				g.setColor(getCurrentLineForeground());
				g.fillRect(0, y /* - lh + fontMetrics.getDescent() - 1 */, getWidth(), lh);
				g.setColor(getForeground());
				g.drawString(lineNumber, insets.left, yt);
			} else {
				g.drawString(lineNumber, insets.left, yt);
			}
		}
	}

	//
	// Implement CaretListener interface
	//
	@Override
	public void caretUpdate(CaretEvent e) {
		// Get the line the caret is positioned on

		int caretPosition = editor.getCaretPosition();
		Element root = editor.getDocument().getDefaultRootElement();
		int currentLine = root.getElementIndex(caretPosition);

		// Need to repaint so the correct line number can be highlighted

		if (lastLine != currentLine) {
			repaint();
			lastLine = currentLine;
		}
	}

	// Implement DocumentListener interface
	@Override
	public void changedUpdate(DocumentEvent e) {
		documentChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		documentChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		documentChanged();
	}

	/*
	 * A document change may affect the number of displayed lines of text. Therefore the lines numbers
	 * will also change.
	 */
	private void documentChanged() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				int preferredHeight = editor.getPreferredSize().height;
				if (lastHeight != preferredHeight) {
					setPreferredWidth(false);
					repaint();
					lastHeight = preferredHeight;
				}
			}
		});
	}

	// Implement PropertyChangeListener interface
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() instanceof Font) {
			float size = getFont().getSize();
			Font font = (Font) evt.getNewValue();
			setFont(font.deriveFont(size));
			setPreferredWidth(true);
		}
	}
}
