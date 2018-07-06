/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** For keeping a count of 'things'. */
public class Counter<K> {

	private final Map<K, Integer> cntr = new HashMap<>();

	public int get(K kind) {
		return cntr.get(kind);
	}

	public void add(K kind) {
		Integer val = cntr.get(kind);
		if (val != null) {
			cntr.put(kind, val + 1);
		} else {
			cntr.put(kind, 1);
		}
	}

	public void addAll(Collection<K> kinds) {
		for (K kind : kinds) {
			add(kind);
		}
	}

	public K maxInstance() {
		K inst = null;
		int max = -1;
		for (Entry<K, Integer> entry : cntr.entrySet()) {
			if (entry.getValue() > max) {
				inst = entry.getKey();
				max = entry.getValue();
			}
		}
		return inst;
	}

	public int total(K kind) {
		Integer val = cntr.get(kind);
		return val != null ? val : 0;
	}

	public int total() {
		int tot = 0;
		for (Integer val : cntr.values()) {
			tot += val != null ? val : 0;
		}
		return tot;
	}

	public void clear() {
		cntr.clear();
	}
}
