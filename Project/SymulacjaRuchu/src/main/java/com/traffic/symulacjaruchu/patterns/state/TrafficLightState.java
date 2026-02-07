package com.traffic.symulacjaruchu.patterns.state;
import javafx.scene.paint.Color;

public interface TrafficLightState {
    boolean canEnter();
    Color getColor();
    TrafficLightState next();
}
