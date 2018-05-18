/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.model;

public enum Kind {
	TERMINAL("Terminal"),
	BLOCKCOMMENT("Block Comment"),
	LINECOMMENT("Line Comment"),
	WHITESPACE("White Space");

	private String kind;

	Kind(String name) {
		kind = name;
	}

	@Override
	public String toString() {
		return kind;
	}

	public boolean isComment() {
		return this == Kind.BLOCKCOMMENT || this == LINECOMMENT;
	}
}
