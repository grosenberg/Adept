/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to provide String manipulation functions not available in
 * standard JDK.
 */
public class Strings {

	private static final Pattern NL = Pattern.compile(".*?(\\R)");

	public static final char EOP = File.separatorChar;	// path
	public static final String EOL = System.lineSeparator();

	public static final String TTAB_MARK = "\u1E6F"; 		// t underbar ṯ
	public static final String TAB_MARK = "\u2666";			// diamond ♦
	public static final String PARA_MARK = "\u00B6";		// pillcrow ¶
	public static final String SPACE_MARK = "\u00B7";		// middle dot ·
	public static final String ELLIPSIS_MARK = "\u2026"; 	// ellipsis …
	public static final String LARR_MARK = "\u2190";		// leftwards arrow
	public static final String RARR_MARK = "\u2192";		// rightwards arrow →
	public static final String SPACE = " ";
	public static final String EMPTY = "";

	public static final char SPC = ' ';
	public static final char TAB = '\t';
	public static final char RET = '\r';
	public static final char NLC = '\n';
	public static final char DOT = '.';

	private Strings() {}

	/**
	 * Returns a separator delimited string representation of the given values. The
	 * returned string will not include a trailing separator.
	 *
	 * @param values ordered list of string values
	 * @param asPrefix if <code>true</code>, the separator is positioned as a prefix
	 *            to each list value, otherwise as a suffix
	 * @param sep the string literal to be used as a list string separator
	 * @return separator delimited string
	 */
	public static String asString(List<String> values, boolean asPrefix, String sep) {
		StringBuilder sb = new StringBuilder();
		String pf = asPrefix ? sep : "";
		String sf = asPrefix ? "" : sep;
		for (String value : values) {
			sb.append(pf + value + sf);
		}
		if (!asPrefix) { // remove trailing sep
			int beg = sb.length() - 1 - sep.length();
			sb.delete(beg, sb.length());
		}
		return sb.toString();
	}

	public static boolean numeric(String value) {
		if (value.length() == 0) return false; // blank string is a string
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String join(Collection<? extends Object> values, String sep) {
		StringBuilder sb = new StringBuilder();
		for (Object value : values) {
			sb.append(value.toString() + sep);
		}
		int dot = sb.length() - sep.length();
		if (dot > -1) sb.delete(dot, sb.length());
		return sb.toString();
	}

	/**
	 * Join the given strings into one string using the passed line delimiter as a
	 * delimiter. No delimiter is added to the last line.
	 *
	 * @param lines the lines
	 * @param delimiter line delimiter
	 * @return the concatenated lines
	 */
	public static String join(String[] lines, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		for (int idx = 0; idx < lines.length; idx++) {
			if (idx > 0) buffer.append(delimiter);
			buffer.append(lines[idx]);
		}
		return buffer.toString();
	}

	public static String join(int[] values, String sep) {
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < values.length; idx++) {
			sb.append(String.format("[%s, %s]", idx, values[idx]));
			if (idx < values.length - 1) sb.append(sep);
		}
		return sb.toString();
	}

	public static String titleCase(String word) {
		if (word == null || word.length() == 0) return "";
		if (word.length() == 1) return word.toUpperCase();
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}

	public static String createIndent(int tabWidth, boolean useTabs, int indents) {
		if (indents < 1) return "";

		StringBuilder sb = new StringBuilder();
		String indent = useTabs ? "\t" : spaces(tabWidth);
		for (int i = 0; i < indents; i++) {
			sb.append(indent);
		}
		return sb.toString();
	}

	public static String indentBlock(String ci, String block) {
		if (block == null) return "<Error: indent block is null>";
		StringReader sr = new StringReader(block);
		BufferedReader buf = new BufferedReader(sr);
		StringBuilder sb = new StringBuilder();
		String s;
		try {
			while ((s = buf.readLine()) != null) {
				sb.append(ci + s + Strings.EOL);
			}
			sb.setLength(sb.length() - Strings.EOL.length());
		} catch (IOException e) {
			sb.append("<Error indenting block: " + e.getMessage() + ">");
		}
		return sb.toString();
	}

	/////////////////////////////////////////////////////////

	/**
	 * Tests if a char is lower case.
	 *
	 * @param ch the char
	 * @return return true if char is lower case
	 */
	public static boolean isLowerCase(char ch) {
		return Character.toLowerCase(ch) == ch;
	}

