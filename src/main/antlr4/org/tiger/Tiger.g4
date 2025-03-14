grammar Tiger;

// 解析器规则
compilationUnit
    : packageDeclaration? importDeclaration* typeDeclaration* EOF
    ;

packageDeclaration
    : 'package' qualifiedName ';'
    ;

importDeclaration
    : 'import' 'static'? qualifiedName ('.' '*')? ';'
    ;

typeDeclaration
    : classDeclaration
    | interfaceDeclaration
    | ';'
    ;

classDeclaration
    : classModifier* 'class' IDENTIFIER
      ('extends' type)?
      ('implements' typeList)?
      classBody
    ;

interfaceDeclaration
    : interfaceModifier* 'interface' IDENTIFIER
      ('extends' typeList)?
      interfaceBody
    ;

classBody
    : '{' classBodyDeclaration* '}'
    ;

interfaceBody
    : '{' interfaceBodyDeclaration* '}'
    ;

interfaceBodyDeclaration
    : interfaceMethodDeclaration
    | interfaceFieldDeclaration
    | ';'
    ;

interfaceMethodDeclaration
    : interfaceMethodModifier* typeTypeOrVoid IDENTIFIER formalParameters ('[' ']')*
      ('throws' qualifiedNameList)? ';'
    ;

interfaceFieldDeclaration
    : fieldModifier* type variableDeclarators ';'
    ;

classBodyDeclaration
    : ';'
    | memberDeclaration
    | block
    ;

memberDeclaration
    : methodDeclaration
    | fieldDeclaration
    | constructorDeclaration
    ;

methodDeclaration
    : methodModifier* typeTypeOrVoid IDENTIFIER formalParameters
      ('[' ']')*
      ('throws' qualifiedNameList)?
      (methodBody | ';')
    ;

methodBody
    : block
    ;

fieldDeclaration
    : fieldModifier* type variableDeclarators ';'
    ;

constructorDeclaration
    : constructorModifier* IDENTIFIER formalParameters
      ('throws' qualifiedNameList)?
      constructorBody
    ;

constructorBody
    : block
    ;

formalParameters
    : '(' formalParameterList? ')'
    ;

formalParameterList
    : formalParameter (',' formalParameter)*
    ;

formalParameter
    : variableModifier* type variableDeclaratorId
    ;

block
    : '{' blockStatement* '}'
    ;

blockStatement
    : localVariableDeclaration ';'
    | statement
    ;

localVariableDeclaration
    : variableModifier* type variableDeclarators
    ;

statement
    : block
    | 'if' parExpression statement ('else' statement)?
    | 'for' '(' forControl ')' statement
    | 'while' parExpression statement
    | 'do' statement 'while' parExpression ';'
    | 'return' expression? ';'
    | 'break' IDENTIFIER? ';'
    | 'continue' IDENTIFIER? ';'
    | expression ';'
    | ';'
    ;

forControl
    : forInit? ';' expression? ';' forUpdate?
    ;

forInit
    : localVariableDeclaration
    | expressionList
    ;

forUpdate
    : expressionList
    ;

parExpression
    : '(' expression ')'
    ;

