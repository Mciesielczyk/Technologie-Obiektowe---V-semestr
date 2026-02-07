package com.traffic.symulacjaruchu.ui;

import com.traffic.symulacjaruchu.core.*;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;
import com.traffic.symulacjaruchu.patterns.observer.ConsoleTrafficLogger;
import com.traffic.symulacjaruchu.patterns.observer.TrafficObserver;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.traffic.symulacjaruchu.model.Road;
import java.util.ArrayList;

public class MainGui extends Application {
    private Simulation simulation;
    private WorldRenderer renderer = new WorldRenderer();
    private long lastSpawnTime = 0;
    private LogPanel logPanel = new LogPanel();

    @Override
    public void start(Stage stage) {
        // Inicjalizacja rdzenia
        simulation = new Simulation(new ArrayList<>());
        simulation.addObserver(new ConsoleTrafficLogger());
        CityGraphBuilder builder = new CityGraphBuilder();
        MapFactory.buildDefaultCity(simulation, builder);

        // UI - Mapa
        Canvas canvas = new Canvas(1500, 1200);
        StackPane canvasHolder = new StackPane(canvas);
        ScrollPane scrollPane = new ScrollPane(canvasHolder);
        scrollPane.setPannable(true);

        // Zoom i Kliknięcia
        setupInteractions(canvas, canvasHolder);

        // UI - Komponenty
        ControlPanel controlPanel = new ControlPanel();

        // Layout główny
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setRight(controlPanel); // Suwaki lądują po prawej!
        VBox rightUI = new VBox(logPanel, controlPanel);
        root.setRight(rightUI);
        Scene scene = new Scene(root, 1200, 900);
        stage.setScene(scene);
        stage.setTitle("Traffic Simulation Pro");

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastSpawnTime > TrafficSettings.getInstance().getSpawnRate()) {
                    if (simulation.getVehicles().size() < 50) simulation.spawnVehicle();
                    lastSpawnTime = currentTime;
                }

                simulation.update();
                renderer.render(canvas.getGraphicsContext2D(),
                        simulation.getRoads(), simulation.getVehicles(), builder.getIntersections());
            }
        }.start();

        simulation.addObserver(logPanel);


        stage.show();
    }

    private void setupInteractions(Canvas canvas, StackPane holder) {
        // Zoom (Ctrl + Scroll)
        holder.setOnScroll(event -> {
            if (event.isControlDown()) {
                double zoom = event.getDeltaY() > 0 ? 1.1 : 0.9;
                holder.setScaleX(Math.max(0.5, Math.min(3.0, holder.getScaleX() * zoom)));
                holder.setScaleY(holder.getScaleX());
                event.consume();
            }
        });

        // Kliknięcia (Blokowanie dróg)
        canvas.setOnMouseClicked(event -> {
            double mx = event.getX();
            double my = event.getY();
            for (Road r1 : simulation.getRoads()) {
                if (getDistanceToSegment(mx, my, r1.getStart().getX(), r1.getStart().getY(), r1.getEnd().getX(), r1.getEnd().getY()) < 10) {
                    handleRoadCycle(r1);
                    break;
                }
            }
        });
    }

    private void handleRoadCycle(Road r1) {
        Road r2 = simulation.findReverseRoad(r1);
        String status;

        if (!r1.isBlocked() && (r2 == null || !r2.isBlocked())) {
            r1.setBlocked(true);
            status = "ZABLOKOWANO JEDEN KIERUNEK: " + r1.getStart().getId() + " -> " + r1.getEnd().getId();
        } else if (r1.isBlocked() && (r2 == null || !r2.isBlocked())) {
            r1.setBlocked(false); if (r2 != null) r2.setBlocked(true);
            status = "ZMIANA KIERUNKU BLOKADY: " + (r2 != null ? r2.getStart().getId() + " -> " + r2.getEnd().getId() : "Brak powrotnej");
        } else if (!r1.isBlocked() && (r2 != null && r2.isBlocked())) {
            r1.setBlocked(true); r2.setBlocked(true);
            status = "CAŁKOWITA BLOKADA ODCINKA: " + r1.getStart().getId() + " <-> " + r1.getEnd().getId();
        } else {
            r1.setBlocked(false); if (r2 != null) r2.setBlocked(false);
            status = "DROGA W PEŁNI ODBLOKOWANA: " + r1.getStart().getId() + " <-> " + r1.getEnd().getId();
        }
        simulation.notifyRoadBlocked(status, r1.isBlocked() || (r2 != null && r2.isBlocked()));    }

    private double getDistanceToSegment(double px, double py, double x1, double y1, double x2, double y2) {
        double l2 = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        if (l2 == 0) return Math.hypot(px - x1, py - y1);
        double t = Math.max(0, Math.min(1, ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / l2));
        return Math.hypot(px - (x1 + t * (x2 - x1)), py - (y1 + t * (y2 - y1)));
    }
}