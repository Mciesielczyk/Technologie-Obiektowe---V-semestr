package Strategy;

import Model.Car;
import Model.Event;
import Model.JRG;

import java.util.List;

public class FireStrategy implements DispatchStrategy{
    @Override
    public List<Car> execute(Event event, List<JRG> units) {
        return List.of();
    }

}