expression
    : primary
    | expression '.' IDENTIFIER
    | expression '.' methodCall
    | methodCall
    | NEW creator
    | expression '[' expression ']'
    | expression ('++' | '--')
    | ('+'|'-'|'++'|'--') expression
    | ('~'|'!') expression
    | expression ('*'|'/'|'%') expression
    | expression ('+'|'-') expression
    | expression ('<' '<' | '>' '>' '>' | '>' '>') expression
    | expression ('<=' | '>=' | '>' | '<') expression
    | expression 'instanceof' type
    | expression ('==' | '!=') expression
    | expression '&' expression
    | expression '^' expression
    | expression '|' expression
    | expression '&&' expression
    | expression '||' expression
    | expression '?' expression ':' expression
    | expression
      ('=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '>>=' | '>>>=' | '<<=' | '%=')
      expression
    ;

primary
    : '(' expression ')'
    | literal
    | IDENTIFIER
    | THIS
    | SUPER
    ;

creator
    : createdName arrayCreatorRest
    | createdName classCreatorRest
    ;

createdName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

arrayCreatorRest
    : '[' (']' ('[' ']')* arrayInitializer | expression ']' ('[' expression ']')* ('[' ']')*)
    ;

classCreatorRest
    : arguments
    ;

methodCall
    : IDENTIFIER arguments
    ;

arguments
    : '(' expressionList? ')'
    ;

expressionList
    : expression (',' expression)*
    ;

// 修饰符
classModifier
    : 'public'
    | 'protected'
    | 'private'
    | 'abstract'
    | 'static'
    | 'final'
    ;

interfaceModifier
    : 'public'
    | 'protected'
    | 'private'
    | 'abstract'
    | 'static'
    ;

methodModifier
    : 'public'
    | 'protected'
    | 'private'
    | 'abstract'
    | 'static'
    | 'final'
    | 'synchronized'
    ;

interfaceMethodModifier
    : 'public'
    | 'abstract'
    | 'default'
    | 'static'
    ;

fieldModifier
    : 'public'
    | 'protected'
    | 'private'
    | 'static'
    | 'final'
    | 'transient'
    | 'volatile'
    ;

constructorModifier
    : 'public'
    | 'protected'
    | 'private'
    ;

variableModifier
    : 'final'
    ;

// 类型相关
type
    : primitiveType ('[' ']')*
    | classType ('[' ']')*
    ;

primitiveType
    : 'boolean'
    | 'char'
    | 'byte'
    | 'short'
    | 'int'
    | 'long'
    | 'float'
    | 'double'
    | 'void'
    ;

classType
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

typeList
    : type (',' type)*
    ;

typeTypeOrVoid
    : type
    | 'void'
    ;

qualifiedName
    : IDENTIFIER ('.' IDENTIFIER)*
    ;

qualifiedNameList
    : qualifiedName (',' qualifiedName)*
    ;

variableDeclarators
    : variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    : variableDeclaratorId ('=' variableInitializer)?
    ;

variableDeclaratorId
    : IDENTIFIER ('[' ']')*
    ;

variableInitializer
    : arrayInitializer
    | expression
    ;

arrayInitializer
    : '{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
    ;

// 字面量
literal
    : DECIMAL_LITERAL
    | HEX_LITERAL
    | OCT_LITERAL
    | BINARY_LITERAL
    | FLOAT_LITERAL
    | CHAR_LITERAL
    | STRING_LITERAL
    | BOOLEAN_LITERAL
    | NULL_LITERAL
    ;

// 词法规则
BOOLEAN_LITERAL : 'true' | 'false';
NULL_LITERAL : 'null';
DECIMAL_LITERAL : ('0' | [1-9] [0-9]*) [lL]?;
HEX_LITERAL : '0' [xX] [0-9a-fA-F]+ [lL]?;
OCT_LITERAL : '0' [0-7]+ [lL]?;
BINARY_LITERAL : '0' [bB] [01]+ [lL]?;
FLOAT_LITERAL : (DECIMAL_LITERAL '.' [0-9]* | '.' [0-9]+) EXPONENT? [fFdD]?
              | DECIMAL_LITERAL EXPONENT [fFdD]?
              | DECIMAL_LITERAL [fFdD]
              ;
CHAR_LITERAL : '\'' (~['\\\r\n] | EscapeSequence) '\'';
STRING_LITERAL : '"' (~["\\\r\n] | EscapeSequence)* '"';

THIS : 'this';
SUPER : 'super';
NEW : 'new';

IDENTIFIER : [a-zA-Z_$][a-zA-Z0-9_$]*;

fragment EXPONENT : [eE] [+-]? [0-9]+;
fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F]
    ;

// 空白字符和注释
WS : [ \t\r\n\u000C]+ -> skip;
COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT : '//' ~[\r\n]* -> skip; 