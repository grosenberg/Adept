package net.certiv.adept.topo;

public class Point {

	private int col;
	private int line;
	private boolean relative;

	public Point(int col, int line) {
		super();
		this.col = col;
		this.line = line;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public Point makeRelativeTo(Point other) {
		col -= other.col;
		line -= other.line;
		relative = true;
		return this;
	}

	public boolean isRelative() {
		return relative;
	}

	@Override
	public String toString() {
		return String.format("Point [line=%s, col=%s]", line, col);
	}
}
