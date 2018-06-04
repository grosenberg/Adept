// Generated from D:/DevFiles/Eclipse/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STLexer.g4 by ANTLR 4.7.1

	package net.certiv.adept.lang.stg.parser.gen;
	import  net.certiv.adept.lang.stg.parser.STLexerAdaptor;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STLexer extends STLexerAdaptor {
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
		Template=1, SubTemplate=2;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "Template", "SubTemplate"
	};

	public static final String[] ruleNames = {
		"DOC_COMMENT", "BLOCK_COMMENT", "LINE_COMMENT", "TMPL_COMMENT", "HORZ_WS", 
		"VERT_WS", "ESCAPE", "LDELIM", "RBRACE", "TEXT", "IN_HORZ_WS", "IN_VERT_WS", 
		"LBRACE", "RDELIM", "STRING", "IF", "ELSEIF", "ELSE", "ENDIF", "SUPER", 
		"END", "TRUE", "FALSE", "AT", "ELLIPSIS", "DOT", "COMMA", "COLON", "SEMI", 
		"AND", "OR", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "EQUALS", "BANG", 
		"ERR_CHAR", "SUB_HORZ_WS", "SUB_VERT_WS", "ID", "SUB_COMMA", "PIPE", "TmplComment", 
		"LTmplMark", "RTmplMark", "Hws", "Vws", "DocComment", "BlockComment", 
		"LineComment", "LineCommentExt", "EscSeq", "EscAny", "UnicodeEsc", "HexDigits", 
		"HexDigit", "String", "NameStartChar", "NameChar", "True", "False", "Esc", 
		"At", "Colon", "Semi", "Dot", "Comma", "Equal", "DQuote", "Underscore", 
		"Dash", "Bang", "Pipe", "LParen", "RParen", "LBrace", "RBrace", "LBrack", 
		"RBrack", "LShift", "RShift", "And", "Or", "Ellipsis"
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


	public STLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "STLexer.g4"; }

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
		case 8:
			RBRACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 9:
			TEXT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void RBRACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 endsSubTemplate(); 
			break;
		}
	}
	private void TEXT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 adjText(); 
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return ESCAPE_sempred((RuleContext)_localctx, predIndex);
		case 7:
			return LDELIM_sempred((RuleContext)_localctx, predIndex);
		case 12:
			return LBRACE_sempred((RuleContext)_localctx, predIndex);
		case 13:
			return RDELIM_sempred((RuleContext)_localctx, predIndex);
		case 44:
			return LTmplMark_sempred((RuleContext)_localctx, predIndex);
		case 45:
			return RTmplMark_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean ESCAPE_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return  isLDelim() ;
		case 1:
			return  isRDelim() ;
		}
		return true;
	}
	private boolean LDELIM_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return  isLDelim() ;
		}
		return true;
	}
	private boolean LBRACE_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return  startsSubTemplate() ;
		}
		return true;
	}
	private boolean RDELIM_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return  isRDelim() ;
		}
		return true;
	}
	private boolean LTmplMark_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return  isLTmplComment() ;
		}
		return true;
	}
	private boolean RTmplMark_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return  isRTmplComment() ;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u022b\b\1\b\1\b"+
		"\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n"+
		"\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21"+
		"\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30"+
		"\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37"+
		"\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t"+
		"*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63"+
		"\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t"+
		"<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4"+
		"H\tH\4I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\t"+
		"S\4T\tT\4U\tU\4V\tV\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3"+
		"\5\3\5\3\5\3\5\3\6\6\6\u00c1\n\6\r\6\16\6\u00c2\3\6\3\6\3\7\6\7\u00c8"+
		"\n\7\r\7\16\7\u00c9\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\6\f\u00e0\n\f\r\f\16\f\u00e1\3\f\3\f"+
		"\3\f\3\r\6\r\u00e8\n\r\r\r\16\r\u00e9\3\r\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3\'"+
		"\3(\6(\u0140\n(\r(\16(\u0141\3(\3(\3(\3)\6)\u0148\n)\r)\16)\u0149\3)\3"+
		")\3)\3*\3*\7*\u0151\n*\f*\16*\u0154\13*\3+\3+\3+\3+\3,\3,\3,\3,\3-\3-"+
		"\7-\u0160\n-\f-\16-\u0163\13-\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3\60\3\60"+
		"\3\61\3\61\3\62\3\62\3\62\3\62\3\62\7\62\u0178\n\62\f\62\16\62\u017b\13"+
		"\62\3\62\3\62\3\62\5\62\u0180\n\62\3\63\3\63\3\63\3\63\7\63\u0186\n\63"+
		"\f\63\16\63\u0189\13\63\3\63\3\63\3\63\5\63\u018e\n\63\3\64\3\64\3\64"+
		"\3\64\7\64\u0194\n\64\f\64\16\64\u0197\13\64\3\65\3\65\3\65\3\65\7\65"+
		"\u019d\n\65\f\65\16\65\u01a0\13\65\3\65\5\65\u01a3\n\65\3\65\3\65\7\65"+
		"\u01a7\n\65\f\65\16\65\u01aa\13\65\3\65\3\65\3\65\3\65\7\65\u01b0\n\65"+
		"\f\65\16\65\u01b3\13\65\7\65\u01b5\n\65\f\65\16\65\u01b8\13\65\3\66\3"+
		"\66\3\66\3\66\3\66\5\66\u01bf\n\66\3\67\3\67\3\67\38\38\38\38\38\58\u01c9"+
		"\n8\58\u01cb\n8\58\u01cd\n8\58\u01cf\n8\39\69\u01d2\n9\r9\169\u01d3\3"+
		":\3:\3;\3;\3;\7;\u01db\n;\f;\16;\u01de\13;\3;\3;\3<\3<\3<\5<\u01e5\n<"+
		"\3=\3=\3=\3=\5=\u01eb\n=\3>\3>\3>\3>\3>\3?\3?\3?\3?\3?\3?\3@\3@\3A\3A"+
		"\3B\3B\3C\3C\3D\3D\3E\3E\3F\3F\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\3L\3L\3M"+
		"\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3R\3R\3S\3S\3S\3T\3T\3T\3U\3U\3U\3V\3V"+
		"\3V\3V\5\u0161\u0179\u0187\2W\5\6\7\7\t\b\13\t\r\n\17\13\21\f\23\r\25"+
		"\16\27\17\31\2\33\2\35\3\37\4!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\63"+
		"\31\65\32\67\339\5;\34=\35?\36A\37C E!G\"I#K$M%O&Q\2S\2U\'W\2Y([\2]\2"+
		"_\2a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\u0083\2"+
		"\u0085\2\u0087\2\u0089\2\u008b\2\u008d\2\u008f\2\u0091\2\u0093\2\u0095"+
		"\2\u0097\2\u0099\2\u009b\2\u009d\2\u009f\2\u00a1\2\u00a3\2\u00a5\2\u00a7"+
		"\2\u00a9\2\u00ab\2\u00ad\2\5\2\3\4\13\4\2\13\13\"\"\4\2\f\f\16\17\4\2"+
		"\f\f\17\17\n\2$$))^^ddhhppttvv\5\2\62;CHch\6\2\f\f\17\17$$^^\4\2C\\c|"+
		"\r\2\u00c2\u00d8\u00da\u00f8\u00fa\u0301\u0372\u037f\u0381\u2001\u200e"+
		"\u200f\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\5\2"+
		"\u00b9\u00b9\u0302\u0371\u2041\u2042\2\u021f\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\31\3\2\2\2\3\33\3\2\2\2\3\35\3\2\2\2"+
		"\3\37\3\2\2\2\3!\3\2\2\2\3#\3\2\2\2\3%\3\2\2\2\3\'\3\2\2\2\3)\3\2\2\2"+
		"\3+\3\2\2\2\3-\3\2\2\2\3/\3\2\2\2\3\61\3\2\2\2\3\63\3\2\2\2\3\65\3\2\2"+
		"\2\3\67\3\2\2\2\39\3\2\2\2\3;\3\2\2\2\3=\3\2\2\2\3?\3\2\2\2\3A\3\2\2\2"+
		"\3C\3\2\2\2\3E\3\2\2\2\3G\3\2\2\2\3I\3\2\2\2\3K\3\2\2\2\3M\3\2\2\2\3O"+
		"\3\2\2\2\4Q\3\2\2\2\4S\3\2\2\2\4U\3\2\2\2\4W\3\2\2\2\4Y\3\2\2\2\5\u00af"+
		"\3\2\2\2\7\u00b3\3\2\2\2\t\u00b7\3\2\2\2\13\u00bb\3\2\2\2\r\u00c0\3\2"+
		"\2\2\17\u00c7\3\2\2\2\21\u00cd\3\2\2\2\23\u00d3\3\2\2\2\25\u00d8\3\2\2"+
		"\2\27\u00db\3\2\2\2\31\u00df\3\2\2\2\33\u00e7\3\2\2\2\35\u00ee\3\2\2\2"+
		"\37\u00f3\3\2\2\2!\u00f8\3\2\2\2#\u00fa\3\2\2\2%\u00fd\3\2\2\2\'\u0104"+
		"\3\2\2\2)\u0109\3\2\2\2+\u010f\3\2\2\2-\u0115\3\2\2\2/\u011a\3\2\2\2\61"+
		"\u011c\3\2\2\2\63\u011e\3\2\2\2\65\u0120\3\2\2\2\67\u0122\3\2\2\29\u0124"+
		"\3\2\2\2;\u0126\3\2\2\2=\u0128\3\2\2\2?\u012a\3\2\2\2A\u012c\3\2\2\2C"+
		"\u012e\3\2\2\2E\u0130\3\2\2\2G\u0132\3\2\2\2I\u0134\3\2\2\2K\u0136\3\2"+
		"\2\2M\u0138\3\2\2\2O\u013a\3\2\2\2Q\u013f\3\2\2\2S\u0147\3\2\2\2U\u014e"+
		"\3\2\2\2W\u0155\3\2\2\2Y\u0159\3\2\2\2[\u015d\3\2\2\2]\u0166\3\2\2\2_"+
		"\u016a\3\2\2\2a\u016e\3\2\2\2c\u0170\3\2\2\2e\u0172\3\2\2\2g\u0181\3\2"+
		"\2\2i\u018f\3\2\2\2k\u0198\3\2\2\2m\u01b9\3\2\2\2o\u01c0\3\2\2\2q\u01c3"+
		"\3\2\2\2s\u01d1\3\2\2\2u\u01d5\3\2\2\2w\u01d7\3\2\2\2y\u01e4\3\2\2\2{"+
		"\u01ea\3\2\2\2}\u01ec\3\2\2\2\177\u01f1\3\2\2\2\u0081\u01f7\3\2\2\2\u0083"+
		"\u01f9\3\2\2\2\u0085\u01fb\3\2\2\2\u0087\u01fd\3\2\2\2\u0089\u01ff\3\2"+
		"\2\2\u008b\u0201\3\2\2\2\u008d\u0203\3\2\2\2\u008f\u0205\3\2\2\2\u0091"+
		"\u0207\3\2\2\2\u0093\u0209\3\2\2\2\u0095\u020b\3\2\2\2\u0097\u020d\3\2"+
		"\2\2\u0099\u020f\3\2\2\2\u009b\u0211\3\2\2\2\u009d\u0213\3\2\2\2\u009f"+
		"\u0215\3\2\2\2\u00a1\u0217\3\2\2\2\u00a3\u0219\3\2\2\2\u00a5\u021b\3\2"+
		"\2\2\u00a7\u021e\3\2\2\2\u00a9\u0221\3\2\2\2\u00ab\u0224\3\2\2\2\u00ad"+
		"\u0227\3\2\2\2\u00af\u00b0\5e\62\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\b\2"+
		"\2\2\u00b2\6\3\2\2\2\u00b3\u00b4\5g\63\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6"+
		"\b\3\2\2\u00b6\b\3\2\2\2\u00b7\u00b8\5i\64\2\u00b8\u00b9\3\2\2\2\u00b9"+
		"\u00ba\b\4\2\2\u00ba\n\3\2\2\2\u00bb\u00bc\5[-\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00be\b\5\2\2\u00be\f\3\2\2\2\u00bf\u00c1\5a\60\2\u00c0\u00bf\3\2\2\2"+
		"\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c4"+
		"\3\2\2\2\u00c4\u00c5\b\6\2\2\u00c5\16\3\2\2\2\u00c6\u00c8\5c\61\2\u00c7"+
		"\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\b\7\2\2\u00cc\20\3\2\2\2\u00cd\u00ce"+
		"\13\2\2\2\u00ce\u00cf\6\b\2\2\u00cf\u00d0\5m\66\2\u00d0\u00d1\13\2\2\2"+
		"\u00d1\u00d2\6\b\3\2\u00d2\22\3\2\2\2\u00d3\u00d4\13\2\2\2\u00d4\u00d5"+
		"\6\t\4\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\b\t\3\2\u00d7\24\3\2\2\2\u00d8"+
		"\u00d9\5\u009fO\2\u00d9\u00da\b\n\4\2\u00da\26\3\2\2\2\u00db\u00dc\13"+
		"\2\2\2\u00dc\u00dd\b\13\5\2\u00dd\30\3\2\2\2\u00de\u00e0\5a\60\2\u00df"+
		"\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2"+
		"\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\b\f\6\2\u00e4\u00e5\b\f\2\2\u00e5"+
		"\32\3\2\2\2\u00e6\u00e8\5c\61\2\u00e7\u00e6\3\2\2\2\u00e8\u00e9\3\2\2"+
		"\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ec"+
		"\b\r\7\2\u00ec\u00ed\b\r\2\2\u00ed\34\3\2\2\2\u00ee\u00ef\5\u009dN\2\u00ef"+
		"\u00f0\6\16\5\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\b\16\b\2\u00f2\36\3\2"+
		"\2\2\u00f3\u00f4\13\2\2\2\u00f4\u00f5\6\17\6\2\u00f5\u00f6\3\2\2\2\u00f6"+
		"\u00f7\b\17\t\2\u00f7 \3\2\2\2\u00f8\u00f9\5w;\2\u00f9\"\3\2\2\2\u00fa"+
		"\u00fb\7k\2\2\u00fb\u00fc\7h\2\2\u00fc$\3\2\2\2\u00fd\u00fe\7g\2\2\u00fe"+
		"\u00ff\7n\2\2\u00ff\u0100\7u\2\2\u0100\u0101\7g\2\2\u0101\u0102\7k\2\2"+
		"\u0102\u0103\7h\2\2\u0103&\3\2\2\2\u0104\u0105\7g\2\2\u0105\u0106\7n\2"+
		"\2\u0106\u0107\7u\2\2\u0107\u0108\7g\2\2\u0108(\3\2\2\2\u0109\u010a\7"+
		"g\2\2\u010a\u010b\7p\2\2\u010b\u010c\7f\2\2\u010c\u010d\7k\2\2\u010d\u010e"+
		"\7h\2\2\u010e*\3\2\2\2\u010f\u0110\7u\2\2\u0110\u0111\7w\2\2\u0111\u0112"+
		"\7r\2\2\u0112\u0113\7g\2\2\u0113\u0114\7t\2\2\u0114,\3\2\2\2\u0115\u0116"+
		"\7B\2\2\u0116\u0117\7g\2\2\u0117\u0118\7p\2\2\u0118\u0119\7f\2\2\u0119"+
		".\3\2\2\2\u011a\u011b\5}>\2\u011b\60\3\2\2\2\u011c\u011d\5\177?\2\u011d"+
		"\62\3\2\2\2\u011e\u011f\5\u0083A\2\u011f\64\3\2\2\2\u0120\u0121\5\u00ad"+
		"V\2\u0121\66\3\2\2\2\u0122\u0123\5\u0089D\2\u01238\3\2\2\2\u0124\u0125"+
		"\5\u008bE\2\u0125:\3\2\2\2\u0126\u0127\5\u0085B\2\u0127<\3\2\2\2\u0128"+
		"\u0129\5\u0087C\2\u0129>\3\2\2\2\u012a\u012b\5\u00a9T\2\u012b@\3\2\2\2"+
		"\u012c\u012d\5\u00abU\2\u012dB\3\2\2\2\u012e\u012f\5\u0099L\2\u012fD\3"+
		"\2\2\2\u0130\u0131\5\u009bM\2\u0131F\3\2\2\2\u0132\u0133\5\u00a1P\2\u0133"+
		"H\3\2\2\2\u0134\u0135\5\u00a3Q\2\u0135J\3\2\2\2\u0136\u0137\5\u008dF\2"+
		"\u0137L\3\2\2\2\u0138\u0139\5\u0095J\2\u0139N\3\2\2\2\u013a\u013b\13\2"+
		"\2\2\u013b\u013c\3\2\2\2\u013c\u013d\b\'\n\2\u013dP\3\2\2\2\u013e\u0140"+
		"\5a\60\2\u013f\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u013f\3\2\2\2\u0141"+
		"\u0142\3\2\2\2\u0142\u0143\3\2\2\2\u0143\u0144\b(\6\2\u0144\u0145\b(\2"+
		"\2\u0145R\3\2\2\2\u0146\u0148\5c\61\2\u0147\u0146\3\2\2\2\u0148\u0149"+
		"\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\u014b\3\2\2\2\u014b"+
		"\u014c\b)\7\2\u014c\u014d\b)\2\2\u014dT\3\2\2\2\u014e\u0152\5y<\2\u014f"+
		"\u0151\5{=\2\u0150\u014f\3\2\2\2\u0151\u0154\3\2\2\2\u0152\u0150\3\2\2"+
		"\2\u0152\u0153\3\2\2\2\u0153V\3\2\2\2\u0154\u0152\3\2\2\2\u0155\u0156"+
		"\5\u008bE\2\u0156\u0157\3\2\2\2\u0157\u0158\b+\13\2\u0158X\3\2\2\2\u0159"+
		"\u015a\5\u0097K\2\u015a\u015b\3\2\2\2\u015b\u015c\b,\t\2\u015cZ\3\2\2"+
		"\2\u015d\u0161\5].\2\u015e\u0160\13\2\2\2\u015f\u015e\3\2\2\2\u0160\u0163"+
		"\3\2\2\2\u0161\u0162\3\2\2\2\u0161\u015f\3\2\2\2\u0162\u0164\3\2\2\2\u0163"+
		"\u0161\3\2\2\2\u0164\u0165\5_/\2\u0165\\\3\2\2\2\u0166\u0167\13\2\2\2"+
		"\u0167\u0168\6.\7\2\u0168\u0169\5\u0095J\2\u0169^\3\2\2\2\u016a\u016b"+
		"\5\u0095J\2\u016b\u016c\13\2\2\2\u016c\u016d\6/\b\2\u016d`\3\2\2\2\u016e"+
		"\u016f\t\2\2\2\u016fb\3\2\2\2\u0170\u0171\t\3\2\2\u0171d\3\2\2\2\u0172"+
		"\u0173\7\61\2\2\u0173\u0174\7,\2\2\u0174\u0175\7,\2\2\u0175\u0179\3\2"+
		"\2\2\u0176\u0178\13\2\2\2\u0177\u0176\3\2\2\2\u0178\u017b\3\2\2\2\u0179"+
		"\u017a\3\2\2\2\u0179\u0177\3\2\2\2\u017a\u017f\3\2\2\2\u017b\u0179\3\2"+
		"\2\2\u017c\u017d\7,\2\2\u017d\u0180\7\61\2\2\u017e\u0180\7\2\2\3\u017f"+
		"\u017c\3\2\2\2\u017f\u017e\3\2\2\2\u0180f\3\2\2\2\u0181\u0182\7\61\2\2"+
		"\u0182\u0183\7,\2\2\u0183\u0187\3\2\2\2\u0184\u0186\13\2\2\2\u0185\u0184"+
		"\3\2\2\2\u0186\u0189\3\2\2\2\u0187\u0188\3\2\2\2\u0187\u0185\3\2\2\2\u0188"+
		"\u018d\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u018b\7,\2\2\u018b\u018e\7\61"+
		"\2\2\u018c\u018e\7\2\2\3\u018d\u018a\3\2\2\2\u018d\u018c\3\2\2\2\u018e"+
		"h\3\2\2\2\u018f\u0190\7\61\2\2\u0190\u0191\7\61\2\2\u0191\u0195\3\2\2"+
		"\2\u0192\u0194\n\4\2\2\u0193\u0192\3\2\2\2\u0194\u0197\3\2\2\2\u0195\u0193"+
		"\3\2\2\2\u0195\u0196\3\2\2\2\u0196j\3\2\2\2\u0197\u0195\3\2\2\2\u0198"+
		"\u0199\7\61\2\2\u0199\u019a\7\61\2\2\u019a\u019e\3\2\2\2\u019b\u019d\n"+
		"\4\2\2\u019c\u019b\3\2\2\2\u019d\u01a0\3\2\2\2\u019e\u019c\3\2\2\2\u019e"+
		"\u019f\3\2\2\2\u019f\u01b6\3\2\2\2\u01a0\u019e\3\2\2\2\u01a1\u01a3\7\17"+
		"\2\2\u01a2\u01a1\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4"+
		"\u01a8\7\f\2\2\u01a5\u01a7\5a\60\2\u01a6\u01a5\3\2\2\2\u01a7\u01aa\3\2"+
		"\2\2\u01a8\u01a6\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01ab\3\2\2\2\u01aa"+
		"\u01a8\3\2\2\2\u01ab\u01ac\7\61\2\2\u01ac\u01ad\7\61\2\2\u01ad\u01b1\3"+
		"\2\2\2\u01ae\u01b0\n\4\2\2\u01af\u01ae\3\2\2\2\u01b0\u01b3\3\2\2\2\u01b1"+
		"\u01af\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2\u01b5\3\2\2\2\u01b3\u01b1\3\2"+
		"\2\2\u01b4\u01a2\3\2\2\2\u01b5\u01b8\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b6"+
		"\u01b7\3\2\2\2\u01b7l\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b9\u01be\5\u0081"+
		"@\2\u01ba\u01bf\t\5\2\2\u01bb\u01bf\5q8\2\u01bc\u01bf\13\2\2\2\u01bd\u01bf"+
		"\7\2\2\3\u01be\u01ba\3\2\2\2\u01be\u01bb\3\2\2\2\u01be\u01bc\3\2\2\2\u01be"+
		"\u01bd\3\2\2\2\u01bfn\3\2\2\2\u01c0\u01c1\5\u0081@\2\u01c1\u01c2\13\2"+
		"\2\2\u01c2p\3\2\2\2\u01c3\u01ce\7w\2\2\u01c4\u01cc\5u:\2\u01c5\u01ca\5"+
		"u:\2\u01c6\u01c8\5u:\2\u01c7\u01c9\5u:\2\u01c8\u01c7\3\2\2\2\u01c8\u01c9"+
		"\3\2\2\2\u01c9\u01cb\3\2\2\2\u01ca\u01c6\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb"+
		"\u01cd\3\2\2\2\u01cc\u01c5\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01cf\3\2"+
		"\2\2\u01ce\u01c4\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cfr\3\2\2\2\u01d0\u01d2"+
		"\5u:\2\u01d1\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d3"+
		"\u01d4\3\2\2\2\u01d4t\3\2\2\2\u01d5\u01d6\t\6\2\2\u01d6v\3\2\2\2\u01d7"+
		"\u01dc\5\u008fG\2\u01d8\u01db\5m\66\2\u01d9\u01db\n\7\2\2\u01da\u01d8"+
		"\3\2\2\2\u01da\u01d9\3\2\2\2\u01db\u01de\3\2\2\2\u01dc\u01da\3\2\2\2\u01dc"+
		"\u01dd\3\2\2\2\u01dd\u01df\3\2\2\2\u01de\u01dc\3\2\2\2\u01df\u01e0\5\u008f"+
		"G\2\u01e0x\3\2\2\2\u01e1\u01e5\t\b\2\2\u01e2\u01e5\5\u0091H\2\u01e3\u01e5"+
		"\t\t\2\2\u01e4\u01e1\3\2\2\2\u01e4\u01e2\3\2\2\2\u01e4\u01e3\3\2\2\2\u01e5"+
		"z\3\2\2\2\u01e6\u01eb\5y<\2\u01e7\u01eb\4\62;\2\u01e8\u01eb\5\u0093I\2"+
		"\u01e9\u01eb\t\n\2\2\u01ea\u01e6\3\2\2\2\u01ea\u01e7\3\2\2\2\u01ea\u01e8"+
		"\3\2\2\2\u01ea\u01e9\3\2\2\2\u01eb|\3\2\2\2\u01ec\u01ed\7v\2\2\u01ed\u01ee"+
		"\7t\2\2\u01ee\u01ef\7w\2\2\u01ef\u01f0\7g\2\2\u01f0~\3\2\2\2\u01f1\u01f2"+
		"\7h\2\2\u01f2\u01f3\7c\2\2\u01f3\u01f4\7n\2\2\u01f4\u01f5\7u\2\2\u01f5"+
		"\u01f6\7g\2\2\u01f6\u0080\3\2\2\2\u01f7\u01f8\7^\2\2\u01f8\u0082\3\2\2"+
		"\2\u01f9\u01fa\7B\2\2\u01fa\u0084\3\2\2\2\u01fb\u01fc\7<\2\2\u01fc\u0086"+
		"\3\2\2\2\u01fd\u01fe\7=\2\2\u01fe\u0088\3\2\2\2\u01ff\u0200\7\60\2\2\u0200"+
		"\u008a\3\2\2\2\u0201\u0202\7.\2\2\u0202\u008c\3\2\2\2\u0203\u0204\7?\2"+
		"\2\u0204\u008e\3\2\2\2\u0205\u0206\7$\2\2\u0206\u0090\3\2\2\2\u0207\u0208"+
		"\7a\2\2\u0208\u0092\3\2\2\2\u0209\u020a\7/\2\2\u020a\u0094\3\2\2\2\u020b"+
		"\u020c\7#\2\2\u020c\u0096\3\2\2\2\u020d\u020e\7~\2\2\u020e\u0098\3\2\2"+
		"\2\u020f\u0210\7*\2\2\u0210\u009a\3\2\2\2\u0211\u0212\7+\2\2\u0212\u009c"+
		"\3\2\2\2\u0213\u0214\7}\2\2\u0214\u009e\3\2\2\2\u0215\u0216\7\177\2\2"+
		"\u0216\u00a0\3\2\2\2\u0217\u0218\7]\2\2\u0218\u00a2\3\2\2\2\u0219\u021a"+
		"\7_\2\2\u021a\u00a4\3\2\2\2\u021b\u021c\7>\2\2\u021c\u021d\7>\2\2\u021d"+
		"\u00a6\3\2\2\2\u021e\u021f\7@\2\2\u021f\u0220\7@\2\2\u0220\u00a8\3\2\2"+
		"\2\u0221\u0222\7(\2\2\u0222\u0223\7(\2\2\u0223\u00aa\3\2\2\2\u0224\u0225"+
		"\7~\2\2\u0225\u0226\7~\2\2\u0226\u00ac\3\2\2\2\u0227\u0228\7\60\2\2\u0228"+
		"\u0229\7\60\2\2\u0229\u022a\7\60\2\2\u022a\u00ae\3\2\2\2!\2\3\4\u00c2"+
		"\u00c9\u00e1\u00e9\u0141\u0149\u0152\u0161\u0179\u017f\u0187\u018d\u0195"+
		"\u019e\u01a2\u01a8\u01b1\u01b6\u01be\u01c8\u01ca\u01cc\u01ce\u01d3\u01da"+
		"\u01dc\u01e4\u01ea\f\2\3\2\4\3\2\3\n\2\3\13\3\t\n\2\t\13\2\4\4\2\4\2\2"+
		"\b\2\2\t\5\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}