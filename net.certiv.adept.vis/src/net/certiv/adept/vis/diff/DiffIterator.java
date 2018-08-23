/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Myers Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package net.certiv.adept.vis.diff;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom iterator to iterate over a List of <code>RangeDiff</code>s. It is used internally by the
 * <code>RangeDiffer</code>.
 */
class DiffIterator {

	List<RangeDiff> fRange;
	int fIndex;
	RangeDiff[] fArray;
	RangeDiff fDifference;

	/**
	 * Creates a differences iterator on an array of <code>RangeDiff</code>s.
	 */
	DiffIterator(RangeDiff[] differenceRanges) {

		fArray = differenceRanges;
		fIndex = 0;
		fRange = new ArrayList<>();
		if (fIndex < fArray.length) fDifference = fArray[fIndex++];
		else fDifference = null;
	}

	/**
	 * Returns the number of RangeDifferences
	 */
	int getCount() {
		return fRange.size();
	}

	/**
	 * Appends the edit to its list and moves to the next <code>RangeDiff</code>.
	 */
	void next() {
		fRange.add(fDifference);
		if (fDifference != null) {
			if (fIndex < fArray.length) fDifference = fArray[fIndex++];
			else fDifference = null;
		}
	}

	/**
	 * Difference iterators are used in pairs. This method returns the other iterator.
	 */
	DiffIterator other(DiffIterator right, DiffIterator left) {
		if (this == right) return left;
		return right;
	}

	/**
	 * Removes all <code>RangeDiff</code>s
	 */
	void removeAll() {
		fRange.clear();
	}
}
