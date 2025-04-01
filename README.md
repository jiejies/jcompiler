# Tiger 编译器

这是一个用 Java 实现的 Tiger 语言编译器。

## 项目结构

```
src/
├── main/
│   ├── antlr4/         # ANTLR4 语法定义
│   │   └── org/tiger/
│   │       └── Tiger.g4
│   └── java/          # Java 源代码
│       └── org/tiger/
│           ├── ast/    # 抽象语法树
│           ├── cfg/    # 控制流图
│           ├── semantic/ # 语义分析
│           └── ...
└── test/             # 测试代码和资源
    ├── java/
    └── resources/    # 测试用例
```

## 已实现功能

### 1. 词法分析
- [x] 使用 ANTLR4 实现词法分析器
- [x] 支持关键字、标识符、运算符等词法单元
- [x] 提供词法单元位置信息（行号、列号）

### 2. 语法分析
- [x] 使用 ANTLR4 实现语法分析器
- [x] 支持类定义、方法定义、字段定义
- [x] 支持继承关系
- [x] 生成语法树

### 3. 语义分析
- [x] 符号表管理
- [x] 类型检查
- [x] 作用域分析
- [x] 错误报告

### 4. 中间代码生成
- [x] 类消除（去除面向对象特性）
  - [x] 字段重命名（添加类名前缀）
  - [x] 方法转换（添加 this 参数）
  - [x] 继承关系处理
- [x] 控制流图（CFG）生成
  - [x] 基本块创建
  - [x] CFG 数据结构
  - [x] AST 到 CFG 的转换
    - [x] 基本赋值语句
    - [x] 数组操作（创建、赋值、访问）
    - [x] 条件语句（if-else）
    - [x] 循环语句（while）
    - [x] 方法调用

### 5. 运行时支持
- [x] TigerRuntime类实现
  - [x] printInt：输出整数值到命令行
  - [x] printString：输出字符串到命令行
  - [x] printBoolean：输出布尔值到命令行

### 6. 测试覆盖
- [x] AstToCfgConverterTest：AST 到 CFG 的转换测试
- [x] ClassEliminatorTest：类继承消除功能测试
- [x] WhileLoopTest：while 循环的 CFG 转换测试
- [x] OutputTest：输出功能测试

## 待实现功能

### 1. 中间代码优化
- [ ] 常量传播
- [ ] 死代码消除
- [ ] 循环优化

### 2. 目标代码生成
- [ ] 字节码生成（使用 ASM）
- [ ] 寄存器分配
- [ ] 指令选择

## 如何运行

1. 编译项目：
```bash
mvn clean compile
```

2. 运行测试：
```bash
mvn test
```

## 测试用例

目前支持以下测试场景：

1. 简单继承：
```java
// simple_inheritance.tiger
class Animal {
    String name;
    int age;
    
    void makeSound() {
        age = age + 1;
    }
}

class Dog extends Animal {
    String breed;
    
    void bark() {
        age = age + 2;
        makeSound();
    }
}
```

2. 输出功能：
```java
// output_test.tiger
class Test_output_main {
    public static void main(String[] args) {
        TigerRuntime.printInt(42);
        TigerRuntime.printString("Hello, Tiger!");
        TigerRuntime.printBoolean(true);
    }
}
```

3. 多层继承：测试多层继承关系下的字段和方法处理
4. 语义错误检查：测试类型错误、未定义变量等场景
5. CFG 生成：
   - 基本赋值语句测试
   - 数组操作测试
   - 条件语句测试
   - 循环语句测试
   - 方法调用测试

## 开发进度

- [x] Lab 1: 词法分析和语法分析
- [x] Lab 2: 语义分析
- [ ] Lab 3: 中间代码生成
  - [x] Part A: CFG 数据结构
  - [x] Part B: 类消除
  - [x] Part C: AST 到 CFG 的基本转换
  - [x] Part D: 完整 CFG 转换（循环和方法调用）
  - [ ] Part E: 字节码生成
  - [ ] Part F: 程序分析和优化

## 下一步计划

1. 实现字节码生成
   - 使用 ASM 库生成 JVM 字节码
   - 处理类和方法的字节码生成
   - 实现基本语句的字节码转换

2. 实现程序优化
   - 实现常量传播
   - 实现死代码消除
   - 实现循环优化

## 依赖

- Java 11
- ANTLR 4.13.1
- ASM 9.6
- JUnit 4.13.2
- SLF4J 2.0.9
- Logback 1.4.11

## 构建要求