	/**
	 * Tests if a char is upper case.
	 *
	 * @param ch the char
	 * @return return true if char is upper case
	 */
	public static boolean isUpperCase(char ch) {
		return Character.toUpperCase(ch) == ch;
	}

	public static boolean matchesNoCase(char c, char d) {
		return Character.toLowerCase(c) == Character.toLowerCase(d);
	}

	public static boolean startsWithIgnoreCase(String text, String prefix) {
		int textLength = text.length();
		int prefixLength = prefix.length();
		if (textLength < prefixLength) return false;
		for (int i = prefixLength - 1; i >= 0; i--) {
			if (Character.toLowerCase(prefix.charAt(i)) != Character.toLowerCase(text.charAt(i))) return false;
		}
		return true;
	}

	public static String removeNewLine(String message) {
		StringBuffer result = new StringBuffer();
		int current = 0;
		int index = message.indexOf('\n', 0);
		while (index != -1) {
			result.append(message.substring(current, index));
			if (current < index && index != 0) result.append(' ');
			current = index + 1;
			index = message.indexOf('\n', current);
		}
		result.append(message.substring(current));
		return result.toString();
	}

	/**
	 * Returns <code>true</code> if the given string only consists of white spaces
	 * according to Java. If the string is blank, <code>true
	 * </code> is returned.
	 *
	 * @param s the string to test
	 * @return <code>true</code> if the string only consists of white spaces;
	 *             otherwise <code>false</code> is returned
	 * @see java.lang.Character#isWhitespace(char)
	 */
	public static boolean containsOnlyWhitespaces(String s) {
		int size = s.length();
		for (int i = 0; i < size; i++) {
			if (!Character.isWhitespace(s.charAt(i))) return false;
		}
		return true;
	}

	/** Returns the given string stripped of any trailing whitespace */
	public static String trimTrailing(String s) {
		for (int idx = s.length() - 1; idx >= 0; idx--) {
			if (!Character.isWhitespace(s.charAt(idx))) return s.substring(0, idx + 1);
		}
		return "";
	}

	/**
	 * Returns the given string trimmed of any trailing HWS.
	 *
	 * @param str the string to check
	 * @return the string without tailing HWS
	 */
	public static String trimTrailinglHWs(String str) {
		int idx = lastNonHWs(str);
		if (idx == -1) return "";
		return str.substring(0, idx + 1);
	}

	/**
	 * Returns the leading HWS contained in the given string.
	 *
	 * @param str the string to check
	 * @return the leading HWS
	 */
	public static String leadHWs(String str) {
		int idx = firstNonHWs(str);
		return idx > -1 ? str.substring(0, idx) : "";
	}

	/**
	 * Returns the trailing HWS contained in the given string.
	 *
	 * @param str the string to check
	 * @return the tailing HWS
	 */
	public static String trailingHWs(String str) {
		int idx = lastNonHWs(str);
		return idx != -1 ? str.substring(idx + 1) : str;
	}

	/**
	 * Returns the index of the first non-horizontal whitespace character in the
	 * given string.
	 *
	 * @param str the string to check
	 * @return index of the first non-whitespace character
	 */
	public static int firstNonHWs(String str) {
		if (str == null || str.isEmpty()) return -1;

		for (int col = 0; col < str.length(); col++) {
			if (!isHWs(str.charAt(col))) return col;
		}
		return -1;
	}

	/**
	 * Returns the index of the last non-horizontal whitespace character in the
	 * given string.
	 *
	 * @param str the string to check
	 * @return index of the last non-whitespace character
	 */
	public static int lastNonHWs(String str) {
		if (str == null || str.isEmpty()) return -1;

		for (int col = str.length() - 1; col >= 0; col--) {
			if (!isHWs(str.charAt(col))) return col;
		}
		return -1;
	}

	/**
	 * Returns <code>true</code> if the given character is an indentation character.
	 * Indentation character are all whitespace characters except the line delimiter
	 * characters.
	 *
	 * @param ch the given character
	 * @return Returns <code>true</code> if this the character is a indent
	 *             character, <code>false</code> otherwise
	 */
	public static boolean isHWs(char ch) {
		return Character.isWhitespace(ch) && !isLineDelimiterChar(ch);
	}

	/**
	 * Returns <code>true</code> if the given character is a line delimiter
	 * character.
	 *
	 * @param ch the given character
	 * @return Returns <code>true</code> if this the character is a line delimiter
	 *             character, <code>false</code> otherwise
	 */
	public static boolean isLineDelimiterChar(char ch) {
		return ch == '\n' || ch == '\r';
	}

