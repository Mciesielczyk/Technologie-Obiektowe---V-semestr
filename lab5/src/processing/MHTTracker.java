package processing;

import model.BlobStats;
import model.Track;
import java.util.*;

public class MHTTracker {
    private final List<Track> activeTracks = new ArrayList<>();
    private int nextId = 1;
    private final double MAX_DISTANCE = 15.0; // Maksymalny skok obiektu między klatkami

    public List<Track> update(List<BlobStats> currentBlobs) {
        List<BlobStats> unassignedBlobs = new ArrayList<>(currentBlobs);

        // 1. Próbuj przypisać nowe detekcje do istniejących ścieżek (Hipotezy)
        for (Track track : activeTracks) {
            BlobStats last = track.getLastBlob();
            BlobStats bestMatch = null;
            double minDistance = MAX_DISTANCE;

            for (BlobStats blob : unassignedBlobs) {
                double dist = Math.sqrt(Math.pow(last.getMeanX() - blob.getMeanX(), 2) +
                        Math.pow(last.getMeanY() - blob.getMeanY(), 2));
                if (dist < minDistance) {
                    minDistance = dist;
                    bestMatch = blob;
                }
            }

            if (bestMatch != null) {
                track.addDetection(bestMatch);
                unassignedBlobs.remove(bestMatch);
            } else {
                track.markMissing();
            }
        }

        // 2. Nowe detekcje, które nie pasują nigdzie, stają się nowymi ścieżkami (Nowe Hipotezy)
        for (BlobStats newBlob : unassignedBlobs) {
            activeTracks.add(new Track(nextId++, newBlob));
        }

        // 3. Usuń ścieżki, które zniknęły na zbyt długo (Odrzucanie hipotez)
        activeTracks.removeIf(t -> t.getMissingFrames() > 10);

        return activeTracks;
    }

    public int getActiveTracksCount() {
        return activeTracks.size();
    }
}