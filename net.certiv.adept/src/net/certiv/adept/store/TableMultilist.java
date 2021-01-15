/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class TableMultilist<R, C, V> {

	private TreeMap<R, TreeMultilist<C, V>> map;

	private Comparator<? super C> colComp;
	private Comparator<? super V> valComp;

	public TableMultilist() {
		this(null, null);
	}

	public TableMultilist(Comparator<? super R> keyComp) {
		this(keyComp, null);
	}

	public TableMultilist(Comparator<? super R> keyComp, Comparator<? super C> colComp) {
		this.map = new TreeMap<>(keyComp);
		this.colComp = colComp;
	}

	public void setValueComp(Comparator<? super V> valComp) {
		this.valComp = valComp;
	}

	/**
	 * Returns the value to which the specified key is mapped. Returns an empty list if this map
	 * contains no mapping for the key.
	 */
	public TreeMultilist<C, V> get(R key) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap;
		return new TreeMultilist<>();
	}

	public List<V> get(R key, C name) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap != null) {
			List<V> nameMap = keyMap.get(name);
			if (nameMap != null) return nameMap;
		}
		return Collections.emptyList();
	}

	public boolean put(R key, C name, V value) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultilist<>(colComp, valComp);
			map.put(key, keyMap);
		}
		return keyMap.put(name, value);
	}

	public boolean put(R key, C name, Collection<V> values) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultilist<>(colComp, valComp);
			map.put(key, keyMap);
		}
		return keyMap.put(name, values);
	}

	public boolean put(R key, TreeMultilist<C, V> valueList) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap == null) {
			keyMap = new TreeMultilist<>(colComp, valComp);
			map.put(key, keyMap);
		}
		return keyMap.put(valueList);
	}

	/**
	 * Copies all of the mappings from the specified map to this map. These mappings replace any
	 * mappings that this map had for any of the keys currently in the specified map.
	 *
	 * @param other mappings to be stored in this map
	 */
	public void put(TableMultilist<R, C, V> other) {
		for (R key : other.keySet()) {
			map.put(key, other.get(key));
		}
	}

	public boolean containsKey(R key) {
		return map.containsKey(key);
	}

	public boolean containsKey(R key, C name) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap == null) return false;
		return keyMap.containsKey(name);
	}

	public Set<R> keySet() {
		return map.keySet();
	}

	public List<C> indexList(R key) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap.keyList();

		return Collections.emptyList();
	}

	public Set<Entry<C, List<V>>> entrySet(R key) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap.entrySet();

		return Collections.emptySet();
	}

	/** Returns all of the values V contained in this map. */
	public List<V> values() {
		List<V> values = new ArrayList<>();
		for (R key : map.keySet()) {
			TreeMultilist<C, V> indexLists = map.get(key);
			for (Entry<C, List<V>> entry : indexLists.entrySet()) {
				values.addAll(entry.getValue());
			}
		}
		return values;
	}

	public TreeMultilist<C, V> values(R key) {
		TreeMultilist<C, V> keyMap = map.get(key);
		if (keyMap != null) return keyMap;

		return new TreeMultilist<>(colComp, valComp);
	}

	public List<V> values(R key, C idx) {
		TreeMultilist<C, V> keyMap = map.get(key);
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

	public int size(R key) {
		TreeMultilist<C, V> keyMap = map.get(key);
		return keyMap != null ? keyMap.size() : 0;
	}

	public TreeMultilist<C, V> remove(R key) {
		return map.remove(key);
	}

	public void clear() {
		for (R key : keySet()) {
			map.get(key).clear();
		}
		map.clear();
	}
}
