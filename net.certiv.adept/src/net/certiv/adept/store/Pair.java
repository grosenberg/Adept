/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.store;

public class Pair<A, B> {

	public A a;
	public B b;

	public Pair() {}

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", a, b);
	}
}