- Java 11 或更高版本
- Maven 3.6 或更高版本
- ANTLR 4.13.1

## Tiger语言特性

Tiger是一个简单的面向对象语言，具有以下特性：

- 基本类型：整数、布尔值、字符串
- 类定义和继承
- 方法定义和重写
- 实例字段和方法
- 静态字段和方法
- 数组操作
- 基本控制流语句（if-else、while）
- 标准输出功能（通过TigerRuntime类）

## 运行时库

Tiger语言提供了一个运行时库`TigerRuntime`，用于支持基本的输入输出操作：

### TigerRuntime类

`TigerRuntime`是一个内置类，提供了以下静态方法：

1. `printInt(int value)`：将整数值输出到命令行
   ```java
   TigerRuntime.printInt(42);  // 输出: 42
   ```

2. `printString(String value)`：将字符串输出到命令行
   ```java
   TigerRuntime.printString("Hello, Tiger!");  // 输出: Hello, Tiger!
   ```

3. `printBoolean(boolean value)`：将布尔值输出到命令行
   ```java
   TigerRuntime.printBoolean(true);  // 输出: true
   ```

这意味着Tiger程序最终会编译成Java字节码，并在JVM上运行，输出显示在命令行终端中。

## 语法规则和命名规范

### 命名规则
1. 主类（包含 main 方法的类）：
   - 类名必须以 `_main` 结尾
   - 必须包含 `public static void main(String[] args)` 方法
   - 示例：
     ```java
     class Counter_main {
         public static void main(String[] args) {
             Counter c = new Counter();
             c.increment();
         }
     }
     ```

2. 标识符规则：
   - 类名：必须以大写字母开头，可包含字母、数字和下划线
   - 方法名：必须以小写字母开头，可包含字母、数字和下划线
   - 变量名：必须以小写字母开头，可包含字母、数字和下划线
   - 不允许使用 Java 关键字作为标识符

### 语法规则（类似 Java 的简化版）

1. 类定义：
   ```java
   class ClassName [extends ParentClass] {
       // 字段和方法定义
   }
   ```

2. 字段定义：
   ```java
   [static] Type fieldName;
   ```
   支持的类型：
   - `int`：整数类型
   - `String`：字符串类型
   - `boolean`：布尔类型
   - 自定义类类型
   - 数组类型：`Type[]`

3. 方法定义：
   ```java
   [public] [static] ReturnType methodName([Parameters]) {
       // 方法体
   }
   ```
   - 返回类型：void 或任何支持的类型
   - 参数列表：逗号分隔的 `Type paramName` 对

4. 表达式：
   - 算术运算：`+`、`-`、`*`
   - 比较运算：`<`、`>`、`==`
   - 逻辑运算：`&&`
   - 方法调用：`object.method(args)`
   - 对象创建：`new ClassName()`
   - 变量引用：`variableName`
   - 字面量：整数、布尔值（true/false）、字符串

5. 语句：
   ```java
   // 赋值语句
   variable = expression;
   
   // if 语句
   if (condition) {
       statements
   } else {
       statements
   }
   
   // while 循环
   while (condition) {
       statements
   }
   
   // 方法调用语句
   object.method(args);
   
   // 打印语句
   print expression;
   ```

### 限制和约束

1. 访问控制：
   - 只支持 public 和 static 修饰符
   - 默认所有成员都是 public

2. 继承限制：
   - 只支持单继承
   - 不支持接口
   - 子类可以重写父类方法

3. 类型系统：
   - 不支持泛型
   - 不支持类型转换
   - 类型必须严格匹配

4. 其他限制：
   - 不支持构造函数重载
   - 不支持方法重载
   - 不支持 this 关键字（编译器自动处理）
   - 不支持 null 值
   - 不支持 break 和 continue
   - 只支持 while 循环（不支持 for、do-while）
   - 支持 TigerRuntime 类的输出方法

### 示例程序

1. 简单计数器：
```java
class Counter {
    int count;
    
    void increment() {
        while (count < 10) {
            count = count + 1;
            TigerRuntime.printInt(count);
        }
    }
}

class Counter_main {
    public static void main(String[] args) {
        Counter c = new Counter();
        c.increment();
    }
}
```

2. 继承示例：
```java
class Animal {
    String name;
    int age;
    
    void makeSound() {
        age = age + 1;
    }
}

class Dog extends Animal {
    String breed;
    
    void bark() {
        age = age + 2;
        makeSound();
        TigerRuntime.printString("Woof!");
    }
}
```