lexer grammar Antlr4Lexer;

options {
	superClass = LexerErrorStrategy ;
}

@header {
	package net.certiv.adept.antlr.parser.gen;
	import net.certiv.adept.parser.LexerErrorStrategy;
}

@members {
	protected void handleEndAction() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(ACT_CONTENT);
		}
	}

	protected void handleEndArgs() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(ARG_CONTENT);
		}
	}
}

BLOCKCOMMENT
 	: ( DocComment | BlockComment ) -> channel(HIDDEN)
	;

LINECOMMENT
 	: LineComment -> channel(HIDDEN)
	;

INT	: Int ;

SET : LBRACK ( ESC | ~']' )* RBRACK ;

STRING
	: SQuoteLiteral
	| DQuoteLiteral
	;

BEG_ACTION	: LBrace 		-> pushMode(Action)		;
BEG_ARGS	: LBrack 		-> pushMode(Args)		;
OPTIONS		: 'options'		-> pushMode(Options) 	;
TOKENS		: 'tokens'		-> pushMode(ListBlock)	;
CHANNELS	: 'channels'	-> pushMode(ListBlock)	;

IMPORT		: 'import'		;
CHANNEL		: 'channel'		;

LSKIP		: 'skip'		;
LMORE		: 'more'		;
LEOF 		: 'EOF'			;

HEADER		: 'header'		;
MEMBERS		: 'members'		;
INIT		: 'init'		;
AFTER		: 'after'		;

MODE		: 'mode'		;
PUSHMODE	: 'pushMode'	;
POPMODE		: 'popMode'		;
TYPE		: 'type'		;

FRAGMENT	: 'fragment'	;
LEXER		: 'lexer'		;
PARSER		: 'parser'		;
GRAMMAR		: 'grammar'		;
PROTECTED	: 'protected'	;
PUBLIC		: 'public'		;
PRIVATE		: 'private'		;
RETURNS		: 'returns'		;
LOCALS		: 'locals'		;
THROWS		: 'throws'		;
CATCH		: 'catch'		;
FINALLY		: 'finally'		;

AT			: At			;
COLON		: Colon			;
COLONCOLON	: ColonColon	;
COMMA		: Comma			;
SEMI		: Semi			;
LPAREN		: LParen		;
RPAREN		: RParen		;
LBRACE		: LBrace		;
RBRACE		: RBrace		;
LBRACK		: LBrack		;
RBRACK		: RBrack		;
RARROW		: RArrow		;
EQ			: Eq			;
QMARK		: QMark			;
STAR		: Star			;
PLUS		: Plus			;
PLUSEQ		: Pluseq		;
NOT			: Not			;
OR			: Or			;
DOT			: Dot			;
RANGE		: Range			;
DOLLAR		: Dollar		;
POUND		: Pound			;

ESC			: Esc			;
SQUOTE		: SQuote		;
DQUOTE		: DQuote		;


ID	: Id ;

HWS	: Hws+	-> channel(HIDDEN)	;
VWS	: Vws	-> channel(HIDDEN)	;

ERRCHAR	: .	-> channel(HIDDEN)	;


// -------------------------

mode Action;

	ACT_NESTED  : LBrace		-> type(ACT_CONTENT), pushMode(Action)	;
	END_ACTION	: RBrace		{ handleEndAction(); }	;

	ACT_DOC		: DocComment 	-> type(ACT_CONTENT)	;
	ACT_BLOCK	: BlockComment 	-> type(ACT_CONTENT)	;
	ACT_LINE	: LineComment 	-> type(ACT_CONTENT)	;
	ACT_STRING	: DQuoteLiteral	-> type(ACT_CONTENT)	;
	ACT_CHAR	: SQuoteLiteral	-> type(ACT_CONTENT)	;
	ACT_ESCAPE	: Esc			-> type(ACT_CONTENT)	;
	ACT_EOF		: EOF 			-> popMode    			;

	ACT_CONTENT : .		;

// -------------------------

mode Args; // [int x, List<String> a[]]

	ARG_NESTED	: LBrack		-> type (ARG_CONTENT), pushMode(Args)	;
	END_ARGS	: RBrack		{ handleEndArgs(); }	;

	ARG_ESC		: Esc			-> type (ARG_CONTENT)   ;
	ARG_STRING	: DQuoteLiteral -> type (ARG_CONTENT)   ;
	ARG_CHAR	: SQuoteLiteral -> type (ARG_CONTENT)   ;
	ARG_EOF		: EOF			-> popMode				;

	ARG_CONTENT	: . 	;

// -------------------------

