package com.traffic.symulacjaruchu.patterns.observer;

import com.traffic.symulacjaruchu.model.vehicles.Vehicle;

public interface TrafficObserver {
    void onVehicleSpawned(Vehicle vehicle);
    void onVehicleFinished(Vehicle vehicle);
    void onRoadBlocked(String roadInfo, boolean isBlocked);
    void onCongestionDetected(String roadId, int vehicleCount);
}