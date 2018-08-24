/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Multimap implemented as a LinkedHashMap with List instance values.
 *
 * @param <K>
 * @param <V>
 */
public class HashMultilist<K, V> {

	private final LinkedHashMap<K, List<V>> map;

	public HashMultilist() {
		map = new LinkedHashMap<>();
	}

	public HashMultilist(Map<K, List<V>> keyMap) {
		this();
		this.map.putAll(keyMap);
	}

	public List<V> get(K key) {
		return map.get(key);
	}

	public boolean put(K key, V value) {
		List<V> values = get(key);
		if (values == null) {
			values = new ArrayList<>();
			map.put(key, values);
		}
		return values.add(value);
	}

	public boolean put(K key, Collection<V> values) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<>();
			map.put(key, list);
		}
		return list.addAll(values);
	}

	public void removeKey(K key) {
		map.remove(key);
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public boolean containsEntry(K key, V value) {
		List<V> values = get(key);
		if (values == null) return false;
		return values.contains(value);
	}

	public List<K> keyList() {
		return new ArrayList<>(map.keySet());
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	/** Returns a list of the value lists held in this HashMultilist. */
	public List<List<V>> valuesList() {
		return new ArrayList<>(map.values());
	}

	/** Returns a list of all of the values held in this HashMultilist. */
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (List<V> subList : map.values()) {
			values.addAll(subList);
		}
		return values;
	}

	public void sort(Comparator<V> comp) {
		for (K key : map.keySet()) {
			map.get(key).sort(comp);
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int keySize() {
		return map.size();
	}

	/** Returns the size (total number of held values) of this HashMultilist. */
	public int size() {
		int cnt = 0;
		for (List<V> values : map.values()) {
			cnt += values.size();
		}
		return cnt;
	}

	public void clear() {
		for (K key : keySet()) {
			map.get(key).clear();
		}
		map.clear();
	}
}
