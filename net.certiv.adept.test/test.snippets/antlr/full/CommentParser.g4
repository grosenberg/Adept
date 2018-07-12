/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
	: at=PARAM ( word desc? )?
	| at=ATTAG desc?
	;

code	: mark=CODE word* RBRACE ;

word	: WORD		;

preform	: PREFORM	;

inline	: INLINE	;

hdret	: HDRET		;

flow	: FLOW		;

list	: LIST		;

item	: ITEM		;

blank	: BLANK		;
