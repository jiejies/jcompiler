package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class NewObjectStatement implements Statement {
    private final String className;

    public NewObjectStatement(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "new " + className + "()";
    }

    @Override
    public StatementType getType() {
        return StatementType.NEW_OBJECT;
    }

    public String getClassName() {
        return className;
    }
} 