lexer grammar AlignComments ;


NUMBER
    :   '-'? INT '.' [0-9]+ EXP?// decimal scientific   notation
    |	'-'? INT EXP              //scientific   notation
    |	'-'? INT// integer notation
    ;


fragment
INT
    :   '0' | [1-9] [0-9]*
    ;

// no leading zeros

fragment
EXP
    :   [Ee] [+\-]? INT
    ;
