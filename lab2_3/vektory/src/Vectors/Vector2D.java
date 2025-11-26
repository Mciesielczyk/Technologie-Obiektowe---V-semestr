package Vectors;

import java.io.Serializable;

import static java.lang.Math.sqrt;

public class Vector2D implements IVector, Serializable {
    private static final long serialVersionUID = 1L;
    protected double x;
    protected double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public double abs() {
        return sqrt(x*x+y*y);
    }

    @Override
    public double cdot(IVector other) {
        double[] tab1 = other.getComponents();
        return this.x * tab1[0] + this.y * tab1[1];
    }

    @Override
    public double[] getComponents() {
        double[]tab= new double[2];
        tab[0]=x;
        tab[1]=y;
        return tab;
    }

    public void setComponents(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double distance(Vector2D other) {
        double[] tab = other.getComponents();
        double dx = this.x - tab[0];
        double dy = this.y - tab[1];
        return sqrt(dx*dx+dy*dy);
    }

}
