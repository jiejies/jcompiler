package org.tiger.cfg;

import org.tiger.cfg.statements.AssignStatement;
import org.tiger.cfg.statements.CallStatement;
import org.tiger.cfg.statements.ReturnStatement;
import java.util.Arrays;

public class CFGTest {
    public static void main(String[] args) {
        // 创建一个简单的CFG
        CFG cfg = new CFG("testMethod");
        
        // 创建基本块
        BasicBlock entryBlock = new BasicBlock("entry");
        BasicBlock block1 = new BasicBlock("block1");
        BasicBlock block2 = new BasicBlock("block2");
        BasicBlock exitBlock = new BasicBlock("exit");
        
        // 添加语句到基本块
        entryBlock.addStatement(new AssignStatement("x", "1"));
        entryBlock.addStatement(new AssignStatement("y", "2"));
        
        block1.addStatement(new CallStatement("result", "add", Arrays.asList("x", "y")));
        
        block2.addStatement(new AssignStatement("z", "result"));
        
        exitBlock.addStatement(new ReturnStatement("z"));
        
        // 设置基本块之间的关系
        entryBlock.addSuccessor(block1);
        block1.addPredecessor(entryBlock);
        
        block1.addSuccessor(block2);
        block2.addPredecessor(block1);
        
        block2.addSuccessor(exitBlock);
        exitBlock.addPredecessor(block2);
        
        // 添加基本块到CFG
        cfg.addBlock(entryBlock);
        cfg.addBlock(block1);
        cfg.addBlock(block2);
        cfg.addBlock(exitBlock);
        
        // 设置入口和出口基本块
        cfg.setEntryBlock(entryBlock);
        cfg.setExitBlock(exitBlock);
        
        // 打印CFG信息
        System.out.println("=== CFG Information ===");
        System.out.println(cfg);
        
        // 打印每个基本块的信息
        System.out.println("\n=== Basic Blocks Information ===");
        for (BasicBlock block : cfg.getBlocks()) {
            System.out.println("\nBlock: " + block.getLabel());
            System.out.println("Predecessors: " + block.getPredecessors().stream()
                    .map(BasicBlock::getLabel)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("none"));
            System.out.println("Successors: " + block.getSuccessors().stream()
                    .map(BasicBlock::getLabel)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("none"));
            System.out.println("Statements:");
            for (Statement stm : block.getStatements()) {
                System.out.println("  " + stm);
            }
        }
    }
} 