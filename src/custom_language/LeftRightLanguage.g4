
/**
 * the `LeftRightLanguage`:
 * "write from left to right"
 */
grammar LeftRightLanguage;

program: (statement | method)* EOF;

/*
    methods / variables
*/

method: type ID '{' arguments '}' '[' statement* ']';   // void a {int a} []
returnStatement: '<-' (END | expression);               // <- 4

methodCall: ID '[' parameters ']';                      // a[2]
parameters : (expression ',')* expression?;

declaration: type ID | type assignment;                 // int a    |   int a = 4
assignment: ID '=' expression;                          // a = 4

arguments: (argument ',')* argument?;
argument: type ID;

/*
    control flow
*/

// for i = 0..9    |    for i = 0..9 step 2 []
forStatement:
    'for' ID '=' from=expression '..' to=expression (('step') step=expression)? '[' statement* ']';

// when
//    {a} []
//    {b} []
//    otherwise []
when:
    'when'
    whenClause+
    ('otherwise ' '[' statement* ']')?;

whenClause:
    '{' condition=expression '}' '[' statement* ']';

/*
    statement / expression
*/

statement
    : COMMENT
    | declaration
    | forStatement
    | when
    | printStatement
    | printLineStatement
    | assignment
    | methodCall
    | returnStatement
    ;

literal
    : INT                                                             #intLiteral
    | FLOAT                                                           #floatLiteral
    | TEXT                                                            #textLiteral
    | (ON | OFF)                                                      #booleanLiteral
    ;

expression
    : '[' expression ']'                                              #groupExpression
    | left=expression ('*' | ' times ' )           right=expression   #multiplyExpression
    | left=expression ('/' | ' divide ')           right=expression   #divideExpression
    | left=expression ('+' | ' plus '  )           right=expression   #addExpression
    | left=expression ('-' | ' minus'  )           right=expression   #minExpression
    | left=expression operator=('==' | ' is ')     right=expression   #logicalExpression   // logical expressions
    | left=expression operator=('!=' | ' is not ') right=expression   #logicalExpression
    | left=expression operator=('>' | ' larger ')  right=expression   #logicalExpression
    | left=expression operator=('<' | ' smaller ') right=expression   #logicalExpression
    | left=expression operator=('&&' | ' and ')    right=expression   #andExpression
    | left=expression operator=('||' | ' or ')     right=expression   #orExpression
    | readExpression                                                  #readValueExpression
    | methodCall                                                      #methodExpression
    | literal                                                         #literalExpression
    | '-' expression                                                  #negateExpression
    | ID                                                              #referenceExpression
    ;

/*
    built-in methods
*/

readExpression:     'readln'  '[' ']';
printStatement:     'print'   '[' expression ']';
printLineStatement: 'println' '[' expression ']';

/*
    types
*/

type
    : INT_TYPE
    | FLOAT_TYPE
    | TEXT_TYPE
    | BOOLEAN_TYPE
    | VOID_TYPE
    ;

INT_TYPE:       'int';
FLOAT_TYPE:     'float';
TEXT_TYPE:      'text';
BOOLEAN_TYPE:   '0/1';
VOID_TYPE:      'void';

/*
    literals
*/

TEXT:   '"' ~["]* '"';
INT:    [0-9]+;
FLOAT:  INT '.' [0-9]+;

ON:     'on';
OFF:    'off';
END:    'end';

/*
    rest
*/

COMMENT: ('^' | '~' | '@' | '#' | '$' | '%' | '\'' | '`' | '?' | '(' | ')') ~[\n]* '\n';

ID: [A-Za-z]+;

// skip tabs, spaces, etc...
WS : [ \r\n\t]+ -> skip;


