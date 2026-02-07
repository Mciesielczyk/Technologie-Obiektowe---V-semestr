package com.traffic.symulacjaruchu.patterns;

import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.*;
import java.util.Random;

public class VehicleFactory {
    private static final Random random = new Random();

    public static Vehicle createRandomVehicle(Road startRoad) {
        double rand = random.nextDouble();

        if (rand < 0.05) return new Ambulance(startRoad);
        if (rand < 0.15) return new Bus(startRoad);
        if (rand < 0.25) return new Truck(startRoad);
        if (rand < 0.35) return new CrazyDriver(startRoad);

        return new Car(startRoad); // Domyślny typ
    }
}