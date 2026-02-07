package com.traffic.symulacjaruchu.model;

import com.traffic.symulacjaruchu.patterns.state.*;
import javafx.scene.paint.Color;

public class TrafficLight {
    private TrafficLightState currentState;
    private int timer = 0;
    private final int changeInterval = 200; // Zmiana co 200 klatek

    public TrafficLight() {
        this.currentState = new RedState(); // Zaczynamy od czerwonego
    }

    public void update() {
        timer++;
        if (timer >= changeInterval) {
            currentState = currentState.next();
            timer = 0;
        }
    }

    public boolean isGreen() { return currentState.canEnter(); }
    public Color getColor() { return currentState.getColor(); }
}