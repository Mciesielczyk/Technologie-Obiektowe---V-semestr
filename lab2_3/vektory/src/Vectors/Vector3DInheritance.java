package Vectors;

import static java.lang.Math.sqrt;

public class Vector3DInheritance extends Vector2D{

    private double z;


    public Vector3DInheritance(double x, double y, double z){
        super(x,y);
        this.z = z;
    }
@Override
    public double abs(){
    return sqrt(super.x*super.x + super.y*super.y + z*z);
    }

    public double[] getComponents(){
        return new double[]{x,y,z};
    }

    public double cdot(IVector other){
        double[] a = this.getComponents();
        double[] b = other.getComponents();

        double z1 = a.length > 2 ? a[2] : 0;
        double z2 = b.length > 2 ? b[2] : 0;

        return a[0]*b[0] + a[1]*b[1] + z1*z2;
}
    public Vector3DInheritance cross(IVector other){
        double[] a = this.getComponents();
        double[] b = other.getComponents();

        double x1 = a[0], y1 = a[1], z1 = a.length > 2 ? a[2] : 0;
        double x2 = b[0], y2 = b[1], z2 = b.length > 2 ? b[2] : 0;

        double crossX = y1*z2 - z1*y2;
        double crossY = z1*x2 - x1*z2;
        double crossZ = x1*y2 - y1*x2;

        return new Vector3DInheritance(crossX,crossY,crossZ);
    }

    public IVector getSrcV(){
        return new Vector3DInheritance(x, y, z);
    }
}
