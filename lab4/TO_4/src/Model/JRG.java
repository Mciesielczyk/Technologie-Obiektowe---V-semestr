package Model;

import Iterators.CarIterator;
import Iterators.ConcreteIterator;

import java.util.ArrayList;
import java.util.List;

public class JRG {
    String name;
    private double x;
    private double y;
    private List<Car> carsList =  new ArrayList<>();
    private int CarsNumber = 5;

    public JRG(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;

        for(int i=0; i<CarsNumber; i++){
            int dl = name.length()-1;
            String name1 = name.substring(dl);
            int id=Integer.parseInt(name1)*10+i;
            carsList.add(new Car(id));

        }


    }

    public List<Car> getCars(){
        return this.carsList;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public String getName(){
        return this.name;
    }

    public CarIterator iterator(){
        return new ConcreteIterator(carsList);
    }


}
