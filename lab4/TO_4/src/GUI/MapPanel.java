package GUI;

import Model.Car;
import Model.Event;
import Model.JRG;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MapPanel extends JPanel {

    private List<JRG> jrgList;
    private Event currentEvent;
    private JRG activeJrg;

    private final double minLat = 49.95;
    private final double maxLat = 50.16;
    private final double minLon = 19.68;
    private final double maxLon = 20.03;

    public void setJrgList(List<JRG> jrgList) {
        this.jrgList = jrgList;
    }

    public void setEvent(Event event) {
        this.currentEvent = event;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (jrgList == null) return;

        // JRG
        g.setColor(Color.BLUE);
        for (JRG jrg : jrgList) {
            Point p = mapToScreen(jrg.getX(), jrg.getY());

            if (jrg.equals(activeJrg)) {
                g.setColor(Color.YELLOW);
                g.fillOval(p.x - 7, p.y - 7, 14, 14);

                g.setColor(Color.BLACK);
                g.drawOval(p.x - 7, p.y - 7, 14, 14);
            } else {
                g.setColor(Color.BLUE);
                g.fillOval(p.x - 5, p.y - 5, 10, 10);
            }

            g.drawString(jrg.getName(), p.x + 6, p.y);
        }


        // EVENT
        if (currentEvent != null) {
            g.setColor(Color.RED);
            Point p = mapToScreen(
                    currentEvent.getLocation().getX(),
                    currentEvent.getLocation().getY()
            );
            g.fillOval(p.x - 6, p.y - 6, 12, 12);
        }
    }

    private Point mapToScreen(double lat, double lon) {
        int w = getWidth();
        int h = getHeight();

        int x = (int) ((lon - minLon) / (maxLon - minLon) * w);
        int y = (int) ((maxLat - lat) / (maxLat - minLat) * h);

        return new Point(x, y);
    }
    public void setActiveJrg(JRG jrg) {
        this.activeJrg = jrg;
    }
}
