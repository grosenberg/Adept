package net.certiv.adept.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public class ArraySet<E> extends AbstractSet<E> implements SortedSet<E> {

	private final List<E> elements = new ArrayList<>();
	private Comparator<? super E> comp;

	/** Create a new ArraySet. */
	public ArraySet() {
		super();
	}

	/** Create a new ArraySet with the mappings from the given Set. */
	public ArraySet(Collection<? extends E> values) {
		super();
		elements.addAll(values);
	}

	public ArraySet(Comparator<? super E> comp) {
		super();
		this.comp = comp;
	}

	/**
	 * Adds the specified object to this set. The set is not modified if it already contains the object.
	 * Objects are added to the Set in insertion order unless the Set was created with a comparator.
	 *
	 * @param e the object to add.
	 * @return {@code true}if this set is modified, {@code false} otherwise.
	 * @throws ClassCastException when the class of the object is inappropriate for this set.
	 */
	public boolean add(E e) {
		if (elements.contains(e)) return false;
		boolean result = elements.add(e);
		if (result && comp != null) elements.sort(comp);
		return result;
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
	public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E o : c) {
			if (!elements.contains(o)) {
				result = result || elements.add(o);
			}
		}
		if (result && comp != null) elements.sort(comp);
		return result;
	}

	@Override
	public E first() {
		return get(0);
	}

	@Override
	public E last() {
		return get(elements.size() - 1);
	}

	public E get(int index) {
		return elements.get(index);
	}

	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return elements.lastIndexOf(o);
	}

	public boolean contains(Object o) {
		return elements.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return elements.retainAll(c);
	}

	@Override
	public SortedSet<E> headSet(E toElement) {
		List<E> subset = elements.subList(0, indexOf(toElement));
		return new ArraySet<>(subset);
	}

	@Override
	public SortedSet<E> tailSet(E fromElement) {
		List<E> subset = elements.subList(indexOf(fromElement), elements.size());
		return new ArraySet<>(subset);
	}

	@Override
	public SortedSet<E> subSet(E fromElement, E toElement) {
		List<E> subset = subList(indexOf(fromElement), indexOf(toElement));
		return new ArraySet<>(subset);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return elements.subList(fromIndex, toIndex);
	}

	public E remove(int index) {
		return elements.remove(index);
	}

	public boolean remove(Object o) {
		return elements.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return elements.removeAll(c);
	}

	@Override
	public Iterator<E> iterator() {
		return elements.iterator();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public int size() {
		return elements.size();
	}

	public void clear() {
		elements.clear();
	}

	@Override
	public Comparator<? super E> comparator() {
		return comp;
	}

	public Object[] toArray() {
		return elements.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}
}
