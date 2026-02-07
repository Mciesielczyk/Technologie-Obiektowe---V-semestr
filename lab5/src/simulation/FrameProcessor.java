package simulation;

import model.BlobStats;
import model.Track;
import processing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.Color;

public class FrameProcessor {
    private final OtsuProcessor otsu = new OtsuProcessor();
    private final CCLProcessor ccl = new CCLProcessor();
    private final TrackingProcessor trackingVis = new TrackingProcessor();

    // Dodajemy width i height jako parametry metody
    public int[][] process(int[][] rawFrame, String mode, double otsuMod, int minArea, MHTTracker tracker, int width, int height) {
        int baseThreshold = otsu.findThreshold(rawFrame);
        int modifiedThreshold = (int)(baseThreshold * otsuMod);
        int[][] binaryFrame = otsu.applyThreshold(rawFrame, Math.min(255, modifiedThreshold));

        if (mode.startsWith("CCL")) {
            List<BlobStats> blobs = ccl.extractBlobs(binaryFrame, false);
            blobs.removeIf(b -> b.getArea() < minArea);
            // Używamy przekazanych wymiarów
            return ccl.getVisualization(width, height, blobs);

        } else if (mode.equals("TRACKING")) {
            List<BlobStats> detectedBlobs = ccl.extractBlobs(binaryFrame, false);
            detectedBlobs.removeIf(b -> b.getArea() < minArea);

            List<Track> tracks = tracker.update(detectedBlobs);
            // Używamy przekazanych wymiarów
            BufferedImage img = trackingVis.drawTracks(width, height, tracks);
            return convertToGray(img);

        } else {
            return binaryFrame;
        }
    }

    public int getThreshold(int[][] rawFrame, double otsuMod) {
        return (int)(otsu.findThreshold(rawFrame) * otsuMod);
    }

    private int[][] convertToGray(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int[][] result = new int[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                result[y][x] = new Color(img.getRGB(x, y)).getGreen();
            }
        }
        return result;
    }
}

