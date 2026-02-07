package model;

import java.util.Random;

public class SimulatedObject {
    private double x, y;
    private double vx, vy;
    private final int size; // Promień obiektu (1 = mały, 3 = duży)

    public SimulatedObject(double x, double y, double vx, double vy, int size) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.size = size;
    }

    public void update(int width, int height) {
        x += vx;
        y += vy;

        // Odbijanie od ścian (żeby obiekty nie uciekały z ekranu)
        if (x < 0 || x >= width) vx *= -1;
        if (y < 0 || y >= height) vy *= -1;
    }

    // Gettery
    public double getX() { return x; }
    public double getY() { return y; }
    public int getSize() { return size; }
}