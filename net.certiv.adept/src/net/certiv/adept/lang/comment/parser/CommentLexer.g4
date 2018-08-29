/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
 lexer grammar CommentLexer;

options {
	superClass = CommentLexerAdaptor ;
}

tokens {
	WORD
}

@header {
	package net.certiv.adept.lang.comment.parser.gen;
	import net.certiv.adept.lang.comment.parser.CommentLexerAdaptor;
}


// -------------------------

DOC		: '/**' -> pushMode(block)	;
BLOCK	: '/*'  -> pushMode(block)	;
LINE	: '//'  -> pushMode(line)	;

WS		: ( VWs | HWs )+ -> skip 	;


mode block ;
	END		: HWs* '*/'	-> popMode ;
	BLANK	: { atBol() }? HWs* (Star HWs*)? VWs ;
	MID		: { atBol() }? HWs* Star  -> skip ;

	PREFORM	: '<pre>' .*? '</pre>' ;
	HDRET	: TagBeg Break  TagEnd ;
	INLINE	: TagBeg Inline TagEnd ;
	FLOW	: TagBeg Flow   TagEnd ;
	LIST	: TagBeg List   TagEnd ;
	ITEM	: TagBeg Item   TagEnd ;

	CODE	: Code		-> pushMode(code) ;
	PARAM	: Param	;
	ATTAG	: AtTag	;

	CHAR	: Char	{ more(WORD); } ;
	BWS		: ( VWs | HWs ) -> skip ;

mode line ;
	NL		: ( Nl | EOF )	-> popMode ;

	LINLINE	: TagBeg Inline TagEnd  -> type(INLINE) ;

	LCODE	: Code		-> type(CODE), pushMode(code) ;
	LCHAR	: Char 		{ more(WORD); } ;
	LWS		: HWs 		-> skip ;


mode code ;
	RBRACE	: ( RBrace | Nl | EOF )	-> popMode ;

	CCHAR	: Char	{ more(WORD); } ;
	CWS		: HWs	-> skip ;


// -------------

fragment TagBeg : LAngle Slash? HWs* ;
fragment TagEnd	: Style* Slash? RAngle ;
fragment Style	: ~[=>\r\n]+ Eq ( ~[>\\\r\n] | EscSeq )+ ;

fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// standard
		| UnicodeEsc	// unicode
		| .				// other
		| EOF			// incomplete
		)
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;

fragment HexDigit		: [0-9a-fA-F]	;

// all chars except control & ws
fragment Char
	: '\u0021'..'\u007E'
	| '\u00A0'..'\u00FF'
	| '\u0100'..'\u02FF'
	| '\u0300'..'\u03FF'
	| '\u0400'..'\u1FFF'
	| '\u200B'..'\u200D'
	| '\u2010'..'\u2027'
	| '\u2030'..'\u205E'
	| '\u2070'..'\u2FFF'
	| '\u3001'..'\uD7FF'
	| '\uF900'..'\uFDCF'
	| '\uFDF0'..'\uFFFD'
	;


fragment Code	: '{@'		;
fragment Param	: '@param'	;
fragment AtTag
	: At
	( 'author' | 'version' | 'return' | 'exception' | 'throws'
	| 'see' | 'since' | 'serial' | 'serialField' | 'serialData' | 'deprecated'
	)
	;

fragment Inline
	: 'b' | 'del' | 'em' | 'i' | 'samp' | 'small' | 'strong' | 'tt' | 'u'
	| 'a' | 'abbr' | 'cite' | 'code' | 'dfn' | 'img'| 'mark'
	| 'q' | 'span' | 'sub' | 'sup' | 'time'
	;

fragment Break
	: 'br'
	;

fragment Flow
	: 'p' | 'hr' | 'header' | 'main' | 'section'
	;

fragment List
	: 'ol' | 'ul' | 'dl' | 'table' | 'thead' | 'tbody' | 'tfoot' | 'tr'
	;

fragment Item
	: 'li' | 'dd' | 'dt' | 'td' | 'th'
	;

fragment Esc	: '\\'			;
fragment Nl		: '\r'? '\n'	;
fragment HWs	: [ \t]			;
fragment VWs	: [\r\n\f]		;

fragment Underscore	: '_'	;
fragment LBrace		: '{'	;
fragment RBrace		: '}'	;
fragment LAngle		: '<'	;
fragment RAngle		: '>'	;
fragment Slash		: '/'	;
fragment Star		: '*'	;
fragment At			: '@'	;
fragment Eq			: '='	;

