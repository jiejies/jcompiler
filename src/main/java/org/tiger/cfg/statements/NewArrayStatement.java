package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class NewArrayStatement implements Statement {
    private final String size;

    public NewArrayStatement(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "new int[" + size + "]";
    }

    @Override
    public StatementType getType() {
        return StatementType.NEW_ARRAY;
    }

    public String getSize() {
        return size;
    }
} 