package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class IntegerLiteralStatement implements Statement {
    private final int value;

    public IntegerLiteralStatement(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public StatementType getType() {
        return StatementType.INTEGER_LITERAL;
    }

    public int getValue() {
        return value;
    }
} 