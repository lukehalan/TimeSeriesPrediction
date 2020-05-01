/*
 * Copyright (c) 2019 Halan.
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ExpressionType {
    ADD, MUL, SUB, DIV, SQRT, POW, LOG, MAX, EXP, IFLEQ, DIFF, DATA, NUMBER, AVG;

    private static final int WEIGHT = 2;
    private static final List<ExpressionType> EXPRESSION_TYPE_LIST = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = EXPRESSION_TYPE_LIST.size() - 1;

    public static ExpressionType randomType(boolean includeTerminal) {
        int r = ThreadLocalRandom.current().nextInt(includeTerminal ? SIZE + WEIGHT : SIZE);
        return r >= SIZE ? NUMBER : EXPRESSION_TYPE_LIST.get(r);
    }

    public int size() {
        switch (this) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case POW:
            case MAX:
            case DIFF:
            case AVG:
                return 2;
            case SQRT:
            case LOG:
            case EXP:
            case DATA:
                return 1;
            case IFLEQ:
                return 4;
            case NUMBER:
                return 0;
            default:
                return -1;
        }
    }
}
