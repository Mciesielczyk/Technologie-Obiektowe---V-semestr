package States;
import People.*;

import java.io.Serializable;

public class InfectedState implements PersonState, Serializable {


    @Override
    public void update(Person person, double dt) {
        person.infenctionTime+=dt;

        if(person.infenctionTime>= Person.recoveryTime){
            person.recover();
        }
    }

    @Override
    public void onContact(Person self, Person other, double dt) {
        // próba zakażenia drugiej osoby (jeśli jest zdrowa)
        if (!other.isInfected() && !other.isImmune()) {
            double distance = self.distanceTo(other);

            if (distance <= self.infectionRadius) {
                other.timeSince += dt;
                other.tooClose = true;
                if (other.timeSince >= Person.infenctionTimeCzas) {
                    double chance = self.symptot ? 1.0 : 0.5; // 100% lub 50%
                    if (Math.random() < chance) {
                        boolean symptomatic = Math.random() < 0.5;
                        other.infect(symptomatic);
                    }
                   // other.timeSince = 0;
                }
            } else {
               // other.timeSince = 0;
            }
        }
    }
}
