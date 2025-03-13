grammar Tiger;

// 解析器规则
program: exp EOF;

exp: literal                                      # LiteralExp
   | ID                                          # IdExp
   | exp operator exp                            # BinaryExp
   | ID '(' (exp (',' exp)*)? ')'               # CallExp
   | 'if' exp 'then' exp ('else' exp)?          # IfExp
   | 'while' exp 'do' exp                       # WhileExp
   | 'for' ID ':=' exp 'to' exp 'do' exp       # ForExp
   | 'let' declaration+ 'in' exp+ 'end'         # LetExp
   | '(' (exp (';' exp)*)? ')'                  # SeqExp
   ;

declaration
   : varDec
   | functionDec
   | typeDec
   ;

varDec: 'var' ID (':' ID)? ':=' exp;

functionDec: 'function' ID '(' typeFields? ')' (':' ID)? '=' exp;

typeDec: 'type' ID '=' type;

type: ID                                         # NameTy
    | 'array' 'of' ID                           # ArrayTy
    | '{' typeFields? '}'                       # RecordTy
    ;

typeFields: ID ':' ID (',' ID ':' ID)*;

operator: '+' | '-' | '*' | '/' | '=' | '<>' | '<' | '<=' | '>' | '>=';

literal: INT | STRING | 'nil';

// 词法规则
INT: [0-9]+;
STRING: '"' (~["\r\n] | '\\' .)* '"';
ID: [a-zA-Z][a-zA-Z0-9_]*;
WS: [ \t\r\n]+ -> skip;
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip; 