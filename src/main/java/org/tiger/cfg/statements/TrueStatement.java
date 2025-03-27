package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class TrueStatement implements Statement {
    @Override
    public String toString() {
        return "true";
    }

    @Override
    public StatementType getType() {
        return StatementType.TRUE;
    }
} 