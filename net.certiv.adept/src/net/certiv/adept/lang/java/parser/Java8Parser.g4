/*
 * [The "BSD license"]
 *  Copyright (c) 2014 Terence Parr
 *  Copyright (c) 2014 Sam Harwell
 *  Copyright (c) 2018 Gerald Rosenberg
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

parser grammar Java8Parser ;

options {
	tokenVocab = Java8Lexer ;
}

@header {
	package net.certiv.adept.lang.java.parser.gen;
}

// Productions from §7 (Packages)

compilationUnit
	:	packageDeclaration?
		importDeclaration*
		typeDeclaration*
		EOF
	;

packageDeclaration
	:	annotation* PACKAGE qualifiedName SEMI
	;

importDeclaration
    : IMPORT STATIC? qualifiedName (DOT STAR)? SEMI
	;

typeDeclaration
	: classDeclaration
	| enumDeclaration
	| interfaceDeclaration
	| annotationTypeDeclaration
	| SEMI
	;

classModifier
	:	annotation
	|	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	ABSTRACT
	|	STATIC
	|	FINAL
	|	STRICTFP
	;

// Productions from §6 (Names)

qualifiedName
	:	Identifier ( DOT Identifier )*
	;

// Productions from §8 (Classes)

classDeclaration
	: classModifier* CLASS Identifier typeParameters?
		( EXTENDS annotation* classType )?
		( IMPLEMENTS classTypeList )?
		classBody
	;

typeParameters
	: LT typeParameter (COMMA typeParameter)* GT
	;

classTypeList
	:	annotation* classType (COMMA annotation* classType)*
	;

classBody
	:	LBRACE classBodyDeclaration* RBRACE
	;

classBodyDeclaration
	:	classMemberDeclaration
	|	STATIC? block
	|	constructorDeclaration
	;

classMemberDeclaration
	:	fieldDeclaration
	|	methodDeclaration
	|	enumDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|	SEMI
	;

fieldDeclaration
	:	fieldModifier* baseType variableDeclaratorList SEMI
	;

fieldModifier
	:	annotation
	|	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	STATIC
	|	FINAL
	|	TRANSIENT
	|	VOLATILE
	;

variableDeclaratorList
	:	variableDeclarator (COMMA variableDeclarator)*
	;

variableDeclarator
	:	Identifier dims? (ASSIGN variableInitializer)?
	;

variableInitializer
	:	expression
	|	arrayInitializer
	;

// Productions from §3 (Lexical Structure)

literal
	:	IntegerLiteral
	|	FloatingPointLiteral
	|	BooleanLiteral
	|	CharacterLiteral
	|	StringLiteral
	|	NullLiteral
	;

// Productions from §4 (Types, Values, and Variables)

baseType
	:	primitiveType
	|	referenceType
	;

primitiveType
	:	numericType
	|	BOOLEAN
	;

numericType
    : CHAR
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
	;

referenceType
	:	classOrInterfaceType
	|	Identifier
	|	arrayType
	;

classType
	:	( classOrInterfaceType DOT )? annotation* Identifier typeArguments?
	;

classOrInterfaceType
	:	Identifier typeArguments? (DOT annotation* Identifier typeArguments?)*
	;

arrayType
	:	primitiveType dims
	|	classOrInterfaceType dims
	|	Identifier dims
	;

dims
	:	annotation* LBRACK RBRACK (annotation* LBRACK RBRACK)*
	;

typeParameter
	:	annotation* Identifier typeBound?
	;

typeBound
	:	EXTENDS
		( annotation* Identifier
		| annotation* classOrInterfaceType additionalBound*
		)
	;

additionalBound
	:	BITAND annotation* classType
	;

typeArguments
	:	LT typeArgumentList GT
	;

typeArgumentList
	:	typeArgument (COMMA typeArgument)*
	;

typeArgument
	:	annotation* referenceType
	|	wildcard
	;

wildcard
	:	annotation* QMARK ( EXTENDS annotation* referenceType |	SUPER annotation* referenceType )?
	;

methodDeclaration
	:	methodModifier* methodHeader methodBody
	;

methodModifier
	:	annotation
	|	PUBLIC
	|	PROTECTED
	|	PRIVATE
	|	ABSTRACT
	|	STATIC
	|	FINAL
	|	SYNCHRONIZED
	|	NATIVE
	|	STRICTFP
	;

methodHeader
	:	result methodDeclarator throws_?
	|	typeParameters annotation* result methodDeclarator throws_?
	;

result
	:	baseType
	|	VOID
	;

methodDeclarator
	:	Identifier LPAREN formalParameterList? RPAREN dims?
	;

formalParameterList
	:	formalParameters COMMA lastFormalParameter
	|	lastFormalParameter
	;

formalParameters
	:	formalParameter (COMMA formalParameter)*
	|	receiverParameter (COMMA formalParameter)*
	;

formalParameter
	:	(FINAL | annotation)* baseType Identifier dims?
	;

lastFormalParameter
	:	(FINAL | annotation)* baseType annotation* ELLIPSIS Identifier dims?
	|	formalParameter
	;

receiverParameter
	:	annotation* baseType (Identifier DOT)? THIS
	;

throws_
	:	THROWS exceptionTypeList
	;

exceptionTypeList
	:	exceptionType (COMMA exceptionType)*
	;

exceptionType
	:	annotation* classType
	|	annotation* Identifier
	;

methodBody
	:	block
	|	SEMI
	;

constructorDeclaration
	:	constructorModifier* constructorDeclarator throws_? constructorBody
	;

constructorModifier
	:	annotation
	|	PUBLIC
	|	PROTECTED
	|	PRIVATE
	;

constructorDeclarator
	:	typeParameters? Identifier LPAREN formalParameterList? RPAREN
	;

constructorBody
	:	LBRACE explicitConstructorInvocation? blockStatement* RBRACE
	;

explicitConstructorInvocation
	:	typeArguments? THIS LPAREN argumentList? RPAREN SEMI
	|	typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
	|	qualifiedName DOT typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
	|	primary DOT typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
	;

enumDeclaration
	: classModifier* ENUM Identifier
		( IMPLEMENTS classTypeList )?
		enumBody
	;

enumBody
	:	LBRACE enumConstantList? COMMA? (SEMI classBodyDeclaration*)? RBRACE
	;

enumConstantList
	:	enumConstant (COMMA enumConstant)*
	;

enumConstant
	:	annotation* Identifier (LPAREN argumentList? RPAREN)? classBody?
	;

// Productions from §9 (Interfaces)

interfaceDeclaration
	: classModifier* INTERFACE Identifier typeParameters?
		( EXTENDS classTypeList )?
		interfaceBody
	;

interfaceBody
	:	LBRACE interfaceMemberDeclaration* RBRACE
	;

interfaceMemberDeclaration
	:	constantDeclaration
	|	interfaceMethodDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|	SEMI
	;

constantDeclaration
	:	constantModifier* baseType variableDeclaratorList SEMI
	;

constantModifier
	:	annotation
	|	PUBLIC
	|	STATIC
	|	FINAL
	;

interfaceMethodDeclaration
	:	interfaceMethodModifier* methodHeader methodBody
	;

interfaceMethodModifier
	:	annotation
	|	PUBLIC
	|	ABSTRACT
	|	DEFAULT
	|	STATIC
	|	STRICTFP
	;

annotationTypeDeclaration
	:	classModifier* AT INTERFACE Identifier annotationTypeBody
	;

annotationTypeBody
	:	LBRACE annotationTypeMemberDeclaration* RBRACE
	;

annotationTypeMemberDeclaration
	:	annotationTypeElementDeclaration
	|	constantDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|	SEMI
	;

annotationTypeElementDeclaration
	:	(PUBLIC|ABSTRACT)* annotation* baseType Identifier LPAREN RPAREN dims? defaultValue? SEMI
	;

defaultValue
	:	DEFAULT elementValue
	;

annotation
	:	normalAnnotation
	|	markerAnnotation
	|	singleElementAnnotation
	;

normalAnnotation
	:	AT qualifiedName LPAREN elementValuePairList? RPAREN
	;

elementValuePairList
	:	elementValuePair (COMMA elementValuePair)*
	;

elementValuePair
	:	Identifier ASSIGN elementValue
	;

elementValue
	:	expression
	|	elementValueArrayInitializer
	|	annotation
	;

elementValueArrayInitializer
	:	LBRACE elementValueList? COMMA? RBRACE
	;

elementValueList
	:	elementValue (COMMA elementValue)*
	;

markerAnnotation
	:	AT qualifiedName
	;

singleElementAnnotation
	:	AT qualifiedName LPAREN elementValue RPAREN
	;

/*
 * Productions from §10 (Arrays)
 */

