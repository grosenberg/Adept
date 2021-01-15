// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/xvisitor/parser/XVisitorParser.g4 by ANTLR 4.7.2

	package net.certiv.adept.lang.xvisitor.parser.gen;
//	import net.certiv.adept.lang.AdeptToken;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XVisitorParser}.
 */
public interface XVisitorParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#grammarSpec}.
	 * @param ctx the parse tree
	 */
	void enterGrammarSpec(XVisitorParser.GrammarSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#grammarSpec}.
	 * @param ctx the parse tree
	 */
	void exitGrammarSpec(XVisitorParser.GrammarSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#optionsSpec}.
	 * @param ctx the parse tree
	 */
	void enterOptionsSpec(XVisitorParser.OptionsSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#optionsSpec}.
	 * @param ctx the parse tree
	 */
	void exitOptionsSpec(XVisitorParser.OptionsSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(XVisitorParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(XVisitorParser.OptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#optionValue}.
	 * @param ctx the parse tree
	 */
	void enterOptionValue(XVisitorParser.OptionValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#optionValue}.
	 * @param ctx the parse tree
	 */
	void exitOptionValue(XVisitorParser.OptionValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#action}.
	 * @param ctx the parse tree
	 */
	void enterAction(XVisitorParser.ActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#action}.
	 * @param ctx the parse tree
	 */
	void exitAction(XVisitorParser.ActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#xmain}.
	 * @param ctx the parse tree
	 */
	void enterXmain(XVisitorParser.XmainContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#xmain}.
	 * @param ctx the parse tree
	 */
	void exitXmain(XVisitorParser.XmainContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#xalt}.
	 * @param ctx the parse tree
	 */
	void enterXalt(XVisitorParser.XaltContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#xalt}.
	 * @param ctx the parse tree
	 */
	void exitXalt(XVisitorParser.XaltContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#xpath}.
	 * @param ctx the parse tree
	 */
	void enterXpath(XVisitorParser.XpathContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#xpath}.
	 * @param ctx the parse tree
	 */
	void exitXpath(XVisitorParser.XpathContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#xpathSpec}.
	 * @param ctx the parse tree
	 */
	void enterXpathSpec(XVisitorParser.XpathSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#xpathSpec}.
	 * @param ctx the parse tree
	 */
	void exitXpathSpec(XVisitorParser.XpathSpecContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#actionBlock}.
	 * @param ctx the parse tree
	 */
	void enterActionBlock(XVisitorParser.ActionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#actionBlock}.
	 * @param ctx the parse tree
	 */
	void exitActionBlock(XVisitorParser.ActionBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#text}.
	 * @param ctx the parse tree
	 */
	void enterText(XVisitorParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#text}.
	 * @param ctx the parse tree
	 */
	void exitText(XVisitorParser.TextContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#reference}.
	 * @param ctx the parse tree
	 */
	void enterReference(XVisitorParser.ReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#reference}.
	 * @param ctx the parse tree
	 */
	void exitReference(XVisitorParser.ReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#separator}.
	 * @param ctx the parse tree
	 */
	void enterSeparator(XVisitorParser.SeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#separator}.
	 * @param ctx the parse tree
	 */
	void exitSeparator(XVisitorParser.SeparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link XVisitorParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(XVisitorParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link XVisitorParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(XVisitorParser.WordContext ctx);
}