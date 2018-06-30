package net.certiv.adept.lang.chunk;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.AdeptToken;

public class Chunk {

	int rule;	// rule number
	int begIdx; // first token index
	int endIdx; // last token index

	// region covered
	public int line;
	public int col;
	public int width;
	public int height;

	public boolean containsBreak;
	public boolean definesBreak;

	// included chunks
	List<Chunk> elements = new ArrayList<>();

	// description: branch length sums and
	// other tree derivedd metrics
	double density;

	/** Constructs a new <code>Chunk</code> for the given context. */
	public Chunk(ParserRuleContext ctx) {
		rule = ctx.getRuleIndex();
		AdeptToken start = (AdeptToken) ctx.start;
		AdeptToken stop = (AdeptToken) ctx.stop;
		begIdx = start.getTokenIndex();
		endIdx = stop.getTokenIndex();

		line = start.getLine();
		col = start.visCol();
		height = stop.getLine() - line + 1;
		width = stop.visCol() + stop.getText().length() - col + 1;
	}

	/** Constructs a new <code>Chunk</code> with the given values. */
	public Chunk(int line, int col, int width, int height) {
		this.line = line;
		this.col = col;
		this.width = width;
		this.height = height;
	}

	public Chunk(Chunk t) {
		this(t.line, t.col, t.width, t.height);
	}

	/** Returns the location of this <code>Chunk</code>. */
	public Point getLocation() {
		return new Point(line, col);
	}

	/** Sets this <code>Chunk</code> to the specified location. */
	public void setLocation(Point p) {
		this.line = p.x;
		this.col = p.y;
	}

	/** Gets the size of this <code>Chunk</code> as a <code>Dimension</code>. */
	public Dimension getSize() {
		return new Dimension(width, height);
	}

	/** Sets the size of this <code>Chunk</code> from the given <code>Dimension</code>. */
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	/** Sets the size of this <code>Chunk</code> to the specified width and height. */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setBounds(int line, int col, int width, int height) {
		this.line = line;
		this.col = col;
		this.width = width;
		this.height = height;
	}

	/**
	 * Checks whether or not this <code>Chunk</code> entirely contains the specified <code>Chunk</code>.
	 */
	public boolean contains(Chunk t) {
		return contains(t.line, t.col, t.width, t.height);
	}

	/**
	 * Checks whether this <code>Chunk</code> entirely contains the <code>Chunk</code> at the given
	 * location.
	 */
	public boolean contains(int X, int Y, int W, int H) {
		int w = this.width;
		int h = this.height;
		if ((w | h | W | H) < 0) {
			// At least one of the dimensions is negative...
			return false;
		}
		// Note: if any dimension is zero, tests below must return false...
		int line = this.line;
		int col = this.col;
		if (X < line || Y < col) {
			return false;
		}
		w += line;
		W += X;
		if (W <= X) {
			// X+W overflowed or W was zero, return false if...
			// either original w or W was zero or
			// line+w did not overflow or
			// the overflowed line+w is smaller than the overflowed X+W
			if (w >= line || W > w) return false;
		} else {
			// X+W did not overflow and W was not zero, return false if...
			// original w was zero or
			// line+w did not overflow and line+w is smaller than X+W
			if (w >= line && W > w) return false;
		}
		h += col;
		H += Y;
		if (H <= Y) {
			if (h >= col || H > h) return false;
		} else {
			if (h >= col && H > h) return false;
		}
		return true;
	}

