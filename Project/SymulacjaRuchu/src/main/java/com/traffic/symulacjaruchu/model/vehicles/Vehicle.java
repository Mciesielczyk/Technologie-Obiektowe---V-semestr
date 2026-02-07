package com.traffic.symulacjaruchu.model.vehicles;

import com.traffic.symulacjaruchu.core.TrafficSettings;
import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import javafx.scene.paint.Color; // Zmieniono z java.awt.Color na javafx dla JavaFX
import com.traffic.symulacjaruchu.model.Road;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

    protected double speed;
    protected double progress = 0;
    protected Road currentRoad;
    protected boolean isWaiting = false;
    protected boolean isFinished = false;
    protected Color color;
  //  private boolean toRemove =

    protected boolean ignoresRules = false;
    protected boolean ignoresCollisions = false;

    public Vehicle(Road currentRoad, double speed) {
        this.currentRoad = currentRoad;
        this.speed = speed;
        this.progress = 0;
    }


    public void update(List<Road> allRoads, List<Vehicle> allVehicles) {
        this.isWaiting = false;

        if (shouldStop()) {
            this.isWaiting = true;
            return;
        }

        double currentSpeed = calculateSpeed();

        if (!ignoresRules && isBlockedByJunction(allVehicles)) {
            this.isWaiting = true;
            return;
        }

        if (!ignoresCollisions && isBlockedByVehicleInFront(allVehicles)) {
            this.isWaiting = true;
            return;
        }

        applyMovement(currentSpeed, allRoads);
    }
    protected abstract boolean shouldStop();

    private double calculateSpeed() {
        double globalModifier = TrafficSettings.getInstance().getGlobalSpeedMultiplier();

        double strategyModifier = 1.0;
        if (currentRoad.getEnd().getStrategy() != null) {
            strategyModifier = currentRoad.getEnd().getStrategy().getSpeedModifier(progress, true);
        }

        return speed * globalModifier * strategyModifier;
    }

    private boolean isBlockedByJunction(List<Vehicle> allVehicles) {
        if (progress > 0.92 && currentRoad.getEnd().getStrategy() != null) {
            return !currentRoad.getEnd().getStrategy().canVehicleEnter(currentRoad, this, allVehicles);
        }
        return false;
    }

    private boolean isBlockedByVehicleInFront(List<Vehicle> allVehicles) {
        for (Vehicle other : allVehicles) {
            if (other != this && other.currentRoad == this.currentRoad) {
                double distance = other.progress - this.progress;

                if (distance > 0 && distance < 0.04) {
                    return true;
                }

                if (distance > 0 && distance < 0.12) {
                    this.speed = Math.min(this.speed, other.speed);
                }
            }
        }
        return false;
    }

    private void applyMovement(double currentSpeed, List<Road> allRoads) {
        if (progress < 1.0) {
            progress += currentSpeed;
        } else {
            chooseNextRoad(allRoads);
        }
    }


    private void chooseNextRoad(List<Road> allRoads) {
        List<Road> nextOptions = new ArrayList<>();
        Intersection currentLoc = currentRoad.getEnd();

        for (Road r : allRoads) {
            if (r.getStart() == currentLoc && r.getEnd() != currentRoad.getStart()) {
                if (!r.isBlocked()) {
                    nextOptions.add(r);
                }
            }
        }

        if (!nextOptions.isEmpty()) {
            this.currentRoad = nextOptions.get((int) (Math.random() * nextOptions.size()));
            this.progress = 0.0;
        } else {
            if (Math.random() < 0.5) {
                this.isFinished = true;
                //System.out.println("Auto zakończyło trasę i zniknęło.");
            } else {
                // 50% SZANS: Auto próbuje zawrócić
                Road backRoad = findBackRoad(allRoads, currentLoc);
                if (backRoad != null) {
                    this.currentRoad = backRoad;
                    this.progress = 0.0;
                   // System.out.println("Auto zdecydowało się zostać i zawrócić.");
                } else {
                    this.isFinished = true;
                }
            }
        }
    }

    private Road findBackRoad(List<Road> allRoads, Intersection currentLoc) {
        for (Road r : allRoads) {
            if (r.getStart() == currentLoc && r.getEnd() == currentRoad.getStart()) {
                if (!r.isBlocked()) return r;
            }
        }
        return null;
    }

    public double getCurrentX() {
        double startX = currentRoad.getStart().getX();
        double endX = currentRoad.getEnd().getX();
        return startX + (endX - startX) * progress;
    }

    public double getCurrentY() {
        double startY = currentRoad.getStart().getY();
        double endY = currentRoad.getEnd().getY();
        return startY + (endY - startY) * progress;
    }

    public boolean isWaiting() { return isWaiting; }
    public boolean isFinished() { return isFinished; }
    public Road getCurrentRoad() { return currentRoad; }
    public double getProgress() { return progress; }
    public Color getColor() { return color; }

}