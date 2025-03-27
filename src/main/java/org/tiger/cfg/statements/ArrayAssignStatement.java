package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class ArrayAssignStatement implements Statement {
    private final String target;
    private final String index;
    private final String value;

    public ArrayAssignStatement(String target, String index, String value) {
        this.target = target;
        this.index = index;
        this.value = value;
    }

    @Override
    public String toString() {
        return target + "[" + index + "] = " + value;
    }

    @Override
    public StatementType getType() {
        return StatementType.ARRAY_ASSIGN;
    }

    public String getTarget() {
        return target;
    }

    public String getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }
} 