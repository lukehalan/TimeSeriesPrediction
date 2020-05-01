/*
 * Copyright (c) 2019 Halan.
 */

import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpFactory;
import de.tudresden.inf.lat.jsexp.SexpParserException;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExpressionTools {

    private static final double MAX_RANDOM = 10;

    public static Expression parseExpression(Sexp sexp) {
        if (sexp.isAtomic()) {
            double val = Double.parseDouble(sexp.toString());
            return new Operators.NUMBER(val);
        } else {
            ExpressionType expressionType = ExpressionType.valueOf(sexp.get(0).toString().toUpperCase());
            return fromExpressionType(expressionType, sexp);
        }
    }

    public static Expression parseExpression(String sexp_string) throws SexpParserException {
        Sexp sexp = SexpFactory.parse(sexp_string);
        return parseExpression(sexp);
    }

    private static Expression fromExpressionType(ExpressionType expressionType, Sexp sexp) {
        ArrayList<Expression> expressionArrayList = new ArrayList<>();
        for (int idx = 1; idx < sexp.getLength(); idx++) {
            expressionArrayList.add(parseExpression(sexp.get(idx)));
        }
        return fromExpressionType(expressionType, expressionArrayList);
    }

    private static Expression fromExpressionType(ExpressionType expressionType, ArrayList<Expression> exp) {
        switch (expressionType) {
            case ADD:
                return new Operators.ADD(exp.get(0), exp.get(1));
            case SUB:
                return new Operators.SUB(exp.get(0), exp.get(1));
            case MUL:
                return new Operators.MUL(exp.get(0), exp.get(1));
            case DIV:
                return new Operators.DIV(exp.get(0), exp.get(1));
            case POW:
                return new Operators.POW(exp.get(0), exp.get(1));
            case SQRT:
                return new Operators.SQRT(exp.get(0));
            case LOG:
                return new Operators.LOG(exp.get(0));
            case EXP:
                return new Operators.EXP(exp.get(0));
            case MAX:
                return new Operators.MAX(exp.get(0), exp.get(1));
            case IFLEQ:
                return new Operators.IFLEQ(exp.get(0), exp.get(1), exp.get(2), exp.get(3));
            case DATA:
                return new Operators.DATA(exp.get(0));
            case DIFF:
                return new Operators.DIFF(exp.get(0), exp.get(1));
            case AVG:
                return new Operators.AVG(exp.get(0), exp.get(1));
            default:
                throw new Error("Error parsing expression: " + expressionType);
        }
    }

    public static Expression getRandom(int mDepth, boolean isTerminal) {
        ExpressionType expressionType = mDepth > 1 ? ExpressionType.randomType(isTerminal) : ExpressionType.NUMBER;
        if (expressionType == ExpressionType.NUMBER) {
            return new Operators.NUMBER(ThreadLocalRandom.current().nextDouble(-MAX_RANDOM, MAX_RANDOM));
        } else {
            ArrayList<Expression> expressionArrayList = new ArrayList<>();
            IntStream.range(0, expressionType.size()).forEach(i -> expressionArrayList.add(getRandom(mDepth - 1, isTerminal)));
            return fromExpressionType(expressionType, expressionArrayList);
        }
    }

    public static Expression getRandom(PopulationGenerationMethod generationMethod, int mDepth) {
        switch (generationMethod) {
            case GROWTH:
                return getRandom(mDepth, true);
            case FULL:
                return getRandom(mDepth, false);
            case HALF:
                return getRandom(mDepth, true);
            default:
                return null;
        }
    }

    public static Expression clone(Expression e) {
        if (!e.isTerminal()) {
            ArrayList<Expression> childrenList = e.children().stream()
                    .map(Expression::clone)
                    .collect(Collectors.toCollection(ArrayList::new));
            return fromExpressionType(e.getExpressionType(), childrenList);
        } else {
            return new Operators.NUMBER(e.calc(null));
        }
    }

}
