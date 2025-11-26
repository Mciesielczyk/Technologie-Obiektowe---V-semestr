package Vectors;

public class TwoDPolarInheritance extends Vector2D{
    public TwoDPolarInheritance(double x, double y){
        super(x,y);
    }

    public double getAngle(){
        double[] tab = this.getComponents();
        return Math.atan2(tab[1],tab[0]);
    }
}
