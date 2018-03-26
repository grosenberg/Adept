package net.certiv.adept.format.indent;

import com.google.gson.annotations.Expose;

public class Dent {

	@Expose public int indents;		// nominal indent level at a token index
	@Expose public Bind bind;		// indenting hint
	@Expose public int extra = 2;	// extra wrapping indents

	public Dent(int indents, Bind bind) {
		this.indents = indents;
		this.bind = bind;
	}

	/**
	 * Returns the indent level subject to the current indent binding hint.
	 */
	public int getIndents() {
		switch (bind) {
			case AFTER:
				return Math.max(0, indents - 1);
			case WRAP:
				return indents + extra;
			default:
				return indents;
		}
	}

	@Override
	public String toString() {
		return String.format("%s [%s:%s]", getIndents(), indents, bind);
	}
}
