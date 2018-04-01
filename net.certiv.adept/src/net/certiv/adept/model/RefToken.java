package net.certiv.adept.model;

import java.util.Comparator;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.google.gson.annotations.Expose;

import net.certiv.adept.format.align.Align;
import net.certiv.adept.format.align.Gap;
import net.certiv.adept.format.align.Place;
import net.certiv.adept.format.indent.Dent;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.util.Damerau;
import net.certiv.adept.unit.Ranked;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Strings;

/**
 * Represents an underlying token for a feature.
 * <p>
 * For a source document, corresponds to a unique concrete token, defined as a lexer token having a
 * unique token index.
 * <p>
 * For the collection of corpus features, each represents a unique equivalent token.
 */
public class RefToken implements Comparator<RefToken>, Ranked, Cloneable {

	private static final int TXTLIMIT = 16;
	private static final String LEFT = "%s[%s] %s(%s)";
	private static final String MIDL = " > %s%s-%s[%s] > ";
	private static final String RGHT = "%s(%s) %s[%s]";

	private static final double RankSignificance = 0.2;		// TODO: tune parameter
	private static final double AssocSignificance = 0.2;	// TODO: tune parameter

	// derived from a corpus ref token
	public RefToken matched;
	public TreeMultiset<Double, RefToken> scored;

	// corpus frequency
	@Expose public int rank = 1;

	// feature token - intrinsic
	@Expose public int type;
	@Expose public int index;
	@Expose public boolean isComment;
	@Expose public int line;			// node line (0..n)
	@Expose public int col;				// node column (0..n)
	@Expose public String text;			// node content

	// feature token - computed
	@Expose public String nodeName;
	@Expose public int visCol;					// actual visual node column (0..n)
	@Expose public Place place;
	@Expose public Dent dent;					// indent level & hint

	// aligns
	@Expose public Align align = Align.NONE;	// type of align group
	@Expose public Gap gap = Gap.VARIABLE;		// characterized number of real intra-aspect tokens
	@Expose public Place inGroup = Place.ANY;	// in the lines of a group
	@Expose public Place inLine = Place.ANY;	// within a line
	@Expose public int grpTotal;				// number of group participants

	// left adjunct
	@Expose public int lIndex = -1;
	@Expose public int lType = Token.INVALID_TYPE;
	@Expose public Spacing lSpacing = Spacing.UNKNOWN;
	@Expose public String lActual = "";
	@Expose public List<Integer> lAssocs;

	// right adjunct
	@Expose public int rIndex = -1;
	@Expose public int rType = Token.INVALID_TYPE;
	@Expose public Spacing rSpacing = Spacing.UNKNOWN;
	@Expose public String rActual = "";
	@Expose public List<Integer> rAssocs;

	public RefToken(AdeptToken token) {
		this.type = token.getType();
		this.index = token.getTokenIndex();
		this.isComment = token.isComment();
		this.line = token.getLine();
		this.col = token.getCharPositionInLine();
		this.text = Strings.shorten(token.getText(), TXTLIMIT);
	}

	public void setAlign(Align align, Gap gap, Place[] places, int grpTotal) {
		this.align = align;
		this.gap = gap;
		this.inGroup = places[0];
		this.inLine = places[1];
		this.grpTotal = grpTotal;
	}

	public void setLeft(AdeptToken left, Spacing lSpacing, String lActual, List<Integer> lAssocs) {
		this.lIndex = left.getTokenIndex();
		this.lType = left.getType();
		this.lSpacing = lSpacing;
		this.lActual = lActual;
		this.lAssocs = lAssocs;
	}

	public void setRight(AdeptToken right, Spacing rSpacing, String rActual, List<Integer> rAssocs) {
		this.rIndex = right.getTokenIndex();
		this.rType = right.getType();
		this.rSpacing = rSpacing;
		this.rActual = rActual;
		this.rAssocs = rAssocs;
	}

	@Override
	public void incRank() {
		rank++;
	}

