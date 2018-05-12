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
 * A ruler that shows the tabs for the current paragraph, as well as allowing the user to manipulate
 * the tabs.
 *
 * @author Scott Violet
 */
public class TabRuler extends JPanel implements CaretListener, PropertyChangeListener {

	// Some defines used in painting the ruler.
	protected static final int DPI = 72;
	protected static final int H_DPI = DPI / 2;
	protected static final int Q_DPI = DPI / 4;
	protected static final int E_DPI = DPI / 8;

	// Sizes for drawing tabs.
	protected static final int TabSize = 2;
	protected static final int TabWidth = 6;
	protected static final int TabHeight = 4;

	/** Shared instance of default border. */
	protected static final Border DefaultBorder = new RulerBorder();

	/** TextPane showing tabs for. */
	private JTextPane editor;
	/** Current TabSet showing. */
	private TabSet tabs;

	/** Current paragraph element at character position. */
	private Element paragraph;
	/** Offset to start drawing tabs from. */
	private int xOffset;
	/** If false, the value of xOffset is not valid. */
	private boolean validOffset;

	/** Font using for the units. */
	private Font unitsFont;
	/** Total font height. */
	private int fontHeight;
	/** Font ascent. */
	private int fontAscent;

	public TabRuler() {
		// setBackground(Color.white);

		setBorder(DefaultBorder);
	}

	public TabRuler(JTextPane editor) {
		this();
		setTextPane(editor);
	}

	/**
	 * Sets the text pane tabs are rendered for.
	 */
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

