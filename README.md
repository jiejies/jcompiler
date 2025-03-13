# Tiger 编译器

这是一个基于《Modern Compiler Implementation》书中的Tiger语言实现的编译器。

## 项目结构

```
src/
├── main/
│   ├── antlr4/         # ANTLR4语法文件
│   └── java/
│       └── org/tiger/
│           ├── lexer/        # 词法分析器
│           ├── parser/       # 语法分析器
│           ├── ast/          # 抽象语法树
│           ├── symboltable/  # 符号表
│           └── codegen/      # 代码生成
```

## 构建要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- ANTLR 4.13.1

## 构建步骤

1. 克隆项目
2. 运行 `mvn clean install`

## Tiger语言特性

Tiger是一个简单的命令式语言，具有以下特性：

- 整数和字符串类型
- 变量声明
- 函数定义
- 数组和记录
- 循环和条件语句

## 开发计划

- [x] 项目基础结构
- [x] ANTLR4语法文件
- [x] AST节点定义
- [ ] 语义分析
- [ ] 类型检查
- [ ] 中间代码生成
- [ ] 目标代码生成 
