// Define a grammar called Hello
grammar TSIGrammatic;
// while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);

code  : whileStatement ES ;      // match keyword hello followed by an identifier

repetetiveStatement : whileStatement;
whileStatement: WHILE OB expression CB DO statement;

expression: simpleExpression (expressionOperator simpleExpression)?;
expressionOperator: EQ | LT;

simpleExpression: term (simpleOperator term)*;
term: signedFactor (termOperator signedFactor)*;
termOperator: STAR | MOD | AND | SLASH | DIV | SLASH | SHL | SHR;
simpleOperator : PLUS | MINUS | OR | XOR;

signedFactor: sign? factor;
factor: variable | constant; // | memberAccess | bracketedExpression | typeCheck | negation;
sign : PLUS | MINUS;
variable : IDENT (ARROW dimensionQualifiers)?;
constant: unsignedNumber | signedNumber | IDENT;

dimensionQualifiers: '[' dimensionQualifier ']';
dimensionQualifier:  expression;

statement: simpleStatement | structuredStatement;
simpleStatement : 'true';//TODO
structuredStatement : 'true';//TODO

signedNumber : sign unsignedNumber;
unsignedNumber: unsignedInteger;
unsignedInteger: LITERAL_INTEGER;

WHILE : 'while';
DO : 'do';

PLUS : '+';
MINUS : '-';

ES : ';';

EQ : '=';
LT : '<';

OB : '(';
CB : ')';

ARROW : '^';

WS : [ \t\r\n]+ -> skip ;
LITERAL_INTEGER: [0-9]+;
IDENT: [a-zA-Z_] [a-zA-Z0-9_]*;