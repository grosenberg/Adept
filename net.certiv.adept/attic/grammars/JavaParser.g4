parser grammar JavaParser;

options {
    tokenVocab = JavaLexer;
	TokenLabelType = AdeptToken ;
}

@header {
	package net.certiv.adept.lang.java.parser.gen;
}

packageName
    : ID (DOT ID)*
    ;

typeName
    : ID
    | packageOrTypeName DOT ID
    ;

packageOrTypeName
    : ID (DOT ID)*
    ;

expressionName
    : ID
    | ambiguousName DOT ID
    ;

methodName
    : ID
    ;

ambiguousName
    : ID (DOT ID)*
    ;

simpleTypeName
    : ID
    ;

typeParameterModifier
    : annotation
    ;

literal
    : NUM
    | STRING
    ;

type
    : primitiveType
    | referenceType
    ;

primitiveType
    : (annotation)* numericType
    | (annotation)* BOOLEAN
    ;

numericType
    : integralType
    | floatingPointType
    ;

integralType
    : BYTE
    | SHORT
    | INT
    | LONG
    | CHAR
    ;

floatingPointType
    : FLOAT
    | DOUBLE
    ;

referenceType
    : classOrInterfaceType
    | typeVariable
    | arrayType
    ;

classOrInterfaceType
    : classType
    | interfaceType
    ;

classType
    : (annotation)* ID (typeArguments)?
    | classType DOT (annotation)* ID (typeArguments)?
    ;

interfaceType
    : classType
    ;

typeVariable
    : (annotation)* ID
    ;

arrayType
    : primitiveType dims
    | classOrInterfaceType dims
    | typeVariable dims
    ;

dims
    : (annotation)* LBRACK RBRACK ((annotation)* LBRACK RBRACK)*
    ;

typeParameter
    : (typeParameterModifier)* ID (typeBound)?
    ;

typeBound
    : EXTENDS typeVariable
    | EXTENDS classOrInterfaceType (additionalBound)*
    ;

additionalBound
    : B_AND interfaceType
    ;

typeArguments
    : LT typeArgumentList GT
    ;

typeArgumentList
    : typeArgument (COMMA typeArgument)*
    ;

typeArgument
    : referenceType
    | wildcard
    ;

wildcard
    : (annotation)* QMARK (wildcardBounds)?
    ;

wildcardBounds
    : EXTENDS referenceType
    | SUPER referenceType
    ;

compilationUnit
    : (packageDeclaration)? (importDeclaration)* (typeDeclaration)*
    ;

packageDeclaration
    : (packageModifier)* PACKAGE ID (DOT ID)* SEMI
    ;

packageModifier
    : annotation
    ;

importDeclaration
    : singleTypeImportDeclaration
    | typeImportOnDemandDeclaration
    | singleStaticImportDeclaration
    | staticImportOnDemandDeclaration
    ;

singleTypeImportDeclaration
    : IMPORT typeName SEMI
    ;

typeImportOnDemandDeclaration
    : IMPORT packageOrTypeName DOT MULT SEMI
    ;

singleStaticImportDeclaration
    : IMPORT STATIC typeName DOT ID SEMI
    ;

staticImportOnDemandDeclaration
    : IMPORT STATIC typeName DOT MULT SEMI
    ;

typeDeclaration
    : classDeclaration
    | interfaceDeclaration
    | SEMI
    ;

classDeclaration
    : normalClassDeclaration
    | enumDeclaration
    ;

normalClassDeclaration
    : (classModifier)* CLASS ID (typeParameters)? (superclass)? (superinterfaces)? classBody
    ;

classModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | ABSTRACT
    | STATIC
    | FINAL
    | STRICTFP
    ;

typeParameters
    : LT typeParameterList GT
    ;

typeParameterList
    : typeParameter (COMMA typeParameter)*
    ;

superclass
    : EXTENDS classType
    ;

superinterfaces
    : IMPLEMENTS interfaceTypeList
    ;

interfaceTypeList
    : interfaceType (COMMA interfaceType)*
    ;

classBody
    : LBRACE classBodyDeclaration* RBRACE
    ;

classBodyDeclaration
    : classMemberDeclaration
    | instanceInitializer
    | staticInitializer
    | constructorDeclaration
    ;

