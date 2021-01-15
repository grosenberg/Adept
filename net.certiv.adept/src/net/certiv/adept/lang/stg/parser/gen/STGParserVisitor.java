// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STGParser.g4 by ANTLR 4.7.2

	package net.certiv.adept.lang.stg.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STGParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STGParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STGParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(STGParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#groupSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupSpec(STGParser.GroupSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#delimiters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelimiters(STGParser.DelimitersContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#imports}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImports(STGParser.ImportsContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#importSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportSpec(STGParser.ImportSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#template}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate(STGParser.TemplateContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#formalArgList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalArgList(STGParser.FormalArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#commaArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaArg(STGParser.CommaArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#formalArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalArg(STGParser.FormalArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#dict}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDict(STGParser.DictContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#dictPairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictPairs(STGParser.DictPairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#keyValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValuePair(STGParser.KeyValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#defaultValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValuePair(STGParser.DefaultValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link STGParser#keyValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeyValue(STGParser.KeyValueContext ctx);
}