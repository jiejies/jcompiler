package org.tiger.ast;

public interface AstVisitor {
    void visit(Program n);
    void visit(MainClass n);
    void visit(ClassDecl n);
    void visit(VarDecl n);
    void visit(MethodDecl n);
    void visit(IntArrayType n);
    void visit(BooleanType n);
    void visit(IntegerType n);
    void visit(IdentifierType n);
    void visit(Block n);
    void visit(If n);
    void visit(While n);
    void visit(Print n);
    void visit(Assign n);
    void visit(ArrayAssign n);
    void visit(And n);
    void visit(LessThan n);
    void visit(Plus n);
    void visit(Minus n);
    void visit(Times n);
    void visit(ArrayLookup n);
    void visit(ArrayLength n);
    void visit(Call n);
    void visit(IntegerLiteral n);
    void visit(True n);
    void visit(False n);
    void visit(IdentifierExp n);
    void visit(This n);
    void visit(NewArray n);
    void visit(NewObject n);
    void visit(Not n);
} 