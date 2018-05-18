/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * Multimap implemented as a TreeMap with NavigableSet instance values. The NavigableSet is
 * specialized to rank duplicate added elements while remaining consistent with the Set interface.
 *
 * @param <K> key
 * @param <V> value
 */
public class TreeNavset<K, V> {

	private final TreeMap<K, Set<V>> map;
	private Comparator<? super K> keyComp;
	private Comparator<? super V> valComp;

	public TreeNavset() {
		this(null, null);
	}

	public TreeNavset(Comparator<? super K> keyComp) {
		this(keyComp, null);
	}

	public TreeNavset(Comparator<? super K> keyComp, Comparator<? super V> valComp) {
		this.map = new TreeMap<>(keyComp);
		this.keyComp = keyComp;
		this.valComp = valComp;
	}

	public void setValComp(Comparator<? super V> valComp) {
		this.valComp = valComp;
	}

	/** Get as navigable set. */
	public NavigableSet<V> get(K key) {
		return (NavigableSet<V>) map.get(key);
	}

	/** Get as list. */
	public List<V> getList(K key) {
		return new ArrayList<>(map.get(key));
	}

	public boolean put(K key, V value) {
		Set<V> set = map.get(key);
		if (set == null) {
			set = new ArraySet<>(valComp);
			map.put(key, set);
		}
		return set.add(value);
	}

	public boolean put(K key, Collection<V> values) {
		Set<V> set = map.get(key);
		if (set == null) {
			set = new RankSet<>(valComp);
			map.put(key, set);
		}
		return set.addAll(values);
	}

	public boolean put(TreeNavset<K, V> map) {
		boolean ok = true;
		for (K key : map.keySet()) {
			Set<V> val = map.get(key);
			ok &= put(key, val);
		}
		return ok;
	}

	public K firstKey() {
		return map.firstKey();
	}

	public K lastKey() {
		return map.lastKey();
	}

	/** Returns {@code true} if a value set for the given key exists. */
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/** Returns {@code true} if at least one of the value sets contains the given value. */
	public boolean containsValue(V value) {
		return map.containsValue(value);
	}

	/** Returns {@code true} if the value set for the given key contains the given value. */
	public boolean contains(K key, V value) {
		if (!map.containsKey(key)) return false;
		return map.get(key).contains(value);
	}

	public Set<Entry<K, Set<V>>> entrySet() {
		return map.entrySet();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public List<K> keyList() {
		return new ArrayList<>(map.keySet());
	}

	public K keyFirst() {
		return map.firstKey();
	}

	public K keyLast() {
		return map.lastKey();
	}

	public int keySize() {
		return map.size();
	}

	/** Returns a sorted unique list of all of the values present in this map instance. */
	@SuppressWarnings("unchecked")
	public <T extends Collection<V>> T values() {
		Collection<V> results = new ArraySet<>(valComp);
		for (Set<V> set : map.values()) {
			results.addAll(set);
		}
		return (T) results;
	}

	public int valuesSize() {
		int cnt = 0;
		for (Set<V> set : map.values()) {
			cnt += set.size();
		}
		return cnt;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<V> remove(K key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	/** Returns an empty, initialized map. */
	public TreeNavset<K, V> empty() {
		return new TreeNavset<>(keyComp, valComp);
	}

	public Map<K, Set<V>> asMap() {
		return map;
	}

	public Type asMapType() {
		return new TypeToken<Map<K, Set<V>>>() {}.getType();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(map.toString());
		builder.append("]");
		return builder.toString();
	}

	public static final class TreeMultimapAdapter<K, V>
			implements JsonSerializer<TreeNavset<K, V>>, JsonDeserializer<TreeNavset<K, V>> {

		@Override
		public JsonElement serialize(TreeNavset<K, V> src, Type typeOfSrc, JsonSerializationContext context) {
			return context.serialize(src.asMap(), src.asMapType());
		}

		@Override
		public TreeNavset<K, V> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {

			final Type key = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
			final Type val = ((ParameterizedType) typeOfT).getActualTypeArguments()[1];
			final Type type = asActualMap(key, val).asMapType();

			Map<K, Set<V>> asMap = context.deserialize(json, type);
			TreeNavset<K, V> multimap = new TreeNavset<>();
			for (Entry<K, Set<V>> entry : asMap.entrySet()) {
				multimap.put(entry.getKey(), entry.getValue());
			}
			return multimap;
		}

		private static <T, U> TreeNavset<T, U> asActualMap(T key, U value) {
			return new TreeNavset<>();
		}
	}
}
