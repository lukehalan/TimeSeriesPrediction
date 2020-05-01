/*
 * Copyright (c) 2019 Halan.
 */

public class Operators {

    static class ADD extends Expression {
        public ADD(Expression exp1, Expression exp2) {
            super(ExpressionType.ADD);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(eArray[0].calc(v) + eArray[1].calc(v));
        }
    }

    static class SUB extends Expression {
        public SUB(Expression exp1, Expression exp2) {
            super(ExpressionType.SUB);
            eArray[0] = exp1;
            eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(eArray[0].calc(v) - eArray[1].calc(v));
        }
    }

    static class MUL extends Expression {
        public MUL(Expression exp1, Expression exp2) {
            super(ExpressionType.MUL);
            eArray[0] = exp1;
            eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(eArray[0].calc(v) * eArray[1].calc(v));
        }
    }

    static class DIV extends Expression {
        public DIV(Expression exp1, Expression exp2) {
            super(ExpressionType.AVG);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(eArray[0].calc(v) / eArray[1].calc(v));
        }

    }

    static class POW extends Expression {
        public POW(Expression exp1, Expression exp2) {
            super(ExpressionType.POW);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(Math.pow(eArray[0].calc(v), eArray[1].calc(v)));
        }
    }

    static class SQRT extends Expression {
        public SQRT(Expression exp1) {
            super(ExpressionType.SQRT);
            eArray[0] = exp1;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(Math.sqrt(eArray[0].calc(v)));
        }

    }

    static class LOG extends Expression {
        private static final double logTwo = Math.log(2);
        public LOG(Expression exp1) {
            super(ExpressionType.LOG);
            this.eArray[0] = exp1;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(Math.log(eArray[0].calc(v)) / logTwo);
        }
    }

    static class EXP extends Expression {
        public EXP(Expression exp1) {
            super(ExpressionType.EXP);
            this.eArray[0] = exp1;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(Math.exp(eArray[0].calc(v)));
        }
    }

    static class MAX extends Expression {
        public MAX(Expression exp1, Expression exp2) {
            super(ExpressionType.MAX);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            return Math.max(eArray[0].calc(v), eArray[1].calc(v));
        }
    }

    static class IFLEQ extends Expression {
        public IFLEQ(Expression exp1, Expression exp2, Expression exp3, Expression exp4) {
            super(ExpressionType.IFLEQ);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
            this.eArray[2] = exp3;
            this.eArray[3] = exp4;
        }
        @Override
        public double calc(MyCustomVector v) {
            return createFinite(eArray[0].calc(v) <= eArray[1].calc(v) ? eArray[2].calc(v) : eArray[3].calc(v));
        }
    }

    static class DATA extends Expression {
        public DATA(Expression exp1) {
            super(ExpressionType.DATA);
            this.eArray[0] = exp1;
        }
        @Override
        public double calc(MyCustomVector v) {
            int s = v.size();
            int i = (int) Math.abs(Math.floor(eArray[0].calc(v))) % s;
            return createFinite(v.get(i));
        }
    }

    static class DIFF extends Expression {
        public DIFF(Expression exp1, Expression exp2) {
            super(ExpressionType.DIFF);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            int s = v.size();
            int i = (int) Math.abs(Math.floor(eArray[0].calc(v))) % s;
            int j = (int) Math.abs(Math.floor(eArray[1].calc(v))) % s;
            return createFinite(v.get(i) - v.get(j));
        }
    }

    static class AVG extends Expression {
        public AVG(Expression exp1, Expression exp2) {
            super(ExpressionType.AVG);
            this.eArray[0] = exp1;
            this.eArray[1] = exp2;
        }
        @Override
        public double calc(MyCustomVector v) {
            int s = v.size();
            int i = (int) (Math.abs(Math.floor(eArray[0].calc(v))) % s);
            int j = (int) (Math.abs(Math.floor(eArray[1].calc(v))) % s);
            if (i == j)
                return 0;
            int minimum = Math.min(i, j);
            int maximum = Math.max(i, j);
            double total = 0;
            for (int k = minimum; k < maximum; k++) {
                total += v.get(k);
            }
            return createFinite(total / (maximum - minimum));
        }
    }

    static final class NUMBER extends Expression {
        private final double value;
        public NUMBER(double value) {
            super(ExpressionType.NUMBER);
            this.value = value;
        }
        @Override
        public double calc(MyCustomVector v) {
            return value;
        }
        @Override
        public String toString(){
            return Double.toString(value);
        }
    }

}