	public void addRank(int rank) {
		this.rank += rank;
	}

	@Override
	public void decRank() {
		rank--;
	}

	@Override
	public int getRank() {
		return rank;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	// ---- Formatter support ----

	public boolean isComment() {
		return isComment;
	}

	public boolean atBol() {
		return place == Place.SOLO || place == Place.BEG;
	}

	public boolean atEol() {
		return place == Place.SOLO || place == Place.END;
	}

	// ---- Match support ----

	/**
	 * Score the similarity between this ref token and the given, possibly matching ref token. The
	 * returned similarity value is normalized to the range {@code 0..1}, where {@code 0} means no
	 * similarity and {@code 1} means effective identify.
	 *
	 * @param matchable the ref token to consider
	 * @param featWeight weight of the containing matchable feature
	 * @param maxRank maximum ranking of the ref tokens in the matchable features
	 * @return similarity value normalized to {@code 0..1}, where {@code 0} is no similarity and
	 *         {@code 1} is effective identify
	 */
	public double score(RefToken matchable, double maxRank) {
		double score = 0;
		double cnt = 10;

		if (place == matchable.place) score++;
		if (lType == matchable.lType) score++;
		if (lType == matchable.lType) score++;
		if (rType == matchable.rType) score++;
		if (rType == matchable.rType) score++;
		if (lSpacing == matchable.lSpacing) score++;
		if (rSpacing == matchable.rSpacing) score++;
		if (align == matchable.align) score++;
		if (align != Align.NONE) {
			if (gap != matchable.gap) score++;
			if (inGroup != matchable.inGroup) score++;
			if (inLine != matchable.inLine) score++;
			cnt += 3;
		}
		score += dent.score(matchable.dent);
		score += rank / maxRank;

		if (lAssocs != null && matchable.lAssocs != null) {
			double lDist = Damerau.distance(lAssocs, matchable.lAssocs);
			score += Damerau.simularity(lDist, lAssocs.size(), matchable.lAssocs.size());
		}
		if (rAssocs != null && matchable.rAssocs != null) {
			double rDist = Damerau.distance(rAssocs, matchable.rAssocs);
			score += Damerau.simularity(rDist, rAssocs.size(), matchable.rAssocs.size());
		}

		cnt += cnt * RankSignificance + cnt * AssocSignificance;

		return score / cnt;
	}

	/**
	 * Assign the 'best' choice to {@code matched}. Selects from the given descending score key multiset
	 * of possible choices.
	 *
	 * @param scored descending key multiset of possible choices
	 */
	public void chooseBest(TreeMultiset<Double, RefToken> scored) {
		this.scored = scored;			// save scored for visualization
		List<RefToken> highest = scored.getList(scored.firstKey());

		RefToken best = highest.get(0);	// TODO: don't punt
		this.matched = derive(best);
	}

	private RefToken derive(RefToken best) {
		RefToken result = copy();
		result.lSpacing = best.lSpacing;
		result.rSpacing = best.rSpacing;
		return result;
	}

	/** Deep clone. */
	public RefToken copy() {
		try {
			return (RefToken) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone failure!");
		}
	}

	/** Comparison for merging; defined using moderate equivalency. */
	public boolean equivalentTo(RefToken ref) {
		if (ref == null) return false;
		if (compare(this, ref) != 0) return false;
		if (lAssocs == null ^ ref.lAssocs == null) return false;
		if (lAssocs != null && ref.lAssocs != null) {
			if (!lAssocs.equals(ref.lAssocs)) return false;
		}
		if (rAssocs == null ^ ref.rAssocs == null) return false;
		if (rAssocs != null && ref.rAssocs != null) {
			if (!rAssocs.equals(ref.rAssocs)) return false;
		}
		return true;
	}

	/** Comparison for sorting; defined using weak equivalency. */
	@Override
	public int compare(RefToken r1, RefToken r2) {
		if (r1.place != r2.place) return r1.place.compareTo(r2.place);
		if (r1.dent.compareTo(r2.dent) != 0) return r1.dent.compareTo(r2.dent);
		if (r1.lType < r2.lType) return -1;
		if (r1.lType > r2.lType) return 1;
		if (r1.rType < r2.rType) return -1;
		if (r1.rType > r2.rType) return 1;
		if (r1.lSpacing != r2.lSpacing) return r1.lSpacing.compareTo(r2.lSpacing);
		if (r1.rSpacing != r2.rSpacing) return r1.rSpacing.compareTo(r2.rSpacing);
		if (align != Align.NONE) {
			if (r1.align != r2.align) return r1.align.compareTo(r2.align);
			if (r1.gap != r2.gap) return r1.gap.compareTo(r2.gap);
			if (r1.inGroup != r2.inGroup) return r1.inGroup.compareTo(r2.inGroup);
			if (r1.inLine != r2.inLine) return r1.inLine.compareTo(r2.inLine);
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dent == null) ? 0 : dent.hashCode());
		result = prime * result + ((dent == null) ? 0 : dent.indents);
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((inGroup == null) ? 0 : inGroup.hashCode());
		result = prime * result + ((inLine == null) ? 0 : inLine.hashCode());
		result = prime * result + ((align == null) ? 0 : align.hashCode());
		result = prime * result + ((gap == null) ? 0 : gap.hashCode());
		result = prime * result + lType;
		result = prime * result + lIndex;
		result = prime * result + ((lAssocs == null) ? 0 : lAssocs.hashCode());
		result = prime * result + ((lSpacing == null) ? 0 : lSpacing.hashCode());
		result = prime * result + ((lActual == null) ? 0 : lActual.hashCode());
		result = prime * result + rType;
		result = prime * result + rIndex;
		result = prime * result + ((rAssocs == null) ? 0 : rAssocs.hashCode());
		result = prime * result + ((rSpacing == null) ? 0 : rSpacing.hashCode());
		result = prime * result + ((rActual == null) ? 0 : rActual.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		RefToken other = (RefToken) obj;
		if (dent == null) {
			if (other.dent != null) return false;
		} else if (!dent.equals(other.dent)) {
			return false;
		} else if (dent.indents != other.dent.indents) {
			return false;
		}
		if (place != other.place) return false;
		if (inGroup != other.inGroup) return false;
		if (inLine != other.inLine) return false;
		if (align != other.align) return false;
		if (gap != other.gap) return false;

		if (lType != other.lType) return false;
		if (lIndex != other.lIndex) return false;
		if (lAssocs == null) {
			if (other.lAssocs != null) return false;
		} else if (!lAssocs.equals(other.lAssocs)) {
			return false;
		}
		if (lSpacing != other.lSpacing) return false;
		if (lActual == null) {
			if (other.lActual != null) return false;
		} else if (!lActual.equals(other.lActual)) {
			return false;
		}
		if (rType != other.rType) return false;
		if (rIndex != other.rIndex) return false;
		if (rAssocs == null) {
			if (other.rAssocs != null) return false;
		} else if (!rAssocs.equals(other.rAssocs)) {
			return false;
		}
		if (rSpacing != other.rSpacing) return false;
		if (rActual == null) {
			if (other.rActual != null) return false;
		} else if (!rActual.equals(other.rActual)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		switch (place) {
			case SOLO:
				return String.format(MIDL, type, inGroup, inLine, index);
			case BEG:
				return String.format("BOL" + MIDL + RGHT, type, inGroup, inLine, index, rSpacing, rActual, rType,
						rIndex);
			case END:
				return String.format(LEFT + MIDL + "EOL", lType, lIndex, lSpacing, lActual, type, inGroup, inLine,
						index);
			default:
				return String.format("%s[%s] %s(%s) > %s[%s] > %s(%s) %s[%s]", lType, lIndex, lSpacing, lActual, type,
						inGroup, inLine, index, rSpacing, rActual, rType, rIndex);
		}
	}
}
