package net.certiv.adept.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

import net.certiv.adept.unit.Ranked;

public class Context implements Ranked, Comparable<Context>, Cloneable {

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

	public static double score(Context source, List<Context> matchables, double maxRank) {
		Context matched = find(matchables, source);
		return matched != null ? 1 + matched.rank / maxRank : 0;
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
