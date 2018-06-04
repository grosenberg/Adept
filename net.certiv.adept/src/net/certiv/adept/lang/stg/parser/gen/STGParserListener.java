// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STGParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.stg.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STGParser}.
 */
public interface STGParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STGParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(STGParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(STGParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#groupSpec}.
	 * @param ctx the parse tree
	 */
	void enterGroupSpec(STGParser.GroupSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#groupSpec}.
	 * @param ctx the parse tree
	 */
	void exitGroupSpec(STGParser.GroupSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#delimiters}.
	 * @param ctx the parse tree
	 */
	void enterDelimiters(STGParser.DelimitersContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#delimiters}.
	 * @param ctx the parse tree
	 */
	void exitDelimiters(STGParser.DelimitersContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterImports(STGParser.ImportsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitImports(STGParser.ImportsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#importSpec}.
	 * @param ctx the parse tree
	 */
	void enterImportSpec(STGParser.ImportSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#importSpec}.
	 * @param ctx the parse tree
	 */
	void exitImportSpec(STGParser.ImportSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(STGParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(STGParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#formalArgList}.
	 * @param ctx the parse tree
	 */
	void enterFormalArgList(STGParser.FormalArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#formalArgList}.
	 * @param ctx the parse tree
	 */
	void exitFormalArgList(STGParser.FormalArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#commaArg}.
	 * @param ctx the parse tree
	 */
	void enterCommaArg(STGParser.CommaArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#commaArg}.
	 * @param ctx the parse tree
	 */
	void exitCommaArg(STGParser.CommaArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#formalArg}.
	 * @param ctx the parse tree
	 */
	void enterFormalArg(STGParser.FormalArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#formalArg}.
	 * @param ctx the parse tree
	 */
	void exitFormalArg(STGParser.FormalArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(STGParser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(STGParser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#dictPairs}.
	 * @param ctx the parse tree
	 */
	void enterDictPairs(STGParser.DictPairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#dictPairs}.
	 * @param ctx the parse tree
	 */
	void exitDictPairs(STGParser.DictPairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#keyValuePair}.
	 * @param ctx the parse tree
	 */
	void enterKeyValuePair(STGParser.KeyValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#keyValuePair}.
	 * @param ctx the parse tree
	 */
	void exitKeyValuePair(STGParser.KeyValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#defaultValuePair}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValuePair(STGParser.DefaultValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#defaultValuePair}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValuePair(STGParser.DefaultValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link STGParser#keyValue}.
	 * @param ctx the parse tree
	 */
	void enterKeyValue(STGParser.KeyValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STGParser#keyValue}.
	 * @param ctx the parse tree
	 */
	void exitKeyValue(STGParser.KeyValueContext ctx);
}