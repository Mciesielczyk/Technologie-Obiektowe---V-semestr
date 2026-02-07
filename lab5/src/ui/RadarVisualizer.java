package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RadarVisualizer extends JPanel {
    private int[][] frame;
    private String title;

    public RadarVisualizer(String title) {
        this.title = title;
        this.setPreferredSize(new Dimension(400, 400));
    }

    public void updateFrame(int[][] newFrame) {
        this.frame = newFrame;
        this.repaint(); // Odśwież widok
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (frame == null) return;

        int rows = frame.length;
        int cols = frame[0].length;

        // Skalowanie pikseli do rozmiaru okna
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int val = frame[y][x];
                g.setColor(new Color(val, val, val)); // Skala szarości
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }

        g.setColor(Color.GREEN);
        g.drawString(title, 10, 20);
    }
}