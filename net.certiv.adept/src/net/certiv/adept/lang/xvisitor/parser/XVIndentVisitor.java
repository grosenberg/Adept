package net.certiv.adept.lang.xvisitor.parser;

import net.certiv.adept.format.plan.Indenter;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.ActionBlockContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.GrammarSpecContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.OptionContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.OptionsSpecContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.XmainContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParser.XpathContext;
import net.certiv.adept.lang.xvisitor.parser.gen.XVisitorParserBaseListener;

public class XVIndentVisitor extends XVisitorParserBaseListener {

	private Indenter indenter;

	public XVIndentVisitor(Indenter indenter) {
		this.indenter = indenter;
	}

	// ---- Statements ----

	@Override
	public void enterOption(OptionContext ctx) {
		indenter.statement(ctx.OPT_ID(0), ctx.OPT_SEMI());
	}

	// ---- Bodies ----

	@Override
	public void enterOptionsSpec(OptionsSpecContext ctx) {
		indenter.indent(ctx.OPT_LBRACE(), ctx.OPT_RBRACE());
	}

	@Override
	public void enterXmain(XmainContext ctx) {
		indenter.indent(ctx.COLON(), ctx.SEMI());
	}

	@Override
	public void enterXpath(XpathContext ctx) {
		indenter.indent(ctx.COLON(), ctx.SEMI());
	}

	@Override
	public void enterActionBlock(ActionBlockContext ctx) {
		indenter.indent(ctx.LBRACE(), ctx.RBRACE());
	}

	// ---- Done ----

	@Override
	public void exitGrammarSpec(GrammarSpecContext ctx) {
		indenter.finalize(ctx);
	}
}
