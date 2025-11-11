package States;
import People.*;

import java.io.Serializable;

public class ImmuneState implements PersonState, Serializable {
    @Override
    public void update(Person person, double dt) {

    }

    @Override
    public void onContact(Person person, Person otherPerson, double dt) {

    }
}
