package org.tiger;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) {
        try {
            // 创建一个简单的Java测试程序
            String input = "package org.example;\n\n" +
                          "import java.util.List;\n" +
                          "import java.util.ArrayList;\n\n" +
                          "public class HelloWorld {\n" +
                          "    private String message;\n\n" +
                          "    public HelloWorld(String message) {\n" +
                          "        this.message = message;\n" +
                          "    }\n\n" +
                          "    public void sayHello() {\n" +
                          "        System.out.println(message);\n" +
                          "    }\n\n" +
                          "    public static void main(String[] args) {\n" +
                          "        HelloWorld hello = new HelloWorld(\"Hello, World!\");\n" +
                          "        hello.sayHello();\n" +
                          "    }\n" +
                          "}";
            
            // 创建输入流
            CharStream inputStream = CharStreams.fromString(input);
            
            // 创建词法分析器
            TigerLexer lexer = new TigerLexer(inputStream);
            
            // 创建token流
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // 创建语法分析器
            TigerParser parser = new TigerParser(tokens);
            
            // 开始解析，从compilationUnit规则开始
            ParseTree tree = parser.compilationUnit();
            
            // 打印语法树
            System.out.println("Parse Tree:");
            System.out.println(formatParseTree(tree, parser, 0));
            
            // 创建访问者
            TigerBaseVisitor<String> visitor = new TigerBaseVisitor<String>() {
                @Override
                public String visitCompilationUnit(TigerParser.CompilationUnitContext ctx) {
                    StringBuilder result = new StringBuilder();
                    if (ctx.packageDeclaration() != null) {
                        result.append("Package: ").append(ctx.packageDeclaration().qualifiedName().getText()).append("\n");
                    }
                    
                    for (TigerParser.ImportDeclarationContext importCtx : ctx.importDeclaration()) {
                        result.append("Import: ").append(importCtx.qualifiedName().getText()).append("\n");
                    }
                    
                    for (TigerParser.TypeDeclarationContext typeCtx : ctx.typeDeclaration()) {
                        if (typeCtx.classDeclaration() != null) {
                            result.append("Class: ").append(typeCtx.classDeclaration().IDENTIFIER().getText()).append("\n");
                        }
                    }
                    
                    return result.toString();
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

    // 格式化语法树的辅助方法
    private static String formatParseTree(ParseTree tree, TigerParser parser, int level) {
        StringBuilder result = new StringBuilder();
        String indent = "  ".repeat(level);  // 每一层缩进两个空格
        
        if (tree instanceof ParserRuleContext) {
            String ruleName = parser.getRuleNames()[((ParserRuleContext) tree).getRuleIndex()];
            result.append(indent).append(ruleName).append(" {\n");
            
            // 递归处理所有子节点
            for (int i = 0; i < tree.getChildCount(); i++) {
                result.append(formatParseTree(tree.getChild(i), parser, level + 1));
            }
            
            result.append(indent).append("}\n");
        } else {
            // 处理终结符
            String nodeText = tree.getText();
            if (!nodeText.trim().isEmpty()) {
                result.append(indent).append("'").append(nodeText).append("'\n");
            }
        }
        
        return result.toString();
    }
} 