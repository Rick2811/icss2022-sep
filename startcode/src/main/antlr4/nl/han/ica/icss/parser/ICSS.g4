grammar ICSS;

// ======================
// PARSER RULES
// ======================

stylesheet
    : (statement | expr)* EOF;

statement
    : varAssign | ruleset;

varAssign
    : CAPITAL_IDENT ASSIGNMENT_OPERATOR value SEMICOLON;

ruleset
    : selector OPEN_BRACE blockItem* CLOSE_BRACE;

selector
    : ID_IDENT | CLASS_IDENT | LOWER_IDENT ;

blockItem
    : declaration | ifClause | varAssign
    ;

declaration
    : LOWER_IDENT COLON value SEMICOLON ;

ifClause
  : IF (LPAREN condition RPAREN | BOX_BRACKET_OPEN condition BOX_BRACKET_CLOSE)
    OPEN_BRACE blockItem* CLOSE_BRACE
    (ELSE OPEN_BRACE blockItem* CLOSE_BRACE)?
  ;


condition
    : boolValue | CAPITAL_IDENT;

boolValue
    : TRUE | FALSE;

value
    : COLOR #ColorLiteral | boolValue #BoolLiteral | expr #NumericOrVarExpr
    ;

expr
    : MIN expr #UnaryMinus| expr MUL expr #Mul | expr (PLUS | MIN) expr #AddSub | atom #AtomExpr;

atom
    : PIXELSIZE #PixelLiteral | PERCENTAGE #PercentLiteral | SCALAR #ScalarLiteral | CAPITAL_IDENT #VariableRef;

// ======================
// LEXER RULES
// ======================

IF  : 'if';
ELSE: 'else';

TRUE : 'TRUE';
FALSE: 'FALSE';

PIXELSIZE : [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR    : [0-9]+;

// ✅ FIXED COLOR REGEX
COLOR : '#' [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F]
          [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F];

ID_IDENT    : '#' [a-z0-9\-]+;
CLASS_IDENT : '.' [a-z0-9\-]+;
LOWER_IDENT : [a-z] [a-z0-9\-]*;
CAPITAL_IDENT : [A-Z] [A-Za-z0-9_]*;

// Haakjes
LPAREN: '(';
RPAREN: ')';


OPEN_BRACE : '{';
CLOSE_BRACE: '}';
BOX_BRACKET_OPEN : '[';
BOX_BRACKET_CLOSE: ']';
SEMICOLON  : ';';
COLON      : ':';
PLUS       : '+';
MIN        : '-';
MUL        : '*';
ASSIGNMENT_OPERATOR : ':=';

LINE_COMMENT : '//' ~[\r\n]* -> skip;
COMMENT : '/*' .*? '*/' -> skip;
WS : [ \t\r\n]+ -> skip;
