/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.google.gson.annotations.Expose;

import net.certiv.adept.format.plan.Count;
import net.certiv.adept.format.plan.Dent;
import net.certiv.adept.format.plan.Place;
import net.certiv.adept.format.plan.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.Ranked;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
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

	// for visualization
	public String text;

	// derived from a corpus ref token
	public RefToken matched;

	// corpus frequency
	@Expose public int rank = 1;

	// feature token - intrinsic
	@Expose public int type;
	@Expose public int index;
	@Expose public boolean isComment;

	// feature token - computed
	@Expose public Place place;				// characterized placement in line
	@Expose public Dent dent;				// indent level & hint
	@Expose public List<Context> contexts;	// associates

	// aligns
	@Expose public Scheme scheme = Scheme.NONE;	// type of align group
	@Expose public Count gap = Count.NONE;		// number of reals between members
	@Expose public Place inGroup = Place.ANY;	// in the lines of a group
	@Expose public Place inLine = Place.ANY;	// within a line
	@Expose public Count lines = Count.NONE;	// number of group lines

	// left adjunct
	@Expose public int lIndex = -1;
	@Expose public int lType = Token.INVALID_TYPE;
	@Expose public Spacing lSpacing = Spacing.UNKNOWN;
	@Expose public String lActual = "";

	// right adjunct
	@Expose public int rIndex = -1;
	@Expose public int rType = Token.INVALID_TYPE;
	@Expose public Spacing rSpacing = Spacing.UNKNOWN;
	@Expose public String rActual = "";

	public RefToken(AdeptToken token) {
		this.type = token.getType();
		this.index = token.getTokenIndex();
		this.isComment = token.isComment();
		this.text = Strings.shorten(token.getText(), TXTLIMIT);
	}

	public void setAlign(Scheme align, Count gap, Place[] places, Count lines) {
		this.scheme = align;
		this.gap = gap;
		this.inGroup = places[0];
		this.inLine = places[1];
		this.lines = lines;
	}

	public void setLeft(AdeptToken left, Spacing lSpacing, String lActual) {
		this.lIndex = left.getTokenIndex();
		this.lType = left.getType();
		this.lSpacing = lSpacing;
		this.lActual = lActual;
	}

	public void setRight(AdeptToken right, Spacing rSpacing, String rActual) {
		this.rIndex = right.getTokenIndex();
		this.rType = right.getType();
		this.rSpacing = rSpacing;
		this.rActual = rActual;
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

	public void createContext(List<Integer> lAssocs, List<Integer> rAssocs) {
		contexts = new ArrayList<>();
		contexts.add(new Context(lAssocs, rAssocs));
	}

	public void mergeContexts(List<Context> assocs) {
		for (Context assoc : assocs) {
			Context existing = Context.find(contexts, assoc);
			if (existing != null) {
				existing.addRank(assoc.getRank());
			} else {
				contexts.add(assoc.copy());
			}
		}
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
	 * Score the similarity between this source document ref token and the given, possibly matching ref
	 * token from a corpus feature. The returned similarity value is normalized to the range
	 * {@code 0..1}, where {@code 0} means no similarity and {@code 1} means effective identify.
	 *
	 * @param matchable the ref token to consider
	 * @param featWeight weight of the containing matchable feature
	 * @param maxRank maximum ranking of the ref tokens in the matchable features
	 * @return similarity value normalized to {@code 0..1}, where {@code 0} is no similarity and
	 *         {@code 1} is effective identify
	 */
	public double score(RefToken matchable, double[] maxRank) {
		double score = 0;
		double cnt = 10;

		score += Place.score(place, matchable.place);
		score += Dent.score(dent, matchable.dent);
		score += Spacing.score(lSpacing, matchable.lSpacing);
		score += Spacing.score(rSpacing, matchable.rSpacing);
		score += Context.score(contexts.get(0), matchable.contexts, maxRank[1]);

		if (lType == matchable.lType) score++;
		if (lType == matchable.lType) score++;
		if (rType == matchable.rType) score++;
		if (rType == matchable.rType) score++;
		if (scheme == matchable.scheme) score++;

		if (scheme != Scheme.NONE) {	// TODO: refine
			double aligns = 0;
			if (gap == matchable.gap) aligns++;
			if (inGroup == matchable.inGroup) aligns++;
			if (inLine == matchable.inLine) aligns++;
			if (lines == matchable.lines) aligns++;
			score += aligns / 4;
			cnt++;
		}

		score += rank / maxRank[0] / cnt;
		return (score - 1) / cnt;
	}

	/**
	 * Assign the 'best' choice to {@code matched}. Selects the highest scored corpus ref token from the
	 * set of possible choices.
	 *
	 * @param scored descending valued key multiset of possible choices
	 */
	public void chooseBest(TreeMultiset<Double, RefToken> scored) {

		// choose highest scored set of corpus ref tokens
		List<RefToken> highest = scored.getList(scored.firstKey());
		if (highest.size() > 1) {
			Log.info(this, "Multiple identically scored choices!");
		}
		RefToken best = highest.get(0);
		this.matched = best.copy();
	}

	/** Deep clone. */
	public RefToken copy() {
		try {
			RefToken copy = (RefToken) super.clone();
			copy.contexts = new ArrayList<>();
			for (Context context : contexts) {
				copy.contexts.add(context.copy());
			}
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone failure!");
		}
	}

	/** Comparison for merging. */
	public boolean equivalentTo(RefToken ref) {
		if (ref == null) return false;
		return compare(this, ref) == 0;
	}

	/** Comparison for sorting. */
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
		if (scheme != Scheme.NONE) {
			if (r1.scheme != r2.scheme) return r1.scheme.compareTo(r2.scheme);
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
		result = prime * result + ((contexts == null) ? 0 : contexts.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((inGroup == null) ? 0 : inGroup.hashCode());
		result = prime * result + ((inLine == null) ? 0 : inLine.hashCode());
		result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
		result = prime * result + ((gap == null) ? 0 : gap.hashCode());
		result = prime * result + lType;
		result = prime * result + lIndex;
		result = prime * result + ((lSpacing == null) ? 0 : lSpacing.hashCode());
		result = prime * result + ((lActual == null) ? 0 : lActual.hashCode());
		result = prime * result + rType;
		result = prime * result + rIndex;
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
		if (contexts == null) {
			if (other.contexts != null) return false;
		} else if (!contexts.equals(other.contexts)) {
			return false;
		}
		if (place != other.place) return false;
		if (inGroup != other.inGroup) return false;
		if (inLine != other.inLine) return false;
		if (scheme != other.scheme) return false;
		if (gap != other.gap) return false;

		if (lType != other.lType) return false;
		if (lIndex != other.lIndex) return false;
		if (lSpacing != other.lSpacing) return false;
		if (lActual == null) {
			if (other.lActual != null) return false;
		} else if (!lActual.equals(other.lActual)) {
			return false;
		}
		if (rType != other.rType) return false;
		if (rIndex != other.rIndex) return false;
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
