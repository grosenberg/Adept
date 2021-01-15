// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/antlr/parser/Antlr4Parser.g4 by ANTLR 4.7.2

	package net.certiv.adept.lang.antlr.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Antlr4Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Antlr4ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#antlr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAntlr(Antlr4Parser.AntlrContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Antlr4Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(Antlr4Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#cmdBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdBlock(Antlr4Parser.CmdBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(Antlr4Parser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#listStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListStmt(Antlr4Parser.ListStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(Antlr4Parser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#atBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtBlock(Antlr4Parser.AtBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#ruleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleSpec(Antlr4Parser.RuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#ruleBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleBlock(Antlr4Parser.RuleBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#argsBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgsBlock(Antlr4Parser.ArgsBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#prequel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrequel(Antlr4Parser.PrequelContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#altList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAltList(Antlr4Parser.AltListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#elements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElements(Antlr4Parser.ElementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(Antlr4Parser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#namedElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedElement(Antlr4Parser.NamedElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#altBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAltBlock(Antlr4Parser.AltBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSet(Antlr4Parser.SetContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#pred}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPred(Antlr4Parser.PredContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction(Antlr4Parser.ActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(Antlr4Parser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(Antlr4Parser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#attrValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrValue(Antlr4Parser.AttrValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#dottedID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDottedID(Antlr4Parser.DottedIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(Antlr4Parser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(Antlr4Parser.IdContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#keyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyword(Antlr4Parser.KeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix(Antlr4Parser.PrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(Antlr4Parser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(Antlr4Parser.OpContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#mod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMod(Antlr4Parser.ModContext ctx);
	/**
	 * Visit a parse tree produced by {@link Antlr4Parser#other}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOther(Antlr4Parser.OtherContext ctx);
}