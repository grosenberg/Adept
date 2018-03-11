package net.certiv.adept.view.utils;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;

public class TextUtils {

	private TextUtils() {}

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
	 * Determines the offset of the start of the given line.
	 *
	 * @param line the line number to translate &gt;= 0
	 * @return the offset &gt;= 0
	 * @exception BadLocationException thrown if the line is less than zero or greater or equal to
	 *                the number of lines contained in the document (as reported by getLineCount).
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
	 * @exception BadLocationException Thrown if the line is less than zero or greater or equal to
	 *                the number of lines contained in the document (as reported by getLineCount).
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
