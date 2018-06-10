// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/xvisitor/parser/XVisitorParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.xvisitor.parser.gen;
//	import net.certiv.adept.lang.AdeptToken;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XVisitorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TEXT=1, RBRACE=2, BLOCK_COMMENT=3, LINE_COMMENT=4, OPTIONS=5, LBRACE=6, 
		GRAMMAR=7, XVISITOR=8, COLON=9, COMMA=10, SEMI=11, ASSIGN=12, QUESTION=13, 
		STAR=14, AT=15, ANY=16, SEP=17, DOT=18, OR=19, NOT=20, ID=21, LITERAL=22, 
		HORZ_WS=23, VERT_WS=24, ERRCHAR=25, OPT_LBRACE=26, OPT_RBRACE=27, OPT_ID=28, 
		OPT_LITERAL=29, OPT_DOT=30, OPT_ASSIGN=31, OPT_SEMI=32, OPT_STAR=33, OPT_INT=34, 
		ABLOCK_RBRACE=35, ONENTRY=36, ONEXIT=37, REFERENCE=38;
	public static final int
		RULE_grammarSpec = 0, RULE_optionsSpec = 1, RULE_option = 2, RULE_optionValue = 3, 
		RULE_action = 4, RULE_xmain = 5, RULE_xalt = 6, RULE_xpath = 7, RULE_xpathSpec = 8, 
		RULE_actionBlock = 9, RULE_text = 10, RULE_reference = 11, RULE_separator = 12, 
		RULE_word = 13;
	public static final String[] ruleNames = {
		"grammarSpec", "optionsSpec", "option", "optionValue", "action", "xmain", 
		"xalt", "xpath", "xpathSpec", "actionBlock", "text", "reference", "separator", 
		"word"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "TEXT", "RBRACE", "BLOCK_COMMENT", "LINE_COMMENT", "OPTIONS", "LBRACE", 
		"GRAMMAR", "XVISITOR", "COLON", "COMMA", "SEMI", "ASSIGN", "QUESTION", 
		"STAR", "AT", "ANY", "SEP", "DOT", "OR", "NOT", "ID", "LITERAL", "HORZ_WS", 
		"VERT_WS", "ERRCHAR", "OPT_LBRACE", "OPT_RBRACE", "OPT_ID", "OPT_LITERAL", 
		"OPT_DOT", "OPT_ASSIGN", "OPT_SEMI", "OPT_STAR", "OPT_INT", "ABLOCK_RBRACE", 
		"ONENTRY", "ONEXIT", "REFERENCE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "XVisitorParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XVisitorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GrammarSpecContext extends ParserRuleContext {
		public TerminalNode XVISITOR() { return getToken(XVisitorParser.XVISITOR, 0); }
		public TerminalNode GRAMMAR() { return getToken(XVisitorParser.GRAMMAR, 0); }
		public TerminalNode ID() { return getToken(XVisitorParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(XVisitorParser.SEMI, 0); }
		public XmainContext xmain() {
			return getRuleContext(XmainContext.class,0);
		}
		public TerminalNode EOF() { return getToken(XVisitorParser.EOF, 0); }
		public OptionsSpecContext optionsSpec() {
			return getRuleContext(OptionsSpecContext.class,0);
		}
		public List<ActionContext> action() {
			return getRuleContexts(ActionContext.class);
		}
		public ActionContext action(int i) {
			return getRuleContext(ActionContext.class,i);
		}
		public List<XpathContext> xpath() {
			return getRuleContexts(XpathContext.class);
		}
		public XpathContext xpath(int i) {
			return getRuleContext(XpathContext.class,i);
		}
		public GrammarSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterGrammarSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitGrammarSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitGrammarSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarSpecContext grammarSpec() throws RecognitionException {
		GrammarSpecContext _localctx = new GrammarSpecContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_grammarSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			match(XVISITOR);
			setState(29);
			match(GRAMMAR);
			setState(30);
			match(ID);
			setState(31);
			match(SEMI);
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OPTIONS) {
				{
				setState(32);
				optionsSpec();
				}
			}

			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(35);
				action();
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41);
			xmain();
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				xpath();
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(47);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionsSpecContext extends ParserRuleContext {
		public TerminalNode OPTIONS() { return getToken(XVisitorParser.OPTIONS, 0); }
		public TerminalNode OPT_LBRACE() { return getToken(XVisitorParser.OPT_LBRACE, 0); }
		public TerminalNode OPT_RBRACE() { return getToken(XVisitorParser.OPT_RBRACE, 0); }
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public OptionsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterOptionsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitOptionsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitOptionsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionsSpecContext optionsSpec() throws RecognitionException {
		OptionsSpecContext _localctx = new OptionsSpecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_optionsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			match(OPTIONS);
			setState(50);
			match(OPT_LBRACE);
			setState(52); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(51);
				option();
				}
				}
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==OPT_ID );
			setState(56);
			match(OPT_RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionContext extends ParserRuleContext {
		public List<TerminalNode> OPT_ID() { return getTokens(XVisitorParser.OPT_ID); }
		public TerminalNode OPT_ID(int i) {
			return getToken(XVisitorParser.OPT_ID, i);
		}
		public TerminalNode OPT_SEMI() { return getToken(XVisitorParser.OPT_SEMI, 0); }
		public TerminalNode OPT_ASSIGN() { return getToken(XVisitorParser.OPT_ASSIGN, 0); }
		public OptionValueContext optionValue() {
			return getRuleContext(OptionValueContext.class,0);
		}
		public List<TerminalNode> OPT_DOT() { return getTokens(XVisitorParser.OPT_DOT); }
		public TerminalNode OPT_DOT(int i) {
			return getToken(XVisitorParser.OPT_DOT, i);
		}
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_option);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(OPT_ID);
			setState(67);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPT_ASSIGN:
				{
				setState(59);
				match(OPT_ASSIGN);
				setState(60);
				optionValue();
				}
				break;
			case OPT_DOT:
				{
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(61);
					match(OPT_DOT);
					setState(62);
					match(OPT_ID);
					}
					}
					setState(65); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==OPT_DOT );
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(69);
			match(OPT_SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionValueContext extends ParserRuleContext {
		public TerminalNode OPT_ID() { return getToken(XVisitorParser.OPT_ID, 0); }
		public TerminalNode OPT_LITERAL() { return getToken(XVisitorParser.OPT_LITERAL, 0); }
		public TerminalNode OPT_INT() { return getToken(XVisitorParser.OPT_INT, 0); }
		public TerminalNode OPT_STAR() { return getToken(XVisitorParser.OPT_STAR, 0); }
		public OptionValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterOptionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitOptionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitOptionValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionValueContext optionValue() throws RecognitionException {
		OptionValueContext _localctx = new OptionValueContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_optionValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPT_ID) | (1L << OPT_LITERAL) | (1L << OPT_STAR) | (1L << OPT_INT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public Token name;
		public TerminalNode AT() { return getToken(XVisitorParser.AT, 0); }
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public TerminalNode ID() { return getToken(XVisitorParser.ID, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(AT);
			setState(74);
			((ActionContext)_localctx).name = match(ID);
			setState(75);
			actionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XmainContext extends ParserRuleContext {
		public Token name;
		public TerminalNode COLON() { return getToken(XVisitorParser.COLON, 0); }
		public List<TerminalNode> ID() { return getTokens(XVisitorParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(XVisitorParser.ID, i);
		}
		public TerminalNode SEMI() { return getToken(XVisitorParser.SEMI, 0); }
		public List<XaltContext> xalt() {
			return getRuleContexts(XaltContext.class);
		}
		public XaltContext xalt(int i) {
			return getRuleContext(XaltContext.class,i);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public XmainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xmain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterXmain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitXmain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitXmain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XmainContext xmain() throws RecognitionException {
		XmainContext _localctx = new XmainContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_xmain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			((XmainContext)_localctx).name = match(ID);
			setState(78);
			match(COLON);
			setState(79);
			match(ID);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(80);
				xalt();
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(86);
				actionBlock();
				}
			}

			setState(89);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XaltContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(XVisitorParser.OR, 0); }
		public TerminalNode ID() { return getToken(XVisitorParser.ID, 0); }
		public XaltContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xalt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterXalt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitXalt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitXalt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XaltContext xalt() throws RecognitionException {
		XaltContext _localctx = new XaltContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_xalt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(OR);
			setState(92);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XpathContext extends ParserRuleContext {
		public Token name;
		public TerminalNode COLON() { return getToken(XVisitorParser.COLON, 0); }
		public XpathSpecContext xpathSpec() {
			return getRuleContext(XpathSpecContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(XVisitorParser.SEMI, 0); }
		public TerminalNode ID() { return getToken(XVisitorParser.ID, 0); }
		public List<ActionBlockContext> actionBlock() {
			return getRuleContexts(ActionBlockContext.class);
		}
		public ActionBlockContext actionBlock(int i) {
			return getRuleContext(ActionBlockContext.class,i);
		}
		public XpathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xpath; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterXpath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitXpath(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitXpath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XpathContext xpath() throws RecognitionException {
		XpathContext _localctx = new XpathContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_xpath);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			((XpathContext)_localctx).name = match(ID);
			setState(95);
			match(COLON);
			setState(96);
			xpathSpec();
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACE) {
				{
				{
				setState(97);
				actionBlock();
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(103);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XpathSpecContext extends ParserRuleContext {
		public List<SeparatorContext> separator() {
			return getRuleContexts(SeparatorContext.class);
		}
		public SeparatorContext separator(int i) {
			return getRuleContext(SeparatorContext.class,i);
		}
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public XpathSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xpathSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterXpathSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitXpathSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitXpathSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XpathSpecContext xpathSpec() throws RecognitionException {
		XpathSpecContext _localctx = new XpathSpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_xpathSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(105);
				separator();
				setState(106);
				word();
				}
				}
				setState(110); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ANY || _la==SEP );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionBlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(XVisitorParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(XVisitorParser.RBRACE, 0); }
		public List<TextContext> text() {
			return getRuleContexts(TextContext.class);
		}
		public TextContext text(int i) {
			return getRuleContext(TextContext.class,i);
		}
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public TerminalNode ONENTRY() { return getToken(XVisitorParser.ONENTRY, 0); }
		public TerminalNode ONEXIT() { return getToken(XVisitorParser.ONEXIT, 0); }
		public ActionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterActionBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitActionBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitActionBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionBlockContext actionBlock() throws RecognitionException {
		ActionBlockContext _localctx = new ActionBlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_actionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(LBRACE);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ONENTRY || _la==ONEXIT) {
				{
				setState(113);
				_la = _input.LA(1);
				if ( !(_la==ONENTRY || _la==ONEXIT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(118); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(118);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(116);
					text();
					}
					break;
				case REFERENCE:
					{
					setState(117);
					reference();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(120); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TEXT || _la==REFERENCE );
			setState(122);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TextContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(XVisitorParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(XVisitorParser.TEXT, i);
		}
		public TextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TextContext text() throws RecognitionException {
		TextContext _localctx = new TextContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_text);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(125); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(124);
					match(TEXT);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(127); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReferenceContext extends ParserRuleContext {
		public TerminalNode REFERENCE() { return getToken(XVisitorParser.REFERENCE, 0); }
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_reference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(REFERENCE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeparatorContext extends ParserRuleContext {
		public TerminalNode ANY() { return getToken(XVisitorParser.ANY, 0); }
		public TerminalNode SEP() { return getToken(XVisitorParser.SEP, 0); }
		public TerminalNode NOT() { return getToken(XVisitorParser.NOT, 0); }
		public SeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_separator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitSeparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitSeparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_separator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_la = _input.LA(1);
			if ( !(_la==ANY || _la==SEP) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(132);
				match(NOT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WordContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(XVisitorParser.ID, 0); }
		public TerminalNode STAR() { return getToken(XVisitorParser.STAR, 0); }
		public TerminalNode LITERAL() { return getToken(XVisitorParser.LITERAL, 0); }
		public WordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_word; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).enterWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XVisitorParserListener ) ((XVisitorParserListener)listener).exitWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XVisitorParserVisitor ) return ((XVisitorParserVisitor<? extends T>)visitor).visitWord(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WordContext word() throws RecognitionException {
		WordContext _localctx = new WordContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_word);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << ID) | (1L << LITERAL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u008c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\2\5\2$\n\2\3"+
		"\2\7\2\'\n\2\f\2\16\2*\13\2\3\2\3\2\6\2.\n\2\r\2\16\2/\3\2\3\2\3\3\3\3"+
		"\3\3\6\3\67\n\3\r\3\16\38\3\3\3\3\3\4\3\4\3\4\3\4\3\4\6\4B\n\4\r\4\16"+
		"\4C\5\4F\n\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7T\n\7"+
		"\f\7\16\7W\13\7\3\7\5\7Z\n\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\7\te"+
		"\n\t\f\t\16\th\13\t\3\t\3\t\3\n\3\n\3\n\6\no\n\n\r\n\16\np\3\13\3\13\5"+
		"\13u\n\13\3\13\3\13\6\13y\n\13\r\13\16\13z\3\13\3\13\3\f\6\f\u0080\n\f"+
		"\r\f\16\f\u0081\3\r\3\r\3\16\3\16\5\16\u0088\n\16\3\17\3\17\3\17\2\2\20"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\6\4\2\36\37#$\3\2&\'\3\2\22\23"+
		"\4\2\20\20\27\30\2\u008c\2\36\3\2\2\2\4\63\3\2\2\2\6<\3\2\2\2\bI\3\2\2"+
		"\2\nK\3\2\2\2\fO\3\2\2\2\16]\3\2\2\2\20`\3\2\2\2\22n\3\2\2\2\24r\3\2\2"+
		"\2\26\177\3\2\2\2\30\u0083\3\2\2\2\32\u0085\3\2\2\2\34\u0089\3\2\2\2\36"+
		"\37\7\n\2\2\37 \7\t\2\2 !\7\27\2\2!#\7\r\2\2\"$\5\4\3\2#\"\3\2\2\2#$\3"+
		"\2\2\2$(\3\2\2\2%\'\5\n\6\2&%\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)"+
		"+\3\2\2\2*(\3\2\2\2+-\5\f\7\2,.\5\20\t\2-,\3\2\2\2./\3\2\2\2/-\3\2\2\2"+
		"/\60\3\2\2\2\60\61\3\2\2\2\61\62\7\2\2\3\62\3\3\2\2\2\63\64\7\7\2\2\64"+
		"\66\7\34\2\2\65\67\5\6\4\2\66\65\3\2\2\2\678\3\2\2\28\66\3\2\2\289\3\2"+
		"\2\29:\3\2\2\2:;\7\35\2\2;\5\3\2\2\2<E\7\36\2\2=>\7!\2\2>F\5\b\5\2?@\7"+
		" \2\2@B\7\36\2\2A?\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2E=\3"+
		"\2\2\2EA\3\2\2\2FG\3\2\2\2GH\7\"\2\2H\7\3\2\2\2IJ\t\2\2\2J\t\3\2\2\2K"+
		"L\7\21\2\2LM\7\27\2\2MN\5\24\13\2N\13\3\2\2\2OP\7\27\2\2PQ\7\13\2\2QU"+
		"\7\27\2\2RT\5\16\b\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2VY\3\2\2\2"+
		"WU\3\2\2\2XZ\5\24\13\2YX\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\7\r\2\2\\\r\3"+
		"\2\2\2]^\7\25\2\2^_\7\27\2\2_\17\3\2\2\2`a\7\27\2\2ab\7\13\2\2bf\5\22"+
		"\n\2ce\5\24\13\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3"+
		"\2\2\2ij\7\r\2\2j\21\3\2\2\2kl\5\32\16\2lm\5\34\17\2mo\3\2\2\2nk\3\2\2"+
		"\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\23\3\2\2\2rt\7\b\2\2su\t\3\2\2ts\3\2"+
		"\2\2tu\3\2\2\2ux\3\2\2\2vy\5\26\f\2wy\5\30\r\2xv\3\2\2\2xw\3\2\2\2yz\3"+
		"\2\2\2zx\3\2\2\2z{\3\2\2\2{|\3\2\2\2|}\7\4\2\2}\25\3\2\2\2~\u0080\7\3"+
		"\2\2\177~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3"+
		"\2\2\2\u0082\27\3\2\2\2\u0083\u0084\7(\2\2\u0084\31\3\2\2\2\u0085\u0087"+
		"\t\4\2\2\u0086\u0088\7\26\2\2\u0087\u0086\3\2\2\2\u0087\u0088\3\2\2\2"+
		"\u0088\33\3\2\2\2\u0089\u008a\t\5\2\2\u008a\35\3\2\2\2\21#(/8CEUYfptx"+
		"z\u0081\u0087";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}