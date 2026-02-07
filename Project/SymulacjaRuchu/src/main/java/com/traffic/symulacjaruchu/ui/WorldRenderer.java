package com.traffic.symulacjaruchu.ui;

import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;
import com.traffic.symulacjaruchu.patterns.strategy.FourWayLightsStrategy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class WorldRenderer {

    public void render(GraphicsContext gc, List<Road> roads, List<Vehicle> vehicles, List<Intersection> intersections) {
        gc.setFill(Color.web("#ecf0f1"));
        gc.fillRect(0, 0, 1500, 1200);

        for (Road r : roads) {
            drawRoadLine(gc, r, vehicles);
        }

        for (Road r : roads) {
            if (r.getPriority() != Road.Priority.NORMAL) {
                drawPrioritySignsOnRoad(gc, r);
            }
        }
        renderBlockades(gc, roads);

        // 3. Rysowanie skrzyżowań
        for (Intersection inter : intersections) {
            drawIntersection(gc, inter);
        }

        // 4. Rysowanie pojazdów (Warstwa górna)
        for (Vehicle v : vehicles) {
            drawVehicle(gc, v);
        }
    }

    private void drawRoadLine(GraphicsContext gc, Road r, List<Vehicle> vehicles) {
        double x1 = r.getStart().getX();
        double y1 = r.getStart().getY();
        double x2 = r.getEnd().getX();
        double y2 = r.getEnd().getY();

        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);

        if (length == 0) return;


        double offsetX = (-dy / length) * 5;
        double offsetY = (dx / length) * 5;

        // Heatmapa - kolor zależny od natężenia
        long waitingCount = vehicles.stream()
                .filter(v -> v.getCurrentRoad() == r && v.isWaiting())
                .count();

        if (r.isBlocked()) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
        } else if (waitingCount > 3) {
            gc.setStroke(Color.web("#c0392b"));
            gc.setLineWidth(5);
        } else if (waitingCount > 0) {
            gc.setStroke(Color.web("#e67e22"));
            gc.setLineWidth(4);
        } else {
            gc.setStroke(Color.web("#34495e"));
            gc.setLineWidth(4);
        }

        gc.strokeLine(x1 + offsetX, y1 + offsetY, x2 + offsetX, y2 + offsetY);
    }

    private void drawIntersection(GraphicsContext gc, Intersection inter) {
        if (inter.getId().contains("_")) {
            // RONDO
            gc.setFill(Color.web("#7f8c8d"));
            gc.fillOval(inter.getX() - 15, inter.getY() - 15, 30, 30);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeOval(inter.getX() - 15, inter.getY() - 15, 30, 30);
        } else {
            // SKRZYŻOWANIE
            gc.setFill(Color.web("#2c3e50"));
            gc.fillOval(inter.getX() - 12, inter.getY() - 12, 24, 24);

            if (inter.getStrategy() instanceof FourWayLightsStrategy) {
                drawLights(gc, inter);
            }
        }
    }

    private void drawVehicle(GraphicsContext gc, Vehicle v) {
        double x = v.getCurrentX();
        double y = v.getCurrentY();

        if (v instanceof com.traffic.symulacjaruchu.model.vehicles.Ambulance) {
            gc.setFill(Color.WHITE);
            gc.fillOval(x - 8, y - 8, 16, 16);
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(x - 8, y - 8, 16, 16);
            if (System.currentTimeMillis() % 600 < 300) {
                gc.setFill(Color.BLUE);
                gc.fillOval(x - 3, y - 12, 6, 6);
            }
        } else if (v instanceof com.traffic.symulacjaruchu.model.vehicles.Bus) {
            gc.setFill(v.isWaiting() ? Color.ORANGE : Color.YELLOW);
            gc.fillRect(x - 10, y - 6, 20, 12);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x - 10, y - 6, 20, 12);
        } else if (v instanceof com.traffic.symulacjaruchu.model.vehicles.Truck) {
            gc.setFill(Color.DARKBLUE);
            gc.fillRect(x - 14, y - 7, 28, 14);
        } else if (v instanceof com.traffic.symulacjaruchu.model.vehicles.CrazyDriver) {
            gc.setFill(Color.PURPLE);
            gc.fillOval(x - 6, y - 6, 12, 12);
        } else {
            gc.setFill(v.isWaiting() ? Color.YELLOW : Color.web("#e74c3c"));
            gc.fillOval(x - 7, y - 7, 14, 14);
        }
    }

    private void renderBlockades(GraphicsContext gc, List<Road> roads) {
        for (Road r1 : roads) {
            if (!r1.isBlocked()) continue;


            Road r2 = roads.stream()
                    .filter(other -> other.getStart() == r1.getEnd() && other.getEnd() == r1.getStart())
                    .findFirst()
                    .orElse(null);

            double midX = (r1.getStart().getX() + r1.getEnd().getX()) / 2;
            double midY = (r1.getStart().getY() + r1.getEnd().getY()) / 2;

            if (r2 != null && r2.isBlocked()) {
                if (System.identityHashCode(r1) < System.identityHashCode(r2)) {
                    drawNoEntrySign(gc, midX, midY, 22);
                }
            } else {
                double dx = r1.getEnd().getX() - r1.getStart().getX();
                double dy = r1.getEnd().getY() - r1.getStart().getY();
                double len = Math.sqrt(dx * dx + dy * dy);
                double ox = (-dy / len) * 7;
                double oy = (dx / len) * 7;

                drawNoEntrySign(gc, midX + ox, midY + oy, 14);
            }
        }
    }

    private void drawNoEntrySign(GraphicsContext gc, double x, double y, double size) {
        gc.setFill(Color.RED);
        gc.fillOval(x - size / 2, y - size / 2, size, size);
        gc.setFill(Color.WHITE);
        gc.fillRect(x - (size * 0.35), y - (size * 0.1), size * 0.7, size * 0.2);
    }

    private void drawLights(GraphicsContext gc, Intersection inter) {
        boolean nsGreen = inter.isNorthSouthGreen();
        double offset = 18;
        double lightSize = 8;

        gc.setFill(nsGreen ? Color.CHARTREUSE : Color.RED);
        gc.fillOval(inter.getX() - lightSize / 2, inter.getY() - offset, lightSize, lightSize);
        gc.fillOval(inter.getX() - lightSize / 2, inter.getY() + offset - lightSize, lightSize, lightSize);

        gc.setFill(!nsGreen ? Color.CHARTREUSE : Color.RED);
        gc.fillOval(inter.getX() - offset, inter.getY() - lightSize / 2, lightSize, lightSize);
        gc.fillOval(inter.getX() + offset - lightSize, inter.getY() - lightSize / 2, lightSize, lightSize);
    }

    private void drawPrioritySignsOnRoad(GraphicsContext gc, Road r) {
        double x1 = r.getStart().getX();
        double y1 = r.getStart().getY();
        double x2 = r.getEnd().getX();
        double y2 = r.getEnd().getY();

        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len < 35) return;

        double ux = dx / len;
        double uy = dy / len;

        // Ustawienie znaku na poboczu (offset lane + side offset)
        double signX = x2 - ux * 35 + uy * 18;
        double signY = y2 - uy * 35 - ux * 18;

        if (r.getPriority() == Road.Priority.YIELD) {
            drawYieldSign(gc, signX - 7, signY - 6);
        } else if (r.getPriority() == Road.Priority.MAIN) {
            drawMainRoadSign(gc, signX - 8, signY - 8);
        }
    }

    private void drawYieldSign(GraphicsContext gc, double x, double y) {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        double[] xs = {x, x + 14, x + 7};
        double[] ys = {y, y, y + 12};
        gc.fillPolygon(xs, ys, 3);
        gc.strokePolygon(xs, ys, 3);
    }

    private void drawMainRoadSign(GraphicsContext gc, double x, double y) {
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        double[] xs = {x, x + 8, x + 16, x + 8};
        double[] ys = {y + 8, y, y + 8, y + 16};
        gc.fillPolygon(xs, ys, 4);
        gc.strokePolygon(xs, ys, 4);
    }
}