package net.certiv.adept.model.topo;

import net.certiv.adept.util.Strings;

public class Info {

	/** The line is blank */
	public boolean blank;
	/** The first non-ws char col */
	public int beg;
	/** The line length */
	public int len;
	/** The line end char col */
	public int end;

	public int indents;
	public int priorIndents;
	public boolean blankAbove;
	public boolean blankBelow;

	public Info(String text, int tabWidth) {
		init(text, tabWidth);
	}

	private void init(String text, int tabWidth) {
		blank = text == null || text.isEmpty();
		if (blank) return;

		beg = Strings.firstNonWS(text);
		len = text.length();
		end = len - 1;

		indents = Strings.measureIndentUnits(text, tabWidth, tabWidth);
	}
}
