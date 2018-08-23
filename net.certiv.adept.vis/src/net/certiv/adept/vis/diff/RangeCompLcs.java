/*******************************************************************************
 * Copyright (c) 2006, 2007 IBM Corporation and others.
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

public class RangeCompLcs extends MyersLcs {

	private final IRangeComp comp1;
	private final IRangeComp comp2;
	private int[][] lcs;

	public static RangeDiff[] findDifferences(IRangeComp left, IRangeComp right) {
		RangeCompLcs lcs = new RangeCompLcs(left, right);
		lcs.longestCommonSubsequence();
		return lcs.getDifferences();
	}

	public RangeCompLcs(IRangeComp comp1, IRangeComp comp2) {
		this.comp1 = comp1;
		this.comp2 = comp2;
	}

	@Override
	protected int getLength1() {
		return comp1.getRangeCount();
	}

	@Override
	protected int getLength2() {
		return comp2.getRangeCount();
	}

	@Override
	protected void initializeLcs(int lcsLength) {
		lcs = new int[2][lcsLength];
	}

	@Override
	protected boolean isRangeEqual(int i1, int i2) {
		return comp1.rangesEqual(i1, comp2, i2);
	}

	@Override
	protected void setLcs(int sl1, int sl2) {
		// Add one to the values so that 0 can mean that the slot is empty
		lcs[0][sl1] = sl1 + 1;
		lcs[1][sl1] = sl2 + 1;
	}

	public RangeDiff[] getDifferences() {
		List<RangeDiff> differences = new ArrayList<>();
		int length = getLength();
		if (length == 0) {
			differences.add(new RangeDiff(RangeDiff.CHANGE, 0, comp2.getRangeCount(), 0, comp1.getRangeCount()));
		} else {
			int index1;
			int index2;
			index1 = index2 = 0;
			int l1;
			int l2;
			int s1 = -1;
			int s2 = -1;
			while (index1 < lcs[0].length && index2 < lcs[1].length) {
				// Move both MyersLcs lists to the next occupied slot
				while ((l1 = lcs[0][index1]) == 0) {
					index1++;
					if (index1 >= lcs[0].length) {
						break;
					}
				}
				if (index1 >= lcs[0].length) {
					break;
				}
				while ((l2 = lcs[1][index2]) == 0) {
					index2++;
					if (index2 >= lcs[1].length) {
						break;
					}
				}
				if (index2 >= lcs[1].length) {
					break;
				}

				// Convert the entry to an array index (see setLcs(int, int))
				int end1 = l1 - 1;
				int end2 = l2 - 1;
				if (s1 == -1 && (end1 != 0 || end2 != 0)) {
					// There is a diff at the beginning
					// TODO: We need to conform that this is the proper order
					differences.add(new RangeDiff(RangeDiff.CHANGE, 0, end2, 0, end1));
				} else if (end1 != s1 + 1 || end2 != s2 + 1) {
					// A diff was found on one of the sides
					int leftStart = s1 + 1;
					int leftLength = end1 - leftStart;
					int rightStart = s2 + 1;
					int rightLength = end2 - rightStart;
					// TODO: We need to confirm that this is the proper order
					differences.add(new RangeDiff(RangeDiff.CHANGE, rightStart, rightLength, leftStart, leftLength));
				}
				s1 = end1;
				s2 = end2;
				index1++;
				index2++;
			}
			if (s1 != -1 && (s1 + 1 < comp1.getRangeCount() || s2 + 1 < comp2.getRangeCount())) {
				// TODO: we need to find the proper way of representing an append
				int leftStart = s1 < comp1.getRangeCount() ? s1 + 1 : s1;
				int rightStart = s2 < comp2.getRangeCount() ? s2 + 1 : s2;
				// TODO: We need to conform that this is the proper order
				differences.add(new RangeDiff(RangeDiff.CHANGE, rightStart, comp2.getRangeCount() - (s2 + 1), leftStart,
						comp1.getRangeCount() - (s1 + 1)));
			}
		}
		return differences.toArray(new RangeDiff[differences.size()]);
	}

	/**
	 * This method takes an MyersLcs result interspersed with zeros (i.e. empty slots from the MyersLcs
	 * algorithm), compacts it and shifts the MyersLcs chunks as far towards the front as possible. This
	 * tends to produce good results most of the time.
	 *
	 * @param lcsSide A subsequence of original, presumably it is the MyersLcs of it and some other
	 *            collection of lines
	 * @param length The number of non-empty (i.e non-zero) entries in MyersLcs
	 * @param comp The comp used to generate the MyersLcs
	 */
	private void compactAndShiftLCS(int[] lcsSide, int length, IRangeComp comp) {
		// If the MyersLcs is empty, just return
		if (length == 0) {
			return;
		}

		// Skip any leading empty slots
		int j = 0;
		while (lcsSide[j] == 0) {
			j++;
		}
		// Put the first non-empty value in position 0
		lcsSide[0] = lcsSide[j];
		j++;
		// Push all non-empty values down into the first N slots (where N is the length)
		for (int i = 1; i < length; i++) {
			while (lcsSide[j] == 0) {
				j++;
			}

			// Push the difference down as far as possible by comparing the line at the
			// start of the diff with the line and the end and adjusting if they are the same
			int nextLine = lcsSide[i - 1] + 1;
			if (nextLine != lcsSide[j] && comp.rangesEqual(nextLine - 1, comp, lcsSide[j] - 1)) {
				lcsSide[i] = nextLine;
			} else {
				lcsSide[i] = lcsSide[j];
			}
			j++;
		}

		// Zero all slots after the length
		for (int i = length; i < lcsSide.length; i++) {
			lcsSide[i] = 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.compare.internal.LCS#longestCommonSubsequence(org.eclipse.core.runtime.SubMonitor)
	 */
	@Override
	public void longestCommonSubsequence() {
		super.longestCommonSubsequence();
		if (lcs != null) { // The MyersLcs can be null if one of the sides is empty
			compactAndShiftLCS(lcs[0], getLength(), comp1);
			compactAndShiftLCS(lcs[1], getLength(), comp2);
		}
	}
}
