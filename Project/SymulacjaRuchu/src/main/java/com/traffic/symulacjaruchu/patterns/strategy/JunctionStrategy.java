package com.traffic.symulacjaruchu.patterns.strategy;

import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;

import java.util.List;

public interface JunctionStrategy {
    boolean canVehicleEnter(Road incomingRoad, Vehicle vehicle, List<Vehicle> allVehicles);
    void update();
    double getSpeedModifier(double progress, boolean isIncoming);

    default boolean checkPrioritySigns(Road incomingRoad, Vehicle vehicle, List<Vehicle> allVehicles) {
        if (incomingRoad.getPriority() == Road.Priority.MAIN) {
            return true;
        }

        for (Vehicle other : allVehicles) {
            if (other != vehicle && other.getCurrentRoad().getEnd() == incomingRoad.getEnd()) {
                if (other.getCurrentRoad().getPriority() == Road.Priority.MAIN) {
                    if (other.getProgress() > 0.6) return false; // Czekamy na auto z głównej
                }
            }
        }
        return true;
    }
}