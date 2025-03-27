package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class ArrayLengthStatement implements Statement {
    private final String array;

    public ArrayLengthStatement(String array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return array + ".length";
    }

    @Override
    public StatementType getType() {
        return StatementType.ARRAY_LENGTH;
    }

    public String getArray() {
        return array;
    }
} 