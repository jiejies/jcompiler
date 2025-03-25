package org.tiger.semantic;

import org.antlr.v4.runtime.Token;
import org.tiger.TigerBaseVisitor;
import org.tiger.TigerParser;
import java.util.ArrayList;
import java.util.List;

/**
 * 语义分析器
 */
public class SemanticAnalyzer extends TigerBaseVisitor<Void> {
    private Scope currentScope;
    private final List<String> errors;
    
    public SemanticAnalyzer() {
        this.currentScope = new Scope(null); // 创建全局作用域
        this.errors = new ArrayList<>();
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    
    @Override
    public Void visitClassDeclaration(TigerParser.ClassDeclarationContext ctx) {
        // 获取类名
        String className = ctx.IDENTIFIER().getText();
        
        // 检查类名是否已定义
        if (currentScope.isDefined(className)) {
            addError(ctx.IDENTIFIER().getSymbol(), "Class '" + className + "' is already defined");
            return null;
        }
        
        // 创建新的作用域
        Scope classScope = new Scope(currentScope);
        currentScope.define(className, ctx); // 在当前作用域中记录类定义
        
        // 切换到类作用域
        Scope previousScope = currentScope;
        currentScope = classScope;
        
        // 访问类成员
        visitChildren(ctx);
        
        // 恢复作用域
        currentScope = previousScope;
        return null;
    }
    
    @Override
    public Void visitMethodDeclaration(TigerParser.MethodDeclarationContext ctx) {
        String methodName = ctx.IDENTIFIER().getText();
        
        // 检查方法是否已定义
        if (currentScope.isDefined(methodName)) {
            addError(ctx.IDENTIFIER().getSymbol(), "Method '" + methodName + "' is already defined");
            return null;
        }
        
        // 创建方法作用域
        Scope methodScope = new Scope(currentScope);
        currentScope.define(methodName, ctx);
        
        // 切换到方法作用域
        Scope previousScope = currentScope;
        currentScope = methodScope;
        
        // 处理参数
        if (ctx.formalParameters() != null) {
            visitFormalParameters(ctx.formalParameters());
        }
        
        // 访问方法体
        if (ctx.methodBody() != null) {
            visit(ctx.methodBody());
        }
        
        // 恢复作用域
        currentScope = previousScope;
        return null;
    }
    
    @Override
    public Void visitLocalVariableDeclaration(TigerParser.LocalVariableDeclarationContext ctx) {
        // 处理变量声明
        for (TigerParser.VariableDeclaratorContext declarator : ctx.variableDeclarators().variableDeclarator()) {
            String varName = declarator.variableDeclaratorId().IDENTIFIER().getText();
            
            // 检查变量是否已在当前作用域中定义
            if (currentScope.isDefined(varName)) {
                addError(declarator.variableDeclaratorId().IDENTIFIER().getSymbol(), 
                        "Variable '" + varName + "' is already defined in current scope");
                continue;
            }
            
            // 在当前作用域中定义变量
            currentScope.define(varName, declarator);
        }
        return null;
    }
    
    private void addError(Token token, String message) {
        String error = String.format("line %d:%d %s", 
                                   token.getLine(), 
                                   token.getCharPositionInLine(), 
                                   message);
        errors.add(error);
    }
} 