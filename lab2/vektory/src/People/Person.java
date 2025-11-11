package People;

import States.HealthyState;
import States.ImmuneState;
import States.InfectedState;
import States.PersonState;
import Vectors.Vector2D;

import java.io.Serializable;


public class Person implements Serializable , Cloneable{

    private static final long serialVersionUID = 1L; // opcjonalne, ale dobre praktyki

    public double infenctionTime=0;  //czas jaki juz jest zarazony sam, jak wiekszy od recovery to zmienia sie w zdrowego
    public static double recoveryTime =  20 + Math.random() * 10; // 20 <= value < 30


    public double timeSince=0; //czas jak dlugo juz jest obok zarazoneo
    public static double infenctionTimeCzas=3; //czas jak dlugo musi byc obok zarazonego


    public static double infectionRadius = 2;

    private Vector2D position;

    private double vx=Math.random(),vy=Math.random();
    private double maxSpeed=2.5;


    private PersonState state;

    public boolean symptot=true;


    private transient PupulationManager pupulationManager; // transient bo nie serializujemy całego menadżera
    public double width ,height;  //szerokosc i wysookosc okna

    private double timeSinceDirChange = 0;
    private double nextDirChangeTime = 1 + Math.random() * 4; // zmiana co 1–5 s kierunku oraz predkosci


    private double speed=1.5;
    private double angle;

    public boolean tooClose = false; //flaga czy byl wystarczajaco blisko zarazonego w danym kroku


    public double[] getPosition(){
    double [] tab = this.position.getComponents();
    return tab;

}
    public Person(double x, double y, PupulationManager pupulationManager) {
        position = new Vector2D(x, y);
        this.pupulationManager = pupulationManager;
        //active = true;
        double random =  Math.random();
        if(random <= 0.1){
          this.state = new InfectedState();
          if(random<=0.5)symptot = false;
        }else{
            this.state = new HealthyState();
        }
        //if(random >= 0.5)this.state = new ImmuneState(); //II przypadek
        width = pupulationManager.x;
        height = pupulationManager.y;

    }

    public void setState(PersonState state){
        this.state = state;
    }
    public PersonState getState(){
        return state;
    }

    public void move(double dt) {

        timeSinceDirChange+=dt;

        if (timeSinceDirChange >= nextDirChangeTime) {
            changeDir();
            timeSinceDirChange = 0;
            nextDirChangeTime = 1 + Math.random() * 4; // nowe losowe opóźnienie
            vx = speed * Math.cos(angle);
            vy = speed * Math.sin(angle);
        }


         double tx = position.getX()+vx*dt;
         double ty = position.getY()+vy*dt;
         reflectOrExit(tx,ty);
         position.setComponents(tx,ty);

    }

    public void changeDir(){
        speed = Math.random() * maxSpeed;
         angle = Math.random() * 2 * Math.PI;
    }

    void reflectOrExit(double x, double y) {
        if (x <= 0 || x >= width  || y <= 0 || y >= height) {
            if (Math.random() < 0.5) {
                if (x <= 0 || x >= width ) vx = -vx; // odbicie w poziomie
                if (y <= 0 || y >= height) vy = -vy; // odbicie w pionie


            } else {
                pupulationManager.subtract(this);
            }

        }
    }

    public boolean isInfected() {
        if(this.state instanceof InfectedState)return true;
        else  return false;
    }

    public double distanceTo(Person otherPerson) {
        return this.position.distance(otherPerson.position);
    }

    public void infect(boolean sym) {
        this.symptot = sym;
        this.setState(new InfectedState());
    }

    public void recover() {
        this.setState(new ImmuneState());
    }

    public void setPopulationManager(PupulationManager pupulationManager) {
    this.pupulationManager = pupulationManager;
    }

    public boolean isImmune() {
        if(this.state instanceof ImmuneState)return true;
        else  return false;
    }





    //zapis

    public Person clone() {
        try {
            Person copy = (Person) super.clone();
            copy.position = new Vector2D(position.getX(), position.getY());
            // skopiuj stan (możesz zrobić deep copy jeśli stan ma dane)
            copy.state = this.state;
            copy.speed = this.speed;
            copy.angle = this.angle;
            copy.timeSince = this.timeSince;
            copy.symptot = this.symptot;
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
