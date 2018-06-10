// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/xvisitor/parser/XVisitorLexer.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.xvisitor.parser.gen;
	import net.certiv.adept.lang.xvisitor.parser.XVLexerAdaptor;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XVisitorLexer extends XVLexerAdaptor {
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
		Options=1, ActionBlock=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "Options", "ActionBlock"
	};

	public static final String[] ruleNames = {
		"BLOCK_COMMENT", "LINE_COMMENT", "OPTIONS", "LBRACE", "GRAMMAR", "XVISITOR", 
		"COLON", "COMMA", "SEMI", "ASSIGN", "QUESTION", "STAR", "AT", "ANY", "SEP", 
		"DOT", "OR", "NOT", "ID", "LITERAL", "HORZ_WS", "VERT_WS", "ERRCHAR", 
		"OPT_LBRACE", "OPT_RBRACE", "OPT_ID", "OPT_LITERAL", "OPT_DOT", "OPT_ASSIGN", 
		"OPT_SEMI", "OPT_STAR", "OPT_INT", "OPT_DOC_COMMENT", "OPT_BLOCK_COMMENT", 
		"OPT_LINE_COMMENT", "OPT_HORZ_WS", "OPT_VERT_WS", "ABLOCK_LBRACE", "ABLOCK_RBRACE", 
		"ABLOCK_DOC_COMMENT", "ABLOCK_BLOCK_COMMENT", "ABLOCK_LINE_COMMENT", "ONENTRY", 
		"ONEXIT", "ABLOCK_STRING", "ABLOCK_CHAR", "REFERENCE", "ABLOCK_TEXT", 
		"ABLOCK_OTHER", "Colon", "Comma", "Semi", "Assign", "Question", "Star", 
		"At", "Any", "Sep", "Bang", "Dot", "LBrace", "RBrace", "Or", "Not", "Dollar", 
		"Hws", "Vws", "DocComment", "BlockComment", "LineComment", "NameChar", 
		"NameStartChar", "SQLiteral", "DQLiteral", "EscSeq", "Int", "HexDigit"
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



		private void handleRightTerminator(int nestedType, int defaultType) {
			popMode();
			if ( _modeStack.size() > 0 ) {
				setType(nestedType);
			} else {
				setType(defaultType);
			}
		}


	public XVisitorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "XVisitorLexer.g4"; }

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
		case 38:
			ABLOCK_RBRACE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ABLOCK_RBRACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 handleRightTerminator(TEXT, RBRACE); 
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return BLOCK_COMMENT_sempred((RuleContext)_localctx, predIndex);
		case 1:
			return LINE_COMMENT_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean BLOCK_COMMENT_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  bcSuffix() ;
		}
		return true;
	}
	private boolean LINE_COMMENT_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return  lcPrefix() ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u0217\b\1\b\1\b"+
		"\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n"+
		"\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21"+
		"\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30"+
		"\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37"+
		"\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t"+
		"*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63"+
		"\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t"+
		"<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4"+
		"H\tH\4I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\3\2\3\2\3\2\5\2\u00a3\n\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3"+
		"\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\7\24\u00e5"+
		"\n\24\f\24\16\24\u00e8\13\24\3\25\3\25\5\25\u00ec\n\25\3\26\6\26\u00ef"+
		"\n\26\r\26\16\26\u00f0\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3"+
		"\30\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\7\33\u0105\n\33\f\33\16\33"+
		"\u0108\13\33\3\34\3\34\5\34\u010c\n\34\3\35\3\35\3\36\3\36\3\37\3\37\3"+
		" \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\6%\u0128"+
		"\n%\r%\16%\u0129\3%\3%\3%\3&\6&\u0130\n&\r&\16&\u0131\3&\3&\3&\3\'\3\'"+
		"\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3,\3,\7,\u014d"+
		"\n,\f,\16,\u0150\13,\3,\3,\3,\3,\3,\3,\3,\3,\3,\3-\3-\7-\u015d\n-\f-\16"+
		"-\u0160\13-\3-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3\60\3\60"+
		"\6\60\u0174\n\60\r\60\16\60\u0175\3\60\3\60\6\60\u017a\n\60\r\60\16\60"+
		"\u017b\5\60\u017e\n\60\3\61\6\61\u0181\n\61\r\61\16\61\u0182\3\61\3\61"+
		"\3\62\3\62\3\62\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67"+
		"\38\38\39\39\3:\3:\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3A\3A\3B\3B"+
		"\3C\3C\3D\5D\u01af\nD\3D\3D\3E\3E\3E\3E\3E\7E\u01b8\nE\fE\16E\u01bb\13"+
		"E\3E\3E\3E\3F\3F\3F\3F\7F\u01c4\nF\fF\16F\u01c7\13F\3F\3F\3F\3G\3G\7G"+
		"\u01ce\nG\fG\16G\u01d1\13G\3G\3G\7G\u01d5\nG\fG\16G\u01d8\13G\3G\3G\7"+
		"G\u01dc\nG\fG\16G\u01df\13G\7G\u01e1\nG\fG\16G\u01e4\13G\3H\3H\5H\u01e8"+
		"\nH\3I\3I\3J\3J\3J\7J\u01ef\nJ\fJ\16J\u01f2\13J\3J\3J\3K\3K\3K\7K\u01f9"+
		"\nK\fK\16K\u01fc\13K\3K\3K\3L\3L\3L\3L\3L\3L\5L\u0206\nL\5L\u0208\nL\5"+
		"L\u020a\nL\5L\u020c\nL\3L\5L\u020f\nL\3M\6M\u0212\nM\rM\16M\u0213\3N\3"+
		"N\4\u01b9\u01c5\2O\5\5\7\6\t\7\13\b\r\t\17\n\21\13\23\f\25\r\27\16\31"+
		"\17\33\20\35\21\37\22!\23#\24%\25\'\26)\27+\30-\31/\32\61\33\63\34\65"+
		"\35\67\369\37; =!?\"A#C$E\2G\2I\2K\2M\2O\2Q%S\2U\2W\2Y&[\']\2_\2a(c\2"+
		"e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2\u0085\2"+
		"\u0087\2\u0089\2\u008b\2\u008d\2\u008f\2\u0091\2\u0093\2\u0095\2\u0097"+
		"\2\u0099\2\u009b\2\u009d\2\5\2\3\4\13\4\2\13\13\"\"\4\2\f\f\16\16\3\2"+
		"\f\f\7\2\62;aa\u00b9\u00b9\u0302\u0371\u2041\u2042\17\2C\\c|\u00c2\u00d8"+
		"\u00da\u00f8\u00fa\u0301\u0372\u037f\u0381\u2001\u200e\u200f\u2072\u2191"+
		"\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\4\2))^^\4\2$$^^\3\2\62"+
		";\5\2\62;CHch\2\u021a\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\3\63\3\2\2\2\3\65\3\2\2\2\3\67\3\2\2\2\39\3\2"+
		"\2\2\3;\3\2\2\2\3=\3\2\2\2\3?\3\2\2\2\3A\3\2\2\2\3C\3\2\2\2\3E\3\2\2\2"+
		"\3G\3\2\2\2\3I\3\2\2\2\3K\3\2\2\2\3M\3\2\2\2\4O\3\2\2\2\4Q\3\2\2\2\4S"+
		"\3\2\2\2\4U\3\2\2\2\4W\3\2\2\2\4Y\3\2\2\2\4[\3\2\2\2\4]\3\2\2\2\4_\3\2"+
		"\2\2\4a\3\2\2\2\4c\3\2\2\2\4e\3\2\2\2\5\u009f\3\2\2\2\7\u00a6\3\2\2\2"+
		"\t\u00ab\3\2\2\2\13\u00b5\3\2\2\2\r\u00b9\3\2\2\2\17\u00c1\3\2\2\2\21"+
		"\u00ca\3\2\2\2\23\u00cc\3\2\2\2\25\u00ce\3\2\2\2\27\u00d0\3\2\2\2\31\u00d2"+
		"\3\2\2\2\33\u00d4\3\2\2\2\35\u00d6\3\2\2\2\37\u00d8\3\2\2\2!\u00da\3\2"+
		"\2\2#\u00dc\3\2\2\2%\u00de\3\2\2\2\'\u00e0\3\2\2\2)\u00e2\3\2\2\2+\u00eb"+
		"\3\2\2\2-\u00ee\3\2\2\2/\u00f4\3\2\2\2\61\u00f8\3\2\2\2\63\u00fc\3\2\2"+
		"\2\65\u00fe\3\2\2\2\67\u0102\3\2\2\29\u010b\3\2\2\2;\u010d\3\2\2\2=\u010f"+
		"\3\2\2\2?\u0111\3\2\2\2A\u0113\3\2\2\2C\u0115\3\2\2\2E\u0117\3\2\2\2G"+
		"\u011c\3\2\2\2I\u0121\3\2\2\2K\u0127\3\2\2\2M\u012f\3\2\2\2O\u0136\3\2"+
		"\2\2Q\u013b\3\2\2\2S\u013e\3\2\2\2U\u0142\3\2\2\2W\u0146\3\2\2\2Y\u014e"+
		"\3\2\2\2[\u015e\3\2\2\2]\u0169\3\2\2\2_\u016d\3\2\2\2a\u0171\3\2\2\2c"+
		"\u0180\3\2\2\2e\u0186\3\2\2\2g\u018a\3\2\2\2i\u018c\3\2\2\2k\u018e\3\2"+
		"\2\2m\u0190\3\2\2\2o\u0192\3\2\2\2q\u0194\3\2\2\2s\u0196\3\2\2\2u\u0198"+
		"\3\2\2\2w\u019b\3\2\2\2y\u019d\3\2\2\2{\u019f\3\2\2\2}\u01a1\3\2\2\2\177"+
		"\u01a3\3\2\2\2\u0081\u01a5\3\2\2\2\u0083\u01a7\3\2\2\2\u0085\u01a9\3\2"+
		"\2\2\u0087\u01ab\3\2\2\2\u0089\u01ae\3\2\2\2\u008b\u01b2\3\2\2\2\u008d"+
		"\u01bf\3\2\2\2\u008f\u01cb\3\2\2\2\u0091\u01e7\3\2\2\2\u0093\u01e9\3\2"+
		"\2\2\u0095\u01eb\3\2\2\2\u0097\u01f5\3\2\2\2\u0099\u01ff\3\2\2\2\u009b"+
		"\u0211\3\2\2\2\u009d\u0215\3\2\2\2\u009f\u00a2\6\2\2\2\u00a0\u00a3\5\u008b"+
		"E\2\u00a1\u00a3\5\u008dF\2\u00a2\u00a0\3\2\2\2\u00a2\u00a1\3\2\2\2\u00a3"+
		"\u00a4\3\2\2\2\u00a4\u00a5\b\2\2\2\u00a5\6\3\2\2\2\u00a6\u00a7\6\3\3\2"+
		"\u00a7\u00a8\5\u008fG\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\b\3\2\2\u00aa"+
		"\b\3\2\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad\7r\2\2\u00ad\u00ae\7v\2\2\u00ae"+
		"\u00af\7k\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7p\2\2\u00b1\u00b2\7u\2\2"+
		"\u00b2\u00b3\3\2\2\2\u00b3\u00b4\b\4\3\2\u00b4\n\3\2\2\2\u00b5\u00b6\5"+
		"}>\2\u00b6\u00b7\3\2\2\2\u00b7\u00b8\b\5\4\2\u00b8\f\3\2\2\2\u00b9\u00ba"+
		"\7i\2\2\u00ba\u00bb\7t\2\2\u00bb\u00bc\7c\2\2\u00bc\u00bd\7o\2\2\u00bd"+
		"\u00be\7o\2\2\u00be\u00bf\7c\2\2\u00bf\u00c0\7t\2\2\u00c0\16\3\2\2\2\u00c1"+
		"\u00c2\7z\2\2\u00c2\u00c3\7x\2\2\u00c3\u00c4\7k\2\2\u00c4\u00c5\7u\2\2"+
		"\u00c5\u00c6\7k\2\2\u00c6\u00c7\7v\2\2\u00c7\u00c8\7q\2\2\u00c8\u00c9"+
		"\7t\2\2\u00c9\20\3\2\2\2\u00ca\u00cb\5g\63\2\u00cb\22\3\2\2\2\u00cc\u00cd"+
		"\5i\64\2\u00cd\24\3\2\2\2\u00ce\u00cf\5k\65\2\u00cf\26\3\2\2\2\u00d0\u00d1"+
		"\5m\66\2\u00d1\30\3\2\2\2\u00d2\u00d3\5o\67\2\u00d3\32\3\2\2\2\u00d4\u00d5"+
		"\5q8\2\u00d5\34\3\2\2\2\u00d6\u00d7\5s9\2\u00d7\36\3\2\2\2\u00d8\u00d9"+
		"\5u:\2\u00d9 \3\2\2\2\u00da\u00db\5w;\2\u00db\"\3\2\2\2\u00dc\u00dd\5"+
		"{=\2\u00dd$\3\2\2\2\u00de\u00df\5\u0081@\2\u00df&\3\2\2\2\u00e0\u00e1"+
		"\5\u0083A\2\u00e1(\3\2\2\2\u00e2\u00e6\5\u0093I\2\u00e3\u00e5\5\u0091"+
		"H\2\u00e4\u00e3\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6"+
		"\u00e7\3\2\2\2\u00e7*\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00ec\5\u0095"+
		"J\2\u00ea\u00ec\5\u0097K\2\u00eb\u00e9\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec"+
		",\3\2\2\2\u00ed\u00ef\5\u0087C\2\u00ee\u00ed\3\2\2\2\u00ef\u00f0\3\2\2"+
		"\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3"+
		"\b\26\2\2\u00f3.\3\2\2\2\u00f4\u00f5\5\u0089D\2\u00f5\u00f6\3\2\2\2\u00f6"+
		"\u00f7\b\27\2\2\u00f7\60\3\2\2\2\u00f8\u00f9\13\2\2\2\u00f9\u00fa\3\2"+
		"\2\2\u00fa\u00fb\b\30\5\2\u00fb\62\3\2\2\2\u00fc\u00fd\5}>\2\u00fd\64"+
		"\3\2\2\2\u00fe\u00ff\5\177?\2\u00ff\u0100\3\2\2\2\u0100\u0101\b\32\6\2"+
		"\u0101\66\3\2\2\2\u0102\u0106\5\u0093I\2\u0103\u0105\5\u0091H\2\u0104"+
		"\u0103\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2"+
		"\2\2\u01078\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010c\5\u0097K\2\u010a\u010c"+
		"\5\u0095J\2\u010b\u0109\3\2\2\2\u010b\u010a\3\2\2\2\u010c:\3\2\2\2\u010d"+
		"\u010e\5{=\2\u010e<\3\2\2\2\u010f\u0110\5m\66\2\u0110>\3\2\2\2\u0111\u0112"+
		"\5k\65\2\u0112@\3\2\2\2\u0113\u0114\5q8\2\u0114B\3\2\2\2\u0115\u0116\5"+
		"\u009bM\2\u0116D\3\2\2\2\u0117\u0118\5\u008bE\2\u0118\u0119\3\2\2\2\u0119"+
		"\u011a\b\"\7\2\u011a\u011b\b\"\2\2\u011bF\3\2\2\2\u011c\u011d\5\u008d"+
		"F\2\u011d\u011e\3\2\2\2\u011e\u011f\b#\7\2\u011f\u0120\b#\2\2\u0120H\3"+
		"\2\2\2\u0121\u0122\5\u008fG\2\u0122\u0123\3\2\2\2\u0123\u0124\b$\b\2\u0124"+
		"\u0125\b$\2\2\u0125J\3\2\2\2\u0126\u0128\5\u0087C\2\u0127\u0126\3\2\2"+
		"\2\u0128\u0129\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b"+
		"\3\2\2\2\u012b\u012c\b%\t\2\u012c\u012d\b%\2\2\u012dL\3\2\2\2\u012e\u0130"+
		"\5\u0089D\2\u012f\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u012f\3\2\2"+
		"\2\u0131\u0132\3\2\2\2\u0132\u0133\3\2\2\2\u0133\u0134\b&\n\2\u0134\u0135"+
		"\b&\2\2\u0135N\3\2\2\2\u0136\u0137\5}>\2\u0137\u0138\3\2\2\2\u0138\u0139"+
		"\b\'\13\2\u0139\u013a\b\'\4\2\u013aP\3\2\2\2\u013b\u013c\5\177?\2\u013c"+
		"\u013d\b(\f\2\u013dR\3\2\2\2\u013e\u013f\5\u008bE\2\u013f\u0140\3\2\2"+
		"\2\u0140\u0141\b)\13\2\u0141T\3\2\2\2\u0142\u0143\5\u008dF\2\u0143\u0144"+
		"\3\2\2\2\u0144\u0145\b*\13\2\u0145V\3\2\2\2\u0146\u0147\5\u008fG\2\u0147"+
		"\u0148\3\2\2\2\u0148\u0149\b+\13\2\u0149X\3\2\2\2\u014a\u014d\5\u0087"+
		"C\2\u014b\u014d\5\u0089D\2\u014c\u014a\3\2\2\2\u014c\u014b\3\2\2\2\u014d"+
		"\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0151\3\2"+
		"\2\2\u0150\u014e\3\2\2\2\u0151\u0152\7q\2\2\u0152\u0153\7p\2\2\u0153\u0154"+
		"\7G\2\2\u0154\u0155\7p\2\2\u0155\u0156\7v\2\2\u0156\u0157\7t\2\2\u0157"+
		"\u0158\7{\2\2\u0158\u0159\7<\2\2\u0159Z\3\2\2\2\u015a\u015d\5\u0087C\2"+
		"\u015b\u015d\5\u0089D\2\u015c\u015a\3\2\2\2\u015c\u015b\3\2\2\2\u015d"+
		"\u0160\3\2\2\2\u015e\u015c\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0161\3\2"+
		"\2\2\u0160\u015e\3\2\2\2\u0161\u0162\7q\2\2\u0162\u0163\7p\2\2\u0163\u0164"+
		"\7G\2\2\u0164\u0165\7z\2\2\u0165\u0166\7k\2\2\u0166\u0167\7v\2\2\u0167"+
		"\u0168\7<\2\2\u0168\\\3\2\2\2\u0169\u016a\5\u0097K\2\u016a\u016b\3\2\2"+
		"\2\u016b\u016c\b.\13\2\u016c^\3\2\2\2\u016d\u016e\5\u0095J\2\u016e\u016f"+
		"\3\2\2\2\u016f\u0170\b/\13\2\u0170`\3\2\2\2\u0171\u0173\5\u0085B\2\u0172"+
		"\u0174\5\u0091H\2\u0173\u0172\3\2\2\2\u0174\u0175\3\2\2\2\u0175\u0173"+
		"\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u017d\3\2\2\2\u0177\u0179\5{=\2\u0178"+
		"\u017a\5\u0091H\2\u0179\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b\u0179"+
		"\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017e\3\2\2\2\u017d\u0177\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017eb\3\2\2\2\u017f\u0181\5\u0091H\2\u0180\u017f\3\2\2"+
		"\2\u0181\u0182\3\2\2\2\u0182\u0180\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184"+
		"\3\2\2\2\u0184\u0185\b\61\13\2\u0185d\3\2\2\2\u0186\u0187\13\2\2\2\u0187"+
		"\u0188\3\2\2\2\u0188\u0189\b\62\13\2\u0189f\3\2\2\2\u018a\u018b\7<\2\2"+
		"\u018bh\3\2\2\2\u018c\u018d\7.\2\2\u018dj\3\2\2\2\u018e\u018f\7=\2\2\u018f"+
		"l\3\2\2\2\u0190\u0191\7?\2\2\u0191n\3\2\2\2\u0192\u0193\7A\2\2\u0193p"+
		"\3\2\2\2\u0194\u0195\7,\2\2\u0195r\3\2\2\2\u0196\u0197\7B\2\2\u0197t\3"+
		"\2\2\2\u0198\u0199\7\61\2\2\u0199\u019a\7\61\2\2\u019av\3\2\2\2\u019b"+
		"\u019c\7\61\2\2\u019cx\3\2\2\2\u019d\u019e\7#\2\2\u019ez\3\2\2\2\u019f"+
		"\u01a0\7\60\2\2\u01a0|\3\2\2\2\u01a1\u01a2\7}\2\2\u01a2~\3\2\2\2\u01a3"+
		"\u01a4\7\177\2\2\u01a4\u0080\3\2\2\2\u01a5\u01a6\7~\2\2\u01a6\u0082\3"+
		"\2\2\2\u01a7\u01a8\7#\2\2\u01a8\u0084\3\2\2\2\u01a9\u01aa\7&\2\2\u01aa"+
		"\u0086\3\2\2\2\u01ab\u01ac\t\2\2\2\u01ac\u0088\3\2\2\2\u01ad\u01af\7\17"+
		"\2\2\u01ae\u01ad\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0"+
		"\u01b1\t\3\2\2\u01b1\u008a\3\2\2\2\u01b2\u01b3\7\61\2\2\u01b3\u01b4\7"+
		",\2\2\u01b4\u01b5\7,\2\2\u01b5\u01b9\3\2\2\2\u01b6\u01b8\13\2\2\2\u01b7"+
		"\u01b6\3\2\2\2\u01b8\u01bb\3\2\2\2\u01b9\u01ba\3\2\2\2\u01b9\u01b7\3\2"+
		"\2\2\u01ba\u01bc\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bc\u01bd\7,\2\2\u01bd"+
		"\u01be\7\61\2\2\u01be\u008c\3\2\2\2\u01bf\u01c0\7\61\2\2\u01c0\u01c1\7"+
		"%\2\2\u01c1\u01c5\3\2\2\2\u01c2\u01c4\13\2\2\2\u01c3\u01c2\3\2\2\2\u01c4"+
		"\u01c7\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c5\u01c3\3\2\2\2\u01c6\u01c8\3\2"+
		"\2\2\u01c7\u01c5\3\2\2\2\u01c8\u01c9\7%\2\2\u01c9\u01ca\7\61\2\2\u01ca"+
		"\u008e\3\2\2\2\u01cb\u01cf\7%\2\2\u01cc\u01ce\n\4\2\2\u01cd\u01cc\3\2"+
		"\2\2\u01ce\u01d1\3\2\2\2\u01cf\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0"+
		"\u01e2\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d2\u01d6\7\f\2\2\u01d3\u01d5\5\u0087"+
		"C\2\u01d4\u01d3\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d6"+
		"\u01d7\3\2\2\2\u01d7\u01d9\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01dd\7%"+
		"\2\2\u01da\u01dc\n\4\2\2\u01db\u01da\3\2\2\2\u01dc\u01df\3\2\2\2\u01dd"+
		"\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01e1\3\2\2\2\u01df\u01dd\3\2"+
		"\2\2\u01e0\u01d2\3\2\2\2\u01e1\u01e4\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e2"+
		"\u01e3\3\2\2\2\u01e3\u0090\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e5\u01e8\5\u0093"+
		"I\2\u01e6\u01e8\t\5\2\2\u01e7\u01e5\3\2\2\2\u01e7\u01e6\3\2\2\2\u01e8"+
		"\u0092\3\2\2\2\u01e9\u01ea\t\6\2\2\u01ea\u0094\3\2\2\2\u01eb\u01f0\7)"+
		"\2\2\u01ec\u01ef\5\u0099L\2\u01ed\u01ef\n\7\2\2\u01ee\u01ec\3\2\2\2\u01ee"+
		"\u01ed\3\2\2\2\u01ef\u01f2\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2"+
		"\2\2\u01f1\u01f3\3\2\2\2\u01f2\u01f0\3\2\2\2\u01f3\u01f4\7)\2\2\u01f4"+
		"\u0096\3\2\2\2\u01f5\u01fa\7$\2\2\u01f6\u01f9\5\u0099L\2\u01f7\u01f9\n"+
		"\b\2\2\u01f8\u01f6\3\2\2\2\u01f8\u01f7\3\2\2\2\u01f9\u01fc\3\2\2\2\u01fa"+
		"\u01f8\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fd\3\2\2\2\u01fc\u01fa\3\2"+
		"\2\2\u01fd\u01fe\7$\2\2\u01fe\u0098\3\2\2\2\u01ff\u020e\7^\2\2\u0200\u020b"+
		"\7w\2\2\u0201\u0209\5\u009dN\2\u0202\u0207\5\u009dN\2\u0203\u0205\5\u009d"+
		"N\2\u0204\u0206\5\u009dN\2\u0205\u0204\3\2\2\2\u0205\u0206\3\2\2\2\u0206"+
		"\u0208\3\2\2\2\u0207\u0203\3\2\2\2\u0207\u0208\3\2\2\2\u0208\u020a\3\2"+
		"\2\2\u0209\u0202\3\2\2\2\u0209\u020a\3\2\2\2\u020a\u020c\3\2\2\2\u020b"+
		"\u0201\3\2\2\2\u020b\u020c\3\2\2\2\u020c\u020f\3\2\2\2\u020d\u020f\13"+
		"\2\2\2\u020e\u0200\3\2\2\2\u020e\u020d\3\2\2\2\u020f\u009a\3\2\2\2\u0210"+
		"\u0212\t\t\2\2\u0211\u0210\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0211\3\2"+
		"\2\2\u0213\u0214\3\2\2\2\u0214\u009c\3\2\2\2\u0215\u0216\t\n\2\2\u0216"+
		"\u009e\3\2\2\2\'\2\3\4\u00a2\u00e6\u00eb\u00f0\u0106\u010b\u0129\u0131"+
		"\u014c\u014e\u015c\u015e\u0175\u017b\u017d\u0182\u01ae\u01b9\u01c5\u01cf"+
		"\u01d6\u01dd\u01e2\u01e7\u01ee\u01f0\u01f8\u01fa\u0205\u0207\u0209\u020b"+
		"\u020e\u0213\r\2\3\2\7\3\2\7\4\2\b\2\2\6\2\2\t\5\2\t\6\2\t\31\2\t\32\2"+
		"\t\3\2\3(\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}