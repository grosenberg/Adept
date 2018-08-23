/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
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
 * A <code>RangeDiffer</code> finds the differences between two or three <code>IRangeComp</code>s.
 * <p>
 * To use the differencer, clients provide an <code>IRangeComp</code> that breaks their input data
 * into a sequence of comparable entities. The differencer returns the differences among these
 * sequences as an array of <code>RangeDiff</code> objects (<code>findDifferences</code> methods).
 * Every <code>RangeDiff</code> represents a single kind of difference and the corresponding ranges
 * of the underlying comparable entities in the left, right, and optionally ancestor sides.
 * <p>
 * Alternatively, the <code>findRanges</code> methods not only return objects for the differing
 * ranges but for non-differing ranges too.
 * <p>
 * The algorithm used is an objectified version of one described in: <it>A File Comparison
 * Program,</it> by Webb Miller and Eugene W. Myers, Software Practice and Experience, Vol. 15, Nov.
 * 1985.
 *
 * @see IRangeComp
 * @see RangeDiff
 */
public final class RangeDiffer {

	private static final RangeDiff[] EMPTY_RESULT = new RangeDiff[0];

	private RangeDiffer() {}

	/**
	 * Finds the differences between two <code>IRangeComp</code>s. The differences are returned as an
	 * array of <code>RangeDiff</code>s. If no differences are detected an empty array is returned.
	 *
	 * @param left the left range comparator
	 * @param right the right range comparator
	 * @return an array of range differences, or an empty array if no differences were found
	 * @since 2.0
	 */
	public static RangeDiff[] find(IRangeComp left, IRangeComp right) {
		return RangeCompLcs.findDifferences(left, right);
	}

	/**
	 * Finds the differences among three <code>IRangeComp</code>s. The differences are returned as a
	 * list of <code>RangeDiff</code>s. If no differences are detected an empty list is returned. If the
	 * ancestor range comparator is <code>null</code>, a two-way comparison is performed.
	 *
	 * @param ancestor the ancestor range comparator or <code>null</code>
	 * @param left the left range comparator
	 * @param right the right range comparator
	 * @return an array of range differences, or an empty array if no differences were found
	 * @since 2.0
	 */
	public static RangeDiff[] findDifferences(IRangeComp ancestor, IRangeComp left, IRangeComp right) {

		if (ancestor == null) {
			return find(left, right);
		}
		RangeDiff[] leftAncestorScript = null;
		RangeDiff[] rightAncestorScript = find(ancestor, right);
		if (rightAncestorScript != null) {
			leftAncestorScript = find(ancestor, left);
		}
		if (rightAncestorScript == null || leftAncestorScript == null) {
			return null;
		}

		DiffIterator myIter = new DiffIterator(rightAncestorScript);
		DiffIterator yourIter = new DiffIterator(leftAncestorScript);

		List<RangeDiff> diff3 = new ArrayList<>();
		diff3.add(new RangeDiff(RangeDiff.ERROR)); // add a sentinel

		int changeRangeStart = 0;
		int changeRangeEnd = 0;

		//
		// Combine the two two-way edit scripts into one
		//
		while (myIter.fDifference != null || yourIter.fDifference != null) {
			DiffIterator startThread;
			myIter.removeAll();
			yourIter.removeAll();
			//
			// take the next diff that is closer to the start
			//
			if (myIter.fDifference == null) {
				startThread = yourIter;
			} else if (yourIter.fDifference == null) {
				startThread = myIter;
			} else { // not at end of both scripts take the lowest range
				if (myIter.fDifference.lStart <= yourIter.fDifference.lStart) {
					// 2 -> common (Ancestor) change range
					startThread = myIter;
				} else {
					startThread = yourIter;
				}
			}
			changeRangeStart = startThread.fDifference.lStart;
			changeRangeEnd = startThread.fDifference.leftEnd();

			startThread.next();
			//
			// check for overlapping changes with other thread
			// merge overlapping changes with this range
			//
			DiffIterator other = startThread.other(myIter, yourIter);
			while (other.fDifference != null && other.fDifference.lStart <= changeRangeEnd) {
				int newMax = other.fDifference.leftEnd();
				other.next();
				if (newMax >= changeRangeEnd) {
					changeRangeEnd = newMax;
					other = other.other(myIter, yourIter);
				}
			}
			diff3.add(createRangeDifference3(myIter, yourIter, diff3, right, left, changeRangeStart, changeRangeEnd));
		}

		// remove sentinel
		diff3.remove(0);
		return diff3.toArray(EMPTY_RESULT);
	}

	/**
	 * Finds the differences among two <code>IRangeComp</code>s. In contrast to
	 * <code>findDifferences</code>, the result contains <code>RangeDiff</code> elements for
	 * non-differing ranges too.
	 *
	 * @param left the left range comparator
	 * @param right the right range comparator
	 * @return an array of range differences
	 * @since 2.0
	 */
	public static RangeDiff[] findRanges(IRangeComp left, IRangeComp right) {
		RangeDiff[] in = find(left, right);
		List<RangeDiff> out = new ArrayList<>();

		RangeDiff rd;

		int mstart = 0;
		int ystart = 0;

		for (RangeDiff es : in) {
			rd = new RangeDiff(RangeDiff.NOCHANGE, mstart, es.rightStart() - mstart, ystart, es.leftStart() - ystart);
			if (rd.maxLength() != 0) {
				out.add(rd);
			}

			out.add(es);

			mstart = es.rightEnd();
			ystart = es.leftEnd();
		}
		rd = new RangeDiff(RangeDiff.NOCHANGE, mstart, right.getRangeCount() - mstart, ystart,
				left.getRangeCount() - ystart);
		if (rd.maxLength() > 0) {
			out.add(rd);
		}

		return out.toArray(EMPTY_RESULT);
	}

