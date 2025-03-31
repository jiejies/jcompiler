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
    private final Map<String, Scope> classScopes;  // 存储类的作用域
    
    public SemanticAnalyzer() {
        this.currentScope = new Scope(null); // 创建全局作用域
        this.errors = new ArrayList<>();
        this.methodDeclarations = new HashMap<>();
        this.classScopes = new HashMap<>();
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
        
        // 处理字面量
        if (expr.primary() != null && expr.primary().literal() != null) {
            TigerParser.LiteralContext literal = expr.primary().literal();
            if (literal.DECIMAL_LITERAL() != null || 
                literal.HEX_LITERAL() != null || 
                literal.OCT_LITERAL() != null || 
                literal.BINARY_LITERAL() != null) {
                return "int";
            } else if (literal.BOOLEAN_LITERAL() != null) {
                return "boolean";
            } else if (literal.STRING_LITERAL() != null) {
                return "String";
            }
        }
        
        // 处理变量引用
        if (expr.primary() != null && expr.primary().IDENTIFIER() != null) {
            String varName = expr.primary().IDENTIFIER().getText();
            Object varDecl = currentScope.resolve(varName);
            
            if (varDecl instanceof TigerParser.LocalVariableDeclarationContext) {
                return ((TigerParser.LocalVariableDeclarationContext) varDecl).type().getText();
            } else if (varDecl instanceof TigerParser.FormalParameterContext) {
                return ((TigerParser.FormalParameterContext) varDecl).type().getText();
            } else if (varDecl instanceof TigerParser.FieldDeclarationContext) {
                return ((TigerParser.FieldDeclarationContext) varDecl).type().getText();
            }
        }
        
        // 处理方法调用
        if (expr.methodCall() != null) {
            String methodName = expr.methodCall().IDENTIFIER().getText();
            TigerParser.MethodDeclarationContext methodDecl = methodDeclarations.get(methodName);
            if (methodDecl != null) {
                return methodDecl.typeTypeOrVoid().getText();
            }
        }
        
        // 处理二元运算
        if (expr.getChildCount() == 3 && expr.getChild(1) != null) {
            String operator = expr.getChild(1).getText();
            String leftType = getExpressionType((TigerParser.ExpressionContext)expr.getChild(0));
            String rightType = getExpressionType((TigerParser.ExpressionContext)expr.getChild(2));
            
            // 算术运算符
            if (operator.matches("[+\\-*/]")) {
                if ("int".equals(leftType) && "int".equals(rightType)) {
                    return "int";
                }
            }
            // 比较运算符
            else if (operator.matches("==|!=|<|>|<=|>=")) {
                if (isTypeCompatible(leftType, rightType)) {
                    return "boolean";
                }
            }
            // 逻辑运算符
            else if (operator.matches("&&|\\|\\|")) {
                if ("boolean".equals(leftType) && "boolean".equals(rightType)) {
                    return "boolean";
                }
            }
        }
        
        return null;
    }
    
    @Override
    public Void visitClassDeclaration(TigerParser.ClassDeclarationContext ctx) {
        String className = ctx.IDENTIFIER().getText();
        
        // 检查类名是否已定义
        if (currentScope.isDefined(className)) {
            addError(ctx.IDENTIFIER().getSymbol(), "Class '" + className + "' is already defined");
            return null;
        }
        
        // 创建类作用域
        Scope classScope = new Scope(currentScope);
        classScopes.put(className, classScope);
        
        // 处理继承
        if (ctx.type() != null) {  // 如果有extends子句
            String parentClassName = ctx.type().getText();
            Scope parentScope = classScopes.get(parentClassName);
            if (parentScope == null) {
                addError(ctx.type().getStart(), "Parent class '" + parentClassName + "' is not defined");
            } else {
                // 将父类作用域设置为当前类作用域的父作用域
                classScope = new Scope(parentScope);
                classScopes.put(className, classScope);
            }
        }
        
        // 在当前作用域中记录类定义
        currentScope.define(className, ctx);
        
        // 切换到类作用域
        Scope previousScope = currentScope;
        currentScope = classScope;
        
        // 处理类成员
        if (ctx.classBody() != null) {
            // 先处理字段
            for (TigerParser.ClassBodyDeclarationContext member : ctx.classBody().classBodyDeclaration()) {
                if (member.memberDeclaration() != null && 
                    member.memberDeclaration().fieldDeclaration() != null) {
                    TigerParser.FieldDeclarationContext field = member.memberDeclaration().fieldDeclaration();
                    String fieldType = field.type().getText();
                    for (TigerParser.VariableDeclaratorContext declarator : field.variableDeclarators().variableDeclarator()) {
                        String fieldName = declarator.variableDeclaratorId().IDENTIFIER().getText();
                        if (classScope.isDefined(fieldName)) {
                            addError(declarator.variableDeclaratorId().IDENTIFIER().getSymbol(),
                                "Field '" + fieldName + "' is already defined in class '" + className + "'");
                        } else {
                            classScope.define(fieldName, field);
                        }
                    }
                }
            }
            
            // 再处理方法
            for (TigerParser.ClassBodyDeclarationContext member : ctx.classBody().classBodyDeclaration()) {
                if (member.memberDeclaration() != null && 
                    member.memberDeclaration().methodDeclaration() != null) {
                    visit(member.memberDeclaration().methodDeclaration());
                }
            }
        }
        
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
        
        // 创建方法作用域，继承类作用域
        Scope methodScope = new Scope(currentScope);
        
        // 先处理参数，将参数添加到方法作用域中
        if (ctx.formalParameters() != null && ctx.formalParameters().formalParameterList() != null) {
            for (TigerParser.FormalParameterContext param : ctx.formalParameters().formalParameterList().formalParameter()) {
                String paramName = param.variableDeclaratorId().IDENTIFIER().getText();
                if (methodScope.isDefined(paramName)) {
                    addError(param.variableDeclaratorId().IDENTIFIER().getSymbol(),
                        "Parameter '" + paramName + "' is already defined");
                } else {
                    methodScope.define(paramName, param);
                }
            }
        }
        
        // 在当前作用域中定义方法
        currentScope.define(methodName, ctx);
        
        // 切换到方法作用域
        Scope previousScope = currentScope;
        currentScope = methodScope;
        
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
            
            // 检查变量是否已定义（包括类字段、方法参数和局部变量）
            Object varDecl = currentScope.resolve(varName);
            if (varDecl == null) {
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