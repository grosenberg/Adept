// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STGParser.g4 by ANTLR 4.7.2

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
public class STGParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DOC_COMMENT=1, BLOCK_COMMENT=2, LINE_COMMENT=3, TMPL_COMMENT=4, HORZ_WS=5, 
		VERT_WS=6, STRING=7, BIGSTRING=8, BIGSTRING_NO_NL=9, ANON_TEMPLATE=10, 
		ASSIGN=11, EQUAL=12, DOT=13, COMMA=14, SEMI=15, COLON=16, LPAREN=17, RPAREN=18, 
		LBRACK=19, RBRACK=20, AT=21, TRUE=22, FALSE=23, GROUP=24, DELIMITERS=25, 
		IMPORT=26, ID=27;
	public static final int
		RULE_group = 0, RULE_groupSpec = 1, RULE_delimiters = 2, RULE_imports = 3, 
		RULE_importSpec = 4, RULE_template = 5, RULE_formalArgList = 6, RULE_commaArg = 7, 
		RULE_formalArg = 8, RULE_dict = 9, RULE_dictPairs = 10, RULE_keyValuePair = 11, 
		RULE_defaultValuePair = 12, RULE_keyValue = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"group", "groupSpec", "delimiters", "imports", "importSpec", "template", 
			"formalArgList", "commaArg", "formalArg", "dict", "dictPairs", "keyValuePair", 
			"defaultValuePair", "keyValue"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DOC_COMMENT", "BLOCK_COMMENT", "LINE_COMMENT", "TMPL_COMMENT", 
			"HORZ_WS", "VERT_WS", "STRING", "BIGSTRING", "BIGSTRING_NO_NL", "ANON_TEMPLATE", 
			"ASSIGN", "EQUAL", "DOT", "COMMA", "SEMI", "COLON", "LPAREN", "RPAREN", 
			"LBRACK", "RBRACK", "AT", "TRUE", "FALSE", "GROUP", "DELIMITERS", "IMPORT", 
			"ID"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "STGParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STGParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GroupContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(STGParser.EOF, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public DelimitersContext delimiters() {
			return getRuleContext(DelimitersContext.class,0);
		}
		public ImportsContext imports() {
			return getRuleContext(ImportsContext.class,0);
		}
		public List<TemplateContext> template() {
			return getRuleContexts(TemplateContext.class);
		}
		public TemplateContext template(int i) {
			return getRuleContext(TemplateContext.class,i);
		}
		public List<DictContext> dict() {
			return getRuleContexts(DictContext.class);
		}
		public DictContext dict(int i) {
			return getRuleContext(DictContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitGroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GROUP) {
				{
				setState(28);
				groupSpec();
				}
			}

			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DELIMITERS) {
				{
				setState(31);
				delimiters();
				}
			}

			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORT) {
				{
				setState(34);
				imports();
				}
			}

			setState(39); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(39);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(37);
					template();
					}
					break;
				case 2:
					{
					setState(38);
					dict();
					}
					break;
				}
				}
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==AT || _la==ID );
			setState(43);
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

	public static class GroupSpecContext extends ParserRuleContext {
		public TerminalNode GROUP() { return getToken(STGParser.GROUP, 0); }
		public TerminalNode ID() { return getToken(STGParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(STGParser.SEMI, 0); }
		public GroupSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterGroupSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitGroupSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitGroupSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupSpecContext groupSpec() throws RecognitionException {
		GroupSpecContext _localctx = new GroupSpecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_groupSpec);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(GROUP);
			setState(46);
			match(ID);
			setState(50);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(47);
					matchWildcard();
					}
					} 
				}
				setState(52);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(53);
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

	public static class DelimitersContext extends ParserRuleContext {
		public Token beg;
		public Token end;
		public TerminalNode DELIMITERS() { return getToken(STGParser.DELIMITERS, 0); }
		public TerminalNode COMMA() { return getToken(STGParser.COMMA, 0); }
		public List<TerminalNode> STRING() { return getTokens(STGParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(STGParser.STRING, i);
		}
		public DelimitersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delimiters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterDelimiters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitDelimiters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitDelimiters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelimitersContext delimiters() throws RecognitionException {
		DelimitersContext _localctx = new DelimitersContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_delimiters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(DELIMITERS);
			setState(56);
			((DelimitersContext)_localctx).beg = match(STRING);
			setState(57);
			match(COMMA);
			setState(58);
			((DelimitersContext)_localctx).end = match(STRING);
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

	public static class ImportsContext extends ParserRuleContext {
		public List<ImportSpecContext> importSpec() {
			return getRuleContexts(ImportSpecContext.class);
		}
		public ImportSpecContext importSpec(int i) {
			return getRuleContext(ImportSpecContext.class,i);
		}
		public ImportsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_imports; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterImports(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitImports(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitImports(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportsContext imports() throws RecognitionException {
		ImportsContext _localctx = new ImportsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_imports);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(60);
				importSpec();
				}
				}
				setState(63); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IMPORT );
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

	public static class ImportSpecContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(STGParser.IMPORT, 0); }
		public TerminalNode STRING() { return getToken(STGParser.STRING, 0); }
		public ImportSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterImportSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitImportSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitImportSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportSpecContext importSpec() throws RecognitionException {
		ImportSpecContext _localctx = new ImportSpecContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_importSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(IMPORT);
			setState(66);
			match(STRING);
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

	public static class TemplateContext extends ParserRuleContext {
		public Token encl;
		public Token name;
		public Token alias;
		public Token target;
		public TerminalNode ASSIGN() { return getToken(STGParser.ASSIGN, 0); }
		public TerminalNode STRING() { return getToken(STGParser.STRING, 0); }
		public TerminalNode BIGSTRING() { return getToken(STGParser.BIGSTRING, 0); }
		public TerminalNode BIGSTRING_NO_NL() { return getToken(STGParser.BIGSTRING_NO_NL, 0); }
		public TerminalNode AT() { return getToken(STGParser.AT, 0); }
		public TerminalNode DOT() { return getToken(STGParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(STGParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(STGParser.RPAREN, 0); }
		public List<TerminalNode> ID() { return getTokens(STGParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(STGParser.ID, i);
		}
		public FormalArgListContext formalArgList() {
			return getRuleContext(FormalArgListContext.class,0);
		}
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitTemplate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitTemplate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_template);
		int _la;
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case AT:
					{
					setState(68);
					match(AT);
					setState(69);
					((TemplateContext)_localctx).encl = match(ID);
					setState(70);
					match(DOT);
					setState(71);
					((TemplateContext)_localctx).name = match(ID);
					setState(72);
					match(LPAREN);
					setState(73);
					match(RPAREN);
					}
					break;
				case ID:
					{
					setState(74);
					((TemplateContext)_localctx).name = match(ID);
					setState(75);
					match(LPAREN);
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ID) {
						{
						setState(76);
						formalArgList();
						}
					}

					setState(79);
					match(RPAREN);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(82);
				match(ASSIGN);
				setState(83);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << BIGSTRING) | (1L << BIGSTRING_NO_NL))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				((TemplateContext)_localctx).alias = match(ID);
				setState(85);
				match(ASSIGN);
				setState(86);
				((TemplateContext)_localctx).target = match(ID);
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

	public static class FormalArgListContext extends ParserRuleContext {
		public FormalArgContext formalArg() {
			return getRuleContext(FormalArgContext.class,0);
		}
		public List<CommaArgContext> commaArg() {
			return getRuleContexts(CommaArgContext.class);
		}
		public CommaArgContext commaArg(int i) {
			return getRuleContext(CommaArgContext.class,i);
		}
		public FormalArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalArgList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterFormalArgList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitFormalArgList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitFormalArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalArgListContext formalArgList() throws RecognitionException {
		FormalArgListContext _localctx = new FormalArgListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_formalArgList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			formalArg();
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(90);
				commaArg();
				}
				}
				setState(95);
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

	public static class CommaArgContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(STGParser.COMMA, 0); }
		public FormalArgContext formalArg() {
			return getRuleContext(FormalArgContext.class,0);
		}
		public CommaArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commaArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterCommaArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitCommaArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitCommaArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommaArgContext commaArg() throws RecognitionException {
		CommaArgContext _localctx = new CommaArgContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_commaArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			match(COMMA);
			setState(97);
			formalArg();
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

	public static class FormalArgContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STGParser.ID, 0); }
		public TerminalNode EQUAL() { return getToken(STGParser.EQUAL, 0); }
		public TerminalNode STRING() { return getToken(STGParser.STRING, 0); }
		public TerminalNode ANON_TEMPLATE() { return getToken(STGParser.ANON_TEMPLATE, 0); }
		public TerminalNode TRUE() { return getToken(STGParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(STGParser.FALSE, 0); }
		public TerminalNode LBRACK() { return getToken(STGParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(STGParser.RBRACK, 0); }
		public FormalArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterFormalArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitFormalArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitFormalArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalArgContext formalArg() throws RecognitionException {
		FormalArgContext _localctx = new FormalArgContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_formalArg);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(ID);
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQUAL) {
				{
				setState(100);
				match(EQUAL);
				setState(107);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case STRING:
					{
					setState(101);
					match(STRING);
					}
					break;
				case ANON_TEMPLATE:
					{
					setState(102);
					match(ANON_TEMPLATE);
					}
					break;
				case TRUE:
					{
					setState(103);
					match(TRUE);
					}
					break;
				case FALSE:
					{
					setState(104);
					match(FALSE);
					}
					break;
				case LBRACK:
					{
					setState(105);
					match(LBRACK);
					setState(106);
					match(RBRACK);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
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

	public static class DictContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(STGParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(STGParser.ASSIGN, 0); }
		public TerminalNode LBRACK() { return getToken(STGParser.LBRACK, 0); }
		public DictPairsContext dictPairs() {
			return getRuleContext(DictPairsContext.class,0);
		}
		public TerminalNode RBRACK() { return getToken(STGParser.RBRACK, 0); }
		public DictContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterDict(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitDict(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitDict(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictContext dict() throws RecognitionException {
		DictContext _localctx = new DictContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_dict);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(ID);
			setState(112);
			match(ASSIGN);
			setState(113);
			match(LBRACK);
			setState(114);
			dictPairs();
			setState(115);
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

	public static class DictPairsContext extends ParserRuleContext {
		public List<KeyValuePairContext> keyValuePair() {
			return getRuleContexts(KeyValuePairContext.class);
		}
		public KeyValuePairContext keyValuePair(int i) {
			return getRuleContext(KeyValuePairContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(STGParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(STGParser.COMMA, i);
		}
		public DefaultValuePairContext defaultValuePair() {
			return getRuleContext(DefaultValuePairContext.class,0);
		}
		public DictPairsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictPairs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterDictPairs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitDictPairs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitDictPairs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictPairsContext dictPairs() throws RecognitionException {
		DictPairsContext _localctx = new DictPairsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_dictPairs);
		int _la;
		try {
			int _alt;
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				keyValuePair();
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(118);
						match(COMMA);
						setState(119);
						keyValuePair();
						}
						} 
					}
					setState(124);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(125);
					match(COMMA);
					setState(126);
					defaultValuePair();
					}
				}

				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				defaultValuePair();
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

	public static class KeyValuePairContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(STGParser.STRING, 0); }
		public TerminalNode COLON() { return getToken(STGParser.COLON, 0); }
		public KeyValueContext keyValue() {
			return getRuleContext(KeyValueContext.class,0);
		}
		public KeyValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterKeyValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitKeyValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitKeyValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyValuePairContext keyValuePair() throws RecognitionException {
		KeyValuePairContext _localctx = new KeyValuePairContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_keyValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(STRING);
			setState(133);
			match(COLON);
			setState(134);
			keyValue();
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

	public static class DefaultValuePairContext extends ParserRuleContext {
		public Token ID;
		public TerminalNode ID() { return getToken(STGParser.ID, 0); }
		public TerminalNode COLON() { return getToken(STGParser.COLON, 0); }
		public KeyValueContext keyValue() {
			return getRuleContext(KeyValueContext.class,0);
		}
		public DefaultValuePairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultValuePair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterDefaultValuePair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitDefaultValuePair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitDefaultValuePair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultValuePairContext defaultValuePair() throws RecognitionException {
		DefaultValuePairContext _localctx = new DefaultValuePairContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_defaultValuePair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			((DefaultValuePairContext)_localctx).ID = match(ID);
			setState(137);
			if (!( "default".equals((((DefaultValuePairContext)_localctx).ID!=null?((DefaultValuePairContext)_localctx).ID.getText():null)) )) throw new FailedPredicateException(this, " \"default\".equals($ID.text) ");
			setState(138);
			match(COLON);
			setState(139);
			keyValue();
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

	public static class KeyValueContext extends ParserRuleContext {
		public Token ID;
		public TerminalNode BIGSTRING() { return getToken(STGParser.BIGSTRING, 0); }
		public TerminalNode BIGSTRING_NO_NL() { return getToken(STGParser.BIGSTRING_NO_NL, 0); }
		public TerminalNode ANON_TEMPLATE() { return getToken(STGParser.ANON_TEMPLATE, 0); }
		public TerminalNode STRING() { return getToken(STGParser.STRING, 0); }
		public TerminalNode TRUE() { return getToken(STGParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(STGParser.FALSE, 0); }
		public TerminalNode LBRACK() { return getToken(STGParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(STGParser.RBRACK, 0); }
		public TerminalNode ID() { return getToken(STGParser.ID, 0); }
		public KeyValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).enterKeyValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STGParserListener ) ((STGParserListener)listener).exitKeyValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STGParserVisitor ) return ((STGParserVisitor<? extends T>)visitor).visitKeyValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyValueContext keyValue() throws RecognitionException {
		KeyValueContext _localctx = new KeyValueContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_keyValue);
		try {
			setState(151);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BIGSTRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				match(BIGSTRING);
				}
				break;
			case BIGSTRING_NO_NL:
				enterOuterAlt(_localctx, 2);
				{
				setState(142);
				match(BIGSTRING_NO_NL);
				}
				break;
			case ANON_TEMPLATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(143);
				match(ANON_TEMPLATE);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(144);
				match(STRING);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 5);
				{
				setState(145);
				match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 6);
				{
				setState(146);
				match(FALSE);
				}
				break;
			case LBRACK:
				enterOuterAlt(_localctx, 7);
				{
				setState(147);
				match(LBRACK);
				setState(148);
				match(RBRACK);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 8);
				{
				setState(149);
				((KeyValueContext)_localctx).ID = match(ID);
				setState(150);
				if (!( "key".equals((((KeyValueContext)_localctx).ID!=null?((KeyValueContext)_localctx).ID.getText():null)) )) throw new FailedPredicateException(this, " \"key\".equals($ID.text) ");
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 12:
			return defaultValuePair_sempred((DefaultValuePairContext)_localctx, predIndex);
		case 13:
			return keyValue_sempred((KeyValueContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean defaultValuePair_sempred(DefaultValuePairContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  "default".equals((((DefaultValuePairContext)_localctx).ID!=null?((DefaultValuePairContext)_localctx).ID.getText():null)) ;
		}
		return true;
	}
	private boolean keyValue_sempred(KeyValueContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return  "key".equals((((KeyValueContext)_localctx).ID!=null?((KeyValueContext)_localctx).ID.getText():null)) ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u009c\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\5\2 \n\2\3\2\5\2#\n\2\3"+
		"\2\5\2&\n\2\3\2\3\2\6\2*\n\2\r\2\16\2+\3\2\3\2\3\3\3\3\3\3\7\3\63\n\3"+
		"\f\3\16\3\66\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\6\5@\n\5\r\5\16\5A\3"+
		"\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7P\n\7\3\7\5\7S\n\7\3"+
		"\7\3\7\3\7\3\7\3\7\5\7Z\n\7\3\b\3\b\7\b^\n\b\f\b\16\ba\13\b\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\nn\n\n\5\np\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\7\f{\n\f\f\f\16\f~\13\f\3\f\3\f\5\f\u0082\n"+
		"\f\3\f\5\f\u0085\n\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u009a\n\17\3\17\3\64\2\20"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\3\3\2\t\13\2\u00a7\2\37\3\2\2\2"+
		"\4/\3\2\2\2\69\3\2\2\2\b?\3\2\2\2\nC\3\2\2\2\fY\3\2\2\2\16[\3\2\2\2\20"+
		"b\3\2\2\2\22e\3\2\2\2\24q\3\2\2\2\26\u0084\3\2\2\2\30\u0086\3\2\2\2\32"+
		"\u008a\3\2\2\2\34\u0099\3\2\2\2\36 \5\4\3\2\37\36\3\2\2\2\37 \3\2\2\2"+
		" \"\3\2\2\2!#\5\6\4\2\"!\3\2\2\2\"#\3\2\2\2#%\3\2\2\2$&\5\b\5\2%$\3\2"+
		"\2\2%&\3\2\2\2&)\3\2\2\2\'*\5\f\7\2(*\5\24\13\2)\'\3\2\2\2)(\3\2\2\2*"+
		"+\3\2\2\2+)\3\2\2\2+,\3\2\2\2,-\3\2\2\2-.\7\2\2\3.\3\3\2\2\2/\60\7\32"+
		"\2\2\60\64\7\35\2\2\61\63\13\2\2\2\62\61\3\2\2\2\63\66\3\2\2\2\64\65\3"+
		"\2\2\2\64\62\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\7\21\2\28\5\3\2\2"+
		"\29:\7\33\2\2:;\7\t\2\2;<\7\20\2\2<=\7\t\2\2=\7\3\2\2\2>@\5\n\6\2?>\3"+
		"\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\t\3\2\2\2CD\7\34\2\2DE\7\t\2\2E"+
		"\13\3\2\2\2FG\7\27\2\2GH\7\35\2\2HI\7\17\2\2IJ\7\35\2\2JK\7\23\2\2KS\7"+
		"\24\2\2LM\7\35\2\2MO\7\23\2\2NP\5\16\b\2ON\3\2\2\2OP\3\2\2\2PQ\3\2\2\2"+
		"QS\7\24\2\2RF\3\2\2\2RL\3\2\2\2ST\3\2\2\2TU\7\r\2\2UZ\t\2\2\2VW\7\35\2"+
		"\2WX\7\r\2\2XZ\7\35\2\2YR\3\2\2\2YV\3\2\2\2Z\r\3\2\2\2[_\5\22\n\2\\^\5"+
		"\20\t\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\17\3\2\2\2a_\3\2\2\2"+
		"bc\7\20\2\2cd\5\22\n\2d\21\3\2\2\2eo\7\35\2\2fm\7\16\2\2gn\7\t\2\2hn\7"+
		"\f\2\2in\7\30\2\2jn\7\31\2\2kl\7\25\2\2ln\7\26\2\2mg\3\2\2\2mh\3\2\2\2"+
		"mi\3\2\2\2mj\3\2\2\2mk\3\2\2\2np\3\2\2\2of\3\2\2\2op\3\2\2\2p\23\3\2\2"+
		"\2qr\7\35\2\2rs\7\r\2\2st\7\25\2\2tu\5\26\f\2uv\7\26\2\2v\25\3\2\2\2w"+
		"|\5\30\r\2xy\7\20\2\2y{\5\30\r\2zx\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2"+
		"\2}\u0081\3\2\2\2~|\3\2\2\2\177\u0080\7\20\2\2\u0080\u0082\5\32\16\2\u0081"+
		"\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0085\5\32"+
		"\16\2\u0084w\3\2\2\2\u0084\u0083\3\2\2\2\u0085\27\3\2\2\2\u0086\u0087"+
		"\7\t\2\2\u0087\u0088\7\22\2\2\u0088\u0089\5\34\17\2\u0089\31\3\2\2\2\u008a"+
		"\u008b\7\35\2\2\u008b\u008c\6\16\2\3\u008c\u008d\7\22\2\2\u008d\u008e"+
		"\5\34\17\2\u008e\33\3\2\2\2\u008f\u009a\7\n\2\2\u0090\u009a\7\13\2\2\u0091"+
		"\u009a\7\f\2\2\u0092\u009a\7\t\2\2\u0093\u009a\7\30\2\2\u0094\u009a\7"+
		"\31\2\2\u0095\u0096\7\25\2\2\u0096\u009a\7\26\2\2\u0097\u0098\7\35\2\2"+
		"\u0098\u009a\6\17\3\3\u0099\u008f\3\2\2\2\u0099\u0090\3\2\2\2\u0099\u0091"+
		"\3\2\2\2\u0099\u0092\3\2\2\2\u0099\u0093\3\2\2\2\u0099\u0094\3\2\2\2\u0099"+
		"\u0095\3\2\2\2\u0099\u0097\3\2\2\2\u009a\35\3\2\2\2\23\37\"%)+\64AORY"+
		"_mo|\u0081\u0084\u0099";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}