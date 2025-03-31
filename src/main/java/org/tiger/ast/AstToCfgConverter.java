package org.tiger.ast;

import org.tiger.cfg.BasicBlock;
import org.tiger.cfg.CFG;
import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;
import org.tiger.cfg.statements.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class AstToCfgConverter implements AstVisitor {
    private CFG currentCFG;
    private BasicBlock currentBlock;
    private final Map<String, CFG> methodCFGs;
    private int blockCounter;
    private boolean isInMethod;

    public AstToCfgConverter() {
        this.methodCFGs = new HashMap<>();
        this.blockCounter = 0;
        this.isInMethod = false;
    }

    private BasicBlock createBlock() {
        return new BasicBlock("B" + blockCounter++);
    }

    private void ensureCFGExists() {
        if (currentCFG == null) {
            currentCFG = new CFG("main");
            methodCFGs.put("main", currentCFG);
            // 确保有一个入口块
            currentBlock = createBlock();
            currentCFG.setEntryBlock(currentBlock);
            currentCFG.addBlock(currentBlock);
        }
    }

    @Override
    public void visit(Program n) {
        // 处理主类
        n.mainClass.accept(this);
        // 处理其他类
        for (ClassDecl classDecl : n.classes) {
            classDecl.accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        currentCFG = new CFG(n.className + "_main");
        currentBlock = createBlock();
        currentCFG.setEntryBlock(currentBlock);
        currentCFG.addBlock(currentBlock);
        isInMethod = true;
        
        // 处理主类中的语句
        n.statement.accept(this);
        
        // 设置出口块
        currentCFG.setExitBlock(currentBlock);
        methodCFGs.put(currentCFG.getMethodName(), currentCFG);
        isInMethod = false;
    }

    @Override
    public void visit(ClassDecl n) {
        // 处理类中的每个方法
        for (MethodDecl method : n.methods) {
            method.accept(this);
        }
    }

    @Override
    public void visit(MethodDecl n) {
        currentCFG = new CFG(n.name);
        currentBlock = createBlock();
        currentCFG.setEntryBlock(currentBlock);
        currentCFG.addBlock(currentBlock);
        isInMethod = true;
        
        // 处理方法体
        for (org.tiger.ast.Statement stmt : n.statements) {
            stmt.accept(this);
        }
        
        // 处理返回语句
        if (n.returnExp != null) {
            Statement stmt = new ReturnStatement(convertExpression(n.returnExp));
            currentBlock.addStatement(stmt);
        }
        
        // 设置出口块
        currentCFG.setExitBlock(currentBlock);
        methodCFGs.put(currentCFG.getMethodName(), currentCFG);
        isInMethod = false;
    }

    @Override
    public void visit(Block n) {
        for (org.tiger.ast.Statement stmt : n.statements) {
            stmt.accept(this);
        }
    }

    @Override
    public void visit(If n) {
        ensureCFGExists();
        
        // 1. 创建条件块
        BasicBlock conditionBlock = createBlock();
        currentCFG.addBlock(conditionBlock);
        conditionBlock.addStatement(convertCondition(n.condition));
        
        // 2. 创建真分支块
        BasicBlock trueBlock = createBlock();
        currentCFG.addBlock(trueBlock);
        BasicBlock savedCurrentBlock = currentBlock;  // 保存当前块
        currentBlock = trueBlock;
        n.thenStmt.accept(this);
        
        // 3. 创建假分支块
        BasicBlock falseBlock = createBlock();
        currentCFG.addBlock(falseBlock);
        currentBlock = falseBlock;
        if (n.elseStmt != null) {
            n.elseStmt.accept(this);
        }
        
        // 4. 创建合并块
        BasicBlock mergeBlock = createBlock();
        currentCFG.addBlock(mergeBlock);
        
        // 5. 构建控制流
        savedCurrentBlock.addSuccessor(conditionBlock);  // 使用保存的块连接条件块
        conditionBlock.addSuccessor(trueBlock);
        conditionBlock.addSuccessor(falseBlock);
        trueBlock.addSuccessor(mergeBlock);
        falseBlock.addSuccessor(mergeBlock);
        
        currentBlock = mergeBlock;
    }

    @Override
    public void visit(While n) {
        ensureCFGExists();
        
        // 1. 创建循环头块
        BasicBlock headerBlock = createBlock();
        currentCFG.addBlock(headerBlock);
        
        // 2. 创建条件块
        BasicBlock conditionBlock = createBlock();
        currentCFG.addBlock(conditionBlock);
        conditionBlock.addStatement(convertCondition(n.condition));
        
        // 3. 创建循环体块
        BasicBlock bodyBlock = createBlock();
        currentCFG.addBlock(bodyBlock);
        currentBlock = bodyBlock;
        n.body.accept(this);
        
        // 4. 构建控制流
        headerBlock.addSuccessor(conditionBlock);
        conditionBlock.addSuccessor(bodyBlock);
        bodyBlock.addSuccessor(headerBlock);
        
        currentBlock = conditionBlock;
    }

    @Override
    public void visit(Assign n) {
        ensureCFGExists();
        
        // 将 AST 的赋值语句转换为 CFG 的赋值语句
        String target = n.id;
        String source = convertExpression(n.exp);
        Statement stmt = new AssignStatement(target, source);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Print n) {
        ensureCFGExists();
        
        // 将 AST 的打印语句转换为 CFG 的打印语句
        String source = convertExpression(n.exp);
        Statement stmt = new PrintStatement(source);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Call n) {
        ensureCFGExists();
        
        // 将 AST 的方法调用转换为 CFG 的方法调用
        List<String> args = new ArrayList<>();
        for (Expression arg : n.args) {
            args.add(convertExpression(arg));
        }
        String target = convertExpression(n.object);
        Statement stmt = new CallStatement(target, n.method, args);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(NewObject n) {
        ensureCFGExists();
        
        // 将 AST 的对象创建转换为 CFG 的对象创建
        Statement stmt = new NewObjectStatement(n.className);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(NewArray n) {
        ensureCFGExists();
        
        // 将 AST 的数组创建转换为 CFG 的数组创建
        String size = convertExpression(n.size);
        Statement stmt = new NewArrayStatement(size);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(ArrayAssign n) {
        ensureCFGExists();
        
        // 将 AST 的数组赋值转换为 CFG 的数组赋值
        String target = n.id;
        String index = convertExpression(n.index);
        String value = convertExpression(n.exp);
        Statement stmt = new ArrayAssignStatement(target, index, value);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(ArrayLookup n) {
        ensureCFGExists();
        
        // 将 AST 的数组访问转换为 CFG 的数组访问
        String array = convertExpression(n.array);
        String index = convertExpression(n.index);
        Statement stmt = new ArrayLookupStatement(array, index);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(ArrayLength n) {
        ensureCFGExists();
        
        // 将 AST 的数组长度访问转换为 CFG 的数组长度访问
        String array = convertExpression(n.array);
        Statement stmt = new ArrayLengthStatement(array);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Plus n) {
        ensureCFGExists();
        
        // 将 AST 的加法表达式转换为 CFG 的加法语句
        String left = convertExpression(n.left);
        String right = convertExpression(n.right);
        Statement stmt = new PlusStatement(left, right);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Minus n) {
        ensureCFGExists();
        
        // 将 AST 的减法表达式转换为 CFG 的减法语句
        String left = convertExpression(n.left);
        String right = convertExpression(n.right);
        Statement stmt = new MinusStatement(left, right);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Times n) {
        ensureCFGExists();
        
        // 将 AST 的乘法表达式转换为 CFG 的乘法语句
        String left = convertExpression(n.left);
        String right = convertExpression(n.right);
        Statement stmt = new TimesStatement(left, right);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(LessThan n) {
        ensureCFGExists();
        
        // 将 AST 的小于表达式转换为 CFG 的小于语句
        String left = convertExpression(n.left);
        String right = convertExpression(n.right);
        Statement stmt = new LessThanStatement(left, right);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(And n) {
        ensureCFGExists();
        
        // 将 AST 的与表达式转换为 CFG 的与语句
        String left = convertExpression(n.left);
        String right = convertExpression(n.right);
        Statement stmt = new AndStatement(left, right);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(Not n) {
        ensureCFGExists();
        
        // 将 AST 的非表达式转换为 CFG 的非语句
        String operand = convertExpression(n.exp);
        Statement stmt = new NotStatement(operand);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(IdentifierExp n) {
        ensureCFGExists();
        
        // 将 AST 的标识符表达式转换为 CFG 的标识符语句
        Statement stmt = new IdentifierStatement(n.name);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(IntegerLiteral n) {
        ensureCFGExists();
        
        // 将 AST 的整数字面量转换为 CFG 的整数字面量语句
        Statement stmt = new IntegerLiteralStatement(n.value);
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(True n) {
        ensureCFGExists();
        
        // 将 AST 的 true 字面量转换为 CFG 的 true 语句
        Statement stmt = new TrueStatement();
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(False n) {
        ensureCFGExists();
        
        // 将 AST 的 false 字面量转换为 CFG 的 false 语句
        Statement stmt = new FalseStatement();
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(This n) {
        ensureCFGExists();
        
        // 将 AST 的 this 表达式转换为 CFG 的 this 语句
        Statement stmt = new ThisStatement();
        currentBlock.addStatement(stmt);
    }

    @Override
    public void visit(IdentifierType n) {
        // 类型节点不需要生成 CFG
    }

    @Override
    public void visit(IntegerType n) {
        // 类型节点不需要生成 CFG
    }

    @Override
    public void visit(BooleanType n) {
        // 类型节点不需要生成 CFG
    }

    @Override
    public void visit(IntArrayType n) {
        // 类型节点不需要生成 CFG
    }

    @Override
    public void visit(VarDecl n) {
        // 变量声明节点不需要生成 CFG
    }

    private Statement convertCondition(Expression condition) {
        // 根据条件表达式类型创建对应的条件语句
        if (condition instanceof LessThan) {
            LessThan lt = (LessThan) condition;
            String left = convertExpression(lt.left);
            String right = convertExpression(lt.right);
            return new LessThanStatement(left, right);
        }
        // ... 处理其他类型的条件
        return null;
    }

    private String convertExpression(Expression exp) {
        if (exp instanceof IdentifierExp) {
            return ((IdentifierExp) exp).name;
        } else if (exp instanceof IntegerLiteral) {
            return String.valueOf(((IntegerLiteral) exp).value);
        } else if (exp instanceof True) {
            return "true";
        } else if (exp instanceof False) {
            return "false";
        } else if (exp instanceof This) {
            return "this";
        } else if (exp instanceof Call) {
            Call call = (Call) exp;
            List<String> args = new ArrayList<>();
            for (Expression arg : call.args) {
                args.add(convertExpression(arg));
            }
            String target = convertExpression(call.object);
            // 生成临时变量来存储方法调用的结果
            String tempVar = "_t" + (blockCounter++);
            Statement stmt = new CallStatement(target, call.method, args);
            currentBlock.addStatement(stmt);
            return tempVar;
        }
        return null;
    }

    public Map<String, CFG> getMethodCFGs() {
        return methodCFGs;
    }
} 