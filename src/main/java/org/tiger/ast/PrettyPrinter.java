package org.tiger.ast;

public class PrettyPrinter implements AstVisitor {
    private int indent = 0;
    private StringBuilder sb = new StringBuilder();

    private void indent() {
        for (int i = 0; i < indent; i++)
            sb.append("  ");
    }

    @Override
    public void visit(Program n) {
        n.mainClass.accept(this);
        for (ClassDecl c : n.classes) {
            sb.append("\n");
            c.accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        indent();
        sb.append("class ").append(n.className).append(" {\n");
        indent += 1;
        indent();
        sb.append("public static void main (String[] ").append(n.argsName).append(") {\n");
        indent += 1;
        n.statement.accept(this);
        indent -= 1;
        indent();
        sb.append("}\n");
        indent -= 1;
        indent();
        sb.append("}\n");
    }

    @Override
    public void visit(ClassDecl n) {
        indent();
        sb.append("class ").append(n.name);
        if (n.parent != null)
            sb.append(" extends ").append(n.parent);
        sb.append(" {\n");
        indent += 1;
        
        for (VarDecl v : n.fields) {
            v.accept(this);
            sb.append("\n");
        }
        
        if (!n.fields.isEmpty() && !n.methods.isEmpty())
            sb.append("\n");
            
        for (MethodDecl m : n.methods) {
            m.accept(this);
            sb.append("\n");
        }
        
        indent -= 1;
        indent();
        sb.append("}\n");
    }

    @Override
    public void visit(VarDecl n) {
        indent();
        n.type.accept(this);
        sb.append(" ").append(n.name).append(";");
    }

    @Override
    public void visit(MethodDecl n) {
        indent();
        sb.append("public ");
        n.returnType.accept(this);
        sb.append(" ").append(n.name).append("(");
        
        for (int i = 0; i < n.params.size(); i++) {
            n.params.get(i).type.accept(this);
            sb.append(" ").append(n.params.get(i).name);
            if (i < n.params.size() - 1)
                sb.append(", ");
        }
        sb.append(") {\n");
        
        indent += 1;
        for (VarDecl v : n.locals) {
            v.accept(this);
            sb.append("\n");
        }
        
        if (!n.locals.isEmpty() && !n.statements.isEmpty())
            sb.append("\n");
            
        for (Statement s : n.statements) {
            s.accept(this);
            sb.append("\n");
        }
        
        indent();
        sb.append("return ");
        n.returnExp.accept(this);
        sb.append(";\n");
        
        indent -= 1;
        indent();
        sb.append("}");
    }

    @Override
    public void visit(IntArrayType n) {
        sb.append("int[]");
    }

    @Override
    public void visit(BooleanType n) {
        sb.append("boolean");
    }

    @Override
    public void visit(IntegerType n) {
        sb.append("int");
    }

    @Override
    public void visit(IdentifierType n) {
        sb.append(n.name);
    }

    @Override
    public void visit(Block n) {
        indent();
        sb.append("{\n");
        indent += 1;
        for (Statement s : n.statements) {
            s.accept(this);
            sb.append("\n");
        }
        indent -= 1;
        indent();
        sb.append("}");
    }

    @Override
    public void visit(If n) {
        indent();
        sb.append("if (");
        n.condition.accept(this);
        sb.append(")\n");
        indent += 1;
        n.thenStmt.accept(this);
        indent -= 1;
        if (n.elseStmt != null) {
            sb.append("\n");
            indent();
            sb.append("else\n");
            indent += 1;
            n.elseStmt.accept(this);
            indent -= 1;
        }
    }

    @Override
    public void visit(While n) {
        indent();
        sb.append("while (");
        n.condition.accept(this);
        sb.append(")\n");
        indent += 1;
        n.body.accept(this);
        indent -= 1;
    }

    @Override
    public void visit(Print n) {
        indent();
        sb.append("System.out.println(");
        n.exp.accept(this);
        sb.append(");");
    }

    @Override
    public void visit(Assign n) {
        indent();
        sb.append(n.id).append(" = ");
        n.exp.accept(this);
        sb.append(";");
    }

    @Override
    public void visit(ArrayAssign n) {
        indent();
        sb.append(n.id).append("[");
        n.index.accept(this);
        sb.append("] = ");
        n.exp.accept(this);
        sb.append(";");
    }

    @Override
    public void visit(And n) {
        sb.append("(");
        n.left.accept(this);
        sb.append(" && ");
        n.right.accept(this);
        sb.append(")");
    }

    @Override
    public void visit(LessThan n) {
        sb.append("(");
        n.left.accept(this);
        sb.append(" < ");
        n.right.accept(this);
        sb.append(")");
    }

    @Override
    public void visit(Plus n) {
        sb.append("(");
        n.left.accept(this);
        sb.append(" + ");
        n.right.accept(this);
        sb.append(")");
    }

    @Override
    public void visit(Minus n) {
        sb.append("(");
        n.left.accept(this);
        sb.append(" - ");
        n.right.accept(this);
        sb.append(")");
    }

    @Override
    public void visit(Times n) {
        sb.append("(");
        n.left.accept(this);
        sb.append(" * ");
        n.right.accept(this);
        sb.append(")");
    }

    @Override
    public void visit(ArrayLookup n) {
        n.array.accept(this);
        sb.append("[");
        n.index.accept(this);
        sb.append("]");
    }

    @Override
    public void visit(ArrayLength n) {
        n.array.accept(this);
        sb.append(".length");
    }

    @Override
    public void visit(Call n) {
        n.object.accept(this);
        sb.append(".").append(n.method).append("(");
        for (int i = 0; i < n.args.size(); i++) {
            n.args.get(i).accept(this);
            if (i < n.args.size() - 1)
                sb.append(", ");
        }
        sb.append(")");
    }

    @Override
    public void visit(IntegerLiteral n) {
        sb.append(n.value);
    }

    @Override
    public void visit(True n) {
        sb.append("true");
    }

    @Override
    public void visit(False n) {
        sb.append("false");
    }

    @Override
    public void visit(IdentifierExp n) {
        sb.append(n.name);
    }

    @Override
    public void visit(This n) {
        sb.append("this");
    }

    @Override
    public void visit(NewArray n) {
        sb.append("new int[");
        n.size.accept(this);
        sb.append("]");
    }

    @Override
    public void visit(NewObject n) {
        sb.append("new ").append(n.className).append("()");
    }

    @Override
    public void visit(Not n) {
        sb.append("!");
        n.exp.accept(this);
    }

    public String toString() {
        return sb.toString();
    }
} 