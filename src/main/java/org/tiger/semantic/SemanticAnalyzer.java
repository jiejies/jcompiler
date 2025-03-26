package org.tiger.semantic;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.tiger.TigerBaseVisitor;
import org.tiger.TigerParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 语义分析器
 */
public class SemanticAnalyzer extends TigerBaseVisitor<Void> {
    private Scope currentScope;
    private final List<String> errors;
    private final Map<String, TigerParser.MethodDeclarationContext> methodDeclarations;
    
    public SemanticAnalyzer() {
        this.currentScope = new Scope(null); // 创建全局作用域
        this.errors = new ArrayList<>();
        this.methodDeclarations = new HashMap<>();
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    // 类型检查辅助方法
    private boolean isTypeCompatible(String type1, String type2) {
        if (type1 == null || type2 == null) return false;
        return type1.equals(type2);
    }

    // 获取表达式的类型
    private String getExpressionType(TigerParser.ExpressionContext expr) {
        if (expr == null) return null;
        
        if (expr.primary() != null) {
            if (expr.primary().literal() != null) {
                // 处理字面量
                String literal = expr.primary().literal().getText();
                if (literal.matches("\\d+")) {
                    return "int";
                } else if (literal.equals("true") || literal.equals("false")) {
                    return "boolean";
                }
            } else if (expr.primary().IDENTIFIER() != null) {
                // 处理变量引用
                String varName = expr.primary().IDENTIFIER().getText();
                Object symbol = currentScope.resolve(varName);
                if (symbol instanceof TigerParser.LocalVariableDeclarationContext) {
                    TigerParser.LocalVariableDeclarationContext varDecl = 
                        (TigerParser.LocalVariableDeclarationContext) symbol;
                    return varDecl.type().getText();
                }
            }
        } else if (expr.methodCall() != null) {
            // 处理方法调用
            String methodName = expr.methodCall().IDENTIFIER().getText();
            TigerParser.MethodDeclarationContext methodDecl = methodDeclarations.get(methodName);
            if (methodDecl != null) {
                return methodDecl.typeTypeOrVoid().getText();
            }
        }
        
        return null;
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
        
        // 记录方法声明，用于后续的方法调用检查
        methodDeclarations.put(methodName, ctx);
        
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
            currentScope.define(varName, ctx);
        }
        return null;
    }

    @Override
    public Void visitMethodCall(TigerParser.MethodCallContext ctx) {
        String methodName = ctx.IDENTIFIER().getText();
        TigerParser.MethodDeclarationContext methodDecl = methodDeclarations.get(methodName);
        
        if (methodDecl == null) {
            addError(ctx.IDENTIFIER().getSymbol(), "Method '" + methodName + "' is not defined");
            return null;
        }
        
        // 检查参数数量
        int expectedParams = methodDecl.formalParameters().formalParameterList() != null ? 
            methodDecl.formalParameters().formalParameterList().formalParameter().size() : 0;
        int actualParams = ctx.arguments().expressionList() != null ? 
            ctx.arguments().expressionList().expression().size() : 0;
            
        if (expectedParams != actualParams) {
            addError(ctx.IDENTIFIER().getSymbol(), 
                "Method '" + methodName + "' expects " + expectedParams + 
                " parameters, but got " + actualParams);
            return null;
        }
        
        // 检查参数类型
        if (ctx.arguments().expressionList() != null && 
            methodDecl.formalParameters().formalParameterList() != null) {
            List<TigerParser.ExpressionContext> actualArgs = ctx.arguments().expressionList().expression();
            List<TigerParser.FormalParameterContext> formalParams = 
                methodDecl.formalParameters().formalParameterList().formalParameter();
            
            for (int i = 0; i < actualArgs.size(); i++) {
                String expectedType = formalParams.get(i).type().getText();
                String actualType = getExpressionType(actualArgs.get(i));
                
                if (!isTypeCompatible(actualType, expectedType)) {
                    addError(actualArgs.get(i).getStart(),
                        "Type mismatch in parameter " + (i + 1) + 
                        ": expected " + expectedType + 
                        ", got " + (actualType != null ? actualType : "unknown"));
                }
            }
        }
        
        return null;
    }

    @Override
    public Void visitStatement(TigerParser.StatementContext ctx) {
        // 检查返回语句的类型
        if (ctx.expression() != null && ctx.getChild(0).getText().equals("return")) {
            TigerParser.MethodDeclarationContext methodCtx = findParentMethod(ctx);
            if (methodCtx != null) {
                String expectedType = methodCtx.typeTypeOrVoid().getText();
                String actualType = getExpressionType(ctx.expression());
                if (!isTypeCompatible(actualType, expectedType)) {
                    addError(ctx.expression().getStart(),
                        "Return type mismatch: expected " + expectedType + 
                        ", got " + (actualType != null ? actualType : "unknown"));
                }
            }
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitPrimary(TigerParser.PrimaryContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            String varName = ctx.IDENTIFIER().getText();
            
            // 检查变量是否已定义
            if (!currentScope.isDefined(varName)) {
                addError(ctx.IDENTIFIER().getSymbol(), 
                    "Variable '" + varName + "' is used before declaration");
            }
        }
        return visitChildren(ctx);
    }
    
    private TigerParser.MethodDeclarationContext findParentMethod(TigerParser.StatementContext ctx) {
        ParseTree parent = ctx.getParent();
        while (parent != null) {
            if (parent instanceof TigerParser.MethodDeclarationContext) {
                return (TigerParser.MethodDeclarationContext) parent;
            }
            parent = parent.getParent();
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