# Tiger 编译器

这是一个基于 ANTLR4 的 Tiger 语言编译器实现。目前已完成词法分析、语法分析和语义分析阶段。

## 项目结构

```
src/
├── main/
│   ├── antlr4/         # ANTLR4 语法文件
│   │   └── org/tiger/
│   │       └── Tiger.g4
│   └── java/
│       └── org/tiger/
│           ├── semantic/    # 语义分析相关类
│           │   ├── Scope.java
│           │   ├── SemanticAnalyzer.java
│           │   └── SemanticTest.java
│           └── ...         # 其他编译器组件
```

## 已实现功能

### 1. 语法分析
- 使用 ANTLR4 实现的语法分析器
- 支持类、接口、方法、变量声明等 Java 风格的语法
- 支持表达式、语句、控制流等基本语言特性

### 2. 语义分析
已实现以下语义检查：

#### 2.1 作用域管理
- 支持嵌套作用域（全局作用域、类作用域、方法作用域）
- 变量和方法的符号表管理

#### 2.2 类型检查
- 基本类型（int、boolean）的兼容性检查
- 方法返回类型检查
- 方法参数类型检查

#### 2.3 符号检查
- 变量重复声明检查
- 变量使用前声明检查
- 方法重复定义检查

#### 2.4 方法调用检查
- 方法存在性检查
- 参数数量匹配检查
- 参数类型兼容性检查
- 返回类型匹配检查

## 使用示例

```java
// 创建语义分析器
SemanticAnalyzer analyzer = new SemanticAnalyzer();

// 分析代码
analyzer.visit(parser.compilationUnit());

// 获取语义错误
if (analyzer.hasErrors()) {
    for (String error : analyzer.getErrors()) {
        System.out.println(error);
    }
}
```

## 错误检测示例

语义分析器可以检测以下类型的错误：

1. 变量重复声明：
```java
int x;
int x;  // 错误：变量 'x' 已在当前作用域中定义
```

2. 未声明变量的使用：
```java
int y = x;  // 错误：变量 'x' 在使用前未声明
```

3. 方法参数错误：
```java
void method1(int a, boolean b) { }

method1(1);           // 错误：方法 'method1' 需要 2 个参数，但只提供了 1 个
method1(1, 1);        // 错误：参数类型不匹配，第 2 个参数应为 boolean 类型
```

4. 返回类型不匹配：
```java
void method2() {
    return 1;  // 错误：返回类型不匹配，期望 void 类型，得到 int 类型
}
```
还有一些可以进一步改进的地方：

类型系统扩展：
添加更多基本类型（如 float、double、char 等）
支持数组类型的检查
支持类类型的继承关系检查

错误处理优化：
添加更详细的错误信息
支持错误恢复机制
添加警告信息

符号表功能增强：
支持方法重载
支持接口实现检查
支持泛型类型检查
## 下一步计划

1. 实现控制流图（CFG）的设计和生成
2. 实现面向对象特性的消除（类的扁平化）
3. 实现中间代码生成
4. 实现程序分析和优化

## 构建和运行

1. 编译项目：
```bash
mvn compile
```

2. 运行测试：
```bash
mvn exec:java
```

## 依赖

- Java 8 或更高版本
- ANTLR 4.13.1
- Maven 3.x

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
