package simulation;

import generator.RadarDataGenerator;
import model.SimulatedObject;
import processing.MHTTracker;
import ui.MainSimulationFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {
    private final RadarDataGenerator generator;
    private final MainSimulationFrame gui;
    private final FrameProcessor processor;
    private final MHTTracker tracker = new MHTTracker();
    private final List<SimulatedObject> objects = new ArrayList<>();

    private final int width;
    private final int height;

    private long lastTime = System.currentTimeMillis();

    public SimulationEngine(int width, int height) {
        this.width = width;
        this.height = height;
        this.generator = new RadarDataGenerator(width, height);
        this.gui = new MainSimulationFrame();
        this.processor = new FrameProcessor();

        spawnInitialObjects();
    }

    public void start() throws InterruptedException {
        while (true) {
            String mode = gui.getCurrentMode();

            for (SimulatedObject obj : objects) {
                obj.update(this.width, this.height);
            }

            int[][] rawFrame = generator.generateNextFrame(objects, gui.getNoiseLevel());

            int[][] processedFrame = processor.process(
                    rawFrame, mode, gui.getThresholdModifier(),
                    gui.getMinBlobSize(), tracker, this.width, this.height
            );

            updateGUI(rawFrame, processedFrame, mode);

            Thread.sleep(50);
        }
    }

    private void updateGUI(int[][] raw, int[][] processed, String mode) {
        long now = System.currentTimeMillis();
        int fps = (int) (1000 / Math.max(1, (now - lastTime)));
        lastTime = now;

        int currentThreshold = processor.getThreshold(raw, gui.getThresholdModifier());

        gui.updateStatus(0, tracker.getActiveTracksCount(), fps, currentThreshold);
        gui.refresh(raw, processed, mode);
    }

    private void spawnInitialObjects() {
        for (int i = 0; i < 2; i++) {
            addNewObject();
        }
    }

    private void addNewObject() {
        Random r = new Random();

        objects.add(new SimulatedObject(
                r.nextInt(this.width), r.nextInt(this.height),
                (r.nextDouble() - 0.5) * 4, (r.nextDouble() - 0.5) * 4,
                r.nextInt(3) + 2
        ));
    }
}