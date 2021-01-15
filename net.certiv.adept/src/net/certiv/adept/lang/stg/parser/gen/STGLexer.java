// Generated from D:/DevFiles/Eclipse/Tools/Adept/net.certiv.adept/src/net/certiv/adept/lang/stg/parser/STGLexer.g4 by ANTLR 4.7.2

	package net.certiv.adept.lang.stg.parser.gen;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STGLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DOC_COMMENT", "BLOCK_COMMENT", "LINE_COMMENT", "TMPL_COMMENT", "HORZ_WS", 
			"VERT_WS", "STRING", "BIGSTRING", "BIGSTRING_NO_NL", "ANON_TEMPLATE", 
			"ASSIGN", "EQUAL", "DOT", "COMMA", "SEMI", "COLON", "LPAREN", "RPAREN", 
			"LBRACK", "RBRACK", "AT", "TRUE", "FALSE", "GROUP", "DELIMITERS", "IMPORT", 
			"ID", "Assign", "LBang", "RBang", "LPct", "RPct", "LDAngle", "RDAngle", 
			"Hws", "Vws", "DocComment", "BlockComment", "LineComment", "EscSeq", 
			"UnicodeEsc", "HexDigit", "String", "NameChar", "NameStartChar", "True", 
			"False", "Esc", "At", "Colon", "Semi", "Dot", "Comma", "Equal", "DQuote", 
			"Underscore", "LParen", "RParen", "LBrace", "RBrace", "LBrack", "RBrack", 
			"LShift", "RShift"
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


	public STGLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "STGLexer.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u0196\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\5\5\u0092\n\5\3\5\3\5\3\5\3\5\3\6\6\6\u0099\n\6\r\6\16\6"+
		"\u009a\3\6\3\6\3\7\6\7\u00a0\n\7\r\7\16\7\u00a1\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\7\t\u00aa\n\t\f\t\16\t\u00ad\13\t\3\t\3\t\3\n\3\n\7\n\u00b3\n\n\f\n"+
		"\16\n\u00b6\13\n\3\n\3\n\3\13\3\13\7\13\u00bc\n\13\f\13\16\13\u00bf\13"+
		"\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\7\34\u00f7"+
		"\n\34\f\34\16\34\u00fa\13\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3"+
		"\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3#\3#\3$\3$\3%\5%\u0113\n%\3%\3%\3"+
		"&\3&\3&\3&\3&\7&\u011c\n&\f&\16&\u011f\13&\3&\3&\3&\5&\u0124\n&\3\'\3"+
		"\'\3\'\3\'\7\'\u012a\n\'\f\'\16\'\u012d\13\'\3\'\3\'\3\'\5\'\u0132\n\'"+
		"\3(\3(\3(\3(\7(\u0138\n(\f(\16(\u013b\13(\3(\5(\u013e\n(\3)\3)\3)\3)\3"+
		")\5)\u0145\n)\3*\3*\3*\3*\3*\5*\u014c\n*\5*\u014e\n*\5*\u0150\n*\5*\u0152"+
		"\n*\3+\3+\3,\3,\3,\7,\u0159\n,\f,\16,\u015c\13,\3,\3,\3-\3-\3-\3-\5-\u0164"+
		"\n-\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\62"+
		"\3\62\3\63\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\38\38\39\39\3"+
		":\3:\3;\3;\3<\3<\3=\3=\3>\3>\3?\3?\3@\3@\3@\3A\3A\3A\7\u00ab\u00b4\u00bd"+
		"\u011d\u012b\2B\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2]\2_\2"+
		"a\2c\2e\2g\2i\2k\2m\2o\2q\2s\2u\2w\2y\2{\2}\2\177\2\u0081\2\3\2\n\4\2"+
		"\13\13\"\"\4\2\f\f\16\16\4\2\f\f\16\17\n\2$$))^^ddhhppttvv\5\2\62;CHc"+
		"h\6\2\f\f\17\17$$^^\5\2\u00b9\u00b9\u0302\u0371\u2041\u2042\17\2C\\c|"+
		"\u00c2\u00d8\u00da\u00f8\u00fa\u0301\u0372\u037f\u0381\u2001\u200e\u200f"+
		"\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2\uffff\2\u018a\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\3\u0083\3\2\2\2\5"+
		"\u0087\3\2\2\2\7\u008b\3\2\2\2\t\u008f\3\2\2\2\13\u0098\3\2\2\2\r\u009f"+
		"\3\2\2\2\17\u00a5\3\2\2\2\21\u00a7\3\2\2\2\23\u00b0\3\2\2\2\25\u00b9\3"+
		"\2\2\2\27\u00c2\3\2\2\2\31\u00c4\3\2\2\2\33\u00c6\3\2\2\2\35\u00c8\3\2"+
		"\2\2\37\u00ca\3\2\2\2!\u00cc\3\2\2\2#\u00ce\3\2\2\2%\u00d0\3\2\2\2\'\u00d2"+
		"\3\2\2\2)\u00d4\3\2\2\2+\u00d6\3\2\2\2-\u00d8\3\2\2\2/\u00da\3\2\2\2\61"+
		"\u00dc\3\2\2\2\63\u00e2\3\2\2\2\65\u00ed\3\2\2\2\67\u00f4\3\2\2\29\u00fb"+
		"\3\2\2\2;\u00ff\3\2\2\2=\u0102\3\2\2\2?\u0105\3\2\2\2A\u0108\3\2\2\2C"+
		"\u010b\3\2\2\2E\u010d\3\2\2\2G\u010f\3\2\2\2I\u0112\3\2\2\2K\u0116\3\2"+
		"\2\2M\u0125\3\2\2\2O\u0133\3\2\2\2Q\u013f\3\2\2\2S\u0146\3\2\2\2U\u0153"+
		"\3\2\2\2W\u0155\3\2\2\2Y\u0163\3\2\2\2[\u0165\3\2\2\2]\u0167\3\2\2\2_"+
		"\u016c\3\2\2\2a\u0172\3\2\2\2c\u0174\3\2\2\2e\u0176\3\2\2\2g\u0178\3\2"+
		"\2\2i\u017a\3\2\2\2k\u017c\3\2\2\2m\u017e\3\2\2\2o\u0180\3\2\2\2q\u0182"+
		"\3\2\2\2s\u0184\3\2\2\2u\u0186\3\2\2\2w\u0188\3\2\2\2y\u018a\3\2\2\2{"+
		"\u018c\3\2\2\2}\u018e\3\2\2\2\177\u0190\3\2\2\2\u0081\u0193\3\2\2\2\u0083"+
		"\u0084\5K&\2\u0084\u0085\3\2\2\2\u0085\u0086\b\2\2\2\u0086\4\3\2\2\2\u0087"+
		"\u0088\5M\'\2\u0088\u0089\3\2\2\2\u0089\u008a\b\3\2\2\u008a\6\3\2\2\2"+
		"\u008b\u008c\5O(\2\u008c\u008d\3\2\2\2\u008d\u008e\b\4\2\2\u008e\b\3\2"+
		"\2\2\u008f\u0091\5;\36\2\u0090\u0092\13\2\2\2\u0091\u0090\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\5=\37\2\u0094\u0095\3\2"+
		"\2\2\u0095\u0096\b\5\2\2\u0096\n\3\2\2\2\u0097\u0099\5G$\2\u0098\u0097"+
		"\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\u009c\3\2\2\2\u009c\u009d\b\6\2\2\u009d\f\3\2\2\2\u009e\u00a0\5I%\2\u009f"+
		"\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\b\7\2\2\u00a4\16\3\2\2\2\u00a5\u00a6"+
		"\5W,\2\u00a6\20\3\2\2\2\u00a7\u00ab\5C\"\2\u00a8\u00aa\13\2\2\2\u00a9"+
		"\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\5E#\2\u00af\22"+
		"\3\2\2\2\u00b0\u00b4\5? \2\u00b1\u00b3\13\2\2\2\u00b2\u00b1\3\2\2\2\u00b3"+
		"\u00b6\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b7\3\2"+
		"\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8\5A!\2\u00b8\24\3\2\2\2\u00b9\u00bd"+
		"\5w<\2\u00ba\u00bc\13\2\2\2\u00bb\u00ba\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd"+
		"\u00be\3\2\2\2\u00bd\u00bb\3\2\2\2\u00be\u00c0\3\2\2\2\u00bf\u00bd\3\2"+
		"\2\2\u00c0\u00c1\5y=\2\u00c1\26\3\2\2\2\u00c2\u00c3\59\35\2\u00c3\30\3"+
		"\2\2\2\u00c4\u00c5\5m\67\2\u00c5\32\3\2\2\2\u00c6\u00c7\5i\65\2\u00c7"+
		"\34\3\2\2\2\u00c8\u00c9\5k\66\2\u00c9\36\3\2\2\2\u00ca\u00cb\5g\64\2\u00cb"+
		" \3\2\2\2\u00cc\u00cd\5e\63\2\u00cd\"\3\2\2\2\u00ce\u00cf\5s:\2\u00cf"+
		"$\3\2\2\2\u00d0\u00d1\5u;\2\u00d1&\3\2\2\2\u00d2\u00d3\5{>\2\u00d3(\3"+
		"\2\2\2\u00d4\u00d5\5}?\2\u00d5*\3\2\2\2\u00d6\u00d7\5c\62\2\u00d7,\3\2"+
		"\2\2\u00d8\u00d9\5]/\2\u00d9.\3\2\2\2\u00da\u00db\5_\60\2\u00db\60\3\2"+
		"\2\2\u00dc\u00dd\7i\2\2\u00dd\u00de\7t\2\2\u00de\u00df\7q\2\2\u00df\u00e0"+
		"\7w\2\2\u00e0\u00e1\7r\2\2\u00e1\62\3\2\2\2\u00e2\u00e3\7f\2\2\u00e3\u00e4"+
		"\7g\2\2\u00e4\u00e5\7n\2\2\u00e5\u00e6\7k\2\2\u00e6\u00e7\7o\2\2\u00e7"+
		"\u00e8\7k\2\2\u00e8\u00e9\7v\2\2\u00e9\u00ea\7g\2\2\u00ea\u00eb\7t\2\2"+
		"\u00eb\u00ec\7u\2\2\u00ec\64\3\2\2\2\u00ed\u00ee\7k\2\2\u00ee\u00ef\7"+
		"o\2\2\u00ef\u00f0\7r\2\2\u00f0\u00f1\7q\2\2\u00f1\u00f2\7t\2\2\u00f2\u00f3"+
		"\7v\2\2\u00f3\66\3\2\2\2\u00f4\u00f8\5[.\2\u00f5\u00f7\5Y-\2\u00f6\u00f5"+
		"\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"8\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fc\7<\2\2\u00fc\u00fd\7<\2\2\u00fd"+
		"\u00fe\7?\2\2\u00fe:\3\2\2\2\u00ff\u0100\7>\2\2\u0100\u0101\7#\2\2\u0101"+
		"<\3\2\2\2\u0102\u0103\7#\2\2\u0103\u0104\7@\2\2\u0104>\3\2\2\2\u0105\u0106"+
		"\7>\2\2\u0106\u0107\7\'\2\2\u0107@\3\2\2\2\u0108\u0109\7\'\2\2\u0109\u010a"+
		"\7@\2\2\u010aB\3\2\2\2\u010b\u010c\5\177@\2\u010cD\3\2\2\2\u010d\u010e"+
		"\5\u0081A\2\u010eF\3\2\2\2\u010f\u0110\t\2\2\2\u0110H\3\2\2\2\u0111\u0113"+
		"\7\17\2\2\u0112\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\3\2\2\2"+
		"\u0114\u0115\t\3\2\2\u0115J\3\2\2\2\u0116\u0117\7\61\2\2\u0117\u0118\7"+
		",\2\2\u0118\u0119\7,\2\2\u0119\u011d\3\2\2\2\u011a\u011c\13\2\2\2\u011b"+
		"\u011a\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011e\3\2\2\2\u011d\u011b\3\2"+
		"\2\2\u011e\u0123\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\7,\2\2\u0121"+
		"\u0124\7\61\2\2\u0122\u0124\7\2\2\3\u0123\u0120\3\2\2\2\u0123\u0122\3"+
		"\2\2\2\u0124L\3\2\2\2\u0125\u0126\7\61\2\2\u0126\u0127\7,\2\2\u0127\u012b"+
		"\3\2\2\2\u0128\u012a\13\2\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2"+
		"\u012b\u012c\3\2\2\2\u012b\u0129\3\2\2\2\u012c\u0131\3\2\2\2\u012d\u012b"+
		"\3\2\2\2\u012e\u012f\7,\2\2\u012f\u0132\7\61\2\2\u0130\u0132\7\2\2\3\u0131"+
		"\u012e\3\2\2\2\u0131\u0130\3\2\2\2\u0132N\3\2\2\2\u0133\u0134\7\61\2\2"+
		"\u0134\u0135\7\61\2\2\u0135\u013d\3\2\2\2\u0136\u0138\n\4\2\2\u0137\u0136"+
		"\3\2\2\2\u0138\u013b\3\2\2\2\u0139\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a"+
		"\u013e\3\2\2\2\u013b\u0139\3\2\2\2\u013c\u013e\7\2\2\3\u013d\u0139\3\2"+
		"\2\2\u013d\u013c\3\2\2\2\u013eP\3\2\2\2\u013f\u0144\5a\61\2\u0140\u0145"+
		"\t\5\2\2\u0141\u0145\5S*\2\u0142\u0145\13\2\2\2\u0143\u0145\7\2\2\3\u0144"+
		"\u0140\3\2\2\2\u0144\u0141\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0143\3\2"+
		"\2\2\u0145R\3\2\2\2\u0146\u0151\7w\2\2\u0147\u014f\5U+\2\u0148\u014d\5"+
		"U+\2\u0149\u014b\5U+\2\u014a\u014c\5U+\2\u014b\u014a\3\2\2\2\u014b\u014c"+
		"\3\2\2\2\u014c\u014e\3\2\2\2\u014d\u0149\3\2\2\2\u014d\u014e\3\2\2\2\u014e"+
		"\u0150\3\2\2\2\u014f\u0148\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0152\3\2"+
		"\2\2\u0151\u0147\3\2\2\2\u0151\u0152\3\2\2\2\u0152T\3\2\2\2\u0153\u0154"+
		"\t\6\2\2\u0154V\3\2\2\2\u0155\u015a\5o8\2\u0156\u0159\5Q)\2\u0157\u0159"+
		"\n\7\2\2\u0158\u0156\3\2\2\2\u0158\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a"+
		"\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u015a\3\2"+
		"\2\2\u015d\u015e\5o8\2\u015eX\3\2\2\2\u015f\u0164\5[.\2\u0160\u0164\4"+
		"\62;\2\u0161\u0164\5q9\2\u0162\u0164\t\b\2\2\u0163\u015f\3\2\2\2\u0163"+
		"\u0160\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0162\3\2\2\2\u0164Z\3\2\2\2"+
		"\u0165\u0166\t\t\2\2\u0166\\\3\2\2\2\u0167\u0168\7v\2\2\u0168\u0169\7"+
		"t\2\2\u0169\u016a\7w\2\2\u016a\u016b\7g\2\2\u016b^\3\2\2\2\u016c\u016d"+
		"\7h\2\2\u016d\u016e\7c\2\2\u016e\u016f\7n\2\2\u016f\u0170\7u\2\2\u0170"+
		"\u0171\7g\2\2\u0171`\3\2\2\2\u0172\u0173\7^\2\2\u0173b\3\2\2\2\u0174\u0175"+
		"\7B\2\2\u0175d\3\2\2\2\u0176\u0177\7<\2\2\u0177f\3\2\2\2\u0178\u0179\7"+
		"=\2\2\u0179h\3\2\2\2\u017a\u017b\7\60\2\2\u017bj\3\2\2\2\u017c\u017d\7"+
		".\2\2\u017dl\3\2\2\2\u017e\u017f\7?\2\2\u017fn\3\2\2\2\u0180\u0181\7$"+
		"\2\2\u0181p\3\2\2\2\u0182\u0183\7a\2\2\u0183r\3\2\2\2\u0184\u0185\7*\2"+
		"\2\u0185t\3\2\2\2\u0186\u0187\7+\2\2\u0187v\3\2\2\2\u0188\u0189\7}\2\2"+
		"\u0189x\3\2\2\2\u018a\u018b\7\177\2\2\u018bz\3\2\2\2\u018c\u018d\7]\2"+
		"\2\u018d|\3\2\2\2\u018e\u018f\7_\2\2\u018f~\3\2\2\2\u0190\u0191\7>\2\2"+
		"\u0191\u0192\7>\2\2\u0192\u0080\3\2\2\2\u0193\u0194\7@\2\2\u0194\u0195"+
		"\7@\2\2\u0195\u0082\3\2\2\2\31\2\u0091\u009a\u00a1\u00ab\u00b4\u00bd\u00f8"+
		"\u0112\u011d\u0123\u012b\u0131\u0139\u013d\u0144\u014b\u014d\u014f\u0151"+
		"\u0158\u015a\u0163\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}