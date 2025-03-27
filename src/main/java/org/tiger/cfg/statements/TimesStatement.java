package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class TimesStatement implements Statement {
    private final String left;
    private final String right;

    public TimesStatement(String left, String right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " * " + right;
    }

    @Override
    public StatementType getType() {
        return StatementType.TIMES;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
} 