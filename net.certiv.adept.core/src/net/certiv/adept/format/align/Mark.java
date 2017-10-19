package net.certiv.adept.format.align;

public class Mark implements Comparable<Mark> {

	public int visCol;
	public int line;
	public int group;

	public Mark(int visCol, int line, int group) {
		this.visCol = visCol;
		this.line = line;
		this.group = group;
	}

	@Override
	public int compareTo(Mark o) {
		if (line < o.line) return -1;
		if (line > o.line) return 1;
		if (group < o.group) return -1;
		if (group > o.group) return 1;
		return 0;
	}
}
