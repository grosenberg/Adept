lexer grammar CommentLexer;

@header {
	package net.certiv.adept.lang.comment.parser.gen;
}

@members {
	public boolean atBol(boolean allowStar) {
		CodePointCharStream cpcs = (CodePointCharStream) _input;
		int offset = cpcs.index();

		for (int dot = -1; dot > -offset; dot--) {
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
INLINE	: LAngle Slash? Inline ( EscSeq | ~[\r\n] )*? RAngle ;
FLOW	: LAngle Slash? Flow   ( EscSeq | ~[\r\n] )*? RAngle ;
LIST	: LAngle Slash? List   ( EscSeq | ~[\r\n] )*? RAngle ;
ITEM	: LAngle Slash? Item   ( EscSeq | ~[\r\n] )*? RAngle ;

PARAM	: '@param' { atBol(true) }? ;
AT		: At	   { atBol(true) }? ;

WORD	: WordChar+ ;
WS		: [ \r\n\t]+ -> skip ;


mode code ;
	CWORD	: WordChar+	 -> type(WORD)	;
	RBRACE	: RBrace	 -> popMode		;
	CWS		: [ \r\n\t]+ -> skip 		;


mode line ;
	LCODE	: '{@'		-> type(CODE), pushMode(code) ;
	LWORD	: WordChar+	-> type(WORD) ;
	NL		: [\r\n]+	-> popMode ;
	HWS		: [ \t]+	-> skip ;


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

fragment WordChar
	:	WordStartChar
	|	'0'..'9'
	|	Underscore
	|	'\u00B7'
	|	'\u0300'..'\u036F'
	|	'\u203F'..'\u2040'
	;

fragment WordStartChar
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

