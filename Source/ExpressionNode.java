/*
 * Copyright (c) 2019 Halan.
 */

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ExpressionNode extends ArrayList<Expression> {

    private PopulationGenerationMethod generationMethod;
    private Expression bExpression;
    private double bFitness = Double.MAX_VALUE;
    private static final long serialVersionUID = -135392962374553684L;
    private int generation = 0;

    private ExpressionNode(PopulationGenerationMethod generationMethod, int depth) {
        this.generationMethod = generationMethod;
    }

    public int getGeneration() {
        return generation;
    }

    public double getBFitness() {
        return bFitness;
    }

    public Expression getBExpression() {
        return bExpression;
    }

    public static ExpressionNode getRandom(PopulationGenerationMethod method, int depth, int lambda) {

        switch (method) {
            case GROWTH:
                return getRandomGMethod(lambda, depth);
            case FULL:
                return getRandomFMethod(lambda, depth);
            case HALF:
                return getRandomHMethod(lambda, depth);
            default:
                return null;
        }
    }

    private static ExpressionNode getRandomGMethod(int lambda, int mDepth) {
        ExpressionNode node = new ExpressionNode(PopulationGenerationMethod.GROWTH, mDepth);
        for (int idx = 0; idx < lambda; idx++) {
            node.add(ExpressionTools.getRandom(mDepth, true));
        }
        return node;
    }

    private static ExpressionNode getRandomFMethod(int lambda, int DepthTree) {
        ExpressionNode node = new ExpressionNode(PopulationGenerationMethod.FULL, DepthTree);
        for (int idx = 0; idx < lambda; idx++) {
            node.add(ExpressionTools.getRandom(DepthTree, false));
        }
        return node;
    }

    private static ExpressionNode getRandomHMethod(int lambda, int mDepth) {
        ExpressionNode pop = new ExpressionNode(PopulationGenerationMethod.HALF, mDepth);
        return pop;
    }

    private Expression tournament(int k, DataFile data) {

        ArrayList<Expression> bestCompetitorList = new ArrayList<Expression>();
        double fitness = 0;
        double b_fitness = Double.MAX_VALUE;

        for (int idx = 0; idx <= k; idx++) {
            int rand = ThreadLocalRandom.current().nextInt(size());
            Expression competitor = get(rand);
            fitness = competitor.fitness(data);
            if (fitness <= b_fitness) {
                if (fitness < b_fitness) {
                    bestCompetitorList.clear();
                    b_fitness = fitness;
                }
                bestCompetitorList.add(competitor);
            }
        }

        if (bestCompetitorList.size() == 0) {
            System.err.println("Error, no best_fighters, fitness: " + fitness);
        }

        return bestCompetitorList.get(ThreadLocalRandom.current().nextInt(bestCompetitorList.size()));
    }

    public void getBestIndividual(DataFile data) {
        for (Expression expression : this) {
            double f = expression.fitness(data);
            if (f < bFitness) {
                bFitness = f;
                bExpression = expression;
            }
        }
    }

    public ExpressionNode develop(int k, double chi, DataFile data, int mDepth) {
        ExpressionNode node = new ExpressionNode(generationMethod, mDepth);
        node.add(getBExpression().clone());
        while (node.size() < size()) {
            ArrayList<Expression> toAdd = Expression.crossover(tournament(k, data), tournament(k, data)).stream()
                    .map(e -> Expression.mutate(e, generationMethod, mDepth))
                    .collect(Collectors.toCollection(ArrayList::new));
            int sl = Math.min(size() - node.size(), toAdd.size());
            for (int idx = 0; idx < sl; idx++) {
                node.add(toAdd.get(idx));
            }
        }
        node.generation = generation + 1;
        return node;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

}

