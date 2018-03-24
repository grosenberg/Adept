package net.certiv.adept.format.misc;

import java.util.StringJoiner;

import net.certiv.adept.util.Log;

public class Gap {

	public boolean join;			// {@code true} to keep or force line joining
	public boolean lineBreak;		// {@code true} to keep or force line break

	public boolean indented;		// at line begin and indented
	public int indents;				// absolute number of indents
	public int relDents;			// num indents relative to real line above

	public boolean ws;				// has ws
	public boolean multWs;			// has multiple ws
	public int numWs;				// num ws

	public boolean alignedAbove;	// whether visually aligned to a node feature in the line above
	public int visCol;				// align visual column
	public boolean blankAbove;		// needs blank line above/below

	public boolean noFormat;		// retain existing

	// ------------------------------------------------------------------------

	/**
	 * Define a whitespace gap format between real tokens based on the trailing aspects of the lhs
	 * format and the leading aspects of the rhs format.
	 */
	public static Gap define(Format lhs, Format rhs) {
		Gap gap = new Gap();

		if (lhs == null || rhs == null) {
			Log.error(Gap.class, "Gap encountered null format value.");
			gap.noFormat = true;
			return gap;
		}

		gap.join = lhs.joinAlways || rhs.joinAlways;
		gap.lineBreak = lhs.atLineEnd || rhs.atLineBeg;

		gap.indented = rhs.indented;
		gap.indents = rhs.indents;
		gap.relDents = rhs.relDents;

		gap.alignedAbove = rhs.alignedAbove;
		gap.blankAbove = lhs.blankAbove && rhs.blankAbove;
		gap.visCol = rhs.visCol;

		gap.ws = lhs.wsAfter || rhs.wsAfter;
		gap.multWs = lhs.multAfter || rhs.multBefore;
		if (gap.multWs) {
			gap.numWs = Math.max(lhs.widthAfter, rhs.widthBefore);
		} else if (gap.ws) {
			gap.numWs = 1;
		}

		gap.noFormat = lhs.noFormat || rhs.noFormat;

		return gap;
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ");
		if (join) sj.add("join");
		if (lineBreak) sj.add("lineBreak");
		if (indented) sj.add("indented=" + indents + " (" + relDents + ")");
		if (ws) sj.add("ws");
		if (multWs) sj.add("multWs=" + numWs + "");
		if (alignedAbove) sj.add("aligned=" + visCol + "");
		if (blankAbove) sj.add("blankAbove");
		if (noFormat) sj.add("noFormat");
		return sj.toString();
	}
}
