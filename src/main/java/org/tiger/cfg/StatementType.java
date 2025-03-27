package org.tiger.cfg;

public enum StatementType {
    ASSIGN,         // 赋值语句
    CALL,           // 方法调用
    RETURN,         // 返回语句
    IF,             // if语句
    WHILE,          // while语句
    BREAK,          // break语句
    CONTINUE,       // continue语句
    LABEL,          // 标签语句
    PRINT,          // 打印语句
    NEW_OBJECT,     // 创建对象
    NEW_ARRAY,      // 创建数组
    ARRAY_ASSIGN,   // 数组赋值
    ARRAY_LOOKUP,   // 数组访问
    ARRAY_LENGTH,   // 数组长度
    PLUS,           // 加法
    MINUS,          // 减法
    TIMES,          // 乘法
    LESS_THAN,      // 小于
    AND,            // 与
    NOT,            // 非
    IDENTIFIER,     // 标识符
    INTEGER_LITERAL,// 整数字面量
    TRUE,           // true
    FALSE,          // false
    THIS            // this
} 