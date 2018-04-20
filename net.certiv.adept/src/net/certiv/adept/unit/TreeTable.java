package net.certiv.adept.unit;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Multimap implemented as a LinkedHashMap with List instance values.
 *
 * @param <K>
 * @param <V>
 */
public class TreeTable<R, C, V> {

	private final TreeMap<R, TreeMap<C, V>> table;
	// private Comparator<R> rowComp;
	private Comparator<C> colComp;

	public TreeTable() {
		this(null, null);
	}

	public TreeTable(Comparator<R> comp) {
		this(comp, null);
	}

	public TreeTable(Comparator<R> rowComp, Comparator<C> colComp) {
		// this.rowComp = rowComp;
		this.colComp = colComp;
		table = new TreeMap<>(rowComp);
	}

	public V get(R row, C col) {
		TreeMap<C, V> colMap = table.get(row);
		if (colMap != null) return colMap.get(col);
		return null;
	}

	public V put(R row, C col, V value) {
		TreeMap<C, V> colMap = table.get(row);
		if (colMap == null) {
			colMap = new TreeMap<>(colComp);
			table.put(row, colMap);
		}
		return colMap.put(col, value);
	}

	public void remove(R row, C col) {
		TreeMap<C, V> colMap = table.get(row);
		if (colMap != null) colMap.remove(col);
	}

	public boolean contains(R row, C col) {
		TreeMap<C, V> colMap = table.get(row);
		if (colMap == null) return false;
		return colMap.containsKey(col);
	}

	public boolean isEmpty() {
		return table.isEmpty();
	}

	public int rows() {
		return table.size();
	}

	public V encloses(R row, C col) {
		Entry<R, TreeMap<C, V>> rowEntry = table.floorEntry(row);
		if (rowEntry == null) return null;

		TreeMap<C, V> colMap = rowEntry.getValue();
		Entry<C, V> colEntry = colMap.ceilingEntry(col);
		if (colEntry == null) {
			R prior = table.lowerKey(rowEntry.getKey());
			return encloses(prior, col);
		}
		return colEntry.getValue();
	}

	public void clear() {
		table.clear();
	}
}