arrayInitializer
	:	LBRACE variableInitializerList? COMMA? RBRACE
	;

variableInitializerList
	:	variableInitializer (COMMA variableInitializer)*
	;

/*
 * Productions from §14 (Blocks and Statements)
 */

block
	:	LBRACE blockStatement* RBRACE
	;

blockStatement
	:	localVariableDeclaration SEMI
	|	classDeclaration
	|	enumDeclaration
	|	interfaceDeclaration
	|	statement
	;


localVariableDeclaration
	:	( FINAL | annotation )* baseType variableDeclaratorList
	;

statement
	:	block
	|	SEMI
	|	statementExpression SEMI
	|	ASSERT expression ( COLON expression )? SEMI
	|	SWITCH LPAREN expression RPAREN switchBlock
	|	DO statement WHILE LPAREN expression RPAREN SEMI
	|	BREAK Identifier? SEMI
	|	CONTINUE Identifier? SEMI
	|	RETURN expression? SEMI
	|	SYNCHRONIZED LPAREN expression RPAREN block
	|	THROW expression SEMI
	|	tryStatement
	|	Identifier COLON statement
	|	IF LPAREN expression RPAREN statement ( ELSE statement )?
	|	WHILE LPAREN expression RPAREN statement
	|	FOR LPAREN forControl RPAREN statement
	;

