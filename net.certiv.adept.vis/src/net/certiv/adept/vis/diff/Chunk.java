package net.certiv.adept.vis.diff;

public class Chunk {
	private int beg;
	private int len;

	public Chunk(int beg, int len) {
		this.beg = beg;
		this.len = len;
	}

	public int getBegin() {
		return beg;
	}

	void setBegin(int begin) {
		this.beg = begin;
	}

	public int getLen() {
		return len;
	}

	void setLen(int len) {
		this.len = len;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + beg;
		result = prime * result + len;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Chunk)) return false;
		Chunk other = (Chunk) obj;
		if (beg != other.beg) return false;
		if (len != other.len) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("@%s:%s", beg, len);
	}
}
