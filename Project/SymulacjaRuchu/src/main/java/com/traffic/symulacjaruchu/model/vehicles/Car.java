package com.traffic.symulacjaruchu.model.vehicles;

import com.traffic.symulacjaruchu.model.Road;

public class Car extends Vehicle {

    private int waitTimer = 0;

    public Car(Road road) {
        super(road, 0.01);
    }

    @Override
    protected boolean shouldStop() {

        return false;
    }
}
