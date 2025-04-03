package org.tiger.codegen;

import org.junit.Test;
import static org.junit.Assert.*;
import org.tiger.cfg.*;
import org.tiger.cfg.statements.*;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

public class BytecodeGeneratorTest {
    @Test
    public void testGenerateClass() throws Exception {
        // 创建CFG
        CFG cfg = new CFG("main");
        
        // 创建基本块
        BasicBlock entryBlock = new BasicBlock("entry");
        BasicBlock exitBlock = new BasicBlock("exit");
        
        // 添加语句到入口块
        entryBlock.addStatement(new AssignStatement("x", "42"));
        entryBlock.addStatement(new PrintStatement("x"));
        entryBlock.addStatement(new ReturnStatement(null));
        
        // 设置基本块之间的关系
        entryBlock.addSuccessor(exitBlock);
        exitBlock.addPredecessor(entryBlock);
        
        // 添加基本块到CFG
        cfg.addBlock(entryBlock);
        cfg.addBlock(exitBlock);
        
        // 设置入口和出口基本块
        cfg.setEntryBlock(entryBlock);
        cfg.setExitBlock(exitBlock);
        
        // 创建方法CFG
        MethodCFG mainMethod = new MethodCFG(
            "main",
            "void",
            Arrays.asList("[Ljava/lang/String;"),
            Map.of(entryBlock.getLabel(), entryBlock, exitBlock.getLabel(), exitBlock),
            entryBlock
        );
        
        // 创建方法CFG集合
        Map<String, MethodCFG> methodCFGs = new HashMap<>();
        methodCFGs.put("main", mainMethod);
        
        // 生成字节码
        BytecodeGenerator generator = new BytecodeGenerator();
        byte[] bytecode = generator.generateClass("org/tiger/TestClass", methodCFGs);
        
        // 保存字节码到文件
        try (FileOutputStream fos = new FileOutputStream("TestClass.class")) {
            fos.write(bytecode);
        }
        
        // 加载并执行生成的类
        ClassLoader loader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.equals("org.tiger.TestClass")) {
                    return defineClass(name, bytecode, 0, bytecode.length);
                }
                return super.findClass(name);
            }
        };
        
        Class<?> clazz = loader.loadClass("org.tiger.TestClass");
        clazz.getMethod("main", String[].class).invoke(null, (Object) new String[0]);
    }
} 