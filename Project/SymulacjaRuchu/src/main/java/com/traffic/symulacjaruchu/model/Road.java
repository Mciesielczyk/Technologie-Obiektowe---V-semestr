package com.traffic.symulacjaruchu.model;


public class Road {
    private final Intersection start;
    private final Intersection end;
    private double speedLimit;
    private boolean blocked = false;



    public enum Priority { NORMAL, YIELD, MAIN }
    private Priority priority = Priority.NORMAL;

    public Road(Intersection start, Intersection end) {
        this.start = start;
        this.end = end;
        this.speedLimit = 50.0;
    }

    public double getSpeedLimit() {
        return speedLimit;
    }
    public void setSpeedLimit(double speedLimit) {
        this.speedLimit = speedLimit;
    }
    public Intersection getStart() {
        return start;
    }
    public Intersection getEnd() {
        return end;
    }

    public void toggleBlock() {
        this.blocked = !this.blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    public void setPriority(Priority p) { this.priority = p; }
    public Priority getPriority() { return priority; }
}
