package net.certiv.adept.util;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeMultimap<K, V> {

	private final TreeMap<K, Set<V>> map;
	private final Comparator<? super V> valComp;

	public TreeMultimap() {
		this(null, null);
	}

	public TreeMultimap(Comparator<? super K> keyComp) {
		this(keyComp, null);
	}

	public TreeMultimap(Comparator<? super K> keyComp, Comparator<? super V> valComp) {
		this.map = new TreeMap<>(keyComp);
		this.valComp = valComp;
	}

	public Set<V> get(K key) {
		return map.get(key);
	}

	public boolean put(K key, V value) {
		TreeSet<V> set = (TreeSet<V>) map.get(key);
		if (set == null) {
			set = new TreeSet<>(valComp);
			map.put(key, set);
		}
		return set.add(value);
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

	public Set<K> keySet() {
		return map.keySet();
	}

	public Set<V> values() {
		Set<V> results = new LinkedHashSet<>();
		for (Set<V> set : map.values()) {
			results.addAll(set);
		}
		return results;
	}

	public Set<V> remove(K key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int size() {
		return map.size();
	}
}
