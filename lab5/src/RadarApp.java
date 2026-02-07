

import simulation.SimulationEngine;

public class RadarApp {
    public static void main(String[] args) throws InterruptedException {
        SimulationEngine engine = new SimulationEngine(300, 300);
        engine.start();
    }
}