package net.certiv.adept.model;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.gson.annotations.Expose;

public class EdgeKey implements Comparable<EdgeKey> {

	private static Table<Integer, String, EdgeKey> pool;
	@Expose private int type;
	@Expose private String text;

	public static EdgeKey create(int type, String text) {
		EdgeKey key = pool.get(type, text);
		if (key == null) {
			key = new EdgeKey(type, text);
			pool.put(type, text, key);
		}
		return key;
	}

	EdgeKey() {
		if (pool == null) pool = TreeBasedTable.create();
	}

	EdgeKey(int type, String text) {
		this.type = type;
		this.text = text != null ? text : "";
	}

	public int getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EdgeKey other = (EdgeKey) obj;
		if (text == null && other.text != null) return false;
		if (!text.equals(other.text)) return false;
		if (type != other.type) return false;
		return true;
	}

	@Override
	public int compareTo(EdgeKey o) {
		if (type < o.getType()) return -1;
		if (type > o.getType()) return 1;
		return text.compareTo(o.getText());
	}

	@Override
	public String toString() {
		return String.format("EdgeKey [type=%s, text=%s]", type, text);
	}
}
