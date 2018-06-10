// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/comment/parser/CommentLexer.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.comment.parser.gen;
	import net.certiv.adept.lang.comment.parser.CommentLexerAdaptor;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CommentLexer extends CommentLexerAdaptor {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WORD=1, DOC=2, BLOCK=3, LINE=4, WS=5, END=6, BLANK=7, MID=8, PREFORM=9, 
		HDRET=10, INLINE=11, FLOW=12, LIST=13, ITEM=14, CODE=15, PARAM=16, ATTAG=17, 
		CHAR=18, BWS=19, NL=20, LCHAR=21, LWS=22, RBRACE=23, CCHAR=24, CWS=25;
	public static final int
		block=1, line=2, code=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "block", "line", "code"
	};

	public static final String[] ruleNames = {
		"DOC", "BLOCK", "LINE", "WS", "END", "BLANK", "MID", "PREFORM", "HDRET", 
		"INLINE", "FLOW", "LIST", "ITEM", "CODE", "PARAM", "ATTAG", "CHAR", "BWS", 
		"NL", "LINLINE", "LCODE", "LCHAR", "LWS", "RBRACE", "CCHAR", "CWS", "TagBeg", 
		"TagEnd", "Style", "EscSeq", "UnicodeEsc", "HexDigit", "Char", "Code", 
		"Param", "AtTag", "Inline", "Break", "Flow", "List", "Item", "Esc", "Nl", 
		"HWs", "VWs", "Underscore", "LBrace", "RBrace", "LAngle", "RAngle", "Slash", 
		"Star", "At", "Eq"
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


	public CommentLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CommentLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 16:
			CHAR_action((RuleContext)_localctx, actionIndex);
			break;
		case 21:
			LCHAR_action((RuleContext)_localctx, actionIndex);
			break;
		case 24:
			CCHAR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void CHAR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 more(WORD); 
			break;
		}
	}
	private void LCHAR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 more(WORD); 
			break;
		}
	}
	private void CCHAR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 more(WORD); 
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return BLANK_sempred((RuleContext)_localctx, predIndex);
		case 6:
			return MID_sempred((RuleContext)_localctx, predIndex);
		case 14:
			return PARAM_sempred((RuleContext)_localctx, predIndex);
		case 15:
			return ATTAG_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean BLANK_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  atBol(true) ;
		}
		return true;
	}
	private boolean MID_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return  atBol(false) ;
		}
		return true;
	}
	private boolean PARAM_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return  atBol(true) ;
		}
		return true;
	}
	private boolean ATTAG_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return  atBol(true) ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u0233\b\1\b\1"+
		"\b\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t"+
		"\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21"+
		"\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30"+
		"\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37"+
		"\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)"+
		"\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63"+
		"\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\6\5\u0085\n\5\r\5\16"+
		"\5\u0086\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7\u0093\n\7\f\7\16"+
		"\7\u0096\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\7\t\u00a6\n\t\f\t\16\t\u00a9\13\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22"+
		"\3\22\3\23\3\23\5\23\u00d5\n\23\3\23\3\23\3\24\3\24\5\24\u00db\n\24\3"+
		"\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3"+
		"\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\5\31\u00f4\n\31\3\31\3\31"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\5\34\u0101\n\34\3\34\7\34"+
		"\u0104\n\34\f\34\16\34\u0107\13\34\3\35\7\35\u010a\n\35\f\35\16\35\u010d"+
		"\13\35\3\35\5\35\u0110\n\35\3\35\3\35\3\36\6\36\u0115\n\36\r\36\16\36"+
		"\u0116\3\36\3\36\3\36\6\36\u011c\n\36\r\36\16\36\u011d\3\37\3\37\3\37"+
		"\3\37\3\37\5\37\u0125\n\37\3 \3 \3 \3 \3 \5 \u012c\n \5 \u012e\n \5 \u0130"+
		"\n \5 \u0132\n \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\5%\u0192\n%\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\3"+
		"&\3&\3&\3&\3&\5&\u01d2\n&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3(\3(\3"+
		"(\3(\3(\3(\3(\3(\3(\3(\3(\3(\5(\u01eb\n(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3"+
		")\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\5)\u0209\n)\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3*\3*\5*\u0215\n*\3+\3+\3,\5,\u021a\n,\3,\3,\3"+
		"-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65"+
		"\3\65\3\66\3\66\3\67\3\67\3\u00a7\28\6\4\b\5\n\6\f\7\16\b\20\t\22\n\24"+
		"\13\26\f\30\r\32\16\34\17\36\20 \21\"\22$\23&\24(\25*\26,\2.\2\60\27\62"+
		"\30\64\31\66\328\33:\2<\2>\2@\2B\2D\2F\2H\2J\2L\2N\2P\2R\2T\2V\2X\2Z\2"+
		"\\\2^\2`\2b\2d\2f\2h\2j\2l\2n\2p\2\6\2\3\4\5\n\5\2\f\f\17\17?@\6\2\f\f"+
		"\17\17@@^^\n\2$$))^^ddhhppttvv\5\2\62;CHch\13\2#\u0080\u00a2\u2001\u200d"+
		"\u200f\u2012\u2029\u2032\u2060\u2072\u3001\u3003\ud801\uf902\ufdd1\ufdf2"+
		"\uffff\4\2ccww\4\2\13\13\"\"\4\2\f\f\17\17\2\u0256\2\6\3\2\2\2\2\b\3\2"+
		"\2\2\2\n\3\2\2\2\2\f\3\2\2\2\3\16\3\2\2\2\3\20\3\2\2\2\3\22\3\2\2\2\3"+
		"\24\3\2\2\2\3\26\3\2\2\2\3\30\3\2\2\2\3\32\3\2\2\2\3\34\3\2\2\2\3\36\3"+
		"\2\2\2\3 \3\2\2\2\3\"\3\2\2\2\3$\3\2\2\2\3&\3\2\2\2\3(\3\2\2\2\4*\3\2"+
		"\2\2\4,\3\2\2\2\4.\3\2\2\2\4\60\3\2\2\2\4\62\3\2\2\2\5\64\3\2\2\2\5\66"+
		"\3\2\2\2\58\3\2\2\2\6r\3\2\2\2\bx\3\2\2\2\n}\3\2\2\2\f\u0084\3\2\2\2\16"+
		"\u008a\3\2\2\2\20\u008f\3\2\2\2\22\u0099\3\2\2\2\24\u009e\3\2\2\2\26\u00b1"+
		"\3\2\2\2\30\u00b5\3\2\2\2\32\u00b9\3\2\2\2\34\u00bd\3\2\2\2\36\u00c1\3"+
		"\2\2\2 \u00c5\3\2\2\2\"\u00c9\3\2\2\2$\u00cc\3\2\2\2&\u00cf\3\2\2\2(\u00d4"+
		"\3\2\2\2*\u00da\3\2\2\2,\u00de\3\2\2\2.\u00e4\3\2\2\2\60\u00e9\3\2\2\2"+
		"\62\u00ec\3\2\2\2\64\u00f3\3\2\2\2\66\u00f7\3\2\2\28\u00fa\3\2\2\2:\u00fe"+
		"\3\2\2\2<\u010b\3\2\2\2>\u0114\3\2\2\2@\u011f\3\2\2\2B\u0126\3\2\2\2D"+
		"\u0133\3\2\2\2F\u0135\3\2\2\2H\u0137\3\2\2\2J\u013a\3\2\2\2L\u0141\3\2"+
		"\2\2N\u01d1\3\2\2\2P\u01d3\3\2\2\2R\u01ea\3\2\2\2T\u0208\3\2\2\2V\u0214"+
		"\3\2\2\2X\u0216\3\2\2\2Z\u0219\3\2\2\2\\\u021d\3\2\2\2^\u021f\3\2\2\2"+
		"`\u0221\3\2\2\2b\u0223\3\2\2\2d\u0225\3\2\2\2f\u0227\3\2\2\2h\u0229\3"+
		"\2\2\2j\u022b\3\2\2\2l\u022d\3\2\2\2n\u022f\3\2\2\2p\u0231\3\2\2\2rs\7"+
		"\61\2\2st\7,\2\2tu\7,\2\2uv\3\2\2\2vw\b\2\2\2w\7\3\2\2\2xy\7\61\2\2yz"+
		"\7,\2\2z{\3\2\2\2{|\b\3\2\2|\t\3\2\2\2}~\7\61\2\2~\177\7\61\2\2\177\u0080"+
		"\3\2\2\2\u0080\u0081\b\4\3\2\u0081\13\3\2\2\2\u0082\u0085\5^.\2\u0083"+
		"\u0085\5\\-\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2"+
		"\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u0089\b\5\4\2\u0089\r\3\2\2\2\u008a\u008b\7,\2\2\u008b\u008c\7\61\2\2"+
		"\u008c\u008d\3\2\2\2\u008d\u008e\b\6\5\2\u008e\17\3\2\2\2\u008f\u0090"+
		"\13\2\2\2\u0090\u0094\6\7\2\2\u0091\u0093\5\\-\2\u0092\u0091\3\2\2\2\u0093"+
		"\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2"+
		"\2\2\u0096\u0094\3\2\2\2\u0097\u0098\5^.\2\u0098\21\3\2\2\2\u0099\u009a"+
		"\5l\65\2\u009a\u009b\6\b\3\2\u009b\u009c\3\2\2\2\u009c\u009d\b\b\4\2\u009d"+
		"\23\3\2\2\2\u009e\u009f\7>\2\2\u009f\u00a0\7r\2\2\u00a0\u00a1\7t\2\2\u00a1"+
		"\u00a2\7g\2\2\u00a2\u00a3\7@\2\2\u00a3\u00a7\3\2\2\2\u00a4\u00a6\13\2"+
		"\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7>"+
		"\2\2\u00ab\u00ac\7\61\2\2\u00ac\u00ad\7r\2\2\u00ad\u00ae\7t\2\2\u00ae"+
		"\u00af\7g\2\2\u00af\u00b0\7@\2\2\u00b0\25\3\2\2\2\u00b1\u00b2\5:\34\2"+
		"\u00b2\u00b3\5P\'\2\u00b3\u00b4\5<\35\2\u00b4\27\3\2\2\2\u00b5\u00b6\5"+
		":\34\2\u00b6\u00b7\5N&\2\u00b7\u00b8\5<\35\2\u00b8\31\3\2\2\2\u00b9\u00ba"+
		"\5:\34\2\u00ba\u00bb\5R(\2\u00bb\u00bc\5<\35\2\u00bc\33\3\2\2\2\u00bd"+
		"\u00be\5:\34\2\u00be\u00bf\5T)\2\u00bf\u00c0\5<\35\2\u00c0\35\3\2\2\2"+
		"\u00c1\u00c2\5:\34\2\u00c2\u00c3\5V*\2\u00c3\u00c4\5<\35\2\u00c4\37\3"+
		"\2\2\2\u00c5\u00c6\5H#\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\b\17\6\2\u00c8"+
		"!\3\2\2\2\u00c9\u00ca\5J$\2\u00ca\u00cb\6\20\4\2\u00cb#\3\2\2\2\u00cc"+
		"\u00cd\5L%\2\u00cd\u00ce\6\21\5\2\u00ce%\3\2\2\2\u00cf\u00d0\5F\"\2\u00d0"+
		"\u00d1\b\22\7\2\u00d1\'\3\2\2\2\u00d2\u00d5\5^.\2\u00d3\u00d5\5\\-\2\u00d4"+
		"\u00d2\3\2\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\b\23"+
		"\4\2\u00d7)\3\2\2\2\u00d8\u00db\5Z,\2\u00d9\u00db\7\2\2\3\u00da\u00d8"+
		"\3\2\2\2\u00da\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\b\24\5\2"+
		"\u00dd+\3\2\2\2\u00de\u00df\5:\34\2\u00df\u00e0\5N&\2\u00e0\u00e1\5<\35"+
		"\2\u00e1\u00e2\3\2\2\2\u00e2\u00e3\b\25\b\2\u00e3-\3\2\2\2\u00e4\u00e5"+
		"\5H#\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\b\26\t\2\u00e7\u00e8\b\26\6\2\u00e8"+
		"/\3\2\2\2\u00e9\u00ea\5F\"\2\u00ea\u00eb\b\27\n\2\u00eb\61\3\2\2\2\u00ec"+
		"\u00ed\5\\-\2\u00ed\u00ee\3\2\2\2\u00ee\u00ef\b\30\4\2\u00ef\63\3\2\2"+
		"\2\u00f0\u00f4\5d\61\2\u00f1\u00f4\5Z,\2\u00f2\u00f4\7\2\2\3\u00f3\u00f0"+
		"\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5"+
		"\u00f6\b\31\5\2\u00f6\65\3\2\2\2\u00f7\u00f8\5F\"\2\u00f8\u00f9\b\32\13"+
		"\2\u00f9\67\3\2\2\2\u00fa\u00fb\5\\-\2\u00fb\u00fc\3\2\2\2\u00fc\u00fd"+
		"\b\33\4\2\u00fd9\3\2\2\2\u00fe\u0100\5f\62\2\u00ff\u0101\5j\64\2\u0100"+
		"\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0105\3\2\2\2\u0102\u0104\5\\"+
		"-\2\u0103\u0102\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3\2\2\2\u0105"+
		"\u0106\3\2\2\2\u0106;\3\2\2\2\u0107\u0105\3\2\2\2\u0108\u010a\5>\36\2"+
		"\u0109\u0108\3\2\2\2\u010a\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c"+
		"\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010b\3\2\2\2\u010e\u0110\5j\64\2\u010f"+
		"\u010e\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\5h"+
		"\63\2\u0112=\3\2\2\2\u0113\u0115\n\2\2\2\u0114\u0113\3\2\2\2\u0115\u0116"+
		"\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u011b\5p\67\2\u0119\u011c\n\3\2\2\u011a\u011c\5@\37\2\u011b\u0119\3\2"+
		"\2\2\u011b\u011a\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011b\3\2\2\2\u011d"+
		"\u011e\3\2\2\2\u011e?\3\2\2\2\u011f\u0124\5X+\2\u0120\u0125\t\4\2\2\u0121"+
		"\u0125\5B \2\u0122\u0125\13\2\2\2\u0123\u0125\7\2\2\3\u0124\u0120\3\2"+
		"\2\2\u0124\u0121\3\2\2\2\u0124\u0122\3\2\2\2\u0124\u0123\3\2\2\2\u0125"+
		"A\3\2\2\2\u0126\u0131\7w\2\2\u0127\u012f\5D!\2\u0128\u012d\5D!\2\u0129"+
		"\u012b\5D!\2\u012a\u012c\5D!\2\u012b\u012a\3\2\2\2\u012b\u012c\3\2\2\2"+
		"\u012c\u012e\3\2\2\2\u012d\u0129\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u0130"+
		"\3\2\2\2\u012f\u0128\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0132\3\2\2\2\u0131"+
		"\u0127\3\2\2\2\u0131\u0132\3\2\2\2\u0132C\3\2\2\2\u0133\u0134\t\5\2\2"+
		"\u0134E\3\2\2\2\u0135\u0136\t\6\2\2\u0136G\3\2\2\2\u0137\u0138\7}\2\2"+
		"\u0138\u0139\7B\2\2\u0139I\3\2\2\2\u013a\u013b\7B\2\2\u013b\u013c\7r\2"+
		"\2\u013c\u013d\7c\2\2\u013d\u013e\7t\2\2\u013e\u013f\7c\2\2\u013f\u0140"+
		"\7o\2\2\u0140K\3\2\2\2\u0141\u0191\5n\66\2\u0142\u0143\7c\2\2\u0143\u0144"+
		"\7w\2\2\u0144\u0145\7v\2\2\u0145\u0146\7j\2\2\u0146\u0147\7q\2\2\u0147"+
		"\u0192\7t\2\2\u0148\u0149\7x\2\2\u0149\u014a\7g\2\2\u014a\u014b\7t\2\2"+
		"\u014b\u014c\7u\2\2\u014c\u014d\7k\2\2\u014d\u014e\7q\2\2\u014e\u0192"+
		"\7p\2\2\u014f\u0150\7t\2\2\u0150\u0151\7g\2\2\u0151\u0152\7v\2\2\u0152"+
		"\u0153\7w\2\2\u0153\u0154\7t\2\2\u0154\u0192\7p\2\2\u0155\u0156\7g\2\2"+
		"\u0156\u0157\7z\2\2\u0157\u0158\7e\2\2\u0158\u0159\7g\2\2\u0159\u015a"+
		"\7r\2\2\u015a\u015b\7v\2\2\u015b\u015c\7k\2\2\u015c\u015d\7q\2\2\u015d"+
		"\u0192\7p\2\2\u015e\u015f\7v\2\2\u015f\u0160\7j\2\2\u0160\u0161\7t\2\2"+
		"\u0161\u0162\7q\2\2\u0162\u0163\7y\2\2\u0163\u0192\7u\2\2\u0164\u0165"+
		"\7u\2\2\u0165\u0166\7g\2\2\u0166\u0192\7g\2\2\u0167\u0168\7u\2\2\u0168"+
		"\u0169\7k\2\2\u0169\u016a\7p\2\2\u016a\u016b\7e\2\2\u016b\u0192\7g\2\2"+
		"\u016c\u016d\7u\2\2\u016d\u016e\7g\2\2\u016e\u016f\7t\2\2\u016f\u0170"+
		"\7k\2\2\u0170\u0171\7c\2\2\u0171\u0192\7n\2\2\u0172\u0173\7u\2\2\u0173"+
		"\u0174\7g\2\2\u0174\u0175\7t\2\2\u0175\u0176\7k\2\2\u0176\u0177\7c\2\2"+
		"\u0177\u0178\7n\2\2\u0178\u0179\7H\2\2\u0179\u017a\7k\2\2\u017a\u017b"+
		"\7g\2\2\u017b\u017c\7n\2\2\u017c\u0192\7f\2\2\u017d\u017e\7u\2\2\u017e"+
		"\u017f\7g\2\2\u017f\u0180\7t\2\2\u0180\u0181\7k\2\2\u0181\u0182\7c\2\2"+
		"\u0182\u0183\7n\2\2\u0183\u0184\7F\2\2\u0184\u0185\7c\2\2\u0185\u0186"+
		"\7v\2\2\u0186\u0192\7c\2\2\u0187\u0188\7f\2\2\u0188\u0189\7g\2\2\u0189"+
		"\u018a\7r\2\2\u018a\u018b\7t\2\2\u018b\u018c\7g\2\2\u018c\u018d\7e\2\2"+
		"\u018d\u018e\7c\2\2\u018e\u018f\7v\2\2\u018f\u0190\7g\2\2\u0190\u0192"+
		"\7f\2\2\u0191\u0142\3\2\2\2\u0191\u0148\3\2\2\2\u0191\u014f\3\2\2\2\u0191"+
		"\u0155\3\2\2\2\u0191\u015e\3\2\2\2\u0191\u0164\3\2\2\2\u0191\u0167\3\2"+
		"\2\2\u0191\u016c\3\2\2\2\u0191\u0172\3\2\2\2\u0191\u017d\3\2\2\2\u0191"+
		"\u0187\3\2\2\2\u0192M\3\2\2\2\u0193\u01d2\7d\2\2\u0194\u0195\7f\2\2\u0195"+
		"\u0196\7g\2\2\u0196\u01d2\7n\2\2\u0197\u0198\7g\2\2\u0198\u01d2\7o\2\2"+
		"\u0199\u01d2\7k\2\2\u019a\u019b\7u\2\2\u019b\u019c\7c\2\2\u019c\u019d"+
		"\7o\2\2\u019d\u01d2\7r\2\2\u019e\u019f\7u\2\2\u019f\u01a0\7o\2\2\u01a0"+
		"\u01a1\7c\2\2\u01a1\u01a2\7n\2\2\u01a2\u01d2\7n\2\2\u01a3\u01a4\7u\2\2"+
		"\u01a4\u01a5\7v\2\2\u01a5\u01a6\7t\2\2\u01a6\u01a7\7q\2\2\u01a7\u01a8"+
		"\7p\2\2\u01a8\u01d2\7i\2\2\u01a9\u01aa\7v\2\2\u01aa\u01d2\7v\2\2\u01ab"+
		"\u01d2\t\7\2\2\u01ac\u01ad\7c\2\2\u01ad\u01ae\7d\2\2\u01ae\u01af\7d\2"+
		"\2\u01af\u01d2\7t\2\2\u01b0\u01b1\7e\2\2\u01b1\u01b2\7k\2\2\u01b2\u01b3"+
		"\7v\2\2\u01b3\u01d2\7g\2\2\u01b4\u01b5\7e\2\2\u01b5\u01b6\7q\2\2\u01b6"+
		"\u01b7\7f\2\2\u01b7\u01d2\7g\2\2\u01b8\u01b9\7f\2\2\u01b9\u01ba\7h\2\2"+
		"\u01ba\u01d2\7p\2\2\u01bb\u01bc\7k\2\2\u01bc\u01bd\7o\2\2\u01bd\u01d2"+
		"\7i\2\2\u01be\u01bf\7o\2\2\u01bf\u01c0\7c\2\2\u01c0\u01c1\7t\2\2\u01c1"+
		"\u01d2\7m\2\2\u01c2\u01d2\7s\2\2\u01c3\u01c4\7u\2\2\u01c4\u01c5\7r\2\2"+
		"\u01c5\u01c6\7c\2\2\u01c6\u01d2\7p\2\2\u01c7\u01c8\7u\2\2\u01c8\u01c9"+
		"\7w\2\2\u01c9\u01d2\7d\2\2\u01ca\u01cb\7u\2\2\u01cb\u01cc\7w\2\2\u01cc"+
		"\u01d2\7r\2\2\u01cd\u01ce\7v\2\2\u01ce\u01cf\7k\2\2\u01cf\u01d0\7o\2\2"+
		"\u01d0\u01d2\7g\2\2\u01d1\u0193\3\2\2\2\u01d1\u0194\3\2\2\2\u01d1\u0197"+
		"\3\2\2\2\u01d1\u0199\3\2\2\2\u01d1\u019a\3\2\2\2\u01d1\u019e\3\2\2\2\u01d1"+
		"\u01a3\3\2\2\2\u01d1\u01a9\3\2\2\2\u01d1\u01ab\3\2\2\2\u01d1\u01ac\3\2"+
		"\2\2\u01d1\u01b0\3\2\2\2\u01d1\u01b4\3\2\2\2\u01d1\u01b8\3\2\2\2\u01d1"+
		"\u01bb\3\2\2\2\u01d1\u01be\3\2\2\2\u01d1\u01c2\3\2\2\2\u01d1\u01c3\3\2"+
		"\2\2\u01d1\u01c7\3\2\2\2\u01d1\u01ca\3\2\2\2\u01d1\u01cd\3\2\2\2\u01d2"+
		"O\3\2\2\2\u01d3\u01d4\7d\2\2\u01d4\u01d5\7t\2\2\u01d5Q\3\2\2\2\u01d6\u01eb"+
		"\7r\2\2\u01d7\u01d8\7j\2\2\u01d8\u01eb\7t\2\2\u01d9\u01da\7j\2\2\u01da"+
		"\u01db\7g\2\2\u01db\u01dc\7c\2\2\u01dc\u01dd\7f\2\2\u01dd\u01de\7g\2\2"+
		"\u01de\u01eb\7t\2\2\u01df\u01e0\7o\2\2\u01e0\u01e1\7c\2\2\u01e1\u01e2"+
		"\7k\2\2\u01e2\u01eb\7p\2\2\u01e3\u01e4\7u\2\2\u01e4\u01e5\7g\2\2\u01e5"+
		"\u01e6\7e\2\2\u01e6\u01e7\7v\2\2\u01e7\u01e8\7k\2\2\u01e8\u01e9\7q\2\2"+
		"\u01e9\u01eb\7p\2\2\u01ea\u01d6\3\2\2\2\u01ea\u01d7\3\2\2\2\u01ea\u01d9"+
		"\3\2\2\2\u01ea\u01df\3\2\2\2\u01ea\u01e3\3\2\2\2\u01ebS\3\2\2\2\u01ec"+
		"\u01ed\7q\2\2\u01ed\u0209\7n\2\2\u01ee\u01ef\7w\2\2\u01ef\u0209\7n\2\2"+
		"\u01f0\u01f1\7f\2\2\u01f1\u0209\7n\2\2\u01f2\u01f3\7v\2\2\u01f3\u01f4"+
		"\7c\2\2\u01f4\u01f5\7d\2\2\u01f5\u01f6\7n\2\2\u01f6\u0209\7g\2\2\u01f7"+
		"\u01f8\7v\2\2\u01f8\u01f9\7j\2\2\u01f9\u01fa\7g\2\2\u01fa\u01fb\7c\2\2"+
		"\u01fb\u0209\7f\2\2\u01fc\u01fd\7v\2\2\u01fd\u01fe\7d\2\2\u01fe\u01ff"+
		"\7q\2\2\u01ff\u0200\7f\2\2\u0200\u0209\7{\2\2\u0201\u0202\7v\2\2\u0202"+
		"\u0203\7h\2\2\u0203\u0204\7q\2\2\u0204\u0205\7q\2\2\u0205\u0209\7v\2\2"+
		"\u0206\u0207\7v\2\2\u0207\u0209\7t\2\2\u0208\u01ec\3\2\2\2\u0208\u01ee"+
		"\3\2\2\2\u0208\u01f0\3\2\2\2\u0208\u01f2\3\2\2\2\u0208\u01f7\3\2\2\2\u0208"+
		"\u01fc\3\2\2\2\u0208\u0201\3\2\2\2\u0208\u0206\3\2\2\2\u0209U\3\2\2\2"+
		"\u020a\u020b\7n\2\2\u020b\u0215\7k\2\2\u020c\u020d\7f\2\2\u020d\u0215"+
		"\7f\2\2\u020e\u020f\7f\2\2\u020f\u0215\7v\2\2\u0210\u0211\7v\2\2\u0211"+
		"\u0215\7f\2\2\u0212\u0213\7v\2\2\u0213\u0215\7j\2\2\u0214\u020a\3\2\2"+
		"\2\u0214\u020c\3\2\2\2\u0214\u020e\3\2\2\2\u0214\u0210\3\2\2\2\u0214\u0212"+
		"\3\2\2\2\u0215W\3\2\2\2\u0216\u0217\7^\2\2\u0217Y\3\2\2\2\u0218\u021a"+
		"\7\17\2\2\u0219\u0218\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u021b\3\2\2\2"+
		"\u021b\u021c\7\f\2\2\u021c[\3\2\2\2\u021d\u021e\t\b\2\2\u021e]\3\2\2\2"+
		"\u021f\u0220\t\t\2\2\u0220_\3\2\2\2\u0221\u0222\7a\2\2\u0222a\3\2\2\2"+
		"\u0223\u0224\7}\2\2\u0224c\3\2\2\2\u0225\u0226\7\177\2\2\u0226e\3\2\2"+
		"\2\u0227\u0228\7>\2\2\u0228g\3\2\2\2\u0229\u022a\7@\2\2\u022ai\3\2\2\2"+
		"\u022b\u022c\7\61\2\2\u022ck\3\2\2\2\u022d\u022e\7,\2\2\u022em\3\2\2\2"+
		"\u022f\u0230\7B\2\2\u0230o\3\2\2\2\u0231\u0232\7?\2\2\u0232q\3\2\2\2\37"+
		"\2\3\4\5\u0084\u0086\u0094\u00a7\u00d4\u00da\u00f3\u0100\u0105\u010b\u010f"+
		"\u0116\u011b\u011d\u0124\u012b\u012d\u012f\u0131\u0191\u01d1\u01ea\u0208"+
		"\u0214\u0219\f\7\3\2\7\4\2\b\2\2\6\2\2\7\5\2\3\22\2\t\r\2\t\21\2\3\27"+
		"\3\3\32\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}