package States;

import Model.Car;
import Model.EventCar;
import Simulations.Simulation;

public class BusyState implements State {
    long step = 100;

    @Override
    public void dispatch(Car car, long timeToReach, long timeToWork,long timeToReturn){
        System.out.println("Car is busy");
    }

    @Override
    public void returnToBase(Car car) {
       car.setState(new FreeState());
       car.setTimeToDo(0);
       car.setTimeAc(0);
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public EventCar checkTime(long timeToReach, long timeToWork, long timeToReturn, long elapsedTime, Car car) {
        elapsedTime += step;
        car.setTimeAc(elapsedTime);

        if (elapsedTime < timeToReach) {
            return EventCar.GOING;
        }

        if (timeToWork == 0 && elapsedTime < timeToReach + step) {
            return EventCar.FALSE_ALARM;
        }

        if (elapsedTime < timeToReach + timeToWork) {
            return EventCar.WORKING;
        }

        if (elapsedTime < timeToReach + timeToWork + timeToReturn) {
            return EventCar.RETURNING;
        }

        returnToBase(car);
        return EventCar.RETURNED;
    }
}
