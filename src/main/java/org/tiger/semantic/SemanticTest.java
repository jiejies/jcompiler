package org.tiger.semantic;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.tiger.TigerLexer;
import org.tiger.TigerParser;

public class SemanticTest {
    public static void main(String[] args) {
        // 测试用例1：基本语法和语义检查
        System.out.println("\n=== 测试用例1：基本语法和语义检查 ===");
        String testCode1 = 
            "class Test {\n" +
            "    int x;\n" +
            "    boolean flag;\n" +
            "    \n" +
            "    int method1(int a, boolean b) {\n" +
            "        int result = a + x;\n" +
            "        if (b && flag) {\n" +
            "            return result;\n" +
            "        }\n" +
            "        return 0;\n" +
            "    }\n" +
            "}";
        
        // 测试用例2：语义错误检查
        System.out.println("\n=== 测试用例2：语义错误检查 ===");
        String testCode2 = 
            "class ErrorTest {\n" +
            "    int x;\n" +
            "    int x;  // 重复声明变量\n" +
            "    \n" +
            "    void method2() {\n" +
            "        int y = undeclaredVar;  // 使用未声明的变量\n" +
            "        return 42;  // void方法不应该返回值\n" +
            "    }\n" +
            "    \n" +
            "    int method3(boolean flag) {\n" +
            "        if (flag) {\n" +
            "            return true;  // 返回类型不匹配\n" +
            "        }\n" +
            "        return \"hello\";  // 返回类型不匹配\n" +
            "    }\n" +
            "}";

        // 测试用例3：复杂语法结构
        System.out.println("\n=== 测试用例3：复杂语法结构 ===");
        String testCode3 = 
            "class Complex {\n" +
            "    int factorial(int n) {\n" +
            "        if (n <= 1) {\n" +
            "            return 1;\n" +
            "        }\n" +
            "        return n * factorial(n - 1);\n" +
            "    }\n" +
            "    \n" +
            "    void testLoop() {\n" +
            "        int sum = 0;\n" +
            "        int i = 0;\n" +
            "        while (i < 10) {\n" +
            "            if (i % 2 == 0) {\n" +
            "                sum = sum + factorial(i);\n" +
            "            }\n" +
            "            i = i + 1;\n" +
            "        }\n" +
            "    }\n" +
            "}";

        // 分析每个测试用例
        analyzeCode(testCode1, "测试用例1");
        analyzeCode(testCode2, "测试用例2");
        analyzeCode(testCode3, "测试用例3");
    }

    private static void analyzeCode(String code, String caseName) {
        System.out.println("\n开始分析 " + caseName + ":");
        
        // 词法分析
        CharStream input = CharStreams.fromString(code);
        TigerLexer lexer = new TigerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        System.out.println("\n1. 词法分析结果：");
        tokens.fill();
        for (Token token : tokens.getTokens()) {
            if (token.getType() != Token.EOF) {
                System.out.printf("%-20s \t类型: %-4d \t行: %d \t列: %d%n",
                    token.getText(),
                    token.getType(),
                    token.getLine(),
                    token.getCharPositionInLine());
            }
        }
        
        // 语法分析
        TigerParser parser = new TigerParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
                                  int line, int charPositionInLine, String msg, RecognitionException e) {
                System.err.println("语法错误 - 行 " + line + ":" + charPositionInLine + " " + msg);
            }
        });
        
        System.out.println("\n2. 语法分析结果：");
        ParseTree tree = parser.compilationUnit();
        System.out.println("语法树深度: " + tree.getChildCount());
        
        // 语义分析
        System.out.println("\n3. 语义分析结果：");
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        try {
            analyzer.visit(tree);
            if (analyzer.hasErrors()) {
                System.out.println("发现语义错误：");
                for (String error : analyzer.getErrors()) {
                    System.out.println("- " + error);
                }
            } else {
                System.out.println("未发现语义错误");
            }
        } catch (Exception e) {
            System.out.println("语义分析过程中发生错误：" + e.getMessage());
        }
        
        System.out.println("\n" + caseName + " 分析完成");
        System.out.println("----------------------------------------");
    }
} 