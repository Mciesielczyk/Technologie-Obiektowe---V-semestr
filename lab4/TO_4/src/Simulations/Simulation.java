package Simulations;

import Model.Event;
import Model.JRG;
import Model.Location;
import Observers.ConsoleObserver;
import Observers.Observer;

import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {

    private SKKM skkm = new SKKM();
    Location location = new Location(0,0);
    public long step = 100;
    public long currentStep = 0;
    public Simulation() {
        //skkm.addObserver(new ConsoleObserver());
    }

    public void start() throws InterruptedException {
        while(true){
            if(currentStep%2000==0 || currentStep==0){
                location.SetRandom();
                Event event = new Event(location);
                skkm.handleEvent(event);
            }

            skkm.checkCars();
            sleep(step);
            currentStep+=step;

        }
    }
    public void addObserver(Observer observer) {
        skkm.addObserver(observer);
    }
    public List<JRG> getUnits() {
        return skkm.getUnits();
    }

    public Event getLastEvent() {
        return skkm.getLastEvent();
    }
    public JRG getActiveJrg() {
        return skkm.getActiveJrg();
    }


}
