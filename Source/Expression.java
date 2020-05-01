/*
 * Copyright (c) 2019 Halan.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Expression {

    private int depth = -1;
    private int size = -1;
    protected ExpressionType expressionType;
    protected Expression[] eArray;
    private static final int MAX_BRANCHES = Integer.MAX_VALUE;


    public abstract double calc(MyCustomVector v);

    protected Expression(ExpressionType expressionType) {
        this.expressionType = expressionType;
        eArray = new Expression[expressionType.size()];
    }

    public double fitness(DataFile dataFile) {
        double fitness = dataFile.getDataLines().stream().mapToDouble(l -> Math.pow(fitness(l), 2)).average()
                .orElseThrow(IllegalStateException::new);
        return Double.isFinite(fitness) ? fitness : Double.MAX_VALUE;
    }

    private double fitness(DataLine dataLine) {
        return dataLine.getY() - calc(dataLine.getX());
    }

    public static double createFinite(double value) {
        return Double.isFinite(value) ? value : 0;
    }

    public int size() {
        return size < 0 ? computeSize() : size;
    }

    private int computeSize() {
        return size = isTerminal() ? 1 : 1 + Arrays.stream(eArray).mapToInt(Expression::size).sum();
    }

    public int depth() {
        return depth < 0 ? computeDepth() : depth;
    }

    private int computeDepth() {
        return depth = isTerminal() ? 0
                : 1 + Arrays.stream(eArray).mapToInt(Expression::depth).max().orElseThrow(IllegalStateException::new);
    }

    public boolean isTerminal() {
        return arity() == 0;
    }

    public int arity() {
        return eArray.length;
    }


    public static Expression mutate(Expression expr_orig, PopulationGenerationMethod generationMethod, int depth) {
        Expression exp = expr_orig.clone();
        Expression rParent;
        if (exp.isTerminal()) {
            return ExpressionTools.getRandom(generationMethod, depth);
        }

        while ((rParent = exp.getRandomParent()).isTerminal()) ;

        int rand1 = ThreadLocalRandom.current().nextInt(rParent.arity());
        rParent.eArray[rand1] = ExpressionTools.getRandom(generationMethod, depth);
        return exp;
    }

    public static LinkedList<Expression> crossover(Expression exp1_orig, Expression exp2_orig) {
        Expression exp1 = exp1_orig.clone();
        Expression exp2 = exp2_orig.clone();

        Expression rParent1 = exp1.getRandomParent();
        Expression rParent2 = exp2.getRandomParent();

        if (rParent1 != null && rParent2 != null) {
            doSwapChildren(rParent1, rParent2);
        }

        LinkedList<Expression> expressionLinkedList = new LinkedList<>();
        expressionLinkedList.add(exp1);
        expressionLinkedList.add(exp2);

        return expressionLinkedList;
    }

    private static void doSwapChildren(Expression exp1, Expression exp2) {
        int ran1 = ThreadLocalRandom.current().nextInt(exp1.arity());
        int ran2 = ThreadLocalRandom.current().nextInt(exp2.arity());
        Expression rChild1 = exp1.eArray[ran1];
        Expression rChild2 = exp2.eArray[ran2];
        exp1.eArray[ran1] = rChild2;
        exp2.eArray[ran2] = rChild1;
    }


    private Expression getRandomParent() {
        if (this.isTerminal()) {
            return null;
        }
        Queue<Expression> expressionQueue = new LinkedList<>();
        expressionQueue.add(this);
        int count = 0;
        int randomNum = countBranches() == 0 ? 0 : ThreadLocalRandom.current().nextInt(Integer.min(MAX_BRANCHES, countBranches()));
        while (!expressionQueue.isEmpty() && count <= randomNum) {
            Expression exp = expressionQueue.remove();
            if (count == randomNum) {
                return exp;
            }
            exp.children().stream().filter(c -> !c.isTerminal()).forEachOrdered(expressionQueue::add);
            count++;
        }
        System.out.println("Count: " + count + "\trandomNum: " + randomNum + "\tcountBranches: " + countBranches());
        throw new IllegalArgumentException("Random parent not found from " + toString());
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public Expression clone() {
        return ExpressionTools.clone(this);
    }

    public ArrayList<Expression> children() {
        return new ArrayList<>(Arrays.asList(eArray));
    }

    public int countBranches() {
        return isTerminal() ? 0 : 1 + Arrays.stream(eArray).mapToInt(Expression::countBranches).sum();

    }

    public String toString() {
        StringBuilder builder = new StringBuilder("(").append(expressionType.toString().toLowerCase());
        Arrays.stream(eArray).forEach(s -> builder.append(" ").append(s));
        return builder.append(")").toString();
    }

}