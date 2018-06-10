// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/comment/parser/CommentParser.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.comment.parser.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommentParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WORD=1, DOC=2, BLOCK=3, LINE=4, WS=5, END=6, BLANK=7, MID=8, PREFORM=9, 
		HDRET=10, INLINE=11, FLOW=12, LIST=13, ITEM=14, CODE=15, PARAM=16, ATTAG=17, 
		CHAR=18, BWS=19, NL=20, LCHAR=21, LWS=22, RBRACE=23, CCHAR=24, CWS=25;
	public static final int
		RULE_comment = 0, RULE_doc = 1, RULE_block = 2, RULE_line = 3, RULE_desc = 4, 
		RULE_param = 5, RULE_code = 6, RULE_word = 7, RULE_preform = 8, RULE_inline = 9, 
		RULE_hdret = 10, RULE_flow = 11, RULE_list = 12, RULE_item = 13, RULE_blank = 14;
	public static final String[] ruleNames = {
		"comment", "doc", "block", "line", "desc", "param", "code", "word", "preform", 
		"inline", "hdret", "flow", "list", "item", "blank"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WORD", "DOC", "BLOCK", "LINE", "WS", "END", "BLANK", "MID", "PREFORM", 
		"HDRET", "INLINE", "FLOW", "LIST", "ITEM", "CODE", "PARAM", "ATTAG", "CHAR", 
		"BWS", "NL", "LCHAR", "LWS", "RBRACE", "CCHAR", "CWS"
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
	public String getGrammarFileName() { return "CommentParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CommentParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CommentContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CommentParser.EOF, 0); }
		public DocContext doc() {
			return getRuleContext(DocContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LineContext line() {
			return getRuleContext(LineContext.class,0);
		}
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_comment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOC:
				{
				setState(30);
				doc();
				}
				break;
			case BLOCK:
				{
				setState(31);
				block();
				}
				break;
			case LINE:
				{
				setState(32);
				line();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(35);
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

	public static class DocContext extends ParserRuleContext {
		public TerminalNode DOC() { return getToken(CommentParser.DOC, 0); }
		public TerminalNode END() { return getToken(CommentParser.END, 0); }
		public TerminalNode EOF() { return getToken(CommentParser.EOF, 0); }
		public DescContext desc() {
			return getRuleContext(DescContext.class,0);
		}
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<BlankContext> blank() {
			return getRuleContexts(BlankContext.class);
		}
		public BlankContext blank(int i) {
			return getRuleContext(BlankContext.class,i);
		}
		public DocContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterDoc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitDoc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitDoc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocContext doc() throws RecognitionException {
		DocContext _localctx = new DocContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_doc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(DOC);
			setState(39);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(38);
				desc();
				}
				break;
			}
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BLANK) | (1L << PARAM) | (1L << ATTAG))) != 0)) {
				{
				setState(43);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PARAM:
				case ATTAG:
					{
					setState(41);
					param();
					}
					break;
				case BLANK:
					{
					setState(42);
					blank();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==END) ) {
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

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode BLOCK() { return getToken(CommentParser.BLOCK, 0); }
		public TerminalNode END() { return getToken(CommentParser.END, 0); }
		public TerminalNode EOF() { return getToken(CommentParser.EOF, 0); }
		public DescContext desc() {
			return getRuleContext(DescContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(BLOCK);
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WORD) | (1L << BLANK) | (1L << PREFORM) | (1L << HDRET) | (1L << INLINE) | (1L << FLOW) | (1L << LIST) | (1L << ITEM) | (1L << CODE))) != 0)) {
				{
				setState(51);
				desc();
				}
			}

			setState(54);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==END) ) {
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

	public static class LineContext extends ParserRuleContext {
		public TerminalNode LINE() { return getToken(CommentParser.LINE, 0); }
		public TerminalNode NL() { return getToken(CommentParser.NL, 0); }
		public TerminalNode EOF() { return getToken(CommentParser.EOF, 0); }
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public List<CodeContext> code() {
			return getRuleContexts(CodeContext.class);
		}
		public CodeContext code(int i) {
			return getRuleContext(CodeContext.class,i);
		}
		public List<InlineContext> inline() {
			return getRuleContexts(InlineContext.class);
		}
		public InlineContext inline(int i) {
			return getRuleContext(InlineContext.class,i);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitLine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitLine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_line);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(LINE);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << WORD) | (1L << INLINE) | (1L << CODE))) != 0)) {
				{
				setState(60);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case WORD:
					{
					setState(57);
					word();
					}
					break;
				case CODE:
					{
					setState(58);
					code();
					}
					break;
				case INLINE:
					{
					setState(59);
					inline();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(65);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==NL) ) {
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

	public static class DescContext extends ParserRuleContext {
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public List<CodeContext> code() {
			return getRuleContexts(CodeContext.class);
		}
		public CodeContext code(int i) {
			return getRuleContext(CodeContext.class,i);
		}
		public List<InlineContext> inline() {
			return getRuleContexts(InlineContext.class);
		}
		public InlineContext inline(int i) {
			return getRuleContext(InlineContext.class,i);
		}
		public List<PreformContext> preform() {
			return getRuleContexts(PreformContext.class);
		}
		public PreformContext preform(int i) {
			return getRuleContext(PreformContext.class,i);
		}
		public List<FlowContext> flow() {
			return getRuleContexts(FlowContext.class);
		}
		public FlowContext flow(int i) {
			return getRuleContext(FlowContext.class,i);
		}
		public List<ListContext> list() {
			return getRuleContexts(ListContext.class);
		}
		public ListContext list(int i) {
			return getRuleContext(ListContext.class,i);
		}
		public List<ItemContext> item() {
			return getRuleContexts(ItemContext.class);
		}
		public ItemContext item(int i) {
			return getRuleContext(ItemContext.class,i);
		}
		public List<BlankContext> blank() {
			return getRuleContexts(BlankContext.class);
		}
		public BlankContext blank(int i) {
			return getRuleContext(BlankContext.class,i);
		}
		public List<HdretContext> hdret() {
			return getRuleContexts(HdretContext.class);
		}
		public HdretContext hdret(int i) {
			return getRuleContext(HdretContext.class,i);
		}
		public DescContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_desc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterDesc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitDesc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitDesc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DescContext desc() throws RecognitionException {
		DescContext _localctx = new DescContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_desc);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(76); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(76);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case WORD:
						{
						setState(67);
						word();
						}
						break;
					case CODE:
						{
						setState(68);
						code();
						}
						break;
					case INLINE:
						{
						setState(69);
						inline();
						}
						break;
					case PREFORM:
						{
						setState(70);
						preform();
						}
						break;
					case FLOW:
						{
						setState(71);
						flow();
						}
						break;
					case LIST:
						{
						setState(72);
						list();
						}
						break;
					case ITEM:
						{
						setState(73);
						item();
						}
						break;
					case BLANK:
						{
						setState(74);
						blank();
						}
						break;
					case HDRET:
						{
						setState(75);
						hdret();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(78); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	public static class ParamContext extends ParserRuleContext {
		public Token at;
		public TerminalNode PARAM() { return getToken(CommentParser.PARAM, 0); }
		public WordContext word() {
			return getRuleContext(WordContext.class,0);
		}
		public DescContext desc() {
			return getRuleContext(DescContext.class,0);
		}
		public TerminalNode ATTAG() { return getToken(CommentParser.ATTAG, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_param);
		int _la;
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PARAM:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				((ParamContext)_localctx).at = match(PARAM);
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WORD) {
					{
					setState(81);
					word();
					setState(83);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(82);
						desc();
						}
						break;
					}
					}
				}

				}
				break;
			case ATTAG:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				((ParamContext)_localctx).at = match(ATTAG);
				setState(89);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(88);
					desc();
					}
					break;
				}
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

	public static class CodeContext extends ParserRuleContext {
		public Token mark;
		public TerminalNode RBRACE() { return getToken(CommentParser.RBRACE, 0); }
		public TerminalNode CODE() { return getToken(CommentParser.CODE, 0); }
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_code);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			((CodeContext)_localctx).mark = match(CODE);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WORD) {
				{
				{
				setState(94);
				word();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(100);
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

	public static class WordContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(CommentParser.WORD, 0); }
		public WordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_word; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitWord(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitWord(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WordContext word() throws RecognitionException {
		WordContext _localctx = new WordContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_word);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(WORD);
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

	public static class PreformContext extends ParserRuleContext {
		public TerminalNode PREFORM() { return getToken(CommentParser.PREFORM, 0); }
		public PreformContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preform; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterPreform(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitPreform(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitPreform(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PreformContext preform() throws RecognitionException {
		PreformContext _localctx = new PreformContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_preform);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(PREFORM);
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

	public static class InlineContext extends ParserRuleContext {
		public TerminalNode INLINE() { return getToken(CommentParser.INLINE, 0); }
		public InlineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inline; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterInline(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitInline(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitInline(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InlineContext inline() throws RecognitionException {
		InlineContext _localctx = new InlineContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_inline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(INLINE);
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

	public static class HdretContext extends ParserRuleContext {
		public TerminalNode HDRET() { return getToken(CommentParser.HDRET, 0); }
		public HdretContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hdret; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterHdret(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitHdret(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitHdret(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HdretContext hdret() throws RecognitionException {
		HdretContext _localctx = new HdretContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_hdret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(HDRET);
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

	public static class FlowContext extends ParserRuleContext {
		public TerminalNode FLOW() { return getToken(CommentParser.FLOW, 0); }
		public FlowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flow; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterFlow(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitFlow(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitFlow(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FlowContext flow() throws RecognitionException {
		FlowContext _localctx = new FlowContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_flow);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(FLOW);
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
		public TerminalNode LIST() { return getToken(CommentParser.LIST, 0); }
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(LIST);
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

	public static class ItemContext extends ParserRuleContext {
		public TerminalNode ITEM() { return getToken(CommentParser.ITEM, 0); }
		public ItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ItemContext item() throws RecognitionException {
		ItemContext _localctx = new ItemContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_item);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(ITEM);
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

	public static class BlankContext extends ParserRuleContext {
		public TerminalNode BLANK() { return getToken(CommentParser.BLANK, 0); }
		public BlankContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blank; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).enterBlank(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CommentParserListener ) ((CommentParserListener)listener).exitBlank(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CommentParserVisitor ) return ((CommentParserVisitor<? extends T>)visitor).visitBlank(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlankContext blank() throws RecognitionException {
		BlankContext _localctx = new BlankContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_blank);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(BLANK);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33y\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\5\2$\n\2\3\2"+
		"\3\2\3\3\3\3\5\3*\n\3\3\3\3\3\7\3.\n\3\f\3\16\3\61\13\3\3\3\3\3\3\4\3"+
		"\4\5\4\67\n\4\3\4\3\4\3\5\3\5\3\5\3\5\7\5?\n\5\f\5\16\5B\13\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6O\n\6\r\6\16\6P\3\7\3\7\3\7\5"+
		"\7V\n\7\5\7X\n\7\3\7\3\7\5\7\\\n\7\5\7^\n\7\3\b\3\b\7\bb\n\b\f\b\16\b"+
		"e\13\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\4"+
		"\3\3\b\b\3\3\26\26\2\u0080\2#\3\2\2\2\4\'\3\2\2\2\6\64\3\2\2\2\b:\3\2"+
		"\2\2\nN\3\2\2\2\f]\3\2\2\2\16_\3\2\2\2\20h\3\2\2\2\22j\3\2\2\2\24l\3\2"+
		"\2\2\26n\3\2\2\2\30p\3\2\2\2\32r\3\2\2\2\34t\3\2\2\2\36v\3\2\2\2 $\5\4"+
		"\3\2!$\5\6\4\2\"$\5\b\5\2# \3\2\2\2#!\3\2\2\2#\"\3\2\2\2$%\3\2\2\2%&\7"+
		"\2\2\3&\3\3\2\2\2\')\7\4\2\2(*\5\n\6\2)(\3\2\2\2)*\3\2\2\2*/\3\2\2\2+"+
		".\5\f\7\2,.\5\36\20\2-+\3\2\2\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3"+
		"\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\t\2\2\2\63\5\3\2\2\2\64\66\7\5"+
		"\2\2\65\67\5\n\6\2\66\65\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\t\2\2\29"+
		"\7\3\2\2\2:@\7\6\2\2;?\5\20\t\2<?\5\16\b\2=?\5\24\13\2>;\3\2\2\2><\3\2"+
		"\2\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2AC\3\2\2\2B@\3\2\2\2CD\t\3"+
		"\2\2D\t\3\2\2\2EO\5\20\t\2FO\5\16\b\2GO\5\24\13\2HO\5\22\n\2IO\5\30\r"+
		"\2JO\5\32\16\2KO\5\34\17\2LO\5\36\20\2MO\5\26\f\2NE\3\2\2\2NF\3\2\2\2"+
		"NG\3\2\2\2NH\3\2\2\2NI\3\2\2\2NJ\3\2\2\2NK\3\2\2\2NL\3\2\2\2NM\3\2\2\2"+
		"OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\13\3\2\2\2RW\7\22\2\2SU\5\20\t\2TV\5\n"+
		"\6\2UT\3\2\2\2UV\3\2\2\2VX\3\2\2\2WS\3\2\2\2WX\3\2\2\2X^\3\2\2\2Y[\7\23"+
		"\2\2Z\\\5\n\6\2[Z\3\2\2\2[\\\3\2\2\2\\^\3\2\2\2]R\3\2\2\2]Y\3\2\2\2^\r"+
		"\3\2\2\2_c\7\21\2\2`b\5\20\t\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2"+
		"df\3\2\2\2ec\3\2\2\2fg\7\31\2\2g\17\3\2\2\2hi\7\3\2\2i\21\3\2\2\2jk\7"+
		"\13\2\2k\23\3\2\2\2lm\7\r\2\2m\25\3\2\2\2no\7\f\2\2o\27\3\2\2\2pq\7\16"+
		"\2\2q\31\3\2\2\2rs\7\17\2\2s\33\3\2\2\2tu\7\20\2\2u\35\3\2\2\2vw\7\t\2"+
		"\2w\37\3\2\2\2\20#)-/\66>@NPUW[]c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}