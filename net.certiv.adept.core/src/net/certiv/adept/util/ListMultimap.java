package net.certiv.adept.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multimap implemented as a LinkedHashMap with List instance values
 *
 * @param <K>
 * @param <V>
 */
public class ListMultimap<K, V> {

	private final Map<K, List<V>> keyMap;

	public ListMultimap() {
		keyMap = new LinkedHashMap<K, List<V>>();
	}

	public ListMultimap(Map<K, List<V>> keyMap) {
		this();
		this.keyMap.putAll(keyMap);
	}

	public List<V> get(K key) {
		return keyMap.get(key);
	}

	public boolean put(K key, V value) {
		List<V> values = get(key);
		if (values == null) {
			values = new ArrayList<>();
			keyMap.put(key, values);
		}
		return values.add(value);
	}

	public boolean put(K key, Collection<V> values) {
		List<V> list = keyMap.get(key);
		if (list == null) {
			list = new ArrayList<>();
			keyMap.put(key, list);
		}
		return list.addAll(values);
	}

	public ListMultimap<K, V> sort(Comparator<V> comp) {
		ListMultimap<K, V> sorted = new ListMultimap<>(keyMap);
		for (K key : sorted.keySet()) {
			Collections.sort(sorted.get(key), comp);
		}
		return sorted;
	}

	public boolean containsKey(K key) {
		return keyMap.containsKey(key);
	}

	public Set<K> keySet() {
		return keyMap.keySet();
	}

	/** Returns a list of all of the values held in this ListMultimap. */
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (List<V> set : keyMap.values()) {
			values.addAll(set);
		}
		return values;
	}

	public boolean isEmpty() {
		return keyMap.isEmpty();
	}

	public int keySize() {
		return keyMap.size();
	}

	/** Returns the size (total number of held values) of this ListMultimap. */
	public int size() {
		int cnt = 0;
		for (List<V> values : keyMap.values()) {
			cnt += values.size();
		}
		return cnt;
	}
}
