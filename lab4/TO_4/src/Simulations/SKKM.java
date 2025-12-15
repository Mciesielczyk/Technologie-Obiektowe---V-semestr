package Simulations;

import Iterators.CarIterator;
import Iterators.ConcreteIterator;
import Model.*;
import Observers.Observer;
import Observers.Subject;
import States.BusyState;
import Strategy.DispatchStrategy;
import Strategy.FireStrategy;
import Strategy.LocalThreatStrategy;

import java.util.ArrayList;
import java.util.List;

public class SKKM extends Subject {

    private DispatchStrategy strategy;
    private List<JRG> units = List.of(
            new JRG("JRG-1", 50.060100, 19.943012),
        (new JRG("JRG-2", 50.033309, 19.935899)),
        (new JRG("JRG-3", 50.075768, 19.887592)),
        (new JRG("JRG-4", 50.037548, 20.005943)),
        (new JRG("JRG-5", 50.091809, 19.919933)),
        (new JRG("JRG-6", 50.015973, 20.015263)),
        (new JRG("JRG-7", 50.094245, 19.977294)),
        (new JRG("JRG-8", 50.077293, 20.032879)),
        (new JRG("JRG-9", 49.968224, 19.799569)),
        (new JRG("JRG-10", 50.072416, 19.804011))
    );



    public void handleEvent(Event event) {

        if(event.getType() == EventType.PZ){
            setStrategy(new FireStrategy());
        } else {
            setStrategy(new LocalThreatStrategy());
        }
        double x = event.getLocation().getX();
        double y = event.getLocation().getY();
        notifyObservers("New Event! Wspolrzedne :  "+   x+"  "+y  +"  Type:" + event.getType());

        setStrategy(new LocalThreatStrategy());
        List<Car> carsToDispatch = strategy.execute(event, units);
        int ilosc = carsToDispatch.size();


        // powiadamiamy obserwatorów

    }


    public void checkCars(){
        for(JRG jrg : units){
            CarIterator it = jrg.iterator();
            while (it.hasNext()){
                Car car = it.next();
                EventCar event = car.check();
                if (event != null && event != EventCar.NONE) {
                    notifyObservers(
                            "Car " + car.getId() + " -> " + event
                    );
                }
            }
        }
    }

    private void setStrategy(DispatchStrategy strategy) {
        this.strategy = strategy;
    }


}
