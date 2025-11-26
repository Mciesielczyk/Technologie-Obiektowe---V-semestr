package Vectors;

public class Vector3DDecorator implements IVector {

    private IVector srcVector;
    private double z;

    public Vector3DDecorator(IVector srcVector,double z){
    this.srcVector = srcVector;
    this.z = z;
    }

@Override
    public double abs() {
        double[] tab =  this.srcVector.getComponents();
        double x = tab[0];
        double y = tab[1];
        double z = tab.length>2 ? tab[2] : this.z;
        return Math.sqrt(x*x+y*y+z*z);
    }

@Override
    public double cdot(IVector other) {
        double[] a = this.getComponents();
        double[] b = other.getComponents();

        double z1 = a.length > 2 ? a[2] : 0;
        double z2 = b.length > 2 ? b[2] : 0;

        return a[0]*b[0] + a[1]*b[1] + z1*z2;
    }

    @Override
    public double[] getComponents() {
        double[] tab =  this.srcVector.getComponents();
        return new double[]{tab[0],tab[1],z};
    }

    public Vector3DDecorator cross(IVector other){
        double[] a = this.getComponents();
        double[] b = other.getComponents();

        double x1 = a[0], y1 = a[1], z1 = a.length > 2 ? a[2] : 0;
        double x2 = b[0], y2 = b[1], z2 = b.length > 2 ? b[2] : 0;

        double crossX = y1*z2 - z1*y2;
        double crossY = z1*x2 - x1*z2;
        double crossZ = x1*y2 - y1*x2;
        IVector vec = new Vector2D(crossX,crossY);
        return new Vector3DDecorator(vec,crossZ);
    }

    public IVector getSrcV(){
        return this.srcVector;
    }
}
