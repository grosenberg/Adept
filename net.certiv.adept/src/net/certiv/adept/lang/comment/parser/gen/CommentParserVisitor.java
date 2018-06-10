// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/comment/parser/CommentParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.comment.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CommentParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CommentParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CommentParser#comment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComment(CommentParser.CommentContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(CommentParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CommentParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#line}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLine(CommentParser.LineContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#desc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDesc(CommentParser.DescContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(CommentParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(CommentParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#word}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWord(CommentParser.WordContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#preform}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreform(CommentParser.PreformContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#inline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInline(CommentParser.InlineContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#hdret}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHdret(CommentParser.HdretContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#flow}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow(CommentParser.FlowContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(CommentParser.ListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitItem(CommentParser.ItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link CommentParser#blank}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(CommentParser.BlankContext ctx);
}