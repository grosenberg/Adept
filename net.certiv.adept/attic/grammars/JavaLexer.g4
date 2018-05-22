lexer grammar JavaLexer;

//@header {
//	package net.certiv.adept.lang.java.parser.gen;
//}

BLOCKCOMMENT
 	: ( DocComment | BlockComment ) -> channel(HIDDEN)
	;

LINECOMMENT
 	: LineComment -> channel(HIDDEN)
	;

NUM	: DecimalNumeral IntSuffix?
	| HexNumeral IntSuffix?
	| OctalNumeral IntSuffix?
	| BinaryNumeral IntSuffix?
    | DecimalFloatingPointLiteral
    | HexadecimalFloatingPointLiteral
    | NULL
    ;

STRING
	: SQuoteLiteral
	| DQuoteLiteral
	| CharLiteral
	;

ABSTRACT		: 'abstract'	;
ASSERT			: 'assert'		;
BOOLEAN			: 'boolean'		;
BREAK			: 'break'		;
BYTE			: 'byte'		;
CASE			: 'case'		;
CATCH			: 'catch'		;
CHAR			: 'char'		;
CLASS			: 'class'		;
CONST			: 'const'		;
CONTINUE		: 'continue'	;
DEFAULT			: 'default'		;
DO				: 'do'			;
DOUBLE			: 'double'		;
ELSE			: 'else'		;
ENUM			: 'enum'		;
EXTENDS			: 'extends'		;
FINAL			: 'final'		;
FINALLY			: 'finally'		;
FLOAT			: 'float'		;
FOR				: 'for'			;
IF				: 'if'			;
GOTO			: 'goto'		;
IMPLEMENTS		: 'implements'	;
IMPORT			: 'import'		;
INSTANCEOF		: 'instanceof'	;
INT				: 'int'			;
INTERFACE		: 'interface'	;
LONG			: 'long'		;
NATIVE			: 'native'		;
NEW				: 'new'			;
NULL			: 'null'		;
PACKAGE			: 'package'		;
PRIVATE			: 'private'		;
PROTECTED		: 'protected'	;
PUBLIC			: 'public'		;
RETURN			: 'return'		;
SHORT			: 'short'		;
STATIC			: 'static'		;
STRICTFP		: 'strictfp'	;
SUPER			: 'super'		;
SWITCH			: 'switch'		;
SYNCHRONIZED	: 'synchronized';
THIS			: 'this'		;
THROW			: 'throw'		;
THROWS			: 'throws'		;
TRANSIENT		: 'transient'	;
TRY				: 'try'			;
VOID			: 'void'		;
VOLATILE		: 'volatile'	;
WHILE			: 'while'		;

TRUE			: 'true'		;
FALSE			: 'false'		;

AT			: '@'		;
COLON		: ':'		;
DCOLON		: '::'		;
COMMA		: ','		;
SEMI		: ';'		;
LPAREN		: '('		;
RPAREN		: ')'		;
LBRACE		: '{'		;
RBRACE		: '}'		;
LBRACK		: '['		;
RBRACK		: ']'		;
RARROW		: '->'		;
BANG		: '!'		;
QMARK		: '?'		;
// STAR		: '*'		;
PLUS		: '+'		;
MINUS		: '-'		;
MULT		: '*'		;
DIV			: '/'		;
B_AND		: '&'		;
B_OR		: '|'		;
XOR			: '^'		;
MOD			: '%'		;
ASSIGN		: '='		;
PLUS_ASSIGN	: '+='		;
MINUS_ASSIGN: '-='		;
MULT_ASSIGN	: '*='		;
DIV_ASSIGN	: '/='		;
AND_ASSIGN	: '&='		;
OR_ASSIGN	: '|='		;
XOR_ASSIGN	: '^='		;
MOD_ASSIGN	: '%='		;
LEFT_ASSIGN	: '<<='		;
RIGHT_ASSIGN: '>>='		;
UR_ASSIGN	: '>>>='	;
LT			: '<'		;
GT			: '>'		;
EQ			: '=='		;
LE			: '<='		;
GE			: '>='		;
NEQ			: '!='		;
L_AND		: '&&'		;
L_OR		: '||'		;
INCREMENT	: '++'		;
DECREMENT	: '--'		;
L_SHIFT		: '<<'		;
DIAMOND		: '<>'		;
TILDE 		: '~'		;
DOT			: '.'		;
ELLIPSES	: '...'		;
DOLLAR		: '$'		;
POUND		: '#'		;

ESC			: Esc		;
SQUOTE		: SQuote	;
DQUOTE		: DQuote	;


ID	: NameStartChar NameChar* ;

QID : ID ( DOT ID )*	;

HWS	:	Hws+	-> channel(HIDDEN)	;
VWS	:	Vws		-> channel(HIDDEN)	;

ERRCHAR : .		-> channel(HIDDEN)	;

// =======================


fragment Esc			: '\\'	;
fragment SQuote			: '\''	;
fragment DQuote			: '"'	;

fragment Hws			: [ \t]			;
fragment Vws			: '\r'? [\n\f]	;

fragment DocComment		: '/**' .*? ('*/' | EOF)	;
fragment BlockComment	: '/*'  .*? ('*/' | EOF)	;

fragment LineComment	: '//' ~[\r\n]* ;



fragment DecimalFloatingPointLiteral
    : DecDigits DOT DecDigits? ExponentPart? FloatTypeSuffix?
    | DOT DecDigits ExponentPart? FloatTypeSuffix?
    | DecDigits ExponentPart FloatTypeSuffix?
    | DecDigits FloatTypeSuffix
    ;

fragment ExponentPart
    : ExponentIndicator SignedInteger
    ;

fragment ExponentIndicator
    : [eE]
    ;

fragment SignedInteger
    : Sign? DecDigits
    ;

fragment Sign
    : [-+]
    ;

fragment FloatTypeSuffix
    : [fFdD]
    ;

fragment HexadecimalFloatingPointLiteral
    : HexSignificand BinaryExponent FloatTypeSuffix?
    ;

fragment HexSignificand
    : HexNumeral '.'?
    | '0' [xX] HexDigits? DOT HexDigits
    ;

fragment BinaryExponent
    : BinaryExponentIndicator SignedInteger
    ;

fragment BinaryExponentIndicator
    : [pP]
    ;

fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// The standard escaped character set such as tab, newline, etc.
		| UnicodeEsc	// A Unicode escape sequence
		| OctalEsc		// An octal escape sequence
		| .				// Invalid escape character
		| EOF			// Incomplete at EOF
		)
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;

fragment OctalEsc
    : OctalDigit
    | OctalDigit OctalDigit
    | [0-3] OctalDigit OctalDigit
	;

fragment IntSuffix
    : 'l'
    | 'L'
    ;

fragment DecimalNumeral
	: '0'
	| [1-9] DecDigit*
	;

fragment HexNumeral
	: '0' [xX] HexDigit+
	;

fragment OctalNumeral
    : '0' OctalDigit+
	;

fragment BinaryNumeral
    : '0' [bB] BinaryDigit+
	;

fragment DecDigits		: DecDigit+		;
fragment HexDigits		: HexDigit+		;

fragment DecDigit		: [0-9]			;
fragment HexDigit		: [0-9a-fA-F]	;
fragment OctalDigit		: [0-7]			;
fragment BinaryDigit	: [0-1]			;

fragment CharLiteral	: SQuote ( EscSeq | ~['\r\n\\] )  SQuote	;
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
