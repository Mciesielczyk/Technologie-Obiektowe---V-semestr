package generator;

import model.SimulatedObject;
import java.util.List;
import java.util.Random;

public class RadarDataGenerator {
    private final int width, height;
    private final Random rand = new Random();

    public RadarDataGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int[][] generateNextFrame(List<SimulatedObject> objects, int noiseLevel) {
        int[][] frame = new int[height][width];

        // 1. Szum tła
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = rand.nextInt(noiseLevel + 1);
            }
        }

        // 2. Rysowanie obiektów o różnej wielkości
        for (SimulatedObject obj : objects) {
            int cx = (int) obj.getX();
            int cy = (int) obj.getY();
            int s = obj.getSize();

            for (int i = -s; i <= s; i++) {
                for (int j = -s; j <= s; j++) {
                    int px = cx + i;
                    int py = cy + j;

                    if (px >= 0 && px < width && py >= 0 && py < height) {
                        // Jasność maleje wraz z odległością od środka (efekt rozmycia)
                        double dist = Math.sqrt(i*i + j*j);
                        if (dist <= s) {
                            int val = (int) (255 - (dist * (50 / s)));
                            frame[py][px] = Math.max(frame[py][px], val);
                        }
                    }
                }
            }
        }
        return frame;
    }
}