classMemberDeclaration
    : fieldDeclaration
    | methodDeclaration
    | classDeclaration
    | interfaceDeclaration
    | SEMI
    ;

fieldDeclaration
    : (fieldModifier)* unannType variableDeclaratorList SEMI
    ;

fieldModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | STATIC
    | FINAL
    | TRANSIENT
    | VOLATILE
    ;

variableDeclaratorList
    : variableDeclarator (COMMA variableDeclarator)*
    ;

variableDeclarator
    : variableDeclaratorId (ASSIGN variableInitializer)?
    ;

variableDeclaratorId
    : ID (dims)?
    ;

variableInitializer
    : expression
    | arrayInitializer
    ;

unannType
    : unannPrimitiveType
    | unannReferenceType
    ;

unannPrimitiveType
    : numericType
    | BOOLEAN
    ;

unannReferenceType
    : unannClassOrInterfaceType
    | unannTypeVariable
    | unannArrayType
    ;

unannClassOrInterfaceType
    : unannClassType
    | unannInterfaceType
    ;

unannClassType
    : ID (typeArguments)?
    | unannClassType DOT (annotation)* ID (typeArguments)?
    ;

unannInterfaceType
    : unannClassType
    ;

unannTypeVariable
    : ID
    ;

unannArrayType
    : unannPrimitiveType dims
    | unannClassOrInterfaceType dims
    | unannTypeVariable dims
    ;

methodDeclaration
    : methodModifier* methodHeader methodBody
    ;

methodModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | ABSTRACT
    | STATIC
    | FINAL
    | SYNCHRONIZED
    | NATIVE
    | STRICTFP
    ;

methodHeader
    : result methodDeclarator thro?
    | typeParameters annotation* result methodDeclarator thro?
    ;

result
    : unannType
    | VOID
    ;

methodDeclarator
    : ID LPAREN formalParameterList? RPAREN dims?
    ;

formalParameterList
    : formalParameters COMMA lastFormalParameter
    | lastFormalParameter
    ;

formalParameters
    : formalParameter (COMMA formalParameter)*
    | receiverParameter (COMMA formalParameter)*
    ;

formalParameter
    : variableModifier* unannType variableDeclaratorId
    ;

variableModifier
    : annotation
    | FINAL
    ;

lastFormalParameter
    : variableModifier* unannType annotation* ELLIPSES variableDeclaratorId
    | formalParameter
    ;

receiverParameter
    : annotation* unannType (ID DOT)? THIS
    ;

thro
    : THROWS exceptionTypeList
    ;

exceptionTypeList
    : exceptionType (COMMA exceptionType)*
    ;

exceptionType
    : classType
    | typeVariable
    ;

methodBody
    : block
    | SEMI
    ;

instanceInitializer
    : block
    ;

staticInitializer
    : STATIC block
    ;

constructorDeclaration
    : constructorModifier* constructorDeclarator thro? constructorBody
    ;

constructorModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    ;

constructorDeclarator
    : typeParameters? simpleTypeName LPAREN formalParameterList? RPAREN
    ;

constructorBody
    : LBRACE explicitConstructorInvocation? blockStatements? RBRACE
    ;

explicitConstructorInvocation
    : typeArguments? THIS LPAREN argumentList? RPAREN SEMI
    | typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
    | expressionName DOT typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
    | primary DOT typeArguments? SUPER LPAREN argumentList? RPAREN SEMI
    ;

enumDeclaration
    : classModifier* ENUM ID superinterfaces? enumBody
    ;

enumBody
    : LBRACE enumConstantList? COMMA? enumBodyDeclarations? RBRACE
    ;

enumConstantList
    : enumConstant (COMMA enumConstant)*
    ;

enumConstant
    : enumConstantModifier* ID (LPAREN argumentList? RPAREN)? classBody?
    ;

enumConstantModifier
    : annotation
    ;

enumBodyDeclarations
    : SEMI classBodyDeclaration*
    ;

interfaceDeclaration
    : normalInterfaceDeclaration
    | annotationTypeDeclaration
    ;

normalInterfaceDeclaration
    : interfaceModifier* INTERFACE ID typeParameters? extendsInterfaces? interfaceBody
    ;

interfaceModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | ABSTRACT
    | STATIC
    | STRICTFP
    ;

extendsInterfaces
    : EXTENDS interfaceTypeList
    ;

