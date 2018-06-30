/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.chunk;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.adept.lang.ParseRecord;

public class Chunker {

	@SuppressWarnings("unused")
	// key=owning context of statement chunks; value=root chunk
	private final Map<ParserRuleContext, Chunk> chunks = new HashMap<>();

	@SuppressWarnings("unused") private ParseRecord data;

	public Chunker(ParseRecord data) {
		this.data = data;
	}

	public void begStatement(ParserRuleContext ctx) {}

	public void add(ParserRuleContext ctx) {}

	public void endStatement(ParserRuleContext ctx) {}
}
