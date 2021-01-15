// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STParser.g4 by ANTLR 4.7.2

	package net.certiv.adept.lang.stg.parser.gen;
	

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STParser}.
 */
public interface STParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STParser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(STParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(STParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(STParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(STParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#singleElement}.
	 * @param ctx the parse tree
	 */
	void enterSingleElement(STParser.SingleElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#singleElement}.
	 * @param ctx the parse tree
	 */
	void exitSingleElement(STParser.SingleElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#compoundElement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundElement(STParser.CompoundElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#compoundElement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundElement(STParser.CompoundElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#exprTag}.
	 * @param ctx the parse tree
	 */
	void enterExprTag(STParser.ExprTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#exprTag}.
	 * @param ctx the parse tree
	 */
	void exitExprTag(STParser.ExprTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#region}.
	 * @param ctx the parse tree
	 */
	void enterRegion(STParser.RegionContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#region}.
	 * @param ctx the parse tree
	 */
	void exitRegion(STParser.RegionContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#subtemplate}.
	 * @param ctx the parse tree
	 */
	void enterSubtemplate(STParser.SubtemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#subtemplate}.
	 * @param ctx the parse tree
	 */
	void exitSubtemplate(STParser.SubtemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#ifstat}.
	 * @param ctx the parse tree
	 */
	void enterIfstat(STParser.IfstatContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#ifstat}.
	 * @param ctx the parse tree
	 */
	void exitIfstat(STParser.IfstatContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(STParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(STParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#andConditional}.
	 * @param ctx the parse tree
	 */
	void enterAndConditional(STParser.AndConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#andConditional}.
	 * @param ctx the parse tree
	 */
	void exitAndConditional(STParser.AndConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#notConditional}.
	 * @param ctx the parse tree
	 */
	void enterNotConditional(STParser.NotConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#notConditional}.
	 * @param ctx the parse tree
	 */
	void exitNotConditional(STParser.NotConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#notConditionalExpr}.
	 * @param ctx the parse tree
	 */
	void enterNotConditionalExpr(STParser.NotConditionalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#notConditionalExpr}.
	 * @param ctx the parse tree
	 */
	void exitNotConditionalExpr(STParser.NotConditionalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#exprOptions}.
	 * @param ctx the parse tree
	 */
	void enterExprOptions(STParser.ExprOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#exprOptions}.
	 * @param ctx the parse tree
	 */
	void exitExprOptions(STParser.ExprOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(STParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(STParser.OptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(STParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(STParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#mapExpr}.
	 * @param ctx the parse tree
	 */
	void enterMapExpr(STParser.MapExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#mapExpr}.
	 * @param ctx the parse tree
	 */
	void exitMapExpr(STParser.MapExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#memberExpr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(STParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#memberExpr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(STParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#mapTemplateRef}.
	 * @param ctx the parse tree
	 */
	void enterMapTemplateRef(STParser.MapTemplateRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#mapTemplateRef}.
	 * @param ctx the parse tree
	 */
	void exitMapTemplateRef(STParser.MapTemplateRefContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#includeExpr}.
	 * @param ctx the parse tree
	 */
	void enterIncludeExpr(STParser.IncludeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#includeExpr}.
	 * @param ctx the parse tree
	 */
	void exitIncludeExpr(STParser.IncludeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(STParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(STParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(STParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(STParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(STParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(STParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#argExprList}.
	 * @param ctx the parse tree
	 */
	void enterArgExprList(STParser.ArgExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#argExprList}.
	 * @param ctx the parse tree
	 */
	void exitArgExprList(STParser.ArgExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link STParser#namedArg}.
	 * @param ctx the parse tree
	 */
	void enterNamedArg(STParser.NamedArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link STParser#namedArg}.
	 * @param ctx the parse tree
	 */
	void exitNamedArg(STParser.NamedArgContext ctx);
}