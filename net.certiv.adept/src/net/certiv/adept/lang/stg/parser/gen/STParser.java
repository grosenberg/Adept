// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.stg.parser.gen;
	

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LBRACE=1, RDELIM=2, COMMA=3, DOC_COMMENT=4, BLOCK_COMMENT=5, LINE_COMMENT=6, 
		TMPL_COMMENT=7, HORZ_WS=8, VERT_WS=9, ESCAPE=10, LDELIM=11, RBRACE=12, 
		TEXT=13, STRING=14, IF=15, ELSEIF=16, ELSE=17, ENDIF=18, SUPER=19, END=20, 
		TRUE=21, FALSE=22, AT=23, ELLIPSIS=24, DOT=25, COLON=26, SEMI=27, AND=28, 
		OR=29, LPAREN=30, RPAREN=31, LBRACK=32, RBRACK=33, EQUALS=34, BANG=35, 
		ERR_CHAR=36, ID=37, PIPE=38;
	public static final int
		RULE_template = 0, RULE_element = 1, RULE_singleElement = 2, RULE_compoundElement = 3, 
		RULE_exprTag = 4, RULE_region = 5, RULE_subtemplate = 6, RULE_ifstat = 7, 
		RULE_conditional = 8, RULE_andConditional = 9, RULE_notConditional = 10, 
		RULE_notConditionalExpr = 11, RULE_exprOptions = 12, RULE_option = 13, 
		RULE_expr = 14, RULE_mapExpr = 15, RULE_memberExpr = 16, RULE_mapTemplateRef = 17, 
		RULE_includeExpr = 18, RULE_primary = 19, RULE_list = 20, RULE_args = 21, 
		RULE_argExprList = 22, RULE_namedArg = 23;
	public static final String[] ruleNames = {
		"template", "element", "singleElement", "compoundElement", "exprTag", 
		"region", "subtemplate", "ifstat", "conditional", "andConditional", "notConditional", 
		"notConditionalExpr", "exprOptions", "option", "expr", "mapExpr", "memberExpr", 
		"mapTemplateRef", "includeExpr", "primary", "list", "args", "argExprList", 
		"namedArg"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LBRACE", "RDELIM", "COMMA", "DOC_COMMENT", "BLOCK_COMMENT", "LINE_COMMENT", 
		"TMPL_COMMENT", "HORZ_WS", "VERT_WS", "ESCAPE", "LDELIM", "RBRACE", "TEXT", 
		"STRING", "IF", "ELSEIF", "ELSE", "ENDIF", "SUPER", "END", "TRUE", "FALSE", 
		"AT", "ELLIPSIS", "DOT", "COLON", "SEMI", "AND", "OR", "LPAREN", "RPAREN", 
		"LBRACK", "RBRACK", "EQUALS", "BANG", "ERR_CHAR", "ID", "PIPE"
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
	public String getGrammarFileName() { return "STParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TemplateContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(STParser.EOF, 0); }
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitTemplate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitTemplate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_template);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LDELIM || _la==TEXT) {
				{
				{
				setState(48);
				element();
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(54);
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

	public static class ElementContext extends ParserRuleContext {
		public SingleElementContext singleElement() {
			return getRuleContext(SingleElementContext.class,0);
		}
		public CompoundElementContext compoundElement() {
			return getRuleContext(CompoundElementContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_element);
		try {
			setState(58);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				singleElement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				compoundElement();
				}
				break;
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

	public static class SingleElementContext extends ParserRuleContext {
		public ExprTagContext exprTag() {
			return getRuleContext(ExprTagContext.class,0);
		}
		public List<TerminalNode> TEXT() { return getTokens(STParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(STParser.TEXT, i);
		}
		public SingleElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterSingleElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitSingleElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitSingleElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SingleElementContext singleElement() throws RecognitionException {
		SingleElementContext _localctx = new SingleElementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_singleElement);
		try {
			int _alt;
			setState(66);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LDELIM:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				exprTag();
				}
				break;
			case TEXT:
				enterOuterAlt(_localctx, 2);
				{
				setState(62); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(61);
						match(TEXT);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(64); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class CompoundElementContext extends ParserRuleContext {
		public IfstatContext ifstat() {
			return getRuleContext(IfstatContext.class,0);
		}
		public RegionContext region() {
			return getRuleContext(RegionContext.class,0);
		}
		public CompoundElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterCompoundElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitCompoundElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitCompoundElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundElementContext compoundElement() throws RecognitionException {
		CompoundElementContext _localctx = new CompoundElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compoundElement);
		try {
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				ifstat();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				region();
				}
				break;
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

	public static class ExprTagContext extends ParserRuleContext {
		public TerminalNode LDELIM() { return getToken(STParser.LDELIM, 0); }
		public MapExprContext mapExpr() {
			return getRuleContext(MapExprContext.class,0);
		}
		public TerminalNode RDELIM() { return getToken(STParser.RDELIM, 0); }
		public TerminalNode SEMI() { return getToken(STParser.SEMI, 0); }
		public ExprOptionsContext exprOptions() {
			return getRuleContext(ExprOptionsContext.class,0);
		}
		public ExprTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterExprTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitExprTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitExprTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprTagContext exprTag() throws RecognitionException {
		ExprTagContext _localctx = new ExprTagContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_exprTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(LDELIM);
			setState(73);
			mapExpr();
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(74);
				match(SEMI);
				setState(75);
				exprOptions();
				}
			}

			setState(78);
			match(RDELIM);
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

	public static class RegionContext extends ParserRuleContext {
		public List<TerminalNode> LDELIM() { return getTokens(STParser.LDELIM); }
		public TerminalNode LDELIM(int i) {
			return getToken(STParser.LDELIM, i);
		}
		public TerminalNode AT() { return getToken(STParser.AT, 0); }
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public List<TerminalNode> RDELIM() { return getTokens(STParser.RDELIM); }
		public TerminalNode RDELIM(int i) {
			return getToken(STParser.RDELIM, i);
		}
		public TerminalNode END() { return getToken(STParser.END, 0); }
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public RegionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_region; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterRegion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitRegion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitRegion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegionContext region() throws RecognitionException {
		RegionContext _localctx = new RegionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_region);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(LDELIM);
			setState(81);
			match(AT);
			setState(82);
			match(ID);
			setState(83);
			match(RDELIM);
			setState(87);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(84);
					element();
					}
					} 
				}
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(90);
			match(LDELIM);
			setState(91);
			match(END);
			setState(92);
			match(RDELIM);
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

	public static class SubtemplateContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(STParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(STParser.RBRACE, 0); }
		public List<TerminalNode> ID() { return getTokens(STParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STParser.ID, i);
		}
		public TerminalNode PIPE() { return getToken(STParser.PIPE, 0); }
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STParser.COMMA, i);
		}
		public SubtemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subtemplate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterSubtemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitSubtemplate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitSubtemplate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubtemplateContext subtemplate() throws RecognitionException {
		SubtemplateContext _localctx = new SubtemplateContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_subtemplate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(LBRACE);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(95);
				match(ID);
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(96);
					match(COMMA);
					setState(97);
					match(ID);
					}
					}
					setState(102);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(103);
				match(PIPE);
				}
			}

			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LDELIM || _la==TEXT) {
				{
				{
				setState(106);
				element();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(112);
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

	public static class IfstatContext extends ParserRuleContext {
		public List<TerminalNode> LDELIM() { return getTokens(STParser.LDELIM); }
		public TerminalNode LDELIM(int i) {
			return getToken(STParser.LDELIM, i);
		}
		public TerminalNode IF() { return getToken(STParser.IF, 0); }
		public List<TerminalNode> LPAREN() { return getTokens(STParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(STParser.LPAREN, i);
		}
		public List<ConditionalContext> conditional() {
			return getRuleContexts(ConditionalContext.class);
		}
		public ConditionalContext conditional(int i) {
			return getRuleContext(ConditionalContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(STParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(STParser.RPAREN, i);
		}
		public List<TerminalNode> RDELIM() { return getTokens(STParser.RDELIM); }
		public TerminalNode RDELIM(int i) {
			return getToken(STParser.RDELIM, i);
		}
		public TerminalNode ENDIF() { return getToken(STParser.ENDIF, 0); }
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public List<TerminalNode> ELSEIF() { return getTokens(STParser.ELSEIF); }
		public TerminalNode ELSEIF(int i) {
			return getToken(STParser.ELSEIF, i);
		}
		public TerminalNode ELSE() { return getToken(STParser.ELSE, 0); }
		public IfstatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifstat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterIfstat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitIfstat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitIfstat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfstatContext ifstat() throws RecognitionException {
		IfstatContext _localctx = new IfstatContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ifstat);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(LDELIM);
			setState(115);
			match(IF);
			setState(116);
			match(LPAREN);
			setState(117);
			conditional();
			setState(118);
			match(RPAREN);
			setState(119);
			match(RDELIM);
			setState(123);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(120);
					element();
					}
					} 
				}
				setState(125);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(140);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(126);
					match(LDELIM);
					setState(127);
					match(ELSEIF);
					setState(128);
					match(LPAREN);
					setState(129);
					conditional();
					setState(130);
					match(RPAREN);
					setState(131);
					match(RDELIM);
					setState(135);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(132);
							element();
							}
							} 
						}
						setState(137);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
					}
					}
					} 
				}
				setState(142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(152);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(143);
				match(LDELIM);
				setState(144);
				match(ELSE);
				setState(145);
				match(RDELIM);
				setState(149);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(146);
						element();
						}
						} 
					}
					setState(151);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				}
				break;
			}
			setState(154);
			match(LDELIM);
			setState(155);
			match(ENDIF);
			setState(156);
			match(RDELIM);
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

	public static class ConditionalContext extends ParserRuleContext {
		public List<AndConditionalContext> andConditional() {
			return getRuleContexts(AndConditionalContext.class);
		}
		public AndConditionalContext andConditional(int i) {
			return getRuleContext(AndConditionalContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(STParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(STParser.OR, i);
		}
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_conditional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			andConditional();
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(159);
				match(OR);
				setState(160);
				andConditional();
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class AndConditionalContext extends ParserRuleContext {
		public List<NotConditionalContext> notConditional() {
			return getRuleContexts(NotConditionalContext.class);
		}
		public NotConditionalContext notConditional(int i) {
			return getRuleContext(NotConditionalContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(STParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(STParser.AND, i);
		}
		public AndConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andConditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterAndConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitAndConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitAndConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndConditionalContext andConditional() throws RecognitionException {
		AndConditionalContext _localctx = new AndConditionalContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_andConditional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			notConditional();
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(167);
				match(AND);
				setState(168);
				notConditional();
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class NotConditionalContext extends ParserRuleContext {
		public TerminalNode BANG() { return getToken(STParser.BANG, 0); }
		public NotConditionalContext notConditional() {
			return getRuleContext(NotConditionalContext.class,0);
		}
		public MemberExprContext memberExpr() {
			return getRuleContext(MemberExprContext.class,0);
		}
		public NotConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notConditional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterNotConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitNotConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitNotConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotConditionalContext notConditional() throws RecognitionException {
		NotConditionalContext _localctx = new NotConditionalContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_notConditional);
		try {
			setState(177);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BANG:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				match(BANG);
				setState(175);
				notConditional();
				}
				break;
			case LBRACE:
			case STRING:
			case SUPER:
			case TRUE:
			case FALSE:
			case AT:
			case LPAREN:
			case LBRACK:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(176);
				memberExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class NotConditionalExprContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(STParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STParser.ID, i);
		}
		public List<TerminalNode> DOT() { return getTokens(STParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(STParser.DOT, i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(STParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(STParser.LPAREN, i);
		}
		public List<MapExprContext> mapExpr() {
			return getRuleContexts(MapExprContext.class);
		}
		public MapExprContext mapExpr(int i) {
			return getRuleContext(MapExprContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(STParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(STParser.RPAREN, i);
		}
		public NotConditionalExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notConditionalExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterNotConditionalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitNotConditionalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitNotConditionalExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotConditionalExprContext notConditionalExpr() throws RecognitionException {
		NotConditionalExprContext _localctx = new NotConditionalExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_notConditionalExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(ID);
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				setState(187);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
				case 1:
					{
					setState(180);
					match(DOT);
					setState(181);
					match(ID);
					}
					break;
				case 2:
					{
					setState(182);
					match(DOT);
					setState(183);
					match(LPAREN);
					setState(184);
					mapExpr();
					setState(185);
					match(RPAREN);
					}
					break;
				}
				}
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ExprOptionsContext extends ParserRuleContext {
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STParser.COMMA, i);
		}
		public ExprOptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprOptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterExprOptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitExprOptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitExprOptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprOptionsContext exprOptions() throws RecognitionException {
		ExprOptionsContext _localctx = new ExprOptionsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_exprOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			option();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(193);
				match(COMMA);
				setState(194);
				option();
				}
				}
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class OptionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public TerminalNode EQUALS() { return getToken(STParser.EQUALS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_option);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(ID);
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUALS) {
				{
				setState(201);
				match(EQUALS);
				setState(202);
				expr();
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

	public static class ExprContext extends ParserRuleContext {
		public MemberExprContext memberExpr() {
			return getRuleContext(MemberExprContext.class,0);
		}
		public TerminalNode COLON() { return getToken(STParser.COLON, 0); }
		public MapTemplateRefContext mapTemplateRef() {
			return getRuleContext(MapTemplateRefContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			memberExpr();
			setState(208);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(206);
				match(COLON);
				setState(207);
				mapTemplateRef();
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

	public static class MapExprContext extends ParserRuleContext {
		public List<MemberExprContext> memberExpr() {
			return getRuleContexts(MemberExprContext.class);
		}
		public MemberExprContext memberExpr(int i) {
			return getRuleContext(MemberExprContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(STParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(STParser.COLON, i);
		}
		public List<MapTemplateRefContext> mapTemplateRef() {
			return getRuleContexts(MapTemplateRefContext.class);
		}
		public MapTemplateRefContext mapTemplateRef(int i) {
			return getRuleContext(MapTemplateRefContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STParser.COMMA, i);
		}
		public MapExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterMapExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitMapExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitMapExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapExprContext mapExpr() throws RecognitionException {
		MapExprContext _localctx = new MapExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_mapExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			memberExpr();
			setState(220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(213); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(211);
					match(COMMA);
					setState(212);
					memberExpr();
					}
					}
					setState(215); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==COMMA );
				setState(217);
				match(COLON);
				setState(218);
				mapTemplateRef();
				}
			}

			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLON) {
				{
				{
				setState(222);
				match(COLON);
				setState(223);
				mapTemplateRef();
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(224);
					match(COMMA);
					setState(225);
					mapTemplateRef();
					}
					}
					setState(230);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class MemberExprContext extends ParserRuleContext {
		public IncludeExprContext includeExpr() {
			return getRuleContext(IncludeExprContext.class,0);
		}
		public List<TerminalNode> DOT() { return getTokens(STParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(STParser.DOT, i);
		}
		public List<TerminalNode> ID() { return getTokens(STParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STParser.ID, i);
		}
		public List<TerminalNode> LPAREN() { return getTokens(STParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(STParser.LPAREN, i);
		}
		public List<MapExprContext> mapExpr() {
			return getRuleContexts(MapExprContext.class);
		}
		public MapExprContext mapExpr(int i) {
			return getRuleContext(MapExprContext.class,i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(STParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(STParser.RPAREN, i);
		}
		public MemberExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterMemberExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitMemberExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitMemberExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberExprContext memberExpr() throws RecognitionException {
		MemberExprContext _localctx = new MemberExprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_memberExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			includeExpr();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				setState(244);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(237);
					match(DOT);
					setState(238);
					match(ID);
					}
					break;
				case 2:
					{
					setState(239);
					match(DOT);
					setState(240);
					match(LPAREN);
					setState(241);
					mapExpr();
					setState(242);
					match(RPAREN);
					}
					break;
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class MapTemplateRefContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public List<TerminalNode> LPAREN() { return getTokens(STParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(STParser.LPAREN, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(STParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(STParser.RPAREN, i);
		}
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public SubtemplateContext subtemplate() {
			return getRuleContext(SubtemplateContext.class,0);
		}
		public MapExprContext mapExpr() {
			return getRuleContext(MapExprContext.class,0);
		}
		public ArgExprListContext argExprList() {
			return getRuleContext(ArgExprListContext.class,0);
		}
		public MapTemplateRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapTemplateRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterMapTemplateRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitMapTemplateRef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitMapTemplateRef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MapTemplateRefContext mapTemplateRef() throws RecognitionException {
		MapTemplateRefContext _localctx = new MapTemplateRefContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_mapTemplateRef);
		int _la;
		try {
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				match(ID);
				setState(250);
				match(LPAREN);
				setState(252);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << ELLIPSIS) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
					{
					setState(251);
					args();
					}
				}

				setState(254);
				match(RPAREN);
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(255);
				subtemplate();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(256);
				match(LPAREN);
				setState(257);
				mapExpr();
				setState(258);
				match(RPAREN);
				setState(259);
				match(LPAREN);
				setState(261);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
					{
					setState(260);
					argExprList();
					}
				}

				setState(263);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class IncludeExprContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(STParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(STParser.RPAREN, 0); }
		public MapExprContext mapExpr() {
			return getRuleContext(MapExprContext.class,0);
		}
		public TerminalNode SUPER() { return getToken(STParser.SUPER, 0); }
		public TerminalNode DOT() { return getToken(STParser.DOT, 0); }
		public ArgsContext args() {
			return getRuleContext(ArgsContext.class,0);
		}
		public TerminalNode AT() { return getToken(STParser.AT, 0); }
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public IncludeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includeExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterIncludeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitIncludeExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitIncludeExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncludeExprContext includeExpr() throws RecognitionException {
		IncludeExprContext _localctx = new IncludeExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_includeExpr);
		int _la;
		try {
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				match(ID);
				setState(268);
				match(LPAREN);
				setState(270);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
					{
					setState(269);
					mapExpr();
					}
				}

				setState(272);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(273);
				match(SUPER);
				setState(274);
				match(DOT);
				setState(275);
				match(ID);
				setState(276);
				match(LPAREN);
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << ELLIPSIS) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
					{
					setState(277);
					args();
					}
				}

				setState(280);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(281);
				match(ID);
				setState(282);
				match(LPAREN);
				setState(284);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << ELLIPSIS) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
					{
					setState(283);
					args();
					}
				}

				setState(286);
				match(RPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(287);
				match(AT);
				setState(288);
				match(SUPER);
				setState(289);
				match(DOT);
				setState(290);
				match(ID);
				setState(291);
				match(LPAREN);
				setState(292);
				match(RPAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(293);
				match(AT);
				setState(294);
				match(ID);
				setState(295);
				match(LPAREN);
				setState(296);
				match(RPAREN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(297);
				primary();
				}
				break;
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

	public static class PrimaryContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public TerminalNode STRING() { return getToken(STParser.STRING, 0); }
		public TerminalNode TRUE() { return getToken(STParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(STParser.FALSE, 0); }
		public SubtemplateContext subtemplate() {
			return getRuleContext(SubtemplateContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public List<TerminalNode> LPAREN() { return getTokens(STParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(STParser.LPAREN, i);
		}
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public List<TerminalNode> RPAREN() { return getTokens(STParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(STParser.RPAREN, i);
		}
		public MapExprContext mapExpr() {
			return getRuleContext(MapExprContext.class,0);
		}
		public ArgExprListContext argExprList() {
			return getRuleContext(ArgExprListContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_primary);
		int _la;
		try {
			setState(320);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				match(STRING);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(302);
				match(TRUE);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(303);
				match(FALSE);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(304);
				subtemplate();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(305);
				list();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(306);
				match(LPAREN);
				setState(307);
				conditional();
				setState(308);
				match(RPAREN);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(310);
				match(LPAREN);
				setState(311);
				mapExpr();
				setState(312);
				match(RPAREN);
				setState(318);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(313);
					match(LPAREN);
					setState(315);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
						{
						setState(314);
						argExprList();
						}
					}

					setState(317);
					match(RPAREN);
					}
				}

				}
				break;
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

	public static class ListContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(STParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(STParser.RBRACK, 0); }
		public ArgExprListContext argExprList() {
			return getRuleContext(ArgExprListContext.class,0);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			match(LBRACK);
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LBRACE) | (1L << STRING) | (1L << SUPER) | (1L << TRUE) | (1L << FALSE) | (1L << AT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ID))) != 0)) {
				{
				setState(323);
				argExprList();
				}
			}

			setState(326);
			match(RBRACK);
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

	public static class ArgsContext extends ParserRuleContext {
		public ArgExprListContext argExprList() {
			return getRuleContext(ArgExprListContext.class,0);
		}
		public List<NamedArgContext> namedArg() {
			return getRuleContexts(NamedArgContext.class);
		}
		public NamedArgContext namedArg(int i) {
			return getRuleContext(NamedArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STParser.COMMA, i);
		}
		public TerminalNode ELLIPSIS() { return getToken(STParser.ELLIPSIS, 0); }
		public ArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsContext args() throws RecognitionException {
		ArgsContext _localctx = new ArgsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_args);
		int _la;
		try {
			int _alt;
			setState(342);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				argExprList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(329);
				namedArg();
				setState(334);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(330);
						match(COMMA);
						setState(331);
						namedArg();
						}
						} 
					}
					setState(336);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				}
				setState(339);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(337);
					match(COMMA);
					setState(338);
					match(ELLIPSIS);
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(341);
				match(ELLIPSIS);
				}
				break;
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

	public static class ArgExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STParser.COMMA, i);
		}
		public ArgExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argExprList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterArgExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitArgExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitArgExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgExprListContext argExprList() throws RecognitionException {
		ArgExprListContext _localctx = new ArgExprListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_argExprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			expr();
			setState(349);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(345);
				match(COMMA);
				setState(346);
				expr();
				}
				}
				setState(351);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class NamedArgContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STParser.ID, 0); }
		public TerminalNode EQUALS() { return getToken(STParser.EQUALS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NamedArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).enterNamedArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STParserListener ) ((STParserListener)listener).exitNamedArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STParserVisitor ) return ((STParserVisitor<? extends T>)visitor).visitNamedArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedArgContext namedArg() throws RecognitionException {
		NamedArgContext _localctx = new NamedArgContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_namedArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			match(ID);
			setState(353);
			match(EQUALS);
			setState(354);
			expr();
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u0167\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\7\2\64\n\2\f\2\16\2\67\13\2\3\2\3\2\3\3\3\3\5\3=\n\3\3\4\3\4\6\4"+
		"A\n\4\r\4\16\4B\5\4E\n\4\3\5\3\5\5\5I\n\5\3\6\3\6\3\6\3\6\5\6O\n\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\7\7X\n\7\f\7\16\7[\13\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\7\be\n\b\f\b\16\bh\13\b\3\b\5\bk\n\b\3\b\7\bn\n\b\f\b\16\b"+
		"q\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t|\n\t\f\t\16\t\177\13\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0088\n\t\f\t\16\t\u008b\13\t\7\t\u008d"+
		"\n\t\f\t\16\t\u0090\13\t\3\t\3\t\3\t\3\t\7\t\u0096\n\t\f\t\16\t\u0099"+
		"\13\t\5\t\u009b\n\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\7\n\u00a4\n\n\f\n\16\n"+
		"\u00a7\13\n\3\13\3\13\3\13\7\13\u00ac\n\13\f\13\16\13\u00af\13\13\3\f"+
		"\3\f\3\f\5\f\u00b4\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00be\n\r\f"+
		"\r\16\r\u00c1\13\r\3\16\3\16\3\16\7\16\u00c6\n\16\f\16\16\16\u00c9\13"+
		"\16\3\17\3\17\3\17\5\17\u00ce\n\17\3\20\3\20\3\20\5\20\u00d3\n\20\3\21"+
		"\3\21\3\21\6\21\u00d8\n\21\r\21\16\21\u00d9\3\21\3\21\3\21\5\21\u00df"+
		"\n\21\3\21\3\21\3\21\3\21\7\21\u00e5\n\21\f\21\16\21\u00e8\13\21\7\21"+
		"\u00ea\n\21\f\21\16\21\u00ed\13\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\7\22\u00f7\n\22\f\22\16\22\u00fa\13\22\3\23\3\23\3\23\5\23\u00ff"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0108\n\23\3\23\3\23\5\23"+
		"\u010c\n\23\3\24\3\24\3\24\5\24\u0111\n\24\3\24\3\24\3\24\3\24\3\24\3"+
		"\24\5\24\u0119\n\24\3\24\3\24\3\24\3\24\5\24\u011f\n\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u012d\n\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u013e\n\25\3\25\5\25\u0141\n\25\5\25\u0143\n\25\3\26\3\26\5\26\u0147"+
		"\n\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u014f\n\27\f\27\16\27\u0152\13"+
		"\27\3\27\3\27\5\27\u0156\n\27\3\27\5\27\u0159\n\27\3\30\3\30\3\30\7\30"+
		"\u015e\n\30\f\30\16\30\u0161\13\30\3\31\3\31\3\31\3\31\3\31\2\2\32\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\2\2\u0186\2\65\3\2\2"+
		"\2\4<\3\2\2\2\6D\3\2\2\2\bH\3\2\2\2\nJ\3\2\2\2\fR\3\2\2\2\16`\3\2\2\2"+
		"\20t\3\2\2\2\22\u00a0\3\2\2\2\24\u00a8\3\2\2\2\26\u00b3\3\2\2\2\30\u00b5"+
		"\3\2\2\2\32\u00c2\3\2\2\2\34\u00ca\3\2\2\2\36\u00cf\3\2\2\2 \u00d4\3\2"+
		"\2\2\"\u00ee\3\2\2\2$\u010b\3\2\2\2&\u012c\3\2\2\2(\u0142\3\2\2\2*\u0144"+
		"\3\2\2\2,\u0158\3\2\2\2.\u015a\3\2\2\2\60\u0162\3\2\2\2\62\64\5\4\3\2"+
		"\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\668\3\2\2\2\67"+
		"\65\3\2\2\289\7\2\2\39\3\3\2\2\2:=\5\6\4\2;=\5\b\5\2<:\3\2\2\2<;\3\2\2"+
		"\2=\5\3\2\2\2>E\5\n\6\2?A\7\17\2\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2"+
		"\2\2CE\3\2\2\2D>\3\2\2\2D@\3\2\2\2E\7\3\2\2\2FI\5\20\t\2GI\5\f\7\2HF\3"+
		"\2\2\2HG\3\2\2\2I\t\3\2\2\2JK\7\r\2\2KN\5 \21\2LM\7\35\2\2MO\5\32\16\2"+
		"NL\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\7\4\2\2Q\13\3\2\2\2RS\7\r\2\2ST\7\31"+
		"\2\2TU\7\'\2\2UY\7\4\2\2VX\5\4\3\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2"+
		"\2\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7\r\2\2]^\7\26\2\2^_\7\4\2\2_\r\3\2\2\2`"+
		"j\7\3\2\2af\7\'\2\2bc\7\5\2\2ce\7\'\2\2db\3\2\2\2eh\3\2\2\2fd\3\2\2\2"+
		"fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2ik\7(\2\2ja\3\2\2\2jk\3\2\2\2ko\3\2\2\2"+
		"ln\5\4\3\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2\2"+
		"rs\7\16\2\2s\17\3\2\2\2tu\7\r\2\2uv\7\21\2\2vw\7 \2\2wx\5\22\n\2xy\7!"+
		"\2\2y}\7\4\2\2z|\5\4\3\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u008e"+
		"\3\2\2\2\177}\3\2\2\2\u0080\u0081\7\r\2\2\u0081\u0082\7\22\2\2\u0082\u0083"+
		"\7 \2\2\u0083\u0084\5\22\n\2\u0084\u0085\7!\2\2\u0085\u0089\7\4\2\2\u0086"+
		"\u0088\5\4\3\2\u0087\u0086\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2"+
		"\2\2\u0089\u008a\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008c"+
		"\u0080\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2"+
		"\2\2\u008f\u009a\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092\7\r\2\2\u0092"+
		"\u0093\7\23\2\2\u0093\u0097\7\4\2\2\u0094\u0096\5\4\3\2\u0095\u0094\3"+
		"\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u0091\3\2\2\2\u009a\u009b\3\2"+
		"\2\2\u009b\u009c\3\2\2\2\u009c\u009d\7\r\2\2\u009d\u009e\7\24\2\2\u009e"+
		"\u009f\7\4\2\2\u009f\21\3\2\2\2\u00a0\u00a5\5\24\13\2\u00a1\u00a2\7\37"+
		"\2\2\u00a2\u00a4\5\24\13\2\u00a3\u00a1\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5"+
		"\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00a5\3\2\2"+
		"\2\u00a8\u00ad\5\26\f\2\u00a9\u00aa\7\36\2\2\u00aa\u00ac\5\26\f\2\u00ab"+
		"\u00a9\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2"+
		"\2\2\u00ae\25\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\7%\2\2\u00b1\u00b4"+
		"\5\26\f\2\u00b2\u00b4\5\"\22\2\u00b3\u00b0\3\2\2\2\u00b3\u00b2\3\2\2\2"+
		"\u00b4\27\3\2\2\2\u00b5\u00bf\7\'\2\2\u00b6\u00b7\7\33\2\2\u00b7\u00be"+
		"\7\'\2\2\u00b8\u00b9\7\33\2\2\u00b9\u00ba\7 \2\2\u00ba\u00bb\5 \21\2\u00bb"+
		"\u00bc\7!\2\2\u00bc\u00be\3\2\2\2\u00bd\u00b6\3\2\2\2\u00bd\u00b8\3\2"+
		"\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\31\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c7\5\34\17\2\u00c3\u00c4\7\5"+
		"\2\2\u00c4\u00c6\5\34\17\2\u00c5\u00c3\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7"+
		"\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\33\3\2\2\2\u00c9\u00c7\3\2\2"+
		"\2\u00ca\u00cd\7\'\2\2\u00cb\u00cc\7$\2\2\u00cc\u00ce\5\36\20\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\35\3\2\2\2\u00cf\u00d2\5\"\22"+
		"\2\u00d0\u00d1\7\34\2\2\u00d1\u00d3\5$\23\2\u00d2\u00d0\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\37\3\2\2\2\u00d4\u00de\5\"\22\2\u00d5\u00d6\7\5\2"+
		"\2\u00d6\u00d8\5\"\22\2\u00d7\u00d5\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\7\34"+
		"\2\2\u00dc\u00dd\5$\23\2\u00dd\u00df\3\2\2\2\u00de\u00d7\3\2\2\2\u00de"+
		"\u00df\3\2\2\2\u00df\u00eb\3\2\2\2\u00e0\u00e1\7\34\2\2\u00e1\u00e6\5"+
		"$\23\2\u00e2\u00e3\7\5\2\2\u00e3\u00e5\5$\23\2\u00e4\u00e2\3\2\2\2\u00e5"+
		"\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00ea\3\2"+
		"\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00e0\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb"+
		"\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec!\3\2\2\2\u00ed\u00eb\3\2\2\2"+
		"\u00ee\u00f8\5&\24\2\u00ef\u00f0\7\33\2\2\u00f0\u00f7\7\'\2\2\u00f1\u00f2"+
		"\7\33\2\2\u00f2\u00f3\7 \2\2\u00f3\u00f4\5 \21\2\u00f4\u00f5\7!\2\2\u00f5"+
		"\u00f7\3\2\2\2\u00f6\u00ef\3\2\2\2\u00f6\u00f1\3\2\2\2\u00f7\u00fa\3\2"+
		"\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9#\3\2\2\2\u00fa\u00f8"+
		"\3\2\2\2\u00fb\u00fc\7\'\2\2\u00fc\u00fe\7 \2\2\u00fd\u00ff\5,\27\2\u00fe"+
		"\u00fd\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u010c\7!"+
		"\2\2\u0101\u010c\5\16\b\2\u0102\u0103\7 \2\2\u0103\u0104\5 \21\2\u0104"+
		"\u0105\7!\2\2\u0105\u0107\7 \2\2\u0106\u0108\5.\30\2\u0107\u0106\3\2\2"+
		"\2\u0107\u0108\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010a\7!\2\2\u010a\u010c"+
		"\3\2\2\2\u010b\u00fb\3\2\2\2\u010b\u0101\3\2\2\2\u010b\u0102\3\2\2\2\u010c"+
		"%\3\2\2\2\u010d\u010e\7\'\2\2\u010e\u0110\7 \2\2\u010f\u0111\5 \21\2\u0110"+
		"\u010f\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u012d\7!"+
		"\2\2\u0113\u0114\7\25\2\2\u0114\u0115\7\33\2\2\u0115\u0116\7\'\2\2\u0116"+
		"\u0118\7 \2\2\u0117\u0119\5,\27\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2"+
		"\2\2\u0119\u011a\3\2\2\2\u011a\u012d\7!\2\2\u011b\u011c\7\'\2\2\u011c"+
		"\u011e\7 \2\2\u011d\u011f\5,\27\2\u011e\u011d\3\2\2\2\u011e\u011f\3\2"+
		"\2\2\u011f\u0120\3\2\2\2\u0120\u012d\7!\2\2\u0121\u0122\7\31\2\2\u0122"+
		"\u0123\7\25\2\2\u0123\u0124\7\33\2\2\u0124\u0125\7\'\2\2\u0125\u0126\7"+
		" \2\2\u0126\u012d\7!\2\2\u0127\u0128\7\31\2\2\u0128\u0129\7\'\2\2\u0129"+
		"\u012a\7 \2\2\u012a\u012d\7!\2\2\u012b\u012d\5(\25\2\u012c\u010d\3\2\2"+
		"\2\u012c\u0113\3\2\2\2\u012c\u011b\3\2\2\2\u012c\u0121\3\2\2\2\u012c\u0127"+
		"\3\2\2\2\u012c\u012b\3\2\2\2\u012d\'\3\2\2\2\u012e\u0143\7\'\2\2\u012f"+
		"\u0143\7\20\2\2\u0130\u0143\7\27\2\2\u0131\u0143\7\30\2\2\u0132\u0143"+
		"\5\16\b\2\u0133\u0143\5*\26\2\u0134\u0135\7 \2\2\u0135\u0136\5\22\n\2"+
		"\u0136\u0137\7!\2\2\u0137\u0143\3\2\2\2\u0138\u0139\7 \2\2\u0139\u013a"+
		"\5 \21\2\u013a\u0140\7!\2\2\u013b\u013d\7 \2\2\u013c\u013e\5.\30\2\u013d"+
		"\u013c\3\2\2\2\u013d\u013e\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0141\7!"+
		"\2\2\u0140\u013b\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0143\3\2\2\2\u0142"+
		"\u012e\3\2\2\2\u0142\u012f\3\2\2\2\u0142\u0130\3\2\2\2\u0142\u0131\3\2"+
		"\2\2\u0142\u0132\3\2\2\2\u0142\u0133\3\2\2\2\u0142\u0134\3\2\2\2\u0142"+
		"\u0138\3\2\2\2\u0143)\3\2\2\2\u0144\u0146\7\"\2\2\u0145\u0147\5.\30\2"+
		"\u0146\u0145\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u0149"+
		"\7#\2\2\u0149+\3\2\2\2\u014a\u0159\5.\30\2\u014b\u0150\5\60\31\2\u014c"+
		"\u014d\7\5\2\2\u014d\u014f\5\60\31\2\u014e\u014c\3\2\2\2\u014f\u0152\3"+
		"\2\2\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0155\3\2\2\2\u0152"+
		"\u0150\3\2\2\2\u0153\u0154\7\5\2\2\u0154\u0156\7\32\2\2\u0155\u0153\3"+
		"\2\2\2\u0155\u0156\3\2\2\2\u0156\u0159\3\2\2\2\u0157\u0159\7\32\2\2\u0158"+
		"\u014a\3\2\2\2\u0158\u014b\3\2\2\2\u0158\u0157\3\2\2\2\u0159-\3\2\2\2"+
		"\u015a\u015f\5\36\20\2\u015b\u015c\7\5\2\2\u015c\u015e\5\36\20\2\u015d"+
		"\u015b\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u015d\3\2\2\2\u015f\u0160\3\2"+
		"\2\2\u0160/\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0163\7\'\2\2\u0163\u0164"+
		"\7$\2\2\u0164\u0165\5\36\20\2\u0165\61\3\2\2\2.\65<BDHNYfjo}\u0089\u008e"+
		"\u0097\u009a\u00a5\u00ad\u00b3\u00bd\u00bf\u00c7\u00cd\u00d2\u00d9\u00de"+
		"\u00e6\u00eb\u00f6\u00f8\u00fe\u0107\u010b\u0110\u0118\u011e\u012c\u013d"+
		"\u0140\u0142\u0146\u0150\u0155\u0158\u015f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}