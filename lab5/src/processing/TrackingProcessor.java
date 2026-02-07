package processing;

import model.BlobStats;
import model.Track;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TrackingProcessor {

    public BufferedImage drawTracks(int width, int height, List<Track> tracks) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();

        // Tło czarne
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);

        // Włączamy wygładzanie krawędzi (antyaliasing) dla ładniejszych linii
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Track track : tracks) {
            List<BlobStats> history = track.getHistory();
            if (history.isEmpty()) continue;

            // 1. Rysowanie linii trajektorii (ogona)
            g2.setColor(new Color(0, 100, 0)); // Ciemnozielony
            for (int i = 0; i < history.size() - 1; i++) {
                BlobStats b1 = history.get(i);
                BlobStats b2 = history.get(i + 1);
                g2.drawLine((int)b1.getMeanX(), (int)b1.getMeanY(),
                        (int)b2.getMeanX(), (int)b2.getMeanY());
            }

            // 2. Rysowanie aktualnej pozycji (ostatni blob)
            BlobStats last = track.getLastBlob();
            double mx = last.getMeanX();
            double my = last.getMeanY();
            double sx = last.getStdDevX();
            double sy = last.getStdDevY();

            // Elipsa niepewności (Rozkład Gaussa)
            g2.setColor(new Color(0, 255, 0, 150)); // Jasnozielony półprzezroczysty
            int ew = (int)(4 * sx) + 2;
            int eh = (int)(4 * sy) + 2;
            g2.drawOval((int)(mx - 2*sx), (int)(my - 2*sy), ew, eh);

            // Numer ID obiektu
            g2.setColor(Color.WHITE);
            g2.drawString("ID:" + track.getId(), (int)mx + 5, (int)my - 5);
        }

        g2.dispose();
        return img;
    }
}