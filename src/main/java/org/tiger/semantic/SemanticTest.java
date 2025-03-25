package org.tiger.semantic;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tiger.TigerLexer;
import org.tiger.TigerParser;

public class SemanticTest {
    public static void main(String[] args) {
        // 测试代码
        String testCode = 
            "class Test {\n" +
            "    public void test() {\n" +
            "        int x = 10;\n" +
            "        int x = 20; // 重复声明错误\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "class Test {  // 重复类名错误\n" +
            "    public void foo() {}\n" +
            "}";

        try {
            // 创建词法分析器
            CharStream input = CharStreams.fromString(testCode);
            TigerLexer lexer = new TigerLexer(input);
            
            // 创建语法分析器
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            TigerParser parser = new TigerParser(tokens);
            
            // 获取语法树
            TigerParser.CompilationUnitContext tree = parser.compilationUnit();
            
            // 创建语义分析器并访问语法树
            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            analyzer.visit(tree);
            
            // 输出分析结果
            if (analyzer.hasErrors()) {
                System.out.println("发现语义错误：");
                for (String error : analyzer.getErrors()) {
                    System.out.println(error);
                }
            } else {
                System.out.println("语义分析通过，未发现错误。");
            }
            
        } catch (Exception e) {
            System.err.println("分析过程中出现异常：");
            e.printStackTrace();
        }
    }
} 