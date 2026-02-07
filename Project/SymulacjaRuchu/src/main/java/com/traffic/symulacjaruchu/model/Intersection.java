package com.traffic.symulacjaruchu.model;

import com.traffic.symulacjaruchu.patterns.strategy.FourWayLightsStrategy;
import com.traffic.symulacjaruchu.patterns.strategy.JunctionStrategy;

public class Intersection {
    private final String id;
    private final double x,y;
    private TrafficLight trafficLight;
    private boolean northSouthGreen = true;
    private int phaseTimer = 0;
    private final int PHASE_DURATION = 300; // Zmiana co 300 klatek
    private JunctionStrategy junctionStrategy;

    public Intersection(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }


    public void updateLights() {
        phaseTimer++;
        if (phaseTimer >= PHASE_DURATION) {
            northSouthGreen = !northSouthGreen;
            phaseTimer = 0;
        }
    }


    public void setTrafficLight(TrafficLight tl) { this.trafficLight = tl; }
    public TrafficLight getTrafficLight() { return trafficLight; }
    public boolean hasTrafficLight() { return trafficLight != null; }


    public String getId() {
        return id;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public boolean isNorthSouthGreen() {
        if (this.junctionStrategy instanceof FourWayLightsStrategy) {
            return ((FourWayLightsStrategy) this.junctionStrategy).isNsGreen();
        }
        return true;
    }
    public JunctionStrategy getStrategy() {
        return junctionStrategy;
    }
    public void setStrategy(JunctionStrategy j) {
        junctionStrategy = j;
    }

}
