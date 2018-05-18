/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.certiv.adept.unit.ArrayMultilist.Entry;

/**
 * Multilist implemented as an ArrayList with List instance data
 *
 * @param <K> ident type
 * @param <V> value type
 */
public class ArrayMultilist<K, V> implements Iterable<Entry<K, V>> {

	private final List<Entry<K, V>> keyMap;

	public ArrayMultilist() {
		keyMap = new ArrayList<>();
	}

	public boolean add(K key, V value) {
		for (Entry<K, V> entry : keyMap) {
			if (entry.ident.equals(key)) {
				return entry.data.add(value);
			}
		}
		ArrayList<V> list = new ArrayList<>();
		list.add(value);
		return add(key, list);
	}

	public boolean add(K key, List<V> value) {
		Entry<K, V> entry = new Entry<>(key, value);
		return keyMap.add(entry);
	}

	public void add(int idx, K key, List<V> value) {
		rangeCheckForAdd(idx);
		Entry<K, V> entry = new Entry<>(key, value);
		keyMap.add(idx, entry);
	}

	public List<V> get(int idx) {
		rangeCheckForAdd(idx);
		Entry<K, V> entry = keyMap.get(idx);
		if (entry == null) return Collections.emptyList();
		return entry.data;
	}

	public List<List<V>> get(K key) {
		List<List<V>> results = new ArrayList<>();
		for (Entry<K, V> entry : keyMap) {
			if (entry.ident.equals(key)) {
				results.add(entry.data);
			}
		}
		return results;
	}

	public boolean containsKey(K key) {
		for (Entry<K, V> entry : keyMap) {
			if (entry.ident == key) return true;
		}
		return false;
	}

	public List<K> keys() {
		List<K> keys = new ArrayList<>();
		for (Entry<K, V> entry : keyMap) {
			keys.add(entry.ident);
		}
		return keys;
	}

	/** Returns a list of all of the data held. */
	public List<List<V>> values() {
		List<List<V>> values = new ArrayList<>();
		for (Entry<K, V> entry : keyMap) {
			values.add(entry.data);
		}
		return values;
	}

	public boolean isEmpty() {
		return keyMap.isEmpty();
	}

	public int keySize() {
		return keyMap.size();
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return keyMap.iterator();
	}

	/* Used by add and addAll. */
	private void rangeCheckForAdd(int idx) {
		if (idx > keyMap.size() || idx < 0) throw new IndexOutOfBoundsException(outOfBoundsMsg(idx));
	}

	/* Constructs an IndexOutOfBoundsException detail message. */
	private String outOfBoundsMsg(int idx) {
		return "Index: " + idx + ", Size: " + keyMap.size();
	}

	public static class Entry<I, D> {

		private I ident;
		private List<D> data;

		public Entry(I ident, List<D> data) {
			super();
			this.ident = ident;
			this.data = data;
		}

		public I key() {
			return ident;
		}

		public List<D> values() {
			return data;
		}

		int size() {
			return data.size();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((ident == null) ? 0 : ident.hashCode());
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			@SuppressWarnings("unchecked")
			Entry<I, D> other = (Entry<I, D>) obj;
			if (ident == null) {
				if (other.ident != null) return false;
			} else if (!ident.equals(other.ident)) {
				return false;
			}
			if (data == null) {
				if (other.data != null) return false;
			} else if (!data.equals(other.data)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Entry [ident=");
			builder.append(ident);
			builder.append(", data=");
			builder.append(data);
			builder.append("]");
			return builder.toString();
		}
	}
}
