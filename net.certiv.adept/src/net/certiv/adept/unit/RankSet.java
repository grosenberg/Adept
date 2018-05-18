/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class RankSet<E> extends TreeSet<E> {

	public RankSet() {
		super();
	}

	public RankSet(Collection<? extends E> c) {
		super(c);
	}

	public RankSet(Comparator<? super E> comparator) {
		super(comparator);
	}

	public RankSet(SortedSet<E> s) {
		super(s);
	}

	/**
	 * Adds the specified object to this set. The set is not modified if it already contains the object.
	 * Objects are added to the Set in insertion order unless the Set was created with a comparator.
	 *
	 * @param e the object to add.
	 * @return {@code true}if this set is modified, {@code false} otherwise.
	 * @throws ClassCastException when the class of the object is inappropriate for this set.
	 */
	@Override
	public boolean add(E e) {
		if (!contains(e)) return super.add(e);

		E existing = get(e);
		if (existing instanceof Ranked) {
			((Ranked) existing).incRank();
		}
		return false;
	}

	/**
	 * Adds the specified Collection to this Set. The set is not modified if it already contains the
	 * object. Objects are added to the Set in insertion order unless the Set was created with a
	 * comparator.
	 *
	 * @param c the Collection to add.
	 * @return {@code true}if this set is modified, {@code false} otherwise.
	 * @throws ClassCastException when the class of the object is inappropriate for this set.
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E o : c) {
			result = add(o) || result;
		}
		return result;
	}

	public E get(E e) {
		return subSet(e, true, e, true).first();
	}
}
