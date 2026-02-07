package States;

import Model.Car;
import Model.EventCar;

public class FreeState implements State {

    @Override
    public void dispatch(Car car,long timeToReach, long timeToWork,long timeToReturn) {
        car.setState(new BusyState());
        System.out.println("Car dispatched  "+car.getId()+ " " +(timeToReach+timeToWork+timeToReturn));

        car.setTimeAc(0);
       // car.setTimeToDo(timeToReach+timeToWork+timeToReturn);
    }

    @Override
    public void returnToBase(Car car) {

    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public EventCar checkTime(long timeToReach, long timeToWork, long timeToReturn, long elapsedTime, Car car) {
        return EventCar.NONE;
    }

}
