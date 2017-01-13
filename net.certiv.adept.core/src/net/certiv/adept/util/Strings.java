/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: IBM Corporation - initial API and
 * implementation
 *******************************************************************************/
package net.certiv.adept.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Helper class to provide String manipulation functions not available in standard JDK.
 */
public class Strings {

	public static final String EOL = System.lineSeparator();
	public static final char EOP = File.separatorChar; // path separator
	public static final char DOT = '.';

	private Strings() {}

	/**
	 * Returns a separator delimited string representation of the given list values. The returned
	 * string will not include a trailing separator.
	 * 
	 * @param values ordered list of string values
	 * @param asPrefix if <code>true</code>, the separator is positioned as a prefix to each list
	 *            value, otherwise as a suffix
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

	public static String csvList(List<String> stringList) {
		if (stringList == null) return "";
		StringBuilder sb = new StringBuilder();
		for (String str : stringList) {
			sb.append(str + ", ");
		}
		int len = sb.length();
		sb.delete(len - 2, len);
		return sb.toString();
	}

	/**
	 * Concatenate the given strings into one string using the passed line delimiter as a delimiter.
	 * No delimiter is added to the last line.
	 *
	 * @param lines the lines
	 * @param delimiter line delimiter
	 * @return the concatenated lines
	 */
	public static String concatenate(String[] lines, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			if (i > 0) buffer.append(delimiter);
			buffer.append(lines[i]);
		}
		return buffer.toString();
	}

	/**
	 * Returns a new array adding the second array at the end of first array. It answers null if the
	 * first and second are null. If the first array is null or if it is blank, then a new array is
	 * created with second. If the second array is null, then the first array is returned. <br>
	 * <br>
	 * For example:
	 * <ol>
	 * <li>
	 * 
	 * <pre>
	 *    first = null
	 *    second = "a"
	 *    => result = {"a"}
	 * </pre>
	 * 
	 * <li>
	 * 
	 * <pre>
	 *    first = {"a"}
	 *    second = null
	 *    => result = {"a"}
	 * </pre>
	 * 
	 * </li>
	 * <li>
	 * 
	 * <pre>
	 *    first = {"a"}
	 *    second = {"b"}
	 *    => result = {"a", "b"}
	 * </pre>
	 * 
	 * </li>
	 * </ol>
	 *
	 * @param first the first array to concatenate
	 * @param second the array to add at the end of the first array
	 * @return a new array adding the second array at the end of first array, or null if the two
	 *         arrays are null.
	 */
	public static String[] arrayConcat(String[] first, String second) {
		if (second == null) return first;
		if (first == null) return new String[] { second };

		int length = first.length;
		if (first.length == 0) {
			return new String[] { second };
		}

		String[] result = new String[length + 1];
		System.arraycopy(first, 0, result, 0, length);
		result[length] = second;
		return result;
	}

	public static String titleCase(String word) {
		if (word == null || word.length() == 0) return "";
		if (word.length() == 1) return word.toUpperCase();
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}

	public static String createIndent(int tabWidth, boolean useTabs, int indents) {
		StringBuilder sb = new StringBuilder();
		if (indents > 0) {
			String indent = useTabs ? "\t" : getNSpaces(tabWidth);
			for (int i = 0; i < indents; i++) {
				sb.append(indent);
			}
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
	 * Returns <code>true</code> if the given string only consists of white spaces according to
	 * Java. If the string is blank, <code>true
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

	/**
	 * Returns the given number of spaces.
	 */
	public static String getNSpaces(int spaces) {
		return getNChars(spaces, ' ');
	}

	/**
	 * Returns <code>count</code> copies of the given character.
	 */
	public static String getNChars(int count, char ch) {
		StringBuffer buf = new StringBuffer(count);
		for (int i = 0; i < count; i++) {
			buf.append(ch);
		}
		return buf.toString();
	}

	public static int firstNonWS(String str) {
		if (str == null || str.isEmpty()) return -1;

		for (int col = 0; col < str.length(); col++) {
			if (!isIndentChar(str.charAt(col))) return col;
		}
		return -1;
	}

	/**
	 * Returns the indentation of the given line in indentation units. Odd spaces are not counted.
	 * This method only analyzes the content of <code>line</code> up to the first non-whitespace
	 * character.
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
	 * Tab characters are counted using the given <code>tabWidth</code> and every other indent
	 * character as one. This method analyzes the content of <code>line</code> up to the first
	 * non-whitespace character.
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
			if (ch == '\t') {
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
	 * Returns <code>true</code> if the given character is an indentation character. Indentation
	 * character are all whitespace characters except the line delimiter characters.
	 *
	 * @param ch the given character
	 * @return Returns <code>true</code> if this the character is a indent character,
	 *         <code>false</code> otherwise
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
}
