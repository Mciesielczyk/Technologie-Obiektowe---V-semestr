package Model;

import java.util.Random;

public class Location {

    private double x,y;

    double minLat = 49.95855025648944;
    double maxLat = 50.154564013341734;
    double minLon = 19.688292482742394;
    double maxLon = 20.02470275868903;


    Random random = new Random();
    public Location(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void SetRandom(){
        this.x = minLat + Math.random() * (maxLat - minLat);
        this.y = minLon + Math.random() * (maxLon - minLon);
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

}
