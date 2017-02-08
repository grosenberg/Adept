package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.List;

import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;

public class LineInfo {

	// token offsets in the line buffer
	int first = -1;			// first real or vws token
	int last = -1;			// last real token
	int terminator = -1;	// line terminator
	int lineComment = -1;
	
	List<Integer> blockComments = new ArrayList<>();

	private int lineBegOffset;
	private int lineEndOffset;
	private int lineCharLength;
	private int indentCharLength;
	private boolean blank = true;

	/* Characterize a line represented by a sequence of tokens. */
	public LineInfo(ParseData data, List<AdeptToken> line) {
		for (int idx = 0; idx < line.size(); idx++) {
			AdeptToken token = line.get(idx);
			int type = token.getType();
			if (type == data.HWS) continue;

			if (first == -1) {
				first = idx;
			}
			if (type == data.VWS) {
				terminator = idx;
			} else if (type == data.BLOCKCOMMENT) {
				blockComments.add(idx);
				blank = false;
			} else if (type == data.LINECOMMENT) {
				lineComment = idx;
				blank = false;
			} else {
				last = idx;
				blank = false;
			}
		}

		lineBegOffset = line.get(0).getStartIndex();
		lineEndOffset = line.get(line.size() - 1).getStopIndex();
		lineCharLength = lineEndOffset - lineBegOffset + 1;

		indentCharLength = line.get(first).getStartIndex() - lineBegOffset;
	}

	public boolean isBlank() {
		return blank;
	}

	public int getOffset() {
		return lineBegOffset;
	}

	public int getEnd() {
		return lineEndOffset;
	}

	public int getLen() {
		return lineCharLength;
	}

	public int getLeadLen() {
		return indentCharLength;
	}
}
