/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

	private final LinkedHashMap<K, List<V>> keyMap;

	public HashMultilist() {
		keyMap = new LinkedHashMap<>();
	}

	public HashMultilist(Map<K, List<V>> keyMap) {
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

	public void removeKey(K key) {
		keyMap.remove(key);
	}

	public boolean containsKey(K key) {
		return keyMap.containsKey(key);
	}

	public boolean containsEntry(K key, V value) {
		List<V> values = get(key);
		if (values == null) return false;
		return values.contains(value);
	}

	public List<K> keyList() {
		return new ArrayList<>(keyMap.keySet());
	}

	public Set<K> keySet() {
		return keyMap.keySet();
	}

	/** Returns a list of the value lists held in this HashMultilist. */
	public List<List<V>> valuesList() {
		return new ArrayList<>(keyMap.values());
	}

	/** Returns a list of all of the values held in this HashMultilist. */
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (List<V> subList : keyMap.values()) {
			values.addAll(subList);
		}
		return values;
	}

	public HashMultilist<K, V> sort(Comparator<V> comp) {
		HashMultilist<K, V> sorted = new HashMultilist<>(keyMap);
		for (K key : sorted.keySet()) {
			Collections.sort(sorted.get(key), comp);
		}
		return sorted;
	}

	public boolean isEmpty() {
		return keyMap.isEmpty();
	}

	public int keySize() {
		return keyMap.size();
	}

	/** Returns the size (total number of held values) of this HashMultilist. */
	public int size() {
		int cnt = 0;
		for (List<V> values : keyMap.values()) {
			cnt += values.size();
		}
		return cnt;
	}

	public void clear() {
		for (K key : keySet()) {
			keyMap.get(key).clear();
		}
		keyMap.clear();
	}
}
