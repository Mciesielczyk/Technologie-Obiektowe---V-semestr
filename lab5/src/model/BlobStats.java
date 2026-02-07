package model;

import java.util.ArrayList;
import java.util.List;

public class BlobStats {
    public List<Pixel> pixels = new ArrayList<>();
    public int label;

    public double getMeanX() {
        return pixels.stream().mapToInt(p -> p.x()).average().orElse(0);
    }

    public double getMeanY() {
        return pixels.stream().mapToInt(p -> p.y()).average().orElse(0);
    }

    // Odchylenie standardowe X: sqrt( suma( (xi - meanX)^2 ) / N )
    public double getStdDevX() {
        double meanX = getMeanX();
        double variance = pixels.stream()
                .mapToDouble(p -> Math.pow(p.x() - meanX, 2))
                .sum() / pixels.size();
        return Math.sqrt(variance);
    }

    // Odchylenie standardowe Y
    public double getStdDevY() {
        double meanY = getMeanY();
        double variance = pixels.stream()
                .mapToDouble(p -> Math.pow(p.y() - meanY, 2))
                .sum() / pixels.size();
        return Math.sqrt(variance);
    }

    public int getArea() {
        return pixels.size();
    }
}