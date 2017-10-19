package net.certiv.adept.format.align;

import net.certiv.adept.util.Strings;

public class Part implements Comparable<Part> {

	public final StringBuilder sb = new StringBuilder();

	public int mark;
	public int visCol;
	public int width;

	public Part(int mark, int visCol) {
		this.mark = mark;
		this.visCol = visCol;
	}

	@Override
	public int compareTo(Part p) {
		if (visCol < p.visCol) return -1;
		if (visCol > p.visCol) return 1;
		return 0;
	}

	public void extendWs(int tabWidth, int alignCol) {
		String tws = trailingWs();
		int visEnd = Strings.measureVisualWidth(sb, tabWidth, visCol);
		tws = Strings.createVisualHWs(tabWidth, visEnd, alignCol);
		sb.append(tws);
	}

	private String trailingWs() {
		int dot = sb.length() - 1;
		while (dot > -1) {
			if (sb.charAt(dot) == ' ' || sb.charAt(dot) == '\t') {
				dot--;
			} else {
				dot++;
				if (dot == sb.length()) return "";
				String hws = sb.substring(dot);
				sb.setLength(dot);
				return hws;
			}
		}
		return sb.toString();
	}
}
