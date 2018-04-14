package net.certiv.adept.lang.antlr.parser;

import net.certiv.adept.format.plan.Aligner;
import net.certiv.adept.format.plan.enums.Align;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser.AltBlockContext;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser.AltListContext;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser.AssignStmtContext;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser.ElementsContext;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4Parser.RuleBlockContext;
import net.certiv.adept.lang.antlr.parser.gen.Antlr4ParserBaseListener;

public class AlignVisitor extends Antlr4ParserBaseListener {

	private Aligner aligner;

	public AlignVisitor(Aligner aligner) {
		this.aligner = aligner;
	}

	// ---- Symbols ----

	// align pair - no group
	@Override
	public void enterAssignStmt(AssignStmtContext ctx) {
		aligner.align(Align.PAIR, ctx, ctx.EQ(), ctx.SEMI());
	}

	// align list - no group
	@Override
	public void enterElements(ElementsContext ctx) {
		if (ctx.POUND() != null) aligner.align(Align.LIST, ctx, ctx.POUND());
	}

	// align guards - allow group members
	@Override
	public void enterRuleBlock(RuleBlockContext ctx) {
		aligner.groupBeg(ctx);
		aligner.align(Align.GROUP, ctx, ctx.COLON(), ctx.SEMI());
	}

	// align guards - allow group members
	@Override
	public void enterAltBlock(AltBlockContext ctx) {
		aligner.groupBeg(ctx);
		aligner.align(Align.GROUP, ctx, ctx.LPAREN(), ctx.RPAREN());
	}

	// align list - include in parent group
	@Override
	public void enterAltList(AltListContext ctx) {
		if (ctx.OR() != null) aligner.align(Align.GROUP, ctx.getParent(), ctx.OR());
	}

	// ----

	@Override
	public void exitAltBlock(AltBlockContext ctx) {
		aligner.groupEnd(ctx);
	}

	@Override
	public void exitRuleBlock(RuleBlockContext ctx) {
		aligner.groupEnd(ctx);
	}
}