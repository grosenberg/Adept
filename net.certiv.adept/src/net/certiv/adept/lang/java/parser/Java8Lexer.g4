/*
 * [The "BSD license"]
 *  Copyright (c) 2014 Terence Parr
 *  Copyright (c) 2014 Sam Harwell
 *  Copyright (c) 2018 Gerald Rosenberg
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

lexer grammar Java8Lexer ;

@header {
	package net.certiv.adept.lang.java.parser.gen;
}


// §3.9 Keywords

ABSTRACT :          'abstract';
ASSERT:             'assert';
BOOLEAN:            'boolean';
BREAK:              'break';
BYTE:               'byte';
CASE:               'case';
CATCH:              'catch';
CHAR:               'char';
CLASS:              'class';
CONST:              'const';
CONTINUE:           'continue';
DEFAULT:            'default';
DO:                 'do';
DOUBLE:             'double';
ELSE:               'else';
ENUM:               'enum';
EXTENDS:            'extends';
FINAL:              'final';
FINALLY:            'finally';
FLOAT:              'float';
FOR:                'for';
IF:                 'if';
GOTO:               'goto';
IMPLEMENTS:         'implements';
IMPORT:             'import';
INSTANCEOF:         'instanceof';
INT:                'int';
INTERFACE:          'interface';
LONG:               'long';
NATIVE:             'native';
NEW:                'new';
PACKAGE:            'package';
PRIVATE:            'private';
PROTECTED:          'protected';
PUBLIC:             'public';
RETURN:             'return';
SHORT:              'short';
STATIC:             'static';
STRICTFP:           'strictfp';
SUPER:              'super';
SWITCH:             'switch';
SYNCHRONIZED:       'synchronized';
THIS:               'this';
THROW:              'throw';
THROWS:             'throws';
TRANSIENT:          'transient';
TRY:                'try';
VOID:               'void';
VOLATILE:           'volatile';
WHILE:              'while';

// §3.10.1 Integer Literals

IntegerLiteral
	:	('0' | [1-9] ( Digits? | '_'+ Digits )) [lL]?				// decimal
	|	'0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?		// hex
	|	'0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?						// octal
	|	'0' [bB] [01] ([01_]* [01])? [lL]?							// binary
	;

// §3.10.2 Floating-Point Literals

FloatingPointLiteral
	: (Digits '.' Digits? | '.' Digits) ExponentPart? [fFdD]?
    | Digits (ExponentPart [fFdD]? | [fFdD])
	| '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits) [pP] [+-]? Digits [fFdD]?
	;

// §3.10.3 Boolean Literals

BooleanLiteral
	:	'true'
	|	'false'
	;

// §3.10.4 Character Literals

CharacterLiteral
	:	'\'' ( ~['\\\r\n] | EscSeq ) '\''
	;

// §3.10.5 String Literals

StringLiteral
	:	'"' ( ~["\\\r\n] | EscSeq )* '"'
	;

// §3.10.6 Escape Sequences for Character and String Literals

fragment 
EscSeq
    : '\\' [btnfr"'\\]
    | '\\' ( [0-3]? [0-7] )? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

// Fragment rules

fragment 
Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment 
ExponentPart
    : [eE] [+-]? Digits
    ;

fragment 
HexDigit
    : [0-9a-fA-F]
    ;

fragment 
HexDigits
    : HexDigit (( HexDigit | '_' )* HexDigit )?
    ;


// §3.10.7 The Null Literal

NullLiteral
	:	'null'
	;

// §3.11 Separators

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMI   : ';';
COMMA  : ',';
DOT    : '.';



// §3.12 Operators

ASSIGN		: '=';
GT			: '>';
LT			: '<';
BANG		: '!';
TILDE		: '~';
QMARK		: '?';
COLON		: ':';
DCOLON		: '::';
EQUAL		: '==';
LE			: '<=';
GE			: '>=';
NOTEQUAL	: '!=';
AND			: '&&';
OR			: '||';
INC			: '++';
DEC			: '--';
ADD			: '+';
SUB			: '-';
STAR		: '*';
DIV			: '/';
BITAND		: '&';
BITOR		: '|';
CARET		: '^';
MOD			: '%';
ARROW		: '->';

ADD_ASSIGN		: '+=';
SUB_ASSIGN		: '-=';
MUL_ASSIGN		: '*=';
DIV_ASSIGN		: '/=';
AND_ASSIGN		: '&=';
OR_ASSIGN		: '|=';
XOR_ASSIGN		: '^=';
MOD_ASSIGN		: '%=';
LSHIFT_ASSIGN	: '<<=';
RSHIFT_ASSIGN	: '>>=';
URSHIFT_ASSIGN	: '>>>=';

// Additional symbols not defined in the lexical specification

AT			: '@';
ELLIPSIS	: '...';


// §3.8 Identifiers

Identifier
	:	Letter LetterOrDigit*
	;

fragment 
Letter
    : [a-zA-Z$_] 						// "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] 	// all non-surrogate characters above 0x7F 
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] 	// UTF-16 surrogates for U+10000 to U+10FFFF
    ;

fragment 
LetterOrDigit
    : Letter
    | [0-9]
    ;


//
// Whitespace and comments
//

HWS	:	Hws+	-> channel(HIDDEN)	;
VWS	:	Vws		-> channel(HIDDEN)	;

BLOCKCOMMENT
 	: ( DocComment | BlockComment ) -> channel(HIDDEN)
	;

LINECOMMENT
 	: LineComment -> channel(HIDDEN)
	;

fragment Hws			: [ \t]			;
fragment Vws			: '\r'? [\n\f]	;

fragment DocComment		: '/**'  .*? ( '*/' | EOF )	;
fragment BlockComment	: '/*'   .*? ( '*/' | EOF )	;
fragment LineComment	: '//' ( ~[\r\n\f]* | EOF ) ;

