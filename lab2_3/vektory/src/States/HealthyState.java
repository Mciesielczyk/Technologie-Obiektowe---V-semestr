package States;
import People.*;

import java.io.Serializable;

public class HealthyState implements PersonState, Serializable {
    @Override
    public void update(Person person, double dt) {
            if(!person.tooClose){
                person.timeSince=0;
            }
            person.tooClose=false;
    }
    @Override
    public void onContact(Person person, Person otherPerson, double dt) { //tylko w infected sprawzamy czy jest blisko
      /*  if(otherPerson.isInfected()){
            double distance = person.distanceTo(otherPerson);

            if(distance < Person.infectionRadius){
                person.timeSince+=dt;
                if(person.timeSince>= Person.infenctionTimeCzas)
                {
                    double chance = otherPerson.symptot ? 1.0:0.5;
                    if(Math.random()<chance){
                        boolean sym = Math.random()<0.5;
                        person.infect(sym);

                    }
                    //else person.timeSince=0;
                }
            }
        }*/
    }
}
