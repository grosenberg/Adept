/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.LineMetrics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.View;

/**
 * A ruler that displays tabs for the document.
 *
 * @author Scott Violet
 */
public class TabRuler extends JPanel implements CaretListener, PropertyChangeListener {

	protected int DPI = Toolkit.getDefaultToolkit().getScreenResolution();
	protected final int H_DPI = DPI / 2;
	protected final int Q_DPI = DPI / 4;
	protected final int E_DPI = DPI / 8;

	// Sizes for drawing tab glyphs.
	protected static final int TabSize = 2;
	protected static final int TabWidth = 6;
	protected static final int TabHeight = 4;

	private static final Border DefaultBorder = new RulerBorder();
	private static final Color DefaultBackground = new JTextField().getBackground();

	private JTextPane editor;
	private TabSet tabs;

	private Element paragraph;
	private int pOffset;
	private boolean offsetValid;

	private Font unitsFont;
	private int fontHeight;
	private int fontAscent;

	public TabRuler() {
		setBackground(DefaultBackground);
		setBorder(DefaultBorder);
	}

	public TabRuler(JTextPane editor) {
		this();
		setTextPane(editor);
	}

	/** Sets the text pane tabs are rendered for. */
	public void setTextPane(JTextPane editor) {
		if (this.editor != null) {
			this.editor.removeCaretListener(this);
			this.editor.removePropertyChangeListener(this);
		}
		this.editor = editor;
		if (this.editor != null) {
			this.editor.addCaretListener(this);
			this.editor.removePropertyChangeListener(this);
			updateTabSet(this.editor.getSelectionStart());
		} else {
			updateTabSet(0);
		}
	}

	/** Gets the text pane tabs are being rendered for. */
	public JTextPane getTextPane() {
		return editor;
	}

	/**
	 * Called when the caret position is updated.
	 *
	 * @param e the caret event
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		updateTabSet(Math.min(e.getDot(), e.getMark()));
	}

	/**
	 * Resets the TabSet, which determines what to display, to be the TabSet in the Paragraph Element at
	 * <code>charPosition</code>.
	 */
	protected void updateTabSet(int charPosition) {
		JTextPane editor = getTextPane();
		TabSet newTabs;

		if (editor != null) {
			Element pe = editor.getStyledDocument().getParagraphElement(charPosition);

			if (pe != paragraph) {
				Integer newOffset = determineOffset(pe);

				paragraph = pe;
				newTabs = StyleConstants.getTabSet(pe.getAttributes());
				if (newOffset == null) {
					offsetValid = false;
				} else if (newOffset.intValue() != pOffset) {
					offsetValid = true;
					pOffset = newOffset.intValue();
					if (tabs == newTabs) repaint();
				}
			} else {
				newTabs = tabs;
			}
		} else {
			newTabs = null;
		}
		if (tabs != newTabs) {
			tabs = newTabs;
			repaint();
		}
	}

	/**
	 * Sets the tabs the receiver represents, forces a repaint.
	 */
	protected void setTabSet(TabSet tabs) {
		this.tabs = tabs;
		repaint();
	}

	/**
	 * Returns the current TabSet, which may be null.
	 */
	protected TabSet getTabSet() {
		tabs = ((CodePane) editor).getTabSet();
		return tabs;
	}

	/**
	 * Returns the offset, along the x axis, tabs are to start from.
	 */
	protected int getXOffset() {
		if (!offsetValid && getParagraphElement() != null) {
			Integer offset = determineOffset(getParagraphElement());
			if (offset == null) return 0;

			pOffset = offset;
			offsetValid = true;
			repaint();
		}
		return pOffset;
	}

	/**
	 * Returns the current paragraph element. If the selection extends across multiple paragraphs this
	 * will return the first paragraph.
	 */
	protected Element getParagraphElement() {
		return paragraph;
	}

	// ================================================
	// Painting methods

	/**
	 * Messaged to paint the Component, will fill the background and message paintUnits and paintTabs.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Rectangle clip = g.getClipBounds();
		Insets insets = getInsets();

		updateFont();
		g.setColor(getBackground());
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		paintUnits(g, clip, insets);
		paintTabMarks(g, clip, insets);
	}

	/** Paint tics. */
	protected void paintUnits(Graphics g, Rectangle clip, Insets insets) {
		g.setFont(getUnitsFont());
		g.setColor(getUnitsColor());

		int xOffset = getXOffset();
		int fontY = getUnitsFontAscent();
		int midY = getUnitsFontHeight() / 2;
		int dpiOffset = xOffset % DPI;

		if (insets != null) {
			fontY += insets.top;
			midY += insets.top;
		}

		FontMetrics fm = g.getFontMetrics();
		int beg = Math.max(xOffset, clip.x / DPI * DPI + dpiOffset);
		int maxX = clip.x + clip.width;
		for (int tic = beg; tic <= maxX; tic += E_DPI) {
			int tempX = tic - dpiOffset;
			if (tempX % DPI == 0) {
				String numString = Integer.toString((tic - xOffset) / DPI);
				g.drawString(numString, tic - fm.stringWidth(numString) / 2, fontY);

			} else if (tempX % H_DPI == 0) {
				g.drawLine(tic, midY - 3, tic, midY + 3);

			} else if (tempX % Q_DPI == 0) {
				g.drawLine(tic, midY - 2, tic, midY + 2);

			} else {
				g.drawLine(tic, midY - 1, tic, midY + 1);
			}
		}
	}

