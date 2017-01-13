package net.certiv.adept.topo;

import net.certiv.adept.util.Strings;

public class FormInfo {

	public boolean blank;
	public int beg;
	public int len;
	public int end;

	public int indents;
	public int priorIndents;
	public boolean blankAbove;
	public boolean blankBelow;

	public FormInfo(String line, int tabWidth) {
		init(line, tabWidth);
	}

	private void init(String line, int tabWidth) {
		blank = line == null || line.isEmpty();
		if (blank) return;

		beg = Strings.firstNonWS(line);
		len = line.length();
		end = len - 1;

		indents = Strings.measureIndentUnits(line, tabWidth, tabWidth);
	}
}
