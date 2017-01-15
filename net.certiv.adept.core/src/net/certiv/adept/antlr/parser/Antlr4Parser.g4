parser grammar Antlr4Parser;

options {
	tokenVocab = Antlr4Lexer;
}

@header {
	package net.certiv.adept.antlr.parser.gen;
}

adept
	:	( statement
		| grammarRule
		| other
		)*
		EOF
	;

statement
	: simple
	| block
	| atBlock
	;

simple
	: keyword+ id+ SEMI
	;

block
	: keyword+ body
	;

body
	: LBRACE ( listStmt | assignStmt+ )? RBRACE
	;

atBlock
	: AT ( ID | keyword )+ action
	;

listStmt
	: id ( COMMA id )* COMMA?
	;

assignStmt
	: id EQ ( dottedID | STRING | INT ) SEMI
	;

grammarRule
	: keyword* id atBlock? COLON elementList action? function? SEMI
	;

elementList
	: ( label | NOT )? LPAREN elementList RPAREN mod?
	| element+ ( ALT element+ )*
	;

element
	: label? id mod?
	| STRING RANGE STRING
	| NOT? ( STRING | DOT | set | id ) mod?
	| pred
	| LEOF
	;

set
	: SET mod?
	;

pred
	: actionBlock QMARK
	;

action
	: ID actionBlock
	;

actionBlock
	: BEGIN_ACTION ACTION_CONTENT* END_ACTION
	;

function
	: RARROW attribute attrValue? ( COMMA attribute attrValue? )*
	;

attrValue
	: LPAREN ID RPAREN
	;

dottedID
	: id ( DOT id )*
	;

label
	: ID op
	;

id
	: ID
	;

keyword
	: GRAMMAR | LEXER | PARSER
	| OPTIONS | TOKENS | IMPORT | CHANNELS
	| COLONCOLON | HEADER | MEMBERS | INIT | AFTER
	| FRAGMENT | PROTECTED | PUBLIC | PRIVATE
	| RETURNS | LOCALS | THROWS | CATCH | FINALLY
	| MODE | LEOF
	;

attribute
	: CHANNEL | LSKIP | LMORE
	| PUSHMODE | POPMODE | TYPE
	;

op
	: EQ | PLUSEQ
	;

mod
	: STAR QMARK? | PLUS QMARK? | QMARK
	;

punct
	: AT | COLON | COMMA
	| LPAREN | RPAREN | LBRACE | RBRACE | LBRACK | RBRACK
	| RARROW | EQ | QMARK | STAR | PLUS | PLUSEQ
	| NOT | ALT | DOT | RANGE | DOLLAR | POUND
	;

other
	: .
	;
