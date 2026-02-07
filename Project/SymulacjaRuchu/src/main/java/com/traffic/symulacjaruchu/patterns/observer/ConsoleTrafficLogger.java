package com.traffic.symulacjaruchu.patterns.observer;

import com.traffic.symulacjaruchu.model.vehicles.Vehicle;

public class ConsoleTrafficLogger implements TrafficObserver {
    @Override
    public void onVehicleSpawned(Vehicle vehicle) {
        System.out.println("[SPAWN] Nowy pojazd " + vehicle.getClass().getSimpleName() +
                " wjechał na drogę: " + vehicle.getCurrentRoad().getStart().getId());
    }

    @Override
    public void onVehicleFinished(Vehicle vehicle) {
        System.out.println("[FINISH] Pojazd zakończył trasę i opuścił miasto.");
    }

    @Override
    public void onRoadBlocked(String roadInfo, boolean isBlocked) {
        String status = isBlocked ? "ZABLOKOWANA" : "ODBLOKOWANA";
        System.out.println("[MAPA] Droga " + roadInfo + " jest teraz " + status);
    }

    @Override
    public void onCongestionDetected(String roadId, int vehicleCount) {
        System.out.println("!!! ALERT !!! Zator na drodze " + roadId + ". Stoi tam już " + vehicleCount + " aut!");
    }
}