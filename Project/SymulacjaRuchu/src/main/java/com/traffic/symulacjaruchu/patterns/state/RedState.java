package com.traffic.symulacjaruchu.patterns.state;

import javafx.scene.paint.Color;

public class RedState implements TrafficLightState {
    public boolean canEnter() { return false; }
    public Color getColor() { return Color.RED; }
    public TrafficLightState next() { return new GreenState(); }
}

