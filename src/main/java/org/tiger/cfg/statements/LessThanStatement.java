package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class LessThanStatement implements Statement {
    private final String left;
    private final String right;

    public LessThanStatement(String left, String right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " < " + right;
    }

    @Override
    public StatementType getType() {
        return StatementType.LESS_THAN;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
} 