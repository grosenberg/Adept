grammar Test01 ;

query   	: term+ EOF ;
term        : alphanum+ STAR alphanum* ;
alphanum    : CHARACTER 
			| NUM 
			;

WHITESPACE  : [ \t\r\n]+ -> skip;

CHARACTER   : 'a'..'z' | 'A'..'Z' ;
NUM         : '0'..'9' ;
STAR		: '*' ;
