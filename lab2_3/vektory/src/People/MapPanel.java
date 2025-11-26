package People;

import People.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class MapPanel extends JPanel {

    private List<Person> population;
    private double dt = 0.04; // 25 kroków na sekundę
    private Timer timer;

    private double mapWidth;
    private double mapHeight;

    public MapPanel(List<Person> population,double mapHeight,double mapWidth) {

        this.mapHeight=mapHeight;
        this.mapWidth=mapWidth;


        this.population = population;
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);

        // Timer do animacji
        timer = new Timer((int)(dt * 1000), e -> {
            // aktualizacja pozycji osobników
            repaint(); // odświeżenie panelu
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        for (Person p : population) {
            // kolor w zależności od stanu (zdrowy / zakażony / odporny)
            if (p.isInfected() && !p.symptot) {
                g.setColor(Color.RED);
            }
            else if (p.isInfected() && p.symptot){
                g.setColor(Color.BLACK);
            }else if (p.isImmune()) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.BLUE);
            }
            int px = (int) (p.getPosition()[0] / mapWidth * panelWidth);
            int py = (int) (p.getPosition()[1] / mapHeight * panelHeight);

            g.fillOval(px, py, 6, 6);
        }
    }

    public void stopAnimation() {
        if (timer != null) timer.stop();
    }

    // opcjonalnie metoda do zmiany dt
    public void setDt(double dt) {
        this.dt = dt;
        if (timer != null) {
            timer.setDelay((int)(dt * 1000));
        }
    }
    public void setPopulation(List<Person> population) {
        this.population = population;
    }

}
