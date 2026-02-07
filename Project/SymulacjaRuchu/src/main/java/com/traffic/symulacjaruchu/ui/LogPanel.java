package com.traffic.symulacjaruchu.ui;

import com.traffic.symulacjaruchu.model.vehicles.Vehicle;
import com.traffic.symulacjaruchu.patterns.observer.TrafficObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LogPanel extends VBox implements TrafficObserver {
    private final ObservableList<LogEntry> allLogs = FXCollections.observableArrayList();
    private final FilteredList<LogEntry> filteredLogs = new FilteredList<>(allLogs, p -> true);

    public LogPanel() {
        setSpacing(10);
        setPadding(new Insets(10));
        setPrefWidth(300);
        setStyle("-fx-background-color: #2c3e50; -fx-border-color: #34495e;");

        Label title = new Label("System Logs");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Przyciski filtrowania
        HBox filters = new HBox(5);
        Button btnAll = new Button("Wszystkie");
        Button btnBlock = new Button("Blokady");
        Button btnTraffic = new Button("Ruch");
        Button btnJam = new Button("Korki");

        btnAll.setOnAction(e -> filteredLogs.setPredicate(log -> true));
        btnBlock.setOnAction(e -> filteredLogs.setPredicate(log -> log.type == LogType.BLOCKADE));
        btnTraffic.setOnAction(e -> filteredLogs.setPredicate(log -> log.type == LogType.TRAFFIC_FLOW));
        btnJam.setOnAction(e -> filteredLogs.setPredicate(log -> log.type == LogType.CONGESTION));

        filters.getChildren().addAll(btnAll, btnBlock, btnTraffic, btnJam);
        // Lista logów
        ListView<LogEntry> listView = new ListView<>(filteredLogs);
        listView.setPrefHeight(400);
        listView.setStyle("-fx-control-inner-background: #34495e; -fx-text-fill: white;");

        // Custom cell factory dla kolorów - teraz 3 kolory!
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(LogEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setText(item.message);
                    switch (item.type) {
                        case BLOCKADE -> setTextFill(javafx.scene.paint.Color.ORANGERED);
                        case CONGESTION -> setTextFill(javafx.scene.paint.Color.YELLOW);
                        case TRAFFIC_FLOW -> setTextFill(javafx.scene.paint.Color.LIGHTGREEN);
                    }
                }
            }
        });
        getChildren().addAll(title, filters, listView);
    }


    @Override
    public void onVehicleSpawned(Vehicle v) {
        // Platform.runLater jest konieczne, by UI odświeżył się bezpiecznie
        javafx.application.Platform.runLater(() ->
                addLog("Nowy pojazd: " + v.getClass().getSimpleName(), LogType.TRAFFIC_FLOW)
        );
    }

    @Override
    public void onRoadBlocked(String info, boolean isBlocked) {
        javafx.application.Platform.runLater(() ->
                addLog((isBlocked ? "ZABLOKOWANO: " : "OTWARTO: ") + info, LogType.BLOCKADE)
        );
    }

    @Override
    public void onCongestionDetected(String roadId, int count) {
        javafx.application.Platform.runLater(() ->
                addLog("KOREK (" + count + " aut): " + roadId, LogType.CONGESTION)
        );
    }

    @Override
    public void onVehicleFinished(Vehicle v) {
        javafx.application.Platform.runLater(() ->
                addLog("Zakończono kurs: " + v.getClass().getSimpleName(), LogType.TRAFFIC_FLOW)
        );
    }


    public void addLog(String message, LogType type) {
        // Dodajemy na początek listy (najnowsze u góry)
        allLogs.add(0, new LogEntry(message, type));
    }

    public enum LogType { BLOCKADE, TRAFFIC_FLOW, CONGESTION } // Nowe typy
    private record LogEntry(String message, LogType type) {}
}