	/** Returns a string containing {@code count} spaces. */
	public static String spaces(int count) {
		return getN(count, SPACE);
	}

	/**
	 * Returns a string containing {@code count} sequential copies of the given
	 * character.
	 */
	public static String getN(int count, char c) {
		if (count <= 0) return "";

		StringBuffer buf = new StringBuffer();
		for (int idx = 0; idx < count; idx++) {
			buf.append(c);
		}
		return buf.toString();
	}

	/**
	 * Returns a string containing {@code count} sequential copies of the given
	 * text.
	 */
	public static String getN(int count, String text) {
		if (count <= 0) return "";

		StringBuffer buf = new StringBuffer();
		for (int idx = 0; idx < count; idx++) {
			buf.append(text);
		}
		return buf.toString();
	}

	/**
	 * Returns the indentation of the given line in indentation units. Odd spaces
	 * are not counted. This method only analyzes the content of <code>line</code>
	 * up to the first non-whitespace character.
	 *
	 * @param line the string to measure the indent of
	 * @param tabWidth the width of one tab character in space equivalents
	 * @param indentWidth the width of one indentation unit in space equivalents
	 * @return the number of indentation units that line is indented by
	 * @exception IllegalArgumentException if:
	 *                <ul>
	 *                <li>the given <code>indentWidth</code> is lower or equals to
	 *                zero</li>
	 *                <li>the given <code>tabWidth</code> is lower than zero</li>
	 *                <li>the given <code>line</code> is null</li>
	 *                </ul>
	 */
	public static int measureIndentUnits(CharSequence line, int tabWidth, int indentWidth) {
		if (indentWidth <= 0 || tabWidth < 0 || line == null) {
			throw new IllegalArgumentException();
		}

		int visualLength = measureIndentInSpaces(line, tabWidth);
		return visualLength / indentWidth;
	}

	/**
	 * Returns the indentation of the given line in space equivalents.
	 * <p>
	 * Tab characters are counted using the given <code>tabWidth</code> and every
	 * other indent character as one. This method analyzes the content of
	 * <code>line</code> up to the first non-whitespace character.
	 * </p>
	 *
	 * @param line the string to measure the indent of
	 * @param tabWidth the width of one tab in space equivalents
	 * @return the measured indent width in space equivalents
	 * @exception IllegalArgumentException if:
	 *                <ul>
	 *                <li>the given <code>line</code> is null</li>
	 *                <li>the given <code>tabWidth</code> is lower than zero</li>
	 *                </ul>
	 */
	public static int measureIndentInSpaces(CharSequence line, int tabWidth) {
		if (tabWidth < 0 || line == null) throw new IllegalArgumentException();

		int length = 0;
		for (int idx = 0, max = line.length(); idx < max; idx++) {
			char ch = line.charAt(idx);
			if (ch == TAB) {
				length += tabWidth - (length % tabWidth);
			} else if (isHWs(ch)) {
				length++;
			} else {
				return length;
			}
		}
		return length;
	}

	/**
	 * Returns the visual width of the given line of text.
	 *
	 * @param text the string to measure
	 * @param tabWidth the visual width of a tab
	 * @return the visual width of <code>text</code>
	 * @see org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/IndentUtil.java
	 */
	public static int measureVisualWidth(CharSequence text, int tabWidth) {
		return measureVisualWidth(text, tabWidth, 0);
	}

	/**
	 * Returns the visual width of the given text starting from the given offset
	 * within a line. Width is reset each time a line separator character is
	 * encountered.
	 *
	 * @param text the string to measure
	 * @param tabWidth the visual width of a tab
	 * @param from the visual starting offset of the text
	 * @return the visual width of <code>text</code>
	 * @see org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/IndentUtil.java
	 */
	public static int measureVisualWidth(CharSequence text, int tabWidth, int from) {
		if (text == null || tabWidth < 0 || from < 0) throw new IllegalArgumentException();

		int width = from;
		for (int idx = 0, len = text.length(); idx < len; idx++) {
			switch (text.charAt(idx)) {
				case TAB:
					if (tabWidth > 0) width += tabWidth - width % tabWidth;
					break;
				case RET:
				case NLC:
					width = 0;
					from = 0;
					break;
				default:
					width++;
			}
		}
		return width - from;
	}

