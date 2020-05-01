/*
 * Copyright (c) 2019 Halan.
 */

public class DataLine {

    private MyCustomVector X;
    private double Y;

    public DataLine(MyCustomVector x, double y) {
        this.X = x;
        this.Y = y;
    }

    public String toString(){
        return X.toString() + Y;
    }

    public MyCustomVector getX(){
        return X;
    }
    public int getXSize(){
        return X.size();
    }
    public double getY(){
        return Y;
    }

}

