parser grammar CommentParser;

options {
	tokenVocab = CommentLexer;
}

@header {
	package net.certiv.adept.lang.comment.parser.gen;
}

comment
	: ( doc | block | line ) EOF
	;

doc
	: DOC desc? ( param | blank )* ( END | EOF )
	;

block
	: BLOCK desc? ( END | EOF )
	;

line
	: LINE ( word | code | inline )* ( NL | EOF )
	;

desc
	: 	( word | code | inline | preform
		| flow | list | item
		| blank | hdret
		)+
	;

param
	: at=PARAM ( name=WORD desc? )?
	| at=AT form=WORD desc?
	;

code	: mark=CODE words+=WORD* RBRACE	;

word	: WORD	;

preform	: PREFORM	;

inline	: INLINE	;

hdret	: HDRET		;

flow	: FLOW		;

list	: LIST		;

item	: ITEM		;

blank	: BLANK		;
