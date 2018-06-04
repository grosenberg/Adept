// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.stg.parser.gen;
	

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STParser#template}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(STParser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(STParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#singleElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElement(STParser.SingleElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#compoundElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundElement(STParser.CompoundElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#exprTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprTag(STParser.ExprTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#region}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegion(STParser.RegionContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#subtemplate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtemplate(STParser.SubtemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#ifstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstat(STParser.IfstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(STParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#andConditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndConditional(STParser.AndConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#notConditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotConditional(STParser.NotConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#notConditionalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotConditionalExpr(STParser.NotConditionalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#exprOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOptions(STParser.ExprOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOption(STParser.OptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(STParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#mapExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapExpr(STParser.MapExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#memberExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(STParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#mapTemplateRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMapTemplateRef(STParser.MapTemplateRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#includeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncludeExpr(STParser.IncludeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(STParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(STParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(STParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#argExprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgExprList(STParser.ArgExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link STParser#namedArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedArg(STParser.NamedArgContext ctx);
}