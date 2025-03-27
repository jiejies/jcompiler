package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class ThisStatement implements Statement {
    @Override
    public String toString() {
        return "this";
    }

    @Override
    public StatementType getType() {
        return StatementType.THIS;
    }
} 