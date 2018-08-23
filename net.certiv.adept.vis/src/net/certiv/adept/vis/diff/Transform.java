package net.certiv.adept.vis.diff;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.util.Strings;

public class Transform implements Iterable<Delta> {

	private final List<Delta> deltas = new LinkedList<>();
	private final AdeptToken[] srcTokens;
	private final AdeptToken[] resTokens;

	public Transform(AdeptToken[] src, AdeptToken[] res) {
		this.srcTokens = adjust(src);
		this.resTokens = adjust(res);
	}

	private AdeptToken[] adjust(AdeptToken[] src) {
		int vOffset = 0;

		for (int idx = 0; idx < src.length; idx++) {
			AdeptToken token = src[idx];
			token.setVisPos(vOffset);

			// calc next vOffset
			String txt = token.getText();
			for (int jdx = 0, len = txt.length(); jdx < len; jdx++) {
				switch (txt.charAt(jdx)) {
					case Strings.NLC:
						vOffset++;
					case Strings.RET:
						break;
					default:
						vOffset++;
				}
			}
		}
		return src;
	}

	@Override
	public Iterator<Delta> iterator() {
		return deltas.iterator();
	}

	public void add(Delta delta) {
		deltas.add(delta);
		delta.setTransform(this);
	}

	public List<Delta> getDeltas() {
		return deltas;
	}

	public AdeptToken[] getSrcTokens() {
		return srcTokens;
	}

	public AdeptToken[] getResTokens() {
		return resTokens;
	}

	public int[][] getLocations(Delta delta) {
		int lIdx = delta.getSource().getBegin();	// token index
		int lNum = delta.getSource().getLen();		// number of tokens

		int lStart = srcTokens[lIdx].getVisPos();
		int lStop = srcTokens[lIdx + lNum].getVisPos();
		int lLen = lStop - lStart;

		int rIdx = delta.getResult().getBegin();
		int rNum = delta.getResult().getLen();

		int rStart = resTokens[rIdx].getVisPos();
		int rStop = resTokens[rIdx + rNum].getVisPos();
		int rLen = rStop - rStart;

		int loc[][] = new int[][] { { lStart, lLen }, { rStart, rLen } };
		return loc;
	}

	public String[] getText(Chunk srcChunk, Chunk resChunk) {
		return getText(srcChunk, resChunk, false);
	}

	public String[] getText(Chunk srcChunk, Chunk resChunk, boolean encode) {
		String[] txt = new String[] { "", "" };
		if (srcChunk != null) {
			txt[0] = getText(srcTokens, srcChunk, encode);
		}
		if (resChunk != null) {
			txt[1] = getText(resTokens, resChunk, encode);
		}
		return txt;
	}

	private String getText(AdeptToken[] tokens, Chunk chunk, boolean encode) {
		int beg = chunk.getBegin();
		int len = chunk.getLen();
		StringBuilder sb = new StringBuilder();
		for (int idx = beg; idx < beg + len; idx++) {
			sb.append(tokens[idx].getText());
		}
		return encode ? Strings.encodeWS(sb.toString()) : sb.toString();
	}
}
