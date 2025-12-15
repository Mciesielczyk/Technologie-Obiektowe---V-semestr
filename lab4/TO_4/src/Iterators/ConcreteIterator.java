package Iterators;

import Model.Car;

import java.util.ArrayList;
import java.util.List;

public class ConcreteIterator implements CarIterator {

    private List<Car> cars;
    private int index =0;

    public ConcreteIterator(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public boolean hasNext() {
        return index<cars.size();
    }


    @Override
    public Car next() {
        return cars.get(index++);
    }
}


