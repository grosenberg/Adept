// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/antlr/parser/Antlr4Parser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.antlr.parser.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BLOCKCOMMENT=1, LINECOMMENT=2, INT=3, SET=4, STRING=5, BEG_ACTION=6, BEG_ARGS=7, 
		OPTIONS=8, TOKENS=9, CHANNELS=10, IMPORT=11, CHANNEL=12, LSKIP=13, LMORE=14, 
		LHIDDEN=15, LEOF=16, MODE=17, PUSHMODE=18, POPMODE=19, TYPE=20, FRAGMENT=21, 
		LEXER=22, PARSER=23, GRAMMAR=24, PROTECTED=25, PUBLIC=26, PRIVATE=27, 
		RETURNS=28, LOCALS=29, THROWS=30, CATCH=31, FINALLY=32, AT=33, COLON=34, 
		COLONCOLON=35, COMMA=36, SEMI=37, LPAREN=38, RPAREN=39, LBRACE=40, RBRACE=41, 
		LBRACK=42, RBRACK=43, RARROW=44, EQ=45, QMARK=46, STAR=47, PLUS=48, PLUSEQ=49, 
		NOT=50, OR=51, DOT=52, RANGE=53, DOLLAR=54, POUND=55, ESC=56, SQUOTE=57, 
		DQUOTE=58, ID=59, HWS=60, VWS=61, ERRCHAR=62, END_ACTION=63, ACT_EOF=64, 
		ACT_CONTENT=65, END_ARGS=66, ARG_EOF=67, ARG_CONTENT=68;
	public static final int
		RULE_antlr = 0, RULE_statement = 1, RULE_primary = 2, RULE_cmdBlock = 3, 
		RULE_body = 4, RULE_listStmt = 5, RULE_assignStmt = 6, RULE_atBlock = 7, 
		RULE_ruleSpec = 8, RULE_ruleBlock = 9, RULE_argsBlock = 10, RULE_prequel = 11, 
		RULE_altList = 12, RULE_elements = 13, RULE_element = 14, RULE_namedElement = 15, 
		RULE_altBlock = 16, RULE_set = 17, RULE_pred = 18, RULE_action = 19, RULE_arguments = 20, 
		RULE_function = 21, RULE_attrValue = 22, RULE_dottedID = 23, RULE_label = 24, 
		RULE_id = 25, RULE_keyword = 26, RULE_prefix = 27, RULE_attribute = 28, 
		RULE_op = 29, RULE_mod = 30, RULE_other = 31;
	public static final String[] ruleNames = {
		"antlr", "statement", "primary", "cmdBlock", "body", "listStmt", "assignStmt", 
		"atBlock", "ruleSpec", "ruleBlock", "argsBlock", "prequel", "altList", 
		"elements", "element", "namedElement", "altBlock", "set", "pred", "action", 
		"arguments", "function", "attrValue", "dottedID", "label", "id", "keyword", 
		"prefix", "attribute", "op", "mod", "other"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "BLOCKCOMMENT", "LINECOMMENT", "INT", "SET", "STRING", "BEG_ACTION", 
		"BEG_ARGS", "OPTIONS", "TOKENS", "CHANNELS", "IMPORT", "CHANNEL", "LSKIP", 
		"LMORE", "LHIDDEN", "LEOF", "MODE", "PUSHMODE", "POPMODE", "TYPE", "FRAGMENT", 
		"LEXER", "PARSER", "GRAMMAR", "PROTECTED", "PUBLIC", "PRIVATE", "RETURNS", 
		"LOCALS", "THROWS", "CATCH", "FINALLY", "AT", "COLON", "COLONCOLON", "COMMA", 
		"SEMI", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "RARROW", 
		"EQ", "QMARK", "STAR", "PLUS", "PLUSEQ", "NOT", "OR", "DOT", "RANGE", 
		"DOLLAR", "POUND", "ESC", "SQUOTE", "DQUOTE", "ID", "HWS", "VWS", "ERRCHAR", 
		"END_ACTION", "ACT_EOF", "ACT_CONTENT", "END_ARGS", "ARG_EOF", "ARG_CONTENT"
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
	public String getGrammarFileName() { return "Antlr4Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Antlr4Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AntlrContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Antlr4Parser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<RuleSpecContext> ruleSpec() {
			return getRuleContexts(RuleSpecContext.class);
		}
		public RuleSpecContext ruleSpec(int i) {
			return getRuleContext(RuleSpecContext.class,i);
		}
		public List<OtherContext> other() {
			return getRuleContexts(OtherContext.class);
		}
		public OtherContext other(int i) {
			return getRuleContext(OtherContext.class,i);
		}
		public AntlrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_antlr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAntlr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAntlr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAntlr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AntlrContext antlr() throws RecognitionException {
		AntlrContext _localctx = new AntlrContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_antlr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BLOCKCOMMENT) | (1L << LINECOMMENT) | (1L << INT) | (1L << SET) | (1L << STRING) | (1L << BEG_ACTION) | (1L << BEG_ARGS) | (1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << CHANNEL) | (1L << LSKIP) | (1L << LMORE) | (1L << LHIDDEN) | (1L << LEOF) | (1L << MODE) | (1L << PUSHMODE) | (1L << POPMODE) | (1L << TYPE) | (1L << FRAGMENT) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << AT) | (1L << COLON) | (1L << COLONCOLON) | (1L << COMMA) | (1L << SEMI) | (1L << LPAREN) | (1L << RPAREN) | (1L << LBRACE) | (1L << RBRACE) | (1L << LBRACK) | (1L << RBRACK) | (1L << RARROW) | (1L << EQ) | (1L << QMARK) | (1L << STAR) | (1L << PLUS) | (1L << PLUSEQ) | (1L << NOT) | (1L << OR) | (1L << DOT) | (1L << RANGE) | (1L << DOLLAR) | (1L << POUND) | (1L << ESC) | (1L << SQUOTE) | (1L << DQUOTE) | (1L << ID) | (1L << HWS) | (1L << VWS) | (1L << ERRCHAR) | (1L << END_ACTION))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (ACT_EOF - 64)) | (1L << (ACT_CONTENT - 64)) | (1L << (END_ARGS - 64)) | (1L << (ARG_EOF - 64)) | (1L << (ARG_CONTENT - 64)))) != 0)) {
				{
				setState(67);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(64);
					statement();
					}
					break;
				case 2:
					{
					setState(65);
					ruleSpec();
					}
					break;
				case 3:
					{
					setState(66);
					other();
					}
					break;
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
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

	public static class StatementContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public CmdBlockContext cmdBlock() {
			return getRuleContext(CmdBlockContext.class,0);
		}
		public AtBlockContext atBlock() {
			return getRuleContext(AtBlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(77);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				primary();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				cmdBlock();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(76);
				atBlock();
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
		public TerminalNode SEMI() { return getToken(Antlr4Parser.SEMI, 0); }
		public List<KeywordContext> keyword() {
			return getRuleContexts(KeywordContext.class);
		}
		public KeywordContext keyword(int i) {
			return getRuleContext(KeywordContext.class,i);
		}
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_primary);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(79);
				keyword();
				}
				}
				setState(82); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON))) != 0) );
			setState(85); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(84);
				id();
				}
				}
				setState(87); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
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

	public static class CmdBlockContext extends ParserRuleContext {
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
		}
		public List<KeywordContext> keyword() {
			return getRuleContexts(KeywordContext.class);
		}
		public KeywordContext keyword(int i) {
			return getRuleContext(KeywordContext.class,i);
		}
		public CmdBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterCmdBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitCmdBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitCmdBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdBlockContext cmdBlock() throws RecognitionException {
		CmdBlockContext _localctx = new CmdBlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_cmdBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(91);
				keyword();
				}
				}
				setState(94); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON))) != 0) );
			setState(96);
			body();
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

	public static class BodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(Antlr4Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(Antlr4Parser.RBRACE, 0); }
		public ListStmtContext listStmt() {
			return getRuleContext(ListStmtContext.class,0);
		}
		public List<AssignStmtContext> assignStmt() {
			return getRuleContexts(AssignStmtContext.class);
		}
		public AssignStmtContext assignStmt(int i) {
			return getRuleContext(AssignStmtContext.class,i);
		}
		public BodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyContext body() throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(LBRACE);
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(99);
				listStmt();
				}
				break;
			case 2:
				{
				setState(101); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(100);
					assignStmt();
					}
					}
					setState(103); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				}
				break;
			}
			setState(107);
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

	public static class ListStmtContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Antlr4Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Antlr4Parser.COMMA, i);
		}
		public ListStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterListStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitListStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitListStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListStmtContext listStmt() throws RecognitionException {
		ListStmtContext _localctx = new ListStmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_listStmt);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			id();
			setState(114);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(110);
					match(COMMA);
					setState(111);
					id();
					}
					} 
				}
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(117);
				match(COMMA);
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

	public static class AssignStmtContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode EQ() { return getToken(Antlr4Parser.EQ, 0); }
		public TerminalNode SEMI() { return getToken(Antlr4Parser.SEMI, 0); }
		public DottedIDContext dottedID() {
			return getRuleContext(DottedIDContext.class,0);
		}
		public TerminalNode STRING() { return getToken(Antlr4Parser.STRING, 0); }
		public TerminalNode INT() { return getToken(Antlr4Parser.INT, 0); }
		public AssignStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStmtContext assignStmt() throws RecognitionException {
		AssignStmtContext _localctx = new AssignStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			id();
			setState(121);
			match(EQ);
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(122);
				dottedID();
				}
				break;
			case STRING:
				{
				setState(123);
				match(STRING);
				}
				break;
			case INT:
				{
				setState(124);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(127);
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

	public static class AtBlockContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(Antlr4Parser.AT, 0); }
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(Antlr4Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4Parser.ID, i);
		}
		public List<KeywordContext> keyword() {
			return getRuleContexts(KeywordContext.class);
		}
		public KeywordContext keyword(int i) {
			return getRuleContext(KeywordContext.class,i);
		}
		public AtBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAtBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAtBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAtBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtBlockContext atBlock() throws RecognitionException {
		AtBlockContext _localctx = new AtBlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_atBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(AT);
			setState(132); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(132);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case ID:
					{
					setState(130);
					match(ID);
					}
					break;
				case OPTIONS:
				case TOKENS:
				case CHANNELS:
				case IMPORT:
				case LEOF:
				case MODE:
				case LEXER:
				case PARSER:
				case GRAMMAR:
				case RETURNS:
				case LOCALS:
				case THROWS:
				case CATCH:
				case FINALLY:
				case COLONCOLON:
					{
					setState(131);
					keyword();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(134); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON) | (1L << ID))) != 0) );
			setState(136);
			action();
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

	public static class RuleSpecContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public RuleBlockContext ruleBlock() {
			return getRuleContext(RuleBlockContext.class,0);
		}
		public List<PrefixContext> prefix() {
			return getRuleContexts(PrefixContext.class);
		}
		public PrefixContext prefix(int i) {
			return getRuleContext(PrefixContext.class,i);
		}
		public List<ArgsBlockContext> argsBlock() {
			return getRuleContexts(ArgsBlockContext.class);
		}
		public ArgsBlockContext argsBlock(int i) {
			return getRuleContext(ArgsBlockContext.class,i);
		}
		public PrequelContext prequel() {
			return getRuleContext(PrequelContext.class,0);
		}
		public RuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleSpecContext ruleSpec() throws RecognitionException {
		RuleSpecContext _localctx = new RuleSpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ruleSpec);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(138);
				prefix();
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(144);
			id();
			setState(148);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(145);
					argsBlock();
					}
					} 
				}
				setState(150);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			setState(152);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << AT) | (1L << COLONCOLON))) != 0)) {
				{
				setState(151);
				prequel();
				}
			}

			setState(154);
			ruleBlock();
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

	public static class RuleBlockContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(Antlr4Parser.COLON, 0); }
		public AltListContext altList() {
			return getRuleContext(AltListContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(Antlr4Parser.SEMI, 0); }
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public RuleBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterRuleBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitRuleBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitRuleBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleBlockContext ruleBlock() throws RecognitionException {
		RuleBlockContext _localctx = new RuleBlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ruleBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			match(COLON);
			setState(157);
			altList();
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEG_ACTION) {
				{
				setState(158);
				action();
				}
			}

			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RARROW) {
				{
				setState(161);
				function();
				}
			}

			setState(164);
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

	public static class ArgsBlockContext extends ParserRuleContext {
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public KeywordContext keyword() {
			return getRuleContext(KeywordContext.class,0);
		}
		public ArgsBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argsBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterArgsBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitArgsBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitArgsBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgsBlockContext argsBlock() throws RecognitionException {
		ArgsBlockContext _localctx = new ArgsBlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_argsBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON))) != 0)) {
				{
				setState(166);
				keyword();
				}
			}

			setState(169);
			arguments();
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

	public static class PrequelContext extends ParserRuleContext {
		public CmdBlockContext cmdBlock() {
			return getRuleContext(CmdBlockContext.class,0);
		}
		public AtBlockContext atBlock() {
			return getRuleContext(AtBlockContext.class,0);
		}
		public PrequelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prequel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterPrequel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitPrequel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitPrequel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrequelContext prequel() throws RecognitionException {
		PrequelContext _localctx = new PrequelContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_prequel);
		try {
			setState(173);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPTIONS:
			case TOKENS:
			case CHANNELS:
			case IMPORT:
			case LEOF:
			case MODE:
			case LEXER:
			case PARSER:
			case GRAMMAR:
			case RETURNS:
			case LOCALS:
			case THROWS:
			case CATCH:
			case FINALLY:
			case COLONCOLON:
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				cmdBlock();
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(172);
				atBlock();
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

	public static class AltListContext extends ParserRuleContext {
		public List<ElementsContext> elements() {
			return getRuleContexts(ElementsContext.class);
		}
		public ElementsContext elements(int i) {
			return getRuleContext(ElementsContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(Antlr4Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(Antlr4Parser.OR, i);
		}
		public AltListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_altList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAltList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAltList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAltList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AltListContext altList() throws RecognitionException {
		AltListContext _localctx = new AltListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_altList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			elements();
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(176);
				match(OR);
				setState(177);
				elements();
				}
				}
				setState(182);
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

	public static class ElementsContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public TerminalNode POUND() { return getToken(Antlr4Parser.POUND, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitElements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitElements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementsContext elements() throws RecognitionException {
		ElementsContext _localctx = new ElementsContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_elements);
		int _la;
		try {
			int _alt;
			setState(193);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(184); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(183);
						element();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(186); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==POUND) {
					{
					setState(188);
					match(POUND);
					setState(189);
					id();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
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

	public static class ElementContext extends ParserRuleContext {
		public NamedElementContext namedElement() {
			return getRuleContext(NamedElementContext.class,0);
		}
		public AltBlockContext altBlock() {
			return getRuleContext(AltBlockContext.class,0);
		}
		public List<TerminalNode> STRING() { return getTokens(Antlr4Parser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(Antlr4Parser.STRING, i);
		}
		public TerminalNode DOT() { return getToken(Antlr4Parser.DOT, 0); }
		public SetContext set() {
			return getRuleContext(SetContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode NOT() { return getToken(Antlr4Parser.NOT, 0); }
		public ModContext mod() {
			return getRuleContext(ModContext.class,0);
		}
		public TerminalNode RANGE() { return getToken(Antlr4Parser.RANGE, 0); }
		public PredContext pred() {
			return getRuleContext(PredContext.class,0);
		}
		public TerminalNode LEOF() { return getToken(Antlr4Parser.LEOF, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_element);
		int _la;
		try {
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(195);
				namedElement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(196);
					match(NOT);
					}
				}

				setState(204);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LPAREN:
					{
					setState(199);
					altBlock();
					}
					break;
				case STRING:
					{
					setState(200);
					match(STRING);
					}
					break;
				case DOT:
					{
					setState(201);
					match(DOT);
					}
					break;
				case SET:
					{
					setState(202);
					set();
					}
					break;
				case ID:
					{
					setState(203);
					id();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(207);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
				case 1:
					{
					setState(206);
					mod();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(209);
				match(STRING);
				setState(210);
				match(RANGE);
				setState(211);
				match(STRING);
				setState(213);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(212);
					mod();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(215);
				pred();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(216);
				match(LEOF);
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

	public static class NamedElementContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public AltBlockContext altBlock() {
			return getRuleContext(AltBlockContext.class,0);
		}
		public ModContext mod() {
			return getRuleContext(ModContext.class,0);
		}
		public NamedElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterNamedElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitNamedElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitNamedElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedElementContext namedElement() throws RecognitionException {
		NamedElementContext _localctx = new NamedElementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_namedElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			label();
			setState(222);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(220);
				element();
				}
				break;
			case 2:
				{
				setState(221);
				altBlock();
				}
				break;
			}
			setState(225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				setState(224);
				mod();
				}
				break;
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

	public static class AltBlockContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(Antlr4Parser.LPAREN, 0); }
		public AltListContext altList() {
			return getRuleContext(AltListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Antlr4Parser.RPAREN, 0); }
		public TerminalNode COLON() { return getToken(Antlr4Parser.COLON, 0); }
		public CmdBlockContext cmdBlock() {
			return getRuleContext(CmdBlockContext.class,0);
		}
		public List<AtBlockContext> atBlock() {
			return getRuleContexts(AtBlockContext.class);
		}
		public AtBlockContext atBlock(int i) {
			return getRuleContext(AtBlockContext.class,i);
		}
		public AltBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_altBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAltBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAltBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAltBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AltBlockContext altBlock() throws RecognitionException {
		AltBlockContext _localctx = new AltBlockContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_altBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(LPAREN);
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON))) != 0)) {
					{
					setState(228);
					cmdBlock();
					}
				}

				setState(234);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(231);
					atBlock();
					}
					}
					setState(236);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(237);
				match(COLON);
				}
				break;
			}
			setState(240);
			altList();
			setState(241);
			match(RPAREN);
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

	public static class SetContext extends ParserRuleContext {
		public TerminalNode SET() { return getToken(Antlr4Parser.SET, 0); }
		public ModContext mod() {
			return getRuleContext(ModContext.class,0);
		}
		public SetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetContext set() throws RecognitionException {
		SetContext _localctx = new SetContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_set);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(SET);
			setState(245);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				setState(244);
				mod();
				}
				break;
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

	public static class PredContext extends ParserRuleContext {
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public TerminalNode QMARK() { return getToken(Antlr4Parser.QMARK, 0); }
		public PredContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pred; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterPred(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitPred(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitPred(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredContext pred() throws RecognitionException {
		PredContext _localctx = new PredContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_pred);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			action();
			setState(248);
			match(QMARK);
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
		public TerminalNode BEG_ACTION() { return getToken(Antlr4Parser.BEG_ACTION, 0); }
		public TerminalNode END_ACTION() { return getToken(Antlr4Parser.END_ACTION, 0); }
		public List<TerminalNode> ACT_CONTENT() { return getTokens(Antlr4Parser.ACT_CONTENT); }
		public TerminalNode ACT_CONTENT(int i) {
			return getToken(Antlr4Parser.ACT_CONTENT, i);
		}
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(BEG_ACTION);
			setState(254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ACT_CONTENT) {
				{
				{
				setState(251);
				match(ACT_CONTENT);
				}
				}
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(257);
			match(END_ACTION);
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

	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode BEG_ARGS() { return getToken(Antlr4Parser.BEG_ARGS, 0); }
		public TerminalNode END_ARGS() { return getToken(Antlr4Parser.END_ARGS, 0); }
		public List<TerminalNode> ARG_CONTENT() { return getTokens(Antlr4Parser.ARG_CONTENT); }
		public TerminalNode ARG_CONTENT(int i) {
			return getToken(Antlr4Parser.ARG_CONTENT, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(BEG_ARGS);
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARG_CONTENT) {
				{
				{
				setState(260);
				match(ARG_CONTENT);
				}
				}
				setState(265);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(266);
			match(END_ARGS);
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

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode RARROW() { return getToken(Antlr4Parser.RARROW, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public List<AttrValueContext> attrValue() {
			return getRuleContexts(AttrValueContext.class);
		}
		public AttrValueContext attrValue(int i) {
			return getRuleContext(AttrValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Antlr4Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Antlr4Parser.COMMA, i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			match(RARROW);
			setState(269);
			attribute();
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(270);
				attrValue();
				}
			}

			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(273);
				match(COMMA);
				setState(274);
				attribute();
				setState(276);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LPAREN) {
					{
					setState(275);
					attrValue();
					}
				}

				}
				}
				setState(282);
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

	public static class AttrValueContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(Antlr4Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Antlr4Parser.RPAREN, 0); }
		public TerminalNode LHIDDEN() { return getToken(Antlr4Parser.LHIDDEN, 0); }
		public TerminalNode ID() { return getToken(Antlr4Parser.ID, 0); }
		public AttrValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attrValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAttrValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAttrValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAttrValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttrValueContext attrValue() throws RecognitionException {
		AttrValueContext _localctx = new AttrValueContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_attrValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(LPAREN);
			setState(284);
			_la = _input.LA(1);
			if ( !(_la==LHIDDEN || _la==ID) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(285);
			match(RPAREN);
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

	public static class DottedIDContext extends ParserRuleContext {
		public IdContext x;
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(Antlr4Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(Antlr4Parser.DOT, i);
		}
		public DottedIDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dottedID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterDottedID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitDottedID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitDottedID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DottedIDContext dottedID() throws RecognitionException {
		DottedIDContext _localctx = new DottedIDContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_dottedID);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			((DottedIDContext)_localctx).x = id();
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(288);
				match(DOT);
				setState(289);
				id();
				}
				}
				setState(294);
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

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Antlr4Parser.ID, 0); }
		public OpContext op() {
			return getRuleContext(OpContext.class,0);
		}
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			match(ID);
			setState(296);
			op();
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

	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Antlr4Parser.ID, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
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

	public static class KeywordContext extends ParserRuleContext {
		public TerminalNode GRAMMAR() { return getToken(Antlr4Parser.GRAMMAR, 0); }
		public TerminalNode LEXER() { return getToken(Antlr4Parser.LEXER, 0); }
		public TerminalNode PARSER() { return getToken(Antlr4Parser.PARSER, 0); }
		public TerminalNode OPTIONS() { return getToken(Antlr4Parser.OPTIONS, 0); }
		public TerminalNode TOKENS() { return getToken(Antlr4Parser.TOKENS, 0); }
		public TerminalNode IMPORT() { return getToken(Antlr4Parser.IMPORT, 0); }
		public TerminalNode CHANNELS() { return getToken(Antlr4Parser.CHANNELS, 0); }
		public TerminalNode RETURNS() { return getToken(Antlr4Parser.RETURNS, 0); }
		public TerminalNode LOCALS() { return getToken(Antlr4Parser.LOCALS, 0); }
		public TerminalNode THROWS() { return getToken(Antlr4Parser.THROWS, 0); }
		public TerminalNode CATCH() { return getToken(Antlr4Parser.CATCH, 0); }
		public TerminalNode FINALLY() { return getToken(Antlr4Parser.FINALLY, 0); }
		public TerminalNode COLONCOLON() { return getToken(Antlr4Parser.COLONCOLON, 0); }
		public TerminalNode MODE() { return getToken(Antlr4Parser.MODE, 0); }
		public TerminalNode LEOF() { return getToken(Antlr4Parser.LEOF, 0); }
		public KeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitKeyword(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitKeyword(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeywordContext keyword() throws RecognitionException {
		KeywordContext _localctx = new KeywordContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_keyword);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << LEOF) | (1L << MODE) | (1L << LEXER) | (1L << PARSER) | (1L << GRAMMAR) | (1L << RETURNS) | (1L << LOCALS) | (1L << THROWS) | (1L << CATCH) | (1L << FINALLY) | (1L << COLONCOLON))) != 0)) ) {
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

	public static class PrefixContext extends ParserRuleContext {
		public TerminalNode FRAGMENT() { return getToken(Antlr4Parser.FRAGMENT, 0); }
		public TerminalNode PUBLIC() { return getToken(Antlr4Parser.PUBLIC, 0); }
		public TerminalNode PROTECTED() { return getToken(Antlr4Parser.PROTECTED, 0); }
		public TerminalNode PRIVATE() { return getToken(Antlr4Parser.PRIVATE, 0); }
		public PrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitPrefix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitPrefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixContext prefix() throws RecognitionException {
		PrefixContext _localctx = new PrefixContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_prefix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) ) {
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode CHANNEL() { return getToken(Antlr4Parser.CHANNEL, 0); }
		public TerminalNode LSKIP() { return getToken(Antlr4Parser.LSKIP, 0); }
		public TerminalNode LMORE() { return getToken(Antlr4Parser.LMORE, 0); }
		public TerminalNode PUSHMODE() { return getToken(Antlr4Parser.PUSHMODE, 0); }
		public TerminalNode POPMODE() { return getToken(Antlr4Parser.POPMODE, 0); }
		public TerminalNode MODE() { return getToken(Antlr4Parser.MODE, 0); }
		public TerminalNode TYPE() { return getToken(Antlr4Parser.TYPE, 0); }
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHANNEL) | (1L << LSKIP) | (1L << LMORE) | (1L << MODE) | (1L << PUSHMODE) | (1L << POPMODE) | (1L << TYPE))) != 0)) ) {
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

	public static class OpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(Antlr4Parser.EQ, 0); }
		public TerminalNode PLUSEQ() { return getToken(Antlr4Parser.PLUSEQ, 0); }
		public OpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			_la = _input.LA(1);
			if ( !(_la==EQ || _la==PLUSEQ) ) {
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

	public static class ModContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(Antlr4Parser.STAR, 0); }
		public TerminalNode QMARK() { return getToken(Antlr4Parser.QMARK, 0); }
		public TerminalNode PLUS() { return getToken(Antlr4Parser.PLUS, 0); }
		public ModContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterMod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitMod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitMod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModContext mod() throws RecognitionException {
		ModContext _localctx = new ModContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_mod);
		try {
			setState(317);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(308);
				match(STAR);
				setState(310);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
				case 1:
					{
					setState(309);
					match(QMARK);
					}
					break;
				}
				}
				break;
			case PLUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(312);
				match(PLUS);
				setState(314);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(313);
					match(QMARK);
					}
					break;
				}
				}
				break;
			case QMARK:
				enterOuterAlt(_localctx, 3);
				{
				setState(316);
				match(QMARK);
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

	public static class OtherContext extends ParserRuleContext {
		public OtherContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_other; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).enterOther(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Antlr4ParserListener ) ((Antlr4ParserListener)listener).exitOther(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Antlr4ParserVisitor ) return ((Antlr4ParserVisitor<? extends T>)visitor).visitOther(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OtherContext other() throws RecognitionException {
		OtherContext _localctx = new OtherContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_other);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			matchWildcard();
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3F\u0144\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\2\7\2F\n\2\f\2\16\2I\13\2\3\2\3\2\3\3\3\3\3\3\5\3P\n\3\3"+
		"\4\6\4S\n\4\r\4\16\4T\3\4\6\4X\n\4\r\4\16\4Y\3\4\3\4\3\5\6\5_\n\5\r\5"+
		"\16\5`\3\5\3\5\3\6\3\6\3\6\6\6h\n\6\r\6\16\6i\5\6l\n\6\3\6\3\6\3\7\3\7"+
		"\3\7\7\7s\n\7\f\7\16\7v\13\7\3\7\5\7y\n\7\3\b\3\b\3\b\3\b\3\b\5\b\u0080"+
		"\n\b\3\b\3\b\3\t\3\t\3\t\6\t\u0087\n\t\r\t\16\t\u0088\3\t\3\t\3\n\7\n"+
		"\u008e\n\n\f\n\16\n\u0091\13\n\3\n\3\n\7\n\u0095\n\n\f\n\16\n\u0098\13"+
		"\n\3\n\5\n\u009b\n\n\3\n\3\n\3\13\3\13\3\13\5\13\u00a2\n\13\3\13\5\13"+
		"\u00a5\n\13\3\13\3\13\3\f\5\f\u00aa\n\f\3\f\3\f\3\r\3\r\5\r\u00b0\n\r"+
		"\3\16\3\16\3\16\7\16\u00b5\n\16\f\16\16\16\u00b8\13\16\3\17\6\17\u00bb"+
		"\n\17\r\17\16\17\u00bc\3\17\3\17\5\17\u00c1\n\17\3\17\5\17\u00c4\n\17"+
		"\3\20\3\20\5\20\u00c8\n\20\3\20\3\20\3\20\3\20\3\20\5\20\u00cf\n\20\3"+
		"\20\5\20\u00d2\n\20\3\20\3\20\3\20\3\20\5\20\u00d8\n\20\3\20\3\20\5\20"+
		"\u00dc\n\20\3\21\3\21\3\21\5\21\u00e1\n\21\3\21\5\21\u00e4\n\21\3\22\3"+
		"\22\5\22\u00e8\n\22\3\22\7\22\u00eb\n\22\f\22\16\22\u00ee\13\22\3\22\5"+
		"\22\u00f1\n\22\3\22\3\22\3\22\3\23\3\23\5\23\u00f8\n\23\3\24\3\24\3\24"+
		"\3\25\3\25\7\25\u00ff\n\25\f\25\16\25\u0102\13\25\3\25\3\25\3\26\3\26"+
		"\7\26\u0108\n\26\f\26\16\26\u010b\13\26\3\26\3\26\3\27\3\27\3\27\5\27"+
		"\u0112\n\27\3\27\3\27\3\27\5\27\u0117\n\27\7\27\u0119\n\27\f\27\16\27"+
		"\u011c\13\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\7\31\u0125\n\31\f\31\16"+
		"\31\u0128\13\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \5 \u0139\n \3 \3 \5 \u013d\n \3 \5 \u0140\n \3!\3!\3"+
		"!\2\2\"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:"+
		"<>@\2\7\4\2\21\21==\7\2\n\r\22\23\30\32\36\"%%\4\2\27\27\33\35\4\2\16"+
		"\20\23\26\4\2//\63\63\2\u015a\2G\3\2\2\2\4O\3\2\2\2\6R\3\2\2\2\b^\3\2"+
		"\2\2\nd\3\2\2\2\fo\3\2\2\2\16z\3\2\2\2\20\u0083\3\2\2\2\22\u008f\3\2\2"+
		"\2\24\u009e\3\2\2\2\26\u00a9\3\2\2\2\30\u00af\3\2\2\2\32\u00b1\3\2\2\2"+
		"\34\u00c3\3\2\2\2\36\u00db\3\2\2\2 \u00dd\3\2\2\2\"\u00e5\3\2\2\2$\u00f5"+
		"\3\2\2\2&\u00f9\3\2\2\2(\u00fc\3\2\2\2*\u0105\3\2\2\2,\u010e\3\2\2\2."+
		"\u011d\3\2\2\2\60\u0121\3\2\2\2\62\u0129\3\2\2\2\64\u012c\3\2\2\2\66\u012e"+
		"\3\2\2\28\u0130\3\2\2\2:\u0132\3\2\2\2<\u0134\3\2\2\2>\u013f\3\2\2\2@"+
		"\u0141\3\2\2\2BF\5\4\3\2CF\5\22\n\2DF\5@!\2EB\3\2\2\2EC\3\2\2\2ED\3\2"+
		"\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2HJ\3\2\2\2IG\3\2\2\2JK\7\2\2\3K\3\3"+
		"\2\2\2LP\5\6\4\2MP\5\b\5\2NP\5\20\t\2OL\3\2\2\2OM\3\2\2\2ON\3\2\2\2P\5"+
		"\3\2\2\2QS\5\66\34\2RQ\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2UW\3\2\2\2"+
		"VX\5\64\33\2WV\3\2\2\2XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\7\'"+
		"\2\2\\\7\3\2\2\2]_\5\66\34\2^]\3\2\2\2_`\3\2\2\2`^\3\2\2\2`a\3\2\2\2a"+
		"b\3\2\2\2bc\5\n\6\2c\t\3\2\2\2dk\7*\2\2el\5\f\7\2fh\5\16\b\2gf\3\2\2\2"+
		"hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2jl\3\2\2\2ke\3\2\2\2kg\3\2\2\2kl\3\2\2\2"+
		"lm\3\2\2\2mn\7+\2\2n\13\3\2\2\2ot\5\64\33\2pq\7&\2\2qs\5\64\33\2rp\3\2"+
		"\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2ux\3\2\2\2vt\3\2\2\2wy\7&\2\2xw\3\2"+
		"\2\2xy\3\2\2\2y\r\3\2\2\2z{\5\64\33\2{\177\7/\2\2|\u0080\5\60\31\2}\u0080"+
		"\7\7\2\2~\u0080\7\5\2\2\177|\3\2\2\2\177}\3\2\2\2\177~\3\2\2\2\u0080\u0081"+
		"\3\2\2\2\u0081\u0082\7\'\2\2\u0082\17\3\2\2\2\u0083\u0086\7#\2\2\u0084"+
		"\u0087\7=\2\2\u0085\u0087\5\66\34\2\u0086\u0084\3\2\2\2\u0086\u0085\3"+
		"\2\2\2\u0087\u0088\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089"+
		"\u008a\3\2\2\2\u008a\u008b\5(\25\2\u008b\21\3\2\2\2\u008c\u008e\58\35"+
		"\2\u008d\u008c\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090"+
		"\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0096\5\64\33\2"+
		"\u0093\u0095\5\26\f\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094"+
		"\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0099"+
		"\u009b\5\30\r\2\u009a\u0099\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3"+
		"\2\2\2\u009c\u009d\5\24\13\2\u009d\23\3\2\2\2\u009e\u009f\7$\2\2\u009f"+
		"\u00a1\5\32\16\2\u00a0\u00a2\5(\25\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3"+
		"\2\2\2\u00a2\u00a4\3\2\2\2\u00a3\u00a5\5,\27\2\u00a4\u00a3\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\7\'\2\2\u00a7\25\3\2\2"+
		"\2\u00a8\u00aa\5\66\34\2\u00a9\u00a8\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa"+
		"\u00ab\3\2\2\2\u00ab\u00ac\5*\26\2\u00ac\27\3\2\2\2\u00ad\u00b0\5\b\5"+
		"\2\u00ae\u00b0\5\20\t\2\u00af\u00ad\3\2\2\2\u00af\u00ae\3\2\2\2\u00b0"+
		"\31\3\2\2\2\u00b1\u00b6\5\34\17\2\u00b2\u00b3\7\65\2\2\u00b3\u00b5\5\34"+
		"\17\2\u00b4\u00b2\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6"+
		"\u00b7\3\2\2\2\u00b7\33\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00bb\5\36\20"+
		"\2\u00ba\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd"+
		"\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bf\79\2\2\u00bf\u00c1\5\64\33\2"+
		"\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c4"+
		"\3\2\2\2\u00c3\u00ba\3\2\2\2\u00c3\u00c2\3\2\2\2\u00c4\35\3\2\2\2\u00c5"+
		"\u00dc\5 \21\2\u00c6\u00c8\7\64\2\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3"+
		"\2\2\2\u00c8\u00ce\3\2\2\2\u00c9\u00cf\5\"\22\2\u00ca\u00cf\7\7\2\2\u00cb"+
		"\u00cf\7\66\2\2\u00cc\u00cf\5$\23\2\u00cd\u00cf\5\64\33\2\u00ce\u00c9"+
		"\3\2\2\2\u00ce\u00ca\3\2\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce"+
		"\u00cd\3\2\2\2\u00cf\u00d1\3\2\2\2\u00d0\u00d2\5> \2\u00d1\u00d0\3\2\2"+
		"\2\u00d1\u00d2\3\2\2\2\u00d2\u00dc\3\2\2\2\u00d3\u00d4\7\7\2\2\u00d4\u00d5"+
		"\7\67\2\2\u00d5\u00d7\7\7\2\2\u00d6\u00d8\5> \2\u00d7\u00d6\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00dc\3\2\2\2\u00d9\u00dc\5&\24\2\u00da\u00dc\7\22"+
		"\2\2\u00db\u00c5\3\2\2\2\u00db\u00c7\3\2\2\2\u00db\u00d3\3\2\2\2\u00db"+
		"\u00d9\3\2\2\2\u00db\u00da\3\2\2\2\u00dc\37\3\2\2\2\u00dd\u00e0\5\62\32"+
		"\2\u00de\u00e1\5\36\20\2\u00df\u00e1\5\"\22\2\u00e0\u00de\3\2\2\2\u00e0"+
		"\u00df\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00e4\5> \2\u00e3\u00e2\3\2\2"+
		"\2\u00e3\u00e4\3\2\2\2\u00e4!\3\2\2\2\u00e5\u00f0\7(\2\2\u00e6\u00e8\5"+
		"\b\5\2\u00e7\u00e6\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00ec\3\2\2\2\u00e9"+
		"\u00eb\5\20\t\2\u00ea\u00e9\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec\u00ea\3"+
		"\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ef\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ef"+
		"\u00f1\7$\2\2\u00f0\u00e7\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2"+
		"\2\2\u00f2\u00f3\5\32\16\2\u00f3\u00f4\7)\2\2\u00f4#\3\2\2\2\u00f5\u00f7"+
		"\7\6\2\2\u00f6\u00f8\5> \2\u00f7\u00f6\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"%\3\2\2\2\u00f9\u00fa\5(\25\2\u00fa\u00fb\7\60\2\2\u00fb\'\3\2\2\2\u00fc"+
		"\u0100\7\b\2\2\u00fd\u00ff\7C\2\2\u00fe\u00fd\3\2\2\2\u00ff\u0102\3\2"+
		"\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0103\3\2\2\2\u0102"+
		"\u0100\3\2\2\2\u0103\u0104\7A\2\2\u0104)\3\2\2\2\u0105\u0109\7\t\2\2\u0106"+
		"\u0108\7F\2\2\u0107\u0106\3\2\2\2\u0108\u010b\3\2\2\2\u0109\u0107\3\2"+
		"\2\2\u0109\u010a\3\2\2\2\u010a\u010c\3\2\2\2\u010b\u0109\3\2\2\2\u010c"+
		"\u010d\7D\2\2\u010d+\3\2\2\2\u010e\u010f\7.\2\2\u010f\u0111\5:\36\2\u0110"+
		"\u0112\5.\30\2\u0111\u0110\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u011a\3\2"+
		"\2\2\u0113\u0114\7&\2\2\u0114\u0116\5:\36\2\u0115\u0117\5.\30\2\u0116"+
		"\u0115\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u0113\3\2"+
		"\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b"+
		"-\3\2\2\2\u011c\u011a\3\2\2\2\u011d\u011e\7(\2\2\u011e\u011f\t\2\2\2\u011f"+
		"\u0120\7)\2\2\u0120/\3\2\2\2\u0121\u0126\5\64\33\2\u0122\u0123\7\66\2"+
		"\2\u0123\u0125\5\64\33\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2\2\u0126"+
		"\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\61\3\2\2\2\u0128\u0126\3\2\2"+
		"\2\u0129\u012a\7=\2\2\u012a\u012b\5<\37\2\u012b\63\3\2\2\2\u012c\u012d"+
		"\7=\2\2\u012d\65\3\2\2\2\u012e\u012f\t\3\2\2\u012f\67\3\2\2\2\u0130\u0131"+
		"\t\4\2\2\u01319\3\2\2\2\u0132\u0133\t\5\2\2\u0133;\3\2\2\2\u0134\u0135"+
		"\t\6\2\2\u0135=\3\2\2\2\u0136\u0138\7\61\2\2\u0137\u0139\7\60\2\2\u0138"+
		"\u0137\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u0140\3\2\2\2\u013a\u013c\7\62"+
		"\2\2\u013b\u013d\7\60\2\2\u013c\u013b\3\2\2\2\u013c\u013d\3\2\2\2\u013d"+
		"\u0140\3\2\2\2\u013e\u0140\7\60\2\2\u013f\u0136\3\2\2\2\u013f\u013a\3"+
		"\2\2\2\u013f\u013e\3\2\2\2\u0140?\3\2\2\2\u0141\u0142\13\2\2\2\u0142A"+
		"\3\2\2\2.EGOTY`iktx\177\u0086\u0088\u008f\u0096\u009a\u00a1\u00a4\u00a9"+
		"\u00af\u00b6\u00bc\u00c0\u00c3\u00c7\u00ce\u00d1\u00d7\u00db\u00e0\u00e3"+
		"\u00e7\u00ec\u00f0\u00f7\u0100\u0109\u0111\u0116\u011a\u0126\u0138\u013c"+
		"\u013f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}