	/**
	 * Gets the text pane tabs are being rendered for.
	 */
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
					validOffset = false;
				} else if (newOffset.intValue() != xOffset) {
					validOffset = true;
					xOffset = newOffset.intValue();
					if (tabs == newTabs) {
						repaint();
					}
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
		if (!validOffset && getParagraphElement() != null) {
			Integer offset = determineOffset(getParagraphElement());
			if (offset == null) return 0;

			xOffset = offset.intValue();
			validOffset = true;
			repaint();
		}
		return xOffset;
	}

	/**
	 * Returns the current paragraph element. If the selection extends across multiple paragraphs this
	 * will return the first paragraph.
	 */
	protected Element getParagraphElement() {
		return paragraph;
	}

	//
	// Painting methods
	//

	/**
	 * Messaged to paint the Component, will fill the background and message paintUnits and paintTabs.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Rectangle clip = g.getClipBounds();
		Insets insets = getInsets();

		updateFontIfNecessary();
		g.setColor(getBackground());
		g.fillRect(clip.x, clip.y, clip.width, clip.height);
		paintUnits(g, clip, insets);
		paintTabMarks(g, clip, insets);
	}

	/** Paints the unit indicators. */
	protected void paintUnits(Graphics g, Rectangle clip, Insets insets) {
		int xOffset = getXOffset();
		int fontY = getUnitsFontAscent();
		int midY = getUnitsFontHeight() / 2;
		int dpiOffset = xOffset % DPI;

		if (insets != null) {
			fontY += insets.top;
			midY += insets.top;
		}
		g.setColor(getUnitsColor());
		g.setFont(getUnitsFont());

		FontMetrics fm = g.getFontMetrics();
		// int width = fm.charWidth('0') ;

		for (int x = Math.max(xOffset, clip.x / DPI * DPI + dpiOffset), maxX = clip.x
				+ clip.width; x <= maxX; x += E_DPI) {
			int tempX = x - dpiOffset;
			if (tempX % DPI == 0) {
				String numString = Integer.toString((x - xOffset) / DPI);
				g.drawString(numString, x - fm.stringWidth(numString) / 2, fontY);
			} else if (tempX % H_DPI == 0) {
				g.drawLine(x, midY - 3, x, midY + 3);
			} else if (tempX % Q_DPI == 0) {
				g.drawLine(x, midY - 2, x, midY + 2);
			} else {
				g.drawLine(x, midY - 1, x, midY + 1);
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
			// Different implementations of View used to represent a
			// Paragraph may not due this.
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
	protected void paintTabMark(Graphics g, Rectangle clip, int x, int y, int alignment, int leader) {
		switch (alignment) {
			case TabStop.ALIGN_LEFT:
				int[] xx = { x, x - 3, x + 3 };
				int[] yy = { y - TabHeight, y, y };
				g.fillPolygon(xx, yy, 3);

				// g.fillRect(x, y - TabHeight, TabSize, TabHeight);
				// g.fillRect(pos, maxY - TabSize, TabWidth + TabSize, TabSize);
				break;
			case TabStop.ALIGN_RIGHT:
				g.fillRect(x, y - TabHeight, TabSize, TabHeight);
				g.fillRect(x - TabWidth, y - TabSize, TabWidth, TabSize);
				break;
			case TabStop.ALIGN_DECIMAL:
				g.fillRect(x, y - TabHeight - TabSize - 2, TabSize, TabSize);
			case TabStop.ALIGN_CENTER:
				g.fillRect(x, y - TabHeight, TabSize, TabHeight);
				g.fillRect(x - TabWidth, y - TabSize, TabWidth * 2 + TabSize, TabSize);
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
		updateFontIfNecessary();

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

	/**
	 * The ascent of the units font.
	 */
	protected int getUnitsFontAscent() {
		return fontAscent;
	}

	/**
	 * Returns the height of the tray.
	 */
	protected int getUnitsFontHeight() {
		return fontHeight;
	}

	/**
	 * Updates font height information.
	 */
	private void updateFontIfNecessary() {
		Font font = getUnitsFont();

		if (unitsFont != font) {
			fontHeight = fontAscent = 0;
			if (font != null) {
				Toolkit tk = getToolkit();

				if (tk != null) {
					// FontMetrics fm = tk.getFontMetrics(font);
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

	/**
	 * Returns the default border to use.
	 */
	protected Border createBorder() {
		return DefaultBorder;
	}

	/**
	 * Draws a little border around the TabRuler.
	 */
	protected static class RulerBorder implements Border {
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

	// /**
	// * Creates and returns the listener to use for moving around tabs.
	// */
	// protected MouseInputListener createMouseInputListener() {
	// return new MouseInputHandler();
	// }

	// /**
	// * MouseInputHandler is responsible for receiving mouse events and translating that into adjusting
	// * the TabSet. A mouse down on an existing tab allows the user to move that tab around, if the
	// shift
	// * key is held down on the initial click the type of tab will change to the next type of alignment
	// * (cycling through left, right, centered, and decimal). A mouse down not near an exising tab
	// causes
	// * a new tab to be created. A tab can be removed by dragging it outside the bounds of the
	// TabRuler.
	// */
	// protected class MouseInputHandler extends MouseInputAdapter {
	// /**
	// * The tab the user is dragging, non null indicates a valid tab has been selected.
	// */
	// protected TabStop tab;
	// /** Original TabSet. */
	// protected TabSet originalTabs;
	// /**
	// * Tab user clicked on. Null indicates the user is creating a new tab.
	// */
	// protected TabStop originalTab;
	// /** While the mouse is down this will be true. */
	// protected boolean dragging;
	// /** Specifies the alignment passed into createTabStop. */
	// protected int newAlignment;
	//
	// /**
	// * Invoked when a mouse button has been pressed on a component.
	// */
	// @Override
	// public void mousePressed(MouseEvent e) {
	// dragging = true;
	// originalTabs = getTabSet();
	// originalTab = getTabClosestTo(e.getX(), e.getY());
	// newAlignment = TabStop.ALIGN_LEFT;
	// if (originalTab == null) {
	// tab = createTabStop(e.getX(), e.getY(), newAlignment);
	// resetTabs();
	// } else {
	// tab = originalTab;
	// if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0) {
	// // Shift mask changes the alignment of the tab.
	// switch (tab.getAlignment()) {
	// case TabStop.ALIGN_LEFT:
	// newAlignment = TabStop.ALIGN_RIGHT;
	// break;
	// case TabStop.ALIGN_RIGHT:
	// newAlignment = TabStop.ALIGN_CENTER;
	// break;
	// case TabStop.ALIGN_CENTER:
	// newAlignment = TabStop.ALIGN_DECIMAL;
	// break;
	// default:
	// newAlignment = TabStop.ALIGN_LEFT;
	// }
	// tab = new TabStop(tab.getPosition(), newAlignment, tab.getLeader());
	// resetTabs();
	// } else {
	// newAlignment = tab.getAlignment();
	// }
	// }
	// }
	//
	// /**
	// * Invoked when a mouse button has been released on a component.
	// */
	// @Override
	// public void mouseReleased(MouseEvent e) {
	// dragging = false;
	//
	// // Push the tabs to the text pane (we may not need to do this
	// // if the tabs haven't changed).
	// TabSet tabs = getTabSet();
	//
	// if (tabs == null) {
	// SimpleAttributeSet sas = new SimpleAttributeSet(getParagraphElement().getAttributes());
	//
	// sas.removeAttribute(StyleConstants.TabSet);
	// getTextPane().setParagraphAttributes(sas, true);
	// } else {
	// SimpleAttributeSet sas = new SimpleAttributeSet();
	//
	// StyleConstants.setTabSet(sas, tabs);
	// getTextPane().setParagraphAttributes(sas, false);
	// }
	// }
	//
	// /**
	// * Invoked when a mouse button is pressed on a component and then dragged. Mouse drag events will
	// * continue to be delivered to the component where the first originated until the mouse button is
	// * released (regardless of whether the mouse position is within the bounds of the component).
	// */
	// @Override
	// public void mouseDragged(MouseEvent e) {
	// if (dragging) {
	// TabStop newTab = createTabStop(e.getX(), e.getY(), newAlignment);
	//
	// // Workaround for TabStop.equals not handling null being
	// // passed in.
	// if (newTab != tab && ((newTab != null && tab != null && !newTab.equals(tab))
	// || (newTab == null || tab == null))) {
	// tab = newTab;
	// resetTabs();
	// }
	// }
	// }
	//
	// /**
	// * Creates a new TabSet and messages setTabSet.
	// */
	// protected void resetTabs() {
	// TabStop[] stops;
	//
	// if (tab == null) {
	// // The tab has been moved off screen, indicating we should
	// // remove it.
	// if (originalTab != null && originalTabs != null) {
	// int tabCount = originalTabs.getTabCount();
	//
	// if (tabCount > 1) {
	// stops = new TabStop[tabCount - 1];
	// for (int counter = 0, index = 0; counter < tabCount; counter++) {
	// TabStop tab = originalTabs.getTab(counter);
	//
	// if (tab != originalTab) {
	// stops[index++] = tab;
	// }
	// }
	// setTabSet(new TabSet(stops));
	// } else {
	// setTabSet(null);
	// }
	// } else {
	// setTabSet(originalTabs);
	// }
	// return;
	// }
	//
	// if (originalTabs == null) {
	// // No starting TabSet, create a new one.
	// stops = new TabStop[1];
	// stops[0] = tab;
	//
	// } else if (originalTab == null) {
	// // Adding a new tab.
	// int numTabs = originalTabs.getTabCount();
	// int nextIndex = originalTabs.getTabIndexAfter(tab.getPosition());
	//
	// stops = new TabStop[numTabs + 1];
	// if (nextIndex == -1) {
	// for (int counter = 0; counter < numTabs; counter++) {
	// stops[counter] = originalTabs.getTab(counter);
	// }
	// stops[numTabs] = tab;
	// } else {
	// for (int counter = 0; counter < nextIndex; counter++) {
	// stops[counter] = originalTabs.getTab(counter);
	// }
	// stops[nextIndex] = tab;
	// for (int counter = nextIndex; counter < numTabs; counter++) {
	// stops[counter + 1] = originalTabs.getTab(counter);
	// }
	// }
	//
	// } else {
	// // Moving an existing tab.
	// int numTabs = originalTabs.getTabCount();
	// int nextIndex = originalTabs.getTabIndexAfter(tab.getPosition());
	// int index = 0;
	//
	// stops = new TabStop[numTabs];
	// if (nextIndex == -1) {
	// for (int counter = 0; counter < numTabs; counter++) {
	// stops[index] = originalTabs.getTab(counter);
	// if (stops[index] != originalTab) {
	// index++;
	// }
	// }
	// stops[index] = tab;
	// } else {
	// for (int counter = 0; counter < nextIndex; counter++) {
	// stops[index] = originalTabs.getTab(counter);
	// if (stops[index] != originalTab) {
	// index++;
	// }
	// }
	// stops[index++] = tab;
	// for (int counter = nextIndex; counter < numTabs && index < numTabs; counter++) {
	// stops[index] = originalTabs.getTab(counter);
	// if (stops[index] != originalTab) {
	// index++;
	// }
	// }
	// }
	// }
	// setTabSet(new TabSet(stops));
	// }
	//
	// /**
	// * Creates a tab stop at the passed in visual location. This should be offset from any margins.
	// */
	// protected TabStop createTabStop(int x, int y, int alignment) {
	// if (x < 0 || x > getBounds().width || y < 0 || y > getBounds().height) {
	// return null;
	// }
	// // Constrain the x to 1/8 of an inch.
	// x = (x - getXOffset()) / E_DPI * E_DPI;
	// return new TabStop(x, alignment, TabStop.LEAD_NONE);
	// }
	// }
}
