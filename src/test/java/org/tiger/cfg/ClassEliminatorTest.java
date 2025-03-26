package org.tiger.cfg;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.tiger.TigerLexer;
import org.tiger.TigerParser;
import org.tiger.semantic.SemanticAnalyzer;

public class ClassEliminatorTest {
    
    @Test
    public void testSimpleInheritance() throws IOException {
        // 读取测试源代码
        String source = Files.readString(Paths.get("src/test/resources/simple_inheritance.tiger"));
        System.out.println("源代码：\n" + source);
        
        // 1. 词法分析
        System.out.println("\n=== 词法分析 ===");
        CharStream input = CharStreams.fromString(source);
        TigerLexer lexer = new TigerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();
        
        // 打印所有词法单元
        System.out.println("词法单元列表：");
        for (Token token : tokens.getTokens()) {
            System.out.printf("行号: %d, 列号: %d, 类型: %s, 文本: %s%n",
                token.getLine(),
                token.getCharPositionInLine(),
                lexer.getVocabulary().getDisplayName(token.getType()),
                token.getText());
        }
        
        // 2. 语法分析
        System.out.println("\n=== 语法分析 ===");
        TigerParser parser = new TigerParser(tokens);
        ParseTree tree = parser.compilationUnit();
        System.out.println("语法树：");
        System.out.println(tree.toStringTree(parser));
        
        // 3. 语义分析
        System.out.println("\n=== 语义分析 ===");
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.visit(tree);
        
        // 打印语义分析结果
        if (semanticAnalyzer.hasErrors()) {
            System.out.println("发现语义错误：");
            semanticAnalyzer.getErrors().forEach(System.out::println);
        } else {
            System.out.println("语义分析通过，未发现错误");
        }
        
        // 4. 类消除（中间代码生成）
        System.out.println("\n=== 中间代码生成（类消除） ===");
        ClassEliminator eliminator = new ClassEliminator();
        
        // 添加类及其继承关系
        eliminator.addClass("Animal", null);
        eliminator.addClass("Dog", "Animal");
        eliminator.addClass("Cat", "Animal");
        
        // 添加字段
        ClassNode animal = eliminator.getClassMap().get("Animal");
        animal.addField(new ClassNode.Field("name", "String"));
        animal.addField(new ClassNode.Field("age", "int"));
        
        // 添加方法
        animal.addMethod(new ClassNode.Method(
            "makeSound",
            "void",
            new ArrayList<>(),
            null
        ));
        
        // 构建继承树并消除类
        eliminator.buildInheritanceTree();
        eliminator.eliminateClasses();
        
        // 打印类消除结果
        System.out.println("\n类消除结果：");
        System.out.println("1. 类信息：");
        eliminator.getClassMap().forEach((className, classNode) -> {
            System.out.println("\n类名：" + className);
            System.out.println("字段：");
            classNode.getFields().forEach(field -> 
                System.out.println("  - " + field.getName() + " : " + field.getType()));
            System.out.println("方法：");
            classNode.getMethods().forEach(method -> {
                System.out.println("  - " + method.getName() + " : " + method.getReturnType());
                System.out.println("    参数：");
                method.getParameters().forEach(param ->
                    System.out.println("    * " + param.getName() + " : " + param.getType()));
            });
        });
        
        System.out.println("\n2. 继承树：");
        System.out.println(eliminator.getInheritanceTree());
    }
}