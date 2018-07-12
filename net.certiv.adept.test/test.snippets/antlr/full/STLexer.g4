/*	[The "BSD license"]
 *	Copyright (c) 2011-2017 Terence Parr
 *	Copyright (c) 2013-2017 Gerald Rosenberg
 *	All rights reserved.
 *
 *	Redistribution and use in source and binary forms, with or without
 *	modification, are permitted provided that the following conditions
 *	are met:
 *	1. Redistributions of source code must retain the above copyright
 *		notice, this list of conditions and the following disclaimer.
 *	2. Redistributions in binary form must reproduce the above copyright
 *		notice, this list of conditions and the following disclaimer in the
 *		documentation and/or other materials provided with the distribution.
 *	3. The name of the author may not be used to endorse or promote products
 *		derived from this software without specific prior written permission.
 *
 *	THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *	IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *	OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *	IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *	INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *	NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *	DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *	THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *	THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
/*	Antlr grammar for StringTemplate v4. */
 
lexer grammar STLexer;

options {
	superClass=STLexerAdaptor;
}

tokens {
	LBRACE,
	RDELIM,
	COMMA
}

@header {
	package net.certiv.adept.lang.stg.parser.gen;
	import  net.certiv.adept.lang.stg.parser.STLexerAdaptor;
}

// -----------------------------------
// default mode

DOC_COMMENT		: DocComment	-> channel(HIDDEN)	;
BLOCK_COMMENT	: BlockComment	-> channel(HIDDEN)	;
LINE_COMMENT	: LineComment	-> channel(HIDDEN)	;
TMPL_COMMENT	: TmplComment	-> channel(HIDDEN)	;

HORZ_WS			: Hws+			-> channel(HIDDEN)	;
VERT_WS			: Vws+			-> channel(HIDDEN)	;

ESCAPE		: . { isLDelim() }? EscSeq . { isRDelim() }?	; // self contained

LDELIM		: . { isLDelim() }?	-> mode(Template)			; // in template

RBRACE		: RBrace { endsSubTemplate(); }					; // conditional switch to template

TEXT		: . { adjText(); }	;  // handle weird terminals


// -----------------------------------
mode Template ;

	IN_HORZ_WS	: Hws+		-> type(HORZ_WS), channel(HIDDEN)	;
	IN_VERT_WS	: Vws+		-> type(VERT_WS), channel(HIDDEN)	;

	LBRACE		: LBrace { startsSubTemplate() }?	-> mode(SubTemplate)	;
	RDELIM		: . { isRDelim() }?					-> mode(DEFAULT_MODE)		;

	STRING		: String		;

	IF			: 'if'			;
	ELSEIF		: 'elseif'		;
	ELSE		: 'else'		;
	ENDIF		: 'endif'		;
	SUPER		: 'super'		;
	END			: '@end'		;

	//// functions
	//FIRST		: 'first'		;
	//LAST		: 'last'		;
	//REST		: 'rest'		;
	//REVERSE	: 'reverse'		;
	//STRIP		: 'strip'		;
	//TRUNC		: 'trunc'		;
	//TRIM		: 'trim'		;
	//LENGTH	: 'length'		;
	//STRLEN	: 'strlen'		;

	TRUE		: True			;
	FALSE		: False			;

	AT			: At			;
	ELLIPSIS	: Ellipsis		;
	DOT			: Dot			;
	COMMA		: Comma			;
	COLON		: Colon			;
	SEMI		: Semi			;
	AND			: And			;
	OR			: Or			;
	LPAREN		: LParen		;
	RPAREN		: RParen		;
	LBRACK		: LBrack		;
	RBRACK		: RBrack		;
	EQUALS		: Equal			;
	BANG		: Bang			;

	// any unknown content
	ERR_CHAR	: .	-> skip		;


// -----------------------------------
mode SubTemplate ;

	SUB_HORZ_WS		: Hws+		-> type(HORZ_WS), channel(HIDDEN)	;
	SUB_VERT_WS		: Vws+		-> type(VERT_WS), channel(HIDDEN)	;

	ID				: NameStartChar NameChar*	;
	SUB_COMMA		: Comma		-> type(COMMA)	;
	PIPE			: Pipe		-> mode(DEFAULT_MODE);




// ===================================
// Lexer fragments

// -----------------------------------
// Grammar specific fragments

fragment TmplComment: LTmplMark .*? RTmplMark		;

fragment LTmplMark	: . { isLTmplComment() }? Bang	;
fragment RTmplMark	: Bang . { isRTmplComment() }?	;


// -----------------------------------
// Whitespace & Comments

fragment Hws			: [ \t] ;
fragment Vws			: [\r\n\f] ;

fragment DocComment		: '/**' .*? ('*/' | EOF) ;
fragment BlockComment	: '/*'  .*? ('*/' | EOF) ;
fragment LineComment	: '//' ~[\r\n]*			 ;
fragment LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;


// -----------------------------------
// Escapes

fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// standard escaped character
		| UnicodeEsc	// Unicode escape sequence
		| .				// Invalid escape character
		| EOF			// Incomplete
		)
	;

fragment EscAny
	:	Esc .
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;


// -----------------------------------
// Digits

fragment HexDigits	: HexDigit+		;
fragment HexDigit	: [0-9a-fA-F]	;


// -----------------------------------
// Literals

fragment String		: DQuote ( EscSeq | ~["\r\n\\] )* DQuote	;


// -----------------------------------
// Character ranges

fragment NameStartChar
	:	'A'..'Z'
	|	'a'..'z'
	|	Underscore
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
	;

fragment NameChar
	:	NameStartChar
	|	'0'..'9'
	|	Dash
	|	'\u00B7'
	|	'\u0300'..'\u036F'
	|	'\u203F'..'\u2040'
	;


// -----------------------------------
// Types

fragment True		 	: 'true'	;
fragment False			: 'false'	;


// -----------------------------------
// Symbols

fragment Esc			: '\\'	;
fragment At				: '@'	;
fragment Colon			: ':'	;
fragment Semi			: ';'	;
fragment Dot			: '.'	;
fragment Comma			: ','	;
fragment Equal			: '='	;
fragment DQuote			: '"'	;
fragment Underscore		: '_'	;
fragment Dash			: '-'	;
fragment Bang			: '!'	;
fragment Pipe			: '|'	;

fragment LParen			: '('	;
fragment RParen			: ')'	;
fragment LBrace			: '{'	;
fragment RBrace			: '}'	;
fragment LBrack			: '['	;
fragment RBrack			: ']'	;

fragment LShift			: '<<'	;
fragment RShift			: '>>'	;
fragment And			: '&&'	;
fragment Or				: '||'	;
fragment Ellipsis		: '...'	;