	/**
	 * Finds the differences among three <code>IRangeComp</code>s. In contrast to
	 * <code>findDifferences</code>, the result contains <code>RangeDiff</code> elements for
	 * non-differing ranges too. If the ancestor range comparator is <code>null</code>, a two-way
	 * comparison is performed.
	 *
	 * @param ancestor the ancestor range comparator or <code>null</code>
	 * @param left the left range comparator
	 * @param right the right range comparator
	 * @return an array of range differences
	 * @since 2.0
	 */
	public static RangeDiff[] findRanges(IRangeComp ancestor, IRangeComp left, IRangeComp right) {
		if (ancestor == null) {
			return findRanges(left, right);
		}

		RangeDiff[] in = findDifferences(ancestor, left, right);
		List<RangeDiff> out = new ArrayList<>();

		RangeDiff rd;

		int mstart = 0;
		int ystart = 0;
		int astart = 0;

		for (RangeDiff es : in) {
			rd = new RangeDiff(RangeDiff.NOCHANGE, mstart, es.rightStart() - mstart, ystart, es.leftStart() - ystart,
					astart, es.ancestorStart() - astart);
			if (rd.maxLength() > 0) {
				out.add(rd);
			}

			out.add(es);

			mstart = es.rightEnd();
			ystart = es.leftEnd();
			astart = es.ancestorEnd();
		}
		rd = new RangeDiff(RangeDiff.NOCHANGE, mstart, right.getRangeCount() - mstart, ystart,
				left.getRangeCount() - ystart, astart, ancestor.getRangeCount() - astart);
		if (rd.maxLength() > 0) {
			out.add(rd);
		}

		return out.toArray(EMPTY_RESULT);
	}

	// ---- private methods

	/*
	 * Creates a <code>RangeDifference3</code> given the state of two DifferenceIterators.
	 */
	private static RangeDiff createRangeDifference3(DiffIterator myIter, DiffIterator yourIter,
			List<RangeDiff> diff3, IRangeComp right, IRangeComp left, int changeRangeStart, int changeRangeEnd) {
		int rightStart;
		int rightEnd;
		int leftStart;
		int leftEnd;
		int kind = RangeDiff.ERROR;
		RangeDiff last = diff3.get(diff3.size() - 1);

		if (myIter.getCount() == 0) { // only left changed
			rightStart = changeRangeStart - last.ancestorEnd() + last.rightEnd();
			rightEnd = changeRangeEnd - last.ancestorEnd() + last.rightEnd();
			kind = RangeDiff.LEFT;
		} else {
			RangeDiff f = myIter.fRange.get(0);
			RangeDiff l = myIter.fRange.get(myIter.fRange.size() - 1);
			rightStart = changeRangeStart - f.lStart + f.rStart;
			rightEnd = changeRangeEnd - l.leftEnd() + l.rightEnd();
		}

		if (yourIter.getCount() == 0) { // only right changed
			leftStart = changeRangeStart - last.ancestorEnd() + last.leftEnd();
			leftEnd = changeRangeEnd - last.ancestorEnd() + last.leftEnd();
			kind = RangeDiff.RIGHT;
		} else {
			RangeDiff f = yourIter.fRange.get(0);
			RangeDiff l = yourIter.fRange.get(yourIter.fRange.size() - 1);
			leftStart = changeRangeStart - f.lStart + f.rStart;
			leftEnd = changeRangeEnd - l.leftEnd() + l.rightEnd();
		}

		if (kind == RangeDiff.ERROR) { // overlapping change (conflict) -> compare the changed ranges
			if (rangeSpansEqual(right, rightStart, rightEnd - rightStart, left, leftStart, leftEnd - leftStart)) {
				kind = RangeDiff.ANCESTOR;
			} else {
				kind = RangeDiff.CONFLICT;
			}
		}
		return new RangeDiff(kind, rightStart, rightEnd - rightStart, leftStart, leftEnd - leftStart, changeRangeStart,
				changeRangeEnd - changeRangeStart);
	}

	private static boolean rangeSpansEqual(IRangeComp right, int rightStart, int rightLen, IRangeComp left,
			int leftStart, int leftLen) {
		if (rightLen == leftLen) {
			int i = 0;
			for (i = 0; i < rightLen; i++) {
				if (!rangesEqual(right, rightStart + i, left, leftStart + i)) {
					break;
				}
			}
			if (i == rightLen) {
				return true;
			}
		}
		return false;
	}

	private static boolean rangesEqual(IRangeComp a, int ai, IRangeComp b, int bi) {
		return a.rangesEqual(ai, b, bi);
	}
}
