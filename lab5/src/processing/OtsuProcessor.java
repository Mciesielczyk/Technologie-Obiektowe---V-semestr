package processing;

public class OtsuProcessor {

    public int findThreshold(int[][] greyImage) {
        int[] histogram = new int[256];
        int total = greyImage.length * greyImage[0].length;

        for (int[] row : greyImage) {
            for (int val : row) histogram[val]++;
        }

        double sum = 0;
        for (int i = 0; i < 256; i++) sum += i * histogram[i];

        double sumB = 0;
        int wB = 0;
        double maxVariance = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) continue;
            int wF = total - wB;
            if (wF == 0) break;

            sumB += (double) (i * histogram[i]);
            double mB = sumB / wB;
            double mF = (sum - sumB) / wF;

            // Wariancja międzyklasowa
            double varBetween = (double) wB * (double) wF * Math.pow(mB - mF, 2);

            if (varBetween > maxVariance) {
                maxVariance = varBetween;
                threshold = i;
            }
        }
        return threshold;
    }

    public int[][] applyThreshold(int[][] frame, int threshold) {
        int[][] binary = new int[frame.length][frame[0].length];
        for (int y = 0; y < frame.length; y++) {
            for (int x = 0; x < frame[0].length; x++) {
                binary[y][x] = (frame[y][x] > threshold) ? 255 : 0;
            }
        }
        return binary;
    }
}