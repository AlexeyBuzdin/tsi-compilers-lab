grammar TSIGrammatic;
// while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);

expressionList: expression (COMMA expression)*;
expression: simpleExpression (expressionOperator simpleExpression)?;
expressionOperator: EQ | LT;

simpleExpression: term (simpleOperator term)*;
term: signedFactor (termOperator signedFactor)*;
termOperator: AND;
simpleOperator : PLUS | MINUS;

signedFactor: sign? factor;
factor: variable | constant | bracketedExpression | memberAccess;
sign : PLUS | MINUS;
variable : identifier (ARROW dimensionQualifiers)?;
constant: unsignedNumber | signedNumber | identifier;

bracketedExpression: '(' expression ')';

dimensionQualifiers: '[' dimensionQualifier ']';
dimensionQualifier:  expression;

// Statement
statements: (statement ES)+;
statement: simpleStatement | structuredStatement;
simpleStatement : memberAccessStatement | emptyStatement;
structuredStatement : repetetiveStatement;
repetetiveStatement : whileStatement;
whileStatement: WHILE '(' expression ')' DO statement;
memberAccessStatement : memberAccess;
emptyStatement: ;

// Methods
memberAccess: (bracketedExpression | functionCall) dimensionQualifiers?;
functionCall: identifier argumentList?;
argumentList: '(' expressionList? ')';

signedNumber : sign unsignedNumber;
unsignedNumber: unsignedInteger;
unsignedInteger: LITERAL_INTEGER;

identifier: IDENT;

WHILE : 'while';
DO : 'do';

AND : 'and';

PLUS : '+';
MINUS : '-';

ES : ';';
COMMA : ',';

EQ : '=';
LT : '<';

ARROW : '^';

WS : [ \t\r\n]+ -> skip ;
LITERAL_INTEGER: [0-9]+;
IDENT: [a-zA-Z_] [a-zA-Z0-9_]*;