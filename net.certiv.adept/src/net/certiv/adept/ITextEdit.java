package net.certiv.adept;

public interface ITextEdit {

	/** Returns the existing text to be replaced. */
	String existing();

	/** Returns the replacement text to be applied by this edit. */
	String replacement();

	/** Returns the starting char offset for the replacement text (0-based). */
	int replOffset();

	/** Returns the char length of the existing text that is to be replaced. */
	int replLen();

	/** Returns the starting line for the replacement text (0-based). */
	int replLine();

	/** Returns the starting column for the replacement text (0-based) */
	int replCol();
}
