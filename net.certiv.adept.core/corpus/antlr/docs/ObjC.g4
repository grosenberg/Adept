// Converted to ANTLR 4 by Terence Parr; added @property and a few others.
// Seems to handle stuff like this except for blocks:
// https://google-api-objectivec-client.googlecode.com/svn/trunk/Examples/ShoppingSample/ShoppingSampleWindowController.m

/**
* ObjectiveC version 2
* based on an LL ansic grammars and
* and ObjectiveC grammar found in Learning Object C
*
* It's a Work in progress, most of the .h file can be parsed
* June 2008 Cedric Cuche
* Updated June 2014, Carlos Mejia.  Fix try-catch, add support for @( @{ @[ and blocks
**/

grammar ObjC;

@header {
package co.codebeat.analyser.antlr;
}

translationUnit : externalDeclaration+ EOF ;

externalDeclaration
	: COMMENT
	| LINE_COMMENT
	| preprocessorDeclaration
	| topLevelFunctionDefinition
	| declaration
	| classInterface
	| classImplementation
	| categoryInterface
	| categoryImplementation
	| protocolDeclaration
	| protocolDeclarationList
	| classDeclarationList
	;

topLevelFunctionDefinition : functionDefinition ;

preprocessorDeclaration
	: IMPORT
	| INCLUDE
	;

classInterface :
	'@interface'
	className (':' superclassName)?
	protocolReferenceList?
	instanceVariables?
	interfaceDeclarationList?
	'@end'
	;

categoryInterface :
	'@interface'
	className '(' categoryName? ')'
	protocolReferenceList?
	instanceVariables?
	interfaceDeclarationList?
	'@end'
	;

classImplementation :
	'@implementation'
	(
	className (':' superclassName )?
	instanceVariables?
	( implementationDefinitionList )?
	)
	'@end'
	;

categoryImplementation :
	'@implementation'(
	className '(' categoryName ')'
	( implementationDefinitionList )?
	)'@end'
	;

protocolDeclaration :
	'@protocol'(
	protocolName ( protocolReferenceList )?
	'@required'? interfaceDeclarationList? '@optional'? interfaceDeclarationList?
	)'@end'
	;

protocolDeclarationList : '@protocol' protocolList ';' ;

classDeclarationList : '@class' classList ';' ;

classList : className (',' className)* ;

protocolReferenceList : '<' protocolList '>' ;

protocolList : protocolName (',' protocolName)* ;

propertyDeclaration : '@property' propertyAttributesDeclaration? structDeclaration ;

propertyAttributesDeclaration : '(' propertyAttributesList ')' ;

propertyAttributesList : propertyAttribute (',' propertyAttribute)* ;

propertyAttribute
	: 'nonatomic' | 'assign' | 'weak' | 'strong' | 'retain' | 'readonly' | 'readwrite'
	| 'getter' '=' IDENTIFIER //  getter
	| 'setter' '=' IDENTIFIER ':' // setter
	| IDENTIFIER
	;

className : IDENTIFIER ;

superclassName : IDENTIFIER ;

categoryName : IDENTIFIER ;

protocolName : protocolReferenceList | IDENTIFIER ;

instanceVariables
	: '{' structDeclaration* '}'
	| '{' visibilitySpecification structDeclaration+ '}'
	| '{' structDeclaration+ instanceVariables '}'
	| '{' visibilitySpecification structDeclaration+ instanceVariables '}'
	;

visibilitySpecification
	: '@private'
	| '@protected'
	| '@package'
	| '@public'
	;

interfaceDeclarationList : interfaceDeclarationListItem+ ;

interfaceDeclarationListItem
	: declaration
	| classMethodDeclaration
	| instanceMethodDeclaration
	| propertyDeclaration
	;

classMethodDeclaration : '+' methodDeclaration ;

instanceMethodDeclaration : '-' methodDeclaration ;

methodDeclaration : ( methodType )? methodSelector ';' ;

implementationDefinitionList : implementationDefinitionListItem+ ;

