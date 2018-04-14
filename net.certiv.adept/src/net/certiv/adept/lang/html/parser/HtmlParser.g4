parser grammar HtmlParser;

options {
	tokenVocab = HtmlLexer ;
}

@header {
	package net.certiv.adept.lang.html.parser.gen;
}

html
	: ( tag text* )+ EOF
 	;

tag	:
	  TAG_OPEN NAME attributeList? WS*
	  		( TAG_CLOSE
	  		| TAG_CLOSE_SLASH
	  		)
	| TAG_OPEN_SLASH NAME TAG_CLOSE
	| TAG_DOCTYPE
	| TAG_COMMENT
	;

attributeList
	: attribute+
	;

attribute
	: WS+ NAME ( ATTR_EQ ATTR_VALUE )?
	;

text
	: PCDATA+
	| SPCHAR
	| WS
	| EOL
	;
