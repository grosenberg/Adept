/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.vis.utils;

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