interfaceBody
    : LBRACE interfaceMemberDeclaration* RBRACE
    ;

interfaceMemberDeclaration
    : constantDeclaration
    | interfaceMethodDeclaration
    | classDeclaration
    | interfaceDeclaration
    | SEMI
    ;

constantDeclaration
    : constantModifier* unannType variableDeclaratorList SEMI
    ;

constantModifier
    : annotation
    | PUBLIC
    | STATIC
    | FINAL
    ;

interfaceMethodDeclaration
    : interfaceMethodModifier* methodHeader methodBody
    ;

interfaceMethodModifier
    : annotation
    | PUBLIC
    | ABSTRACT
    | DEFAULT
    | STATIC
    | STRICTFP
    ;

annotationTypeDeclaration
    : interfaceModifier* AT INTERFACE ID annotationTypeBody
    ;

annotationTypeBody
    : LBRACE annotationTypeMemberDeclaration* RBRACE
    ;

annotationTypeMemberDeclaration
    : annotationTypeElementDeclaration
    | constantDeclaration
    | classDeclaration
    | interfaceDeclaration
    | SEMI
    ;

annotationTypeElementDeclaration
    : annotationTypeElementModifier* unannType ID LPAREN RPAREN dims? defaultValue? SEMI
    ;

annotationTypeElementModifier
    : annotation
    | PUBLIC
    | ABSTRACT
    ;

defaultValue
    : DEFAULT elementValue
    ;

annotation
    : normalAnnotation
    | markerAnnotation
    | singleElementAnnotation
    ;

normalAnnotation
    : AT typeName LPAREN elementValuePairList? RPAREN
    ;

elementValuePairList
    : elementValuePair (COMMA elementValuePair)*
    ;

elementValuePair
    : ID ASSIGN elementValue
    ;

elementValue
    : conditionalExpression
    | elementValueArrayInitializer
    | annotation
    ;

elementValueArrayInitializer
    : LBRACE elementValueList? COMMA? RBRACE
    ;

elementValueList
    : elementValue (COMMA elementValue)*
    ;

markerAnnotation
    : AT typeName
    ;

singleElementAnnotation
    : AT typeName LPAREN elementValue RPAREN
    ;

arrayInitializer
    : LBRACE variableInitializerList? (COMMA)? RBRACE
    ;

variableInitializerList
    : variableInitializer (COMMA variableInitializer)*
    ;

block
    : LBRACE blockStatements? RBRACE
    ;

blockStatements
    : blockStatement+
    ;

blockStatement
    : localVariableDeclarationStatement
    | classDeclaration
    | statement
    ;

localVariableDeclarationStatement
    : localVariableDeclaration SEMI
    ;

localVariableDeclaration
    : variableModifier* unannType variableDeclaratorList
    ;

statement
    : statementWithoutTrailingSubstatement
    | labeledStatement
    | ifThenStatement
    | ifThenElseStatement
    | whileStatement
    | forStatement
    ;

statementNoShortIf
    : statementWithoutTrailingSubstatement
    | labeledStatementNoShortIf
    | ifThenElseStatementNoShortIf
    | whileStatementNoShortIf
    | forStatementNoShortIf
    ;

statementWithoutTrailingSubstatement
    : block
    | emptyStatement
    | expressionStatement
    | assertStatement
    | switchStatement
    | doStatement
    | breakStatement
    | continueStatement
    | returnStatement
    | synchronizedStatement
    | throwStatement
    | tryStatement
    ;

emptyStatement
    : SEMI
    ;

labeledStatement
    : ID COLON statement
    ;

labeledStatementNoShortIf
    : ID COLON statementNoShortIf
    ;

expressionStatement
    : statementExpression SEMI
    ;

statementExpression
    : assignment
    | preIncrementExpression
    | preDecrementExpression
    | postfixExpression INCREMENT
    | postfixExpression DECREMENT
    | methodInvocation
    | classInstanceCreationExpression
    ;

ifThenStatement
    : IF LPAREN expression RPAREN statement
    ;

ifThenElseStatement
    : IF LPAREN expression RPAREN statementNoShortIf ELSE statement
    ;

ifThenElseStatementNoShortIf
    : IF LPAREN expression RPAREN statementNoShortIf ELSE statementNoShortIf
    ;

assertStatement
    : ASSERT expression SEMI
    | ASSERT expression COLON expression SEMI
    ;

