package com.traffic.symulacjaruchu.patterns.strategy;

import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;

import java.util.List;




public class FourWayLightsStrategy implements JunctionStrategy {
    private boolean nsGreen = true;
    private int timer = 0;
    private final int GREEN_DURATION = 300;

    @Override
    public void update() {
        timer++;
        if (timer > GREEN_DURATION) {
            nsGreen = !nsGreen;
            timer = 0;
        }
    }

    @Override
    public boolean canVehicleEnter(Road road, Vehicle vehicle, List<Vehicle> allVehicles) {
        boolean isVertical = Math.abs(road.getStart().getX() - road.getEnd().getX()) < 10;

        return isVertical == nsGreen;
    }

    @Override
    public double getSpeedModifier(double progress, boolean isIncoming) {

        if (isIncoming && progress > 0.8) {
            return 0.7;
        }
        return 1.0;
    }

    public boolean isNsGreen() {
        return nsGreen;
    }
}