/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.lang.java.parser;

import net.certiv.adept.format.plan.Indenter;
import net.certiv.adept.lang.java.parser.gen.Java8Parser.BlockContext;
import net.certiv.adept.lang.java.parser.gen.Java8Parser.CompilationUnitContext;
import net.certiv.adept.lang.java.parser.gen.Java8ParserBaseListener;

public class JavaIndentVisitor extends Java8ParserBaseListener {

	private Indenter indenter;

	public JavaIndentVisitor(Indenter indenter) {
		this.indenter = indenter;
	}

	// ---- Statements ----

	// @Override
	// public void enterPrimary(PrimaryContext ctx) {
	// indenter.statement(indenter.first(ctx.keyword()), ctx.SEMI());
	// }
	//
	// @Override
	// public void enterBlock(BlockContext ctx) {
	// // indenter.statement(indenter.first(ctx.keyword()), indenter.last(ctx.keyword()));
	// }
	//
	// @Override
	// public void enterAtBlock(AtBlockContext ctx) {
	// indenter.statement(ctx.AT(), indenter.before(ctx.action()));
	// }
	//
	// @Override
	// public void enterRuleSpec(RuleSpecContext ctx) {
	// indenter.statement(indenter.first(ctx.prefix(), ctx.id()), indenter.before(ctx.ruleBlock()));
	// }
	//
	// // ---- Bodies ----
	//
	@Override
	public void enterBlock(BlockContext ctx) {
		indenter.indent(ctx.LBRACE(), ctx.RBRACE());
	}

	// @Override
	// public void enterRuleBlock(RuleBlockContext ctx) {
	// indenter.indent(ctx.COLON(), ctx.SEMI());
	// }
	//
	// @Override
	// public void enterBody(BodyContext ctx) {
	// indenter.indent(ctx.LBRACE(), ctx.RBRACE());
	// }
	//
	// @Override
	// public void enterAltBlock(AltBlockContext ctx) {
	// indenter.indent(ctx.LPAREN(), ctx.RPAREN());
	// }
	//
	// @Override
	// public void enterAction(ActionContext ctx) {
	// indenter.indent(ctx.BEG_ACTION(), ctx.END_ACTION());
	// }

	// ---- Done ----

	@Override
	public void exitCompilationUnit(CompilationUnitContext ctx) {
		indenter.finalize(ctx);
	}
}
