package com.traffic.symulacjaruchu.model.vehicles;


import com.traffic.symulacjaruchu.model.Road;

public class Ambulance extends Vehicle {
    public Ambulance(Road road) {
        super(road, 0.012);
        this.ignoresRules = true;
        this.ignoresCollisions = true;
    }

    @Override
    protected boolean shouldStop() { return false; }
}


