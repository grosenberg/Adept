package net.certiv.adept.format;

import net.certiv.adept.model.Format;

public class Gap {

	public boolean join;			// {@code true} to keep or force line joining
	public boolean lineBreak;		// {@code true} to keep or force line break

	public boolean indented;		// at line begin and indented
	public int indents;				// absolute number of indents
	public int relDents;			// num indents relative to real line above

	public boolean ws;				// has ws
	public boolean multWs;			// has multiple ws
	public int numWs;				// num ws

	public boolean aligned;			// whether visually aligned to a node feature in a surrounding real line
	public boolean alignedAbove;
	public boolean alignedBelow;
	public int visCol;				// align visual column

	public boolean blankAbove;		// needs blank line above/below
	public boolean blankBelow;

	public boolean noFormat;		// retain existing

	// ------------------------------------------------------------------------

	/**
	 * Define a whitespace gap format between real tokens based on the trailing aspects of the lhs
	 * format and the leading aspects of the rhs format.
	 */
	public static Gap define(Format lhs, Format rhs) {
		Gap gap = new Gap();

		gap.join = lhs.joinAlways || rhs.joinAlways;
		gap.lineBreak = lhs.atLineEnd || rhs.atLineBeg;

		gap.indented = rhs.indented;
		gap.indents = rhs.indents;
		gap.relDents = rhs.relDents;

		gap.ws = lhs.wsAfter || rhs.wsAfter;
		gap.multWs = lhs.multAfter || rhs.multBefore;
		gap.numWs = Math.max(lhs.numAfter, rhs.numBefore);

		gap.aligned = rhs.aligned;
		gap.alignedAbove = rhs.alignedAbove;
		gap.alignedBelow = rhs.alignedBelow;
		gap.visCol = rhs.visCol;

		gap.blankAbove = lhs.blankAbove && rhs.blankAbove;
		gap.blankBelow = lhs.blankBelow && rhs.blankBelow;

		gap.noFormat = lhs.noFormat || rhs.noFormat;

		return gap;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (join) sb.append("join, ");
		if (lineBreak) sb.append("lineBreak, ");
		if (indented) sb.append("indented, ");
		if (indents > 0) sb.append("indents=" + indents + ", ");
		if (relDents > 0) sb.append("indents, ");
		if (relDents < 0) sb.append("outdents, ");
		if (ws) sb.append("ws, ");
		if (multWs) sb.append("multWs=" + numWs + ", ");
		if (aligned) sb.append("aligned=" + visCol + ", ");
		if (blankAbove) sb.append("blankAbove, ");
		if (blankBelow) sb.append("blankBelow, ");
		if (noFormat) sb.append("noFormat, ");
		int dot = sb.lastIndexOf(",");
		if (dot > -1) sb.setLength(dot);
		return sb.toString();
	}
}
