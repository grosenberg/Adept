grammar First ;

rule
	: A
	| B ;

A
	: 'a' ;

B
: A 'b'
;

WS
	: [ \t\r\n\f]* -> channel(HIDDEN) ;

