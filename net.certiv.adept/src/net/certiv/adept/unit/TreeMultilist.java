package net.certiv.adept.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Multimap implemented as a TreeMap with List instance values
 *
 * @param <K> key
 * @param <V> value
 */
public class TreeMultilist<K, V> {

	private final TreeMap<K, List<V>> map;
	private Comparator<? super K> keyComp;
	private Comparator<? super V> valComp;

	public TreeMultilist() {
		this(null, null);
	}

	public TreeMultilist(Comparator<? super K> keyComp) {
		this(keyComp, null);
	}

	public TreeMultilist(Comparator<? super K> keyComp, Comparator<? super V> valComp) {
		this.map = new TreeMap<>(keyComp);
		this.keyComp = keyComp;
		this.valComp = valComp;
	}

	public void setValueComparator(Comparator<? super V> valComp) {
		this.valComp = valComp;
	}

	/** Get the values for the given key. Sort the values if the value comparator is not null. */
	public List<V> get(K key) {
		List<V> values = map.get(key);
		if (values != null && valComp != null) values.sort(valComp);
		return values;
	}

	public boolean put(K key, V value) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		return list.add(value);
	}

	public boolean put(K key, Collection<V> values) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		return list.addAll(values);
	}

	public boolean put(TreeMultiset<K, V> map) {
		boolean ok = true;
		for (K key : map.keySet()) {
			Set<V> val = map.get(key);
			ok &= put(key, val);
		}
		return ok;
	}

	/** @throws NoSuchElementException */
	public K firstKey() {
		return map.firstKey();
	}

	/** @throws NoSuchElementException */
	public K lastKey() {
		return map.lastKey();
	}

	/** Returns {@code true} if a value set for the given key exists. */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/** Returns {@code true} if at least one of the value sets contains the given value. */
	public boolean containsValue(V value) {
		return map.containsValue(value);
	}

	/** Returns {@code true} if the value set for the given key contains the given value. */
	public boolean contains(K key, V value) {
		if (!map.containsKey(key)) return false;
		return map.get(key).contains(value);
	}

	public Set<Entry<K, List<V>>> entrySet() {
		return map.entrySet();
	}

	public Entry<K, List<V>> lastEntry() {
		return map.lastEntry();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public List<K> keyList() {
		return new ArrayList<>(map.keySet());
	}

	@Deprecated
	public K keyFirst() {
		return map.firstKey();
	}

	@Deprecated
	public K keyLast() {
		return map.lastKey();
	}

	public int keySize() {
		return map.size();
	}

	/**
	 * Returns the collection of lists of the values present in this map instance.
	 */
	@SuppressWarnings("unchecked")
	public <C extends Collection<List<V>>> C values() {
		return (C) map.values();
	}

	/**
	 * Returns a consolidated list of all of the values present in this map instance. Sorts the values
	 * if the value comparator is not null.
	 */
	public List<V> valuesAll() {
		ArrayList<V> results = new ArrayList<>();
		for (List<V> list : map.values()) {
			results.addAll(list);
		}
		if (valComp != null) results.sort(valComp);
		return results;
	}

	public int size() {
		return map.size();
	}

	/** Size of the last list of values. */
	public int lastSize() {
		if (map.isEmpty()) return 0;
		return map.get(map.lastKey()).size();
	}

	public int valuesSize() {
		int cnt = 0;
		for (List<V> set : map.values()) {
			cnt += set.size();
		}
		return cnt;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public List<V> remove(K key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	/** Returns an empty, initialized instance. */
	public TreeMultilist<K, V> empty() {
		return new TreeMultilist<>(keyComp, valComp);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(map.toString());
		builder.append("]");
		return builder.toString();
	}
}
