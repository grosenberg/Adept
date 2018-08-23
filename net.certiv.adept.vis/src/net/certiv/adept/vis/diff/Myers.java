package net.certiv.adept.vis.diff;

import java.util.List;

import net.certiv.adept.lang.AdeptToken;

public class Myers {

	public static final Myers INST = new Myers();

	public Myers() {}

	public Transform diff(List<AdeptToken> lhsTokens, List<AdeptToken> rhsTokens) {
		AdeptToken[] src = lhsTokens.toArray(new AdeptToken[0]);
		AdeptToken[] res = rhsTokens.toArray(new AdeptToken[0]);
		Transform result = new Transform(src, res);

		RangeDiff[] diffs = RangeDiffer.find(new RangeComp(src), new RangeComp(res));
		for (RangeDiff diff : diffs) {
			Chunk cl = new Chunk(diff.leftStart(), diff.leftLength());
			Chunk cr = new Chunk(diff.rightStart(), diff.rightLength());
			result.add(new Delta(cl, cr));
		}
		return result;
	}

	private class RangeComp implements IRangeComp {
		private AdeptToken[] tokens;

		RangeComp(AdeptToken[] tokens) {
			this.tokens = tokens;
		}

		@Override
		public int getRangeCount() {
			return tokens.length;
		}

		@Override
		public boolean rangesEqual(int tIdx, IRangeComp other, int oIdx) {
			AdeptToken tok1 = tokens[tIdx];
			AdeptToken tok2 = ((RangeComp) other).tokens[oIdx];

			if (tok1 == tok2) return true;
			if (tok1 == null && tok2 != null) return false;
			if (tok1 != null && tok2 == null) return false;
			return tok1.equivTo(tok2);
		}

		@Override
		public boolean skipRangeComparison(int length, int maxLength, IRangeComp other) {
			return false;
		}
	}
}
