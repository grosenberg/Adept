lexer grammar CommentLexer;

@header {
	package net.certiv.adept.lang.comment.parser.gen;
}

@members {
	public boolean atBol(boolean allowStar) {
		if (_tokenStartCharIndex == 0) return true;

		CodePointCharStream cpcs = (CodePointCharStream) _input;
		for (int dot = -1; dot >= -(_tokenStartCharIndex); dot--) {
			char c = (char) cpcs.LA(dot);
			switch (c) {
				case '\t':
				case ' ':
					continue;
				case '\r':
				case '\n':
					return true;
				case '*':
					if (allowStar) continue;
				default:
					return false;
			}
		}
		return true;
	}
}

// -------------------------

DOC		: '/**' { atBol(false) }? ;
BLOCK	: '/*'  { atBol(false) }? ;
LINE	: '//'  { atBol(false) }? -> pushMode(line) ;
END		: '*/'  ;

BLANK	: . { atBol(true) }? [ \t]* [\r\n] ;
MID		: Star  { atBol(false) }? -> skip ;


CODE	: '{@'	-> pushMode(code) ;

PREFORM	: '<pre>' .*? '</pre>' ;
HDRET	: LAngle Break Slash? RAngle ;
INLINE	: TagBeg Inline TagEnd ;
FLOW	: TagBeg Flow   TagEnd ;
LIST	: TagBeg List   TagEnd ;
ITEM	: TagBeg Item   TagEnd ;

PARAM	: '@param' { atBol(true) }? ;
AT		: At	   { atBol(true) }? ;

WORD	: WordChar+ ;
WS		: [ \r\n\t]+ -> skip ;


mode code ;
	RBRACE	: RBrace		-> popMode		;
	CWORD	: WordChar+		-> type(WORD)	;
	CNL		: ( Nl | EOF )	-> type(RBRACE), popMode ;
	CHWS	: [ \t]+		-> skip ;


mode line ;
	LINLINE	: TagBeg Inline TagEnd  -> type(INLINE) ;
	LCODE	: '{@'			-> type(CODE), pushMode(code) ;
	LWORD	: WordChar+		-> type(WORD) ;
	NL		: ( Nl | EOF )	-> popMode ;
	HWS		: [ \t]+		-> skip ;


// -------------

fragment Nl		: '\r'? '\n'	;
fragment TagBeg : LAngle Slash? ;
fragment TagEnd : ( ~[>\\\r\n] | EscSeq )* RAngle ;

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

// all chars except control and ws
fragment WordChar
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

fragment Esc			: '\\'	;
fragment HexDigit		: [0-9a-fA-F]	;

fragment Underscore		: '_'	;
fragment LBrace			: '{'	;
fragment RBrace			: '}'	;
fragment LAngle			: '<'	;
fragment RAngle			: '>'	;
fragment Slash			: '/'	;
fragment Star			: '*'	;
fragment At				: '@'	;

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