implementationDefinitionListItem
	: functionDefinition
	| declaration
	| classMethodDefinition
	| instanceMethodDefinition
	| propertyImplementation
	;

classMethodDefinition : '+' methodDefinition ;

instanceMethodDefinition : '-' methodDefinition ;

methodDefinition : methodType? methodSelector initDeclaratorList? ';'? compoundStatement ;

methodSelector
	: selector
	| keywordDeclarator+ (parameterList)?
	;

keywordDeclarator : selector? ':' methodType* IDENTIFIER ;

selector : IDENTIFIER;

methodType : '(' typeName ')' ;

propertyImplementation
	: '@synthesize' propertySynthesizeList ';'
	| '@dynamic' propertySynthesizeList ';'
	;

propertySynthesizeList : propertySynthesizeItem (',' propertySynthesizeItem)* ;

propertySynthesizeItem : IDENTIFIER | IDENTIFIER '=' IDENTIFIER ;

blockType : typeSpecifier '(''^' typeSpecifier? ')' blockParameters? ;

genericsSpecifier : '<' typeSpecifier? (',' typeSpecifier)* '>' ;

typeSpecifier
	: 'void' | 'char' | 'short' | 'int' | 'long' | 'float' | 'double' | 'signed'
	| 'unsigned' | 'instancetype'
	| 'id' protocolReferenceList?
	| className (protocolReferenceList | genericsSpecifier)? '*'?
	| structOrUnionSpecifier
	| enumSpecifier
	| IDENTIFIER
	| IDENTIFIER pointer
	;

typeQualifier : 'const' | 'volatile' | protocolQualifier ;

protocolQualifier : 'in' | 'out' | 'inout' | 'bycopy' | 'byref' | 'oneway' ;

primaryExpression
	: IDENTIFIER
	| vaArgExpression
	| constant
	| stringLiteral
	| '(' expression ')'
	| 'self'
	| 'super'
	| messageExpression
	| selectorExpression
	| protocolExpression
	| encodeExpression
	| dictionaryExpression
	| arrayExpression
	| boxExpression
	| blockExpression
	| structExpression
	;

structExpression : '{' (structPair ','?)+ '}' ;
structPair : '.' identifier '=' postfixExpression ;

vaArgExpression : 'va_arg' '(' postfixExpression ',' typeSpecifier ')' ;

dictionaryExpression: '@''{' dictionaryPair? (',' dictionaryPair)* ','? '}' ;
dictionaryPair :  postfixExpression':'postfixExpression ;

arrayExpression: '@''[' postfixExpression? (',' postfixExpression)* ','? ']' ;

boxExpression : '@''('expression')' | '@'constant ;
blockParameters : '(' (typeVariableDeclarator | typeName | 'void')? (',' (typeVariableDeclarator | typeName))* ')' ;

blockExpression :'^' typeSpecifier? blockParameters? compoundStatement ;

messageExpression : '[' receiver messageSelector ']' ;

receiver
	: expression
	| className
	| 'super'
	;

messageSelector
	: selector
	| keywordArgument+
	;

keywordArgument : selector? ':' expression (',' expression)*;

selectorExpression : '@selector' '(' selectorName ')' ;

selectorName
	: selector
	| (selector? ':')+
	;

protocolExpression : '@protocol' '(' protocolName ')' ;

encodeExpression : '@encode' '(' typeName ')' ;

typeVariableDeclarator : declarationSpecifier+ declarator ;

tryStatement : '@try' compoundStatement ;

catchStatement : '@catch' '(' typeVariableDeclarator ')' compoundStatement ;

finallyStatement : '@finally' compoundStatement ;

throwStatement
	: '@throw' '(' IDENTIFIER ')'
	| '@throw' expression
	;

tryBlock : tryStatement catchStatement* finallyStatement? ;

synchronizedStatement : '@synchronized' '(' primaryExpression ')' compoundStatement ;

autoreleaseStatement: '@autoreleasepool'  compoundStatement ;

