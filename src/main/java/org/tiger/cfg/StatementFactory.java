package org.tiger.cfg;

import org.tiger.cfg.statements.*;

public class StatementFactory {
    public static Statement createStatement(StatementType type, Object... args) {
        switch (type) {
            case PRINT:
                return new PrintStatement((String) args[0]);
            case NEW_OBJECT:
                return new NewObjectStatement((String) args[0]);
            case NEW_ARRAY:
                return new NewArrayStatement((String) args[0]);
            case ARRAY_ASSIGN:
                return new ArrayAssignStatement((String) args[0], (String) args[1], (String) args[2]);
            case ARRAY_LOOKUP:
                return new ArrayLookupStatement((String) args[0], (String) args[1]);
            case ARRAY_LENGTH:
                return new ArrayLengthStatement((String) args[0]);
            case PLUS:
                return new PlusStatement((String) args[0], (String) args[1]);
            case MINUS:
                return new MinusStatement((String) args[0], (String) args[1]);
            case TIMES:
                return new TimesStatement((String) args[0], (String) args[1]);
            case LESS_THAN:
                return new LessThanStatement((String) args[0], (String) args[1]);
            case AND:
                return new AndStatement((String) args[0], (String) args[1]);
            case NOT:
                return new NotStatement((String) args[0]);
            case IDENTIFIER:
                return new IdentifierStatement((String) args[0]);
            case INTEGER_LITERAL:
                return new IntegerLiteralStatement((Integer) args[0]);
            case TRUE:
                return new TrueStatement();
            case FALSE:
                return new FalseStatement();
            case THIS:
                return new ThisStatement();
            default:
                throw new IllegalArgumentException("Unknown statement type: " + type);
        }
    }
} 