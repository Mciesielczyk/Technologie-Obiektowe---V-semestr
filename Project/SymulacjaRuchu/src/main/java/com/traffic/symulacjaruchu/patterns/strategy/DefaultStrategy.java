package com.traffic.symulacjaruchu.patterns.strategy;


import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.vehicles.Vehicle;
import java.util.List;

public class DefaultStrategy implements JunctionStrategy {

    @Override
    public void update() {
    }
    @Override
    public boolean canVehicleEnter(Road incomingRoad, Vehicle vehicle, List<Vehicle> allVehicles) {
        if (!checkPrioritySigns(incomingRoad, vehicle, allVehicles)) {
            return false;
        }

        if (incomingRoad.getPriority() == Road.Priority.MAIN) {
            return applyRightHandRule(incomingRoad, vehicle, allVehicles, Road.Priority.MAIN);
        }

        if (incomingRoad.getPriority() == Road.Priority.YIELD) {
            return applyRightHandRule(incomingRoad, vehicle, allVehicles, Road.Priority.YIELD);
        }

        return applyRightHandRule(incomingRoad, vehicle, allVehicles, Road.Priority.NORMAL);
    }

    private boolean applyRightHandRule(Road myRoad, Vehicle myVehicle, List<Vehicle> allVehicles, Road.Priority priorityToCompare) {
        double myAngle = getAngle(myRoad);

        for (Vehicle other : allVehicles) {
            if (other == myVehicle) continue;

            if (other.getCurrentRoad().getEnd() == myRoad.getEnd()) {
                if (other.getCurrentRoad().getPriority() == priorityToCompare) {
                    double otherAngle = getAngle(other.getCurrentRoad());
                    double angleDiff = (otherAngle - myAngle + 360) % 360;

                    if (angleDiff > 45 && angleDiff < 135) {
                        if (other.getProgress() > 0.7) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public double getSpeedModifier(double progress, boolean isIncoming) {
        if (isIncoming && progress > 0.8) return 0.6;
        return 1.0;
    }

    private double getAngle(Road road) {
        return Math.toDegrees(Math.atan2(
                road.getEnd().getY() - road.getStart().getY(),
                road.getEnd().getX() - road.getStart().getX()
        ));
    }
}