functionDefinition : declarationSpecifier* functionSignature compoundStatement ;

functionSignature : declarator ;

declaration : declarationSpecifier* initDeclaratorList? ';' ;

declarationSpecifier
	: arcBehaviourSpecifier
	| storageClassSpecifier
	| typeSpecifier
	| typeQualifier
	;

arcBehaviourSpecifier : '__unsafe_unretained' | '__weak' ;
storageClassSpecifier : 'auto' | 'register' | 'static' | 'extern' | 'typedef' ;

initDeclaratorList :  initDeclarator (',' initDeclarator)* ;
initDeclarator : declarator ('=' initializer)? ;

structOrUnionSpecifier: ('struct' | 'union')
	( IDENTIFIER | IDENTIFIER? '{' structDeclaration+ '}') ;

structDeclaration : specifierQualifierList structDeclaratorList ';' ;

specifierQualifierList : (arcBehaviourSpecifier | typeSpecifier | typeQualifier)+ ;

structDeclaratorList : structDeclarator (',' structDeclarator)* ;

structDeclarator : declarator | declarator? ':' constant ;

enumSpecifier
	: 'enum' (':' typeName)? ( identifier ('{' enumeratorList '}')? | '{' enumeratorList '}')
	| 'NS_OPTIONS' '(' typeName ',' identifier ')' '{' enumeratorList '}'
	| 'NS_ENUM' '(' typeName ',' identifier ')' '{' enumeratorList '}'
	;


enumeratorList : enumerator (',' enumerator)* ','? ;
enumerator : identifier ('=' constantExpression)? ;

pointer
	:   '*' declarationSpecifier*
	|   '*' declarationSpecifier* pointer
	;



declarator : pointer ? directDeclarator ;

directDeclarator
	: identifier declaratorSuffix*
	| '(' declarator ')' declaratorSuffix*
	| '(''^' identifier? ')' blockParameters
	;


declaratorSuffix
	: '[' constantExpression? ']'
	| '(' parameterList? ')'
	;

parameterList : parameterDeclarationList ( ',' '...' )? ;

parameterDeclaration
	: declarationSpecifier+ (declarator? | abstractDeclarator) ;

initializer
	: expression
	| '{' initializer (',' initializer)* ','? '}'
	;

typeName
	: specifierQualifierList abstractDeclarator?
	| blockType
	;

abstractDeclarator
	: pointer abstractDeclarator
	| '(' abstractDeclarator ')' abstractDeclaratorSuffix+
	| ('[' constantExpression? ']')+
	;

abstractDeclaratorSuffix
	: '[' constantExpression? ']'
	| '('  parameterDeclarationList? ')'
	;

parameterDeclarationList : parameterDeclaration ( ',' parameterDeclaration )* ;

statementList : statement+ ;

statement
	: labeledStatement ';'?
	| compoundStatement ';'?
	| selectionStatement ';'?
	| iterationStatement ';'?
	| jumpStatement ';'?
	| synchronizedStatement ';'?
	| autoreleaseStatement ';'?
	| throwStatement ';'?
	| tryBlock ';'?
	| expression ';'?
	;

labeledStatement
	: identifier ':' statement
	| 'case' constantExpression ':' statement
	| 'default' ':' statement ;

compoundStatement : '{' (declaration|statementList)* '}' ;

selectionStatement
	: 'if' '(' expression ')' statement ('else' statement)?
	| 'switch' '(' expression ')' statement ;

forInStatement : 'for' '(' typeVariableDeclarator 'in' expression? ')' statement;
forStatement: 'for' '(' ((declarationSpecifier+ initDeclaratorList) | expression)? ';' expression? ';' expression? ')' statement;
whileStatement: 'while' '(' expression ')' statement;
doStatement: 'do' statement 'while' '(' expression ')' ';';

iterationStatement
	: whileStatement
	| doStatement
	| forStatement
	| forInStatement ;

jumpStatement
	: 'goto' identifier ';'
	| 'continue' ';'
	| 'break' ';'
	| 'return' expression? ';'
	;

