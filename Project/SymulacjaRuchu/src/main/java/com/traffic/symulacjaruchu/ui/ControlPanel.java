package com.traffic.symulacjaruchu.ui;

import com.traffic.symulacjaruchu.core.TrafficSettings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {
    public ControlPanel() {
        setSpacing(15);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");
        setPrefWidth(250);

        // Suwak prędkości
        Label speedLabel = new Label("Globalna Prędkość:");
        Slider speedSlider = new Slider(0.1, 5.0, 1.0);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(1.0);
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                TrafficSettings.getInstance().setGlobalSpeedMultiplier(newVal.doubleValue())
        );

        // Suwak spawnu
        Label spawnLabel = new Label("Częstotliwość Spawnu (ms):");
        Slider spawnSlider = new Slider(200, 5000, 2000);
        spawnSlider.setShowTickLabels(true);
        spawnSlider.setShowTickMarks(true);
        spawnSlider.setMajorTickUnit(1000);
        spawnSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                TrafficSettings.getInstance().setSpawnRate(newVal.intValue())
        );

        getChildren().addAll(speedLabel, speedSlider, spawnLabel, spawnSlider);
    }
}