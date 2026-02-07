package com.traffic.symulacjaruchu.model.vehicles;

import com.traffic.symulacjaruchu.model.Road;

public class Bus extends Vehicle {
    private int waitTimer = 0;

    public Bus(Road road) {
        super(road, 0.006);
    }

    @Override
    protected boolean shouldStop() {
        if (waitTimer > 0) {
            waitTimer--;
            return true;
        }
        if (Math.random() < 0.002) {
            waitTimer = 120;
            return true;
        }
        return false;
    }
}