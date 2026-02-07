package com.traffic.symulacjaruchu.patterns.strategy;

import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;

import java.util.List;

public class RoundaboutStrategy implements JunctionStrategy {
    @Override
    public boolean canVehicleEnter(Road incomingRoad, Vehicle vehicle, List<Vehicle> allVehicles) {

        if (incomingRoad.getStart().getId().contains("_")) {
            return true;
        }

        Intersection target = incomingRoad.getEnd();
        for (Vehicle other : allVehicles) {
            if (other == vehicle) continue;

            if (other.getCurrentRoad().getEnd() == target) {
                if (other.getCurrentRoad().getStart().getId().contains("_")) {
                    if (other.getProgress() > 0.3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @Override
    public double getSpeedModifier(double progress, boolean isIncoming) {

        if (isIncoming && progress > 0.85) return 0.7;
        return 1.0;
    }

    @Override
    public void update() {

    }
}