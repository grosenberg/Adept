grammar AlignBegEnd ;

comment		: DocComment
		| BlockComment
			|   LineComment
|		LineCommentExt
	;

DocComment		: '/**' .*? ('*/' | EOF)	;
BlockComment		: '/*'  .*? ('*/' | EOF)	;
LineComment	: '//' ~[\r\n]* 							;
LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;
