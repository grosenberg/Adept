package net.certiv.adept;

public interface ITextEdit {

	/** Returns the existing text to be replaced. */
	String existing();

	/** Returns the replacement text to be applied by this edit. */
	String replacement();

	/** Returns the starting stream char offset for the replacement text (0-based). */
	int replOffset();

	/** Returns the char length of the existing text that is to be replaced. */
	int replLen();

	/** Returns the document line for the replacement text (0-based). */
	int replLine();

	/** Returns the document column for the replacement text (0-based) */
	int replCol();
}
