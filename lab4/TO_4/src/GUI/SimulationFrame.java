package GUI;

import Observers.GuiObserver;
import Simulations.Simulation;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JFrame {

    private JTextArea logArea;
    private JButton startButton;
    private Simulation simulation;
    private Thread simulationThread;
    private MapPanel mapPanel;

    public SimulationFrame() {

        setTitle("SKKM – Symulacja");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);

        mapPanel = new MapPanel();

        JScrollPane scrollPane = new JScrollPane(logArea);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                mapPanel,
                scrollPane
        );

        splitPane.setDividerLocation(450);


        add(splitPane, BorderLayout.CENTER);

        startButton = new JButton("START");
        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startSimulation());

        setVisible(true);
    }

    private void startSimulation() {

        startButton.setEnabled(false);


        simulation = new Simulation();
        simulation.addObserver(new GuiObserver(logArea));

        mapPanel.setJrgList(simulation.getUnits());

        Timer repaintTimer = new Timer(200, e -> {
            mapPanel.setEvent(simulation.getLastEvent());
            mapPanel.setActiveJrg(simulation.getActiveJrg());
            mapPanel.repaint();
        });
        repaintTimer.start();



        simulationThread = new Thread(() -> {
            try {
                simulation.start();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        simulationThread.start();
    }

}
