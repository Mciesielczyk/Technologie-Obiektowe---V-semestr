package generator;

import java.util.Random;

public class RadarGenerator {
    private final Random random = new Random();

    public int[][] generateRawFrame(int width, int height, int noiseLevel) {
        int[][] frame = new int[height][width];

        // Generowanie szumu tła (0 - noiseLevel)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = random.nextInt(noiseLevel);
            }
        }
        return frame;
    }

    public void injectObject(int[][] frame, int centerX, int centerY, int radius, int intensity) {
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (y >= 0 && y < frame.length && x >= 0 && x < frame[0].length) {
                    // Symulacja odbicia - środek najjaśniejszy
                    frame[y][x] = Math.min(255, frame[y][x] + intensity);
                }
            }
        }
    }
}
