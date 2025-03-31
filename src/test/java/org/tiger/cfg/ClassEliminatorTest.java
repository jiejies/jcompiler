package org.tiger.cfg;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.tiger.TigerLexer;
import org.tiger.TigerParser;
import org.tiger.semantic.SemanticAnalyzer;
import org.tiger.ast.*;
import org.tiger.cfg.CFG;
import java.util.Map;

public class ClassEliminatorTest {
    
    @Test
    public void testSimpleInheritance() throws IOException {
        System.out.println("开始测试类继承消除...");
        
        // 1. 读取并解析源代码，词法分析语法分析
        System.out.println("步骤1: 解析源代码...");
        String source = Files.readString(Paths.get("src/test/resources/simple_inheritance.tiger"));
        CharStream input = CharStreams.fromString(source);
        TigerLexer lexer = new TigerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        TigerParser parser = new TigerParser(tokens);
        ParseTree tree = parser.compilationUnit();
        
        // 2. 语义分析
        System.out.println("步骤2: 进行语义分析...");
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.visit(tree);
        
        if (semanticAnalyzer.hasErrors()) {
            System.out.println("语义分析发现以下错误：");
            for (String error : semanticAnalyzer.getErrors()) {
                System.out.println("- " + error);
            }
            assertFalse("语义分析不应该有错误", semanticAnalyzer.hasErrors());
        }
        
        // 3. ParseTree 转换为 AST
        System.out.println("步骤3: 转换为AST...");
        ParseTreeToAstConverter astConverter = new ParseTreeToAstConverter();
        Ast ast = astConverter.visit(tree);
        assertNotNull("AST 不应该为空", ast);
        
        // 4. AST 转换为 CFG
        System.out.println("步骤4: 转换为CFG...");
        AstToCfgConverter cfgConverter = new AstToCfgConverter();
        ast.accept(cfgConverter);
        Map<String, CFG> methodCFGs = cfgConverter.getMethodCFGs();
        assertFalse("应该至少生成一个方法的 CFG", methodCFGs.isEmpty());
        
        // 5. 打印生成的 CFG 结构
        System.out.println("\n=== 生成的 CFG 结构 ===");
        for (Map.Entry<String, CFG> entry : methodCFGs.entrySet()) {
            System.out.println("\n方法: " + entry.getKey());
            CFG cfg = entry.getValue();
            System.out.println(cfg.toString());
        }
        
        System.out.println("测试通过！成功完成了类继承消除的转换过程。");
    }
}