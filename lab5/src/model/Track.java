package model;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private final int id;
    private final List<BlobStats> history = new ArrayList<>();
    private int missingFrames = 0; // Ile klatek obiekt był niewidoczny

    public Track(int id, BlobStats initialBlob) {
        this.id = id;
        this.history.add(initialBlob);
    }

    public void addDetection(BlobStats blob) {
        history.add(blob);
        missingFrames = 0;
    }

    public void markMissing() {
        missingFrames++;
    }

    public BlobStats getLastBlob() {
        return history.get(history.size() - 1);
    }

    public int getMissingFrames() { return missingFrames; }
    public List<BlobStats> getHistory() { return history; }
    public int getId() { return id; }
}