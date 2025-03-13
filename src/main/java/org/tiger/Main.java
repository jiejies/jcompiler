package org.tiger;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
//测试程序
public class Main {
    public static void main(String[] args) {
        try {
            // 创建一个简单的测试程序
            String input = "var x = 10;\n" +
                          "var y = 5;\n" +
                          "print(x + y);\n" +
                          "if x > y then print(x) else print(y);\n" +
                          "while x > 0 do x = x - 1;\n" +
                          "for i = 1 to 5 do print(i);\n" +
                          "let var z = 15 in print(z) end;";
            
            // 创建输入流
            CharStream inputStream = CharStreams.fromString(input);
            
            // 创建词法分析器
            TigerLexer lexer = new TigerLexer(inputStream);
            
            // 创建token流
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // 创建语法分析器
            TigerParser parser = new TigerParser(tokens);
            
            // 开始解析
            ParseTree tree = parser.program();
            
            // 打印语法树
            System.out.println("Parse Tree:");
            System.out.println(tree.toStringTree(parser));
            
            // 创建访问者
            TigerBaseVisitor<String> visitor = new TigerBaseVisitor<String>() {
                @Override
                public String visitProgram(TigerParser.ProgramContext ctx) {
                    return "Program: " + ctx.exp().getText();
                }
            };
            
            // 访问语法树
            String result = visitor.visit(tree);
            System.out.println("\nVisitor Result:");
            System.out.println(result);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 