statementExpression
	:	assignment
	|	INC unaryExpression
	|	DEC unaryExpression
	|	postfixExpression INC
	|	postfixExpression DEC
	|	methodInvocation
	|	classInstanceCreationExpression
	;

switchBlock
	:	LBRACE switchBlockStatementGroup* switchLabel* RBRACE
	;

switchBlockStatementGroup
	:	switchLabel+ blockStatement+
	;

switchLabel
	:	CASE expression COLON
	|	CASE Identifier COLON
	|	DEFAULT COLON
	;

tryStatement
	:	TRY block catches
	|	TRY block catches? FINALLY block
	|	TRY LPAREN resourceList SEMI? RPAREN block catches? ( FINALLY block )?
	;

forControl
	:	forInit? SEMI expression? SEMI statementExpressionList?
	|	FINAL? annotation* baseType Identifier dims? COLON expression
	;

forInit
	:	statementExpressionList
	|	localVariableDeclaration
	;

statementExpressionList
	:	statementExpression (COMMA statementExpression)*
	;

catches
	:	( CATCH LPAREN catchFormalParameter RPAREN block )+
	;

catchFormalParameter
	:	FINAL? annotation* catchType Identifier dims?
	;

catchType
	:	classType (BITOR annotation* classType)*
	;

resourceList
	:	resource (SEMI resource)*
	;

resource
	:	(FINAL | annotation)* baseType Identifier dims? ASSIGN expression
	;


// Productions from §15 (Expressions)

primary
	:	( primaryNoNewArray_lfno_primary | arrayCreationExpression ) primaryNoNewArray_lf_primary*
	;

primaryNoNewArray
	:	literal
	|	qualifiedName (LBRACK RBRACK)* DOT CLASS
	|	VOID DOT CLASS
	|	THIS
	|	qualifiedName DOT THIS
	|	LPAREN expression RPAREN
	|	classInstanceCreationExpression
	|	fieldAccess
	|	arrayAccess
	|	methodInvocation
	|	methodReference
	;

primaryNoNewArray_lfno_arrayAccess
	:	literal
	|	qualifiedName (LBRACK RBRACK)* DOT CLASS
	|	VOID DOT CLASS
	|	THIS
	|	qualifiedName DOT THIS
	|	LPAREN expression RPAREN
	|	classInstanceCreationExpression
	|	fieldAccess
	|	methodInvocation
	|	methodReference
	;

primaryNoNewArray_lf_primary
	:	classInstanceCreationExpression_lf_primary
	|	fieldAccess_lf_primary
	|	arrayAccess_lf_primary
	|	DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	DCOLON typeArguments? Identifier
	;

primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary
	:	classInstanceCreationExpression_lf_primary
	|	fieldAccess_lf_primary
	|	DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	DCOLON typeArguments? Identifier
	;

primaryNoNewArray_lfno_primary
	:	literal
	|	qualifiedName (LBRACK RBRACK)* DOT CLASS
	|	primitiveType (LBRACK RBRACK)* DOT CLASS
	|	VOID DOT CLASS
	|	THIS
	|	qualifiedName DOT THIS
	|	LPAREN expression RPAREN
	|	classInstanceCreationExpression_lfno_primary
	|	fieldAccess_lfno_primary
	|	arrayAccess_lfno_primary
	|	methodInvocation_lfno_primary
	|	methodReference_lfno_primary
	;

primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary
	:	literal
	|	qualifiedName (LBRACK RBRACK)* DOT CLASS
	|	primitiveType (LBRACK RBRACK)* DOT CLASS
	|	VOID DOT CLASS
	|	THIS
	|	qualifiedName DOT THIS
	|	LPAREN expression RPAREN
	|	classInstanceCreationExpression_lfno_primary
	|	fieldAccess_lfno_primary
	|	methodInvocation_lfno_primary
	|	methodReference_lfno_primary
	;

