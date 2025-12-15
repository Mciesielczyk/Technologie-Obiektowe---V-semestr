package States;

import Model.Car;
import Model.EventCar;

public interface State {
    void dispatch(Car car, long timeToReach, long timeToWork,long timeToReturn);
    void returnToBase(Car car);
    boolean isFree();
    EventCar checkTime(long timeToReach, long timeToWork, long timeToReturn, long elapsedTime, Car car);
}
