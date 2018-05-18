/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.unit;

import java.util.Comparator;

import net.certiv.adept.lang.AdeptToken;

public class AdeptComp implements Comparator<AdeptToken> {

	public static final AdeptComp Instance = new AdeptComp();

	@Override
	public int compare(AdeptToken o1, AdeptToken o2) {
		if (o1.getTokenIndex() < o2.getTokenIndex()) return -1;
		if (o1.getTokenIndex() > o2.getTokenIndex()) return 1;
		return 0;
	}
}
