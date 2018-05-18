/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.unit.Ranked;

public class Context implements Ranked, Comparable<Context>, Cloneable {

	public static double RankFactor = 1.0 / ParseRecord.AssocLimit / 2;

	// context frequency
	@Expose public int rank = 1;

	@Expose public final List<Integer> lAssocs;
	@Expose public final List<Integer> rAssocs;

	public Context(List<Integer> lAssocs, List<Integer> rAssocs) {
		this.lAssocs = lAssocs != null ? lAssocs : Collections.emptyList();
		this.rAssocs = rAssocs != null ? rAssocs : Collections.emptyList();
	}

	public static Context find(List<Context> contexts, Context target) {
		int idx = contexts.indexOf(target);
		if (idx > -1) return contexts.get(idx);
		return null;
	}

	/**
	 * Returns the score for the closest one of the given matchables list of contexts to the given
	 * source context.
	 */
	public static double score(Context source, List<Context> matchables, double maxRank) {
		Context found = find(matchables, source);
		if (found != null) return 1;

		double max = 0;
		for (Context matchable : matchables) {
			double score = 0;
			score += score(source.lAssocs, matchable.lAssocs) / 2;
			score += score(source.rAssocs, matchable.rAssocs) / 2;
			score += RankFactor * matchable.rank / maxRank;
			max = Math.max(max, score);
		}
		return max - RankFactor;
	}

	/** For visualization */
	public static double[] score(Context source, Context matchable, double maxRank) {
		double[] score = new double[4];
		score[0] = score(source.lAssocs, matchable.lAssocs) / 2;
		score[1] = score(source.rAssocs, matchable.rAssocs) / 2;
		score[2] = RankFactor * matchable.rank / maxRank;
		score[3] = RankFactor * -1;
		return score;
	}

	public static double score(List<Integer> src, List<Integer> mat) {
		int min = Math.min(src.size(), mat.size());
		if (min == 0) return 1;

		int max = Math.max(src.size(), mat.size());
		int rel = 1 + max - min;

		double score = 0;
		for (int idx = 0; idx < min && src.get(idx) == mat.get(idx); idx++) {
			score++;
		}
		return score / max / rel;
	}

	public void addRank(int rank) {
		this.rank += rank;
	}

	@Override
	public void incRank() {
		rank++;
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

	@Override
	public int compareTo(Context ctx) {
		if (this.hashCode() > ctx.hashCode()) return 1;
		if (this.hashCode() < ctx.hashCode()) return -1;
		return 0;
	}

	/** Deep clone. */
	public Context copy() {
		Context copy = new Context(new ArrayList<>(lAssocs), new ArrayList<>(rAssocs));
		copy.setRank(getRank());
		return copy;
	}

	private static final int prime = 31;
	private int hash = 1;

	@Override
	public int hashCode() {
		if (hash == 1) {
			hash = prime * hash + lAssocs.hashCode();
			hash = prime * hash + rAssocs.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Context other = (Context) obj;
		if (lAssocs == null) {
			if (other.lAssocs != null) return false;
		} else if (!lAssocs.equals(other.lAssocs)) return false;
		if (rAssocs == null) {
			if (other.rAssocs != null) return false;
		} else if (!rAssocs.equals(other.rAssocs)) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Context [lAssocs=%s, rAssocs=%s]", lAssocs, rAssocs);
	}
}