arrayCreationExpression
	:	NEW annotation*
		( primitiveType ( dimExpr+ dims? | dims arrayInitializer )
		| classOrInterfaceType ( dimExpr+ dims? | dims arrayInitializer )
		)
	;

arrayAccess
	:	( qualifiedName | primaryNoNewArray_lfno_arrayAccess ) arrayAccessExpression+
	;

arrayAccess_lf_primary
	:	( primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary )? arrayAccessExpression+
	;

arrayAccess_lfno_primary
	:	( qualifiedName | primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary ) arrayAccessExpression+
	;

arrayAccessExpression
	:	( LBRACK expression RBRACK )+
	;

classInstanceCreationExpression
	:	( qualifiedName DOT	| primary DOT )? instanceCreationExpression
	;

classInstanceCreationExpression_lf_primary
	:	DOT instanceCreationExpression
	;

classInstanceCreationExpression_lfno_primary
	:	( qualifiedName DOT )? instanceCreationExpression
	;

instanceCreationExpression
	:	NEW typeArguments? annotation* Identifier (DOT annotation* Identifier)* typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
	;

typeArgumentsOrDiamond
	:	typeArguments
	|	LT GT
	;

fieldAccess
	:	primary DOT Identifier
	|	SUPER DOT Identifier
	|	qualifiedName DOT SUPER DOT Identifier
	;

fieldAccess_lf_primary
	:	DOT Identifier
	;

fieldAccess_lfno_primary
	:	SUPER DOT Identifier
	|	qualifiedName DOT SUPER DOT Identifier
	;

methodInvocation
	:	Identifier LPAREN argumentList? RPAREN
	|	qualifiedName DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	primary DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	SUPER DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	qualifiedName DOT SUPER DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	;

methodInvocation_lfno_primary
	:	Identifier LPAREN argumentList? RPAREN
	|	qualifiedName DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	SUPER DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	|	qualifiedName DOT SUPER DOT typeArguments? Identifier LPAREN argumentList? RPAREN
	;

argumentList
	:	expression (COMMA expression)*
	;

methodReference
	:	primary DCOLON typeArguments? Identifier
	|	qualifiedName DCOLON typeArguments? Identifier
	|	annotation* referenceType DCOLON typeArguments? Identifier
	|	SUPER DCOLON typeArguments? Identifier
	|	qualifiedName DOT SUPER DCOLON typeArguments? Identifier
	|	annotation* classType DCOLON typeArguments? NEW
	|	annotation* arrayType DCOLON NEW
	;

methodReference_lfno_primary
	:	qualifiedName DCOLON typeArguments? Identifier
	|	annotation* referenceType DCOLON typeArguments? Identifier
	|	SUPER DCOLON typeArguments? Identifier
	|	qualifiedName DOT SUPER DCOLON typeArguments? Identifier
	|	annotation* classType DCOLON typeArguments? NEW
	|	annotation* arrayType DCOLON NEW
	;

dimExpr
	:	annotation* LBRACK expression RBRACK
	;

expression
	:	literal
	|	assignment
	|	castExpression
	|	qualifiedName
	|	unaryExpression
	|	expression op=(ADD|SUB|STAR|DIV|MOD) expression
    | 	expression op=(LE|GE|LT|GT) expression
    | 	expression (LT LT | GT GT GT | GT GT) expression
	|	expression op=(AND|OR|BITAND|BITOR|CARET|EQUAL|NOTEQUAL) expression
	|	expression op=QMARK expression COLON expression
	|	expression INSTANCEOF annotation* referenceType
	|	lambdaExpression
	;

assignment
	:	<assoc=right>
		( qualifiedName | fieldAccess | arrayAccess )
		 op=( ASSIGN | ADD_ASSIGN | SUB_ASSIGN | MUL_ASSIGN
    		| DIV_ASSIGN | AND_ASSIGN | OR_ASSIGN | XOR_ASSIGN
    		| RSHIFT_ASSIGN | URSHIFT_ASSIGN | LSHIFT_ASSIGN | MOD_ASSIGN
    		)
    	expression
	;

castExpression
	:	LPAREN annotation* primitiveType RPAREN unaryExpression
	|	LPAREN annotation* referenceType additionalBound* RPAREN ( postfixExpression | lambdaExpression )
	;

lambdaExpression
	:	lambdaParameters ARROW ( expression | block )
	;

lambdaParameters
	:	Identifier
	|	LPAREN formalParameterList? RPAREN
	|	LPAREN Identifier (COMMA Identifier)* RPAREN
	;

unaryExpression
	:	op=(INC|DEC|ADD|SUB) unaryExpression
	|	op=(TILDE|BANG) unaryExpression
	|	postfixExpression
	;

postfixExpression
	:	( primary |	qualifiedName ) ( INC |	DEC )*
	;

