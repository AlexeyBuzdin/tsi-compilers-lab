// Define a grammar called Hello
grammar TSIGrammatic;
// while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);

code  : whileStatement ES ;      // match keyword hello followed by an identifier

repetetiveStatement : whileStatement;
whileStatement: WHILE OB expression CB DO statement;

expression: simpleExpression (expressionOperator simpleExpression)?;
expressionOperator: EQ | LT;

simpleExpression: § (simpleOperator term)*;
term: signedFactor (termOperator signedFactor)*;
termOperator: STAR | MOD | AND | SLASH | DIV | SLASH | SHL | SHR;
simpleOperator : 'true'; //TODO

signedFactor: sign? factor;
factor: variable ; //| constant | memberAccess | bracketedExpression | typeCheck | negation;
sign : PLUS | MINUS;
variable : IDENT; // (dimensionQualifiers)?;

dimensionQualifiers: '[' dimensionQualifier ']';
dimensionQualifier:  expression;

statement: simpleStatement | structuredStatement;
simpleStatement : 'true';//TODO
structuredStatement : 'true';//TODO

IDENT: [a-zA-Z_] [a-zA-Z0-9_]*;
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

WHILE : 'while';
DO : 'do';

PLUS : '+';
MINUS : '-';

ES : ';';

EQ : '=';
LT : '<';

OB : '(';
CB : ')';