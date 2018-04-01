package net.certiv.adept.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class TableMultilist<K, N, V> {

	private TreeMap<K, TreeMultilist<N, V>> map;

	private Comparator<? super N> idxComp;
	private Comparator<? super V> valComp;

	public TableMultilist() {
		this(null, null);
	}

	public TableMultilist(Comparator<? super K> keyComp) {
		this(keyComp, null);
	}

	public TableMultilist(Comparator<? super K> compKey, Comparator<? super N> idxComp) {
		this.map = new TreeMap<>(compKey);
		this.idxComp = idxComp;
	}

	public void setValueComp(Comparator<? super V> valComp) {
		this.valComp = valComp;
	}

	public TreeMultilist<N, V> get(K key) {
		return map.get(key);
	}

	public List<V> get(K key, N name) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap != null) {
			List<V> nameMap = keyMap.get(name);
			if (nameMap != null) return nameMap;
		}
		return Collections.emptyList();
	}

	public boolean put(K key, N name, V value) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultilist<>(idxComp, valComp);
			map.put(key, keyMap);
		}
		return keyMap.put(name, value);
	}

	public boolean put(K key, N name, Collection<V> values) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultilist<>(idxComp, valComp);
			map.put(key, keyMap);
		}
		return keyMap.put(name, values);
	}

	/**
	 * Copies all of the mappings from the specified map to this map. These mappings replace any
	 * mappings that this map had for any of the keys currently in the specified map.
	 *
	 * @param other mappings to be stored in this map
	 */
	public void put(TableMultilist<K, N, V> other) {
		for (K key : other.keySet()) {
			this.map.put(key, other.get(key));
		}
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public boolean containsKey(K key, N name) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap == null) return false;
		return keyMap.containsKey(name);
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public List<N> indexList(K key) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap.keyList();

		return Collections.emptyList();
	}

	public Set<Entry<N, List<V>>> entrySet(K key) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap.entrySet();

		return Collections.emptySet();
	}

	/** Returns all of the values V contained in this map. */
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (K key : map.keySet()) {
			TreeMultilist<N, V> indexLists = map.get(key);
			for (Entry<N, List<V>> entry : indexLists.entrySet()) {
				values.addAll(entry.getValue());
			}
		}
		return values;
	}

	public TreeMultilist<N, V> values(K key) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap;

		return new TreeMultilist<>(idxComp, valComp);
	}

	public List<V> values(K key, N idx) {
		TreeMultilist<N, V> keyMap = map.get(key);
		if (keyMap != null) {
			List<V> values = keyMap.get(idx);
			if (values != null) return values;
		}
		return Collections.emptyList();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int size() {
		return map.size();
	}

	public int size(K key) {
		TreeMultilist<N, V> keyMap = map.get(key);
		return keyMap != null ? keyMap.keySize() : 0;
	}

	public TreeMultilist<N, V> remove(K key) {
		return map.remove(key);
	}

	public void clear() {
		for (K key : keySet()) {
			map.get(key).clear();
		}
		map.clear();
	}
}
