package com.traffic.symulacjaruchu.core;

import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.*;
import com.traffic.symulacjaruchu.patterns.VehicleFactory;
import com.traffic.symulacjaruchu.patterns.observer.TrafficObserver;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();
    private List<Intersection> allIntersections = new ArrayList<>();
    private int updateCounter = 0;
    // --- NOWE: Lista obserwatorów ---
    private List<TrafficObserver> observers = new ArrayList<>();

    public Simulation(List<Road> roads) {
        this.roads = roads;
    }

    public void addObserver(TrafficObserver observer) {
        observers.add(observer);
    }

    public void spawnVehicle() {
        if (roads.isEmpty()) return;

        List<Road> entryRoads = roads.stream()
                .filter(r -> r.getStart().getId().startsWith("D") ||
                        r.getStart().getId().equals("A1") ||
                        r.getStart().getId().equals("C4"))
                .toList();

        if (entryRoads.isEmpty()) return;

        Road startRoad = entryRoads.get(new java.util.Random().nextInt(entryRoads.size()));


        Vehicle newVehicle = VehicleFactory.createRandomVehicle(startRoad);
        vehicles.add(newVehicle);

        for (TrafficObserver o : observers) {
            o.onVehicleSpawned(newVehicle);
        }
    }

    public void update() {

        updateCounter++;
        if (updateCounter >= 100) {
            checkCongestion();
            updateCounter = 0;
        }

        for (Intersection inter : allIntersections) {
            if (inter.getStrategy() != null) {
                inter.getStrategy().update();
            }
        }

        vehicles.removeIf(vehicle -> {
            vehicle.update(roads, vehicles);
            if (vehicle.isFinished()) {
                for (TrafficObserver o : observers) {
                    o.onVehicleFinished(vehicle);
                }
                return true;
            }
            return false;
        });
    }
    public void notifyRoadBlocked(String roadInfo, boolean isBlocked) {
        for (TrafficObserver observer : observers) {
            observer.onRoadBlocked(roadInfo, isBlocked);
        }
    }


    private void checkCongestion() {
        for (Road r : roads) {
            long waitingNow = vehicles.stream()
                    .filter(v -> v.getCurrentRoad() == r && v.isWaiting())
                    .count();

            if (waitingNow >= 3) {
                String roadLabel = r.getStart().getId() + "->" + r.getEnd().getId();
                for (TrafficObserver o : observers) {
                    o.onCongestionDetected(roadLabel, (int)waitingNow);
                }
            }
        }
    }



    public Road findReverseRoad(Road road) {
        if (road == null) return null;

        for (Road r : roads) {
            if (r.getStart().equals(road.getEnd()) && r.getEnd().equals(road.getStart())) {
                return r;
            }
        }
        return null;
    }
    public List<Vehicle> getVehicles() { return vehicles; }
    public List<Road> getRoads() { return roads; }
    public void addRoad(Road road) { roads.add(road); }
    public void setAllIntersections(List<Intersection> intersections) {
        this.allIntersections = intersections;
    }


}