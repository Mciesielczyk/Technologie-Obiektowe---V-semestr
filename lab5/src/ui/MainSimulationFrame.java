package ui;

import javax.swing.*;
import java.awt.*;

public class MainSimulationFrame extends JFrame {
    private RadarVisualizer rawPanel;
    private RadarVisualizer processedPanel;
    private String currentMode = "OTSU";
    private JSlider thresholdSlider;
    private JSlider noiseSlider;
    private JSlider cclFilterSlider;
    private JLabel statusLabel;

    public MainSimulationFrame() {
        setTitle("Radar Tracking Debugger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. INICJALIZACJA KOMPONENTÓW STERUJĄCYCH
        noiseSlider = new JSlider(0, 255, 120);
        thresholdSlider = new JSlider(50, 200, 100);
        thresholdSlider.setMajorTickSpacing(50);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        cclFilterSlider = new JSlider(1, 20, 3);

        // 2. PANEL GÓRNY (SUWAKI)
        JPanel sliderPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Parametry Przetwarzania"));
        sliderPanel.add(new JLabel(" Poziom Szumu:"));
        sliderPanel.add(noiseSlider);
        sliderPanel.add(new JLabel(" Otsu Modifier (%):"));
        sliderPanel.add(thresholdSlider);
        sliderPanel.add(new JLabel(" Min. Wielkość Bloba (CCL):"));
        sliderPanel.add(cclFilterSlider);

        // 3. PANEL ŚRODKOWY (WIZUALIZACJA RADARU)
        JPanel viewPanel = new JPanel(new GridLayout(1, 2));
        rawPanel = new RadarVisualizer("Sygnał Surowy");
        processedPanel = new RadarVisualizer("Podgląd Procesu");
        viewPanel.add(rawPanel);
        viewPanel.add(processedPanel);

        // 4. PANEL DOLNY (PRZYCISKI + STATUS)
        // Tworzymy kontener, który pomieści obie rzeczy na dole
        JPanel bottomContainer = new JPanel();
        bottomContainer.setLayout(new BoxLayout(bottomContainer, BoxLayout.Y_AXIS));

        // Podpanel przycisków
        JPanel buttonsPanel = new JPanel();
        String[] modes = {"OTSU", "CCL_RAW", "CCL_FILTERED", "TRACKING"};
        for (String mode : modes) {
            JButton btn = new JButton(mode);
            btn.addActionListener(e -> currentMode = mode);
            buttonsPanel.add(btn);
        }

        // Podpanel statusu
        statusLabel = new JLabel(" Inicjalizacja systemu...");
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(Color.BLUE);

        bottomContainer.add(buttonsPanel);
        bottomContainer.add(statusLabel);

        // 5. SKŁADANIE CAŁOŚCI W OKNIE
        add(sliderPanel, BorderLayout.NORTH);
        add(viewPanel, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Metody odświeżania i pobierania danych
    public void refresh(int[][] raw, int[][] processed, String activeMode) {
        rawPanel.updateFrame(raw);
        processedPanel.updateFrame(processed);
    }

    public void updateStatus(int blobs, int tracks, int fps, int threshold) {
        statusLabel.setText(String.format(
                " Detekcje (CCL): %d | Ścieżki (MHT): %d | FPS: %d | Próg: %d",
                blobs, tracks, fps, threshold
        ));

        if (fps < 15 || blobs > 50) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(new Color(0, 100, 0));
        }
    }

    public String getCurrentMode() { return currentMode; }
    public double getThresholdModifier() { return thresholdSlider.getValue() / 100.0; }
    public int getNoiseLevel() { return noiseSlider.getValue(); }
    public int getMinBlobSize() { return cclFilterSlider.getValue(); }
}