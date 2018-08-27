/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format.prep;

/** Alignment schemes */
public enum Scheme {

	/** Default: for identification of unaliged ref tokens. */
	NONE,

	/**
	 * Describes a single pair of guard elements, of typically complementary type, that are semantically
	 * related.
	 * <ul>
	 * <li>if on the same line, align with respective like types on the prior line
	 * <li>if on different lines, align with each other
	 */
	PAIR,

	/**
	 * Describes a series of elements, typically of the same type, that are semantically related.
	 * <ul>
	 * <li>if on different consecutive lines, align with each other
	 */
	LIST,

	/**
	 * Describes some combination of PAIR and LIST elements that occur within a semantically defined
	 * grouping.
	 * <ul>
	 * <li>requires group begin and end calls to define a single context for all elements
	 * <li>effectively allows zero or one PAIR and zero or more LISTs to be grouped
	 * <li>the PAIR elements must be the first and last elements, by token index
	 * <li>the LIST elements align with each other and any PAIR element on consecutive lines
	 */
	GROUP,

	/**
	 * Describes some combination of elements that occur within some proximally defined relation.
	 * <ul>
	 * <li>proximity is defined as GROUP occurence on contiguous lines
	 */
	SERIAL,

	/**
	 * Describes a line comment.
	 */
	COMMENT;
}