expression
	: primaryExpression ';'?
	| postfixExpression ';'?
	| unaryExpression binaryExpression* ';'?
	| binaryExpression ';'?
	;

binaryExpression
	: binaryOperator unaryExpression
	| assignmentExpression
	| conditionalExpression
	| castExpression
	;

assignmentExpression : assignmentOperator unaryExpression ;

conditionalExpression : conditionalOperator unaryExpression ;

assignmentOperator :
	'=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|=';

conditionalOperator : '?' expression ':' ;

binaryOperator : '>' | '<' | '!' | '~' | '?' | ':' | '==' | '<=' | '>='
							 | '!=' | '&&' | '||' | '+' | '-' | '*' | '/' | '&' | '|' | '^'
							 | '%' | '>>' | '<<' ;

constantExpression : identifier ;

castExpression : '(' typeName ')' castExpression | unaryExpression ;

unaryExpression
	: postfixExpression
	| '++' unaryExpression
	| '--' unaryExpression
	| unaryOperator castExpression
	| 'sizeof' ('(' typeName ')' | unaryExpression) ;

unaryOperator : '&' | '*' | '-' | '~' | '!' ;

postfixExpression : primaryExpression
	('[' expression ']'
	| '(' argumentExpressionList? ')'
	| dataMemberAccess
	| '++'
	| '--'
	| '*'
	)* ;

dataMemberAccess : ('.' | '->') identifier ;

argumentExpressionList : expression (',' expression)* ; // Support variadic functions.

identifier : IDENTIFIER ;

constant
	: DECIMAL_LITERAL
	| HEX_LITERAL
	| OCTAL_LITERAL
	| CHARACTER_LITERAL
	| FLOATING_POINT_LITERAL
	;

stringLiteral : ('L' | '@') stringPiece+ ;
stringPiece : STRING ;

// LEXER

// §3.9 Keywords


AUTORELEASEPOOL : '@autoreleasepool';
CATCH           : '@catch';
CLASS           : '@class';
DYNAMIC         : '@dynamic';
ENCODE          : '@encode';
END             : '@end';
FINALLY         : '@finally';
IMPLEMENTATION  : '@implementation';
INTERFACE       : '@interface';
PACKAGE         : '@package';
PROTOCOL        : '@protocol';
OPTIONAL        : '@optional';
PRIVATE         : '@private';
PROPERTY        : '@property';
PROTECTED       : '@protected';
PUBLIC          : '@public';
SELECTOR        : '@selector';
SYNCHRONIZED    : '@synchronized';
SYNTHESIZE      : '@synthesize';
THROW           : '@throw';
TRY             : '@try';

SUPER           : 'super';
SELF            : 'self';


ABSTRACT      : 'abstract';
AUTO          : 'auto';
BOOLEAN       : 'boolean';
BREAK         : 'break';
BYCOPY        : 'bycopy';
BYREF         : 'byref';
CASE          : 'case';
CHAR          : 'char';
CONST         : 'const';
CONTINUE      : 'continue';
DEFAULT       : 'default';
DO            : 'do';
DOUBLE        : 'double';
ELSE          : 'else';
ENUM          : 'enum';
EXTERN        : 'extern';
FLOAT         : 'float';
FOR           : 'for';
ID            : 'id';
IF            : 'if';
IN            : 'in';
INOUT         : 'inout';
INSTANCETYPE  : 'instancetype';
GOTO          : 'goto';
INT           : 'int';
LONG          : 'long';
ONEWAY        : 'oneway';
OUT           : 'out';
REGISTER      : 'register';
RETURN        : 'return';
SHORT         : 'short';
SIGNED        : 'signed';
SIZEOF        : 'sizeof';
STATIC        : 'static';
STRUCT        : 'struct';
SWITCH        : 'switch';
TYPEDEF       : 'typedef';
UNION         : 'union';
UNSIGNED      : 'unsigned';
VOID          : 'void';
VOLATILE      : 'volatile';
WHILE         : 'while';

