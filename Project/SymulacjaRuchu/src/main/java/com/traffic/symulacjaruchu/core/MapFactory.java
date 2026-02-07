package com.traffic.symulacjaruchu.core;

import com.traffic.symulacjaruchu.model.Intersection;
import com.traffic.symulacjaruchu.model.Road;
import java.util.List;

public class MapFactory {

    public static void buildDefaultCity(Simulation simulation, CityGraphBuilder builder) {

        // --- POZIOM E (NAJWYŻSZY) ---
        builder.addIntersection("E1", 150, 50, false);  // Nad A1
      //  builder.addIntersection("E2", 600, 20, false);  // Nad D5
        builder.addIntersection("E3", 1050, 50, false); // Nad rondem A4

        // --- 1. DEFINICJA PUNKTÓW ---
        builder.addIntersection("A1", 150, 150, false)
                .addIntersection("A2", 450, 150, true)
                .addIntersection("A3", 750, 150, false)
                .addComplexRoundabout("A4", 1050, 150);

        // Odnoga D5 (Północ)
        double midX_A = (450 + 750) / 2;
        builder.addIntersection("A2-A3-MID", midX_A, 150, false);
        builder.addIntersection("D5", midX_A, 50, false);

        // Rząd B - ZMIANA: B3 staje się rondem
        builder.addComplexRoundabout("B1", 150, 450)
                .addIntersection("B2", 450, 450, false)
                .addComplexRoundabout("B3", 750, 450) // <--- TUTAJ RONDO
                .addIntersection("B4", 1050, 450, true);

        // Odnoga D6 i D8
        double midY_BC = (450 + 750) / 2;
        builder.addIntersection("B2-C2-MID", 450, midY_BC, false);
        builder.addIntersection("D6", 600, midY_BC, false);
        builder.addIntersection("D8", 50, 450, false); // Nowa odnoga przy B1

        // Rząd C
        builder.addIntersection("C1", 150, 750, false)
                .addIntersection("C2", 450, 750, true)
                .addComplexRoundabout("C3", 750, 750)
                .addIntersection("C4", 1050, 750, false);

        // Odnogi D4 i D7
        double midX = (150 + 450) / 2;
        builder.addIntersection("C1-C2-MID", midX, 750, false);
        builder.addIntersection("D4", midX, 900, false);
        builder.addIntersection("D7", 1150, 750, false); // Nowa odnoga przy C4

        builder.addIntersection("D9", 450, 900, false); // Nowa odnoga przy C4

        // --- 2. POŁĄCZENIA POZIOME ---
        builder.connect("D9", "D4");
        builder.connect("D4", "D9");

        builder.connect("D9", "C2");
        builder.connect("C2", "D9");

        builder.connect("E1", "A1");
        builder.connect("A1", "E1");

        builder.connect("E1", "D5").setPriority(Road.Priority.MAIN);
        builder.connect("D5", "E1");

        builder.connect("E3", "A4");
        builder.connect("A4", "E3");

        // Pozioma trasa E1 <-> E2 <-> E3
        //builder.connect("E1", "E2");
       // builder.connect("E2", "E1");

        builder.connect("D5", "E3");
        builder.connect("E3", "D5").setPriority(Road.Priority.MAIN);

        builder.connect("E3", "A4_N");
        builder.connect("A4_N", "E3");


        // Rząd A
        builder.connect("A1", "A2").setPriority(Road.Priority.MAIN);
        builder.connect("A2", "A1");
        builder.connect("A3", "A4_W").setPriority(Road.Priority.YIELD);
        builder.connect("A4_W", "A3");

        // Rząd B (Uwzględniając nowe rondo B3)
        builder.connect("D8", "B1_W").setPriority(Road.Priority.YIELD); // Wjazd z D8 na rondo B1
        builder.connect("B1_W", "D8");

        builder.connect("B1_E", "B2");
        builder.connect("B2", "B1_E");
        builder.connect("B2", "B3_W").setPriority(Road.Priority.MAIN); // Dojazd do ronda B3
        builder.connect("B3_W", "B2");
        builder.connect("B3_E", "B4"); // Wyjazd z ronda B3 na B4
        builder.connect("B4", "B3_E").setPriority(Road.Priority.YIELD);

        // Rząd C
        builder.connect("C1", "C1-C2-MID").setPriority(Road.Priority.MAIN);
        builder.connect("C1-C2-MID", "C1").setPriority(Road.Priority.MAIN);
        builder.connect("C1-C2-MID", "C2").setPriority(Road.Priority.MAIN);
        builder.connect("C2", "C1-C2-MID").setPriority(Road.Priority.MAIN);
        builder.connect("D4", "C1-C2-MID").setPriority(Road.Priority.YIELD);
        builder.connect("C1-C2-MID", "D4");

        builder.connect("C2", "C3_W");
        builder.connect("C3_W", "C2");
        builder.connect("C3_E", "C4");
        builder.connect("C4", "C3_E");
        builder.connect("C4", "D7"); // Wyjazd na wschodnią odnogę
        builder.connect("D7", "C4").setPriority(Road.Priority.YIELD);

        // --- 3. POŁĄCZENIA PIONOWE ---

        // Kolumna 1
        builder.connect("A1", "B1_N").setPriority(Road.Priority.YIELD);
        builder.connect("B1_N", "A1");
        builder.connect("B1_S", "C1");
        builder.connect("C1", "B1_S");

        // Kolumna 2
        builder.connect("A2", "B2").setPriority(Road.Priority.MAIN);
        builder.connect("B2", "A2");
        builder.connect("B2", "B2-C2-MID").setPriority(Road.Priority.MAIN);
        builder.connect("B2-C2-MID", "B2");
        builder.connect("B2-C2-MID", "C2").setPriority(Road.Priority.MAIN);
        builder.connect("C2", "B2-C2-MID");
        builder.connect("D6", "B2-C2-MID").setPriority(Road.Priority.YIELD);
        builder.connect("B2-C2-MID", "D6");

        // Kolumna 3 (Uwzględniając ronda B3 i C3)
        builder.connect("A3", "B3_N").setPriority(Road.Priority.YIELD);
        builder.connect("B3_N", "A3");
        builder.connect("B3_S", "C3_N").setPriority(Road.Priority.YIELD);
        builder.connect("C3_N", "B3_S");
        // Poprawka dla ronda C3 (domknięcie)
       // builder.connect("C3_S", "C3_W");

        // Kolumna 4
        builder.connect("A4_S", "B4");
        builder.connect("B4", "A4_S");
        builder.connect("B4", "C4");
        builder.connect("C4", "B4");

        // Odnoga D5
        builder.connect("A2", "A2-A3-MID").setPriority(Road.Priority.MAIN);
        builder.connect("A2-A3-MID", "A2");
        builder.connect("A2-A3-MID", "A3").setPriority(Road.Priority.MAIN);
        builder.connect("A3", "A2-A3-MID");
        builder.connect("D5", "A2-A3-MID").setPriority(Road.Priority.YIELD);
        builder.connect("A2-A3-MID", "D5").setPriority(Road.Priority.YIELD);

        // --- 4. FINALIZACJA ---
        List<Road> roadsList = builder.build();
        List<Intersection> allIntersections = builder.getIntersections();

        simulation.setAllIntersections(allIntersections);
        for (Road r : roadsList) {
            simulation.addRoad(r);
        }
    }
}