switchStatement
    : SWITCH LPAREN expression RPAREN switchBlock
    ;

switchBlock
    : LBRACE switchBlockStatementGroup* switchLabel*
    ;

switchBlockStatementGroup
    : switchLabels blockStatements
    ;

switchLabels
    : switchLabel+
    ;

switchLabel
    : CASE constantExpression COLON
    | CASE enumConstantName COLON
    | DEFAULT COLON
    ;

enumConstantName
    : ID
    ;

whileStatement
    : WHILE LPAREN expression RPAREN statement
    ;

whileStatementNoShortIf
    : WHILE LPAREN expression RPAREN statementNoShortIf
    ;

doStatement
    : DO statement WHILE LPAREN expression RPAREN SEMI
    ;

forStatement
    : basicForStatement
    | enhancedForStatement
    ;

forStatementNoShortIf
    : basicForStatementNoShortIf
    | enhancedForStatementNoShortIf
    ;

basicForStatement
    : FOR LPAREN forInit? SEMI expression? SEMI forUpdate? RPAREN statement
    ;

basicForStatementNoShortIf
    : FOR LPAREN forInit? SEMI expression? SEMI forUpdate? RPAREN statementNoShortIf
    ;

forInit
    : statementExpressionList
    | localVariableDeclaration
    ;

forUpdate
    : statementExpressionList
    ;

statementExpressionList
    : statementExpression (COMMA statementExpression)*
    ;

enhancedForStatement
    : FOR LPAREN variableModifier* unannType variableDeclaratorId COLON expression RPAREN statement
    ;

enhancedForStatementNoShortIf
    : FOR LPAREN variableModifier* unannType variableDeclaratorId COLON expression RPAREN statementNoShortIf
    ;

breakStatement
    : BREAK ID? SEMI
    ;

continueStatement
    : CONTINUE ID? SEMI
    ;

returnStatement
    : RETURN expression? SEMI
    ;

throwStatement
    : THROW expression SEMI
    ;

synchronizedStatement
    : SYNCHRONIZED LPAREN expression RPAREN block
    ;

tryStatement
    : TRY block catches
    | TRY block catches? finalClause
    | tryWithResourcesStatement
    ;

catches
    : catchClause+
    ;

catchClause
    : CATCH LPAREN catchFormalParameter RPAREN block
    ;

catchFormalParameter
    : variableModifier* catchType variableDeclaratorId
    ;

catchType
    : unannClassType (B_OR classType)*
    ;

finalClause
    : FINALLY block
    ;

tryWithResourcesStatement
    : TRY resourceSpecification block catches? finalClause?
    ;

resourceSpecification
    : LPAREN resourceList SEMI? RPAREN
    ;

resourceList
    : resource SEMI resource*
    ;

resource
    : variableModifier* unannType variableDeclaratorId ASSIGN expression
    ;

primary
    : ( primaryNoNewArray_lfno_primary | arrayCreationExpression ) primaryNoNewArray_lf_primary*
    ;

primaryNoNewArray
    : literal
    | typeName (LBRACK RBRACK)* DOT CLASS
    | VOID DOT CLASS
    | THIS
    | typeName DOT THIS
    | LPAREN expression RPAREN
    | classInstanceCreationExpression
    | fieldAccess
    | arrayAccess
    | methodInvocation
    | methodReference
    ;


primaryNoNewArray_lf_arrayAccess
	:
	;

primaryNoNewArray_lfno_arrayAccess
	: literal
    | typeName (LBRACK RBRACK)* DOT CLASS
    | VOID DOT CLASS
    | THIS
    | typeName DOT THIS
    | LPAREN expression RPAREN
	| classInstanceCreationExpression
	| fieldAccess
	| methodInvocation
	| methodReference
	;

primaryNoNewArray_lf_primary
	: classInstanceCreationExpression_lf_primary
	| fieldAccess_lf_primary
	| arrayAccess_lf_primary
	| methodInvocation_lf_primary
	| methodReference_lf_primary
	;

primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary
	:
	;

primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary
	: classInstanceCreationExpression_lf_primary
	| fieldAccess_lf_primary
	| methodInvocation_lf_primary
	| methodReference_lf_primary
	;

