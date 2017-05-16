package net.certiv.adept.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashList<K, V> {

	private Map<K, ArrayList<V>> keyMap;
	private Comparator<V> comp;

	public HashList() {
		keyMap = new HashMap<K, ArrayList<V>>();
	}

	public HashList(Comparator<V> comp) {
		this();
		this.comp = comp;
	}

	public HashList(Map<K, ArrayList<V>> keyMap) {
		this.keyMap = keyMap;
	}

	public List<V> get(K key) {
		return keyMap.get(key);
	}

	public boolean put(K key, V value) {
		ArrayList<V> values = (ArrayList<V>) get(key);
		if (values == null) {
			values = new ArrayList<>();
			keyMap.put(key, values);
		}
		boolean ok = values.add(value);
		Collections.sort(values, comp);
		return ok;
	}

	public void sort(Comparator<V> comp) {
		for (List<V> list : keyMap.values()) {
			Collections.sort(list, comp);
		}
	}

	public boolean containsKey(K key) {
		return keyMap.containsKey(key);
	}

	public Set<K> keySet() {
		return keyMap.keySet();
	}

	/** Returns a list of all of the values held in this HashList. */
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

	/** Returns the size (total number of held values) of this HashList. */
	public int size() {
		return keyMap.size();
	}

	// public Map<K, List<V>> asMap() {
	// return keyMap;
	// }
}
