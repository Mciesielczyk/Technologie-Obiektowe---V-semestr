package Model;

import java.util.Random;

public class Event {
    private EventType type;
    private Location location;

    public Event(Location location) {
        this.location = location;
        this.type = randomType();
    }


    public EventType randomType(){
        int value = new Random().nextInt(100);
        if(value<70){
            return EventType.MZ;
        }else {
            return EventType.PZ;
        }
    }

    public EventType getType() {
        return this.type;
    }
    public Location getLocation() {
        return this.location;
    }
}
