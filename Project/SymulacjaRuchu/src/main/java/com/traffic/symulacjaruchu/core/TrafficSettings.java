package com.traffic.symulacjaruchu.core;

public class TrafficSettings {
    private static TrafficSettings instance;

    private double globalSpeedMultiplier = 0.01;
    private int spawnRate = 2000; // ms
    private boolean debugMode = false;

    private TrafficSettings() {}

    public static TrafficSettings getInstance() {
        if (instance == null) {
            instance = new TrafficSettings();
        }
        return instance;
    }

    public double getGlobalSpeedMultiplier() { return globalSpeedMultiplier; }
    public void setGlobalSpeedMultiplier(double mult) { this.globalSpeedMultiplier = mult; }

    public int getSpawnRate() { return spawnRate; }
    public void setSpawnRate(int rate) { this.spawnRate = rate; }

    public boolean isDebugMode() { return debugMode; }
    public void setDebugMode(boolean debug) { this.debugMode = debug; }
}