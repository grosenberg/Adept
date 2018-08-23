/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Myers Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

public class TextUtils {

	/**
	 * The values for the string key for Text Anti-Aliasing
	 */
	private static RenderingHints sysHints;

	static {
		sysHints = null;
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			@SuppressWarnings("unchecked")
			Map<RenderingHints.Key, ?> map = (Map<RenderingHints.Key, ?>) toolkit
					.getDesktopProperty("awt.font.desktophints");
			sysHints = new RenderingHints(map);
		} catch (Throwable t) {}
	}

	private TextUtils() {}

	/**
	 * Sets the Rendering Hints on the Graphics. This is used so that any painters can set the Rendering
	 * Hints to match the view.
	 *
	 * @param g2d
	 */
	public static void setRenderingHits(Graphics2D g2d) {
		g2d.addRenderingHints(sysHints);
	}

	/**
	 * Translates an offset into the components text to a line number.
	 *
	 * @param offset the offset &gt;= 0
	 * @return the line number &gt;= 0
	 * @exception BadLocationException thrown if the offset is less than zero or greater than the
	 *                document length.
	 */
	public static int getLineOfOffset(Document doc, int offset) throws BadLocationException {
		if (offset < 0) {
			throw new BadLocationException("Can't translate offset to line", -1);
		} else if (offset > doc.getLength()) {
			throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
		} else {
			Element map = doc.getDefaultRootElement();
			return map.getElementIndex(offset);
		}
	}

	/**
	 * Determines the number of lines contained in the area.
	 *
	 * @return the number of lines &gt; 0
	 */
	public static int getLineCount(Document doc) {
		Element map = doc.getDefaultRootElement();
		return map.getElementCount();
	}

	/**
	 * Gets the Line Number at the give position of the editor component. The first line number is ZERO
	 *
	 * @return line number
	 * @throws BadLocationException
	 */
	public static int getLineNumber(Document doc, int pos) {
		return doc.getDefaultRootElement().getElementIndex(pos);
	}

	/**
	 * Determines the offset of the start of the given line.
	 *
	 * @param line the line number to translate &gt;= 0
	 * @return the offset &gt;= 0
	 * @exception BadLocationException thrown if the line is less than zero or greater or equal to the
	 *                number of lines contained in the document (as reported by getLineCount).
	 */
	public static int getLineStartOffset(Document doc, int line) throws BadLocationException {
		int lineCount = getLineCount(doc);
		if (line < 0) {
			throw new BadLocationException("Negative line", -1);
		} else if (line >= lineCount) {
			throw new BadLocationException("No such line", doc.getLength() + 1);
		} else {
			Element map = doc.getDefaultRootElement();
			Element lineElem = map.getElement(line);
			return lineElem.getStartOffset();
		}
	}

	/**
	 * Determines the offset of the end of the given line.
	 *
	 * @param line the line &gt;= 0
	 * @return the offset &gt;= 0
	 * @exception BadLocationException Thrown if the line is less than zero or greater or equal to the
	 *                number of lines contained in the document (as reported by getLineCount).
	 */
	public static int getLineEndOffset(Document doc, int line) throws BadLocationException {
		int lineCount = getLineCount(doc);
		if (line < 0) {
			throw new BadLocationException("Negative line", -1);
		} else if (line >= lineCount) {
			throw new BadLocationException("No such line", doc.getLength() + 1);
		} else {
			Element map = doc.getDefaultRootElement();
			Element lineElem = map.getElement(line);
			int endOffset = lineElem.getEndOffset();
			// hide the implicit break at the end of the document
			return ((line == lineCount - 1) ? (endOffset - 1) : endOffset);
		}
	}

	public static int getRow(JTextPane text, int pos) {
		int row = pos == 0 ? 1 : 0;
		try {
			int offs = pos;
			while (offs > 0) {
				offs = Utilities.getRowStart(text, offs) - 1;
				row++;
			}
		} catch (BadLocationException e) {}
		return row;
	}

	public static int getColumn(JTextPane text, int pos) {
		try {
			return pos - Utilities.getRowStart(text, pos) + 1;
		} catch (BadLocationException e) {}
		return -1;
	}

}