primaryNoNewArray_lfno_primary
	: literal
    | typeName (LBRACK RBRACK)* DOT CLASS
	| unannPrimitiveType (LBRACK RBRACK)* DOT CLASS
    | VOID DOT CLASS
    | THIS
    | typeName DOT THIS
    | LPAREN expression RPAREN
	| classInstanceCreationExpression_lfno_primary
	| fieldAccess_lfno_primary
	| arrayAccess_lfno_primary
	| methodInvocation_lfno_primary
	| methodReference_lfno_primary
	;

primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary
	:
	;

primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary
	: literal
    | typeName (LBRACK RBRACK)* DOT CLASS
	| unannPrimitiveType (LBRACK RBRACK)* DOT CLASS
    | VOID DOT CLASS
    | THIS
    | typeName DOT THIS
    | LPAREN expression RPAREN
	| classInstanceCreationExpression_lfno_primary
	| fieldAccess_lfno_primary
	| methodInvocation_lfno_primary
	| methodReference_lfno_primary
	;

classInstanceCreationExpression
    : NEW typeArguments? annotation* ID typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
    | expressionName DOT NEW typeArguments? annotation* ID typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
    | primary DOT NEW typeArguments? annotation* ID typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
    ;

classInstanceCreationExpression_lf_primary
	:	DOT NEW typeArguments? annotation* ID typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
	;

classInstanceCreationExpression_lfno_primary
	:	NEW typeArguments? annotation* ID (DOT annotation* ID)* typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
	|	expressionName DOT NEW typeArguments? annotation* ID typeArgumentsOrDiamond? LPAREN argumentList? RPAREN classBody?
	;

typeArgumentsOrDiamond
    : typeArguments
    | DIAMOND
    ;

fieldAccess
    : primary DOT ID
    | SUPER DOT ID
    | typeName DOT SUPER DOT ID
    ;

fieldAccess_lf_primary
	: DOT ID
	;

fieldAccess_lfno_primary
	: SUPER DOT ID
    | typeName DOT SUPER DOT ID
	;

arrayAccess
    :	( expressionName LBRACK expression RBRACK
    	| primaryNoNewArray_lfno_arrayAccess LBRACK expression RBRACK
    	)
    	( primaryNoNewArray_lf_arrayAccess LBRACK expression RBRACK )*
    ;

arrayAccess_lf_primary
	:	( primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary LBRACK expression RBRACK	)
		( primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary LBRACK expression RBRACK )*
	;

arrayAccess_lfno_primary
	:	( expressionName LBRACK expression RBRACK
		| primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary LBRACK expression RBRACK
		)
		( primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary LBRACK expression RBRACK )*
	;

methodInvocation
    : methodName LPAREN argumentList? RPAREN
    | typeName DOT typeArguments? ID LPAREN argumentList? RPAREN
    | expressionName DOT (typeArguments)? ID LPAREN argumentList? RPAREN
    | primary DOT typeArguments? ID LPAREN argumentList? RPAREN
    | SUPER DOT typeArguments? ID LPAREN argumentList? RPAREN
    | typeName DOT SUPER DOT typeArguments? ID LPAREN argumentList? RPAREN
    ;

methodInvocation_lf_primary
	: DOT typeArguments? ID LPAREN argumentList? RPAREN
	;

methodInvocation_lfno_primary
	: methodName LPAREN argumentList? RPAREN
	| typeName DOT typeArguments? ID LPAREN argumentList? RPAREN
	| expressionName DOT typeArguments? ID LPAREN argumentList? RPAREN
	| SUPER DOT typeArguments? ID LPAREN argumentList? RPAREN
	| typeName DOT SUPER DOT typeArguments? ID LPAREN argumentList? RPAREN
	;

argumentList
    : expression (COMMA expression)*
    ;

methodReference
    : expressionName DCOLON typeArguments? ID
    | referenceType DCOLON typeArguments? ID
    | primary DCOLON typeArguments? ID
    | SUPER DCOLON typeArguments? ID
    | typeName DOT SUPER DCOLON typeArguments? ID
    | classType DCOLON typeArguments? NEW
    | arrayType DCOLON NEW
    ;

methodReference_lf_primary
	: DCOLON typeArguments? ID
	;

