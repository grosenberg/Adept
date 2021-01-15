/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * ArrayList with ArrayList instance data
 *
 * @param <V> value type
 */
public class ArrayLists<V> implements Iterable<List<V>> {

	private final List<List<V>> lists;

	public ArrayLists() {
		lists = new ArrayList<>();
	}

	public boolean add(V value) {
		return add(lists.size(), value);
	}

	public boolean add(int idx, V value) {
		rangeCheckForAdd(idx);
		List<V> list = lists.get(idx);
		if (list == null) {
			list = new ArrayList<>();
			lists.set(idx, list);
		}
		return list.add(value);
	}

	public boolean add(List<V> values) {
		return add(lists.size(), values);
	}

	public boolean add(int idx, List<V> values) {
		rangeCheckForAdd(idx);
		List<V> list = lists.get(idx);
		if (list == null) {
			list = new ArrayList<>();
			lists.set(idx, list);
		}
		return list.addAll(list);
	}

	public List<V> get(int idx) {
		List<V> list = lists.get(idx);
		if (list == null) return Collections.emptyList();
		return list;
	}

	public boolean isEmpty() {
		return lists.isEmpty();
	}

	public int keySize() {
		return lists.size();
	}

	@Override
	public Iterator<List<V>> iterator() {
		return lists.iterator();
	}

	/* Used by add and addAll. */
	private void rangeCheckForAdd(int idx) {
		if (idx > lists.size() || idx < 0) {
			throw new IndexOutOfBoundsException(String.format("Index: %s, Size: %s", idx, lists.size()));
		}
	}
}