mode Options;

	OPT_DOC		: DocComment	-> type(BLOCKCOMMENT), channel(HIDDEN)	;
	OPT_BLOCK	: BlockComment	-> type(BLOCKCOMMENT), channel(HIDDEN)	;
	OPT_LINE	: LineComment	-> type(LINECOMMENT), channel(HIDDEN)	;
	OPT_HWS		: Hws+			-> type(HWS), channel(HIDDEN)			;
	OPT_VWS		: Vws			-> type(VWS), channel(HIDDEN)			;

	OPT_LBRACE	: LBrace		-> type(LBRACE)   			;
	OPT_RBRACE	: RBrace		-> type(RBRACE), popMode	;

	OPT_ID		: Id			-> type(ID)   	;
	OPT_DOT		: Dot			-> type(DOT)	;
	OPT_ASSIGN	: Eq			-> type(EQ)	;
	OPT_STRING	: SQuoteLiteral	-> type(STRING)	;
	OPT_INT		: Int			-> type(INT)	;
	OPT_STAR	: Star			-> type(STAR)	;
	OPT_SEMI	: Semi			-> type(SEMI)	;

// -------------------------

mode ListBlock; // tokens and channel blocks

	LST_DOC		: DocComment	-> type(BLOCKCOMMENT), channel(HIDDEN)	;
	LST_BLOCK	: BlockComment	-> type(BLOCKCOMMENT), channel(HIDDEN)	;
	LST_LINE	: LineComment	-> type(LINECOMMENT), channel(HIDDEN)	;
	LST_HWS		: Hws+			-> type(HWS), channel(HIDDEN)			;
	LST_VWS		: Vws			-> type(VWS), channel(HIDDEN)			;

	LST_LBRACE	: LBrace 		-> type(LBRACE)				;
	LST_RBRACE	: RBrace		-> type(RBRACE), popMode	;

	LST_ID		: Id			-> type(ID)		;
	LST_DOT		: Dot			-> type(DOT)	;
	LST_COMMA	: Comma			-> type(COMMA)	;

// =======================

fragment Id 	: NameStartChar NameChar* ;

fragment At			: '@'	;
fragment Colon		: ':'	;
fragment ColonColon	: '::'	;
fragment Comma		: ','	;
fragment Semi		: ';'	;
fragment LParen		: '('	;
fragment RParen		: ')'	;
fragment LBrace		: '{'	;
fragment RBrace		: '}'	;
fragment LBrack		: '['	;
fragment RBrack		: ']'	;
fragment RArrow		: '->'	;
fragment Eq			: '='	;
fragment QMark		: '?'	;
fragment Star		: '*'	;
fragment Plus		: '+'	;
fragment Pluseq		: '+='	;
fragment Not		: '~'	;
fragment Or			: '|'	;
fragment Dot		: '.'	;
fragment Range		: '..'	;
fragment Dollar		: '$'	;
fragment Pound		: '#'	;


fragment Esc			: '\\'	;
fragment SQuote			: '\''	;
fragment DQuote			: '"'	;

fragment Hws			: [ \t]			;
fragment Vws			: '\r'? [\n\f]	;

fragment DocComment		: '/**' .*? ('*/' | EOF)	;
fragment BlockComment	: '/*'  .*? ('*/' | EOF)	;

fragment LineComment	: '//' ~[\r\n]* 							;
fragment LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;

fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// The standard escaped character set such as tab, newline, etc.
		| UnicodeEsc	// A Unicode escape sequence
		| .				// Invalid escape character
		| EOF			// Incomplete at EOF
		)
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;

fragment Int
	:	'0'
	|	[1-9] DecDigit*
	;

fragment DecDigit		: [0-9]			;
fragment HexDigit		: [0-9a-fA-F]	;

fragment SQuoteLiteral	: SQuote ( EscSeq | ~['\r\n\\] )* SQuote	;
fragment DQuoteLiteral	: DQuote ( EscSeq | ~["\r\n\\] )* DQuote	;


fragment NameChar
	:	NameStartChar
	|	'0'..'9'
	|	'_'
	|	'\u00B7'
	|	'\u0300'..'\u036F'
	|	'\u203F'..'\u2040'
	;

fragment NameStartChar
	:	'A'..'Z'
	|	'a'..'z'
	|	'\u00C0'..'\u00D6'
	|	'\u00D8'..'\u00F6'
	|	'\u00F8'..'\u02FF'
	|	'\u0370'..'\u037D'
	|	'\u037F'..'\u1FFF'
	|	'\u200C'..'\u200D'
	|	'\u2070'..'\u218F'
	|	'\u2C00'..'\u2FEF'
	|	'\u3001'..'\uD7FF'
	|	'\uF900'..'\uFDCF'
	|	'\uFDF0'..'\uFFFD'
	;	// ignores | ['\u10000-'\uEFFFF] ;
