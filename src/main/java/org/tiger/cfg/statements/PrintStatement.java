package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class PrintStatement implements Statement {
    private final String source;

    public PrintStatement(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "print " + source;
    }

    @Override
    public StatementType getType() {
        return StatementType.PRINT;
    }

    public String getSource() {
        return source;
    }
} 