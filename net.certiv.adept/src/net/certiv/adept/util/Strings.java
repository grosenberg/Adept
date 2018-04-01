package net.certiv.adept.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/** Helper class to provide String manipulation functions not available in standard JDK. */
public class Strings {

	public static final String PARA_MARK = "\u00B6";
	public static final String SPACE_MARK = "\u00B7";
	public static final String TAB_MARK = "\u1E6F";		// double left arrow: "\u00BB";
	public static final String ELLIPSIS_MARK = "\u2026";
	public static final String LARR_MARK = "\u2190";
	public static final String RARR_MARK = "\u2192";

	public static final char EOP = File.separatorChar;	// path
	public static final String EOL = System.lineSeparator();
	public static final String SPACE = " ";
	public static final char TAB = '\t';
	public static final char DOT = '.';

	private Strings() {}

	/**
	 * Returns a separator delimited string representation of the given values. The returned string will
	 * not include a trailing separator.
	 *
	 * @param values ordered list of string values
	 * @param asPrefix if <code>true</code>, the separator is positioned as a prefix to each list value,
	 *            otherwise as a suffix
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
	 * Join the given strings into one string using the passed line delimiter as a delimiter. No
	 * delimiter is added to the last line.
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
		String indent = useTabs ? "\t" : getNSpaces(tabWidth);
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
	 * Returns <code>true</code> if the given string only consists of white spaces according to Java. If
	 * the string is blank, <code>true
	 * </code> is returned.
	 *
	 * @param s the string to test
	 * @return <code>true</code> if the string only consists of white spaces; otherwise
	 *         <code>false</code> is returned
	 * @see java.lang.Character#isWhitespace(char)
	 */
	public static boolean containsOnlyWhitespaces(String s) {
		int size = s.length();
		for (int i = 0; i < size; i++) {
			if (!Character.isWhitespace(s.charAt(i))) return false;
		}
		return true;
	}

	/** Returns a string containing {@code count} spaces. */
	public static String getNSpaces(int count) {
		return getN(count, SPACE);
	}

