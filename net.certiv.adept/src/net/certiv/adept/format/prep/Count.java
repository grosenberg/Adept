/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format.prep;

import net.certiv.adept.util.Maths;

public enum Count {
	NONE,
	ONE,
	TWO,
	SOME,		// 3..5
	MANY,		// 6+
	VARIABLE;

	public static Count characterize(double[] nums) {
		if (nums.length > 1 && Maths.stdDeviation(nums) > 2) return Count.VARIABLE;
		return characterize((int) Maths.mean(nums));
	}

	public static Count characterize(int num) {
		if (num < 1) return NONE;
		if (num < 2) return ONE;
		if (num < 3) return TWO;
		if (num < 6) return SOME;
		return MANY;
	}
}
