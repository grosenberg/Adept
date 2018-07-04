package net.certiv.adept.unit;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Multimap implemented as a TreeMap with Hashmap instance values
 *
 * @param <K> key
 * @param <T> type
 * @param <V> value
 */
public class TreeMultimap<K, T, V> {

	private final TreeMap<K, Map<T, V>> map;

	public TreeMultimap() {
		this(null);
	}

	public TreeMultimap(Comparator<? super K> keyComp) {
		this.map = new TreeMap<>(keyComp);
	}

	public V put(K key, T type, V value) {
		Map<T, V> data = map.get(key);
		if (data == null) {
			data = new LinkedHashMap<>();
			map.put(key, data);
		}
		return data.put(type, value);
	}

	public Map<T, V> get(K key) {
		return map.get(key);
	}

	public V get(K key, T type) {
		Map<T, V> data = map.get(key);
		return data != null ? data.get(type) : null;
	}

	public K firstKey() {
		return map.firstKey();
	}

	public K lastKey() {
		return map.lastKey();
	}

	/** Returns {@code true} if a value set for the given key exists. */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/** Returns {@code true} if the value set for the given key contains the given type. */
	public boolean containsKey(K key, T type) {
		Map<T, V> data = map.get(key);
		return data != null ? data.containsKey(type) : false;
	}

	/** Returns {@code true} if at least one of the value sets contains the given value. */
	public boolean containsValue(K key, V value) {
		Map<T, V> data = map.get(key);
		return data.containsValue(value);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void clear() {
		map.clear();
	}
}
