// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/comment/parser/CommentParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.comment.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CommentParser}.
 */
public interface CommentParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CommentParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(CommentParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(CommentParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(CommentParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(CommentParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CommentParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CommentParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(CommentParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(CommentParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#desc}.
	 * @param ctx the parse tree
	 */
	void enterDesc(CommentParser.DescContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#desc}.
	 * @param ctx the parse tree
	 */
	void exitDesc(CommentParser.DescContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(CommentParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(CommentParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(CommentParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(CommentParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(CommentParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(CommentParser.WordContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#preform}.
	 * @param ctx the parse tree
	 */
	void enterPreform(CommentParser.PreformContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#preform}.
	 * @param ctx the parse tree
	 */
	void exitPreform(CommentParser.PreformContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#inline}.
	 * @param ctx the parse tree
	 */
	void enterInline(CommentParser.InlineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#inline}.
	 * @param ctx the parse tree
	 */
	void exitInline(CommentParser.InlineContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#hdret}.
	 * @param ctx the parse tree
	 */
	void enterHdret(CommentParser.HdretContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#hdret}.
	 * @param ctx the parse tree
	 */
	void exitHdret(CommentParser.HdretContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#flow}.
	 * @param ctx the parse tree
	 */
	void enterFlow(CommentParser.FlowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#flow}.
	 * @param ctx the parse tree
	 */
	void exitFlow(CommentParser.FlowContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(CommentParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(CommentParser.ListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(CommentParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(CommentParser.ItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link CommentParser#blank}.
	 * @param ctx the parse tree
	 */
	void enterBlank(CommentParser.BlankContext ctx);
	/**
	 * Exit a parse tree produced by {@link CommentParser#blank}.
	 * @param ctx the parse tree
	 */
	void exitBlank(CommentParser.BlankContext ctx);
}