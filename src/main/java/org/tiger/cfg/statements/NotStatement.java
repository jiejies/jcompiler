package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class NotStatement implements Statement {
    private final String expr;

    public NotStatement(String expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "!" + expr;
    }

    @Override
    public StatementType getType() {
        return StatementType.NOT;
    }

    public String getExpr() {
        return expr;
    }
} 