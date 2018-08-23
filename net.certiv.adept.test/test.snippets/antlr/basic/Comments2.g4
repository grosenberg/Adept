/*******************************************************************************
 * Copyright (c) 2010-2015 Gerald Rosenberg & others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the standard 3-clause BSD License.  A copy of the License
 * is provided with this distribution in the License.txt file.
 *******************************************************************************/
/* ANTLR v4 XVisitor grammar */

lexer grammar XVisitorLexer;

@header {
	package net.certiv.xvisitordt.core.parser.gen;
	import  net.certiv.xvisitordt.core.parser.LexerAdaptor;
}

options {
	superClass = LexerAdaptor ;
}

tokens {
	INT,
	RBRACE,
	TEXT
}



// ---------------------------------------------------------------------------------
// default mode

// --------
// Comments
//
// All comments go on the hidden channel - no reason to handle JavaDoc
// comments in the parser. Consecutive line comments are gathered into a
// single token to simplify automated formatting.  Horizontal and vertical
// whitespace is tokenized separately to also simplify automated formatting.

DOC_COMMENT
	:	DocComment -> channel(HIDDEN)
	;

BLOCK_COMMENT
	:	BlockComment -> channel(HIDDEN)
	;

LINE_COMMENT
	:	LineComment -> channel(HIDDEN)
	;

// -------
// From the default mode, options and action blocks are handled in separate modes.
// The RBRACE should never be encountered in the default mode.
//
OPTIONS
	: 'options'	-> pushMode(Options)
	;

LBRACE
	: LBrace	-> pushMode(ActionBlock)
	;

