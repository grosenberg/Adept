package net.certiv.adept.model;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

public class FeatKey implements Comparable<FeatKey> {

	private static Map<Long, FeatKey> pool = new HashMap<>();

	private int docId;
	private int type;
	private int start;
	private int stop;

	public static FeatKey create(int docId, int type, Token start, Token stop) {
		long mark = combine(docId, type, start.getTokenIndex(), stop.getTokenIndex());
		FeatKey key = pool.get(mark);
		if (key == null) {
			key = new FeatKey(docId, type, start.getTokenIndex(), stop.getTokenIndex());
			pool.put(mark, key);
		}
		return key;
	}

	FeatKey(int docId, int type, int start, int stop) {
		this.docId = docId;
		this.type = type;
		this.start = start;
		this.stop = stop;
	}

	private static long combine(int docId, int type, int start, int stop) {
		return docId << 48 | type << 32 | start << 16 | stop;
	}

	public int getDocId() {
		return docId;
	}

	public int getType() {
		return type;
	}

	public int getStart() {
		return start;
	}

	public int getStop() {
		return stop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + docId;
		result = prime * result + type;
		result = prime * result + start;
		result = prime * result + stop;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		FeatKey other = (FeatKey) obj;
		if (docId != other.docId) return false;
		if (type != other.type) return false;
		if (start != other.start) return false;
		if (stop != other.stop) return false;
		return true;
	}

	@Override
	public int compareTo(FeatKey o) {
		if (docId < o.docId) return -1;
		if (docId > o.docId) return 1;
		if (type < o.type) return -1;
		if (type > o.type) return 1;
		if (start < o.start) return -1;
		if (start > o.start) return 1;
		if (stop < o.stop) return -1;
		if (stop > o.stop) return 1;
		return 0;
	}

	@Override
	public String toString() {
		return String.format("FeatKey [docId=%s, type=%s, start=%s, stop=%s]", docId, type, start, stop);
	}
}
