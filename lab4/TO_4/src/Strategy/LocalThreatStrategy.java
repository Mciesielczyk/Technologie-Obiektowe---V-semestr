package Strategy;

import Iterators.CarIterator;
import Model.Car;
import Model.Event;
import Model.EventType;
import Model.JRG;
import States.FreeState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class  LocalThreatStrategy implements DispatchStrategy{

    private long timeToReach;
    private long timeToWork;
    private long timeToReturn;
    private long elapsedTime;
    private JRG usedJrg;

    @Override
    public List<Car> execute(Event event, List<JRG> units) {
        List<Car> result = new ArrayList<>();
        int amountCars = event.getType() == EventType.MZ? 2:3;
        double smallestDistance = Double.MAX_VALUE;

        List<JRG> sorted = new ArrayList<>(units);
        sorted.sort((j1,j2)->{
            double d1 = distance(event.getLocation().getX(), event.getLocation().getY(), j1.getX(), j1.getY());
            double d2 = distance(event.getLocation().getX(), event.getLocation().getY(), j2.getX(), j2.getY());
            return Double.compare(d1, d2);


        });
            System.out.println(sorted.getFirst().getName());
        for(JRG jrg : sorted){
            CarIterator it = jrg.iterator();
            while (it.hasNext()){
                Car car = it.next();
               if(car.isFree()==true){
                   if (result.isEmpty()) {
                       usedJrg = jrg;
                   }

                   result.add(car);
                   losulosu();
                   car.dispatch(timeToReach,timeToWork,timeToReturn);
               }
               if(result.size()==amountCars){
                   return result;
               }
            }
        }
        if(result.size()==0){
            System.out.println("BRAK AUT!!!!");
        }
        return result;
    }

    public void losulosu(){
        timeToReach = (long)(Math.random() * 3001);
        int falsee = (int)(Math.random()*101);
        if(falsee>=20) {
             timeToWork = 5000 + (long) (Math.random() * 20001);
        }else timeToWork = 0;
         timeToReturn = (long)(Math.random() * 3001);
    }

    public double distance(double x1, double y1, double x2, double y2){
        return ((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public JRG getUsedJrg() {
        return usedJrg;
    }
}
