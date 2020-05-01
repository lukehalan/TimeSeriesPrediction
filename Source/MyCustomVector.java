/*
 * Copyright (c) 2019 Halan.
 */

import java.util.Arrays;

public final class MyCustomVector {

    private final double[] values;

    public MyCustomVector(double[] values) {
        this.values = values;
    }

    public double get(int i) {
        if (i < 0 || i >= values.length) {
            System.err.println("Invalid get " + i);
        }
        return values[i];
    }

    public int size() {
        return values.length;
    }

    public String toString(){
        return Arrays.toString(values);
    }

    public static MyCustomVector parse(String x_str, int n) {
        double[] values = Arrays.stream(x_str.split(" ")).mapToDouble(Double::parseDouble).toArray();
        assert n == values.length;
        return new MyCustomVector(values);
    }
}
