package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class IdentifierStatement implements Statement {
    private final String name;

    public IdentifierStatement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public StatementType getType() {
        return StatementType.IDENTIFIER;
    }

    public String getName() {
        return name;
    }
} 