	/** Paints the tab marks. */
	protected void paintTabMarks(Graphics g, Rectangle clip, Insets insets) {
		int xOffset = getXOffset();
		TabSet tabs = getTabSet();
		int lastX = clip.x - 10;
		int maxX = clip.x + clip.width + 10;
		int maxY = getUnitsFontHeight() + TabHeight;

		if (insets != null) {
			maxY += insets.top;
		}
		if (tabs == null) {
			g.setColor(getSynthesizedTabColor());
			// Paragraph treats a null tabset as a tab at every 72 pixels.
			lastX = Math.max(xOffset, lastX / DPI * DPI + xOffset % DPI);
			while (lastX <= maxX) {
				paintTabMark(g, clip, lastX, maxY, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
				lastX += DPI;
			}
		} else {
			TabStop tab;
			g.setColor(getTabColor());
			do {
				tab = tabs.getTabAfter(lastX + .01f);
				if (tab != null) {
					lastX = (int) tab.getPosition() + xOffset;
					if (lastX <= maxX) {
						paintTabMark(g, clip, lastX, maxY, tab.getAlignment(), tab.getLeader());
					} else {
						tab = null;
					}
				}
			} while (tab != null);
		}
	}

	/**
	 * Paints a particular tab marker. <code>tab</code> may be null, indicating a synthesized tab is
	 * being painted.
	 */
	protected void paintTabMark(Graphics g, Rectangle clip, int xPos, int maxY, int alignment, int leader) {
		switch (alignment) {
			case TabStop.ALIGN_LEFT:
				int[] xx = { xPos, xPos - 3, xPos + 3 };
				int[] yy = { maxY - TabHeight, maxY, maxY };
				g.fillPolygon(xx, yy, 3);
				// g.fillRect(xPos, maxY - TabHeight, TabSize, TabHeight);
				// g.fillRect(xPos, maxY - TabSize, TabWidth + TabSize, TabSize);
				break;
			case TabStop.ALIGN_RIGHT:
				g.fillRect(xPos, maxY - TabHeight, TabSize, TabHeight);
				g.fillRect(xPos - TabWidth, maxY - TabSize, TabWidth, TabSize);
				break;
			case TabStop.ALIGN_DECIMAL:
				g.fillRect(xPos, maxY - TabHeight - TabSize - 2, TabSize, TabSize);
			case TabStop.ALIGN_CENTER:
				g.fillRect(xPos, maxY - TabHeight, TabSize, TabHeight);
				g.fillRect(xPos - TabWidth, maxY - TabSize, TabWidth * 2 + TabSize, TabSize);
				break;
			default:
				break;
		}
	}

	/**
	 * Returns the color to use for the units and ticks.
	 */
	protected Color getUnitsColor() {
		return Color.black;
	}

	/**
	 * Returns the Font to use for the units. Override this to specify a different font.
	 */
	protected Font getUnitsFont() {
		return getFont();
	}

	/**
	 * Returns the color to draw the actual tabs in.
	 */
	protected Color getTabColor() {
		return Color.black;
	}

	/**
	 * Returns the color to draw generated tabs in (tabs are generated if there is no TabSet set on a
	 * particular Element).
	 */
	protected Color getSynthesizedTabColor() {
		return Color.lightGray;
	}

	//
	// Component methods
	//

	@Override
	public Dimension getPreferredSize() {
		updateFont();

		Insets insets = getInsets();

		if (insets != null) {
			return new Dimension(insets.left + insets.right + 10,
					insets.top + insets.bottom + getUnitsFontHeight() + TabHeight);
		}
		return new Dimension(10, getUnitsFontHeight());
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/** The ascent of the units font. */
	protected int getUnitsFontAscent() {
		return fontAscent;
	}

	/** Returns the height of the ruler tray. */
	protected int getUnitsFontHeight() {
		return fontHeight;
	}

	/** Updates font height information. */
	private void updateFont() {
		Font font = getUnitsFont();
		if (unitsFont != font) {
			fontHeight = fontAscent = 0;
			if (font != null) {
				Toolkit kit = getToolkit();
				if (kit != null) {
					LineMetrics fm = font.getLineMetrics("Ag", FontChoiceBox.FRC);
					if (fm != null) {
						fontHeight = Math.round(fm.getHeight());
						fontAscent = Math.round(fm.getAscent());
						unitsFont = font;
					}
				}
			}
		}
	}

	/**
	 * Determines the offset (along the x axis) from which tabs are to begin. This is obtained from the
	 * bounds of the View that represents <code>paragraph</code>. A return value of null indicates the
	 * offset could not be obtained.
	 */
	protected Integer determineOffset(Element paragraph) {
		JTextPane text = getTextPane();

		if (text != null) {
			// This is a workaround to avoid a NullPointerException that
			// is fixed in post swing 1.1 (JDK1.2).
			try {
				if (text.modelToView(paragraph.getStartOffset()) == null) {
					return null;
				}
			} catch (BadLocationException ble) {
				return null;
			}

			// This assumes the views are layed out sequentially.
			Insets insets = text.getInsets();
			Rectangle alloc = new Rectangle(text.getSize());
			TextUI ui = text.getUI();
			View view = ui.getRootView(text);
			int offset = paragraph.getStartOffset();

			alloc.x += insets.left;
			alloc.y += insets.top;
			alloc.width -= insets.left + insets.right;
			alloc.height -= insets.top + insets.bottom;

			Shape bounds = alloc;

			while (view != null && view.getElement() != paragraph) {
				int nchildren = view.getViewCount();
				// int index;
				int lower = 0;
				int upper = nchildren - 1;
				int mid = 0;
				int p0 = view.getStartOffset();
				int p1;

				if (nchildren == 0 || offset >= view.getEndOffset() || offset < view.getStartOffset()) {
					view = null;
				} else {
					boolean found = false;
					while (lower <= upper) {
						mid = lower + ((upper - lower) / 2);
						View v = view.getView(mid);
						p0 = v.getStartOffset();
						p1 = v.getEndOffset();
						if ((offset >= p0) && (offset < p1)) {
							// found the location
							found = true;
							bounds = view.getChildAllocation(mid, bounds);
							view = v;
							lower = upper + 1;
						} else if (offset < p0) {
							upper = mid - 1;
						} else {
							lower = mid + 1;
						}
					}
					if (!found) {
						view = null;
					}
				}
			}
			if (view != null && bounds != null) {
				return new Integer(bounds.getBounds().x);
			}
		}
		return null;
	}

	/**
	 * Returns the TabStop closest to the passed in location. This may return null, or this may return a
	 * synthesized tab if there are currently no tabs and the location is close to a synthesized tab.
	 */
	protected TabStop getTabClosestTo(int xLocation, int yLocation) {
		TabSet tabs = getTabSet();

		xLocation -= getXOffset();
		float xFloat = xLocation;
		if (tabs == null) {
			if (xLocation % DPI <= 5) {
				return new TabStop(xLocation / DPI * DPI);
			}
		} else {
			for (int counter = tabs.getTabCount() - 1; counter >= 0; counter--) {
				TabStop tab = tabs.getTab(counter);
				switch (tab.getAlignment()) {
					case TabStop.ALIGN_LEFT:
						if (xFloat >= tab.getPosition() && xFloat <= (tab.getPosition() + TabWidth + 2)) {
							return tab;
						}
						break;
					case TabStop.ALIGN_RIGHT:
						if (xFloat <= tab.getPosition() && xFloat >= (tab.getPosition() - TabWidth)) {
							return tab;
						}
						break;
					case TabStop.ALIGN_CENTER:
					case TabStop.ALIGN_DECIMAL:
						if (xFloat >= (tab.getPosition() - TabWidth) && xFloat <= (tab.getPosition() + TabWidth)) {
							return tab;
						}
						break;
					default:
						break;
				}
			}
		}
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "font":
				if (evt.getNewValue() instanceof Font) {
					setFont((Font) evt.getNewValue());
				}
				break;

			case "tabWidth":
				if (evt.getNewValue() instanceof Integer) {
					float size = (int) evt.getNewValue();
					setFont(getFont().deriveFont(size));
				}
				break;

			default:
		}
	}

	/** Draws a little border around the TabRuler. */
	private static class RulerBorder implements Border {
		protected static final Insets DefaultInsets = new Insets(2, 0, 4, 0);

		/**
		 * Paints the border for the specified component with the specified position and size.
		 *
		 * @param c the component for which this border is being painted
		 * @param g the paint graphics
		 * @param x the x position of the painted border
		 * @param y the y position of the painted border
		 * @param width the width of the painted border
		 * @param height the height of the painted border
		 */
		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(Color.darkGray);
			g.drawLine(x, y + 1, x + width, y + 1);
			g.setColor(Color.lightGray);
			g.drawLine(x, y, x + width, y);
			g.fillRect(x, y + height - 3, width, 2);
		}

		/**
		 * Returns the insets of the border.
		 *
		 * @param c the component for which this border insets value applies
		 */
		@Override
		public Insets getBorderInsets(Component c) {
			return (Insets) DefaultInsets.clone();
		}

		/**
		 * Returns whether or not the border is opaque. If the border is opaque, it is responsible for
		 * filling in it's own background when painting.
		 */
		@Override
		public boolean isBorderOpaque() {
			return false;
		}
	}
}
