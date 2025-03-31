package org.tiger.ast;

import org.antlr.v4.runtime.tree.ParseTree;
import org.tiger.TigerBaseVisitor;
import org.tiger.TigerParser;
import java.util.ArrayList;
import java.util.List;

public class ParseTreeToAstConverter extends TigerBaseVisitor<Ast> {
    
    @Override
    public Ast visitCompilationUnit(TigerParser.CompilationUnitContext ctx) {
        MainClass mainClass = null;
        List<ClassDecl> classes = new ArrayList<>();
        
        // 处理所有类声明
        for (TigerParser.TypeDeclarationContext typeDecl : ctx.typeDeclaration()) {
            if (typeDecl.classDeclaration() != null) {
                ClassDecl classDecl = (ClassDecl) visit(typeDecl.classDeclaration());
                if (classDecl.name.endsWith("_main")) {
                    // 如果是主类
                    mainClass = new MainClass(
                        classDecl.name,
                        "args",  // 默认参数名
                        new Block(classDecl.methods.get(0).statements)  // 使用第一个方法的语句作为主方法
                    );
                } else {
                    classes.add(classDecl);
                }
            }
        }
        
        // 确保主类存在
        if (mainClass == null) {
            throw new RuntimeException("没有找到主类");
        }
        
        return new Program(mainClass, classes);
    }
    
    @Override
    public Ast visitClassDeclaration(TigerParser.ClassDeclarationContext ctx) {
        String className = ctx.IDENTIFIER().getText();
        String parentClass = null;
        List<VarDecl> fields = new ArrayList<>();
        List<MethodDecl> methods = new ArrayList<>();
        
        // 处理继承
        if (ctx.getChildCount() > 2 && ctx.getChild(1).getText().equals("extends")) {
            parentClass = ctx.type().getText();
        }
        
        // 处理类体
        if (ctx.classBody() != null) {
            for (TigerParser.ClassBodyDeclarationContext member : ctx.classBody().classBodyDeclaration()) {
                if (member.memberDeclaration() != null) {
                    if (member.memberDeclaration().fieldDeclaration() != null) {
                        // 处理字段
                        TigerParser.FieldDeclarationContext field = member.memberDeclaration().fieldDeclaration();
                        Type type = convertType(field.type());
                        for (TigerParser.VariableDeclaratorContext declarator : field.variableDeclarators().variableDeclarator()) {
                            String fieldName = declarator.variableDeclaratorId().IDENTIFIER().getText();
                            fields.add(new VarDecl(type, fieldName));
                        }
                    } else if (member.memberDeclaration().methodDeclaration() != null) {
                        // 处理方法
                        TigerParser.MethodDeclarationContext method = member.memberDeclaration().methodDeclaration();
                        Type returnType = convertType(method.typeTypeOrVoid().type());
                        String methodName = className + "." + method.IDENTIFIER().getText();  // 添加类名前缀
                        
                        // 处理参数
                        List<VarDecl> params = new ArrayList<>();
                        if (method.formalParameters() != null && method.formalParameters().formalParameterList() != null) {
                            for (TigerParser.FormalParameterContext param : method.formalParameters().formalParameterList().formalParameter()) {
                                Type paramType = convertType(param.type());
                                String paramName = param.variableDeclaratorId().IDENTIFIER().getText();
                                params.add(new VarDecl(paramType, paramName));
                            }
                        }
                        
                        // 处理局部变量
                        List<VarDecl> locals = new ArrayList<>();
                        
                        // 处理语句
                        List<Statement> statements = new ArrayList<>();
                        if (method.methodBody() != null) {
                            for (TigerParser.BlockStatementContext stmt : method.methodBody().block().blockStatement()) {
                                if (stmt.statement() != null) {
                                    Statement astStmt = (Statement) visit(stmt.statement());
                                    if (astStmt != null) {  // 确保语句不为空
                                        statements.add(astStmt);
                                    }
                                }
                            }
                        }
                        
                        // 处理返回表达式
                        Expression returnExp = null;
                        if (method.methodBody() != null && method.methodBody().block().blockStatement() != null) {
                            for (TigerParser.BlockStatementContext stmt : method.methodBody().block().blockStatement()) {
                                if (stmt.statement() != null && stmt.statement().getChildCount() > 0 && 
                                    stmt.statement().getChild(0).getText().equals("return")) {
                                    returnExp = (Expression) visit(stmt.statement().expression());
                                    break;
                                }
                            }
                        }
                        
                        methods.add(new MethodDecl(returnType, methodName, params, locals, statements, returnExp));
                    }
                }
            }
        }
        
        return new ClassDecl(className, parentClass, fields, methods);
    }
    
