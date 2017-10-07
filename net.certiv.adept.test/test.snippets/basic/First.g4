grammar First ;

rule
	: A
		| B 
	;

A
	: 'a' ;

B
: A 'b'
;



WS
	: [ \t\r\n\f]*           -> channel(HIDDEN) ;

fragment DocComment		: '/**' .*? ('*/' | EOF)	;
fragment BlockComment	: '/*'  .*? ('*/' | EOF)	;

fragment LineComment	: '//' ~[\r\n]* 							;
fragment LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;