methodReference_lfno_primary
	: expressionName DCOLON typeArguments? ID
	| referenceType DCOLON typeArguments? ID
	| SUPER DCOLON typeArguments? ID
	| typeName DOT SUPER DCOLON typeArguments? ID
	| classType DCOLON typeArguments? NEW
	| arrayType DCOLON NEW
	;

arrayCreationExpression
    : NEW primitiveType dimExprs dims?
    | NEW classOrInterfaceType dimExprs dims?
    | NEW primitiveType dims arrayInitializer
    | NEW classOrInterfaceType dims arrayInitializer
    ;

dimExprs
    : dimExpr dimExpr*
    ;

dimExpr
    : annotation* LBRACK expression RBRACK
    ;

constantExpression
    : expression
    ;

expression
    : lambdaExpression
    | assignmentExpression
    ;

lambdaExpression
    : lambdaParameters RARROW lambdaBody
    ;

lambdaParameters
    : ID
    | LPAREN formalParameterList? RPAREN
    | LPAREN inferredFormalParameterList RPAREN
    ;

inferredFormalParameterList
    : ID (COMMA ID)*
    ;

lambdaBody
    : expression
    | block
    ;

assignmentExpression
    : conditionalExpression
    | assignment
    ;

assignment
    : leftHandSide assignmentOperator expression
    ;

leftHandSide
    : expressionName
    | fieldAccess
    | arrayAccess
    ;

assignmentOperator
    : ASSIGN
    | MULT_ASSIGN
    | DIV_ASSIGN
    | MOD_ASSIGN
    | PLUS_ASSIGN
    | MINUS_ASSIGN
    | LEFT_ASSIGN
    | RIGHT_ASSIGN
    | UR_ASSIGN
    | AND_ASSIGN
    | XOR_ASSIGN
    | OR_ASSIGN
    ;

conditionalExpression
    : conditionalOrExpression
    | conditionalOrExpression QMARK expression COLON conditionalExpression
    ;

conditionalOrExpression
    : conditionalAndExpression
    | conditionalOrExpression L_OR conditionalAndExpression
    ;

conditionalAndExpression
    : inclusiveOrExpression
    | conditionalAndExpression L_AND inclusiveOrExpression
    ;

inclusiveOrExpression
    : exclusiveOrExpression
    | inclusiveOrExpression B_OR exclusiveOrExpression
    ;

exclusiveOrExpression
    : andExpression
    | exclusiveOrExpression XOR andExpression
    ;

andExpression
    : equalityExpression
    | andExpression B_AND equalityExpression
    ;

equalityExpression
    : relationalExpression
    | equalityExpression EQ relationalExpression
    | equalityExpression NEQ relationalExpression
    ;

relationalExpression
    : shiftExpression
    | relationalExpression LT shiftExpression
    | relationalExpression GT shiftExpression
    | relationalExpression LE shiftExpression
    | relationalExpression GE shiftExpression
    | relationalExpression INSTANCEOF referenceType
    ;

shiftExpression
    : additiveExpression
    | shiftExpression L_SHIFT additiveExpression
    | shiftExpression GT GT additiveExpression
    | shiftExpression GT GT GT additiveExpression
    ;

additiveExpression
    : multiplicativeExpression
    | additiveExpression PLUS multiplicativeExpression
    | additiveExpression MINUS multiplicativeExpression
    ;

multiplicativeExpression
    : unaryExpression
    | multiplicativeExpression MULT unaryExpression
    | multiplicativeExpression DIV unaryExpression
    | multiplicativeExpression MOD unaryExpression
    ;

unaryExpression
    : preIncrementExpression
    | preDecrementExpression
    | PLUS unaryExpression
    | MINUS unaryExpression
    | unaryExpressionNotPlusMinus
    ;

preIncrementExpression
    : INCREMENT unaryExpression
    ;

preDecrementExpression
    : DECREMENT unaryExpression
    ;

unaryExpressionNotPlusMinus
    : postfixExpression
    | TILDE unaryExpression
    | BANG unaryExpression
    | castExpression
    ;

postfixExpression
    : primary
    | expressionName
    | postfixExpression INCREMENT
    | postfixExpression DECREMENT
    ;

castExpression
    : LPAREN primitiveType RPAREN unaryExpression
    | LPAREN referenceType (additionalBound)* RPAREN unaryExpressionNotPlusMinus
    | LPAREN referenceType (additionalBound)* RPAREN lambdaExpression
    ;
