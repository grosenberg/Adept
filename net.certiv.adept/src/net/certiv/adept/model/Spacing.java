/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model;

import net.certiv.adept.util.Strings;

public enum Spacing {

	HFLEX("HFlex", 1),
	HSPACE("HSpace", 2),
	NONE("None", 3),
	VLINE("VLine", 4),
	VFLEX("VFlex", 5),

	UNKNOWN("", 99);

	private final String _name;
	private final int _div;

	private Spacing(String name, int div) {
		_name = name;
		_div = div;
	}

	public boolean terminal() {
		return this == VLINE || this == VFLEX;
	}

	/** Characterize the white space in the given string. */
	public static Spacing characterize(String text, int tabWidth) {
		switch (Strings.countVWS(text)) {
			case 0:
				switch (Strings.measureVisualWidth(text, tabWidth)) {
					case 0:
						return NONE;
					case 1:
						return HSPACE;
					default:
						return HFLEX;
				}
			case 1:
				return VLINE;
			default:
				return VFLEX;
		}
	}

	public static double score(Spacing a, Spacing b) {
		if (a == UNKNOWN || b == UNKNOWN) return 0;

		double dist = 4 - Math.abs(a._div - b._div);
		return dist / 4;
	}

	@Override
	public String toString() {
		return _name;
	}
}
