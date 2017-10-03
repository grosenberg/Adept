package net.certiv.adept.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class TreeTreeMultimap<K, N, V> {

	private TreeMap<K, TreeMultimap<N, V>> map;
	private Comparator<? super N> compName;

	public TreeTreeMultimap() {
		this(null, null);
	}

	public TreeTreeMultimap(Comparator<? super K> compKey) {
		this(compKey, null);
	}

	public TreeTreeMultimap(Comparator<? super K> compKey, Comparator<? super N> compName) {
		this.map = new TreeMap<>(compKey);
		this.compName = compName;
	}

	public Set<V> get(K key, N name) {
		TreeMultimap<N, V> keyMap = map.get(key);
		if (keyMap != null) {
			Set<V> nameMap = keyMap.get(name);
			if (nameMap != null) return nameMap;
		}
		return Collections.emptySet();
	}

	public boolean put(K key, N name, V value) {
		TreeMultimap<N, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultimap<>(compName);
			map.put(key, keyMap);
		}
		return keyMap.put(name, value);
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public boolean containsKey(K key, N name) {
		TreeMultimap<N, V> keyMap = map.get(key);
		if (keyMap == null) return false;
		return keyMap.containsKey(name);
	}

	public TreeMultimap<N, V> remove(K key) {
		return map.remove(key);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Set<N> keySet(K key) {
		TreeMultimap<N, V> keyMap = map.get(key);
		if (keyMap != null) {
			return keyMap.keySet();
		}
		return Collections.emptySet();
	}

	public Set<V> values(K key) {
		TreeMultimap<N, V> keyMap = map.get(key);
		if (keyMap != null) {
			return keyMap.values();
		}
		return Collections.emptySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean isEmpty(K key) {
		TreeMultimap<N, V> keyMap = map.get(key);
		return keyMap != null ? keyMap.isEmpty() : true;
	}

	public int size() {
		return map.size();
	}

	public int size(K key) {
		TreeMultimap<N, V> keyMap = map.get(key);
		return keyMap != null ? keyMap.keySize() : 0;
	}

	public void clear() {
		for (K key : keySet()) {
			map.get(key).clear();
		}
		map.clear();
	}
}
