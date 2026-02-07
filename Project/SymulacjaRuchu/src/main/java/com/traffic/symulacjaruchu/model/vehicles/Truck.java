package com.traffic.symulacjaruchu.model.vehicles;

import com.traffic.symulacjaruchu.model.Road;

public class Truck extends Vehicle {
    public Truck(Road road) {
        super(road, 0.004);
    }

    @Override
    protected boolean shouldStop() { return false; }
}