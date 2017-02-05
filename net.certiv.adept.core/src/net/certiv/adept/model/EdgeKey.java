package net.certiv.adept.model;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.gson.annotations.Expose;

import net.certiv.adept.model.equiv.EFeature;

public class EdgeKey implements Comparable<EdgeKey> {

	private static Table<EFeature, EFeature, EdgeKey> pool = TreeBasedTable.create();

	@Expose private int typeR;
	@Expose private int typeL;
	@Expose private String textR;
	@Expose private String textL;

	public static EdgeKey create(Feature root, Feature leaf) {
		EdgeKey key = pool.get(root.equiv(), leaf.equiv());
		if (key == null) {
			key = new EdgeKey(root, leaf);
			pool.put(root.equiv(), leaf.equiv(), key);
		}
		return key;
	}

	public EdgeKey(Feature root, Feature leaf) {
		this.typeR = root.getType();
		this.typeL = leaf.getType();
		this.textR = root.isVar() || root.isRule() ? "" : root.getText();
		this.textL = leaf.isVar() || leaf.isRule() ? "" : leaf.getText();
	}

	public int getType() {
		return typeL;
	}

	public String getText() {
		return textL;
	}

	@Override
	public int compareTo(EdgeKey o) {
		if (typeR < o.getType()) return -1;
		if (typeR > o.getType()) return 1;
		return textR.compareTo(o.getText());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		EdgeKey other = (EdgeKey) obj;
		if (textL == null) {
			if (other.textL != null) return false;
		} else if (!textL.equals(other.textL)) return false;
		if (textR == null) {
			if (other.textR != null) return false;
		} else if (!textR.equals(other.textR)) return false;
		if (typeL != other.typeL) return false;
		if (typeR != other.typeR) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + textL.hashCode();
		result = prime * result + textR.hashCode();
		result = prime * result + typeL;
		result = prime * result + typeR;
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s '%s'", typeL, textL);
	}
}
