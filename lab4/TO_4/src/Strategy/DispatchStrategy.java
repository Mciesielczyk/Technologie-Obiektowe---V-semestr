package Strategy;

import Model.Car;
import Model.Event;
import Model.JRG;

import java.util.List;

public interface DispatchStrategy {

    List<Car> execute(Event event, List<JRG> units);
}