	/** Returns a string containing {@code count} sequential copies of the given character. */
	public static String getN(int count, char c) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < count; i++) {
			buf.append(c);
		}
		return buf.toString();
	}

	/** Returns a string containing {@code count} sequential copies of the given text. */
	public static String getN(int count, String text) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < count; i++) {
			buf.append(text);
		}
		return buf.toString();
	}

	/**
	 * Returns any leading HWS from the given string.
	 *
	 * @param str the string to check
	 * @return the leading HWS
	 */
	public static String leadHWS(String str) {
		int idx = firstNonHWS(str);
		return idx > -1 ? str.substring(0, idx) : "";
	}

	/**
	 * Returns the index of the first non-horizontal whitespace character in the given string.
	 *
	 * @param str the string to check
	 * @return index of the first non-whitespace character
	 */
	public static int firstNonHWS(String str) {
		if (str == null || str.isEmpty()) return -1;

		for (int col = 0; col < str.length(); col++) {
			if (!isIndentChar(str.charAt(col))) return col;
		}
		return -1;
	}

	/**
	 * Returns <code>true</code> if the given character is an indentation character. Indentation
	 * character are all whitespace characters except the line delimiter characters.
	 *
	 * @param ch the given character
	 * @return Returns <code>true</code> if this the character is a indent character, <code>false</code>
	 *         otherwise
	 */
	public static boolean isIndentChar(char ch) {
		return Character.isWhitespace(ch) && !isLineDelimiterChar(ch);
	}

	/**
	 * Returns <code>true</code> if the given character is a line delimiter character.
	 *
	 * @param ch the given character
	 * @return Returns <code>true</code> if this the character is a line delimiter character,
	 *         <code>false</code> otherwise
	 */
	public static boolean isLineDelimiterChar(char ch) {
		return ch == '\n' || ch == '\r';
	}

	/**
	 * Returns the indentation of the given line in indentation units. Odd spaces are not counted. This
	 * method only analyzes the content of <code>line</code> up to the first non-whitespace character.
	 *
	 * @param line the string to measure the indent of
	 * @param tabWidth the width of one tab character in space equivalents
	 * @param indentWidth the width of one indentation unit in space equivalents
	 * @return the number of indentation units that line is indented by
	 * @exception IllegalArgumentException if:
	 *                <ul>
	 *                <li>the given <code>indentWidth</code> is lower or equals to zero</li>
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
	 * Tab characters are counted using the given <code>tabWidth</code> and every other indent character
	 * as one. This method analyzes the content of <code>line</code> up to the first non-whitespace
	 * character.
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
		if (tabWidth < 0 || line == null) {
			throw new IllegalArgumentException();
		}

		int length = 0;
		int max = line.length();
		for (int i = 0; i < max; i++) {
			char ch = line.charAt(i);
			if (ch == TAB) {
				int reminder = length % tabWidth;
				length += tabWidth - reminder;
			} else if (isIndentChar(ch)) {
				length++;
			} else {
				return length;
			}
		}
		return length;
	}

	/**
	 * Returns the visual width of a given given line.
	 *
	 * @param line the string to measure
	 * @param tabWidth the visual width of a tab
	 * @return the visual width of <code>text</code>
	 * @see https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/IndentUtil.java
	 */
	public static int measureVisualWidth(CharSequence line, int tabWidth) {
		return measureVisualWidth(line, tabWidth, 0);
	}

	/**
	 * Returns the visual width of a given given line.
	 *
	 * @param text the string to measure
	 * @param tabWidth the visual width of a tab
	 * @param from the visual starting offset of the text
	 * @return the visual width of <code>text</code>
	 * @see https://github.com/eclipse/eclipse.jdt.ui/blob/master/org.eclipse.jdt.ui/ui/org/eclipse/jdt/internal/ui/javaeditor/IndentUtil.java
	 */
	public static int measureVisualWidth(CharSequence text, int tabWidth, int from) {
		if (text == null || tabWidth < 0 || from < 0) throw new IllegalArgumentException();

		int width = from;
		int len = text.length();
		for (int idx = 0; idx < len; idx++) {
			if (text.charAt(idx) == TAB) {
				if (tabWidth > 0) width += tabWidth - width % tabWidth;
			} else {
				width++;
			}
		}
		return width - from;
	}

	public static String createVisualWs(int tabWidth, int from, int to) {
		if (tabWidth < 1) tabWidth = 1;
		if (from < 0 || to < from) throw new IllegalArgumentException(String.format("from %s to %s", from, to));

		StringBuilder sb = new StringBuilder();
		int dot = from;
		int nxt = dot;
		while ((nxt = dot + tabWidth - dot % tabWidth) < to) {
			sb.append(TAB);
			dot = nxt;
		}
		while (dot < to) {
			sb.append(SPACE);
			dot++;
		}
		return sb.toString();
	}

	public static String shorten(String in, int len) {
		if (in.length() <= len) return in;
		String out = encodeWS(in.substring(0, len - 3));
		return out + ELLIPSIS_MARK;
	}

	public static String encodeWS(String in) {
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < in.length(); idx++) {
			char c = in.charAt(idx);
			switch (c) {
				case ' ':
					sb.append(SPACE_MARK);
					break;
				case TAB:
					sb.append(TAB_MARK);
					break;
				case '\r':
					if (idx + 1 == in.length() || in.charAt(idx + 1) != '\n') {
						sb.append(PARA_MARK);
					}
					break;
				case '\n':
					sb.append(PARA_MARK);
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	public static int count(String text, String mark) {
		if (text == null || text.isEmpty()) return 0;
		return text.split(Pattern.quote(mark), -1).length - 1;
	}

	public static int countLines(String txt) {
		if (txt == null || txt.isEmpty()) return 0;
		return txt.split("\\R", -1).length;
	}
}