	public static String createVisualWs(int tabWidth, int from, int to) {
		if (tabWidth < 1) tabWidth = 1;
		if (from < 0 || from > to) {
			throw new IllegalArgumentException(String.format("%d:%d", from, to));
		}

		int ftabs = from / tabWidth;
		int fspcs = from % tabWidth;

		int ttabs = to / tabWidth;
		int tspcs = to % tabWidth;

		int tabs = ttabs - ftabs;
		int spcs = tabs > 0 ? tspcs : tspcs - fspcs;
		return Strings.getN(tabs, TAB) + Strings.getN(spcs, SPC);
	}

	public static String shorten(String in, int len) {
		if (in.length() <= len) return in;
		String out = encodeWS(in.substring(0, len - 3));
		return out + ELLIPSIS_MARK;
	}

	public static List<String> encodeWS(List<String> in) {
		if (in != null) {
			for (int idx = 0; idx < in.size(); idx++) {
				in.set(idx, encodeWS(in.get(idx)));
			}
		}
		return in;
	}

	/** Encodes WS as visible characters. */
	public static String encodeWS(String in) {
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < in.length(); idx++) {
			char c = in.charAt(idx);
			switch (c) {
				case SPC:
					sb.append(SPACE_MARK);
					break;
				case TAB:
					sb.append(TAB_MARK);
					break;
				case RET:
					if (idx + 1 == in.length() || in.charAt(idx + 1) != NLC) {
						sb.append(PARA_MARK);
					}
					break;
				case NLC:
					sb.append(PARA_MARK);
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	/** Split all lines, preserving blank last line, if any. */
	public static String[] splitLines(String text) {
		return text.split("\\R", -1);
	}

	public static int count(String text, String mark) {
		if (text == null || text.isEmpty()) return 0;
		return text.split(Pattern.quote(mark), -1).length - 1;
	}

	public static int countVWS(String txt) {
		if (txt == null || txt.isEmpty()) return 0;

		int cnt = 0;
		Matcher m = NL.matcher(txt);
		while (m.find()) {
			cnt++;
		}
		return cnt;
	}

	/**
	 * Returns the column of the tab stop equal to or larger than the given column.
	 */
	public static int nextTabCol(int col, int tabWidth) {
		int rem = col % tabWidth;
		if (rem == 0) return col;
		return col + tabWidth - rem;
	}

	/** Returns the tab column of the nearest tab stop to the given column. */
	public static int nearestTabCol(int col, int tabWidth) {
		int rem = col % tabWidth;
		if (rem / 2 >= tabWidth / 2) return col + tabWidth - rem;
		return col - rem;
	}

	public static String wrap(String txt, int wrap, String prefix, String terminal, String splits) {
		if (txt == null || txt.isEmpty()) return "";
		if (prefix == null) prefix = "";
		if (terminal == null) terminal = EOL;
		if (splits == null || splits.isEmpty()) splits = SPACE;
		if (wrap < 1) wrap = 80;

		final Pattern splitter = Pattern.compile(splits);
		int len = txt.length();
		StringBuilder sb = new StringBuilder(len);
		sb.append(prefix);

		int beg = 0;
		while (beg < len) {
			int wrapAt = -1;
			int end = Math.min((int) Math.min(Integer.MAX_VALUE, beg + wrap + 1L), len);
			Matcher matcher = splitter.matcher(txt.substring(beg, end));
			if (matcher.find()) {
				if (matcher.start() == 0) {
					beg += matcher.end();
					continue;
				}
				wrapAt = matcher.start() + beg;
			}
			// only last line without leading spaces is left
			if (len - beg <= wrap) break;

			while (matcher.find()) {
				wrapAt = matcher.start() + beg;
			}

			if (wrapAt >= beg) {	// normal case
				sb.append(txt, beg, wrapAt);
				sb.append(terminal + prefix);
				beg = wrapAt + 1;

			} else {
				matcher = splitter.matcher(txt.substring(beg + wrap));
				if (matcher.find()) {
					wrapAt = matcher.start() + beg + wrap;
				}

				if (wrapAt >= 0) {
					sb.append(txt, beg, wrapAt);
					sb.append(terminal);
					beg = wrapAt + 1;
				} else {
					sb.append(txt, beg, txt.length());
					beg = len;
				}
			}
		}

		// append remainder
		sb.append(txt, beg, txt.length());
		return sb.toString();
	}
}
