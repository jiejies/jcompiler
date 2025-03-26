package org.tiger.cfg.statements;

import org.tiger.cfg.Statement;
import org.tiger.cfg.StatementType;
import java.util.List;

public class CallStatement implements Statement {
    private final String target;
    private final String methodName;
    private final List<String> arguments;

    public CallStatement(String target, String methodName, List<String> arguments) {
        this.target = target;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (target != null) {
            sb.append(target).append(" = ");
        }
        sb.append("call ").append(methodName).append("(");
        for (int i = 0; i < arguments.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arguments.get(i));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public StatementType getType() {
        return StatementType.CALL;
    }

    public String getTarget() {
        return target;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getArguments() {
        return arguments;
    }
} 