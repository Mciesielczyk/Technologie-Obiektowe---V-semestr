package com.traffic.symulacjaruchu.core;

import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import com.traffic.symulacjaruchu.model.TrafficLight;
import com.traffic.symulacjaruchu.patterns.strategy.DefaultStrategy;
import com.traffic.symulacjaruchu.patterns.strategy.FourWayLightsStrategy;
import com.traffic.symulacjaruchu.patterns.strategy.JunctionStrategy;
import com.traffic.symulacjaruchu.patterns.strategy.RoundaboutStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityGraphBuilder {
    private Map<String, Intersection> nodes = new HashMap<>();
    private List<Road> roads = new ArrayList<>();

    public CityGraphBuilder addIntersection(String id, double x, double y, boolean hasLights) {
        Intersection intersection = new Intersection(id, x, y);

        if (hasLights) {
            intersection.setStrategy(new FourWayLightsStrategy());
        } else {
            intersection.setStrategy(new DefaultStrategy());
        }

        nodes.put(id, intersection);
        return this;
    }

    public CityGraphBuilder addLightedIntersection(String id, double x, double y) {
        return addIntersection(id, x, y, true); // Delegacja do głównej metody
    }




    public Road connect(String fromId, String toId) {
        Intersection start = nodes.get(fromId);
        Intersection end = nodes.get(toId);
        if (start != null && end != null) {
            Road road = new Road(start, end);
            roads.add(road);
            return road;
        }
        return null;
    }

    public CityGraphBuilder createRoad(String fromId, String toId, Road.Priority priority) {
        Road r = connect(fromId, toId);
        if (r != null) r.setPriority(priority);
        return this;
    }

    public List<Road> build() {
        return roads;
    }

    public List<Intersection> getIntersections() {
        return new ArrayList<>(nodes.values());
    }

    public CityGraphBuilder addRoundabout(String id, double x, double y) {
        Intersection inter = new Intersection(id, x, y);
        inter.setStrategy(new RoundaboutStrategy());
        nodes.put(id, inter);
        return this;
    }



    public CityGraphBuilder addComplexRoundabout(String id, double x, double y) {
        double r = 40; // Promień ronda

        JunctionStrategy roundaboutStrategy = new RoundaboutStrategy();

        Intersection north = new Intersection(id + "_N", x, y - r);
        Intersection east  = new Intersection(id + "_E", x + r, y);
        Intersection south = new Intersection(id + "_S", x, y + r);
        Intersection west  = new Intersection(id + "_W", x - r, y);

        north.setStrategy(roundaboutStrategy);
        east.setStrategy(roundaboutStrategy);
        south.setStrategy(roundaboutStrategy);
        west.setStrategy(roundaboutStrategy);


        this.nodes.put(north.getId(), north);
        this.nodes.put(east.getId(), east);
        this.nodes.put(south.getId(), south);
        this.nodes.put(west.getId(), west);


        connect(id + "_N", id + "_W");
        connect(id + "_W", id + "_S");
        connect(id + "_S", id + "_E");
        connect(id + "_E", id + "_N");

        return this;
    }
}