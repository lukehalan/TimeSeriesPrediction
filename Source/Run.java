/*
 * Copyright (c) 2019 Halan.
 */

import de.tudresden.inf.lat.jsexp.SexpParserException;

import java.io.IOException;


public class Run {

    private static int question;
    private static int n;
    private static String x;
    private static String expr;
    private static int m;
    private static String data;
    private static int lambda;
    private static int time_budget;


    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].substring(1).equals("question"))
                    question = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("n"))
                    n = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("x"))
                    x = args[++i];
                else if (args[i].substring(1).equals("expr"))
                    expr = args[++i];
                else if (args[i].substring(1).equals("m"))
                    m = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("data"))
                    data = args[++i];
                else if (args[i].substring(1).equals("lambda"))
                    lambda = Integer.parseInt(args[++i]);
                else if (args[i].substring(1).equals("time_budget"))
                    time_budget = Integer.parseInt(args[++i]);
                else {
                    System.err.println("You entered invalid argument: " + args[i]);
                }
            }
        }

        switch (question) {
            case 1:
                exercise1(expr, n, x);
                break;
            case 2:
                exercise2(expr, n, m, data);
                break;
            case 3:
                exercise3(lambda, n, m, data, time_budget);
                break;
            default:
                System.err.println("The question number is not valid");
                break;
        }
    }

    private static void exercise1(String input_expr, int input_n, String input_x) {
        try {
            Expression expression = ExpressionTools.parseExpression(input_expr);
            System.out.println(expression.calc(MyCustomVector.parse(input_x, input_n)));
        } catch (SexpParserException e) {
            e.printStackTrace();
        }
    }

    private static void exercise2(String input_expr, int input_n, int input_m, String input_data) {
        try {
            DataFile dataFile = DataFile.parseDataFile(input_data, input_n, input_m);
            Expression expression = ExpressionTools.parseExpression(input_expr);
            System.out.println(expression.fitness(dataFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SexpParserException e) {
            e.printStackTrace();
        }
    }

    private static void exercise3(int lambda, int n, int m, String input_data, int time_budget) {
        try {
            DataFile dataFile = DataFile.parseDataFile(input_data, n, m);
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(lambda, n, m, dataFile, time_budget);
            geneticAlgorithm.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}