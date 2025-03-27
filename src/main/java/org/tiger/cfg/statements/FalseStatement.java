package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class FalseStatement implements Statement {
    @Override
    public String toString() {
        return "false";
    }

    @Override
    public StatementType getType() {
        return StatementType.FALSE;
    }
} 