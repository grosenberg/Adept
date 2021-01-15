/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format.prep;

import java.util.List;

import net.certiv.adept.lang.AdeptToken;

public enum Place {

	SOLO(0),

	BEG(1),
	MID(2),
	END(3),

	ANY(5);		// un-aligned/inapplicable

	private final int _div;

	private Place(int div) {
		_div = div;
	}

	public boolean atBOL() {
		return this == SOLO || this == BEG;
	}

	public boolean atEOL() {
		return this == END;
	}

	public static Place characterize(List<AdeptToken> tokens, AdeptToken token) {
		int idx = tokens.indexOf(token);
		if (idx == -1) return ANY;
		int len = tokens.size();
		if (len == 1) return SOLO;
		if (idx == 0) return BEG;
		if (idx == len) return Place.END;
		return MID;
	}

	// ANY -> MID = 0.0
	// SOL -> BEG = 0.5
	// BEG -> MID = 0.6
	// BEG -> END = 0.3
	// ANY -> ANY = 1.0
	public static double score(Place a, Place b) {
		if (a == b) return 1;
		if (a == ANY || b == ANY) return 0;
		if (a == SOLO || b == SOLO) {
			if (a == BEG || b == BEG || a == END || b == END) return 0.5;
		}

		double dist = 3 - Math.abs(a._div - b._div);
		return dist / 3;
	}
}
