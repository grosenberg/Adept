package net.certiv.adept.data;

import java.awt.Dimension;

/**
 * A 2D grid of elements.
 */
public class Grid<E> {

	E[][] elements;
	int[][] focus;
	View field;

	public Grid(Dimension size) {
		super();
	}
}
