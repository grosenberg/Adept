parser grammar Antlr4Parser;

options {
	tokenVocab = Antlr4Lexer;
}

@header {
	package net.certiv.adept.antlr.parser.gen;
}

adept
	:	( statement
		| ruleSpec
		| other
		)*
		EOF
	;

statement
	: primary
	| cmdBlock
	| atBlock
	;

primary // grammar, import, mode
	: keyword+ id+ SEMI
	;

cmdBlock // options, tokens, channels, imports
	: keyword+ body
	;

body
	: LBRACE ( listStmt | assignStmt+ )? RBRACE
	;

listStmt
	: id ( COMMA id )* COMMA?
	;

assignStmt
	: id EQ ( dottedID | STRING | INT ) SEMI
	;

atBlock
	: AT ( ID | keyword )+ action
	;

ruleSpec
	: prefix* id argsBlock* prequel?
		COLON altList action? function?
		SEMI
	;

argsBlock // parameters, return, throws
	: keyword? arguments
	;

prequel
	: cmdBlock
	| atBlock
	;

altList
	: elements ( OR elements )*
	;

elements
	: element+ ( POUND id )?
	| // empty alt
	;

element
	: namedElement
	| altBlock mod?
	| NOT? ( STRING | DOT | set | id ) mod?
	| STRING RANGE STRING
	| pred
	| LEOF
	;

namedElement
	: label ( element | altBlock) mod?
	;

altBlock
	: LPAREN ( cmdBlock? atBlock* COLON )? altList RPAREN
	;

set
	: SET mod?
	;

pred
	: action QMARK
	;

action
	: BEG_ACTION ACT_CONTENT* END_ACTION
	;

arguments
	: BEG_ARGS ARG_CONTENT* END_ARGS
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
	| RETURNS | LOCALS | THROWS | CATCH | FINALLY
	| MODE | LEOF
	;

prefix
	: FRAGMENT | PUBLIC | PROTECTED | PRIVATE
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

//punct
//	: AT | COLON | COMMA
//	| LPAREN | RPAREN | LBRACE | RBRACE | LBRACK | RBRACK
//	| RARROW | EQ | QMARK | STAR | PLUS | PLUSEQ
//	| NOT | ALT | DOT | RANGE | DOLLAR | POUND
//	;

other
	: .
	;
