package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class ReturnStatement implements Statement {
    private final String value;

    public ReturnStatement(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "return " + value;
    }

    @Override
    public StatementType getType() {
        return StatementType.RETURN;
    }

    public String getValue() {
        return value;
    }
} 