    private Type convertType(TigerParser.TypeContext ctx) {
        if (ctx.primitiveType() != null) {
            String typeName = ctx.primitiveType().getText();
            switch (typeName) {
                case "int":
                    return new IntegerType();
                case "boolean":
                    return new BooleanType();
                default:
                    return new IdentifierType(typeName);
            }
        } else if (ctx.classType() != null) {
            return new IdentifierType(ctx.classType().getText());
        }
        return null;
    }
    
    @Override
    public Ast visitStatement(TigerParser.StatementContext ctx) {
        if (ctx.block() != null) {
            List<Statement> statements = new ArrayList<>();
            for (TigerParser.BlockStatementContext stmt : ctx.block().blockStatement()) {
                if (stmt.statement() != null) {
                    Statement astStmt = (Statement) visit(stmt.statement());
                    if (astStmt != null) {
                        statements.add(astStmt);
                    }
                }
            }
            return new Block(statements);
        } else if (ctx.expression() != null) {
            Expression expr = (Expression) visit(ctx.expression());
            // 所有表达式都包装在 Print 语句中
            return new Print(expr);
        } else if (ctx.getChild(0).getText().equals("while")) {
            Expression condition = (Expression) visit(ctx.parExpression());
            Statement body = (Statement) visit(ctx.statement(0));
            return new While(condition, body);
        } else if (ctx.getChild(0).getText().equals("if")) {
            Expression condition = (Expression) visit(ctx.parExpression());
            Statement thenStmt = (Statement) visit(ctx.statement(0));
            Statement elseStmt = ctx.statement(1) != null ?
                (Statement) visit(ctx.statement(1)) : null;
            return new If(condition, thenStmt, elseStmt);
        }
        return null;
    }
    
    @Override
    public Ast visitExpression(TigerParser.ExpressionContext ctx) {
        if (ctx.primary() != null) {
            return visit(ctx.primary());
        } else if (ctx.methodCall() != null) {
            TigerParser.MethodCallContext call = ctx.methodCall();
            Expression object = new This();  // 默认使用this
            String methodName = call.IDENTIFIER().getText();
            List<Expression> args = new ArrayList<>();
            
            if (call.arguments() != null && call.arguments().expressionList() != null) {
                for (TigerParser.ExpressionContext arg : call.arguments().expressionList().expression()) {
                    args.add((Expression) visit(arg));
                }
            }
            
            return new Call(object, methodName, args);
        }
        return null;
    }
    
    @Override
    public Ast visitPrimary(TigerParser.PrimaryContext ctx) {
        if (ctx.literal() != null) {
            TigerParser.LiteralContext literal = ctx.literal();
            if (literal.DECIMAL_LITERAL() != null) {
                return new IntegerLiteral(Integer.parseInt(literal.DECIMAL_LITERAL().getText()));
            } else if (literal.BOOLEAN_LITERAL() != null) {
                return literal.BOOLEAN_LITERAL().getText().equals("true") ? new True() : new False();
            }
        } else if (ctx.IDENTIFIER() != null) {
            return new IdentifierExp(ctx.IDENTIFIER().getText());
        } else if (ctx.THIS() != null) {
            return new This();
        }
        return null;
    }
} 