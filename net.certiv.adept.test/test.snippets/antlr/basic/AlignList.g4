lexer grammar First ;

DocComment		: '/**' .*? ('*/' | EOF)	;
BlockComment		: '/*'  .*? ('*/' | EOF)	;
LineComment	: '//' ~[\r\n]* 							;
LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;
