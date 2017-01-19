package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.List;

import net.certiv.adept.parser.AdeptToken;
import net.certiv.adept.parser.ParseData;

public class LineInfo {

	int first = -1;
	int last = -1;
	int terminator = -1;
	int lineComment = -1;
	List<Integer> blockComments = new ArrayList<>();

	private int offset;
	private int end;
	private int len;
	private int leadLen;
	private boolean blank = true;

	/* Characterize a line represented by a sequence of tokens. */
	public LineInfo(ParseData data, List<AdeptToken> line) {
		for (int idx = 0; idx < line.size(); idx++) {
			AdeptToken token = line.get(idx);
			int type = token.getType();
			if (type == data.VWS) {
				if (first == -1) first = idx;
				terminator = idx;
			} else if (type == data.BLOCKCOMMENT) {
				if (first == -1) first = idx;
				blockComments.add(idx);
				blank = false;
			} else if (type == data.LINECOMMENT) {
				if (first == -1) first = idx;
				lineComment = idx;
				blank = false;
			} else {
				if (first == -1) first = idx;
				last = idx;
				blank = false;
			}
		}

		offset = line.get(0).getStartIndex();
		end = line.get(line.size() - 1).getStopIndex();
		len = end - offset + 1;

		leadLen = line.get(first).getStartIndex() - offset;
	}

	public boolean isBlank() {
		return blank;
	}

	public int getOffset() {
		return offset;
	}

	public int getEnd() {
		return end;
	}

	public int getLen() {
		return len;
	}

	public int getLeadLen() {
		return leadLen;
	}
}
