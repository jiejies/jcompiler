package org.tiger.ast;

import java.util.List;
import java.util.ArrayList;

// AST基类
public abstract class Ast {
    public abstract void accept(AstVisitor visitor);
}

// 程序节点
class Program extends Ast {
    public MainClass mainClass;
    public List<ClassDecl> classes;

    public Program(MainClass mainClass, List<ClassDecl> classes) {
        this.mainClass = mainClass;
        this.classes = classes;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 主类节点
class MainClass extends Ast {
    public String className;
    public String argsName;
    public Statement statement;

    public MainClass(String className, String argsName, Statement statement) {
        this.className = className;
        this.argsName = argsName;
        this.statement = statement;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 类声明节点
class ClassDecl extends Ast {
    public String name;
    public String parent;  // 可能为null，表示没有父类
    public List<VarDecl> fields;
    public List<MethodDecl> methods;

    public ClassDecl(String name, String parent, List<VarDecl> fields, List<MethodDecl> methods) {
        this.name = name;
        this.parent = parent;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 变量声明节点
class VarDecl extends Ast {
    public Type type;
    public String name;

    public VarDecl(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 方法声明节点
class MethodDecl extends Ast {
    public Type returnType;
    public String name;
    public List<VarDecl> params;
    public List<VarDecl> locals;
    public List<Statement> statements;
    public Expression returnExp;

    public MethodDecl(Type returnType, String name, List<VarDecl> params,
                     List<VarDecl> locals, List<Statement> statements, Expression returnExp) {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.locals = locals;
        this.statements = statements;
        this.returnExp = returnExp;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 类型节点
abstract class Type extends Ast {}

class IntArrayType extends Type {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class BooleanType extends Type {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class IntegerType extends Type {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class IdentifierType extends Type {
    public String name;

    public IdentifierType(String name) {
        this.name = name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 语句节点
abstract class Statement extends Ast {}

class Block extends Statement {
    public List<Statement> statements;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class If extends Statement {
    public Expression condition;
    public Statement thenStmt;
    public Statement elseStmt;

    public If(Expression condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class While extends Statement {
    public Expression condition;
    public Statement body;

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Print extends Statement {
    public Expression exp;

    public Print(Expression exp) {
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Assign extends Statement {
    public String id;
    public Expression exp;

    public Assign(String id, Expression exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class ArrayAssign extends Statement {
    public String id;
    public Expression index;
    public Expression exp;

    public ArrayAssign(String id, Expression index, Expression exp) {
        this.id = id;
        this.index = index;
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

// 表达式节点
abstract class Expression extends Ast {}

class And extends Expression {
    public Expression left, right;

    public And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class LessThan extends Expression {
    public Expression left, right;

    public LessThan(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Plus extends Expression {
    public Expression left, right;

    public Plus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Minus extends Expression {
    public Expression left, right;

    public Minus(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Times extends Expression {
    public Expression left, right;

    public Times(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class ArrayLookup extends Expression {
    public Expression array;
    public Expression index;

    public ArrayLookup(Expression array, Expression index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class ArrayLength extends Expression {
    public Expression array;

    public ArrayLength(Expression array) {
        this.array = array;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Call extends Expression {
    public Expression object;
    public String method;
    public List<Expression> args;

    public Call(Expression object, String method, List<Expression> args) {
        this.object = object;
        this.method = method;
        this.args = args;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class IntegerLiteral extends Expression {
    public int value;

    public IntegerLiteral(int value) {
        this.value = value;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class True extends Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class False extends Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class IdentifierExp extends Expression {
    public String name;

    public IdentifierExp(String name) {
        this.name = name;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class This extends Expression {
    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class NewArray extends Expression {
    public Expression size;

    public NewArray(Expression size) {
        this.size = size;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class NewObject extends Expression {
    public String className;

    public NewObject(String className) {
        this.className = className;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
}

class Not extends Expression {
    public Expression exp;

    public Not(Expression exp) {
        this.exp = exp;
    }

    @Override
    public void accept(AstVisitor visitor) {
        visitor.visit(this);
    }
} 