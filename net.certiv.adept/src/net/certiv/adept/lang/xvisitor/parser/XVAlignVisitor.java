/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.xvisitor.parser;

import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.XaltContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.XmainContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.XpathContext;
import net.certiv.adept.format.prep.Aligner;
import net.certiv.adept.format.prep.Scheme;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParserBaseListener;

public class XVAlignVisitor extends XVisitorParserBaseListener {

	private Aligner aligner;

	public XVAlignVisitor(Aligner aligner) {
		this.aligner = aligner;
	}

	// ---- Symbols ----

	// align guards - allow group members
	@Override
	public void enterXmain(XmainContext ctx) {
		aligner.groupBeg(ctx);
		aligner.align(Scheme.GROUP, ctx, ctx.COLON(), ctx.SEMI());
	}

	// align list - include in parent group
	@Override
	public void enterXalt(XaltContext ctx) {
		if (ctx.OR() != null) aligner.align(Scheme.GROUP, ctx.getParent(), ctx.OR());
	}

	// align guards - allow group members
	@Override
	public void enterXpath(XpathContext ctx) {
		aligner.groupBeg(ctx);
		aligner.align(Scheme.GROUP, ctx, ctx.COLON(), ctx.SEMI());
	}

	// ----

	@Override
	public void exitXmain(XmainContext ctx) {
		aligner.groupEnd(ctx);
	}

	@Override
	public void exitXpath(XpathContext ctx) {
		aligner.groupEnd(ctx);
	}
}
