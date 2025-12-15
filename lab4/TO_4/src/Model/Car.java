package Model;

import States.FreeState;
import States.State;

public class Car {
    private int id;
    private State state;

    private long timeToReach;
    private long timeToWork;
    private long timeToReturn;
    private long elapsedTime;
    private EventCar lastEvent = EventCar.NONE;

    public Car(int id){
        this.state= new FreeState();
        this.id = id;
    }
    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

    public void dispatch(long timeToReach,long timeToWork,long timeToReturn){
        this.timeToReach = timeToReach;
        this.timeToWork = timeToWork;
        this.timeToReturn = timeToReturn;
        state.dispatch(this, timeToReach,timeToWork,timeToReturn);

    }
    public void returnToBase(){
        state.returnToBase(this);
    }
    public boolean isFree(){
        return state.isFree();
    }

    public EventCar check(){
        EventCar current = state.checkTime(timeToReach,timeToWork,timeToReturn,elapsedTime,this);
        if (current != lastEvent) {
            lastEvent = current;
            return current;
        }
        return EventCar.NONE;
    }

    public int getId(){
        return this.id;
    }
    public void setTimeAc(long time){
        this.elapsedTime=time;
    }
    public void setTimeToDo(long time){
       // this.timeToDo=time;
    }


}
