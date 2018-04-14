lexer grammar HtmlLexer;

@header {
	package net.certiv.adept.lang.html.parser.gen;
}

@members {
	public boolean isTag() {
		return letter() && closed();
	}

	private boolean letter() {
		return Character.isLetter((char) _input.LA(1));
	}

	private boolean closed() {
		int p = _input.index();
		int z = _input.size();
		for (int idx = p + 1; idx < z; idx++) {
			char la = (char) _input.LA(idx - p);
			if (la == '>') return true;
			if (la == '\r' || la == '\n') return false;
		}
		return false;
	}
}


TAG_DOCTYPE		: '<!DOCTYPE' .*? '>'	;
TAG_COMMENT		: '<!--' .*? '-->'		;

TAG_OPEN_SLASH	: '</'				-> pushMode(TAG) ;
TAG_OPEN 		: '<' { isTag() }?  -> pushMode(TAG) ;

SPCHAR
	: AMP HASH INT SEMI
	| AMP LETTERS SEMI
	;

PCDATA	: PCCHAR	;
WS		: [ \t]+	;
EOL		: [\r\n\f]+	;


// -- mode ---------------------------------

mode TAG ;
	TAG_CLOSE_SLASH	: '/>'		-> popMode  ;
	TAG_CLOSE		: '>'		-> popMode  ;
	TAG_WS			: [ \t]		-> type(WS) ;
	NAME			: NAMECHAR+	;
	ATTR_EQ	 		: '='		;
	ATTR_VALUE 		: LITERAL	;

// -- fragments ----------------------------

fragment NAMECHAR
	: LETTER | DIGIT |  '.' | '-' | '_'
	;

// catch-all; after all other tokenized strings
fragment PCCHAR 
	: LETTER | DIGIT | PUNCTUATION | '/' | '>' | UNICODE | '<' | '&'
    ;

fragment LETTERS
    :	LETTER+
    ;
    
fragment INT
	:	DIGIT+
	;

fragment DIGIT
    :    '0'..'9'
    ;

fragment LETTER
	:	'A'..'Z'
	|	'a'..'z'
    ;

fragment LITERAL
    :  '"'  ( ESCAPE_SEQ | ~('\\'|'"')  )* '"'
    |  '\'' ( ESCAPE_SEQ | ~('\''|'\\') )* '\''
    ;

fragment ESCAPE_SEQ
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

fragment UNICODE
	:	'\u0080'..'\u200B'	// Unicode upper range
	|	'\u200D'..'\uD7FF'
	;
    
fragment Esc			: '\\'	;
fragment HexDigit		: [0-9a-fA-F]	;

fragment AMP	: '&' ;
fragment HASH	: '#' ;
fragment SEMI	: ';' ;

// excludes '<' | '>' | '/' | '&'
fragment PUNCTUATION 
	: '!'..'%'
	| '\''..'.'
	| ':' | ';'
	| '=' | '?'
	| '@'
	| '['..'_'
	| '`'
	| '{'..'~'
	;
