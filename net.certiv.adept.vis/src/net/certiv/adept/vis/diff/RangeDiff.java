/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Myers Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package net.certiv.adept.vis.diff;

/**
 * Description of a change between two or three ranges of comparable entities.
 * <p>
 * <code>RangeDiff</code> objects are the elements of a compare result returned from the
 * <code>RangeDiffer</code> <code>find* </code> methods. Clients use these objects as they are
 * returned from the differencer. This class is not intended to be instantiated or subclassed
 * outside of the Compare framework.
 * <p>
 * Note: A range in the <code>RangeDiff</code> object is given as a start index and length in terms
 * of comparable entities. However, these entity indices and counts are not necessarily character
 * positions. For example, if an entity represents a line in a document, the start index would be a
 * line number and the count would be in lines.
 * </p>
 *
 * @see RangeDiffer
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public class RangeDiff {

	/** Two-way change constant indicating no change. */
	public final static int NOCHANGE = 0;
	/** Two-way change constant indicating two-way change (same as <code>RIGHT</code>) */
	public final static int CHANGE = 2;

	/** Three-way change constant indicating a change in both right and left. */
	public final static int CONFLICT = 1;
	/** Three-way change constant indicating a change in right. */
	public final static int RIGHT = 2;
	/** Three-way change constant indicating a change in left. */
	public final static int LEFT = 3;
	/**
	 * Three-way change constant indicating the same change in both right and left, that is only the
	 * ancestor is different.
	 */
	public final static int ANCESTOR = 4;

	/** Constant indicating an unknown change kind. */
	public final static int ERROR = 5;

	/** the kind of change: NOCHANGE, MOD, LEFT, RIGHT, ANCESTOR, CONFLICT, ERROR */
	int kind;

	int lStart;
	int lLen;
	int rStart;
	int rLen;
	int lAncestorStart;
	int lAncestorLen;

	/**
	 * Creates a new range difference with the given change kind.
	 *
	 * @param changeKind the kind of change
	 */
	RangeDiff(int kind) {
		this.kind = kind;
	}

	/**
	 * Creates a new <code>RangeDiff</code> with the given change kind and left and right ranges.
	 *
	 * @param kind the kind of change
	 * @param rStart start index of entity on right side
	 * @param rightLength number of entities on right side
	 * @param lStart start index of entity on left side
	 * @param leftLength number of entities on left side
	 */
	RangeDiff(int kind, int rStart, int rightLength, int lStart, int leftLength) {
		this.kind = kind;
		this.rStart = rStart;
		this.rLen = rightLength;
		this.lStart = lStart;
		this.lLen = leftLength;
	}

	/**
	 * Creates a new <code>RangeDiff</code> with the given change kind and left, right, and ancestor
	 * ranges.
	 *
	 * @param kind the kind of change
	 * @param rStart start index of entity on right side
	 * @param rightLength number of entities on right side
	 * @param lStart start index of entity on left side
	 * @param leftLength number of entities on left side
	 * @param ancestorStart start index of entity on ancestor side
	 * @param ancestorLength number of entities on ancestor side
	 */
	RangeDiff(int kind, int rStart, int rightLength, int lStart, int leftLength, int ancestorStart,
			int ancestorLength) {
		this(kind, rStart, rightLength, lStart, leftLength);
		this.lAncestorStart = ancestorStart;
		this.lAncestorLen = ancestorLength;
	}

	/**
	 * Returns the kind of difference.
	 *
	 * @return the kind of difference, one of <code>NOCHANGE</code>, <code>MOD</code>,
	 *         <code>LEFT</code>, <code>RIGHT</code>, <code>ANCESTOR</code>, <code>CONFLICT</code>,
	 *         <code>ERROR</code>
	 */
	public int kind() {
		return kind;
	}

	/**
	 * Returns the start index of the entity range on the ancestor side.
	 *
	 * @return the start index of the entity range on the ancestor side
	 */
	public int ancestorStart() {
		return lAncestorStart;
	}

	/**
	 * Returns the number of entities on the ancestor side.
	 *
	 * @return the number of entities on the ancestor side
	 */
	public int ancestorLength() {
		return lAncestorLen;
	}

	/**
	 * Returns the end index of the entity range on the ancestor side.
	 *
	 * @return the end index of the entity range on the ancestor side
	 */
	public int ancestorEnd() {
		return lAncestorStart + lAncestorLen;
	}

	/**
	 * Returns the start index of the entity range on the right side.
	 *
	 * @return the start index of the entity range on the right side
	 */
	public int rightStart() {
		return rStart;
	}

	/**
	 * Returns the number of entities on the right side.
	 *
	 * @return the number of entities on the right side
	 */
	public int rightLength() {
		return rLen;
	}

	/**
	 * Returns the end index of the entity range on the right side.
	 *
	 * @return the end index of the entity range on the right side
	 */
	public int rightEnd() {
		return rStart + rLen;
	}

	/**
	 * Returns the start index of the entity range on the left side.
	 *
	 * @return the start index of the entity range on the left side
	 */
	public int leftStart() {
		return lStart;
	}

	/**
	 * Returns the number of entities on the left side.
	 *
	 * @return the number of entities on the left side
	 */
	public int leftLength() {
		return lLen;
	}

	/**
	 * Returns the end index of the entity range on the left side.
	 *
	 * @return the end index of the entity range on the left side
	 */
	public int leftEnd() {
		return lStart + lLen;
	}

	/**
	 * Returns the maximum number of entities in the left, right, and ancestor sides of this range.
	 *
	 * @return the maximum number of entities in the left, right, and ancestor sides of this range
	 */
	public int maxLength() {
		return Math.max(rLen, Math.max(lLen, lAncestorLen));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RangeDiff) {
			RangeDiff other = (RangeDiff) obj;
			return kind == other.kind && lStart == other.lStart && lLen == other.lLen && rStart == other.rStart
					&& rLen == other.rLen && lAncestorStart == other.lAncestorStart
					&& lAncestorLen == other.lAncestorLen;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		String string = "Left: " + toRangeString(lStart, lLen) + " Right: " + toRangeString(rStart, rLen); // $NON-NLS-1$
		if (lAncestorLen > 0 || lAncestorStart > 0)
			string += " Ancestor: " + toRangeString(lAncestorStart, lAncestorLen); //$NON-NLS-1$
		return string;
	}

	private String toRangeString(int start, int len) {
		return "(" + start + ", " + len + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
