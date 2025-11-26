package People;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<PupulationManager> states = new ArrayList<>();

    public void addState(PupulationManager state) {
        states.add(state);
    }

    public PupulationManager getState(int index) {
        return states.get(index);
    }

    public int size() {
        return states.size();
    }

    public PupulationManager getLastState() {
        if(states.isEmpty()) return null;
        return states.get(states.size() - 1);
    }
}
