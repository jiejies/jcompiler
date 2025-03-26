package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;

public class AssignStatement implements Statement {
    private final String target;
    private final String source;

    public AssignStatement(String target, String source) {
        this.target = target;
        this.source = source;
    }

    @Override
    public String toString() {
        return target + " = " + source;
    }

    @Override
    public StatementType getType() {
        return StatementType.ASSIGN;
    }

    public String getTarget() {
        return target;
    }

    public String getSource() {
        return source;
    }
} 