	/**
	 * Determines whether or not this <code>Chunk</code> and the specified <code>Chunk</code> intersect.
	 * Two rectangles intersect if their intersection is nonempty.
	 */
	public boolean intersects(Chunk t) {
		int tw = this.width;
		int th = this.height;
		int rw = t.width;
		int rh = t.height;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		int tx = this.line;
		int ty = this.col;
		int rx = t.line;
		int ry = t.col;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	/**
	 * Computes the intersection of this <code>Chunk</code> with the specified <code>Chunk</code>.
	 * Returns a new <code>Chunk</code> that represents the intersection of the two rectangles. If the
	 * two rectangles do not intersect, the result will be an empty rectangle.
	 */
	public Chunk intersection(Chunk t) {
		int tx1 = this.line;
		int ty1 = this.col;
		int rx1 = t.line;
		int ry1 = t.col;
		long tx2 = tx1;
		tx2 += this.width;
		long ty2 = ty1;
		ty2 += this.height;
		long rx2 = rx1;
		rx2 += t.width;
		long ry2 = ry1;
		ry2 += t.height;
		if (tx1 < rx1) tx1 = rx1;
		if (ty1 < ry1) ty1 = ry1;
		if (tx2 > rx2) tx2 = rx2;
		if (ty2 > ry2) ty2 = ry2;
		tx2 -= tx1;
		ty2 -= ty1;
		// tx2,ty2 will never overflow (they will never be
		// larger than the smallest of the two source w,h)
		// they might underflow, though...
		if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
		if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
		return new Chunk(tx1, ty1, (int) tx2, (int) ty2);
	}

	/**
	 * Computes the union of this <code>Chunk</code> with the specified <code>Chunk</code>. Returns a
	 * new <code>Chunk</code> that represents the union of the two rectangles.
	 * <p>
	 * If either {@code Chunk} has any dimension less than zero the rules for
	 * <a href=#NonExistant>non-existant</a> rectangles apply. If only one has a dimension less than
	 * zero, then the result will be a copy of the other {@code Chunk}. If both have dimension less than
	 * zero, then the result will have at least one dimension less than zero.
	 * <p>
	 * If the resulting {@code Chunk} would have a dimension too large to be expressed as an
	 * {@code int}, the result will have a dimension of {@code Integer.MAX_VALUE} along that dimension.
	 */
	public Chunk union(Chunk r) {
		long tx2 = this.width;
		long ty2 = this.height;
		if ((tx2 | ty2) < 0) {
			// This rectangle has negative dimensions...
			// If r has non-negative dimensions then it is the answer.
			// If r is non-existant (has a negative dimension), then both
			// are non-existant and we can return any non-existant rectangle
			// as an answer. Thus, returning r meets that criterion.
			// Either way, r is our answer.
			return new Chunk(r);
		}
		long rx2 = r.width;
		long ry2 = r.height;
		if ((rx2 | ry2) < 0) {
			return new Chunk(this);
		}
		int tx1 = this.line;
		int ty1 = this.col;
		tx2 += tx1;
		ty2 += ty1;
		int rx1 = r.line;
		int ry1 = r.col;
		rx2 += rx1;
		ry2 += ry1;
		if (tx1 > rx1) tx1 = rx1;
		if (ty1 > ry1) ty1 = ry1;
		if (tx2 < rx2) tx2 = rx2;
		if (ty2 < ry2) ty2 = ry2;
		tx2 -= tx1;
		ty2 -= ty1;
		// tx2,ty2 will never underflow since both original rectangles
		// were already proven to be non-empty
		// they might overflow, though...
		if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
		if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
		return new Chunk(tx1, ty1, (int) tx2, (int) ty2);
	}

	/**
	 * Adds a point, specified by the integer arguments {@code newx,newy} to the bounds of this
	 * {@code Chunk}.
	 * <p>
	 * If this {@code Chunk} has any dimension less than zero, the rules for
	 * <a href=#NonExistant>non-existant</a> rectangles apply. In that case, the new bounds of this
	 * {@code Chunk} will have a location equal to the specified coordinates and width and height equal
	 * to zero.
	 * <p>
	 * After adding a point, a call to <code>contains</code> with the added point as an argument does
	 * not necessarily return <code>true</code>. The <code>contains</code> method does not return
	 * <code>true</code> for points on the right or bottom edges of a <code>Chunk</code>. Therefore, if
	 * the added point falls on the right or bottom edge of the enlarged <code>Chunk</code>,
	 * <code>contains</code> returns <code>false</code> for that point. If the specified point must be
	 * contained within the new {@code Chunk}, a 1x1 rectangle should be added instead:
	 *
	 * <pre>
	 * r.add(newx, newy, 1, 1);
	 * </pre>
	 *
	 * @param newx the X coordinate of the new point
	 * @param newy the Y coordinate of the new point
	 */
	public void add(int line, int col) {
		if ((width | height) < 0) {
			this.line = line;
			this.col = col;
			this.width = this.height = 0;
			return;
		}
		int x1 = this.line;
		int y1 = this.col;
		long x2 = this.width;
		long y2 = this.height;
		x2 += x1;
		y2 += y1;
		if (x1 > line) x1 = line;
		if (y1 > col) y1 = col;
		if (x2 < line) x2 = line;
		if (y2 < col) y2 = col;
		x2 -= x1;
		y2 -= y1;
		if (x2 > Integer.MAX_VALUE) x2 = Integer.MAX_VALUE;
		if (y2 > Integer.MAX_VALUE) y2 = Integer.MAX_VALUE;
		setBounds(x1, y1, (int) x2, (int) y2);
	}

	/**
	 * Adds a <code>Chunk</code> to this <code>Chunk</code>. The resulting <code>Chunk</code> is the
	 * union of the two rectangles.
	 * <p>
	 * If either {@code Chunk} has any dimension less than 0, the result will have the dimensions of the
	 * other {@code Chunk}. If both {@code Chunk}s have at least one dimension less than 0, the result
	 * will have at least one dimension less than 0.
	 * <p>
	 * If either {@code Chunk} has one or both dimensions equal to 0, the result along those axes with 0
	 * dimensions will be equivalent to the results obtained by adding the corresponding origin
	 * coordinate to the result rectangle along that axis, similar to the operation of the
	 * {@link #add(Point)} method, but contribute no further dimension beyond that.
	 * <p>
	 * If the resulting {@code Chunk} would have a dimension too large to be expressed as an
	 * {@code int}, the result will have a dimension of {@code Integer.MAX_VALUE} along that dimension.
	 *
	 * @param r the specified <code>Chunk</code>
	 */
	public void add(Chunk t) {
		long tx2 = this.width;
		long ty2 = this.height;
		if ((tx2 | ty2) < 0) {
			setBounds(t.line, t.col, t.width, t.height);
		}
		long rx2 = t.width;
		long ry2 = t.height;
		if ((rx2 | ry2) < 0) {
			return;
		}
		int tx1 = this.line;
		int ty1 = this.col;
		tx2 += tx1;
		ty2 += ty1;
		int rx1 = t.line;
		int ry1 = t.col;
		rx2 += rx1;
		ry2 += ry1;
		if (tx1 > rx1) tx1 = rx1;
		if (ty1 > ry1) ty1 = ry1;
		if (tx2 < rx2) tx2 = rx2;
		if (ty2 < ry2) ty2 = ry2;
		tx2 -= tx1;
		ty2 -= ty1;
		// tx2,ty2 will never underflow since both original
		// rectangles were non-empty
		// they might overflow, though...
		if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
		if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
		setBounds(tx1, ty1, (int) tx2, (int) ty2);
	}

	void density() {

	}

	public boolean isEmpty() {
		return (width <= 0) || (height <= 0);
	}

	/**
	 * Checks whether two rectangles are equal.
	 * <p>
	 * The result is <code>true</code> if and only if the argument is not <code>null</code> and is a
	 * <code>Chunk</code> object that has the same upper-left corner, width, and height as this
	 * <code>Chunk</code>.
	 *
	 * @param obj the <code>Object</code> to compare with this <code>Chunk</code>
	 * @return <code>true</code> if the objects are equal; <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chunk) {
			Chunk r = (Chunk) obj;
			return ((line == r.line) && (col == r.col) && (width == r.width) && (height == r.height));
		}
		return super.equals(obj);
	}

	/**
	 * Returns a <code>String</code> representing this <code>Chunk</code> and its values.
	 *
	 * @return a <code>String</code> representing this <code>Chunk</code> object's coordinate and size
	 *         values.
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[line=" + line + ",col=" + col + ",width=" + width + ",height=" + height + "]";
	}
}
