lexer grammar First ;

IntegerLiteral
	:	('0' | [1-9] ( Digits? | '_'+ Digits )) [lL]?				// decimal
	|	'0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?		// hex
	|	'0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?						// octal
	|	'0' [bB] [01] ([01_]* [01])? [lL]?							// binary
	;

