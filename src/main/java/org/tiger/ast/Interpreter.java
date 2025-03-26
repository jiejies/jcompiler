package org.tiger.ast;

import java.util.HashMap;
import java.util.Map;

public class Interpreter implements AstVisitor {
    private Environment currentEnv;
    private Map<String, ClassDecl> classes;
    private Object currentResult;
    
    public Interpreter() {
        this.currentEnv = new Environment(null);
        this.classes = new HashMap<>();
    }
    
    public Object execute(Program program) {
        visit(program);
        return currentResult;
    }

    @Override
    public void visit(Program n) {
        // 收集所有类定义
        for (ClassDecl c : n.classes) {
            classes.put(c.name, c);
        }
        // 执行主类的main方法
        n.mainClass.accept(this);
    }

    @Override
    public void visit(MainClass n) {
        // 创建新的环境
        Environment mainEnv = new Environment(currentEnv);
        Environment prevEnv = currentEnv;
        currentEnv = mainEnv;
        
        // 执行main方法中的语句
        n.statement.accept(this);
        
        // 恢复环境
        currentEnv = prevEnv;
    }

    @Override
    public void visit(ClassDecl n) {
        // 类声明在Program访问时已处理
    }

    @Override
    public void visit(MethodDecl n) {
        // 处理局部变量
        for (VarDecl v : n.locals) {
            v.accept(this);
        }
        
        // 执行语句
        for (Statement s : n.statements) {
            s.accept(this);
        }
        
        // 计算返回值
        n.returnExp.accept(this);
    }

    @Override
    public void visit(VarDecl n) {
        // 为变量分配默认值
        if (n.type instanceof IntegerType) {
            currentEnv.put(n.name, 0);
        } else if (n.type instanceof BooleanType) {
            currentEnv.put(n.name, false);
        } else if (n.type instanceof IntArrayType) {
            currentEnv.put(n.name, null);
        } else {
            currentEnv.put(n.name, null);
        }
    }

    @Override
    public void visit(Block n) {
        for (Statement s : n.statements) {
            s.accept(this);
        }
    }

    @Override
    public void visit(If n) {
        n.condition.accept(this);
        boolean condition = (Boolean) currentResult;
        
        if (condition) {
            n.thenStmt.accept(this);
        } else if (n.elseStmt != null) {
            n.elseStmt.accept(this);
        }
    }

    @Override
    public void visit(While n) {
        n.condition.accept(this);
        while ((Boolean) currentResult) {
            n.body.accept(this);
            n.condition.accept(this);
        }
    }

    @Override
    public void visit(Print n) {
        n.exp.accept(this);
        System.out.println(currentResult);
    }

    @Override
    public void visit(Assign n) {
        n.exp.accept(this);
        currentEnv.put(n.id, currentResult);
    }

    @Override
    public void visit(ArrayAssign n) {
        int[] array = (int[]) currentEnv.get(n.id);
        n.index.accept(this);
        int index = (Integer) currentResult;
        n.exp.accept(this);
        int value = (Integer) currentResult;
        array[index] = value;
    }

    @Override
    public void visit(And n) {
        n.left.accept(this);
        boolean left = (Boolean) currentResult;
        
        if (!left) {
            currentResult = false;
            return;
        }
        
        n.right.accept(this);
        boolean right = (Boolean) currentResult;
        currentResult = left && right;
    }

    @Override
    public void visit(LessThan n) {
        n.left.accept(this);
        int left = (Integer) currentResult;
        n.right.accept(this);
        int right = (Integer) currentResult;
        currentResult = left < right;
    }

    @Override
    public void visit(Plus n) {
        n.left.accept(this);
        int left = (Integer) currentResult;
        n.right.accept(this);
        int right = (Integer) currentResult;
        currentResult = left + right;
    }

    @Override
    public void visit(Minus n) {
        n.left.accept(this);
        int left = (Integer) currentResult;
        n.right.accept(this);
        int right = (Integer) currentResult;
        currentResult = left - right;
    }

    @Override
    public void visit(Times n) {
        n.left.accept(this);
        int left = (Integer) currentResult;
        n.right.accept(this);
        int right = (Integer) currentResult;
        currentResult = left * right;
    }

    @Override
    public void visit(ArrayLookup n) {
        n.array.accept(this);
        int[] array = (int[]) currentResult;
        n.index.accept(this);
        int index = (Integer) currentResult;
        currentResult = array[index];
    }

    @Override
    public void visit(ArrayLength n) {
        n.array.accept(this);
        int[] array = (int[]) currentResult;
        currentResult = array.length;
    }

    @Override
    public void visit(Call n) {
        // Get object
        n.object.accept(this);
        Object obj = currentResult;
        
        if (!(obj instanceof MiniJavaObject)) {
            throw new RuntimeException("Attempting to call method on non-object");
        }
        
        MiniJavaObject receiver = (MiniJavaObject) obj;
        ClassDecl classDecl = classes.get(receiver.className);
        
        // Find method
        MethodDecl method = null;
        for (MethodDecl m : classDecl.methods) {
            if (m.name.equals(n.method)) {
                method = m;
                break;
            }
        }
        
        if (method == null) {
            throw new RuntimeException("Method not found: " + n.method);
        }
        
        // Create new environment
        Environment methodEnv = new Environment(currentEnv);
        Environment prevEnv = currentEnv;
        currentEnv = methodEnv;
        
        // Set this
        currentEnv.put("this", receiver);
        
        // Set parameters
        for (int i = 0; i < method.params.size(); i++) {
            VarDecl param = method.params.get(i);
            Expression arg = n.args.get(i);
            arg.accept(this);
            currentEnv.put(param.name, currentResult);
        }
        
        // Handle local variables
        for (VarDecl v : method.locals) {
            v.accept(this);
        }
        
        // Execute statements
        for (Statement s : method.statements) {
            s.accept(this);
        }
        
        // Calculate return value
        method.returnExp.accept(this);
        Object result = currentResult;
        
        // Restore environment
        currentEnv = prevEnv;
        currentResult = result;
    }

    @Override
    public void visit(IntegerLiteral n) {
        currentResult = n.value;
    }

    @Override
    public void visit(True n) {
        currentResult = true;
    }

    @Override
    public void visit(False n) {
        currentResult = false;
    }

    @Override
    public void visit(IdentifierExp n) {
        currentResult = currentEnv.get(n.name);
    }

    @Override
    public void visit(This n) {
        currentResult = currentEnv.get("this");
    }

    @Override
    public void visit(NewArray n) {
        n.size.accept(this);
        int size = (Integer) currentResult;
        currentResult = new int[size];
    }

    @Override
    public void visit(NewObject n) {
        ClassDecl classDecl = classes.get(n.className);
        if (classDecl == null) {
            throw new RuntimeException("Class not found: " + n.className);
        }
        currentResult = new MiniJavaObject(n.className);
    }

    @Override
    public void visit(Not n) {
        n.exp.accept(this);
        currentResult = !(Boolean) currentResult;
    }

    // 用于表示MiniJava对象的辅助类
    private static class MiniJavaObject {
        String className;
        Map<String, Object> fields;

        MiniJavaObject(String className) {
            this.className = className;
            this.fields = new HashMap<>();
        }
    }

    @Override
    public void visit(IntArrayType n) {}

    @Override
    public void visit(BooleanType n) {}

    @Override
    public void visit(IntegerType n) {}

    @Override
    public void visit(IdentifierType n) {}
} 