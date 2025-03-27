package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class ArrayLookupStatement implements Statement {
    private final String array;
    private final String index;

    public ArrayLookupStatement(String array, String index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return array + "[" + index + "]";
    }

    @Override
    public StatementType getType() {
        return StatementType.ARRAY_LOOKUP;
    }

    public String getArray() {
        return array;
    }

    public String getIndex() {
        return index;
    }
} 