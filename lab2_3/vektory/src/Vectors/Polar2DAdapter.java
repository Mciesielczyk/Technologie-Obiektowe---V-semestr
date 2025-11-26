package Vectors;

public class Polar2DAdapter implements IPolar2D,IVector{
    private Vector2D srcVector;

    public Polar2DAdapter(Vector2D srcVector){
        this.srcVector = srcVector;
    }

    @Override
    public double getAngle() {
        double[] tab=srcVector.getComponents();
        return Math.atan2(tab[1],tab[0]);
    }

    @Override
    public double abs() {
        return srcVector.abs();
    }

    @Override
    public double cdot(IVector other) {
        return srcVector.cdot(other);
    }
    @Override
    public double[] getComponents() {
        return srcVector.getComponents();
    }


}
