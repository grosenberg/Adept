parser grammar Antlr4Parser;

options {
	tokenVocab = Antlr4Lexer ;
	// TokenLabelType = AdeptToken ;
	// contextSuperClass = RuleContextWithAltNum ;
}

@header {
	package net.certiv.adept.lang.antlr.parser.gen;
}

antlr
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
	: prefix* id argsBlock* prequel? ruleBlock
	;

ruleBlock
	: COLON altList action? function? SEMI
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
	| NOT? ( altBlock | STRING | DOT | set | id ) mod?
	| STRING RANGE STRING mod?
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
	: LPAREN ( LHIDDEN | ID ) RPAREN
	;

dottedID
	: x=id ( DOT id )*
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
	| RETURNS | LOCALS | THROWS | CATCH | FINALLY
	| COLONCOLON | MODE | LEOF
	;

prefix
	: FRAGMENT | PUBLIC | PROTECTED | PRIVATE
	;

attribute
	: CHANNEL | LSKIP | LMORE
	| PUSHMODE | POPMODE | MODE | TYPE
	;

op
	: EQ | PLUSEQ
	;

mod
	: STAR QMARK? | PLUS QMARK? | QMARK
	;

other
	: .
	;
