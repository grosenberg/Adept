package net.certiv.adept.format.align;

public enum Align {

	/** Default: for identification of unaliged ref tokens. */
	NONE,

	/**
	 * Describes a single pair of guard elements, of typically complementary type.
	 * <ul>
	 * <li>if on the same line, align with respective like types on the prior line
	 * <li>if on different lines, align with each other
	 */
	PAIR,

	/**
	 * Describes a series of elements, typically of the same type.
	 * <ul>
	 * <li>if on different consecutive lines, align with each other
	 */
	LIST,

	/**
	 * Describes a combination of PAIR and LIST elements.
	 * <ul>
	 * <li>requires group begin and end calls to define a single context for all elements
	 * <li>effectively allows zero or one PAIR and zero or more LISTs to be grouped
	 * <li>the PAIR elements must be the first and last elements, by token index
	 * <li>the LIST elements align with each other and any PAIR element on consecutive lines
	 */
	GROUP;
}
