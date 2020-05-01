/*
 * Copyright (c) 2019 Halan.
 */

import java.io.PrintStream;

public class GeneticAlgorithm {

    private PopulationGenerationMethod generationMethod = PopulationGenerationMethod.GROWTH;
    private DataFile dataFile;
    private int time_budget, lambda, m, n;
    private int depth = 5;
    private double chi = 5;
    private int k = 2;


    public GeneticAlgorithm(int lambda, int n, int m, DataFile dataFile, int depth, int time_budget, double chi,
                            int k, PopulationGenerationMethod generationMethod) {
        this.generationMethod = generationMethod;
        this.dataFile = dataFile;
        this.time_budget = time_budget;
        this.lambda = lambda;
        this.n = n;
        this.m = m;
        this.depth = depth;
        this.chi = chi;
        this.k = k;
    }

    public GeneticAlgorithm(int lambda, int n, int m, DataFile dataFile, int time_budget) {
        this.dataFile = dataFile;
        this.time_budget = time_budget;
        this.lambda = lambda;
        this.n = n;
        this.m = m;
    }


    public void run() {
        runToGetBestExpr(System.out);
    }

    public void runToGetBestExpr(PrintStream out) {

        long endTimer = System.currentTimeMillis() + time_budget * 1000;
        boolean log = out != System.out;

        ExpressionNode node = ExpressionNode.getRandom(generationMethod, depth, lambda);
        node.getBestIndividual(dataFile);

        while (System.currentTimeMillis() < endTimer) {
            node = node.develop(k, chi, dataFile, depth);
            node.getBestIndividual(dataFile);
        }

        System.out.println(node.getBExpression());

        if (log) {
            StringBuilder builder = new StringBuilder();
            out.println(builder);
        }
    }
}
