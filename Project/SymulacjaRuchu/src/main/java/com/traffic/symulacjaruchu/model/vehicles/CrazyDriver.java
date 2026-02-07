package com.traffic.symulacjaruchu.model.vehicles;

import com.traffic.symulacjaruchu.model.Road;

public class CrazyDriver extends Vehicle {
    public CrazyDriver(Road road) {
        super(road, 0.018);
        this.ignoresRules = true;
        this.ignoresCollisions = true;
    }

    @Override
    protected boolean shouldStop() { return false; }
}