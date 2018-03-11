package net.certiv.adept.format.align;

public class Mark implements Comparable<Mark> {

	public int stopCol;
	public int line;
	public int group;

	public Mark(int stopCol, int line, int group) {
		this.stopCol = stopCol;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("@" + stopCol);
		builder.append(" " + line + ":" + group);
		return builder.toString();
	}
}
