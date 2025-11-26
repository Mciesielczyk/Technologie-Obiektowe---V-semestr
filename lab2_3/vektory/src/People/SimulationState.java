package People;

import java.util.List;

public class SimulationState {
    private final List<Person> populationCopy;
    private final double time;

    public SimulationState(List<Person> populationCopy, double time) {
        this.populationCopy = populationCopy;
        this.time = time;
    }

    public List<Person> getPopulationCopy() {
        return populationCopy;
    }

    public double getTime() {
        return time;
    }
}
