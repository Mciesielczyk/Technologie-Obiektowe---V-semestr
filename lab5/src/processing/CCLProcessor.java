package processing;

import java.awt.*;
import java.util.*;

import model.BlobStats;
import model.Pixel;
import java.util.*;
import java.util.List;

public class CCLProcessor {

    public List<BlobStats> extractBlobs(int[][] binaryFrame, boolean filtered) {
        int height = binaryFrame.length;
        int width = binaryFrame[0].length;
        int[][] labeled = new int[height][width];
        List<BlobStats> allBlobs = new ArrayList<>();
        int currentLabel = 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (binaryFrame[y][x] == 255 && labeled[y][x] == 0) {
                    BlobStats blob = new BlobStats();
                    blob.label = currentLabel;
                    floodFill(binaryFrame, labeled, x, y, currentLabel, blob);
                    allBlobs.add(blob);
                    currentLabel++;
                }
            }
        }

        if (filtered) {
            // MODYFIKACJA: Usuwamy bloby mniejsze niż 3 piksele (szum)
            allBlobs.removeIf(b -> b.getArea() < 3);
        }

        return allBlobs;
    }

    private void floodFill(int[][] binary, int[][] labeled, int x, int y, int label, BlobStats blob) {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x, y));

        while (!stack.isEmpty()) {
            Point p = stack.pop();
            if (p.x < 0 || p.x >= binary[0].length || p.y < 0 || p.y >= binary.length) continue;
            if (binary[p.y][p.x] != 255 || labeled[p.y][p.x] != 0) continue;

            labeled[p.y][p.x] = label;
            blob.pixels.add(new Pixel(p.x, p.y, 255));

            // Dodajemy sąsiadów na stos (bezpieczne dla pamięci)
            stack.push(new Point(p.x + 1, p.y));
            stack.push(new Point(p.x - 1, p.y));
            stack.push(new Point(p.x, p.y + 1));
            stack.push(new Point(p.x, p.y - 1));
            // Opcjonalnie dodaj ukosy, jeśli chcesz 8-sąsiedztwo
        }
    }

    // Metoda pomocnicza do rysowania blobów w UI
    public int[][] getVisualization(int width, int height, List<BlobStats> blobs) {
        int[][] output = new int[height][width];
        for (BlobStats b : blobs) {
            int color = (b.label * 50) % 200 + 55; // Różne odcienie jasnego szarego
            for (Pixel p : b.pixels) output[p.y()][p.x()] = color;
        }
        return output;
    }
}