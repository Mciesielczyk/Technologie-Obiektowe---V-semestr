package com.traffic.symulacjaruchu.patterns.state;
import javafx.scene.paint.Color;

public class GreenState implements TrafficLightState {
    public boolean canEnter() { return true; }
    public Color getColor() { return Color.GREEN; }
    public TrafficLightState next() { return new RedState(); }
}