NS_OPTIONS          : 'NS_OPTIONS';
NS_ENUM             : 'NS_ENUM';
WWEAK               : '__weak';
WUNSAFE_UNRETAINED  : '__unsafe_unretained';

// §3.11 Separators

LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACK          : '[';
RBRACK          : ']';
SEMI            : ';';
COMMA           : ',';
DOT             : '.';
STRUCTACCESS    : '->';
AT              : '@';

// Operators

ASSIGN          : '=';
GT              : '>';
LT              : '<';
BANG            : '!';
TILDE           : '~';
QUESTION        : '?';
COLON           : ':';
EQUAL           : '==';
LE              : '<=';
GE              : '>=';
NOTEQUAL        : '!=';
AND             : '&&';
OR              : '||';
INC             : '++';
DEC             : '--';
ADD             : '+';
SUB             : '-';
MUL             : '*';
DIV             : '/';
BITAND          : '&';
BITOR           : '|';
CARET           : '^';
MOD             : '%';
SHIFT_R         : '>>';
SHIFT_L         : '<<';

// Assignment

ADD_ASSIGN      : '+=';
SUB_ASSIGN      : '-=';
MUL_ASSIGN      : '*=';
DIV_ASSIGN      : '/=';
AND_ASSIGN      : '&=';
OR_ASSIGN       : '|=';
XOR_ASSIGN      : '^=';
MOD_ASSIGN      : '%=';
LSHIFT_ASSIGN   : '<<=';
RSHIFT_ASSIGN   : '>>=';
ELIPSIS         : '...';

// Property attributes
ASSIGNPA        : 'assign';
GETTER          : 'getter';
NONATOMIC       : 'nonatomic';
SETTER          : 'setter';
STRONG          : 'strong';
RETAIN          : 'retain';
READONLY        : 'readonly';
READWRITE       : 'readwrite';
WEAK            : 'weak';

IDENTIFIER
	: LETTER (LETTER|'0'..'9')*
	;

fragment
LETTER
	: '$'
	| 'A'..'Z'
	| 'a'..'z'
	| '_'
	;

CHARACTER_LITERAL
		:   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
		;

STRING : '"' ( EscapeSequence | ~('\\'|'"') )* '"' ;

HEX_LITERAL : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DECIMAL_LITERAL : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OCTAL_LITERAL : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix
	: ('u'|'U'|'l'|'L')
	;

FLOATING_POINT_LITERAL
	: ('0'..'9')+ ('.' ('0'..'9')*)? Exponent? FloatTypeSuffix?
	;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

fragment
EscapeSequence
		: '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
		| OctalEscape
		| UnicodeEscape
		;

fragment
OctalEscape
		:   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
		|   '\\' ('0'..'7') ('0'..'7')
		|   '\\' ('0'..'7')
		;

fragment
UnicodeEscape
		:   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
		;

IMPORT : '#import' [ \t]* (STRING|ANGLE_STRING) WS -> skip ;
INCLUDE: '#include'[ \t]* (STRING|ANGLE_STRING) WS -> skip ;
PRAGMA : '#pragma' ~[\r\n]* -> skip ;

fragment
ANGLE_STRING
		:   '<' .*? '>'
		;

WS  :  [ \r\n\t\u000C] -> skip ;

COMMENT
		:   '/*' .*? '*/'  -> skip
		;

LINE_COMMENT
		: '//' ~[\r\n]*  -> skip
		;

// ignore preprocessor defines for now

HDEFINE : '#define' ~[\r\n]* -> skip;
HIF : '#if' ~[\r\n]* -> skip;
HELIF : '#elif' ~[\r\n]* -> skip;
HELSE : '#else' ~[\r\n]* -> skip;
HUNDEF : '#undef' ~[\r\n]* -> skip;
HIFNDEF : '#ifndef' ~[\r\n]* -> skip;
HENDIF : '#endif' ~[\r\n]* -> skip;
