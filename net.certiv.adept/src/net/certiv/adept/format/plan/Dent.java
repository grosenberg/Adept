package net.certiv.adept.format.plan;

import com.google.gson.annotations.Expose;

public class Dent implements Comparable<Dent> {

	@Expose public int indents;		// indent level at a token index
	@Expose public Op op;			// indent direction
	@Expose public Bind bind;		// indenting hint
	@Expose public int wrap = 2;	// wrap wrapping indents; TODO: settings parameter

	public Dent(Mark mark) {
		indents = mark.indents;
		op = mark.op;
		bind = mark.bind;
	}

	public static double score(Dent a, Dent b) {
		int score = 0;
		if (a.op == b.op) score++;
		if (a.bind == b.bind) score++;
		return score / 2;
	}

	@Override
	public int compareTo(Dent dent) {
		if (op != dent.op) return op.compareTo(dent.op);
		return bind.compareTo(dent.bind);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bind == null) ? 0 : bind.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + wrap;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Dent other = (Dent) obj;
		if (bind != other.bind) return false;
		if (op != other.op) return false;
		if (wrap != other.wrap) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s [%s:%s]", indents, op, bind);
	}
}
