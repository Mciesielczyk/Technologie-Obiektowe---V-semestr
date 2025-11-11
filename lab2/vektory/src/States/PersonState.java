package States;
import People.*;
public interface PersonState {

    void update(Person person, double dt);

    void onContact(Person person, Person otherPerson, double dt);
}
