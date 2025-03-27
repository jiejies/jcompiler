package org.tiger.ast;

import org.junit.Test;
import static org.junit.Assert.*;
import org.tiger.cfg.*;
import org.tiger.cfg.statements.*;
import java.util.Map;

public class AstToCfgConverterTest {
    
    @Test
    public void testBasicAssignment() {
        // 创建一个简单的赋值语句 AST
        Assign assign = new Assign("x", new IntegerLiteral(10));
        
        // 创建转换器
        AstToCfgConverter converter = new AstToCfgConverter();
        
        // 访问 AST
        assign.accept(converter);
        
        // 获取生成的 CFG
        Map<String, CFG> cfgs = converter.getMethodCFGs();
        
        // 验证 CFG 是否正确生成
        assertNotNull("CFG map should not be null", cfgs);
        assertFalse("CFG map should not be empty", cfgs.isEmpty());
        
        // 获取主方法的 CFG
        CFG mainCFG = cfgs.get("main");
        assertNotNull("Main method CFG should exist", mainCFG);
        
        // 验证基本块
        BasicBlock entryBlock = mainCFG.getEntryBlock();
        assertNotNull("Entry block should exist", entryBlock);
        
        // 验证语句
        assertFalse("Entry block should have statements", entryBlock.getStatements().isEmpty());
        org.tiger.cfg.Statement firstStmt = entryBlock.getStatements().get(0);
        assertTrue("First statement should be an assignment", firstStmt instanceof AssignStatement);
        
        AssignStatement assignStmt = (AssignStatement) firstStmt;
        assertEquals("Target should be 'x'", "x", assignStmt.getTarget());
        assertEquals("Source should be '10'", "10", assignStmt.getSource());
    }
    
    @Test
    public void testArrayOperations() {
        // 创建数组操作相关的 AST
        NewArray newArray = new NewArray(new IntegerLiteral(5));
        ArrayAssign arrayAssign = new ArrayAssign("arr", new IntegerLiteral(0), new IntegerLiteral(1));
        
        // 创建转换器
        AstToCfgConverter converter = new AstToCfgConverter();
        
        // 访问 AST
        newArray.accept(converter);
        arrayAssign.accept(converter);
        
        // 获取生成的 CFG
        Map<String, CFG> cfgs = converter.getMethodCFGs();
        
        // 验证 CFG 是否正确生成
        assertNotNull("CFG map should not be null", cfgs);
        assertFalse("CFG map should not be empty", cfgs.isEmpty());
        
        // 获取主方法的 CFG
        CFG mainCFG = cfgs.get("main");
        assertNotNull("Main method CFG should exist", mainCFG);
        
        // 验证基本块
        BasicBlock entryBlock = mainCFG.getEntryBlock();
        assertNotNull("Entry block should exist", entryBlock);
        
        // 验证语句
        assertFalse("Entry block should have statements", entryBlock.getStatements().isEmpty());
        
        // 验证数组创建语句
        org.tiger.cfg.Statement newArrayStmt = entryBlock.getStatements().get(0);
        assertTrue("First statement should be a new array statement", newArrayStmt instanceof NewArrayStatement);
        NewArrayStatement newArrayStatement = (NewArrayStatement) newArrayStmt;
        assertEquals("Array size should be '5'", "5", newArrayStatement.getSize());
        
        // 验证数组赋值语句
        org.tiger.cfg.Statement arrayAssignStmt = entryBlock.getStatements().get(1);
        assertTrue("Second statement should be an array assignment", arrayAssignStmt instanceof ArrayAssignStatement);
        ArrayAssignStatement arrayAssignStatement = (ArrayAssignStatement) arrayAssignStmt;
        assertEquals("Target should be 'arr'", "arr", arrayAssignStatement.getTarget());
        assertEquals("Index should be '0'", "0", arrayAssignStatement.getIndex());
        assertEquals("Value should be '1'", "1", arrayAssignStatement.getValue());
    }
    
    @Test
    public void testConditionalStatement() {
        // 创建条件语句相关的 AST
        LessThan condition = new LessThan(new IdentifierExp("x"), new IdentifierExp("y"));
        Print thenStmt = new Print(new IntegerLiteral(1));
        Print elseStmt = new Print(new IntegerLiteral(0));
        If ifStmt = new If(condition, thenStmt, elseStmt);
        
        // 创建转换器
        AstToCfgConverter converter = new AstToCfgConverter();
        
        // 访问 AST
        ifStmt.accept(converter);
        
        // 获取生成的 CFG
        Map<String, CFG> cfgs = converter.getMethodCFGs();
        
        // 验证 CFG 是否正确生成
        assertNotNull("CFG map should not be null", cfgs);
        assertFalse("CFG map should not be empty", cfgs.isEmpty());
        
        // 获取主方法的 CFG
        CFG mainCFG = cfgs.get("main");
        assertNotNull("Main method CFG should exist", mainCFG);
        
        // 验证基本块
        BasicBlock entryBlock = mainCFG.getEntryBlock();
        assertNotNull("Entry block should exist", entryBlock);
        
        // 验证条件块
        assertFalse("Entry block should have successors", entryBlock.getSuccessors().isEmpty());
        BasicBlock conditionBlock = entryBlock.getSuccessors().get(0);
        assertNotNull("Condition block should exist", conditionBlock);
        
        // 验证条件语句
        assertFalse("Condition block should have statements", conditionBlock.getStatements().isEmpty());
        org.tiger.cfg.Statement conditionStmt = conditionBlock.getStatements().get(0);
        assertTrue("First statement should be a less than statement", conditionStmt instanceof LessThanStatement);
        
        // 验证分支块
        assertFalse("Condition block should have successors", conditionBlock.getSuccessors().isEmpty());
        assertEquals("Condition block should have two successors", 2, conditionBlock.getSuccessors().size());
    }
} 